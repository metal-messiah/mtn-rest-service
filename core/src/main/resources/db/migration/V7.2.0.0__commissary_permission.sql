INSERT INTO auth_permission
	(system_name, display_name, description, subject, `action`, created_by, created_date, updated_by, updated_date)
	VALUES ('COMMISSARY_READ', 'Commissary (Read)', 'Ability to read Commissary', 'COMMISSARY', 'READ', 1, NOW(), 1, NOW());
