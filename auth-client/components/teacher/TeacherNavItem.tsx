'use client';

import { cn } from "@/utils/cn";
import { LucideIcon } from "lucide-react";
import Link from "next/link";
import { usePathname } from "next/navigation";

type NavItemProps = {
    href: string;
    label: string;
    icon: LucideIcon;
};

export default function TeacherNavItem({ href, label, icon: Icon} : NavItemProps){
    const pathname = usePathname();
    const isActive = pathname === href;

    return (
        <Link 
        href={href}
        className={cn(
            'flex items-center gap-3 px-4 py-2 rounded-md hover:bg-blue-100 transition-colors',
            isActive && 'bg-blue-200 font-semibold text-blue-900'
        )}
        >
            <Icon className="w-5 h-5" />
            <span>{label}</span>
        </Link>
    );
}