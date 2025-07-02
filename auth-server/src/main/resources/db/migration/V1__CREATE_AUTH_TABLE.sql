CREATE TABLE app_user_roles
(
    user_id VARCHAR(255) NOT NULL,
    `role`  VARCHAR(255) NULL
);

CREATE TABLE app_users
(
    id        VARCHAR(255) NOT NULL,
    tenant_id VARCHAR(255) NULL,
    username  VARCHAR(255) NULL,
    password  VARCHAR(255) NULL,
    enabled   BIT(1)       NOT NULL,
    CONSTRAINT pk_app_users PRIMARY KEY (id)
);

CREATE TABLE tenants
(
    id     VARCHAR(255) NOT NULL,
    name   VARCHAR(255) NULL,
    domain VARCHAR(255) NULL,
    CONSTRAINT pk_tenants PRIMARY KEY (id)
);

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
    tenant_id                     VARCHAR(100) NOT NULL
);

ALTER TABLE app_user_roles
    ADD CONSTRAINT fk_app_user_roles_on_app_user FOREIGN KEY (user_id) REFERENCES app_users (id);

CREATE UNIQUE INDEX idx_client_id_tenant_id ON registered_client (client_id, tenant_id);

