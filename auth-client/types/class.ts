export interface ClassResponseDto {
  id: string;
  name: string;
  section: string;
  grade: string;
  schoolId: string;
  tenantId: string;
  createdBy: string;
  updatedBy: string;
  createdAt: string; // or Date if you convert
  updatedAt: string;
  deleted: boolean;
}