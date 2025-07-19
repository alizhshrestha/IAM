import { StudentItem } from "@/types/student";

export default function StudentCard({
    student,
    onEdit,
    onDelete,
    onView,
}: {
    student: StudentItem;
    onEdit: () => void;
    onDelete: () => void;
    onView: () => void;
}) {
    return (
        <div className="bg-white p-4 rounded-xl shadow-sm border hover:shadow-md transition">
            <div className="flex justify-between items-center">
                <div>
                    <h3 className="font-semibold text-[var(--color-primary)]">
                        {student.name}
                    </h3>
                    <p className="text-sm text-gray-500">{student.enrollmentNo}</p>
                </div>
                <div className="flex gap-2">
                    <button onClick={onView} className="text-blue-600 text-sm">View</button>
                    <button onClick={onEdit} className="text-yellow-600 text-sm">Edit</button>
                    <button onClick={onDelete} className="text-red-600 text-sm">Delete</button>
                </div>
            </div>
        </div>
    )
}