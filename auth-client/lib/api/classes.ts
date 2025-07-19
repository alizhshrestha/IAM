import { ClassResponseDto } from "@/types/class";

export const getAllClasses = async (
  schoolId: string
): Promise<ClassResponseDto[]> => {

  const token = sessionStorage.getItem("access_token");

  const res = await fetch(
    `http://localhost:8081/api/school/${schoolId}/class`,
    {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    if(!res.ok){
        throw new Error(`Failed to fetch classes: ${res.statusText}`);
    }

    const data : ClassResponseDto[] = await res.json();
    return data;
};
