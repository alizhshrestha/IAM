'use client'

import { BarChart2, BookOpen, Calendar, ClipboardList, FileText, FolderOpen, Home, Inbox, Megaphone, MessageCircle, Settings, User } from "lucide-react";
import TeacherNavItem from "./TeacherNavItem";
import { useSchool } from "@/context/SchoolContext";

export default function TeacherSidebar() {
    const {schoolId, userId, role} = useSchool();
    console.log(`school Id: ${schoolId}`);
    const base = `/schools/${schoolId}/teacher`

    const mainNavLinks = [
    { href: `${base}`, label: 'Dashboard', icon: Home },
    { href: `${base}/classes`, label: 'My Classes', icon: BookOpen },
    { href: `${base}/schedule`, label: 'Schedule', icon: Calendar },
    { href: `${base}/assignments`, label: 'Assignments', icon: ClipboardList },
    { href: `${base}/attendance`, label: 'Attendance', icon: BarChart2 },
    { href: `${base}/exams`, label: 'Exams', icon: FileText },
    { href: `${base}/resources`, label: 'Resources', icon: FolderOpen },
  ];

  const communicationNavLinks = [
    { href: `${base}/announcements`, label: 'Announcements', icon: Megaphone },
    { href: `${base}/messages`, label: 'Messages', icon: MessageCircle },
    { href: `${base}/submissions`, label: 'Submissions', icon: Inbox },
  ];

  const settingsNavLinks = [
    { href: `${base}/profile`, label: 'Profile', icon: User },
    { href: `${base}/settings`, label: 'Preferences', icon: Settings },
  ];

    return (
        <aside className="w-64 bg-white shadow-sm border-r border-gray-200 p-4">
            <div className="text-2xl font-bold text-blue-600 mb-6">Teacher Panel</div>
            <nav className="flex flex-col gap-2">
                {mainNavLinks.map((link) => (
                    <TeacherNavItem
                        key={link.href}
                        href={link.href}
                        label={link.label}
                        icon={link.icon}
                    />
                ))}
            </nav>

            {/* --- Communication --- */}
            <div className="text-xs text-gray-500 uppercase mb-2 mt-2">Interaction</div>
            <nav className="flex flex-col gap-2">
                {communicationNavLinks.map((link) => (
                    <TeacherNavItem
                        key={link.href}
                        href={link.href}
                        label={link.label}
                        icon={link.icon}
                    />
                ))}
            </nav>

            {/* --- Settings --- */}
            <div className="text-xs text-gray-500 uppercase mb-2 mt-2">Settings</div>
            <nav className="flex flex-col gap-2">
                {settingsNavLinks.map((link) => (
                    <TeacherNavItem
                        key={link.href}
                        href={link.href}
                        label={link.label}
                        icon={link.icon}
                    />
                ))}
            </nav>

            {/* --- Communication --- */}
        </aside>
    )
}