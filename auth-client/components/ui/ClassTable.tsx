import { ClassItem, ClassTableProps } from "@/types";
import ClassSkeleton from "./ClassSkeleton";
import ClassCard from "./ClassCard";

const ClassTable: React.FC<ClassTableProps> = ({ classes, onSelectClass, loading = false }:
    {
        classes: ClassItem[];
        onSelectClass: (classItem: ClassItem) => void;
        loading?: boolean;

    }) => {
    return loading ? (
        <div className="grid md:grid-cols-3 gap-4">
            {Array.from({ length: 6 }).map((_, i) => (
                <ClassSkeleton key={i} />
            ))}
        </div>
    ) : (
        <div className="grid md:grid-cols-3 gap-4">
            {classes.map((cls) => (
                <ClassCard
                    key={cls.id}
                    className={cls.name}
                    section={cls.section}
                    onClick={() => onSelectClass(cls)}
                />
            ))}
        </div>
    )
}

export default ClassTable;