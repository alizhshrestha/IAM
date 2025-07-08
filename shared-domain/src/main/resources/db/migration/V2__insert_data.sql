-- Insert Tenants
INSERT INTO tenants (id, name, domain)
VALUES ('f8a231cb-3b45-4d67-87b0-08df2d3e9c11', 'Himalaya EdTech', 'tenant1.auth.example.com'),
       ('1b9cf799-e33e-4624-9f7a-71947656fc29', 'Everest Schools', 'tenant2.auth.example.com');

-- Insert Registered Clients
INSERT INTO registered_client (id, client_id, client_name, client_authentication_methods,
                               authorization_grant_types, redirect_uris, scopes,
                               client_settings, token_settings, tenant_id)
VALUES ('d9a2b703-018f-4e2c-a231-6f9f40d09b1f', 'admin-client', 'Admin Client', 'none',
        'authorization_code,refresh_token', 'http://localhost:3000/callback', 'openid,profile',
        '{"requireProofKey":true,"requireAuthorizationConsent":true}', '{"accessTokenTimeToLive":"PT30M"}', 'f8a231cb-3b45-4d67-87b0-08df2d3e9c11'),

       ('a5716e60-e78e-4dcb-b16e-54b15717d14c', 'teacher-client', 'Teacher Client', 'none',
        'authorization_code,refresh_token', 'http://localhost:3000/callback', 'openid,profile',
        '{"requireProofKey":true,"requireAuthorizationConsent":true}', '{"accessTokenTimeToLive":"PT30M"}', 'f8a231cb-3b45-4d67-87b0-08df2d3e9c11');

-- Insert App Users
INSERT INTO app_users (id, tenant_id, username, password, enabled)
VALUES ('3e9d57a0-62cf-4a64-8f76-2e4d5b9446c6', 'f8a231cb-3b45-4d67-87b0-08df2d3e9c11', 'admin@himalaya.edu',
        '$2a$10$hPFsfcVjRBy.Ym.yhvFESOYRMx0OYcftUJh9D5Eo92/UlNwM8CXN6', 1),
       ('a1f0ce42-beb9-4e3e-92c5-5b5714578dc7', 'f8a231cb-3b45-4d67-87b0-08df2d3e9c11', 'teacher1@himalaya.edu',
        '$2a$10$hPFsfcVjRBy.Ym.yhvFESOYRMx0OYcftUJh9D5Eo92/UlNwM8CXN6', 1),
       ('f309287e-cabd-466e-85bb-d9ea13c9dc57', '1b9cf799-e33e-4624-9f7a-71947656fc29', 'admin@everest.edu', '$2a$10$hPFsfcVjRBy.Ym.yhvFESOYRMx0OYcftUJh9D5Eo92/UlNwM8CXN6',
        1);

-- Insert App User Roles
INSERT INTO app_user_roles (user_id, role)
VALUES ('3e9d57a0-62cf-4a64-8f76-2e4d5b9446c6', 'ROLE_ADMIN'),
       ('a1f0ce42-beb9-4e3e-92c5-5b5714578dc7', 'ROLE_TEACHER'),
       ('f309287e-cabd-466e-85bb-d9ea13c9dc57', 'ROLE_ADMIN');

-- Insert Schools
INSERT INTO schools (id, name, logo_url, address, academic_year, tenant_id)
VALUES ('bfb6eb69-f90a-4536-9c32-9e4f8b9b9ef6', 'Himalaya High School', 'https://example.com/logo1.png', 'Kathmandu',
        '2024-25', 'f8a231cb-3b45-4d67-87b0-08df2d3e9c11'),
       ('579674f3-b7a2-45f4-a9f7-f93a276f3d3b', 'Everest International', 'https://example.com/logo2.png', 'Pokhara',
        '2024-25', '1b9cf799-e33e-4624-9f7a-71947656fc29');

-- Insert Roles
INSERT INTO roles (id, name, description)
VALUES ('2e52c77a-1df0-4c5c-a587-3e1b3c4f0d55', 'ROLE_ADMIN', 'School administrator'),
       ('bd49b43d-6ed3-4b67-9c8f-95865d9e847f', 'ROLE_TEACHER', 'Teacher'),
       ('7bb1f1a6-3f4e-4b2e-81b5-fbe68f6f75e1', 'ROLE_STUDENT', 'Student');

-- Insert Users (Domain-specific Users under a School)
INSERT INTO users (id, full_name, email, password, active, school_id)
VALUES ('41fae9b4-4ae5-4b59-9f85-ff762a1e2e75', 'Admin One', 'admin@himalaya.edu', '$2a$10$hPFsfcVjRBy.Ym.yhvFESOYRMx0OYcftUJh9D5Eo92/UlNwM8CXN6', true,
        'bfb6eb69-f90a-4536-9c32-9e4f8b9b9ef6'),
       ('e62fd26c-1b5d-4530-8f08-1cf65f65f6a0', 'Teacher A', 'teacher1@himalaya.edu', '$2a$10$hPFsfcVjRBy.Ym.yhvFESOYRMx0OYcftUJh9D5Eo92/UlNwM8CXN6', true,
        'bfb6eb69-f90a-4536-9c32-9e4f8b9b9ef6'),
       ('3d0f39a5-81a5-4e96-bc52-7c4aa3f1a34f', 'Student X', 'student1@everest.edu', '$2a$10$hPFsfcVjRBy.Ym.yhvFESOYRMx0OYcftUJh9D5Eo92/UlNwM8CXN6', true,
        '579674f3-b7a2-45f4-a9f7-f93a276f3d3b');

-- Assign Roles to Users
INSERT INTO user_roles (user_id, role_id)
VALUES ('41fae9b4-4ae5-4b59-9f85-ff762a1e2e75', '2e52c77a-1df0-4c5c-a587-3e1b3c4f0d55'), -- Admin
       ('e62fd26c-1b5d-4530-8f08-1cf65f65f6a0', 'bd49b43d-6ed3-4b67-9c8f-95865d9e847f'), -- Teacher
       ('3d0f39a5-81a5-4e96-bc52-7c4aa3f1a34f', '7bb1f1a6-3f4e-4b2e-81b5-fbe68f6f75e1'); -- Student
