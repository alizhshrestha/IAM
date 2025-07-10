'use client';

import SchoolSelector, { getRolePath, School } from "@/components/SchoolSelector";
import { useRouter, useSearchParams } from "next/navigation";
import { useEffect, useState } from "react";

export default function CallbackPage() {
    const searchParams = useSearchParams();
    const router = useRouter();
    const [error, setError] = useState<string | null>(null);
    const [schools, setSchools] = useState<School[] | null>(null);

    useEffect(() => {
        const handleOAuthCallback = async () => {
            const code = searchParams.get('code');
            const state = searchParams.get('state');

            if (!code || !state) {
                setError('Missing code or state from callback URL.');
                return;
            }

            const [tenant, receivedState] = state.split('|');
            console.log(`tenant: ${tenant}, receivedState: ${receivedState}`);

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
                sessionStorage.setItem('access_token', data.access_token);
                sessionStorage.setItem('id_token', data.id_token || '');

                // ✅ Fetch schools
                const schoolResponse = await fetch(`http://localhost:8081/api/users/me/schools`, {
                    headers: {
                        Authorization: `Bearer ${data.access_token}`,
                    },
                });

                const schoolsData = await schoolResponse.json();
                if(!schoolResponse.ok) throw new Error("Failed to fetch schools");

                if(schoolsData.length === 1){
                    const school = schoolsData[0];
                    const rolePath = getRolePath(school.role);
                    router.push(`/schools/${school.id}/${rolePath}`)
                }else{
                    setSchools(schoolsData);
                }
            } catch (err: any) {
                setError(err.message);
            }
        };

        handleOAuthCallback();
    }, [searchParams, router]);

    if (error) {
        return <div className="p-6 text-red-600">Error: {error}</div>;
    }

    if(schools){
        return <SchoolSelector schools={schools}/>
    }

    return null;
}