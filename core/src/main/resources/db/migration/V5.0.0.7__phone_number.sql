ALTER TABLE `user_profile`
	ADD COLUMN `phone_number` VARCHAR(16) NULL DEFAULT NULL AFTER `last_name`;
