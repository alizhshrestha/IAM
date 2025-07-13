'use client';

import RoleSelector from "@/components/teacher/RoleSelector";
import { getRolePath, School } from "@/types";
import { useRouter, useSearchParams } from "next/navigation";
import { useEffect, useState } from "react";

export default function CallbackPage() {
    const searchParams = useSearchParams();
    const router = useRouter();

    const [error, setError] = useState<string | null>(null);
    const [availableRoles, setAvailableRoles] = useState<string[]>([]);
    const [selectedSchool, setSelectedSchool] = useState<School | null>(null);

    useEffect(() => {
        const handleOAuthCallback = async () => {
            const code = searchParams.get("code");
            const state = searchParams.get("state");

            if (!code || !state) {
                setError("Missing code or state from callback URL.");
                return;
            }

            const [tenant, receivedState] = state.split("|");
            const expectedState = sessionStorage.getItem("oauth_state");
            const codeVerifier = sessionStorage.getItem("pkce_code_verifier");

            if (!codeVerifier || !expectedState || receivedState !== expectedState) {
                setError("State or code verifier mismatch");
                return;
            }

            const tokenEndpoint = `http://${tenant}.auth.example.com:9000/oauth2/token`;

            try {
                const res = await fetch(tokenEndpoint, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded",
                    },
                    body: new URLSearchParams({
                        grant_type: "authorization_code",
                        code,
                        redirect_uri: sessionStorage.getItem("redirect-uri")!,
                        client_id: sessionStorage.getItem("client_id")!,
                        code_verifier: codeVerifier,
                    }),
                });

                const data = await res.json();
                if (!res.ok) throw new Error(data.error_description || "Token exchange failed");

                sessionStorage.setItem("access_token", data.access_token);
                sessionStorage.setItem("id_token", data.id_token || "");

                const [, payload] = data.access_token.split(".");
                const decoded = JSON.parse(atob(payload));
                const roles: string[] = decoded.roles || [];
                const appUserId: string = decoded.app_user_id;

                setAvailableRoles(roles);

                // 🧠 Get school info from sessionStorage
                const schoolId = sessionStorage.getItem("selected_school_id");
                const schoolName = sessionStorage.getItem("selected_school_name");

                if (!schoolId || !schoolName) {
                    setError("Missing school info from session.");
                    return;
                }

                // Fetch full school data for this user to get role
                const schoolResponse = await fetch("http://localhost:8081/api/users/me/schools", {
                    headers: { Authorization: `Bearer ${data.access_token}` },
                });

                console.log("Fetching school response: ", schoolResponse);

                const allSchools: School[] = await schoolResponse.json();
                if (!schoolResponse.ok) throw new Error("Failed to fetch school data");

                const matchingSchool = allSchools.find((s) => s.id === schoolId);
                if (!matchingSchool) throw new Error("School not found for user");
                console.log("Matching school: ", matchingSchool);
                setSelectedSchool({ ...matchingSchool});
            } catch (err: any) {
                setError(err.message);
            }
        };

        handleOAuthCallback();
    }, [searchParams]);

    const handleRoleSelect = (role: string) => {
        if (!selectedSchool) return;

        const rolePath = getRolePath(role);

        localStorage.setItem("selected_school_id", selectedSchool.id);
        localStorage.setItem("selected_role", role);
        localStorage.setItem("user_id", selectedSchool.userId);
        localStorage.setItem("tenant_id", selectedSchool.tenantId || "");

        router.push(`/schools/${selectedSchool.id}/${rolePath}`);
    };

    useEffect(() => {
        if (selectedSchool && availableRoles.length === 1) {
            handleRoleSelect(availableRoles[0]);
        }
    }, [selectedSchool, availableRoles]);

    if (error) return <div className="p-6 text-red-600">Error: {error}</div>;

    if (selectedSchool && availableRoles.length > 1) {
        return <RoleSelector roles={availableRoles} onSelect={handleRoleSelect} />;
    }

    return null;
}
