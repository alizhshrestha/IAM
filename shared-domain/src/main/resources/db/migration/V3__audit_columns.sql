-- Add audit columns to tenants
ALTER TABLE tenants
    ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- Add audit columns to registered_client
ALTER TABLE registered_client
    ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- Add audit columns to app_users
ALTER TABLE app_users
    ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- Add audit columns to schools
ALTER TABLE schools
    ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- Add audit columns to users
ALTER TABLE users
    ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- Add audit columns to roles
ALTER TABLE roles
    ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- Optionally for user_roles and app_user_roles (if needed for auditing)
-- Though usually not necessary unless you want to track role assignment time

-- ALTER TABLE user_roles
-- ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
-- ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- ALTER TABLE app_user_roles
-- ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
-- ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
