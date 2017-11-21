ALTER TABLE "company"
	RENAME COLUMN "name" TO company_name;

ALTER TABLE "company"
	ADD "created_date" TIMESTAMP NULL,
	ADD "created_by" INTEGER NOT NULL,
	ADD "updated_date" TIMESTAMP NULL,
	ADD "updated_by" INTEGER NOT NULL,
	ADD "deleted_date" TIMESTAMP NULL,
	ADD "deleted_by" INTEGER NULL DEFAULT NULL,
	ADD "version" INTEGER NOT NULL DEFAULT 1;
	
ALTER TABLE "project"
	RENAME COLUMN "started_date" TO "date_started";
ALTER TABLE "project"
	RENAME COLUMN "completed_date" TO "date_completed";
	
ALTER TABLE "shopping_center_access"
 	ADD "updated_date" TIMESTAMP NULL,
	ADD "access_type" VARCHAR(32) NOT NULL;
	
ALTER TABLE "shopping_center_casing"
 	ADD "shopping_center_casing_date" TIMESTAMP NULL;
 	
ALTER TABLE "shopping_center_survey"
 	ADD "shopping_center_survey_date" TIMESTAMP NULL;

ALTER TABLE "shopping_center_tenant"
 	ADD "updated_date" TIMESTAMP NULL;
 	
ALTER TABLE "site"
 	DROP "location";
ALTER TABLE "site"
 	ADD "latitude" DOUBLE PRECISION NOT NULL,
 	ADD "longiutde" DOUBLE PRECISION NOT NULL;
 	
-- Create Source table
CREATE TABLE "source"
(
  source_id integer NOT NULL,
  source_name character varying(256),
  source_native_id character varying(128),
  source_url text null,
  store_id integer null,
  shopping_center_id integer null,
  version integer NOT NULL DEFAULT 1,
  created_by integer NOT NULL,
  created_date timestamp without time zone NOT NULL DEFAULT now(),
  deleted_by integer,
  deleted_date timestamp without time zone,
  updated_by integer NOT NULL,
  updated_date timestamp without time zone NOT NULL DEFAULT now(),
  legacy_source_id integer NULL,
  CONSTRAINT pk_source PRIMARY KEY (source_id),
  CONSTRAINT fk_site_created_by FOREIGN KEY (created_by)
      REFERENCES user_profile (user_profile_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_site_deleted_by FOREIGN KEY (deleted_by)
      REFERENCES user_profile (user_profile_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_shopping_center_id FOREIGN KEY (shopping_center_id)
      REFERENCES shopping_center (shopping_center_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
   CONSTRAINT fk_store_id FOREIGN KEY (store_id)
      REFERENCES store (store_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_site_updated_by FOREIGN KEY (updated_by)
      REFERENCES user_profile (user_profile_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.site
  OWNER TO "mtn-service-user";
