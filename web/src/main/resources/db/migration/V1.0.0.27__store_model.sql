CREATE SEQUENCE seq_store_model_id;

CREATE TABLE store_model (
  store_model_id     INT         NOT NULL,
  store_id           INT         NOT NULL,
  mapkey             VARCHAR(64),
  curve              FLOAT,
  pwta               FLOAT,
  power              FLOAT,
  fit_adjusted_power FLOAT,
  model_type         VARCHAR(64) NOT NULL,
  model_date         TIMESTAMP   NOT NULL,
  version            INT         NOT NULL DEFAULT 1,
  legacy_casing_id   INT,
  created_by         INT         NOT NULL,
  created_date       TIMESTAMP   NOT NULL DEFAULT NOW(),
  deleted_by         INT,
  deleted_date       TIMESTAMP,
  updated_by         INT         NOT NULL,
  updated_date       TIMESTAMP   NOT NULL DEFAULT NOW(),
  CONSTRAINT pk_store_model PRIMARY KEY (store_model_id),
  CONSTRAINT fk_store_model_store FOREIGN KEY (store_id) REFERENCES store (store_id),
  CONSTRAINT fk_store_model_created_by FOREIGN KEY (created_by) REFERENCES user_profile (user_profile_id),
  CONSTRAINT fk_store_model_deleted_by FOREIGN KEY (deleted_by) REFERENCES user_profile (user_profile_id),
  CONSTRAINT fk_store_model_updated_by FOREIGN KEY (updated_by) REFERENCES user_profile (user_profile_id)
);

CREATE INDEX idx_store_model_store
  ON store_model (store_id);

INSERT INTO auth_permission (auth_permission_id, system_name, display_name, description) VALUES
  (NEXTVAL('seq_auth_permission_id'), 'STORE_MODELS_CREATE', 'Store Models (Create)',
   'Ability to create Store Models'),
  (NEXTVAL('seq_auth_permission_id'), 'STORE_MODELS_READ', 'Store Models (Read)',
   'Ability to read Store Models'),
  (NEXTVAL('seq_auth_permission_id'), 'STORE_MODELS_UPDATE', 'Store Models (Update)',
   'Ability to update Store Models'),
  (NEXTVAL('seq_auth_permission_id'), 'STORE_MODELS_DELETE', 'Store Models (Delete)',
   'Ability to delete Store Models');