ALTER TABLE `shopping_center_survey` CHANGE COLUMN `shopping_center_survey_notes` `shopping_center_survey_note` TEXT NULL AFTER `center_type`;
ALTER TABLE `shopping_center_casing` CHANGE COLUMN `shopping_center_casing_notes` `shopping_center_casing_note` TEXT NULL AFTER `shopping_center_casing_date`;
ALTER TABLE `store_survey` CHANGE COLUMN `store_survey_notes` `store_survey_note` TEXT NULL AFTER `store_survey_date`;
ALTER TABLE `store_survey` CHANGE COLUMN `department_online_pickup` `department_online_pickup` TINYINT(4) NULL DEFAULT NULL AFTER `department_olive_bar`;
ALTER TABLE `store_casing`	CHANGE COLUMN `store_casing_notes` `store_casing_note` TEXT NULL AFTER `store_casing_date`;