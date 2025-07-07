'use client';

import { Bar, BarChart, CartesianGrid, Line, LineChart, ResponsiveContainer, Tooltip, XAxis, YAxis } from "recharts";

const attendanceData = [
    { month: 'Jan', present: 900 },
    { month: 'Feb', present: 1000 },
    { month: 'Mar', present: 950 },
    { month: 'Apr', present: 1100 },
    { month: 'May', present: 1130 },
    { month: 'Jun', present: 1075 },
];

const feeData = [
    { month: 'Jan', fees: 200000 },
    { month: 'Feb', fees: 240000 },
    { month: 'Mar', fees: 180000 },
    { month: 'Apr', fees: 290000 },
    { month: 'May', fees: 310000 },
    { month: 'Jun', fees: 275000 },
];

export default function AdminCharts() {
    return (
        <section className="grid grid-cols-1 lg:grid-cols-2 gap-6 mt-6">
            {/* Attendance Line Chart */}
            <div className="bg-white rounded-xl p-4 shadow-sm">
                <h2 className="font-semibold text-lg mb-2">📅 Attendance Trend</h2>
                <ResponsiveContainer width="100%" height={250}>
                    <LineChart data={attendanceData}>
                        <CartesianGrid strokeDasharray="3 3" />
                        <XAxis dataKey="month" />
                        <YAxis />
                        <Tooltip />
                        <Line type="monotone" dataKey="present" stroke="#7c3aed" strokeWidth={2} />
                    </LineChart>
                </ResponsiveContainer>
            </div>

            {/* Fees Collected Bar Chart */}
            <div className="bg-white rounded-xl p-4 shadow-sm">
                <h2 className="font-semibold text-lg mb-2">💰 Monthly Fees Collected</h2>
                <ResponsiveContainer width="100%" height={250}>
                    <BarChart data={feeData}>
                        <CartesianGrid strokeDasharray="3 3" />
                        <XAxis dataKey="month" />
                        <YAxis />
                        <Tooltip />
                        <Bar dataKey="fees" fill="#10b981" radius={[6, 6, 0, 0]} />
                    </BarChart>
                </ResponsiveContainer>
            </div>
        </section>
    )
}