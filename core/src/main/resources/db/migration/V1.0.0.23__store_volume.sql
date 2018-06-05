ALTER TABLE `store_volume`
	ADD COLUMN `volume_grocery` INT(11) NULL DEFAULT NULL AFTER `source`,
	ADD COLUMN `volume_percent_grocery` FLOAT NULL DEFAULT NULL AFTER `volume_grocery`,
	ADD COLUMN `volume_meat` INT(11) NULL DEFAULT NULL AFTER `volume_percent_grocery`,
	ADD COLUMN `volume_percent_meat` FLOAT NULL DEFAULT NULL AFTER `volume_meat`,
	ADD COLUMN `volume_non_food` INT(11) NULL DEFAULT NULL AFTER `volume_percent_meat`,
	ADD COLUMN `volume_percent_non_food` FLOAT NULL DEFAULT NULL AFTER `volume_non_food`,
	ADD COLUMN `volume_other` INT(11) NULL DEFAULT NULL AFTER `volume_percent_non_food`,
	ADD COLUMN `volume_percent_other` FLOAT NULL DEFAULT NULL AFTER `volume_other`,
	ADD COLUMN `volume_produce` INT(11) NULL DEFAULT NULL AFTER `volume_percent_other`,
	ADD COLUMN `volume_percent_produce` FLOAT NULL DEFAULT NULL AFTER `volume_produce`,
	ADD COLUMN `volume_plus_minus` INT(11) NULL DEFAULT NULL AFTER `volume_percent_produce`,
	ADD COLUMN `volume_note` TEXT NULL AFTER `volume_plus_minus`,
	ADD COLUMN `volume_confidence` ENUM('LOW','MEDIUM','HIGH','VERY_HIGH') NULL DEFAULT NULL AFTER `volume_note`,
	CHANGE COLUMN `created_date` `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `volume_confidence`,
	CHANGE COLUMN `created_by` `created_by` INT(11) NOT NULL DEFAULT '0' AFTER `created_date`,
	CHANGE COLUMN `updated_date` `updated_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP AFTER `created_by`,
	CHANGE COLUMN `updated_by` `updated_by` INT(11) NOT NULL DEFAULT '0' AFTER `updated_date`,
	CHANGE COLUMN `deleted_date` `deleted_date` TIMESTAMP NULL DEFAULT NULL AFTER `updated_by`,
	CHANGE COLUMN `deleted_by` `deleted_by` INT(11) NULL DEFAULT NULL AFTER `deleted_date`,
	CHANGE COLUMN `version` `version` INT(11) NOT NULL DEFAULT '1' AFTER `deleted_by`,
	CHANGE COLUMN `legacy_casing_id` `legacy_casing_id` INT(11) NULL DEFAULT NULL AFTER `version`;

ALTER TABLE `store_volume`
	CHANGE COLUMN `volume_type` `volume_type` ENUM('ACTUAL','ESTIMATE','PROJECTION','PLACEHOLDER','THIRD_PARTY') NOT NULL AFTER `volume_date`;

ALTER TABLE `store_casing`
	DROP COLUMN `volume_grocery`,
	DROP COLUMN `volume_percent_grocery`,
	DROP COLUMN `volume_meat`,
	DROP COLUMN `volume_percent_meat`,
	DROP COLUMN `volume_non_food`,
	DROP COLUMN `volume_percent_non_food`,
	DROP COLUMN `volume_other`,
	DROP COLUMN `volume_percent_other`,
	DROP COLUMN `volume_produce`,
	DROP COLUMN `volume_percent_produce`,
	DROP COLUMN `volume_plus_minus`,
	DROP COLUMN `volume_note`,
	DROP COLUMN `volume_confidence`,
	DROP COLUMN `volume_is_placeholder`;
