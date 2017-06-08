INSERT INTO auth_permission (id, system_name, display_name, description) VALUES
  (NEXTVAL('seq_auth_permission_id'), 'STORE_SURVEYS_CREATE', 'Store Surveys (Create)',
   'Ability to create Store Surveys'),
  (NEXTVAL('seq_auth_permission_id'), 'STORE_SURVEYS_READ', 'Store Surveys (Read)', 'Ability to read Store Surveys'),
  (NEXTVAL('seq_auth_permission_id'), 'STORE_SURVEYS_UPDATE', 'Store Surveys (Update)',
   'Ability to update Store Surveys'),
  (NEXTVAL('seq_auth_permission_id'), 'STORE_SURVEYS_DELETE', 'Store Surveys (Delete)',
   'Ability to delete Store Surveys');