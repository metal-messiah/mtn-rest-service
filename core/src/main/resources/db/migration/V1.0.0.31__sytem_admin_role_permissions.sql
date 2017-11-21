INSERT INTO auth_role (auth_role_id, display_name, description, created_by, created_date, updated_by, updated_date) VALUES
  (NEXTVAL('seq_auth_role_id'), 'System Administrator', 'All Powerful Admin', 1, NOW(), 1, NOW());

INSERT INTO auth_role_auth_permission (auth_role_id, auth_permission_id)
SELECT 1, auth_permission_id
FROM auth_permission;

UPDATE user_profile SET auth_role_id = 1;