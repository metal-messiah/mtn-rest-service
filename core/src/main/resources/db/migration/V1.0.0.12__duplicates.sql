ALTER TABLE `site` ADD COLUMN `is_duplicate` TINYINT NULL DEFAULT NULL AFTER `active_store_id`;

ALTER TABLE `store`
	DROP COLUMN `base_rent`,
	DROP COLUMN `lease_expiration_date`,
	DROP COLUMN `latest_sales_area`,
	DROP COLUMN `latest_volume`,
	DROP COLUMN `latest_volume_date`,
	DROP COLUMN `latest_total_area`;
