-- ========== TENANTS ==========
ALTER TABLE attendances
    ADD COLUMN created_by VARCHAR(100),
    ADD COLUMN updated_by VARCHAR(100),
    ADD COLUMN deleted BOOLEAN DEFAULT FALSE;

ALTER TABLE class_subjects
    ADD COLUMN deleted BOOLEAN DEFAULT FALSE;

ALTER TABLE resources
    ADD COLUMN created_by VARCHAR(100),
    ADD COLUMN updated_by VARCHAR(100);

