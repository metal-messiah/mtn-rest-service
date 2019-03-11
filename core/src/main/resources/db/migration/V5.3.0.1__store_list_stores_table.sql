CREATE TABLE `store_list_stores` (
	`store_list_stores_id` INT(11) NOT NULL AUTO_INCREMENT,
	`store_list_id` INT(11) NOT NULL,
	`store_id` INT(11) NOT NULL,
	PRIMARY KEY (`store_list_stores_id`),
	INDEX `Index_2` (`store_list_id`)
)