ALTER TABLE shopping_center
  ADD COLUMN legacy_location_id INT;
ALTER TABLE shopping_center
  DROP COLUMN legacy_casing_id;
ALTER TABLE shopping_center
  RENAME id TO shopping_center_id;
ALTER TABLE shopping_center
  RENAME name TO shopping_center_name;

ALTER TABLE store
  ADD COLUMN store_number VARCHAR(64);
ALTER TABLE store
  ADD COLUMN legacy_location_id INT;
ALTER TABLE store
  RENAME type TO store_type;
ALTER TABLE store
  RENAME company_id TO parent_company_id;

ALTER TABLE shopping_center_survey
  ADD COLUMN shopping_center_type VARCHAR(64);
ALTER TABLE shopping_center_survey
  ADD COLUMN shopping_center_survey_note TEXT;
ALTER TABLE shopping_center_survey
  ADD COLUMN flow_has_landscaping BOOLEAN DEFAULT FALSE;
ALTER TABLE shopping_center_survey
  RENAME has_speed_bumps TO flow_has_speed_bumps;
ALTER TABLE shopping_center_survey
  ADD COLUMN flow_has_stop_signs BOOLEAN DEFAULT FALSE;
ALTER TABLE shopping_center_survey
  ADD COLUMN flow_has_one_way_aisles BOOLEAN DEFAULT FALSE;
ALTER TABLE shopping_center_survey
  RENAME has_angled_spaces TO parking_has_angled_spaces;
ALTER TABLE shopping_center_survey
  RENAME has_parking_hog TO parking_has_parking_hog;
ALTER TABLE shopping_center_survey
  ADD COLUMN parking_is_poorly_lit BOOLEAN DEFAULT FALSE;
ALTER TABLE shopping_center_survey
  ADD COLUMN parking_space_count INT;
ALTER TABLE shopping_center_survey
  ADD COLUMN tenant_occupied_count INT;
ALTER TABLE shopping_center_survey
  ADD COLUMN tenant_vacant_count INT;
ALTER TABLE shopping_center_survey
  ADD COLUMN sq_ft_percent_occupied FLOAT;
ALTER TABLE shopping_center_survey
  ADD COLUMN legacy_casing_id INT;
ALTER TABLE shopping_center_survey
  ADD COLUMN version INT;

ALTER TABLE shopping_center_tenant
  RENAME name TO tenant_name;
ALTER TABLE shopping_center_tenant
  DROP type;
ALTER TABLE shopping_center_tenant
  ADD COLUMN is_anchor BOOLEAN DEFAULT FALSE;
ALTER TABLE shopping_center_tenant
  ADD COLUMN created_by INT;
ALTER TABLE shopping_center_tenant
  ALTER COLUMN created_by SET NOT NULL;
ALTER TABLE shopping_center_tenant
  ADD CONSTRAINT fk_shopping_center_tenant_created_by FOREIGN KEY (created_by) REFERENCES user_profile (id);
ALTER TABLE shopping_center_tenant
  ADD COLUMN created_date TIMESTAMP;
ALTER TABLE shopping_center_tenant
  ALTER COLUMN created_date SET NOT NULL;
ALTER TABLE shopping_center_tenant
  ADD COLUMN deleted_by INT;
ALTER TABLE shopping_center_tenant
  ADD CONSTRAINT fk_shopping_center_tenant_deleted_by FOREIGN KEY (deleted_by) REFERENCES user_profile (id);
ALTER TABLE shopping_center_tenant
  ADD COLUMN deleted_date TIMESTAMP;
ALTER TABLE shopping_center_tenant
  ADD COLUMN updated_by INT;
ALTER TABLE shopping_center_tenant
  ALTER COLUMN updated_by SET NOT NULL;
ALTER TABLE shopping_center_tenant
  ADD CONSTRAINT fk_shopping_center_tenant_updated_by FOREIGN KEY (updated_by) REFERENCES user_profile (id);
ALTER TABLE shopping_center_tenant
  ADD COLUMN legacy_casing_id INT;
ALTER TABLE shopping_center_tenant
  ADD COLUMN version INT;

ALTER TABLE shopping_center_access
  ADD COLUMN created_by INT;
ALTER TABLE shopping_center_access
  ALTER COLUMN created_by SET NOT NULL;
ALTER TABLE shopping_center_access
  ADD CONSTRAINT fk_shopping_center_access_created_by FOREIGN KEY (created_by) REFERENCES user_profile (id);
ALTER TABLE shopping_center_access
  ADD COLUMN created_date TIMESTAMP;
ALTER TABLE shopping_center_access
  ALTER COLUMN created_date SET NOT NULL;
ALTER TABLE shopping_center_access
  ADD COLUMN deleted_by INT;
ALTER TABLE shopping_center_access
  ADD CONSTRAINT fk_shopping_center_access_deleted_by FOREIGN KEY (deleted_by) REFERENCES user_profile (id);
ALTER TABLE shopping_center_access
  ADD COLUMN deleted_date TIMESTAMP;
ALTER TABLE shopping_center_access
  ADD COLUMN updated_by INT;
ALTER TABLE shopping_center_access
  ALTER COLUMN updated_by SET NOT NULL;
ALTER TABLE shopping_center_access
  ADD CONSTRAINT fk_shopping_center_access_updated_by FOREIGN KEY (updated_by) REFERENCES user_profile (id);
ALTER TABLE shopping_center_access
  ADD COLUMN legacy_casing_id INT;
ALTER TABLE shopping_center_access
  ADD COLUMN version INT;