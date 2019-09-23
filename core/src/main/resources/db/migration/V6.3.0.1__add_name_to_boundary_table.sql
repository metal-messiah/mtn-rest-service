ALTER TABLE `boundary` ADD COLUMN `boundary_name` VARCHAR(255) AFTER `boundary`;

ALTER TABLE `user_boundary` DROP COLUMN `boundary_name`;