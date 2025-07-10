'use client'

import { useEffect, useState } from "react"

type Tenant = {
    id: string;
    name: string;
    domain: string;
}

export default function LoginPage() {
    const [isLoading, setIsLoading] = useState(false);
    const [tenants, setTenants] = useState<Tenant[]>([]);
    const [selectedTenant, setSelectedTenant] = useState<Tenant | null>(null);
    // const tenantId = 'f8a231cb-3b45-4d67-87b0-08df2d3e9c11';

    useEffect(()=>{
        fetch(`http://public.auth.example.com:9000/public/api/tenants`)
        .then((res) => res.json())
        .then(setTenants)
        .catch((err) => console.error("Failed to fetch tenants", err));
    },[]);

    console.log(tenants);

    const handleLogin = async () => {

        if(!selectedTenant){
            alert('Please select a tenant');
            return;
        }

        setIsLoading(true);

        const tenant = selectedTenant;
        const tenantId = selectedTenant.id;
        const clientId = process.env.NEXT_PUBLIC_CLIENT_ID!;
        const redirectUri = process.env.NEXT_PUBLIC_REDIRECT_URI!;
        const authBaseUrl = `http://${tenantId}.auth.example.com:9000/oauth2/authorize`;

        const csrfToken = crypto.randomUUID(); // Unique state for CSRF protection
        const state = `${tenantId}|${csrfToken}`
        const codeVerifier = generateCodeVerifier();
        const codeChallenge = await generateCodeChallenge(codeVerifier);

        sessionStorage.setItem('pkce_code_verifier', codeVerifier);
        sessionStorage.setItem('oauth_state', csrfToken);
        sessionStorage.setItem('tenant', tenantId);
        console.log(`Tenant is: ${tenant}`)

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
        <main className="flex flex-col h-screen gap-4 items-center justify-center bg-gray-100">
            <div>
                <label className="block mb-1 font-medium">Select Tenant</label>
                <select
                    className="px-4 py-2 rounded border border-gray-300 text-black"
                    value={selectedTenant?.id || ""}
                    onChange={(e) => {
                        const tenant = tenants.find((t) => t.id === e.target.value) || null;
                        setSelectedTenant(tenant);
                    }}
                >
                    <option value="">-- Choose Tenant --</option>
                    {
                        tenants.map((tenant) => (
                            <option key={tenant.id} value={tenant.id}>
                                {tenant.name}
                            </option>
                        ))
                    }
                </select>
            </div>
            <button
                onClick={handleLogin}
                className="px-6 py-3 bg-green-500 text-white rounded-md shadow-md hover:bg-green-700 transition"
                disabled={isLoading || !selectedTenant}
            >
                {isLoading ? 'Redirecting...' : `Login with ${selectedTenant?.name}`}
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