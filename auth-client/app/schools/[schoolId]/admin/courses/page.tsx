'use client';

import CourseCard from "@/components/course/CourseCard";

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

            <div className="space-y-4">
                {dummyCourses.map((course) => (
                    <CourseCard
                        key={course.id}
                        id={course.id}
                        name={course.name}
                        grade={course.grade}
                        teacher={course.teacher}
                    />
                ))}
            </div>
        </div>
    );
}
