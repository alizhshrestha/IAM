'use client';

import { useEffect, useState } from "react";
import SchoolSelector from "@/components/selector/SchoolSelector";
import ClientSelector from "@/components/selector/ClientSelector";
import { generateCodeChallenge, generateCodeVerifier } from "@/lib/pkce";
import { useRouter } from "next/navigation";

interface ClientInfo {
    clientId: string;
    clientName: string;
    redirectUri: string;
    scopes: string;
}

interface SchoolInfo {
    schoolId: string;
    schoolName: string;
    tenantId: string;
    tenantDomain: string;
    clients: ClientInfo[];
}

export default function LoginPage() {
    const [schools, setSchools] = useState<SchoolInfo[]>([]);
    const [selectedSchool, setSelectedSchool] = useState<SchoolInfo | null>(null);
    const [selectedClient, setSelectedClient] = useState<ClientInfo | null>(null);
    const router = useRouter();

    useEffect(() => {
        const fetchSchools = async () => {
            try {
                const res = await fetch('http://public.auth.example.com:9000/public/api/schools');
                const data = await res.json();
                setSchools(data);
            } catch (err) {
                console.error("Failed to fetch schools", err);
            }
        };

        fetchSchools();
    }, []);

    useEffect(() => {
        if (!selectedSchool) return;

        if (selectedSchool.clients.length === 1) {
            setSelectedClient(selectedSchool.clients[0]);
        }
    }, [selectedSchool]);

    useEffect(() => {
        if (selectedSchool && selectedClient) {
            handleLogin();
        }
    }, [selectedClient]);

    const handleLogin = async () => {
        const tenantId = selectedSchool!.tenantId;
        const clientId = selectedClient!.clientId;
        const redirectUri = selectedClient!.redirectUri;
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
        sessionStorage.setItem('selected_school_id', selectedSchool!.schoolId);
        sessionStorage.setItem('selected_school_name', selectedSchool!.schoolName);

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
        <main className="p-6 max-w-xl mx-auto space-y-4">
            {!selectedSchool && (
                <SchoolSelector schools={schools} onSelect={setSelectedSchool} />
            )}

            {selectedSchool && selectedSchool.clients.length > 1 && !selectedClient && (
                <ClientSelector clients={selectedSchool.clients} onSelect={setSelectedClient} />
            )}
        </main>
    );
}
