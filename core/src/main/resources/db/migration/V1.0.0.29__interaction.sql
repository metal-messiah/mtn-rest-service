CREATE SEQUENCE seq_interaction_id;

CREATE TABLE interaction (
  interaction_id            INT       NOT NULL,
  project_id                INT       NOT NULL,
  shopping_center_id        INT,
  shopping_center_survey_id INT,
  shopping_center_casing_id INT,
  store_id                  INT,
  store_survey_id           INT,
  store_casing_id           INT,
  interaction_date          TIMESTAMP NOT NULL,
  version                   INT       NOT NULL DEFAULT 1,
  legacy_casing_id          INT,
  created_by                INT       NOT NULL,
  created_date              TIMESTAMP NOT NULL DEFAULT NOW(),
  deleted_by                INT,
  deleted_date              TIMESTAMP,
  updated_by                INT       NOT NULL,
  updated_date              TIMESTAMP NOT NULL DEFAULT NOW(),
  CONSTRAINT pk_interaction PRIMARY KEY (interaction_id),
  CONSTRAINT fk_interaction_project FOREIGN KEY (project_id) REFERENCES project (project_id),
  CONSTRAINT fk_interaction_shopping_center FOREIGN KEY (shopping_center_id) REFERENCES shopping_center (shopping_center_id),
  CONSTRAINT fk_interaction_shopping_center_survey FOREIGN KEY (shopping_center_survey_id) REFERENCES shopping_center_survey (shopping_center_survey_id),
  CONSTRAINT fk_interaction_shopping_center_casing FOREIGN KEY (shopping_center_casing_id) REFERENCES shopping_center_casing (shopping_center_casing_id),
  CONSTRAINT fk_interaction_store FOREIGN KEY (store_id) REFERENCES store (store_id),
  CONSTRAINT fk_interaction_store_survey FOREIGN KEY (store_survey_id) REFERENCES store_survey (store_survey_id),
  CONSTRAINT fk_interaction_store_casing FOREIGN KEY (store_casing_id) REFERENCES store_casing (store_casing_id),
  CONSTRAINT fk_interaction_created_by FOREIGN KEY (created_by) REFERENCES user_profile (user_profile_id),
  CONSTRAINT fk_interaction_deleted_by FOREIGN KEY (deleted_by) REFERENCES user_profile (user_profile_id),
  CONSTRAINT fk_interaction_updated_by FOREIGN KEY (updated_by) REFERENCES user_profile (user_profile_id)
);

INSERT INTO auth_permission (auth_permission_id, system_name, display_name, description) VALUES
  (NEXTVAL('seq_auth_permission_id'), 'INTERACTIONS_CREATE', 'Interactions (Create)',
   'Ability to create Interactions'),
  (NEXTVAL('seq_auth_permission_id'), 'INTERACTIONS_READ', 'Interactions (Read)',
   'Ability to read Interactions'),
  (NEXTVAL('seq_auth_permission_id'), 'INTERACTIONS_UPDATE', 'Interactions (Update)',
   'Ability to update Interactions'),
  (NEXTVAL('seq_auth_permission_id'), 'INTERACTIONS_DELETE', 'Interactions (Delete)',
   'Ability to delete Interactions');

CREATE INDEX idx_interaction_project
  ON interaction (project_id);
CREATE INDEX idx_interaction_shopping_center
  ON interaction (shopping_center_id);
CREATE INDEX idx_interaction_shopping_center_survey
  ON interaction (shopping_center_survey_id);
CREATE INDEX idx_interaction_shopping_center_casing
  ON interaction (shopping_center_casing_id);
CREATE INDEX idx_interaction_store
  ON interaction (store_id);
CREATE INDEX idx_interaction_store_survey
  ON interaction (store_survey_id);
CREATE INDEX idx_interaction_store_casing
  ON interaction (store_casing_id);