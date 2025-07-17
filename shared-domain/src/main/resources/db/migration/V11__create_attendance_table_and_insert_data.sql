-- Drop the previous FK constraint and update the column if needed
-- (Skip if this is first-time creation)
# recorded_by = Ram Sir’s users.id → 'e62fd26c-1b5d-4530-8f08-1cf65f65f6a0'

DROP TABLE IF EXISTS attendances;

CREATE TABLE attendances
(
    id           VARCHAR(100) PRIMARY KEY,
    student_id   VARCHAR(100) NOT NULL,        -- FK to students
    class_id     VARCHAR(100) NOT NULL,        -- FK to classes
    date         DATE         NOT NULL,
    present      BOOLEAN      NOT NULL,
    remarks      TEXT,                         -- Optional: late, sick, etc.
    recorded_by  VARCHAR(100),                 -- FK to users (teachers/admins)
    tenant_id    VARCHAR(100) NOT NULL,
    school_id    VARCHAR(100) NOT NULL,

    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by   VARCHAR(100),
    updated_by   VARCHAR(100),
    deleted      BOOLEAN DEFAULT FALSE,

    CONSTRAINT fk_attendance_student     FOREIGN KEY (student_id)   REFERENCES students(id) ON DELETE CASCADE,
    CONSTRAINT fk_attendance_class       FOREIGN KEY (class_id)     REFERENCES classes(id) ON DELETE CASCADE,
    CONSTRAINT fk_attendance_recorder    FOREIGN KEY (recorded_by)  REFERENCES users(id),
    CONSTRAINT fk_attendance_tenant      FOREIGN KEY (tenant_id)    REFERENCES tenants(id),
    CONSTRAINT fk_attendance_school      FOREIGN KEY (school_id)    REFERENCES schools(id)
);

-- === ATTENDANCES for 5 students from Grade 1 - A (Today) ===
INSERT INTO attendances (
    id, student_id, class_id, date, present, remarks,
    recorded_by, tenant_id, school_id,
    created_at, updated_at, created_by, updated_by
) VALUES
-- Present
('att-hima-01', 'stu-db-1-1', 'hima-class-01', CURRENT_DATE, TRUE,  NULL,
 'e62fd26c-1b5d-4530-8f08-1cf65f65f6a0', 'f8a231cb-3b45-4d67-87b0-08df2d3e9c11', 'bfb6eb69-f90a-4536-9c32-9e4f8b9b9ef6',
 NOW(), NOW(), 'ram@himalaya.edu', 'ram@himalaya.edu'),

-- Absent with remarks
('att-hima-02', 'stu-db-1-2', 'hima-class-01', CURRENT_DATE, FALSE, 'Sick leave',
 'e62fd26c-1b5d-4530-8f08-1cf65f65f6a0', 'f8a231cb-3b45-4d67-87b0-08df2d3e9c11', 'bfb6eb69-f90a-4536-9c32-9e4f8b9b9ef6',
 NOW(), NOW(), 'ram@himalaya.edu', 'ram@himalaya.edu'),

-- Present
('att-hima-03', 'stu-db-1-3', 'hima-class-01', CURRENT_DATE, TRUE, NULL,
 'e62fd26c-1b5d-4530-8f08-1cf65f65f6a0', 'f8a231cb-3b45-4d67-87b0-08df2d3e9c11', 'bfb6eb69-f90a-4536-9c32-9e4f8b9b9ef6',
 NOW(), NOW(), 'ram@himalaya.edu', 'ram@himalaya.edu'),

-- Present
('att-hima-04', 'stu-db-1-4', 'hima-class-01', CURRENT_DATE, TRUE, NULL,
 'e62fd26c-1b5d-4530-8f08-1cf65f65f6a0', 'f8a231cb-3b45-4d67-87b0-08df2d3e9c11', 'bfb6eb69-f90a-4536-9c32-9e4f8b9b9ef6',
 NOW(), NOW(), 'ram@himalaya.edu', 'ram@himalaya.edu'),

-- Absent with remarks
('att-hima-05', 'stu-db-1-5', 'hima-class-01', CURRENT_DATE, FALSE, 'Late arrival - not allowed',
 'e62fd26c-1b5d-4530-8f08-1cf65f65f6a0', 'f8a231cb-3b45-4d67-87b0-08df2d3e9c11', 'bfb6eb69-f90a-4536-9c32-9e4f8b9b9ef6',
 NOW(), NOW(), 'ram@himalaya.edu', 'ram@himalaya.edu');

