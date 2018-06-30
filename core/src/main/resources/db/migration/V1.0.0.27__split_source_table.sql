DROP TABLE IF EXISTS `source`;

CREATE TABLE `store_source` (
	`source_id` INT(11) NOT NULL AUTO_INCREMENT,
	`store_id` INT(11) NULL DEFAULT NULL,
	`source_name` VARCHAR(255) NOT NULL DEFAULT '' COLLATE 'latin1_swedish_ci',
	`source_native_id` VARCHAR(128) NOT NULL DEFAULT '' COLLATE 'latin1_swedish_ci',
	`source_url` TEXT NULL,
	`created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`created_by` INT(11) NULL DEFAULT NULL,
	`updated_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`updated_by` INT(11) NULL DEFAULT NULL,
	`deleted_date` TIMESTAMP NULL DEFAULT NULL,
	`deleted_by` INT(11) NULL DEFAULT NULL,
	`version` INT(11) NOT NULL DEFAULT '1',
	`legacy_source_id` INT(11) NULL DEFAULT NULL,
	`validated_date` TIMESTAMP NULL DEFAULT NULL,
	`validated_by` INT(11) NULL DEFAULT NULL,
	`source_store_name` VARCHAR(128) NULL DEFAULT NULL,
	`source_created_date` TIMESTAMP NULL DEFAULT NULL,
	`source_edited_date` TIMESTAMP NULL DEFAULT NULL,
	PRIMARY KEY (`source_id`),
	INDEX `FK_store_source_store` (`store_id`),
	CONSTRAINT `FK_store_source_store` FOREIGN KEY (`store_id`) REFERENCES `store` (`store_id`) ON DELETE SET NULL
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE `shopping_center_source` (
	`source_id` INT(11) NOT NULL AUTO_INCREMENT,
	`shopping_center_id` INT(11) NULL DEFAULT NULL,
	`source_name` VARCHAR(255) NOT NULL DEFAULT '' COLLATE 'latin1_swedish_ci',
	`source_native_id` VARCHAR(128) NOT NULL DEFAULT '' COLLATE 'latin1_swedish_ci',
	`source_url` TEXT NULL,
	`created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`created_by` INT(11) NULL DEFAULT NULL,
	`updated_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`updated_by` INT(11) NULL DEFAULT NULL,
	`deleted_date` TIMESTAMP NULL DEFAULT NULL,
	`deleted_by` INT(11) NULL DEFAULT NULL,
	`version` INT(11) NOT NULL DEFAULT '1',
	`legacy_source_id` INT(11) NULL DEFAULT NULL,
	`validated_date` TIMESTAMP NULL DEFAULT NULL,
	`validated_by` INT(11) NULL DEFAULT NULL,
	`source_shopping_center_name` VARCHAR(128) NULL DEFAULT NULL,
	`source_created_date` TIMESTAMP NULL DEFAULT NULL,
	`source_edited_date` TIMESTAMP NULL DEFAULT NULL,
	PRIMARY KEY (`source_id`),
	INDEX `FK_sc_source_sc` (`shopping_center_id`),
	CONSTRAINT `FK_sc_source_sc` FOREIGN KEY (`shopping_center_id`) REFERENCES `shopping_center` (`shopping_center_id`) ON DELETE SET NULL
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;


