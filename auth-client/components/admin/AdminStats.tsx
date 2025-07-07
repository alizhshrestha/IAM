'use client';

import { AlertTriangleIcon, BookIcon, CalendarIcon, DollarSignIcon, GraduationCapIcon, UsersIcon } from "lucide-react";

type StatCardProps = {
    title: string;
    value: string;
    icon?: React.ReactNode;
    bgColor: string;
}

function StatCard({ title, value, icon, bgColor }: StatCardProps) {
    return (
        <div className={`rounded-xl p-4 flex items-center gap-4 shadow-sm border border-gray-200`} style={{ backgroundColor: bgColor }}>
            <div className="bg-white/80 p-3 rounded-full shadow-md">
                {icon}
            </div>
            <div>
                <p className="text-sm text-gray-600">{title}</p>
                <h2 className="text-2xl font-bold text-gray-900">{value}</h2>
            </div>
        </div>
    )
}

export default function AdminStats() {
  return (
    <section className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-5 gap-4">
      <StatCard
        title="Total Students"
        value="1,200"
        icon={<UsersIcon className="w-6 h-6 text-violet-500" />}
        bgColor="#f3e8ff" // pastel violet
      />
      <StatCard
        title="Total Teachers"
        value="45"
        icon={<GraduationCapIcon className="w-6 h-6 text-sky-500" />}
        bgColor="#e0f2fe" // pastel blue
      />
      <StatCard
        title="Today’s Attendance"
        value="1130 / 1200"
        icon={<CalendarIcon className="w-6 h-6 text-orange-500" />}
        bgColor="#fff7ed" // pastel orange
      />
      <StatCard
        title="Fees Collected"
        value="Rs. 1.5M"
        icon={<DollarSignIcon className="w-6 h-6 text-emerald-500" />}
        bgColor="#ecfdf5" // pastel green
      />
      <StatCard
        title="Performance Alerts"
        value="3 Alerts"
        icon={<AlertTriangleIcon className="w-6 h-6 text-rose-500" />}
        bgColor="#ffe4e6" // pastel red
      />
    </section>
  );
}