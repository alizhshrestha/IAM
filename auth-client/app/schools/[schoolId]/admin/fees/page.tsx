'use client';

import FeeCard, { Fee } from "@/components/fee/FeeCard";

const dummyFees: Fee[] = [
    { id: '1', student: 'Anita Sharma', amount: 3500, date: '2025-07-01', status: 'Paid' },
    { id: '2', student: 'Ramesh Pandey', amount: 3500, date: '2025-07-03', status: 'Pending' },
    { id: '3', student: 'Sita Lama', amount: 4000, date: '2025-07-04', status: 'Paid' },
];

export default function FeesPage() {
    return (
        <div className="space-y-6">
            <div className="flex items-center justify-between">
                <h1 className="text-2xl font-bold">💸 Fees</h1>
                <button className="px-4 py-2 bg-blue-600 text-white rounded-md text-sm hover:bg-blue-700 transition">
                    + Add Payment
                </button>
            </div>
            <div className="space-y-4">
                {
                    dummyFees.map(fee => (
                        <FeeCard
                            key={fee.id}
                            id={fee.id}
                            student={fee.student}
                            amount={fee.amount}
                            date={fee.date}
                            status={fee.status}
                        />
                    ))
                }
            </div>
        </div>
    )
}