'use client';

import AttendanceCard, { AttendanceStatus } from "@/components/attendance/AttendanceCard";

const dummyAttendance = [
    { id: '1', student: 'Anita Sharma', grade: 'Grade 6', date: '2025-07-07', status: 'Present' as AttendanceStatus },
    { id: '2', student: 'Ramesh Pandey', grade: 'Grade 6', date: '2025-07-07', status: 'Absent' as AttendanceStatus },
    { id: '3', student: 'Sita Lama', grade: 'Grade 7', date: '2025-07-07', status: 'Present' as AttendanceStatus },
];

export default function AttendancePage() {
    return (
        <div className="space-y-6">
            <div className="flex items-center justify-between">
                <h1 className="text-2xl font-bold">🗓️ Attendance</h1>
                <button className="px-4 py-2 bg-green-500 text-white text-sm rounded-md hover:bg-green-600 transition">
                    Mark Attendance
                </button>
            </div>

            <div className="space-y-4">
                {dummyAttendance.map(entry => (
                    <AttendanceCard
                        key={entry.id}
                        student={entry.student}
                        grade={entry.grade}
                        date={entry.date}
                        status= {entry.status}
                    />
                ))}
            </div>
        </div>
    )
}