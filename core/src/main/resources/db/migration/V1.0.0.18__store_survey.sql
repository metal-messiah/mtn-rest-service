ALTER TABLE store
  DROP fit;
ALTER TABLE store
  DROP format;
ALTER TABLE store
  DROP area_sales;
ALTER TABLE store
  DROP area_sales_percent_of_total;
ALTER TABLE store
  DROP area_total;
ALTER TABLE store
  DROP area_is_estimate;

CREATE SEQUENCE seq_store_survey_id;

CREATE TABLE store_survey (
  id                          INT       NOT NULL,
  store_id                    INT       NOT NULL,
  fit                         VARCHAR(64),
  format                      VARCHAR(64),
  area_sales                  INT,
  area_sales_percent_of_total DOUBLE PRECISION,
  area_total                  INT,
  area_is_estimate            BOOLEAN   NOT NULL DEFAULT TRUE,
  created_by                  INT       NOT NULL,
  created_date                TIMESTAMP NOT NULL DEFAULT NOW(),
  deleted_by                  INT,
  deleted_date                TIMESTAMP,
  updated_by                  INT       NOT NULL,
  updated_date                TIMESTAMP NOT NULL DEFAULT NOW(),
  CONSTRAINT pk_store_survey PRIMARY KEY (id),
  CONSTRAINT fk_store_survey_store FOREIGN KEY (store_id) REFERENCES store (id),
  CONSTRAINT fk_store_survey_created_by FOREIGN KEY (created_by) REFERENCES user_profile (id),
  CONSTRAINT fk_store_survey_deleted_by FOREIGN KEY (deleted_by) REFERENCES user_profile (id),
  CONSTRAINT fk_store_survey_updated_by FOREIGN KEY (updated_by) REFERENCES user_profile (id)
);

CREATE INDEX idx_store_survey_store
  ON store_survey (store_id);