import TeacherSidebar from '@/components/teacher/TeacherSidebar';
import React from 'react';

export default function TeacherLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <div className='flex min-h-screen'>
      <TeacherSidebar />
      <main className='flex-1 p-6 bg-gray-50'>{children}</main>
    </div>
  );
}
