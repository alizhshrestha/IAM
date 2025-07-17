-- === STUDENTS for Grade 1 in Himalaya High School ===
# | Student     | User ID        | Class         | School               | Tenant          |
# | ----------- | -------------- | ------------- | -------------------- | --------------- |
# | Student One | `stu-hima-1-1` | `Grade 1 - A` | Himalaya High School | Himalaya EdTech |
# | Student Two | `stu-hima-1-2` | `Grade 1 - A` | Himalaya High School | Himalaya EdTech |
# | ...         | ...            | ...           | ...                  | ...             |

INSERT INTO students (
    id, user_id, school_id, class_id, enrollment_no,
    enrollment_date, guardian_name, guardian_phone,
    address, tenant_id, created_by, updated_by
) VALUES
-- Student One
('stu-db-1-1', 'stu-hima-1-1', 'bfb6eb69-f90a-4536-9c32-9e4f8b9b9ef6', 'hima-class-01', 'ENR-1001',
 current_date - INTERVAL 2 year, 'Hari Bahadur', '9800000001', 'Lalitpur', 'f8a231cb-3b45-4d67-87b0-08df2d3e9c11', 'admin', 'admin'),

-- Student Two
('stu-db-1-2', 'stu-hima-1-2', 'bfb6eb69-f90a-4536-9c32-9e4f8b9b9ef6', 'hima-class-01', 'ENR-1002',
 current_date - INTERVAL 2 year, 'Sita Devi', '9800000002', 'Bhaktapur', 'f8a231cb-3b45-4d67-87b0-08df2d3e9c11', 'admin', 'admin'),

-- Student Three
('stu-db-1-3', 'stu-hima-1-3', 'bfb6eb69-f90a-4536-9c32-9e4f8b9b9ef6', 'hima-class-01', 'ENR-1003',
 current_date - INTERVAL 2 year, 'Ramesh Kumar', '9800000003', 'Kathmandu', 'f8a231cb-3b45-4d67-87b0-08df2d3e9c11', 'admin', 'admin'),

-- Student Four
('stu-db-1-4', 'stu-hima-1-4', 'bfb6eb69-f90a-4536-9c32-9e4f8b9b9ef6', 'hima-class-01', 'ENR-1004',
 current_date - INTERVAL 2 year, 'Gita Kumari', '9800000004', 'Lalitpur', 'f8a231cb-3b45-4d67-87b0-08df2d3e9c11', 'admin', 'admin'),

-- Student Five
('stu-db-1-5', 'stu-hima-1-5', 'bfb6eb69-f90a-4536-9c32-9e4f8b9b9ef6', 'hima-class-01', 'ENR-1005',
 current_date - INTERVAL 2 year, 'Shyam Lal', '9800000005', 'Pokhara', 'f8a231cb-3b45-4d67-87b0-08df2d3e9c11', 'admin', 'admin');
