CREATE SEQUENCE seq_company_id;

CREATE TABLE company (
  id                INT          NOT NULL,
  name              VARCHAR(128) NOT NULL,
  website_url       VARCHAR(256),
  parent_company_id INT,
  CONSTRAINT pk_company PRIMARY KEY (id),
  CONSTRAINT uk_company_name UNIQUE (name),
  CONSTRAINT fk_company_parent_company FOREIGN KEY (parent_company_id) REFERENCES company (id)
);

ALTER TABLE store
  ADD company_id INT;
ALTER TABLE store
  ADD CONSTRAINT fk_store_company FOREIGN KEY (company_id) REFERENCES company (id);

INSERT INTO auth_permission (id, system_name, display_name, description) VALUES
  (NEXTVAL('seq_auth_permission_id'), 'COMPANY_CREATE', 'Companies (Create)',
   'Ability to create Companies'),
  (NEXTVAL('seq_auth_permission_id'), 'COMPANY_READ', 'Companies (Read)', 'Ability to read Companies'),
  (NEXTVAL('seq_auth_permission_id'), 'COMPANY_UPDATE', 'Companies (Update)',
   'Ability to update Companies'),
  (NEXTVAL('seq_auth_permission_id'), 'COMPANY_DELETE', 'Companies (Delete)',
   'Ability to delete Companies');