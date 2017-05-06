CREATE SEQUENCE seq_auth_permission_id;

CREATE TABLE auth_permission (
  id           INT         NOT NULL,
  system_name  VARCHAR(64) NOT NULL,
  display_name VARCHAR(64) NOT NULL,
  description  VARCHAR(512),
  CONSTRAINT pk_auth_permission PRIMARY KEY (id),
  CONSTRAINT uk_auth_permission_system_name UNIQUE (system_name)
);