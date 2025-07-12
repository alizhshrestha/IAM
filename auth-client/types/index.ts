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
