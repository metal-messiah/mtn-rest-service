ALTER TABLE `site`
	ADD COLUMN `backfilled_non_grocery` INT NULL DEFAULT '0' AFTER `assignee_id`;