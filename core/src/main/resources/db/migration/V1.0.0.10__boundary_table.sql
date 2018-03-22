CREATE TABLE `boundary` (
	`boundary_id` INT(11) NOT NULL AUTO_INCREMENT,
	`boundary` MULTIPOLYGON NULL DEFAULT NULL,
	`geojson` JSON NULL DEFAULT NULL,
	`legacy_project_id` INT(11) NULL DEFAULT NULL,
	`created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`created_by` INT(11) NULL DEFAULT NULL,
	`updated_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`updated_by` INT(11) NULL DEFAULT NULL,
	`deleted_date` TIMESTAMP NULL DEFAULT NULL,
	`deleted_by` INT(11) NULL DEFAULT NULL,
	`version` INT(11) NOT NULL DEFAULT '1',
	PRIMARY KEY (`boundary_id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

ALTER TABLE `project`
	CHANGE COLUMN `boundary` `boundary_id` INT NULL DEFAULT NULL AFTER `source`,
	ADD CONSTRAINT `FK_project_boundary` FOREIGN KEY (`boundary_id`) REFERENCES `boundary` (`boundary_id`) ON UPDATE CASCADE ON DELETE SET NULL;

ALTER TABLE `store_casing`
CHANGE COLUMN `volume_notes` `volume_note` TEXT NULL AFTER `volume_plus_minus`;
