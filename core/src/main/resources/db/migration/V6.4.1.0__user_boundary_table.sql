CREATE TABLE `user_boundary` (
    `user_boundary_id` INT(11) NOT NULL AUTO_INCREMENT,
    `user_profile_id` INT(11) NOT NULL,
    `boundary_id` INT(11) NOT NULL,
    `boundary_name` VARCHAR(128) NOT NULL,
    `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by` INT(11) NULL DEFAULT NULL,
    `updated_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `updated_by` INT(11) NULL DEFAULT NULL,
    `deleted_date` TIMESTAMP NULL DEFAULT NULL,
    `deleted_by` INT(11) NULL DEFAULT NULL,
    `version` INT(11) NOT NULL DEFAULT '1',
    PRIMARY KEY (`user_boundary_id`),
    INDEX `user_id_index` (`user_profile_id`),
    INDEX `boundary_id_index` (`boundary_id`),
    INDEX `boundary_name_index` (`boundary_name`),
    CONSTRAINT `FK_user_boundary_boundary` FOREIGN KEY (`boundary_id`) REFERENCES `boundary` (`boundary_id`) ON UPDATE CASCADE,
    CONSTRAINT `FK_user_boundary_user_profile` FOREIGN KEY (`user_profile_id`) REFERENCES `user_profile` (`user_profile_id`) ON UPDATE CASCADE
);