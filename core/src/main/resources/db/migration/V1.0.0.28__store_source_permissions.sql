INSERT INTO auth_permission (system_name, display_name, description, subject, action, created_by, created_date, deleted_by, deleted_date, updated_by, updated_date) VALUES
('STORE_SOURCE_CREATE', 'Store Source (Create)', 'Ability to create Store Source', 'STORE_SOURCE', 'CREATE', 1, NOW(), NULL, NULL, 1, NOW()),
('STORE_SOURCE_READ', 'Store Source (Read)', 'Ability to read Store Source', 'STORE_SOURCE', 'READ', 1, NOW(), NULL, NULL, 1, NOW()),
('STORE_SOURCE_UPDATE', 'Store Source (Update)', 'Ability to update Store Source', 'STORE_SOURCE', 'UPDATE', 1, NOW(), NULL, NULL, 1, NOW()),
('STORE_SOURCE_DELETE', 'Store Source (Delete)', 'Ability to delete Store Source', 'STORE_SOURCE', 'DELETE', 1, NOW(), NULL, NULL, 1, NOW());

INSERT INTO auth_role_auth_permission (auth_role_id, auth_permission_id)
  SELECT 1, p.auth_permission_id
  FROM auth_permission p
  WHERE p.subject = 'STORE_SOURCE';