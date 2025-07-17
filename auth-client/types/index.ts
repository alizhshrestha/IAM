export interface Tenant {
  id: string;
  name: string;
  domain: string;
}

// export interface ClientInfo {
//   clientId: string;
//   redirectUris: string;
//   tenantId: string;
//   scopes: string;
//   clientName: string;
// }

export interface ClassData {
  subjectId: string;
  id: string;
  name: string;
  grade: string;
  section: string;
  subject: string;
}

export interface ClientInfo {
  clientId: string;
  clientName: string;
  redirectUri: string;
  scopes: string;
}

export interface SchoolInfo {
  schoolId: string;
  schoolName: string;
  tenantId: string;
  tenantDomain: string;
  clients: ClientInfo[];
}

export interface TeacherScheduleResponseDto {
  timeSlots: string[];
  days: string[];
  schedule: {
    [timeSlot: string]: {
      [dayOfWeek: string]: string;
    };
  };
}

export interface AssignmentData{
  id: string;
  title: string;
  subject: string;
  className: string;
  dueDate: string;
}

export type School = {
  id: string;
  name: string;
  logoUrl: string;
  address: string;
  academicYear: string;
  tenantId: string;
  role: string;
  userId: string;
};

export type AttendanceEntry = {
  studentId: string;
  studentName: string;
  present: boolean;
}

export type AttendancePostPayload = {
  id: string | null;
  classId: string;
  studentId: string;
  date: string;
  present: boolean;
  recordedBy: string;
  remarks: string;
  tenantId: string;
  schoolId: string;
  createdBy: string;
  updatedBy: string;
};

export const getRolePath = (role: string) => {
  switch (role) {
    case "ROLE_ADMIN":
      return "admin";
    case "ROLE_TEACHER":
      return "teacher";
    case "ROLE_STUDENT":
      return "student";
    default:
      return "unauthorized";
  }
};
