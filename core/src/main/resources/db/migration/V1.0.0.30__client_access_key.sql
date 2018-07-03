CREATE TABLE `client_access_key` (
	`client_access_key_id` INT(11) NOT NULL AUTO_INCREMENT,
	`access_key` VARCHAR(255) NOT NULL,
	`client_unique_identifier` VARCHAR(255) NULL DEFAULT NULL,
	`active` TINYINT(4) NOT NULL DEFAULT '0',
	`last_verified` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`created_by` INT(11) NULL DEFAULT NULL,
	`updated_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`updated_by` INT(11) NULL DEFAULT NULL,
	`deleted_date` TIMESTAMP NULL DEFAULT NULL,
	`deleted_by` INT(11) NULL DEFAULT NULL,
	`version` INT(11) NOT NULL DEFAULT '1',
	PRIMARY KEY (`client_access_key_id`),
	UNIQUE INDEX `Index 2` (`access_key`),
	UNIQUE INDEX `Index 3` (`client_unique_identifier`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
