ALTER TABLE `store_casing`
	ADD COLUMN `shopping_center_casing_id` INT(11) NULL AFTER `store_survey_id`,
	ADD CONSTRAINT `FK_store_casing_shopping_center_casing` FOREIGN KEY (`shopping_center_casing_id`) REFERENCES `shopping_center_casing` (`shopping_center_casing_id`) ON DELETE SET NULL;
