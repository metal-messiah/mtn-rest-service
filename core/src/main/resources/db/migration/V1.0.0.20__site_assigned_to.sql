ALTER TABLE `site`
	ADD COLUMN `assignee_id` INT NULL DEFAULT NULL AFTER `is_duplicate`,
	ADD CONSTRAINT `FK_site_user_profile` FOREIGN KEY (`assignee_id`) REFERENCES `user_profile` (`user_profile_id`);
