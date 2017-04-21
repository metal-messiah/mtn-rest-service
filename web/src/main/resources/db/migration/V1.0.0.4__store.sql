CREATE SEQUENCE seq_store_id;

CREATE TABLE store (
  id                          INT         NOT NULL,
  site_id                     INT         NOT NULL,
  name                        VARCHAR(64) NOT NULL,
  fit                         VARCHAR(64),
  format                      VARCHAR(64),
  is_active                   BOOLEAN     NOT NULL DEFAULT FALSE,
  area_sales                  INT,
  area_sales_percent_of_total DOUBLE PRECISION,
  area_total                  INT,
  area_is_estimate            BOOLEAN     NOT NULL DEFAULT TRUE,
  opened_date                 TIMESTAMP,
  closed_date                 TIMESTAMP,
  version                     INT         NOT NULL DEFAULT 1,
  created_by                  INT         NOT NULL,
  created_date                TIMESTAMP   NOT NULL DEFAULT NOW(),
  deleted_by                  INT,
  deleted_date                TIMESTAMP,
  updated_by                  INT         NOT NULL,
  updated_date                TIMESTAMP   NOT NULL DEFAULT NOW(),
  CONSTRAINT pk_store PRIMARY KEY (id),
  CONSTRAINT fk_store_site_id FOREIGN KEY (site_id) REFERENCES site (id),
  CONSTRAINT fk_store_created_by FOREIGN KEY (created_by) REFERENCES user_profile (id),
  CONSTRAINT fk_store_deleted_by FOREIGN KEY (deleted_by) REFERENCES user_profile (id),
  CONSTRAINT fk_store_updated_by FOREIGN KEY (updated_by) REFERENCES user_profile (id)
);

CREATE INDEX idx_store_site_id
  ON store (site_id);
CREATE INDEX idx_store_name
  ON store (name);