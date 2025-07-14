'use client';

import { useSchool } from "@/context/SchoolContext";
import { AssignmentData } from "@/types";
import { Loader2 } from "lucide-react";
import { useEffect, useState } from "react";

export default function TeacherAssignmentsSidebar() {
    const [assignments, setAssignments] = useState<AssignmentData[]>([]);
    const [loading, setLoading] = useState(true);
    const { schoolId, userId } = useSchool();

    useEffect(() => {
        const fetchAssignments = async () => {
            try {
                const token = sessionStorage.getItem("access_token");
                const res = await fetch(`http://localhost:8081/api/school/${schoolId}/teacher/${userId}/assignments`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });

                const data = await res.json();
                setAssignments(data);
            } catch (err) {
                console.error("Failed to fetch assignments", err);
            } finally {
                setLoading(false);
            }
        };

        fetchAssignments();
    }, [schoolId, userId]);

    return (
        <div className="p-4 space-y-4">
            <h2 className="text-xl font-semibold text-[--color-text-main]">Upcoming Assignments</h2>
            {loading ? (
                <div className="flex justify-center py-8">
                    <Loader2 className="w-6 h-6 animate-spin text-[--color-text-muted]" />
                </div>
            ) : assignments.length === 0 ? (
                <p className="text-sm text-[--color-text-muted]">No assignments yet.</p>
            ) : (
                <div className="space-y-3">
                    {assignments.map((a) => (
                        <div key={a.id} className="rounded-2xl bg-[--color-primary-light] shadow-sm p-4">
                            <div className="text-[--color-primary] font-medium text-sm mb-1">{a.subject}</div>
                            <div className="text-[--color-text-main] font-semibold text-base">{a.title}</div>
                            <div className="text-[--color-text-muted] text-sm">{a.className}</div>
                            <div className="text-[--color-text-muted] text-xs mt-1">
                                Due: {new Date(a.dueDate).toLocaleDateString(undefined, {
                                    weekday: "short",
                                    month: "short",
                                    day: "numeric",
                                    hour: "2-digit",
                                    minute: "2-digit",
                                })}
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    )
}