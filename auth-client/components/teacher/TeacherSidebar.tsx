'use client'

import { BookOpen, ClipboardList, Home, User } from "lucide-react";
import TeacherNavItem from "./TeacherNavItem";

const navLinks = [
    { href: '/teacher', label: 'Dashboard', icon: Home },
    { href: '/teacher/classes', label: 'My Classes', icon: BookOpen },
    { href: '/teacher/assignments', label: 'Assignments', icon: ClipboardList },
    { href: '/teacher/profile', label: 'Profile', icon: User },
];

export default function TeacherSidebar(){
    return (
        <aside className="w-64 bg-white shadow-sm border-r border-gray-200 p-4">
            <div className="text-2xl font-bold text-blue-600 mb-6">Teacher Panel</div>
            <nav className="flex flex-col gap-2">
                {navLinks.map((link) => (
                    <TeacherNavItem
                    key={link.href}
                    href={link.href}
                    label={link.label}
                    icon={link.icon}
                    />
                ))}
            </nav>
        </aside>
    )
}