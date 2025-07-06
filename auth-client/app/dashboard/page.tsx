'use client'

import AdminDashboard from "@/components/AdminDashboard";
import StudentDashboard from "@/components/StudentDashboard";
import TeacherDashboard from "@/components/TeacherDashboard";
import { useUser } from "@/context/UserContext"

export default function DashboardPage(){
    const user = useUser();

    if(!user) return <div>Loading...</div>;

    console.log(`user roles is ${user.roles}`)
    if(user.roles.includes("ROLE_ADMIN")) return <AdminDashboard />;
    if(user.roles.includes("ROLE_TEACHER")) return <TeacherDashboard />;
    if(user.roles.includes("ROLE_STUDENT")) return <StudentDashboard />;

    return <div>No dashboard available for your role.</div>
}