ALTER TABLE `shopping_center_casing` 
	CHANGE COLUMN `legacy_casing_id` `legacy_casing_id` INT(11) NULL DEFAULT NULL AFTER `version`,
	CHANGE COLUMN `shopping_center_casing_note` `shopping_center_casing_note` TEXT NULL DEFAULT NULL AFTER `shopping_center_casing_date`;	
ALTER TABLE `store_casing`	
	CHANGE COLUMN `legacy_casing_id` `legacy_casing_id` INT(11) NULL DEFAULT NULL AFTER `version`,
	CHANGE COLUMN `store_casing_note` `store_casing_note` TEXT NULL DEFAULT NULL AFTER `store_casing_date`,
	CHANGE COLUMN `volume_note` `volume_note` TEXT NULL DEFAULT NULL AFTER `volume_plus_minus`;

ALTER TABLE `project`
	CHANGE COLUMN `legacy_project_id` `legacy_project_id` INT(11) NULL DEFAULT NULL AFTER `version`;
ALTER TABLE `shopping_center_survey`
	CHANGE COLUMN `legacy_casing_id` `legacy_casing_id` INT(11) NULL DEFAULT NULL AFTER `version`;
ALTER TABLE `site`
	CHANGE COLUMN `legacy_location_id` `legacy_location_id` INT(11) NULL DEFAULT NULL AFTER `version`;
ALTER TABLE `store_status`
	CHANGE COLUMN `legacy_location_id` `legacy_location_id` INT(11) NULL DEFAULT NULL AFTER `version`,
	CHANGE COLUMN `legacy_casing_id` `legacy_casing_id` INT(11) NULL DEFAULT NULL AFTER `legacy_location_id`;
ALTER TABLE `store_survey`
	CHANGE COLUMN `legacy_casing_id` `legacy_casing_id` INT(10) UNSIGNED NULL DEFAULT NULL AFTER `version`;
ALTER TABLE `store_volume`
	CHANGE COLUMN `legacy_casing_id` `legacy_casing_id` INT(11) NULL DEFAULT NULL AFTER `version`;