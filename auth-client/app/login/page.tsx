'use client'

import ClientSelector from "@/components/ClientSelector";
import TenantSelector from "@/components/TenantSelector";
import { generateCodeChallenge, generateCodeVerifier } from "@/lib/pkce";
import { ClientInfo, Tenant } from "@/types";
import { useEffect, useState } from "react"

export default function LoginPage() {

    const [tenants, setTenants] = useState<Tenant[]>([]);
    const [clients, setClients] = useState<ClientInfo[]>([]);
    // const [schools, setSchools] = useState([]);
    const [selectedTenant, setSelectedTenant] = useState<Tenant | null>(null);
    const [selectedClient, setSelectedClient] = useState<ClientInfo | null>(null);
    // const [isLoading, setIsLoading] = useState(false);
    // const tenantId = 'f8a231cb-3b45-4d67-87b0-08df2d3e9c11';


    //Fetch tenants on load
    useEffect(() => {
        const fetchTenants = async () => {
            try {
                const res = await fetch(`http://public.auth.example.com:9000/public/api/tenants`);
                const data = await res.json();
                setTenants(data);
            } catch (error) {
                console.error('Failed to fetch tenants', error);
            }
        };
        fetchTenants();
    }, []);

    //Step2: fetch clients once tenant is selected
    useEffect(() => {
        if (!selectedTenant) return;
        const fetchClients = async () => {
            try {
                const res = await fetch(`http://public.auth.example.com:9000/public/api/clients/tenant?tenantId=${selectedTenant.id}`);
                const data = await res.json();
                setClients(data);
            } catch (error) {
                console.error("Failed to fetch clients", error);
            }
        }

        fetchClients();
    }, [selectedTenant]);


    useEffect(() => {
        if (!selectedClient || !selectedTenant) return;
        console.log(`selected client: ${selectedClient} & selected tenant: ${selectedTenant}`)
        handleLogin();
    }, [selectedClient]);

    const handleLogin = async (): Promise<void> => {
        const tenantId = selectedTenant!.id;
        const clientId = selectedClient!.clientId;
        const redirectUri = selectedClient!.redirectUris;
        const scope = selectedClient!.scopes.replace(/,/g, ' ');

        const csrfToken = crypto.randomUUID();
        const state = `${tenantId}|${csrfToken}`;
        const codeVerifier = generateCodeVerifier();
        const codeChallenge = await generateCodeChallenge(codeVerifier);

        sessionStorage.setItem('pkce_code_verifier', codeVerifier);
        sessionStorage.setItem('oauth_state', csrfToken);
        sessionStorage.setItem('tenant', tenantId);
        sessionStorage.setItem('client_id', clientId);
        sessionStorage.setItem('redirect-uri', redirectUri);


        const authBaseUrl = `http://${tenantId}.auth.example.com:9000/oauth2/authorize`;

        const url = new URL(authBaseUrl);
        url.searchParams.set('response_type', 'code');
        url.searchParams.set('client_id', clientId);
        url.searchParams.set('redirect_uri', redirectUri);
        url.searchParams.set('scope', scope);
        url.searchParams.set('code_challenge', codeChallenge);
        url.searchParams.set('code_challenge_method', 'S256');
        url.searchParams.set('state', state);

        window.location.href = url.toString();

    };

    return (
        <main className="p-6 max-w-xl mx-auto">
            {!selectedTenant && (
                <TenantSelector tenants={tenants} onSelect={setSelectedTenant} />
            )}

            {selectedTenant && !selectedClient && clients.length > 0 && (
                <ClientSelector clients={clients} onSelect={setSelectedClient} />
            )}
        </main>
    );
}

