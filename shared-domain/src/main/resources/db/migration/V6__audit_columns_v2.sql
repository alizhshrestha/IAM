-- ========== TENANTS ==========
ALTER TABLE tenants
    ADD COLUMN created_by VARCHAR(100),
    ADD COLUMN updated_by VARCHAR(100),
    ADD COLUMN deleted BOOLEAN DEFAULT FALSE;

-- ========== REGISTERED CLIENTS ==========
ALTER TABLE registered_client
    ADD COLUMN created_by VARCHAR(100),
    ADD COLUMN updated_by VARCHAR(100),
    ADD COLUMN deleted BOOLEAN DEFAULT FALSE;

-- ========== APP USERS ==========
ALTER TABLE app_users
    ADD COLUMN created_by VARCHAR(100),
    ADD COLUMN updated_by VARCHAR(100),
    ADD COLUMN deleted BOOLEAN DEFAULT FALSE;

-- ========== SCHOOLS ==========
ALTER TABLE schools
    ADD COLUMN created_by VARCHAR(100),
    ADD COLUMN updated_by VARCHAR(100),
    ADD COLUMN deleted BOOLEAN DEFAULT FALSE;

-- ========== USERS ==========
ALTER TABLE users
    ADD COLUMN created_by VARCHAR(100),
    ADD COLUMN updated_by VARCHAR(100),
    ADD COLUMN deleted BOOLEAN DEFAULT FALSE;

-- ========== ROLES ==========
ALTER TABLE roles
    ADD COLUMN created_by VARCHAR(100),
    ADD COLUMN updated_by VARCHAR(100),
    ADD COLUMN deleted BOOLEAN DEFAULT FALSE;
