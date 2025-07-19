import { StudentItem } from "@/types/student";
import StudentCard from "./StudentCard";

export default function StudentsList({
    students,
    onAddStudent,
    onEditStudent,
    onDeleteStudent,
    onViewStudent,
}: {
    students: StudentItem[];
    onAddStudent: () => void;
    onEditStudent: (student: StudentItem) => void;
    onDeleteStudent: (student: StudentItem) => void;
    onViewStudent: (student: StudentItem) => void;
}){
    return (
        <div className="space-y-4">
            <div className="flex justify-between items-center mb-4">
                <h2 className="text-lg font-semibold text-[var(--color-primary)]">Students</h2>
                <button
                    onClick={onAddStudent}
                    className="bg-[var(--color-primary)] text-white px-4 py-1 rounded-lg"
                >
                    + Add Student
                </button>
            </div>

            {students.length === 0 ? (
                <p className="text-gray-500">No students found.</p>
            ):(
                <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-4">
                    {students.map((student) => (
                        <StudentCard
                            key={student.id}
                            student={student}
                            onEdit={() => onEditStudent(student)}
                            onDelete={() => onDeleteStudent(student)}
                            onView={() => onViewStudent(student)}
                        />
                    ))}
                </div>
            )}
        </div>
    )
}