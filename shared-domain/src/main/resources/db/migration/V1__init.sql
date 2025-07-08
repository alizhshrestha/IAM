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

-- ========== ROLES ==========
CREATE TABLE roles
(
    id          VARCHAR(255) PRIMARY KEY,
    name        VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255)
);

-- ========== USERS ==========
CREATE TABLE users
(
    id        VARCHAR(255) PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email     VARCHAR(255) NOT NULL UNIQUE,
    password  VARCHAR(255) NOT NULL,
    active    BOOLEAN DEFAULT TRUE,
    school_id VARCHAR(255),
    CONSTRAINT fk_users_school FOREIGN KEY (school_id) REFERENCES schools (id)
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

-- ========== AUTH USERS (FOR SPRING AUTH SERVER) ==========
CREATE TABLE app_users
(
    id        VARCHAR(255) PRIMARY KEY,
    tenant_id VARCHAR(100) NOT NULL,
    username  VARCHAR(255) NOT NULL UNIQUE,
    password  VARCHAR(255) NOT NULL,
    enabled   BIT(1)       NOT NULL DEFAULT 1,
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
