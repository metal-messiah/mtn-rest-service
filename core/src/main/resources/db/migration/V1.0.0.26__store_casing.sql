CREATE SEQUENCE seq_store_casing_id;

CREATE TABLE store_casing (
  store_casing_id                 INT       NOT NULL,
  store_id                        INT       NOT NULL,
  store_casing_note               TEXT,
  store_status                    VARCHAR(256),
  condition_ceiling               VARCHAR(64),
  condition_checkstands           VARCHAR(64),
  condition_floors                VARCHAR(64),
  condition_frozen_refrigerated   VARCHAR(64),
  condition_shelving_gondolas     VARCHAR(64),
  condition_walls                 VARCHAR(64),
  fuel_gallons_weekly             INT,
  pharmacy_scripts_weekly         INT,
  pharmacy_avg_dollars_per_script FLOAT,
  pharmacy_pharmacist_count       INT,
  pharmacy_technician_count       INT,
  volume_grocery                  INT,
  volume_percent_grocery          FLOAT,
  volume_meat                     INT,
  volume_percent_meat             FLOAT,
  volume_non_food                 INT,
  volume_percent_non_food         FLOAT,
  volume_other                    INT,
  volume_percent_other            FLOAT,
  volume_produce                  INT,
  volume_percent_produce          FLOAT,
  volume_plus_minus               INT,
  volume_note                     TEXT,
  volume_total                    INT,
  volume_confidence               VARCHAR(64),
  version                         INT       NOT NULL DEFAULT 1,
  legacy_casing_id                INT,
  created_by                      INT       NOT NULL,
  created_date                    TIMESTAMP NOT NULL DEFAULT NOW(),
  deleted_by                      INT,
  deleted_date                    TIMESTAMP,
  updated_by                      INT       NOT NULL,
  updated_date                    TIMESTAMP NOT NULL DEFAULT NOW(),
  CONSTRAINT pk_store_casing PRIMARY KEY (store_casing_id),
  CONSTRAINT fk_store_casing_store FOREIGN KEY (store_id) REFERENCES store (store_id),
  CONSTRAINT fk_store_casing_created_by FOREIGN KEY (created_by) REFERENCES user_profile (user_profile_id),
  CONSTRAINT fk_store_casing_deleted_by FOREIGN KEY (deleted_by) REFERENCES user_profile (user_profile_id),
  CONSTRAINT fk_store_casing_updated_by FOREIGN KEY (updated_by) REFERENCES user_profile (user_profile_id)
);

CREATE INDEX idx_store_casing_store
  ON store_casing (store_id);

INSERT INTO auth_permission (auth_permission_id, system_name, display_name, description) VALUES
  (NEXTVAL('seq_auth_permission_id'), 'STORE_CASINGS_CREATE', 'Store Casings (Create)',
   'Ability to create Store Casings'),
  (NEXTVAL('seq_auth_permission_id'), 'STORE_CASINGS_READ', 'Store Casings (Read)',
   'Ability to read Store Casings'),
  (NEXTVAL('seq_auth_permission_id'), 'STORE_CASINGS_UPDATE', 'Store Casings (Update)',
   'Ability to update Store Casings'),
  (NEXTVAL('seq_auth_permission_id'), 'STORE_CASINGS_DELETE', 'Store Casings (Delete)',
   'Ability to delete Store Casings');