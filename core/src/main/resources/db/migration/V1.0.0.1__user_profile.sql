CREATE SEQUENCE seq_user_profile_id;
CREATE SEQUENCE seq_user_identity_id;

CREATE TABLE user_profile (
  id           INT         NOT NULL,
  email        VARCHAR(64) NOT NULL,
  first_name   VARCHAR(64),
  last_name    VARCHAR(64),
  created_by   INT         NOT NULL,
  created_date TIMESTAMP   NOT NULL DEFAULT NOW(),
  deleted_by   INT,
  deleted_date TIMESTAMP,
  updated_by   INT         NOT NULL,
  updated_date TIMESTAMP   NOT NULL DEFAULT NOW(),
  CONSTRAINT pk_user_profile PRIMARY KEY (id),
  CONSTRAINT uk_user_profile_email UNIQUE (email),
  CONSTRAINT fk_user_profile_created_by FOREIGN KEY (created_by) REFERENCES user_profile (id),
  CONSTRAINT fk_user_profile_deleted_by FOREIGN KEY (deleted_by) REFERENCES user_profile (id),
  CONSTRAINT fk_user_profile_updated_by FOREIGN KEY (updated_by) REFERENCES user_profile (id)
);

CREATE TABLE user_identity (
  id               INT         NOT NULL,
  user_profile_id  INT         NOT NULL,
  provider         VARCHAR(64) NOT NULL,
  provider_user_id VARCHAR(64) NOT NULL,
  CONSTRAINT pk_user_identity PRIMARY KEY (id),
  CONSTRAINT uk_user_identity_provider_provider_user_id UNIQUE (provider, provider_user_id),
  CONSTRAINT fk_user_identity_user_profile_id FOREIGN KEY (user_profile_id) REFERENCES user_profile (id)
);

CREATE INDEX idx_user_identity_user_profile_id
  ON user_identity (user_profile_id);
CREATE INDEX idx_user_identity_provider_user_id_provider
  ON user_identity (provider_user_id, provider);