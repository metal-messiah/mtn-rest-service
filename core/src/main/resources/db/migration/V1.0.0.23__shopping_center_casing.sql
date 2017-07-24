CREATE SEQUENCE seq_shopping_center_casing_id;

CREATE TABLE shopping_center_casing (
  shopping_center_casing_id   INT       NOT NULL,
  shopping_center_id          INT       NOT NULL,
  shopping_center_casing_note TEXT,
  rating_parking_lot          VARCHAR(64),
  rating_buildings            VARCHAR(64),
  rating_lighting             VARCHAR(64),
  rating_synergy              VARCHAR(64),
  version                     INT       NOT NULL DEFAULT 1,
  legacy_casing_id            INT,
  created_by                  INT       NOT NULL,
  created_date                TIMESTAMP NOT NULL DEFAULT NOW(),
  deleted_by                  INT,
  deleted_date                TIMESTAMP,
  updated_by                  INT       NOT NULL,
  updated_date                TIMESTAMP NOT NULL DEFAULT NOW(),
  CONSTRAINT pk_shopping_center_casing PRIMARY KEY (shopping_center_casing_id),
  CONSTRAINT fk_shopping_center_casing_shopping_center FOREIGN KEY (shopping_center_id) REFERENCES shopping_center (shopping_center_id),
  CONSTRAINT fk_shopping_center_casing_created_by FOREIGN KEY (created_by) REFERENCES user_profile (user_profile_id),
  CONSTRAINT fk_shopping_center_casing_deleted_by FOREIGN KEY (deleted_by) REFERENCES user_profile (user_profile_id),
  CONSTRAINT fk_shopping_center_casing_updated_by FOREIGN KEY (updated_by) REFERENCES user_profile (user_profile_id)
);

CREATE INDEX idx_shopping_center_casing_shopping_center
  ON shopping_center_casing (shopping_center_id);

INSERT INTO auth_permission (auth_permission_id, system_name, display_name, description) VALUES
  (NEXTVAL('seq_auth_permission_id'), 'SHOPPING_CENTER_CASINGS_CREATE', 'Shopping Center Casings (Create)',
   'Ability to create Shopping Center Casings'),
  (NEXTVAL('seq_auth_permission_id'), 'SHOPPING_CENTER_CASINGS_READ', 'Shopping Center Casings (Read)',
   'Ability to read Shopping Center Casings'),
  (NEXTVAL('seq_auth_permission_id'), 'SHOPPING_CENTER_CASINGS_UPDATE', 'Shopping Center Casings (Update)',
   'Ability to update Shopping Center Casings'),
  (NEXTVAL('seq_auth_permission_id'), 'SHOPPING_CENTER_CASINGS_DELETE', 'Shopping Center Casings (Delete)',
   'Ability to delete Shopping Center Casings');