CREATE TABLE `user_permission` (
	`user_profile_id` INT(11) NULL DEFAULT NULL,
	`auth_permission_id` INT(11) NULL DEFAULT NULL,
	INDEX `FK_user_permission_auth_permission` (`auth_permission_id`),
	INDEX `FK_user_permission_user_profile` (`user_profile_id`),
	CONSTRAINT `FK_user_permission_auth_permission` FOREIGN KEY (`auth_permission_id`) REFERENCES `auth_permission` (`auth_permission_id`) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT `FK_user_permission_user_profile` FOREIGN KEY (`user_profile_id`) REFERENCES `user_profile` (`user_profile_id`) ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO user_permission
SELECT u.user_profile_id, p.auth_permission_id
FROM user_profile u
JOIN auth_role r ON r.auth_role_id = u.auth_role_id
JOIN auth_role_auth_permission p ON r.auth_role_id = p.auth_role_id
ORDER BY u.user_profile_id, p.auth_permission_id;

INSERT INTO `auth_permission` (`auth_permission_id`, `system_name`, `display_name`, `description`, `subject`, `action`, `created_by`, `created_date`, `deleted_by`, `deleted_date`, `updated_by`, `updated_date`, `version`) VALUES (72, 'SITE_WISE_READ', 'SiteWise (Read)', 'Ability to read from site-wise endpoint', 'SITE_WISE', 'READ', 1, NOW(), NULL, NULL, 1, NOW(), 1);
