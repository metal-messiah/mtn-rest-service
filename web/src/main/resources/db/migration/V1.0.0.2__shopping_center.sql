CREATE SEQUENCE seq_shopping_center_id;

CREATE TABLE shopping_center (
    id INT NOT NULL,
    name VARCHAR(64) NOT NULL,
    owner VARCHAR(64),
    native_id VARCHAR(64),
    url VARCHAR(256),
    version INT NOT NULL DEFAULT 1,
    created_by INT NOT NULL,
    created_date TIMESTAMP NOT NULL DEFAULT NOW(),
    deleted_by INT,
    deleted_date TIMESTAMP,
    updated_by INT NOT NULL,
    updated_date TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT pk_shopping_center PRIMARY KEY (id),
    CONSTRAINT fk_shopping_center_created_by FOREIGN KEY (created_by) REFERENCES user_profile (id),
    CONSTRAINT fk_shopping_center_deleted_by FOREIGN KEY (deleted_by) REFERENCES user_profile (id),
    CONSTRAINT fk_shopping_center_updated_by FOREIGN KEY (updated_by) REFERENCES user_profile (id)
);

CREATE INDEX idx_shopping_center_name ON shopping_center (name);
CREATE INDEX idx_shopping_center_native_id ON shopping_center (native_id);
CREATE INDEX idx_shopping_center_owner ON shopping_center (owner);