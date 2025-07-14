'use client';

import { useEffect, useState } from "react";
import { Loader2 } from "lucide-react";
import { useSchool } from "@/context/SchoolContext";

interface TeacherScheduleResponseDto {
    timeSlots: string[];
    days: string[];
    schedule: {
        [timeSlot: string]: {
            [dayOfWeek: string]: string;
        };
    };
}

export default function TeacherSchedulePage() {
    const { schoolId, userId } = useSchool();
    const [data, setData] = useState<TeacherScheduleResponseDto | null>(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const token = sessionStorage.getItem("access_token");

        fetch(`http://localhost:8081/api/school/${schoolId}/teacher/${userId}/schedule`, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        })
            .then((res) => res.json())
            .then(setData)
            .catch((err) => console.error("Failed to fetch schedule", err))
            .finally(() => setLoading(false));
    }, [schoolId, userId]);

    console.log("Schedule Data:", data);

    if (loading) {
        return (
            <div className="flex items-center justify-center h-full">
                <Loader2 className="w-6 h-6 animate-spin text-gray-500" />
            </div>
        );
    }

    if (!data) {
        return <div className="p-4 text-gray-600">No schedule data available.</div>;
    }

    const { days, timeSlots, schedule } = data;

    return (
        <div className="p-6">
            <h2 className="text-xl font-semibold mb-4 text-[--color-text-main]">Weekly Schedule</h2>

            <div className="overflow-x-auto">
                <table className="w-full border-separate border-spacing-2">
                    <thead className="bg-[--color-primary-light] text-[--color-text-main]">
                        <tr>
                            <th className="text-left p-2 text-[--color-text-main]">Time</th>
                            {days.map((day) => (
                                <th key={day} className="text-left p-2 text-[--color-text-main]">{day.substring(0,3)}</th>
                            ))}
                        </tr>
                    </thead>
                    <tbody>
                        {timeSlots.map((slot) => (
                            <tr key={slot} className="p-2 text-sm font-medium text-[--color-text-main]">
                                <td className="p-2 text-sm font-medium text-[--color-text-main]">{slot}</td>
                                {
                                    days.map((day) => {
                                        const normalizedDay = day.trim().toUpperCase();
                                        const entry = schedule[slot]?.[normalizedDay];
                                        if (entry) {
                                            const [className, subject] = entry.split(',');
                                            return (
                                                <td key={day} className="p-2 align-top">
                                                    <div className="bg-[--color-primary-light] rounded-xl shadow p-3">
                                                        <div className="font-semibold text-[--color-text-main]">
                                                            {subject}
                                                        </div>
                                                        <div className="text-sm text-[--color-text-muted]">
                                                            {className}
                                                        </div>
                                                    </div>
                                                </td>
                                            )
                                        } else {
                                            return (
                                                <td key={day} className="p-2 align-top">
                                                    <div className="rounded-xl p-3 text-sm text-[--color-text-muted] bg-[--color-border]">
                                                        -
                                                    </div>
                                                </td>
                                            );
                                        }
                                    })}
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}
