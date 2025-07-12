import { SchoolInfo } from "@/types";

interface Props {
    schools: SchoolInfo[];
    onSelect: (school: SchoolInfo) => void;
}

export default function SchoolSelector({ schools, onSelect }: Props) {
    return (
        <div>
            <h2 className="text-lg font-semibold mb-2">Select your school</h2>
            <div className="grid gap-2">
                {schools.map((school) => (
                    <button
                        key={school.schoolId}
                        onClick={() => onSelect(school)}
                        className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition"
                    >
                        {school.schoolName}
                    </button>
                ))}
            </div>
        </div>
    );
}
