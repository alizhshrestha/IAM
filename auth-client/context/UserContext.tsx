// context/UserContext.tsx
'use client'

import { createContext, useContext, useEffect, useState } from "react";
import { parseJwt } from "@/lib/jwt";

export type AuthenticatedUser = {
    username: string;
    roles: string[];
    tenant: string;
    userId?: string;
    email?: string;
};

const UserContext = createContext<AuthenticatedUser | null>(null);

export const useUser = () => useContext(UserContext);

export const UserProvider = ({ children }: { children: React.ReactNode }) => {
    const [user, setUser] = useState<AuthenticatedUser | null>(null);

    useEffect(() => {
        const token = sessionStorage.getItem("access_token");
        const tenant = sessionStorage.getItem("tenant");
        if (!token || !tenant) return;

        const decoded = parseJwt(token);
        if (decoded) {
            const userObj: AuthenticatedUser = {
                username: decoded.sub,
                roles: decoded.roles || [],
                tenant,
                userId: decoded.user_id || undefined,
                email: decoded.email || undefined,
            };
            setUser(userObj);
        }
    }, []);

    return <UserContext.Provider value={user}>{children}</UserContext.Provider>;
};
