CREATE TABLE `store_list` (
	`store_list_id` INT(11) NOT NULL AUTO_INCREMENT,
	`store_list_name` VARCHAR(128) NOT NULL,
	`created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`created_by` INT(11) NULL DEFAULT NULL,
	`updated_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`updated_by` INT(11) NULL DEFAULT NULL,
	`deleted_date` TIMESTAMP NULL DEFAULT NULL,
	`deleted_by` INT(11) NULL DEFAULT NULL,
	`version` INT(11) NOT NULL DEFAULT '1',
	PRIMARY KEY (`store_list_id`),
	INDEX `Index_2` (`store_list_name`)
)