'use client';

type Course = {
    id: string;
    name: string;
    teacher: string;
    grade: string;
};

const dummyCourses: Course[] = [
    { id: '1', name: 'Mathematics', teacher: 'Ramesh Bhandari', grade: 'Grade 6' },
    { id: '2', name: 'Science', teacher: 'Sita Koirala', grade: 'Grade 7' },
    { id: '3', name: 'English', teacher: 'Suraj Rana', grade: 'Grade 8' },
];

export default function CoursesPage() {
    return (
        <div className="space-y-6">
            <div className="flex items-center justify-between">
                <h1 className="text-2xl font-bold">📚 Courses</h1>
                <button className="px-4 py-2 bg-blue-500 text-white text-sm rounded-md hover:bg-blue-600 transition">
                    + Add Course
                </button>
            </div>
            <div className="bg-white rounded-xl shadow-sm overflow-x-auto">
                <table className="min-w-full text-sm text-left">
                    <thead className="bg-gray-50 border-b text-gray-600">
                        <tr>
                            <th className="py-3 px-4">Course Name</th>
                            <th className="py-3 px-4">Teacher</th>
                            <th className="py-3 px-4">Grade</th>
                            <th className="py-3 px-4">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {dummyCourses.map((course) => (
                            <tr key={course.id} className="border-b hover:bg-gray-50 transition">
                                <td className="py-3 px-4 font-medium text-gray-800">{course.name}</td>
                                <td className="py-3 px-4">{course.teacher}</td>
                                <td className="py-3 px-4">{course.grade}</td>
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