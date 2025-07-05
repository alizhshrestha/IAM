'use client'

import { useState } from "react"

export default function LoginPage() {
    const [isLoading, setIsLoading] = useState(false);

    const handleLogin = async () => {
        setIsLoading(true);

        const tenant = 'tenant1'; // later, dynamically from subdomain or input
        const clientId = process.env.NEXT_PUBLIC_CLIENT_ID!;
        const redirectUri = process.env.NEXT_PUBLIC_REDIRECT_URI!;
        const authBaseUrl = `http://${tenant}.auth.example.com:9000/oauth2/authorize`;

        const csrfToken = crypto.randomUUID(); // Unique state for CSRF protection
        const state = `${tenant}|${csrfToken}`
        const codeVerifier = generateCodeVerifier();
        const codeChallenge = await generateCodeChallenge(codeVerifier);

        sessionStorage.setItem('pkce_code_verifier', codeVerifier);
        sessionStorage.setItem('oauth_state', csrfToken);
        sessionStorage.setItem('tenant', tenant);

        const url = new URL(authBaseUrl);
        url.searchParams.set('response_type', 'code');
        url.searchParams.set('client_id', clientId);
        url.searchParams.set('redirect_uri', redirectUri);
        url.searchParams.set('scope', 'openid profile');
        url.searchParams.set('code_challenge', codeChallenge);
        url.searchParams.set('code_challenge_method', 'S256');
        url.searchParams.set('state', state);

        window.location.href = url.toString();
    };

    return (
        <main className="flex h-screen items-center justify-center bg-gray-100">
            <button
                onClick={handleLogin}
                className="px-6 py-3 bg-green-500 text-white rounded-md shadow-md hover:bg-green-700 transition"
                disabled={isLoading}
            >
                {isLoading ? 'Redirecting...' : 'Login with Tenant1'}
            </button>
        </main>
    )
}

// Helpers
function generateCodeVerifier(): string {
  const array = new Uint8Array(32);
  crypto.getRandomValues(array);

  return Array.from(array)
    .map((b) => b.toString(16).padStart(2, '0'))
    .join('');
}

async function generateCodeChallenge(codeVerifier: string): Promise<string> {
  const encoder = new TextEncoder();
  const data = encoder.encode(codeVerifier);
  const digest = await crypto.subtle.digest('SHA-256', data);
  return btoa(String.fromCharCode(...new Uint8Array(digest)))
    .replace(/\+/g, '-')
    .replace(/\//g, '_')
    .replace(/=+$/, '');
}