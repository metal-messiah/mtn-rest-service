ALTER TABLE api_client
  RENAME id TO api_client_id;
ALTER TABLE auth_group
  RENAME id TO auth_group_id;
ALTER TABLE auth_permission
  RENAME id TO auth_permission_id;
ALTER TABLE auth_role
  RENAME id TO auth_role_id;
ALTER TABLE company
  RENAME id TO company_id;
ALTER TABLE shopping_center_access
  RENAME id TO shopping_center_access_id;
ALTER TABLE shopping_center_survey
  RENAME id TO shopping_center_survey_id;
ALTER TABLE shopping_center_tenant
  RENAME id TO shopping_center_tenant_id;
ALTER TABLE site
  RENAME id TO site_id;
ALTER TABLE store
  RENAME id TO store_id;
ALTER TABLE store_survey
  RENAME id TO store_survey_id;
ALTER TABLE user_profile
  RENAME id TO user_profile_id;