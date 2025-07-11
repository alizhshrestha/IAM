'use client'

import { Card, CardContent } from "@/components/ui/Card";
import { ClassData } from "@/types";
import { Loader2 } from "lucide-react";
import { useEffect, useState } from "react";

export default function TeacherClassesPage() {
    const [classes, setClasses] = useState<ClassData[]>([]);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        const token = sessionStorage.getItem('access_token');
        fetch('http://localhost:8081/api/teacher/classes', {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        })
            .then((res) => res.json())
            .then((data) => {
                setClasses(data);
                setLoading(false);
            })
            .catch((err) => {
                console.error('Failed to fetch classes: ', err);
                setLoading(false);
            })
    }, []);

    if (loading) {
        return (
            <div className="flex items-center justify-center h-full">
                <Loader2 className="w-6 h-6 animate-spin text-gray-600" />
            </div>
        )
    }

    return (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 p-4">
            {classes.map((cls) => (
                <Card key={cls.id} className="rounded-2xl">
                    <CardContent className="p-4 space-y-2">
                        <h3 className="text-xl font-semibold text-gray-800">{cls.name}</h3>
                        <p className="text-gray-600">Grade: {cls.grade}</p>
                        <p className="text-gray-600">Section: {cls.section}</p>
                        <p className="text-gray-600">Subject: {cls.subject}</p>
                    </CardContent>
                </Card>
            ))}
            {classes.length === 0 && (
                <div className="col-span-full text-center text-gray-500">
                    No classes assigned.
                </div>
            )}
        </div>
    )
}