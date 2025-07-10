export interface Tenant {
  id: string;
  name: string;
  domain: string;
}

export interface ClientInfo {
  clientId: string;
  redirectUris: string;
  tenantId: string;
  scopes: string;
  clientName: string;
}
