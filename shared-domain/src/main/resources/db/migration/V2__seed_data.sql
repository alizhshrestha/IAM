-- ========== TENANTS ==========
INSERT INTO tenants (id, name, domain)
VALUES
    ('f8a231cb-3b45-4d67-87b0-08df2d3e9c11', 'Himalaya EdTech', 'tenant1.auth.example.com'),
    ('1b9cf799-e33e-4624-9f7a-71947656fc29', 'Everest Schools', 'tenant2.auth.example.com');

-- ========== REGISTERED CLIENTS ==========
INSERT INTO registered_client (
    id, client_id, client_name, client_authentication_methods,
    authorization_grant_types, redirect_uris, scopes,
    client_settings, token_settings, tenant_id
) VALUES
      ('d9a2b703-018f-4e2c-a231-6f9f40d09b1f', 'admin-client', 'Admin Client', 'none',
       'authorization_code,refresh_token', 'http://localhost:3000/callback', 'openid,profile',
       '{"requireProofKey":true,"requireAuthorizationConsent":true}', '{"accessTokenTimeToLive":"PT30M"}', 'f8a231cb-3b45-4d67-87b0-08df2d3e9c11'),

      ('a5716e60-e78e-4dcb-b16e-54b15717d14c', 'teacher-client', 'Teacher Client', 'none',
       'authorization_code,refresh_token', 'http://localhost:3000/callback', 'openid,profile',
       '{"requireProofKey":true,"requireAuthorizationConsent":true}', '{"accessTokenTimeToLive":"PT30M"}', 'f8a231cb-3b45-4d67-87b0-08df2d3e9c11');

-- $2a$10$hPFsfcVjRBy.Ym.yhvFESOYRMx0OYcftUJh9D5Eo92/UlNwM8CXN6

-- ========== APP USERS (SSO identities) ==========
INSERT INTO app_users (id, tenant_id, username, password, enabled, is_super_admin)
VALUES
  -- SUPER ADMIN
  ('11111111-1111-1111-1111-111111111111', NULL, 'superadmin@root.com', '$2a$10$hPFsfcVjRBy.Ym.yhvFESOYRMx0OYcftUJh9D5Eo92/UlNwM8CXN6', TRUE, TRUE),

  -- Admin of Himalaya
  ('3e9d57a0-62cf-4a64-8f76-2e4d5b9446c6', 'f8a231cb-3b45-4d67-87b0-08df2d3e9c11', 'admin@himalaya.edu', '$2a$10$hPFsfcVjRBy.Ym.yhvFESOYRMx0OYcftUJh9D5Eo92/UlNwM8CXN6', TRUE, FALSE),

  -- Teacher of Himalaya
  ('a1f0ce42-beb9-4e3e-92c5-5b5714578dc7', 'f8a231cb-3b45-4d67-87b0-08df2d3e9c11', 'ram@himalaya.edu', '$2a$10$hPFsfcVjRBy.Ym.yhvFESOYRMx0OYcftUJh9D5Eo92/UlNwM8CXN6', TRUE, FALSE),

  -- Admin of Everest
  ('f309287e-cabd-466e-85bb-d9ea13c9dc57', '1b9cf799-e33e-4624-9f7a-71947656fc29', 'admin@everest.edu', '$2a$10$hPFsfcVjRBy.Ym.yhvFESOYRMx0OYcftUJh9D5Eo92/UlNwM8CXN6', TRUE, FALSE),

  -- Asha with two roles (admin and teacher)
  ('f001287e-cabd-466e-85bb-d9ea13c9dc99', '1b9cf799-e33e-4624-9f7a-71947656fc29', 'asha@everest.edu', '$2a$10$hPFsfcVjRBy.Ym.yhvFESOYRMx0OYcftUJh9D5Eo92/UlNwM8CXN6', TRUE, FALSE);

-- ========== APP USER ROLES ==========
INSERT INTO app_user_roles (user_id, role)
VALUES
    ('11111111-1111-1111-1111-111111111111', 'ROLE_SUPER_ADMIN'),
    ('3e9d57a0-62cf-4a64-8f76-2e4d5b9446c6', 'ROLE_ADMIN'),
    ('a1f0ce42-beb9-4e3e-92c5-5b5714578dc7', 'ROLE_TEACHER'),
    ('f309287e-cabd-466e-85bb-d9ea13c9dc57', 'ROLE_ADMIN'),
    ('f001287e-cabd-466e-85bb-d9ea13c9dc99', 'ROLE_ADMIN'),
    ('f001287e-cabd-466e-85bb-d9ea13c9dc99', 'ROLE_TEACHER');

