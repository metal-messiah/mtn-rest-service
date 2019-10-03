INSERT INTO auth_permission
	(system_name, display_name, description, subject, `action`, created_by, created_date, updated_by, updated_date)
	VALUES ('SITE_WISE_READ', 'SiteWise (Read)', 'Ability to read SiteWise', 'SITE_WISE', 'READ', 1, NOW(), 1, NOW());
