import { StudentItem } from "@/types/student";

export const getStudentsOfClass = async (
  schoolId: string,
  classId: string
): Promise<StudentItem[]> => {
  const token = sessionStorage.getItem("access_token");
  const res = await fetch(
    `http://localhost:8081/api/school/${schoolId}/class/${classId}/students`,
    {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    }
  );
  if(!res.ok) throw new Error("Failed to fetch students");
  return res.json();
};
