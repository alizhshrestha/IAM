'use client';

export type AttendanceStatus = 'Present' | 'Absent';

type AttendanceCardProps = {
    student: string;
    grade: string;
    date: string;
    status: AttendanceStatus;
};

export default function AttendanceCard({
    student,
    grade,
    date,
    status
}: AttendanceCardProps) {
    const statusColor = status === 'Present'
        ? 'bg-green-100 text-green-800'
        : 'bg-red-100 text-red-800';

    return (
        <div className="card">
            <div>
                <p className="font-semibold text-gray-800">{student}</p>
                <p className="text-sm text-gray-500">{grade} • {date}</p>
            </div>
            <span className={`px-2 py-1 text-xs rounded-full font-medium mt-2 sm:mt-0 ${statusColor}`}>
                {status}
            </span>
        </div>
    )
}