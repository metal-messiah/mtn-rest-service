ALTER TABLE `store`
	ADD COLUMN `validated_date` TIMESTAMP NULL DEFAULT NULL AFTER `is_float`,
	ADD COLUMN `validated_by` INT(11) NULL DEFAULT NULL AFTER `validated_date`;