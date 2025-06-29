CREATE TABLE realm
(
    id          VARCHAR(36) PRIMARY KEY, -- UUID
    name        VARCHAR(100) UNIQUE NOT NULL,
    description VARCHAR(255),
    enabled     BOOLEAN DEFAULT TRUE
);

CREATE TABLE tenant
(
    id          VARCHAR(36) PRIMARY KEY, -- UUID
    realm_id    VARCHAR(36)     NOT NULL,
    name        VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    enabled     BOOLEAN DEFAULT TRUE,
    UNIQUE (realm_id, name),
    FOREIGN KEY (realm_id) REFERENCES realm (id)
);

-- Create tables
CREATE TABLE users
(
    id        VARCHAR(36) PRIMARY KEY, -- UUID
    username  VARCHAR(100) NOT NULL,
    password  VARCHAR(255) NOT NULL,
    enabled   BOOLEAN DEFAULT TRUE,
    realm_id  VARCHAR(36)     NOT NULL,
    tenant_id VARCHAR(36)     NOT NULL,
    UNIQUE (username, tenant_id),   -- Unique within tenant
    FOREIGN KEY (realm_id) REFERENCES realm (id),
    FOREIGN KEY (tenant_id) REFERENCES tenant (id)
);

CREATE TABLE roles
(
    id       VARCHAR(36) PRIMARY KEY,
    name     VARCHAR(100) NOT NULL,
    realm_id VARCHAR(36)     NOT NULL,
    UNIQUE (name, realm_id),
    FOREIGN KEY (realm_id) REFERENCES realm (id)
);

CREATE TABLE user_roles
(
    user_id VARCHAR(36) NOT NULL,
    role_id VARCHAR(36) NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);


-- OAuth2 Registered Client
CREATE TABLE oauth2_registered_client
(
    id                            VARCHAR(100) PRIMARY KEY,
    client_id                     VARCHAR(100) UNIQUE NOT NULL,
    client_id_issued_at           TIMESTAMP,
    client_secret                 VARCHAR(200),
    client_secret_expires_at      TIMESTAMP,
    client_name                   VARCHAR(200),
    client_authentication_methods VARCHAR(1000),
    authorization_grant_types     VARCHAR(1000),
    redirect_uris                 VARCHAR(1000),
    scopes                        VARCHAR(1000),
    client_settings               VARCHAR(2000),
    token_settings                VARCHAR(2000),
    realm_id                      VARCHAR(36),
    FOREIGN KEY (realm_id) REFERENCES realm(id)
);

-- OAuth2 Authorization
CREATE TABLE oauth2_authorization
(
    id                            VARCHAR(100) PRIMARY KEY,
    registered_client_id          VARCHAR(100) NOT NULL,
    principal_name                VARCHAR(200) NOT NULL,
    authorization_grant_type      VARCHAR(100) NOT NULL,
    attributes                    BLOB,
    state                         VARCHAR(500),
    authorization_code_value      BLOB,
    authorization_code_issued_at  TIMESTAMP,
    authorization_code_expires_at TIMESTAMP,
    authorization_code_metadata   BLOB,
    access_token_value            BLOB,
    access_token_issued_at        TIMESTAMP,
    access_token_expires_at       TIMESTAMP,
    access_token_metadata         BLOB,
    access_token_type             VARCHAR(100),
    access_token_scopes           VARCHAR(1000),
    oidc_id_token_value           BLOB,
    oidc_id_token_issued_at       TIMESTAMP,
    oidc_id_token_expires_at      TIMESTAMP,
    oidc_id_token_metadata        BLOB,
    refresh_token_value           BLOB,
    refresh_token_issued_at       TIMESTAMP,
    refresh_token_expires_at      TIMESTAMP,
    refresh_token_metadata        BLOB
);

-- OAuth2 Authorization Consent
CREATE TABLE oauth2_authorization_consent
(
    registered_client_id VARCHAR(100) NOT NULL,
    principal_name       VARCHAR(200) NOT NULL,
    authorities          VARCHAR(1000),
    PRIMARY KEY (registered_client_id, principal_name)
);


CREATE INDEX idx_users_realm_id ON users (realm_id);
CREATE INDEX idx_users_tenant_id ON users (tenant_id);
CREATE INDEX idx_roles_realm_id ON roles (realm_id);
