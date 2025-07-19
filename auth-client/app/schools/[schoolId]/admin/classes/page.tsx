'use client';

import EditClassForm from "@/components/admin/classes/EditClassInfoForm";
import StudentsList from "@/components/students/StudentsList";
import ClassTable from "@/components/ui/ClassTable";
import { Tabs } from "@/components/ui/Tabs";
import { useSchool } from "@/context/SchoolContext";
import { getAllClasses } from "@/lib/api/classes";
import { getStudentsOfClass } from "@/lib/api/student";
import { ClassItem } from "@/types";
import { StudentItem } from "@/types/student";
import { useEffect, useState } from "react"; // New form replacing modal

export default function AdminClassesPage() {
    const { schoolId } = useSchool();
    const [classes, setClasses] = useState<ClassItem[]>([]);
    const [selectedClass, setSelectedClass] = useState<ClassItem | null>(null);
    const [students, setStudents] = useState<StudentItem[]>([]);
    const [activeTab, setActiveTab] = useState("edit");
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchClasses = async () => {
            try {
                const res = await getAllClasses(schoolId as string);
                setClasses(res);
            } catch (err) {
                console.error("Failed to fetch classes", err);
            } finally {
                setLoading(false);
            }
        };

        fetchClasses();
    }, [schoolId]);

    const handleSelectClass = async (cls: ClassItem) => {
        setSelectedClass(cls);
        setActiveTab("edit");

        try {
            const res = await getStudentsOfClass(cls.schoolId, cls.id);
            setStudents(res);
        } catch (err) {
            console.error("Failed to fetch students", err);
        }
    };

    const handleClosePanel = () => {
        setSelectedClass(null);
        setStudents([]);
    };

    const handleSaveClass = (updated: ClassItem) => {
        setSelectedClass(updated);
        setClasses(prev => prev.map(c => c.id === updated.id ? updated : c));
    };

    const handleAddStudent = () => {
        // TODO: open AddStudent drawer/modal
    };

    const handleEditStudent = (student: StudentItem) => {
        // TODO: open EditStudent drawer/modal
    };

    const handleDeleteStudent = (student: StudentItem) => {
        // TODO: call DELETE API, then refresh list
    };

    const handleViewStudent = (student: StudentItem) => {
        // TODO: show student profile or navigate to view page
    };

    return (
        <div className="relative p-4">
            <h1 className="text-2xl font-semibold mb-4">Classes</h1>
            <ClassTable
                classes={classes}
                loading={loading}
                onSelectClass={handleSelectClass}
            />

            {selectedClass && (
                <div className="fixed top-0 right-0 h-full w-full sm:w-[500px] bg-white shadow-lg z-50 p-4 border-l transition-transform duration-300 ease-in-out">
                    <div className="flex justify-between items-center mb-2">
                        <h2 className="text-lg font-semibold text-[var(--color-primary)]">
                            {selectedClass.name}
                        </h2>
                        <button
                            onClick={handleClosePanel}
                            className="text-gray-500 hover:text-red-500 text-xl"
                            aria-label="Close panel"
                        >
                            ×
                        </button>
                    </div>

                    <Tabs
                        tabs={[
                            { label: "Edit Class", value: "edit" },
                            { label: "Students", value: "students" },
                        ]}
                        defaultValue="edit"
                        onChange={setActiveTab}
                    />

                    <div className="mt-4">
                        {activeTab === "edit" && (
                            <EditClassForm
                                classItem={selectedClass}
                                onSave={handleSaveClass}
                            />
                        )}

                        {activeTab === "students" && (
                            <StudentsList
                                students={students}
                                onAddStudent={handleAddStudent}
                                onEditStudent={handleEditStudent}
                                onDeleteStudent={handleDeleteStudent}
                                onViewStudent={handleViewStudent}
                            />
                        )}
                    </div>
                </div>
            )}
        </div>
    );
}
