'use client'

import { Card, CardContent } from "@/components/ui/Card";
import { useSchool } from "@/context/SchoolContext";
import { ClassData } from "@/types";
import { Loader2, Search } from "lucide-react";
import Link from "next/link";
import { useEffect, useState } from "react";

export default function TeacherClassesPage() {
    const [classes, setClasses] = useState<ClassData[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const { schoolId, userId, role, tenantId } = useSchool();

    useEffect(() => {
        if (!schoolId || !userId) return;

        const token = sessionStorage.getItem('access_token');
        console.log("Fetching classes for:", { schoolId, userId, role, tenantId });

        fetch(`http://localhost:8081/api/school/${schoolId}/teacher/${userId}/classes`, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        })
            .then((res) => {
                return res.json();
            })
            .then((data) => {
                setClasses(data);
                setLoading(false);
            })
            .catch((err) => {
                console.error('Failed to fetch classes: ', err);
                setLoading(false);
            })
    }, [schoolId, userId]);

    return (
        <div className="p-6 space-y-6 bg-[var(--color-bg) min-h-screen">
            <div className="flex justify-between items-center">
                <h1 className="text-2xl font-bold text-[var(--color-text-main)]">My Classes</h1>
                <button className="bg-[var(--color-primary)] hover:bg-purple-700 text-white px-4 py-2 rounded-md text-sm shadow">
                    Add Class
                </button>
            </div>

            <div className="relative">
                <input
                    type="text"
                    placeholder="Search classes"
                    className="w-full pl-10 pr-4 py-2 rounded-md border border-[var(--color-border)] 
                    bg-white text-sm text-[var(--color-text-main)] focus:outline-none focus:ring-2 focus:ring-[var(--color-primary)]"
                />
                <Search className="absolute left-3 top-2.5 w-4 h-4 text-[var(--color-text-muted)]" />
            </div>

            {loading ? (
                <div className="flex justify-center py-10">
                    <Loader2 className="w-6 h-6 animate-spin text-[var(--color-text-muted)]" />
                </div>
            ) : (
                <div className="overflow-x-auto bg-[var(--color-card)] rounded-lg shadow border border-[var(--color-border)]">
                    <table className="min-w-full text-sm text-[var(--color-text-main)]">
                        <thead className="bg-[var(--color-primary-light)] text-[var(--color-text-muted)]">
                            <tr>
                                <th className="px-6 py-3 font-medium">Class Name</th>
                                <th className="px-6 py-3 font-medium">Subject</th>
                                <th className="px-6 py-3 font-medium">Grade Level</th>
                                <th className="px-6 py-3 font-medium">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {classes.length > 0 ? (
                                classes.map((cls) => (
                                    <tr key={cls.subjectId} className="border-t border-[var(--color-border)] hover:bg-[var(--color-bg)] transition text-center">
                                        <td className="px-6 py-4 font-medium">Grade {cls.grade} - {cls.section}</td>
                                        <td className="px-6 py-4 text-[var(--color-accent-1)]">{cls.subject}</td>
                                        <td className="px-6 py-4">{cls.grade}th Grade</td>
                                        <td className="px-6 py-4 space-x-2 text-sm">
                                            <Link href="#" className="text-[var(--color-accent-2)] hover:underline">View</Link>
                                            <Link href="#" className="text-[var(--color-accent-3)] hover:underline">Edit</Link>
                                            <Link href="#" className="text-[var(--color-primary)] hover:underline">Manage</Link>
                                        </td>
                                    </tr>
                                ))
                            ) : (
                                <tr>
                                    <td colSpan={4} className="px-6 py-10 text-center text-[var(--color-text-muted)]">
                                        No classes assigned yet.
                                    </td>
                                </tr>
                            )}
                        </tbody>

                    </table>
                </div>
            )}

        </div>
    )
}