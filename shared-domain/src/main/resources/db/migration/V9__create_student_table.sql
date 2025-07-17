-- V9__students.sql
CREATE TABLE students
(
    id              VARCHAR(100) PRIMARY KEY,
    user_id         VARCHAR(100) NOT NULL UNIQUE,
    school_id       VARCHAR(100) NOT NULL,
    class_id        VARCHAR(100) NOT NULL,
    enrollment_no   VARCHAR(100) UNIQUE,
    enrollment_date DATE         NOT NULL,
    guardian_name   VARCHAR(255),
    guardian_phone  VARCHAR(50),
    address         TEXT,
    tenant_id       VARCHAR(100) NOT NULL,

    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by      VARCHAR(100),
    updated_by      VARCHAR(100),
    deleted         BOOLEAN   DEFAULT FALSE,

    CONSTRAINT fk_student_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_student_school FOREIGN KEY (school_id) REFERENCES schools (id) ON DELETE CASCADE,
    CONSTRAINT fk_student_class FOREIGN KEY (class_id) REFERENCES classes (id),
    CONSTRAINT fk_student_tenant FOREIGN KEY (tenant_id) REFERENCES tenants (id)
);

-- Index for filtering/searching
CREATE INDEX idx_students_by_school_class ON students (school_id, class_id);

CREATE INDEX idx_students_by_tenant ON students (tenant_id);
