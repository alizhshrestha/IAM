CREATE TABLE class_schedule
(
    id               VARCHAR(100) PRIMARY KEY,
    day_of_week      VARCHAR(10)  NOT NULL, -- e.g., MONDAY, TUESDAY
    time_slot        VARCHAR(50)  NOT NULL, -- e.g., 08:00-09:00
    class_subject_id VARCHAR(100) NOT NULL,
    school_id        VARCHAR(100) NOT NULL,
    tenant_id        VARCHAR(100) NOT NULL,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by       VARCHAR(100),
    updated_by       VARCHAR(100),
    deleted          BOOLEAN   DEFAULT FALSE,
    CONSTRAINT fk_schedule_class_subject FOREIGN KEY (class_subject_id) REFERENCES class_subjects (id) ON DELETE CASCADE,
    CONSTRAINT fk_schedule_school FOREIGN KEY (school_id) REFERENCES schools (id) ON DELETE CASCADE,
    CONSTRAINT fk_schedule_tenant FOREIGN KEY (tenant_id) REFERENCES tenants (id)
);

-- Insert sample schedule for teacher e62fd26c-1b5d-4530-8f08-1cf65f65f6a0

INSERT INTO class_schedule (id, day_of_week, time_slot, class_subject_id, school_id, tenant_id, created_at, updated_at)
VALUES
    ('sched-001', 'MONDAY',    '08:00-09:00', 'cs-001', 'bfb6eb69-f90a-4536-9c32-9e4f8b9b9ef6', 'f8a231cb-3b45-4d67-87b0-08df2d3e9c11', now(), now()),
    ('sched-002', 'MONDAY',    '09:00-10:00', 'cs-002', 'bfb6eb69-f90a-4536-9c32-9e4f8b9b9ef6', 'f8a231cb-3b45-4d67-87b0-08df2d3e9c11', now(), now()),
    ('sched-003', 'MONDAY',    '10:00-11:00', 'cs-003', 'bfb6eb69-f90a-4536-9c32-9e4f8b9b9ef6', 'f8a231cb-3b45-4d67-87b0-08df2d3e9c11', now(), now()),

    ('sched-004', 'TUESDAY',   '08:00-09:00', 'cs-004', 'bfb6eb69-f90a-4536-9c32-9e4f8b9b9ef6', 'f8a231cb-3b45-4d67-87b0-08df2d3e9c11', now(), now()),
    ('sched-005', 'TUESDAY',   '09:00-10:00', 'cs-005', 'bfb6eb69-f90a-4536-9c32-9e4f8b9b9ef6', 'f8a231cb-3b45-4d67-87b0-08df2d3e9c11', now(), now()),
    ('sched-006', 'TUESDAY',   '10:00-11:00', 'cs-006', 'bfb6eb69-f90a-4536-9c32-9e4f8b9b9ef6', 'f8a231cb-3b45-4d67-87b0-08df2d3e9c11', now(), now()),

    ('sched-007', 'WEDNESDAY', '08:00-09:00', 'cs-001', 'bfb6eb69-f90a-4536-9c32-9e4f8b9b9ef6', 'f8a231cb-3b45-4d67-87b0-08df2d3e9c11', now(), now()),
    ('sched-008', 'WEDNESDAY', '09:00-10:00', 'cs-004', 'bfb6eb69-f90a-4536-9c32-9e4f8b9b9ef6', 'f8a231cb-3b45-4d67-87b0-08df2d3e9c11', now(), now()),

    ('sched-009', 'THURSDAY',  '08:00-09:00', 'cs-002', 'bfb6eb69-f90a-4536-9c32-9e4f8b9b9ef6', 'f8a231cb-3b45-4d67-87b0-08df2d3e9c11', now(), now()),
    ('sched-010', 'THURSDAY',  '09:00-10:00', 'cs-005', 'bfb6eb69-f90a-4536-9c32-9e4f8b9b9ef6', 'f8a231cb-3b45-4d67-87b0-08df2d3e9c11', now(), now()),

    ('sched-011', 'FRIDAY',    '08:00-09:00', 'cs-003', 'bfb6eb69-f90a-4536-9c32-9e4f8b9b9ef6', 'f8a231cb-3b45-4d67-87b0-08df2d3e9c11', now(), now()),
    ('sched-012', 'FRIDAY',    '09:00-10:00', 'cs-006', 'bfb6eb69-f90a-4536-9c32-9e4f8b9b9ef6', 'f8a231cb-3b45-4d67-87b0-08df2d3e9c11', now(), now());

