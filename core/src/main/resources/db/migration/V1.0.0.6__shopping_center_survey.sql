CREATE SEQUENCE seq_shopping_center_survey_id;

CREATE TABLE shopping_center_survey (
  id                 INT       NOT NULL,
  shopping_center_id INT       NOT NULL,
  has_angled_spaces  BOOLEAN            DEFAULT FALSE,
  has_parking_hog    BOOLEAN            DEFAULT FALSE,
  has_speed_bumps    BOOLEAN            DEFAULT FALSE,
  created_by         INT       NOT NULL,
  created_date       TIMESTAMP NOT NULL DEFAULT NOW(),
  deleted_by         INT,
  deleted_date       TIMESTAMP,
  updated_by         INT       NOT NULL,
  updated_date       TIMESTAMP NOT NULL DEFAULT NOW(),
  CONSTRAINT pk_shopping_center_survey PRIMARY KEY (id),
  CONSTRAINT fk_shopping_center_survey_shopping_center FOREIGN KEY (shopping_center_id) REFERENCES shopping_center (id),
  CONSTRAINT fk_shopping_center_survey_created_by FOREIGN KEY (created_by) REFERENCES user_profile (id),
  CONSTRAINT fk_shopping_center_survey_deleted_by FOREIGN KEY (deleted_by) REFERENCES user_profile (id),
  CONSTRAINT fk_shopping_center_survey_updated_by FOREIGN KEY (updated_by) REFERENCES user_profile (id)
);

CREATE INDEX idx_shopping_center_survey_shopping_center
  ON shopping_center_survey (shopping_center_id);

CREATE SEQUENCE seq_shopping_center_access_id;

CREATE TABLE shopping_center_access (
  id                        INT NOT NULL,
  shopping_center_survey_id INT NOT NULL,
  has_left_in               BOOLEAN DEFAULT FALSE,
  has_left_out              BOOLEAN DEFAULT FALSE,
  has_traffic_light         BOOLEAN DEFAULT FALSE,
  has_one_way_road          BOOLEAN DEFAULT FALSE,
  has_right_in              BOOLEAN DEFAULT FALSE,
  has_right_out             BOOLEAN DEFAULT FALSE,
  CONSTRAINT pk_shopping_center_access PRIMARY KEY (id),
  CONSTRAINT fk_shopping_center_access_shopping_center_survey FOREIGN KEY (shopping_center_survey_id) REFERENCES shopping_center_survey (id)
);

CREATE INDEX idx_shopping_center_access_survey
  ON shopping_center_access (shopping_center_survey_id);

CREATE SEQUENCE seq_shopping_center_tenant_id;

CREATE TABLE shopping_center_tenant (
  id                        INT         NOT NULL,
  shopping_center_survey_id INT         NOT NULL,
  name                      VARCHAR(64) NOT NULL,
  type                      VARCHAR(64) NOT NULL,
  is_outparcel              BOOLEAN DEFAULT FALSE,
  sqft                      INT,
  CONSTRAINT pk_shopping_center_tenant PRIMARY KEY (id),
  CONSTRAINT fk_shopping_center_tenant_shopping_center_survey FOREIGN KEY (shopping_center_survey_id) REFERENCES shopping_center_survey (id)
);

CREATE INDEX idx_shopping_center_tenant_shopping_center
  ON shopping_center_tenant (shopping_center_survey_id);