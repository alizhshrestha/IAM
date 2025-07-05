'use client';

import { useRouter, useSearchParams } from "next/navigation";
import { useEffect, useState } from "react";

export default function CallbackPage() {
    const searchParams = useSearchParams();
    const router = useRouter();
    const [error, setError] = useState<string | null>(null);
    const [token, setToken] = useState<any>(null);

    useEffect(() => {
        const handleOAuthCallback = async () => {
            const code = searchParams.get('code');
            const state = searchParams.get('state');

            if (!code || !state) {
                setError('Missing code or state from callback URL.');
                return;
            }

            const [tenant, receivedState] = state.split('|');

            const expectedState = sessionStorage.getItem('oauth_state');
            const codeVerifier = sessionStorage.getItem('pkce_code_verifier');

            if (!codeVerifier || !expectedState || receivedState !== expectedState) {
                setError('state or code verifier mismatch');
                return;
            }

            const tokenEndpoint = `http://${tenant}.auth.example.com:9000/oauth2/token`;

            try {
                const res = await fetch(tokenEndpoint, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: new URLSearchParams({
                        grant_type: 'authorization_code',
                        code,
                        redirect_uri: process.env.NEXT_PUBLIC_REDIRECT_URI!,
                        client_id: process.env.NEXT_PUBLIC_CLIENT_ID!,
                        code_verifier: codeVerifier,
                    }),
                });

                const data = await res.json();

                if (!res.ok) {
                    throw new Error(data.error_description || 'Token exchange failed');
                }

                setToken(data);
                sessionStorage.setItem('access_token', data.access_token);
                sessionStorage.setItem('id_token', data.id_token || '');

                // router.push('/dashboard');
            } catch (err: any) {
                setError(err.message);
            }
        };

        handleOAuthCallback();
    }, [searchParams]);

    if (error) {
        return <div className="p-6 text-red-600">Error: {error}</div>;
    }

    if (!token) {
        return <div className="p-6 text-gray-500">Exchanging code for tokens...</div>;
    }

    return (
        <main className="p-6">
            <h1 className="text-xl font-bold mb-4">Authentication Successful</h1>
            <pre className="bg-gray-700 text-white p-4 rounded-md overflow-x-auto text-sm">
                {JSON.stringify(token, null, 2)}
            </pre>
        </main>
    );
}