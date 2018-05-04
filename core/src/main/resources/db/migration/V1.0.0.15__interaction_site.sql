ALTER TABLE `interaction`
	CHANGE COLUMN `shopping_center_id` `shopping_center_id` INT(11) NULL DEFAULT NULL AFTER `legacy_casing_id`,
	CHANGE COLUMN `store_id` `store_id` INT(11) NULL DEFAULT NULL AFTER `shopping_center_id`,
	ADD COLUMN `site_id` INT NULL DEFAULT NULL AFTER `store_id`,
	ADD CONSTRAINT `FK_interaction_site` FOREIGN KEY (`site_id`) REFERENCES `site` (`site_id`);
