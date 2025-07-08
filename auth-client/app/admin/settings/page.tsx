'use client';

import { useState } from "react";

export default function SettingsPage() {
    const [form, setForm] = useState({
        schoolName: 'Sunshine Public School',
        logoUrl: '',
        academicYear: '2025-2026'
    });

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        console.log("Saving settings: ", form);

        //Call API to save
    };

    return (
        <div className="space-y-6 max-w-xl">
            <h1 className="text-2xl font-bold">⚙️ School Settings</h1>
            <form onSubmit={handleSubmit} className="space-y-4">
                <div>
                    <label className="form-label">School Name</label>
                    <input
                        type="text"
                        name="schoolName"
                        value={form.schoolName}
                        onChange={handleChange}
                        className="form-text-field"
                    />
                </div>

                <div>
                    <label className="form-label">Logo URL</label>
                    <input
                        type="text"
                        name="logoUrl"
                        value={form.logoUrl}
                        onChange={handleChange}
                        className="form-text-field" />
                </div>
                <button
                    type="submit"
                    className="bg-green-600 text-white px-4 py-2 rounded-md hover:bg-green-700 transition"
                >
                    Save Settings
                </button>
            </form>
        </div>
    )
}