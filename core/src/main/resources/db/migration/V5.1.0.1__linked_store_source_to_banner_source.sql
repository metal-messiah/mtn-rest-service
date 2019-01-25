ALTER TABLE `store_source`
	ADD COLUMN `source_deleted` TIMESTAMP NULL DEFAULT NULL AFTER `source_edited_date`,
	ADD COLUMN `banner_source_id` INT NULL DEFAULT NULL AFTER `source_deleted_date`,
	ADD CONSTRAINT `FK_store_source_banner_source` FOREIGN KEY (`banner_source_id`) REFERENCES `banner_source` (`source_id`);
