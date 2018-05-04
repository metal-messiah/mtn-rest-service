DROP TABLE `interaction`;

ALTER TABLE `shopping_center_casing`
	ADD COLUMN `shopping_center_survey_id` INT(11) NOT NULL AFTER `legacy_casing_id`,
	ADD CONSTRAINT `FK_shopping_center_casing_shopping_center_survey` FOREIGN KEY (`shopping_center_survey_id`) REFERENCES `shopping_center_survey` (`shopping_center_survey_id`);

ALTER TABLE `store_casing`
	ADD COLUMN `store_survey_id` INT(11) NOT NULL AFTER `legacy_casing_id`,
	ADD CONSTRAINT `FK_store_casing_store_survey` FOREIGN KEY (`store_survey_id`) REFERENCES `store_survey` (`store_survey_id`);

CREATE TABLE `shopping_center_casing_project` (
	`shopping_center_casing_id` INT(11) NOT NULL,
	`project_id` INT(11) NOT NULL,
	INDEX `FK_shopping_center_casing_project_store_casing` (`shopping_center_casing_id`),
	INDEX `FK_shopping_center_casing_project_project` (`project_id`),
	CONSTRAINT `shopping_center_casing_project_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE CASCADE,
	CONSTRAINT `shopping_center_casing_project_ibfk_2` FOREIGN KEY (`shopping_center_casing_id`) REFERENCES `shopping_center_casing` (`shopping_center_casing_id`) ON DELETE CASCADE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE `store_casing_project` (
	`store_casing_id` INT(11) NOT NULL,
	`project_id` INT(11) NOT NULL,
	INDEX `FK_store_casing_project_store_casing` (`store_casing_id`),
	INDEX `FK_store_casing_project_project` (`project_id`),
	CONSTRAINT `FK_store_casing_project_project` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE CASCADE,
	CONSTRAINT `FK_store_casing_project_store_casing` FOREIGN KEY (`store_casing_id`) REFERENCES `store_casing` (`store_casing_id`) ON DELETE CASCADE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;