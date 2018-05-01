CREATE TABLE `store_status` (
	`store_status_id` INT(11) NOT NULL AUTO_INCREMENT,
	`store_id` INT(11) NOT NULL,
	`status` VARCHAR(50) NOT NULL COLLATE 'utf8_unicode_ci',
	`status_start_date` TIMESTAMP NULL DEFAULT NULL,
	`created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`created_by` INT(11) NOT NULL DEFAULT '0',
	`updated_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`updated_by` INT(11) NOT NULL DEFAULT '0',
	`deleted_date` TIMESTAMP NULL DEFAULT NULL,
	`deleted_by` INT(11) NULL DEFAULT NULL,
	`version` INT(11) NOT NULL DEFAULT '1',
	`legacy_location_id` INT(11) NULL DEFAULT '0',
	`legacy_casing_id` INT(11) NULL DEFAULT '0',
	PRIMARY KEY (`store_status_id`),
	INDEX `FK_store_status_store` (`store_id`),
	INDEX `Index 3` (`status_start_date`),
	INDEX `Index 4` (`legacy_casing_id`),
	CONSTRAINT `FK_store_status_store` FOREIGN KEY (`store_id`) REFERENCES `store` (`store_id`) ON UPDATE CASCADE ON DELETE CASCADE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

ALTER TABLE `store`
	ADD COLUMN `current_store_status_id` INT(11) NULL DEFAULT NULL AFTER `banner_id`,
	DROP COLUMN `current_status`;

ALTER TABLE `store`
	ADD CONSTRAINT `FK_store_store_status` FOREIGN KEY (`current_store_status_id`) REFERENCES `store_status` (`store_status_id`) ON UPDATE CASCADE ON DELETE SET NULL;

ALTER TABLE `store_casing`
	ADD COLUMN `store_status_id` INT NULL DEFAULT NULL AFTER `store_casing_note`,
	DROP COLUMN `store_status`;

ALTER TABLE `store_casing`
	ADD CONSTRAINT `FK_store_casing_store_status` FOREIGN KEY (`store_status_id`) REFERENCES `store_status` (`store_status_id`) ON UPDATE CASCADE ON DELETE SET NULL;
