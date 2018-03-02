ALTER TABLE `site` ADD COLUMN `active_store_id` INT NULL DEFAULT NULL AFTER `location`;
ALTER TABLE `site` ADD CONSTRAINT `FK_site_store` FOREIGN KEY (`active_store_id`) REFERENCES `store` (`store_id`) ON DELETE SET NULL;

ALTER TABLE `store`
	ADD COLUMN `latest_sales_area` INT NULL DEFAULT NULL AFTER `lease_expiration_date`,
	ADD COLUMN `latest_total_area` INT(11) NULL DEFAULT NULL AFTER `latest_sales_area`,
	ADD COLUMN `latest_volume` INT NULL DEFAULT NULL AFTER `latest_sales_area`,
	ADD COLUMN `latest_volume_date` TIMESTAMP NULL DEFAULT NULL AFTER `latest_volume`,
	ADD COLUMN `current_status` VARCHAR(50) NULL DEFAULT NULL AFTER `latest_volume_date`;
