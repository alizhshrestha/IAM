'use client';

import { Checkbox } from "@/components/ui/Checkbox";
import { Select } from "@/components/ui/Select";
import { Tabs } from "@/components/ui/Tabs";
import { useSchool } from "@/context/SchoolContext";
import { AttendanceEntry, ClassData } from "@/types";
import { Key } from "lucide-react";
import { useEffect, useState } from "react";
import Calendar from "react-calendar";


const AttendancePage = () => {
    const [selectedClass, setSelectedClass] = useState('');
    const [selectedDate, setSelectedDate] = useState<Date>(new Date());
    const [classes, setClasses] = useState<ClassData[]>([]);
    const [attendanceData, setAttendanceData] = useState<AttendanceEntry[]>([]);

    const { schoolId, userId, tenantId } = useSchool();
    const teacherId = userId; // Assuming userId is the teacher's ID


    useEffect(() => {
        if (!schoolId || !userId) return;

        const token = sessionStorage.getItem('access_token');
        console.log("Fetching classes for:", { schoolId, userId, tenantId });

        fetch(`http://localhost:8081/api/school/${schoolId}/teacher/${userId}/classes`, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        })
            .then((res) => {
                if (!res.ok) {
                    throw new Error(`HTTP error! status: ${res.status}`);
                }
                return res.json();
            })
            .then((data) => {
                setClasses(data);
                if (data.length > 0) {
                    setSelectedClass(data[0].id);
                }
            })
            .catch((err) => {
                console.error('Failed to fetch classes: ', err);
                alert('Failed to load classes. Please try again.');
            })
    }, [schoolId, userId]);

    useEffect(() => {
        const token = sessionStorage.getItem('access_token');


        if (!selectedClass || !selectedDate) return;
        const formattedDate = selectedDate.toLocaleDateString('en-CA');
        console.log(`Selected class: ${selectedClass}, Selected date: ${formattedDate.toString()} for schoolId: ${schoolId},
        teacherId: ${teacherId}, tenantId: ${tenantId}`);

        const url = new URL('http://localhost:8081/api/attendance');
        url.searchParams.append('classId', selectedClass);
        url.searchParams.append('date', formattedDate);
        url.searchParams.append('schoolId', schoolId);
        url.searchParams.append('teacherId', teacherId);
        url.searchParams.append('tenantId', tenantId);

        fetch(url.toString(), {
            method: 'GET',
            headers: {
                Authorization: `Bearer ${token}`,
            }
        })
            .then((res) => {
                if (!res.ok) {
                    throw new Error("Failed to fetch attendance");
                }
                return res.json();
            })
            .then((data) => {
                setAttendanceData(data);
            })
            .catch((err) => {
                console.error('Error fetching attendance:', err);
            });
    }, [selectedClass, selectedDate, schoolId, teacherId, tenantId]);


    const handleAttendanceChange = (studentId: string) => {
        setAttendanceData((prev) =>
            prev.map((s) =>
                s.studentId === studentId ? { ...s, present: !s.present } : s
            )
        );
    };

    const handleSubmit = async () => {
        const token = sessionStorage.getItem('access_token');
        const formattedDate = selectedDate.toLocaleDateString('en-CA');

        const payload = attendanceData.map((entry) => ({
            id: null,
            classId: selectedClass,
            studentId: entry.studentId,
            date: formattedDate,
            present: entry.present,
            recordedBy: teacherId,
            remarks: entry.present ? 'Present' : 'Absent',
            tenantId: tenantId,
            schoolId: schoolId,
            createdBy: teacherId,
            updatedBy: teacherId,
        }));

        try {
            const res = await fetch('http://localhost:8081/api/attendance', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token}`,
                },
                body: JSON.stringify(payload),
            });

            if (!res.ok) {
                const errorData = await res.json();
                throw new Error(errorData.message || "Failed to submit attendance");
            }

            alert('Attendance submitted successfully!');
        } catch (err) {
            console.error("Failed to submit:", err);
            alert("Error submitting attendance.");
        }
    };

    const classOptions = classes.map(cls => ({
        label: `${cls.name} (${cls.subject} - ${cls.grade} ${cls.section})`,
        key: cls.subjectId,
        value: cls.id,
    }));

    return (
        <div className="p-6 space-y-6">
            <div className="space-y-1">
                <h1 className="text-2xl font-bold text-[--color-heading]">Attendance</h1>
                <Tabs
                    tabs={[
                        { label: "Mark Attendance", value: "mark" },
                        { label: "View Attendance", value: "view" },
                        { label: "Manage Attendance", value: "manage" },
                    ]}
                    defaultValue="mark"
                />
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-4 items-start">
                <Select
                    label="Select Class"
                    value={selectedClass}
                    onChange={(e) => {
                        console.log(`The value of selected class is: ${e.target.value}`)
                        setSelectedClass(e.target.value)
                    }}
                    options={classOptions}
                />
                <div>
                    <label className="block mb-1 text-sm font-medium text-gray-700">Select Date</label>
                    <Calendar
                        value={selectedDate}
                        onChange={(date) => setSelectedDate(date as Date)}
                    />
                </div>

                <div className="mt-6">
                    <h2 className="text-lg font-semibold mb-2">Students</h2>
                    <div className="space-y-3">
                        {attendanceData.length === 0 ? (
                            <p>No students found for selected class and date.</p>
                        ) : (
                            attendanceData.map((student) => {
                                console.log("Student data:", student);
                                return (
                                    <div key={student.studentId} className="flex items-center gap-4">
                                        <Checkbox
                                            checked={student.present}
                                            onChange={() => handleAttendanceChange(student.studentId)}
                                        />
                                        <span>{student.studentName}</span>
                                    </div>
                                )
                            })
                        )}
                    </div>
                </div>
            </div>
            <button
                onClick={handleSubmit}
                className="mt-6 px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
            >
                Submit Attendance
            </button>

        </div>


    )
}

export default AttendancePage;