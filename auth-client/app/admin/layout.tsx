'use client';

import { BookIcon, CalendarIcon, CreditCardIcon, LayoutDashboardIcon, SettingsIcon, UsersIcon } from "lucide-react";
import Link from "next/link";
import { usePathname } from "next/navigation";

const navItems = [
    { icon: LayoutDashboardIcon, label: 'Dashboard', href: '/admin' },
    { icon: UsersIcon, label: 'Users', href: '/admin/users' },
    { icon: BookIcon, label: 'Courses', href: '/admin/courses' },
    { icon: CalendarIcon, label: 'Attendance', href: '/admin/attendance' },
    { icon: CreditCardIcon, label: 'Fees', href: '/admin/fees' },
    { icon: SettingsIcon, label: 'Settings', href: '/admin/settings' },
];

export default function AdminLayout({ children }: { children: React.ReactNode }) {
    const pathname = usePathname();

    return (
        <div className="flex min-h-screen bg-[var(--color-bg)] text-[var(--color-text-main)]">
            {/* Sidebar */}
            <aside className="w-64 bg-white border-r border-[var(--color-border)] shadow-sm hidden md:block">
                <div className="p-6 font-bold text-lg text-[var(--color-primary)]">School Admin</div>
                <nav className="flex flex-col gap-1 px-4">
                    {navItems.map(({ icon: Icon, label, href }) => (
                        <Link
                            key={label}
                            href={href}
                            className={`flex items-center gap-3 px-4 py-2 rounded-md transition hover:bg-[var(--color-primary-light)] ${pathname === href
                                    ? 'bg-[var(--color-primary-light)] text-[var(--color-primary)] font-semibold'
                                    : 'text-[var(--color-text-main)]'
                                }`}
                        >
                            <Icon className="w-5 h-5" />
                            {label}
                        </Link>
                    ))}
                </nav>
            </aside>

            {/* Main Content */}
            <main className="flex-1 p-6">
                <header className="mb-6 text-xl font-bold">Admin Panel</header>
                {children}
            </main>
        </div>
    );
}
