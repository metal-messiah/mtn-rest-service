INSERT INTO auth_permission
	(system_name, display_name, description, subject, `action`, created_by, created_date, updated_by, updated_date)
	VALUES ('MAP_BOX_READ', 'MapBox (Read)', 'Ability to read MapBox', 'MAP_BOX', 'READ', 1, NOW(), 1, NOW()),
			('GRAVITY_READ', 'Gravity (Read)', 'Ability to read Gravity', 'GRAVITY', 'READ', 1, NOW(), 1, NOW());