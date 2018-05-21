ALTER TABLE `store`
	ADD COLUMN `current_store_survey_id` INT NULL DEFAULT NULL AFTER `current_store_status_id`,
	ADD CONSTRAINT `FK_store_store_survey` FOREIGN KEY (`current_store_survey_id`) REFERENCES `store_survey` (`store_survey_id`);
