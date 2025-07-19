import { GraduationCapIcon } from "lucide-react";

interface ClassCardProps {
    className: string;
    section: string;
    onClick: () => void;
}

export default function ClassCard({ className, section, onClick }: ClassCardProps) {
    return (
        <div
            onClick={onClick}
            className="cursor-pointer border rounded-2xl p-4 shadow-md bg-white hover:shadow-lg transition-all"
        >
            <div className="flex items-center gap-3 text-[var(--color-primary)]">
                <GraduationCapIcon className="w-5 h-5" />
                <h3 className="font-semibold text-lg">{className}</h3>
            </div>
            <p className="text-gray-500 text-sm mt-1">Section: {section}</p>
        </div>
    );
}