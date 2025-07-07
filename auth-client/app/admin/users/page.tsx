'use client';

type User = {
    id: string;
    name: string;
    email: string;
    role: 'Teacher' | 'Student' | 'Admin';
};

const dummyUsers: User[] = [
    { id: '1', name: 'Ramesh Bhandari', email: 'ramesh@school.com', role: 'Teacher' },
    { id: '2', name: 'Anita Sharma', email: 'anita@school.com', role: 'Student' },
    { id: '3', name: 'Suraj Rana', email: 'suraj@school.com', role: 'Admin' },
    { id: '4', name: 'Sita Koirala', email: 'sita@school.com', role: 'Teacher' },
];

export default function UsersPage() {
    return (
        <div className="space-y-6">
            <div className="flex items-center justify-between">
                <h1 className="text-2xl font-bold">👤 User Management</h1>
                <button className="px-4 py-2 bg-blue-500 text-white text-sm rounded-md hover:bg-blue-600 transition">
                    + Add User
                </button>
            </div>

            <div className="bg-white rounded-xl shadow-sm overflow-x-auto">
                <table className="min-w-full text-sm text-left">
                    <thead className="bg-gray-50 border-b text-gray-600">
                        <tr>
                            <th className="py-3 px-4">Name</th>
                            <th className="py-3 px-4">Email</th>
                            <th className="py-3 px-4">Role</th>
                            <th className="py-3 px-4">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {dummyUsers.map((user) => (
                            <tr key={user.id} className="border-b hover:bg-gray-50 transition">
                                <td className="py-3 px-4 font-medium text-gray-800">{user.name}</td>
                                <td className="py-3 px-4">{user.email}</td>
                                <td className="py-3 px-4">
                                    <RoleBadge role={user.role} />
                                </td>
                                <td className="py-3 px-4 space-x-2">
                                    <button className="text-blue-600 hover:underline text-sm">Edit</button>
                                    <button className="text-red-600 hover:underline text-sm">Delete</button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    )
}

function RoleBadge({ role }: { role: User['role'] }) {
    const colorMap = {
        Teacher: 'bg-blue-100 text-blue-800',
        Student: 'bg-green-100 text-green-800',
        Admin: 'bg-purple-100 text-purple-800',
    }

    return (
        <span className={`px-2 py-1 rounded-full text-xs font-medium ${colorMap[role]}`}>
            {role}
        </span>
    );
}