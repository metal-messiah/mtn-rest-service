ALTER TABLE `user_profile`
	ADD COLUMN `restriction_boundary_id` INT(11) NULL DEFAULT NULL AFTER `legacy_user_id`,
	ADD CONSTRAINT `FK_user_profile_boundary` FOREIGN KEY (`restriction_boundary_id`) REFERENCES `boundary` (`boundary_id`) ON DELETE SET NULL;
