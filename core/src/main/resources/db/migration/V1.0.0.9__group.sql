CREATE SEQUENCE seq_auth_group_id;

CREATE TABLE auth_group (
  id           INT         NOT NULL,
  display_name VARCHAR(64) NOT NULL,
  description  VARCHAR(512),
  created_by   INT         NOT NULL,
  created_date TIMESTAMP   NOT NULL DEFAULT NOW(),
  deleted_by   INT,
  deleted_date TIMESTAMP,
  updated_by   INT         NOT NULL,
  updated_date TIMESTAMP   NOT NULL DEFAULT NOW(),
  CONSTRAINT pk_auth_group PRIMARY KEY (id),
  CONSTRAINT uk_auth_group_system_name UNIQUE (display_name),
  CONSTRAINT fk_auth_group_created_by FOREIGN KEY (created_by) REFERENCES user_profile (id),
  CONSTRAINT fk_auth_group_deleted_by FOREIGN KEY (deleted_by) REFERENCES user_profile (id),
  CONSTRAINT fk_auth_group_updated_by FOREIGN KEY (updated_by) REFERENCES user_profile (id)
);

ALTER TABLE user_profile
  ADD COLUMN auth_group_id INT;
ALTER TABLE user_profile
  ADD CONSTRAINT fk_user_profile_auth_group FOREIGN KEY (auth_group_id) REFERENCES auth_group (id);
CREATE INDEX idx_user_profile_auth_group_id
  ON user_profile (auth_group_id);