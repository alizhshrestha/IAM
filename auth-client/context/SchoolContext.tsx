'use client';

import { createContext, useContext, useEffect, useState } from "react";

type SchoolContextType = {
  schoolId: string;
  userId: string;
  role: string;
};

const SchoolContext = createContext<SchoolContextType | null>(null);

export const useSchool = () => {
  const context = useContext(SchoolContext);
  if (!context) {
    throw new Error("useSchool must be used within a SchoolProvider");
  }
  return context;
};

export const SchoolProvider = ({ children }: { children: React.ReactNode }) => {
  const [schoolId, setSchoolId] = useState<string | null>(null);
  const [userId, setUserId] = useState<string | null>(null);
  const [role, setRole] = useState<string | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const sid = localStorage.getItem("selected_school_id");
    const uid = localStorage.getItem("user_id");
    const r = localStorage.getItem("selected_role");

    setSchoolId(sid);
    setUserId(uid);
    setRole(r);
    setLoading(false);
  }, []);

  if (loading) return null; // or a loader/spinner

  if (!schoolId || !userId || !role) {
    // You could redirect to login/select-school page here
    return <div className="text-red-500 p-4">Missing school session info</div>;
  }

  return (
    <SchoolContext.Provider value={{ schoolId, userId, role }}>
      {children}
    </SchoolContext.Provider>
  );
};
