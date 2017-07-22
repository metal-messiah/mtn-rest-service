CREATE SEQUENCE seq_project_id;

CREATE TABLE project (
  project_id        INT          NOT NULL,
  project_name      VARCHAR(256) NOT NULL,
  metro_area        VARCHAR(256),
  client_name       VARCHAR(256),
  project_year      INT,
  project_month     INT,
  is_active         BOOLEAN               DEFAULT FALSE,
  is_primary_data   BOOLEAN               DEFAULT FALSE,
  started_date      TIMESTAMP,
  completed_date    TIMESTAMP,
  source            VARCHAR(256),
  boundary          GEOMETRY(POLYGON),
  version           INT          NOT NULL DEFAULT 1,
  legacy_project_id INT,
  created_by        INT          NOT NULL,
  created_date      TIMESTAMP    NOT NULL DEFAULT NOW(),
  deleted_by        INT,
  deleted_date      TIMESTAMP,
  updated_by        INT          NOT NULL,
  updated_date      TIMESTAMP    NOT NULL DEFAULT NOW(),
  CONSTRAINT pk_project PRIMARY KEY (project_id),
  CONSTRAINT fk_project_created_by FOREIGN KEY (created_by) REFERENCES user_profile (user_profile_id),
  CONSTRAINT fk_project_deleted_by FOREIGN KEY (deleted_by) REFERENCES user_profile (user_profile_id),
  CONSTRAINT fk_project_updated_by FOREIGN KEY (updated_by) REFERENCES user_profile (user_profile_id)
);

INSERT INTO auth_permission (auth_permission_id, system_name, display_name, description) VALUES
  (NEXTVAL('seq_auth_permission_id'), 'PROJECTS_CREATE', 'Projects (Create)',
   'Ability to create Projects'),
  (NEXTVAL('seq_auth_permission_id'), 'PROJECTS_READ', 'Projects (Read)',
   'Ability to read Projects'),
  (NEXTVAL('seq_auth_permission_id'), 'PROJECTS_UPDATE', 'Projects (Update)',
   'Ability to update Projects'),
  (NEXTVAL('seq_auth_permission_id'), 'PROJECTS_DELETE', 'Projects (Delete)',
   'Ability to delete Projects');

ALTER TABLE store_model
  ADD COLUMN project_id INT;
ALTER TABLE store_model
  ALTER COLUMN project_id SET NOT NULL;
ALTER TABLE store_model
  ADD CONSTRAINT fk_store_model_project FOREIGN KEY (project_id) REFERENCES project (project_id);

CREATE INDEX idx_store_model_project
  ON store_model (project_id);