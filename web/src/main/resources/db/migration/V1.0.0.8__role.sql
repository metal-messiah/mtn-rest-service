CREATE SEQUENCE seq_auth_role_id;

CREATE TABLE auth_role (
  id           INT         NOT NULL,
  display_name VARCHAR(64) NOT NULL,
  description  VARCHAR(512),
  created_by   INT         NOT NULL,
  created_date TIMESTAMP   NOT NULL DEFAULT NOW(),
  deleted_by   INT,
  deleted_date TIMESTAMP,
  updated_by   INT         NOT NULL,
  updated_date TIMESTAMP   NOT NULL DEFAULT NOW(),
  CONSTRAINT pk_auth_role PRIMARY KEY (id),
  CONSTRAINT uk_auth_role_system_name UNIQUE (display_name),
  CONSTRAINT fk_auth_role_created_by FOREIGN KEY (created_by) REFERENCES user_profile (id),
  CONSTRAINT fk_auth_role_deleted_by FOREIGN KEY (deleted_by) REFERENCES user_profile (id),
  CONSTRAINT fk_auth_role_updated_by FOREIGN KEY (updated_by) REFERENCES user_profile (id)
);

ALTER TABLE user_profile
  ADD COLUMN auth_role_id INT;
ALTER TABLE user_profile
  ADD CONSTRAINT fk_user_profile_auth_role FOREIGN KEY (auth_role_id) REFERENCES auth_role (id);
CREATE INDEX idx_user_profile_auth_role_id
  ON user_profile (auth_role_id);

CREATE TABLE auth_role_auth_permission (
  auth_role_id       INT NOT NULL,
  auth_permission_id INT NOT NULL,
  CONSTRAINT pk_auth_role_auth_permission PRIMARY KEY (auth_role_id, auth_permission_id),
  CONSTRAINT fk_auth_role_auth_permission_auth_role FOREIGN KEY (auth_role_id) REFERENCES auth_role (id),
  CONSTRAINT fk_auth_role_auth_permission_auth_permission FOREIGN KEY (auth_permission_id) REFERENCES auth_permission (id)
);