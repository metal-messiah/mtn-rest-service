CREATE SEQUENCE seq_site_id;

CREATE TABLE site (
  id                            INT             NOT NULL,
  shopping_center_id            INT             NOT NULL,
  location                      GEOMETRY(POINT) NOT NULL,
  type                          VARCHAR(64)     NOT NULL,
  address_1                     VARCHAR(128),
  address_2                     VARCHAR(128),
  city                          VARCHAR(64),
  state                         VARCHAR(64),
  postal_code                   VARCHAR(16),
  county                        VARCHAR(64),
  country                       VARCHAR(64),
  footprint_sqft                INT,
  intersection_street_primary   VARCHAR(64),
  intersection_street_secondary VARCHAR(64),
  intersection_quad             VARCHAR(64),
  position_in_center            VARCHAR(64),
  version                       INT             NOT NULL DEFAULT 1,
  created_by                    INT             NOT NULL,
  created_date                  TIMESTAMP       NOT NULL DEFAULT NOW(),
  deleted_by                    INT,
  deleted_date                  TIMESTAMP,
  updated_by                    INT             NOT NULL,
  updated_date                  TIMESTAMP       NOT NULL DEFAULT NOW(),
  CONSTRAINT pk_site PRIMARY KEY (id),
  CONSTRAINT fk_site_shopping_center_id FOREIGN KEY (shopping_center_id) REFERENCES shopping_center (id),
  CONSTRAINT fk_site_created_by FOREIGN KEY (created_by) REFERENCES user_profile (id),
  CONSTRAINT fk_site_deleted_by FOREIGN KEY (deleted_by) REFERENCES user_profile (id),
  CONSTRAINT fk_site_updated_by FOREIGN KEY (updated_by) REFERENCES user_profile (id)
);

CREATE INDEX idx_site_shopping_center_id
  ON site (shopping_center_id);
CREATE INDEX idx_site_location
  ON site USING GIST (location);