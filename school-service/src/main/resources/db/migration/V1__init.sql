CREATE TABLE schools
(
    id            CHAR(36)   NOT NULL PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    logo_url      VARCHAR(255),
    address       VARCHAR(255),
    academic_year VARCHAR(100),
    tenant_id     VARCHAR(100) NOT NULL
);

CREATE TABLE roles
(
    id          CHAR(36)   NOT NULL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255)
);

CREATE TABLE users
(
    id        CHAR(36)   NOT NULL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email     VARCHAR(255) NOT NULL UNIQUE,
    password  VARCHAR(255) NOT NULL,
    active    BOOLEAN DEFAULT TRUE,
    school_id CHAR(36),
    FOREIGN KEY (school_id) REFERENCES schools (id)
);

CREATE TABLE user_roles
(
    user_id CHAR(36) NOT NULL,
    role_id CHAR(36) NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

-- SEEDING SAMPLE DATA

-- Insert Schools
INSERT INTO schools (id, name, logo_url, address, academic_year, tenant_id)
VALUES ('131adfe6-49a2-418a-8094-fd7be4b046e0', 'Himalaya School', 'https://example.com/logo1.png', 'Kathmandu', '2024-25',
        'tenant1'),
       ('687e5b7f-fc9f-48a8-afda-13d2f2885e22', 'Everest Academy', 'https://example.com/logo2.png', 'Pokhara', '2024-25',
        'tenant2');

-- Insert Roles
INSERT INTO roles (id, name, description)
VALUES ('0f4b0446-ce40-4eeb-827f-21392f2af5ae', 'ROLE_ADMIN', 'Administrator'),
       ('c27e07c7-30f6-46fe-8869-657c07f1e917', 'ROLE_TEACHER', 'Teacher'),
       ('b40b90e8-4ec8-4cf6-9f08-598fc3dfc302', 'ROLE_STUDENT', 'Student');

-- Insert Users
INSERT INTO users (id, full_name, email, password, active, school_id)
VALUES
    -- Admin for tenant1
    ('460f8bd7-17f8-4731-a7b1-d5cfbe9b7c78', 'Admin User', 'admin@himalaya.edu', '$2a$10$GluKLQXTTB8OCV0Dqh6a6ONLThp25r5Mh53AixAajrtKvhRmrZ3IK', true,
     (SELECT id FROM schools WHERE name = 'Himalaya School')),

    -- Teacher for tenant1
    ('219cefe8-8d2c-4d87-a027-0ae81cc9a0c3', 'Teacher One', 'teacher1@himalaya.edu', '$2a$10$GluKLQXTTB8OCV0Dqh6a6ONLThp25r5Mh53AixAajrtKvhRmrZ3IK', true,
     (SELECT id FROM schools WHERE name = 'Himalaya School')),

    -- Student for tenant2
    ('cdae58e3-da4f-46e0-9912-3d95fbd9d49a', 'Student Alpha', 'student1@everest.edu', '$2a$10$GluKLQXTTB8OCV0Dqh6a6ONLThp25r5Mh53AixAajrtKvhRmrZ3IK', true,
     (SELECT id FROM schools WHERE name = 'Everest Academy'));

-- Assign Roles
-- Assign Admin role
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u,
     roles r
WHERE u.email = 'admin@himalaya.edu'
  AND r.name = 'ROLE_ADMIN';

-- Assign Teacher role
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u,
     roles r
WHERE u.email = 'teacher1@himalaya.edu'
  AND r.name = 'ROLE_TEACHER';

-- Assign Student role
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u,
     roles r
WHERE u.email = 'student1@everest.edu'
  AND r.name = 'ROLE_STUDENT';