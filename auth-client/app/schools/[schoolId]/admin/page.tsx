'use client'

import AdminCharts from "@/components/admin/AdminCharts";
import AdminStats from "@/components/admin/AdminStats";
import RecentAdmissionsTable from "@/components/admin/RecentAdmissionsTable";
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
    <main className="p-6 space-y-6">
      <h1 className="text-3xl font-bold">Admin Dashboard</h1>

      <AdminStats />
      <AdminCharts/>
      <RecentAdmissionsTable/>

      {/* Next: Charts or Reports Section */}
    </main>
  );
}
