'use client';

export type Fee = {
    id: string;
    student: string;
    amount: number;
    date: string;
    status: 'Paid' | 'Pending'
};

export default function FeeCard({
    id,
    student,
    amount,
    date,
    status
}: Fee){
    return (
        <div
            key={id}
            className="card"
          >
            <div>
              <p className="font-semibold text-gray-800">{student}</p>
              <p className="text-sm text-gray-500">{date}</p>
            </div>
            <div className="flex items-center gap-4 mt-2 sm:mt-0">
              <span className="text-gray-800 font-semibold">Rs. {amount}</span>
              <span
                className={`text-xs font-medium px-2 py-1 rounded-full ${
                  status === 'Paid'
                    ? 'bg-green-100 text-green-700'
                    : 'bg-yellow-100 text-yellow-700'
                }`}
              >
                {status}
              </span>
            </div>
          </div>
    )
}