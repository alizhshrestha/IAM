'use client';

import { useRouter } from "next/navigation";
import { useState } from "react";

export type School = {
    id: string;
    name: string;
    logoUrl: string;
    address: string;
    academicYear: string;
    tenantId: string;
    role: string;
    userId: string;
};

export const getRolePath = (role: string) => {
    switch (role) {
        case "ROLE_ADMIN":
            return "admin";
        case "ROLE_TEACHER":
            return "teacher";
        case "ROLE_STUDENT":
            return "student";
        default:
            return "unauthorized";
    }
}

export default function SchoolSelector({ schools }: { schools: School[] }) {
    const router = useRouter();
    const [loading, setLoading] = useState(false);

    const handleSelect = (school: School) => {
        localStorage.setItem("selected_school_id", school.id);
        localStorage.setItem("selected_role", school.role);
        localStorage.setItem("user_id", school.userId);
        router.push(`/schools/${school.id}/${getRolePath(school.role)}`);
    };

    return (
        <div className="p-8">
            <h2 className="text-xl font-semibold mb-4">Select Your School</h2>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                {schools.map((school) => (
                    <button
                        key={school.id}
                        onClick={() => handleSelect(school)}
                        className="p-4 border rounded-xl hover:shadow-md text-left transition bg-white"
                    >
                        <div className="flex items-center justify-between">
                            <h3 className="text-lg font-bold">{school.name}</h3>
                            <span className="text-sm bg-gray-100 px-2 py-1 rounded">
                                {getRolePath(school.role)} dashboard
                            </span>
                        </div>
                        <p className="text-sm text-gray-500 mt-2">{school.address}</p>
                        <p className="text-sm text-gray-400">{school.academicYear}</p>
                    </button>
                ))
                }
            </div>
        </div>
    )
}