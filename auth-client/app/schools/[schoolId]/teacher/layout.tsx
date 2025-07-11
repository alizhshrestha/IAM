'use client';

import TeacherSidebar from '@/components/teacher/TeacherSidebar';
import { useSchool } from '@/context/SchoolContext';
import React from 'react';

export default function TeacherLayout({
  children,
}: {
  children: React.ReactNode;
}) {

  const { schoolId, userId, role } = useSchool();
  console.log(`school Id: ${schoolId}, user id: ${userId}, role: ${role}`);

  return (
    <div className='flex min-h-screen'>
      <TeacherSidebar />
      <main className='flex-1 p-6 bg-gray-50'>{children}</main>
    </div>
  );
}
