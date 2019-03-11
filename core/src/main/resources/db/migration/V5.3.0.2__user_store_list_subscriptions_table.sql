CREATE TABLE `user_store_list_subscriptions` (
	`user_store_list_subscriptions_id` INT(11) NOT NULL AUTO_INCREMENT,
	`store_list_id` INT(11) NOT NULL,
	PRIMARY KEY (`user_store_list_subscriptions_id`),
	INDEX `Index_2` (`store_list_id`)
)