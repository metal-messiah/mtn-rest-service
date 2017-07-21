CREATE SEQUENCE seq_store_volume_id;

CREATE TABLE store_volume (
  store_volume_id  INT          NOT NULL,
  store_id         INT          NOT NULL,
  volume_total     INT          NOT NULL,
  volume_date      TIMESTAMP    NOT NULL,
  volume_type      VARCHAR(64)  NOT NULL,
  source           VARCHAR(256) NOT NULL,
  version          INT          NOT NULL DEFAULT 1,
  legacy_casing_id INT,
  created_by       INT          NOT NULL,
  created_date     TIMESTAMP    NOT NULL DEFAULT NOW(),
  deleted_by       INT,
  deleted_date     TIMESTAMP,
  updated_by       INT          NOT NULL,
  updated_date     TIMESTAMP    NOT NULL DEFAULT NOW(),
  CONSTRAINT pk_store_volume PRIMARY KEY (store_volume_id),
  CONSTRAINT fk_store_volume_store FOREIGN KEY (store_id) REFERENCES store (store_id),
  CONSTRAINT fk_store_volume_created_by FOREIGN KEY (created_by) REFERENCES user_profile (user_profile_id),
  CONSTRAINT fk_store_volume_deleted_by FOREIGN KEY (deleted_by) REFERENCES user_profile (user_profile_id),
  CONSTRAINT fk_store_volume_updated_by FOREIGN KEY (updated_by) REFERENCES user_profile (user_profile_id)
);

CREATE INDEX idx_store_volume_store
  ON store_volume (store_id);

INSERT INTO auth_permission (auth_permission_id, system_name, display_name, description) VALUES
  (NEXTVAL('seq_auth_permission_id'), 'STORE_VOLUMES_CREATE', 'Store Volumes (Create)',
   'Ability to create Store Volumes'),
  (NEXTVAL('seq_auth_permission_id'), 'STORE_VOLUMES_READ', 'Store Volumes (Read)',
   'Ability to read Store Volumes'),
  (NEXTVAL('seq_auth_permission_id'), 'STORE_VOLUMES_UPDATE', 'Store Volumes (Update)',
   'Ability to update Store Volumes'),
  (NEXTVAL('seq_auth_permission_id'), 'STORE_VOLUMES_DELETE', 'Store Volumes (Delete)',
   'Ability to delete Store Volumes');