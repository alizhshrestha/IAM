'use client';

import { SchoolProvider } from "@/context/SchoolContext";
import { logout } from "@/utils/auth";

export default function SchoolLayout({ children, }: { children: React.ReactNode; }) {
    return (
        <SchoolProvider>
            {/* Top Navbar */}
            <nav className="flex items-center justify-between px-6 py-3 shadow-sm bg-[--color-card] border-b border-[--color-border]">
                <div className="text-lg font-semibold text-[--color-primary]">School Portal</div>
                <button
                    onClick={logout}
                    className="bg-[--color-primary] hover:bg-[--color-primary-light] text-black font-medium px-4 py-1.5 rounded-xl transition"
                >
                    Logout
                </button>
            </nav>
            {children}
        </SchoolProvider>
    )
}