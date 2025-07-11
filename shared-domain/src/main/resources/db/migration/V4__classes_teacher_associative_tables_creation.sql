-- ========== CLASSES ==========
CREATE TABLE classes
(
    id         VARCHAR(100) PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    section    VARCHAR(100),
    grade      VARCHAR(50),
    school_id  VARCHAR(100) NOT NULL,
    tenant_id  VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted    BOOLEAN   DEFAULT FALSE,
    CONSTRAINT fk_classes_school FOREIGN KEY (school_id) REFERENCES schools (id) ON DELETE CASCADE,
    CONSTRAINT fk_classes_tenant FOREIGN KEY (tenant_id) REFERENCES tenants (id)
);

-- ========== SUBJECTS ==========
CREATE TABLE subjects
(
    id          VARCHAR(100) PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    school_id   VARCHAR(100) NOT NULL,
    tenant_id   VARCHAR(100) NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by  VARCHAR(100),
    updated_by  VARCHAR(100),
    deleted     BOOLEAN   DEFAULT FALSE,
    CONSTRAINT fk_subjects_school FOREIGN KEY (school_id) REFERENCES schools (id) ON DELETE CASCADE,
    CONSTRAINT fk_subjects_tenant FOREIGN KEY (tenant_id) REFERENCES tenants (id)
);

-- ========== CLASS_SUBJECTS ==========
CREATE TABLE class_subjects
(
    id         VARCHAR(100) PRIMARY KEY,
    class_id   VARCHAR(100) NOT NULL,
    subject_id VARCHAR(100) NOT NULL,
    teacher_id VARCHAR(100), -- FK to users (teacher)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    CONSTRAINT fk_class_subject_class FOREIGN KEY (class_id) REFERENCES classes (id) ON DELETE CASCADE,
    CONSTRAINT fk_class_subject_subject FOREIGN KEY (subject_id) REFERENCES subjects (id) ON DELETE CASCADE,
    CONSTRAINT fk_class_subject_teacher FOREIGN KEY (teacher_id) REFERENCES users (id)
);

-- ========== ASSIGNMENTS ==========
CREATE TABLE assignments
(
    id               VARCHAR(100) PRIMARY KEY,
    title            VARCHAR(255) NOT NULL,
    description      TEXT,
    class_subject_id VARCHAR(100) NOT NULL,
    due_date         TIMESTAMP,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by       VARCHAR(100),
    updated_by       VARCHAR(100),
    deleted          BOOLEAN   DEFAULT FALSE,
    CONSTRAINT fk_assignments_class_subject FOREIGN KEY (class_subject_id) REFERENCES class_subjects (id) ON DELETE CASCADE
);

-- ========== EXAMS ==========
CREATE TABLE exams
(
    id               VARCHAR(100) PRIMARY KEY,
    title            VARCHAR(255) NOT NULL,
    description      TEXT,
    class_subject_id VARCHAR(100) NOT NULL,
    scheduled_at     TIMESTAMP,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by       VARCHAR(100),
    updated_by       VARCHAR(100),
    deleted          BOOLEAN   DEFAULT FALSE,
    CONSTRAINT fk_exams_class_subject FOREIGN KEY (class_subject_id) REFERENCES class_subjects (id) ON DELETE CASCADE
);

-- ========== ATTENDANCES ==========
CREATE TABLE attendances
(
    id          VARCHAR(100) PRIMARY KEY,
    class_id    VARCHAR(100) NOT NULL,
    date        DATE         NOT NULL,
    student_id  VARCHAR(100) NOT NULL, -- FK to users
    present     BOOLEAN      NOT NULL,
    recorded_by VARCHAR(100),          -- FK to users
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_attendance_class FOREIGN KEY (class_id) REFERENCES classes (id) ON DELETE CASCADE,
    CONSTRAINT fk_attendance_student FOREIGN KEY (student_id) REFERENCES users (id),
    CONSTRAINT fk_attendance_recorded_by FOREIGN KEY (recorded_by) REFERENCES users (id)
);

-- ========== RESOURCES ==========
CREATE TABLE resources
(
    id               VARCHAR(100) PRIMARY KEY,
    title            VARCHAR(255) NOT NULL,
    file_url         VARCHAR(500),
    type             VARCHAR(50), -- e.g., PDF, DOC, VIDEO, etc.
    class_subject_id VARCHAR(100),
    uploaded_by      VARCHAR(100),
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted          BOOLEAN   DEFAULT FALSE,
    CONSTRAINT fk_resources_class_subject FOREIGN KEY (class_subject_id) REFERENCES class_subjects (id) ON DELETE CASCADE,
    CONSTRAINT fk_resources_uploaded_by FOREIGN KEY (uploaded_by) REFERENCES users (id)
);
