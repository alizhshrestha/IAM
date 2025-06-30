# OAuth2 PKCE Flow Documentation

## 1. Introduction

This document provides an overview of the OAuth2 architecture in the auth-server application and details the requirements for implementing the OAuth2 PKCE (Proof Key for Code Exchange) flow.

## 2. Current OAuth2 Implementation

### 2.1 Architecture Overview

The auth-server is built using Spring Security OAuth2 Authorization Server and implements a multi-tenant architecture with the following key components:

#### 2.1.1 Core Entities

- **Realm**: Represents a security domain that contains users, clients, and roles
- **Tenant**: Represents an organization within a realm
- **User**: Represents an end-user who can authenticate and access resources
- **Role**: Represents a set of permissions assigned to users
- **OAuth2RegisteredClient**: Represents an OAuth2 client application registered with the authorization server
- **OAuth2Authorization**: Represents an authorization granted by a user to a client
- **OAuth2AuthorizationConsent**: Represents user consent for client authorizations

#### 2.1.2 Key Components

- **AuthServerConfig**: Configures the OAuth2 authorization server, including JWT token customization
- **RealmAwareRegisteredClientRepository**: Retrieves OAuth2 clients based on the current realm context
- **RealmAwareUserDetailsService**: Loads user details based on the current realm context
- **ClientController**: Provides REST endpoints for managing OAuth2 clients

### 2.2 Current OAuth2 Flow

The current implementation supports the standard OAuth2 authorization code flow:

1. **Client Registration**: Clients are registered through the ClientController and stored in the oauth2_registered_client table
2. **Authorization Request**: Clients redirect users to the authorization endpoint
3. **User Authentication**: Users authenticate with their credentials
4. **Consent**: Users provide consent for the requested scopes
5. **Authorization Code**: The server issues an authorization code to the client
6. **Token Exchange**: The client exchanges the authorization code for access and refresh tokens
7. **Resource Access**: The client uses the access token to access protected resources

## 3. OAuth2 PKCE Flow

### 3.1 What is PKCE?

PKCE (Proof Key for Code Exchange) is an extension to the OAuth2 authorization code flow designed to prevent authorization code interception attacks, particularly for public clients such as mobile and single-page applications (SPAs).

### 3.2 Why PKCE is Needed

PKCE addresses security vulnerabilities in the standard OAuth2 authorization code flow:

1. **Code Interception**: In the standard flow, if an attacker intercepts the authorization code, they could exchange it for tokens
2. **Public Clients**: Public clients cannot securely store client secrets, making them vulnerable to impersonation
3. **Mobile and SPA Security**: Mobile apps and SPAs are particularly vulnerable to code interception attacks

### 3.3 How PKCE Works

The PKCE flow adds the following steps to the standard authorization code flow:

1. **Code Verifier Generation**: The client generates a random string called the "code verifier"
2. **Code Challenge Derivation**: The client creates a "code challenge" by applying a transformation to the code verifier (typically SHA-256 hashing followed by base64url encoding)
3. **Authorization Request with Challenge**: The client includes the code challenge and challenge method in the authorization request
4. **Code Issuance**: The server stores the code challenge and issues an authorization code as usual
5. **Token Request with Verifier**: When exchanging the authorization code for tokens, the client includes the original code verifier
6. **Server Verification**: The server transforms the code verifier using the same method and verifies it matches the stored code challenge before issuing tokens

## 4. Implementing PKCE in the Auth Server

### 4.1 Current Support for PKCE

The auth-server already has partial support for PKCE:

1. The OAuth2RegisteredClient model includes a clientSettings field that can store PKCE configuration
2. The commented code in AuthServerConfig (lines 79-97) shows an example of a client with PKCE enabled via `requireProofKey(true)`
3. Spring Security OAuth2 Authorization Server natively supports PKCE

### 4.2 Required Changes

To fully implement PKCE support, the following changes are needed:

1. **Client Settings Configuration**:
   - Ensure the clientSettings field in OAuth2RegisteredClient includes the `requireProofKey` setting
   - Update the OAuthClientMapper to correctly map this setting

2. **Client Registration Updates**:
   - Update the ClientController to allow setting PKCE requirements during client registration
   - Provide guidance to client developers on enabling PKCE

3. **Documentation and Examples**:
   - Create documentation for client developers on how to implement PKCE
   - Provide example code for different client types (web, mobile, SPA)

4. **Testing**:
   - Create test cases to verify PKCE flow works correctly
   - Test with different client types and challenge methods

### 4.3 Implementation Steps

1. **Fix OAuthClientMapper Bug**:
   - Correct the mapping of grant types, redirect URIs, and scopes in the OAuthClientMapper class

2. **Update Client Registration**:
   - Enhance the ClientController to explicitly support PKCE configuration
   - Add validation for PKCE-related settings

3. **Client Documentation**:
   - Create developer documentation explaining how to use PKCE with the auth-server
   - Include code samples for different client platforms

4. **Testing**:
   - Develop test cases for the PKCE flow
   - Verify that authorization fails if an incorrect code verifier is provided

## 5. PKCE Flow Example

### 5.1 Client-Side Flow

```javascript
// 1. Generate a code verifier (random string of 43-128 chars)
const codeVerifier = generateRandomString(64);

// 2. Generate code challenge (base64url encoded SHA-256 hash of verifier)
const codeChallenge = base64urlEncode(sha256(codeVerifier));

// 3. Redirect to authorization endpoint with code challenge
const authUrl = 'https://localhost:9000/realms/myrealm/oauth2/authorize' +
  '?client_id=myclient' +
  '&response_type=code' +
  '&redirect_uri=https://myapp.com/callback' +
  '&scope=openid profile' +
  '&code_challenge=' + codeChallenge +
  '&code_challenge_method=S256';

// 4. After redirect back with code, exchange for tokens
const tokenResponse = await fetch('https://localhost:9000/realms/myrealm/oauth2/token', {
  method: 'POST',
  headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
  body: new URLSearchParams({
    grant_type: 'authorization_code',
    client_id: 'myclient',
    code: authorizationCode,
    redirect_uri: 'https://myapp.com/callback',
    code_verifier: codeVerifier
  })
});
```

### 5.2 Server-Side Verification

The auth-server will:
1. Receive the token request with the code_verifier
2. Retrieve the stored code_challenge from the authorization request
3. Apply the code_challenge_method (S256) to the code_verifier
4. Verify the result matches the stored code_challenge
5. Issue tokens only if verification succeeds

## 6. Conclusion

Implementing PKCE in the auth-server will enhance security for public clients such as mobile apps and SPAs. The current architecture already has the foundation for PKCE support, requiring minimal changes to fully implement this security enhancement.

PKCE is particularly important for modern application architectures where the client code runs in potentially insecure environments like browsers or mobile devices. By implementing PKCE, the auth-server will provide a more secure authentication option for these client types.