INSERT INTO auth_permission (id, system_name, display_name, description) VALUES
  (NEXTVAL('seq_auth_permission_id'), 'PERMISSIONS_READ', 'Permissions (Read)', 'Ability to read Permissions'),
  (NEXTVAL('seq_auth_permission_id'), 'PERMISSIONS_UPDATE', 'Permissions (Update)', 'Ability to update Permissions'),
  (NEXTVAL('seq_auth_permission_id'), 'ROLES_CREATE', 'Roles (Create)', 'Ability to create Roles'),
  (NEXTVAL('seq_auth_permission_id'), 'ROLES_READ', 'Roles (Read)', 'Ability to read Roles'),
  (NEXTVAL('seq_auth_permission_id'), 'ROLES_UPDATE', 'Roles (Update)', 'Ability to update Roles'),
  (NEXTVAL('seq_auth_permission_id'), 'ROLES_DELETE', 'Roles (Delete)', 'Ability to delete Roles'),
  (NEXTVAL('seq_auth_permission_id'), 'GROUPS_CREATE', 'Groups (Create)', 'Ability to create Groups'),
  (NEXTVAL('seq_auth_permission_id'), 'GROUPS_READ', 'Groups (Read)', 'Ability to read Groups'),
  (NEXTVAL('seq_auth_permission_id'), 'GROUPS_UPDATE', 'Groups (Update)', 'Ability to update Groups'),
  (NEXTVAL('seq_auth_permission_id'), 'GROUPS_DELETE', 'Groups (Delete)', 'Ability to delete Groups'),
  (NEXTVAL('seq_auth_permission_id'), 'SEARCH_READ', 'Search (Read)', 'Ability to read from Search API'),
  (NEXTVAL(
       'seq_auth_permission_id'), 'SHOPPING_CENTERS_CREATE', 'Shopping Centers (Create)', 'Ability to create Shopping Centers'),
  (NEXTVAL(
       'seq_auth_permission_id'), 'SHOPPING_CENTERS_READ', 'Shopping Centers (Read)', 'Ability to read Shopping Centers'),
  (NEXTVAL(
       'seq_auth_permission_id'), 'SHOPPING_CENTERS_UPDATE', 'Shopping Centers (Update)', 'Ability to update Shopping Centers'),
  (NEXTVAL(
       'seq_auth_permission_id'), 'SHOPPING_CENTERS_DELETE', 'Shopping Centers (Delete)', 'Ability to delete Shopping Centers'),
  (NEXTVAL(
       'seq_auth_permission_id'), 'SHOPPING_CENTER_SURVEYS_CREATE', 'Shopping Center Surveys (Create)', 'Ability to create Shopping Center Surveys'),
  (NEXTVAL(
       'seq_auth_permission_id'), 'SHOPPING_CENTER_SURVEYS_READ', 'Shopping Center Surveys (Read)', 'Ability to read Shopping Center Surveys'),
  (NEXTVAL(
       'seq_auth_permission_id'), 'SHOPPING_CENTER_SURVEYS_UPDATE', 'Shopping Center Surveys (Update)', 'Ability to update Shopping Center Surveys'),
  (NEXTVAL(
       'seq_auth_permission_id'), 'SHOPPING_CENTER_SURVEYS_DELETE', 'Shopping Center Surveys (Delete)', 'Ability to delete Shopping Center Surveys'),
  (NEXTVAL('seq_auth_permission_id'), 'SITES_CREATE', 'Sites (Create)', 'Ability to create Sites'),
  (NEXTVAL('seq_auth_permission_id'), 'SITES_READ', 'Sites (Read)', 'Ability to read Sites'),
  (NEXTVAL('seq_auth_permission_id'), 'SITES_UPDATE', 'Sites (Update)', 'Ability to update Sites'),
  (NEXTVAL('seq_auth_permission_id'), 'SITES_DELETE', 'Sites (Delete)', 'Ability to delete Sites'),
  (NEXTVAL('seq_auth_permission_id'), 'STORES_CREATE', 'Stores (Create)', 'Ability to create Stores'),
  (NEXTVAL('seq_auth_permission_id'), 'STORES_READ', 'Stores (Read)', 'Ability to read Stores'),
  (NEXTVAL('seq_auth_permission_id'), 'STORES_UPDATE', 'Stores (Update)', 'Ability to update Stores'),
  (NEXTVAL('seq_auth_permission_id'), 'STORES_DELETE', 'Stores (Delete)', 'Ability to delete Stores'),
  (NEXTVAL('seq_auth_permission_id'), 'USERS_CREATE', 'Users (Create)', 'Ability to create Users'),
  (NEXTVAL('seq_auth_permission_id'), 'USERS_READ', 'Users (Read)', 'Ability to read Users'),
  (NEXTVAL('seq_auth_permission_id'), 'USERS_UPDATE', 'Users (Update)', 'Ability to update Users'),
  (NEXTVAL('seq_auth_permission_id'), 'USERS_DELETE', 'Users (Delete)', 'Ability to delete Users');
