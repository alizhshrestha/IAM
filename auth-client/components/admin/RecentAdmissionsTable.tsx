'use client';

type Admission = {
  name: string;
  grade: string;
  joinedOn: string;
  status: 'active' | 'pending' | 'rejected';
};

const recentAdmissions: Admission[] = [
  { name: 'Anish Pandey', grade: '6', joinedOn: '2025-06-15', status: 'active' },
  { name: 'Neha Gurung', grade: '8', joinedOn: '2025-06-14', status: 'pending' },
  { name: 'Manish Karki', grade: '5', joinedOn: '2025-06-10', status: 'rejected' },
  { name: 'Sita Lama', grade: '7', joinedOn: '2025-06-09', status: 'active' },
];

export default function RecentAdmissionsTable() {
  return (
    <div className="bg-white rounded-xl shadow-sm p-6">
      <h2 className="text-lg font-semibold mb-4">📥 Recent Admissions</h2>
      <div className="overflow-x-auto">
        <table className="min-w-full text-sm text-left">
          <thead className="text-gray-600 border-b">
            <tr>
              <th className="py-2 px-4">Name</th>
              <th className="py-2 px-4">Grade</th>
              <th className="py-2 px-4">Joined On</th>
              <th className="py-2 px-4">Status</th>
            </tr>
          </thead>
          <tbody>
            {recentAdmissions.map((student, index) => (
              <tr key={index} className="border-b last:border-none hover:bg-gray-50 transition">
                <td className="py-2 px-4 font-medium text-gray-800">{student.name}</td>
                <td className="py-2 px-4">{student.grade}</td>
                <td className="py-2 px-4">{student.joinedOn}</td>
                <td className="py-2 px-4">
                  <StatusPill status={student.status} />
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

function StatusPill({ status }: { status: Admission['status'] }) {
  const colorMap = {
    active: 'bg-green-100 text-green-800',
    pending: 'bg-yellow-100 text-yellow-800',
    rejected: 'bg-red-100 text-red-800',
  };
  const labelMap = {
    active: 'Active',
    pending: 'Pending',
    rejected: 'Rejected',
  };

  return (
    <span className={`px-2 py-1 rounded-full text-xs font-medium ${colorMap[status]}`}>
      {labelMap[status]}
    </span>
  );
}
