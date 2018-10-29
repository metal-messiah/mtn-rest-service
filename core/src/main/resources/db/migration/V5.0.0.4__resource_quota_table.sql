ALTER TABLE resource_quota
	ADD COLUMN `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	ADD COLUMN `created_by` INT(11) NOT NULL DEFAULT '0',
	ADD COLUMN `updated_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	ADD COLUMN `updated_by` INT(11) NOT NULL DEFAULT '0',
	ADD COLUMN `deleted_date` TIMESTAMP NULL DEFAULT NULL,
	ADD COLUMN `deleted_by` INT(11) NULL DEFAULT NULL,
	ADD COLUMN `version` INT(11) NOT NULL DEFAULT '1';



	
