ALTER TABLE `client_access_key`
	ADD COLUMN `client_name` VARCHAR(255) NULL AFTER `client_access_key_id`;