-- ========== SCHOOLS ==========
INSERT INTO schools (id, name, logo_url, address, academic_year, tenant_id)
VALUES
    ('bfb6eb69-f90a-4536-9c32-9e4f8b9b9ef6', 'Himalaya High School', 'https://example.com/logo1.png', 'Kathmandu', '2024-25', 'f8a231cb-3b45-4d67-87b0-08df2d3e9c11'),
    ('579674f3-b7a2-45f4-a9f7-f93a276f3d3b', 'Everest International', 'https://example.com/logo2.png', 'Pokhara', '2024-25', '1b9cf799-e33e-4624-9f7a-71947656fc29'),
    ('a3f674f3-b7a2-45f4-a9f7-f93a276f3d3b', 'Everest Central School', 'https://example.com/logo3.png', 'Biratnagar', '2024-25', '1b9cf799-e33e-4624-9f7a-71947656fc29');

-- ========== ROLES ==========
INSERT INTO roles (id, name, description)
VALUES
    ('2e52c77a-1df0-4c5c-a587-3e1b3c4f0d55', 'ROLE_ADMIN', 'School administrator'),
    ('bd49b43d-6ed3-4b67-9c8f-95865d9e847f', 'ROLE_TEACHER', 'Teacher'),
    ('7bb1f1a6-3f4e-4b2e-81b5-fbe68f6f75e1', 'ROLE_STUDENT', 'Student');

-- ========== USERS (School-specific profiles) ==========
INSERT INTO users (id, full_name, email, school_id, app_user_id, active)
VALUES
    -- Himalaya School Admin
    ('41fae9b4-4ae5-4b59-9f85-ff762a1e2e75', 'Admin One', 'admin@himalaya.edu', 'bfb6eb69-f90a-4536-9c32-9e4f8b9b9ef6', '3e9d57a0-62cf-4a64-8f76-2e4d5b9446c6', true),

    -- Himalaya Teacher (Ram)
    ('e62fd26c-1b5d-4530-8f08-1cf65f65f6a0', 'Ram Sir', 'ram@himalaya.edu', 'bfb6eb69-f90a-4536-9c32-9e4f8b9b9ef6', 'a1f0ce42-beb9-4e3e-92c5-5b5714578dc7', true),

    -- Everest Admin
    ('3d0f39a5-81a5-4e96-bc52-7c4aa3f1a34f', 'Everest Admin', 'admin@everest.edu', '579674f3-b7a2-45f4-a9f7-f93a276f3d3b', 'f309287e-cabd-466e-85bb-d9ea13c9dc57', true),

    -- Asha as Admin
    ('7c17d81a-927d-4142-b111-91ac60c12eb5', 'Asha', 'asha@everest.edu', '579674f3-b7a2-45f4-a9f7-f93a276f3d3b', 'f001287e-cabd-466e-85bb-d9ea13c9dc99', true),

    -- Asha as Teacher in another school
    ('5a1d6a7e-4184-41cb-bd4b-7b593212ce85', 'Asha', 'asha@everest.edu', 'a3f674f3-b7a2-45f4-a9f7-f93a276f3d3b', 'f001287e-cabd-466e-85bb-d9ea13c9dc99', true);

-- ========== USER ROLES ==========
INSERT INTO user_roles (user_id, role_id)
VALUES
    ('41fae9b4-4ae5-4b59-9f85-ff762a1e2e75', '2e52c77a-1df0-4c5c-a587-3e1b3c4f0d55'), -- Admin
    ('e62fd26c-1b5d-4530-8f08-1cf65f65f6a0', 'bd49b43d-6ed3-4b67-9c8f-95865d9e847f'), -- Teacher
    ('3d0f39a5-81a5-4e96-bc52-7c4aa3f1a34f', '2e52c77a-1df0-4c5c-a587-3e1b3c4f0d55'), -- Admin Everest
    ('7c17d81a-927d-4142-b111-91ac60c12eb5', '2e52c77a-1df0-4c5c-a587-3e1b3c4f0d55'), -- Asha Admin
    ('5a1d6a7e-4184-41cb-bd4b-7b593212ce85', 'bd49b43d-6ed3-4b67-9c8f-95865d9e847f'); -- Asha Teacher

-- ========== USER PROFILES (OPTIONAL) ==========
-- INSERT INTO user_profiles (id, user_id, phone_number, gender, dob, profile_pic, address, bio)
-- VALUES ('d0c1c2b6-46cb-4a9d-a179-b1dbd1c3e1b0', '41fae9b4-4ae5-4b59-9f85-ff762a1e2e75', '+9779800000001', 'Male',
--         '1985-02-10',
--         'https://example.com/profiles/admin.png', 'Lalitpur, Nepal', 'Experienced school administrator.'),
--
--        ('e23e1d7a-502f-4b2f-bb58-7ae77d213e2e', 'e62fd26c-1b5d-4530-8f08-1cf65f65f6a0', '+9779800000002', 'Female',
--         '1990-05-20',
--         'https://example.com/profiles/teacher.png', 'Kathmandu, Nepal', 'Passionate science teacher.'),
--
--        ('b1c07e3d-8012-4e7d-9618-9352f8310db3', '3d0f39a5-81a5-4e96-bc52-7c4aa3f1a34f', '+9779800000003', 'Other',
--         '2007-09-01',
--         'https://example.com/profiles/student.png', 'Pokhara, Nepal', 'Loves robotics and literature.');
