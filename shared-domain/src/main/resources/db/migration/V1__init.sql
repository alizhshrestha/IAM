-- Create the schema
-- CREATE SCHEMA IF NOT EXISTS school_mgmt_db;
-- USE school_mgmt_db;

-- ========== TENANTS ==========
CREATE TABLE tenants
(
    id     VARCHAR(100) PRIMARY KEY,
    name   VARCHAR(255) NOT NULL,
    domain VARCHAR(255) NOT NULL UNIQUE
);

-- ========== REGISTERED CLIENTS ==========
CREATE TABLE registered_client
(
    id                            VARCHAR(100) PRIMARY KEY,
    client_id                     VARCHAR(100) NOT NULL,
    client_id_issued_at           TIMESTAMP,
    client_secret                 VARCHAR(200),
    client_secret_expires_at      TIMESTAMP,
    client_name                   VARCHAR(200),
    client_authentication_methods VARCHAR(1000),
    authorization_grant_types     VARCHAR(1000),
    redirect_uris                 VARCHAR(1000),
    scopes                        VARCHAR(1000),
    client_settings               VARCHAR(2000),
    token_settings                VARCHAR(2000),
    tenant_id                     VARCHAR(100) NOT NULL,
    CONSTRAINT fk_registered_client_tenant FOREIGN KEY (tenant_id) REFERENCES tenants (id),
    CONSTRAINT uq_client_tenant UNIQUE (client_id, tenant_id)
);

-- ========== AUTH USERS (FOR SPRING AUTH SERVER) ==========
CREATE TABLE app_users
(
    id             VARCHAR(255) PRIMARY KEY,
    tenant_id      VARCHAR(100),
    username       VARCHAR(255) NOT NULL UNIQUE,
    password       VARCHAR(255) NOT NULL,
    enabled        BOOLEAN DEFAULT TRUE,
    is_super_admin BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_app_users_tenant FOREIGN KEY (tenant_id) REFERENCES tenants (id)
);

-- ========== AUTH USER ROLES ==========
CREATE TABLE app_user_roles
(
    user_id VARCHAR(255) NOT NULL,
    role    VARCHAR(255),
    PRIMARY KEY (user_id, role),
    FOREIGN KEY (user_id) REFERENCES app_users (id) ON DELETE CASCADE
);

-- ========== SCHOOLS ==========
CREATE TABLE schools
(
    id            VARCHAR(255) PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    logo_url      VARCHAR(255),
    address       VARCHAR(255),
    academic_year VARCHAR(100),
    tenant_id     VARCHAR(100) NOT NULL,
    CONSTRAINT fk_schools_tenant FOREIGN KEY (tenant_id) REFERENCES tenants (id)
);

-- ========== DOMAIN USERS (linked to app_users) ==========
CREATE TABLE users
(
    id          VARCHAR(255) PRIMARY KEY,
    app_user_id VARCHAR(100) NOT NULL,
    full_name   VARCHAR(255) NOT NULL,
    email       VARCHAR(255) NOT NULL,
    active      BOOLEAN DEFAULT TRUE,
    school_id   VARCHAR(255),
    CONSTRAINT fk_users_school FOREIGN KEY (school_id) REFERENCES schools (id),
    CONSTRAINT fk_users_app_user FOREIGN KEY (app_user_id) REFERENCES app_users (id)
);

-- ========== USER PROFILES (optional extra info) ==========
-- CREATE TABLE user_profiles
-- (
--     id           VARCHAR(100) PRIMARY KEY,
--     user_id      VARCHAR(100) NOT NULL UNIQUE,
--     phone_number VARCHAR(50),
--     gender       VARCHAR(10),
--     dob          DATE,
--     profile_pic  VARCHAR(255),
--     address      VARCHAR(255),
--     bio          TEXT,
--     FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
-- );

-- ========== ROLES ==========
CREATE TABLE roles
(
    id          VARCHAR(255) PRIMARY KEY,
    name        VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255)
);

-- ========== USER ROLES ==========
CREATE TABLE user_roles
(
    user_id VARCHAR(255) NOT NULL,
    role_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);
