'use client';

import { useAuth } from "@/hooks/useAuth";
import { useRouter } from "next/navigation";
import { useEffect } from "react";

export default function RoleBasedRedirect(){
    const router = useRouter();
    const {user, loading} = useAuth();

    useEffect(()=> {
        if(loading || !user) return;

        if(user.roles.includes('ROLE_ADMIN')){
            router.replace('/admin');
        }else{
            router.replace('/dashboard');
        }
    }, [user, loading]);

    return null;
}