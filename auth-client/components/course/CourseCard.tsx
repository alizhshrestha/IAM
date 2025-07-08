'use client';

type CourseCardProps = {
    id: string;
    name: string;
    grade: string;
    teacher: string;
}

export default function CourseCard({
    id,
    name,
    grade,
    teacher
}: CourseCardProps) {
    return (
        <div
            key={id}
            className="card"
        >
            <div>
                <p className="font-semibold text-gray-800">{name}</p>
                <p className="text-sm text-gray-500">
                    Grade: {grade} • Teacher: {teacher}
                </p>
            </div>
            <div className="mt-2 sm:mt-0 flex gap-2 text-sm">
                <button className="text-blue-600 hover:underline">Edit</button>
                <button className="text-red-600 hover:underline">Delete</button>
            </div>
        </div>
    )
}

