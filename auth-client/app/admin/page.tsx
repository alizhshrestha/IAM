'use client'

import { useAuth } from "@/hooks/useAuth"
import { useRouter } from "next/navigation";
import { useEffect } from "react";

export default function AdminPage() {
    const { user, loading } = useAuth();
    const router = useRouter();

    useEffect(() => {
        if (!loading && (!user?.roles.includes('ROLE_ADMIN'))) {
            router.replace('/unauthorized');
        }
    }, [user, loading]);

    if (loading) return <p>Loading...</p>
    if (!user?.roles.includes('ROLE_ADMIN')) return null;

    return (
        <main className="p-6">
            <h1 className="text-3xl font-bold mb-4">Welcome Admin {user.username}</h1>

            <section className="grid grid-cols-2 gap-4">
                <AdminCard title="Manage Users" description="Add, remove or update users" />
                <AdminCard title="Manage Schools" description="Configure school details" />
                <AdminCard title="Reports" description="Download insights and reports" />
                <AdminCard title="Settings" description="Configure tenant preferences" />
            </section>
        </main>
    )
}

function AdminCard({title, description}: {title: string, description: string}){
    return (
        <div className="bg-white p-6 shadow rounded-lg border border-gray-200">
            <h2 className="text-xl font-semibold mb-2">{title}</h2>
            <p className="text-gray-600">{description}</p>
        </div>
    )
}