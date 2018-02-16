ALTER TABLE mtn_dev.user_profile
	ADD COLUMN `legacy_user_id` INT(11) NULL DEFAULT NULL AFTER `version`;
	
ALTER TABLE mtn_dev.shopping_center
	CHANGE COLUMN `storeName` `shopping_center_name` VARCHAR(128) NULL DEFAULT NULL COLLATE 'latin1_swedish_ci' AFTER `shopping_center_id`;
	
ALTER TABLE mtn_dev.site
	CHANGE COLUMN `storeType` `site_type` ENUM('PLACEHOLDER','ANCHOR','DEFAULT') NULL DEFAULT 'DEFAULT' AFTER `longitude`,
	ADD COLUMN `location` POINT NULL DEFAULT NULL AFTER `cbsa_id`;