INSERT INTO user_profile (email, first_name, last_name, created_by, created_date, updated_by, updated_date) VALUES
  ('system.administrator@mtnra.com', 'System', 'Administrator', 1, NOW(), 1, NOW());

INSERT INTO api_client (name, client_id, client_secret) VALUES
  ('MTN-RS Administration', 'YVQTbbTwp4ZIgmC9LtpuoG5gx0i8lUaR', '3nKggQwEUQkWTx-bzkySJ_dQ66ur2W9gxV9Hi3ANM7qWphmJH8dGTOF2rXw-LPg9');

INSERT INTO auth_permission (system_name, display_name, description, subject, action, created_by, created_date, deleted_by, deleted_date, updated_by, updated_date) VALUES
('PERMISSIONS_READ', 'Permissions (Read)', 'Ability to read Permissions', 'PERMISSIONS', 'READ', 1, NOW(), NULL, NULL, 1, NOW()),
('PERMISSIONS_UPDATE', 'Permissions (Update)', 'Ability to update Permissions', 'PERMISSIONS', 'UPDATE', 1, NOW(), NULL, NULL, 1, NOW()),
('ROLES_CREATE', 'Roles (Create)', 'Ability to create Roles', 'ROLES', 'CREATE', 1, NOW(), NULL, NULL, 1, NOW()),
('ROLES_READ', 'Roles (Read)', 'Ability to read Roles', 'ROLES', 'READ', 1, NOW(), NULL, NULL, 1, NOW()),
('ROLES_UPDATE', 'Roles (Update)', 'Ability to update Roles', 'ROLES', 'UPDATE', 1, NOW(), NULL, NULL, 1, NOW()),
('ROLES_DELETE', 'Roles (Delete)', 'Ability to delete Roles', 'ROLES', 'DELETE', 1, NOW(), NULL, NULL, 1, NOW()),
('GROUPS_CREATE', 'Groups (Create)', 'Ability to create Groups', 'GROUPS', 'CREATE', 1, NOW(), NULL, NULL, 1, NOW()),
('GROUPS_READ', 'Groups (Read)', 'Ability to read Groups', 'GROUPS', 'READ', 1, NOW(), NULL, NULL, 1, NOW()),
('GROUPS_UPDATE', 'Groups (Update)', 'Ability to update Groups', 'GROUPS', 'UPDATE', 1, NOW(), NULL, NULL, 1, NOW()),
('GROUPS_DELETE', 'Groups (Delete)', 'Ability to delete Groups', 'GROUPS', 'DELETE', 1, NOW(), NULL, NULL, 1, NOW()),
('SEARCH_READ', 'Search (Read)', 'Ability to read from Search API', 'SEARCH', 'READ', 1, NOW(), NULL, NULL, 1, NOW()),
('SHOPPING_CENTERS_CREATE', 'Shopping Centers (Create)', 'Ability to create Shopping Centers', 'SHOPPING_CENTER', 'CREATE', 1, NOW(), NULL, NULL, 1, NOW()),
('SHOPPING_CENTERS_READ', 'Shopping Centers (Read)', 'Ability to read Shopping Centers', 'SHOPPING_CENTER', 'READ', 1, NOW(), NULL, NULL, 1, NOW()),
('SHOPPING_CENTERS_UPDATE', 'Shopping Centers (Update)', 'Ability to update Shopping Centers', 'SHOPPING_CENTER', 'UPDATE', 1, NOW(), NULL, NULL, 1, NOW()),
('SHOPPING_CENTERS_DELETE', 'Shopping Centers (Delete)', 'Ability to delete Shopping Centers', 'SHOPPING_CENTER', 'DELETE', 1, NOW(), NULL, NULL, 1, NOW()),
('SHOPPING_CENTER_SURVEYS_CREATE', 'Shopping Center Surveys (Create)', 'Ability to create Shopping Center Surveys', 'SHOPPING_CENTER_SURVEY', 'CREATE', 1, NOW(), NULL, NULL, 1, NOW()),
('SHOPPING_CENTER_SURVEYS_READ', 'Shopping Center Surveys (Read)', 'Ability to read Shopping Center Surveys', 'SHOPPING_CENTER_SURVEY', 'READ', 1, NOW(), NULL, NULL, 1, NOW()),
('SHOPPING_CENTER_SURVEYS_UPDATE', 'Shopping Center Surveys (Update)', 'Ability to update Shopping Center Surveys', 'SHOPPING_CENTER_SURVEY', 'UPDATE', 1, NOW(), NULL, NULL, 1, NOW()),
('SHOPPING_CENTER_SURVEYS_DELETE', 'Shopping Center Surveys (Delete)', 'Ability to delete Shopping Center Surveys', 'SHOPPING_CENTER_SURVEY', 'DELETE', 1, NOW(), NULL, NULL, 1, NOW()),
('SITES_CREATE', 'Sites (Create)', 'Ability to create Sites', 'SITES', 'CREATE', 1, NOW(), NULL, NULL, 1, NOW()),
('SITES_READ', 'Sites (Read)', 'Ability to read Sites', 'SITES', 'READ', 1, NOW(), NULL, NULL, 1, NOW()),
('SITES_UPDATE', 'Sites (Update)', 'Ability to update Sites', 'SITES', 'UPDATE', 1, NOW(), NULL, NULL, 1, NOW()),
('SITES_DELETE', 'Sites (Delete)', 'Ability to delete Sites', 'SITES', 'DELETE', 1, NOW(), NULL, NULL, 1, NOW()),
('STORES_CREATE', 'Stores (Create)', 'Ability to create Stores', 'STORE', 'CREATE', 1, NOW(), NULL, NULL, 1, NOW()),
('STORES_READ', 'Stores (Read)', 'Ability to read Stores', 'STORE', 'READ', 1, NOW(), NULL, NULL, 1, NOW()),
('STORES_UPDATE', 'Stores (Update)', 'Ability to update Stores', 'STORE', 'UPDATE', 1, NOW(), NULL, NULL, 1, NOW()),
('STORES_DELETE', 'Stores (Delete)', 'Ability to delete Stores', 'STORE', 'DELETE', 1, NOW(), NULL, NULL, 1, NOW()),
('USERS_CREATE', 'Users (Create)', 'Ability to create Users', 'USERS', 'CREATE', 1, NOW(), NULL, NULL, 1, NOW()),
('USERS_READ', 'Users (Read)', 'Ability to read Users', 'USERS', 'READ', 1, NOW(), NULL, NULL, 1, NOW()),
('USERS_UPDATE', 'Users (Update)', 'Ability to update Users', 'USERS', 'UPDATE', 1, NOW(), NULL, NULL, 1, NOW()),
('USERS_DELETE', 'Users (Delete)', 'Ability to delete Users', 'USERS', 'DELETE', 1, NOW(), NULL, NULL, 1, NOW()),
('STORE_SURVEYS_CREATE', 'Store Surveys (Create)', 'Ability to create Store Surveys', 'STORE_SURVEY', 'CREATE', 1, NOW(), NULL, NULL, 1, NOW()),
('STORE_SURVEYS_READ', 'Store Surveys (Read)', 'Ability to read Store Surveys', 'STORE_SURVEY', 'READ', 1, NOW(), NULL, NULL, 1, NOW()),
('STORE_SURVEYS_UPDATE', 'Store Surveys (Update)', 'Ability to update Store Surveys', 'STORE_SURVEY', 'UPDATE', 1, NOW(), NULL, NULL, 1, NOW()),
('STORE_SURVEYS_DELETE', 'Store Surveys (Delete)', 'Ability to delete Store Surveys', 'STORE_SURVEY', 'DELETE', 1, NOW(), NULL, NULL, 1, NOW()),
('COMPANY_CREATE', 'Companies (Create)', 'Ability to create Companies', 'COMPANY', 'CREATE', 1, NOW(), NULL, NULL, 1, NOW()),
('COMPANY_READ', 'Companies (Read)', 'Ability to read Companies', 'COMPANY', 'READ', 1, NOW(), NULL, NULL, 1, NOW()),
('COMPANY_UPDATE', 'Companies (Update)', 'Ability to update Companies', 'COMPANY', 'UPDATE', 1, NOW(), NULL, NULL, 1, NOW()),
('COMPANY_DELETE', 'Companies (Delete)', 'Ability to delete Companies', 'COMPANY', 'DELETE', 1, NOW(), NULL, NULL, 1, NOW()),
('SHOPPING_CENTER_CASINGS_CREATE', 'Shopping Center Casings (Create)', 'Ability to create Shopping Center Casings', 'SHOPPING_CENTER_CASING', 'CREATE', 1, NOW(), NULL, NULL, 1, NOW()),
('SHOPPING_CENTER_CASINGS_READ', 'Shopping Center Casings (Read)', 'Ability to read Shopping Center Casings', 'SHOPPING_CENTER_CASING', 'READ', 1, NOW(), NULL, NULL, 1, NOW()),
('SHOPPING_CENTER_CASINGS_UPDATE', 'Shopping Center Casings (Update)', 'Ability to update Shopping Center Casings', 'SHOPPING_CENTER_CASING', 'UPDATE', 1, NOW(), NULL, NULL, 1, NOW()),
('SHOPPING_CENTER_CASINGS_DELETE', 'Shopping Center Casings (Delete)', 'Ability to delete Shopping Center Casings', 'SHOPPING_CENTER_CASING', 'DELETE', 1, NOW(), NULL, NULL, 1, NOW()),
('STORE_VOLUMES_CREATE', 'Store Volumes (Create)', 'Ability to create Store Volumes', 'STORE_VOLUME', 'CREATE', 1, NOW(), NULL, NULL, 1, NOW()),
('STORE_VOLUMES_READ', 'Store Volumes (Read)', 'Ability to read Store Volumes', 'STORE_VOLUME', 'READ', 1, NOW(), NULL, NULL, 1, NOW()),
('STORE_VOLUMES_UPDATE', 'Store Volumes (Update)', 'Ability to update Store Volumes', 'STORE_VOLUME', 'UPDATE', 1, NOW(), NULL, NULL, 1, NOW()),
('STORE_VOLUMES_DELETE', 'Store Volumes (Delete)', 'Ability to delete Store Volumes', 'STORE_VOLUME', 'DELETE', 1, NOW(), NULL, NULL, 1, NOW()),
('STORE_CASINGS_CREATE', 'Store Casings (Create)', 'Ability to create Store Casings', 'STORE_CASING', 'CREATE', 1, NOW(), NULL, NULL, 1, NOW()),
('STORE_CASINGS_READ', 'Store Casings (Read)', 'Ability to read Store Casings', 'STORE_CASING', 'READ', 1, NOW(), NULL, NULL, 1, NOW()),
('STORE_CASINGS_UPDATE', 'Store Casings (Update)', 'Ability to update Store Casings', 'STORE_CASING', 'UPDATE', 1, NOW(), NULL, NULL, 1, NOW()),
('STORE_CASINGS_DELETE', 'Store Casings (Delete)', 'Ability to delete Store Casings', 'STORE_CASING', 'DELETE', 1, NOW(), NULL, NULL, 1, NOW()),
('STORE_MODELS_CREATE', 'Store Models (Create)', 'Ability to create Store Models', 'STORE_MODEL', 'CREATE', 1, NOW(), NULL, NULL, 1, NOW()),
('STORE_MODELS_READ', 'Store Models (Read)', 'Ability to read Store Models', 'STORE_MODEL', 'READ', 1, NOW(), NULL, NULL, 1, NOW()),
('STORE_MODELS_UPDATE', 'Store Models (Update)', 'Ability to update Store Models', 'STORE_MODEL', 'UPDATE', 1, NOW(), NULL, NULL, 1, NOW()),
('STORE_MODELS_DELETE', 'Store Models (Delete)', 'Ability to delete Store Models', 'STORE_MODEL', 'DELETE', 1, NOW(), NULL, NULL, 1, NOW()),
('PROJECTS_CREATE', 'Projects (Create)', 'Ability to create Projects', 'PROJECTS', 'CREATE', 1, NOW(), NULL, NULL, 1, NOW()),
('PROJECTS_READ', 'Projects (Read)', 'Ability to read Projects', 'PROJECTS', 'READ', 1, NOW(), NULL, NULL, 1, NOW()),
('PROJECTS_UPDATE', 'Projects (Update)', 'Ability to update Projects', 'PROJECTS', 'UPDATE', 1, NOW(), NULL, NULL, 1, NOW()),
('PROJECTS_DELETE', 'Projects (Delete)', 'Ability to delete Projects', 'PROJECTS', 'DELETE', 1, NOW(), NULL, NULL, 1, NOW()),
('INTERACTIONS_CREATE', 'Interactions (Create)', 'Ability to create Interactions', 'INTERACTIONS', 'CREATE', 1, NOW(), NULL, NULL, 1, NOW()),
('INTERACTIONS_READ', 'Interactions (Read)', 'Ability to read Interactions', 'INTERACTIONS', 'READ', 1, NOW(), NULL, NULL, 1, NOW()),
('INTERACTIONS_UPDATE', 'Interactions (Update)', 'Ability to update Interactions', 'INTERACTIONS', 'UPDATE', 1, NOW(), NULL, NULL, 1, NOW()),
('INTERACTIONS_DELETE', 'Interactions (Delete)', 'Ability to delete Interactions', 'INTERACTIONS', 'DELETE', 1, NOW(), NULL, NULL, 1, NOW());

INSERT INTO auth_role (display_name, description, created_by, created_date, updated_by, updated_date) VALUES
  ('System Administrator', 'All Powerful Admin', 1, NOW(), 1, NOW());

INSERT INTO auth_role_auth_permission (auth_role_id, auth_permission_id)
  SELECT 1, auth_permission_id
  FROM auth_permission;

UPDATE user_profile SET auth_role_id = 1;