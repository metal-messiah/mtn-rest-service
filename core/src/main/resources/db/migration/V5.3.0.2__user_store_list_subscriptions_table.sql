CREATE TABLE `user_store_list_subscriptions` (
	`store_list_id` INT(11) NOT NULL,
	`user_profile_id` INT(11) NOT NULL,
	INDEX `Index_2` (`store_list_id`),
	INDEX `Index_3` (`user_profile_id`)
)