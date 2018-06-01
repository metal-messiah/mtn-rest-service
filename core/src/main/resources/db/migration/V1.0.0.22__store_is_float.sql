ALTER TABLE `store`
	ADD COLUMN `is_float` TINYINT NULL DEFAULT '0' AFTER `current_store_survey_id`;
