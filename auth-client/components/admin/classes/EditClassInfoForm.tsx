import { ClassItem } from "@/types";
import { useState, useEffect } from "react";

export default function EditClassForm({
    classItem,
    onSave,
}: {
    classItem: ClassItem;
    onSave: (updated: ClassItem) => void;
}) {
    const [name, setName] = useState(classItem.name);
    const [section, setSection] = useState(classItem.section);
    const [grade, setGrade] = useState(classItem.grade);

    useEffect(() => {
        setName(classItem.name);
        setSection(classItem.section);
        setGrade(classItem.grade);
    }, [classItem]);

    const handleSubmit = () => {
        onSave({ ...classItem, name, section, grade });
    };

    return (
        <div className="space-y-4">
            <h2 className="text-lg font-semibold text-[var(--color-primary)]">
                Edit Class Info
            </h2>

            <div>
                <label className="text-sm text-gray-600 block">Name</label>
                <input
                    className="w-full border rounded px-3 py-2"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                />
            </div>

            <div>
                <label className="text-sm text-gray-600 block">Section</label>
                <input
                    className="w-full border rounded px-3 py-2"
                    value={section}
                    onChange={(e) => setSection(e.target.value)}
                />
            </div>

            <div>
                <label className="text-sm text-gray-600 block">Grade</label>
                <input
                    className="w-full border rounded px-3 py-2"
                    value={grade}
                    onChange={(e) => setGrade(e.target.value)}
                />
            </div>

            <div className="text-right">
                <button
                    onClick={handleSubmit}
                    className="bg-[var(--color-primary)] text-white px-4 py-1 rounded-lg"
                >
                    Save Changes
                </button>
            </div>
        </div>
    );
}
