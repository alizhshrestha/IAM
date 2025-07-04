ALTER TABLE registered_client
    ADD UNIQUE INDEX idx_clientid_tenantid (client_id, tenant_id);