ALTER TABLE `store_survey`
	ADD COLUMN `pharmacy_scripts_weekly` INT(11) NULL DEFAULT NULL AFTER `pharmacy_has_drive_through`,
	ADD COLUMN `pharmacy_avg_dollars_per_script` FLOAT NULL DEFAULT NULL AFTER `pharmacy_scripts_weekly`,
	ADD COLUMN `pharmacy_pharmacist_count` INT(11) NULL DEFAULT NULL AFTER `pharmacy_avg_dollars_per_script`,
	ADD COLUMN `pharmacy_technician_count` INT(11) NULL DEFAULT NULL AFTER `pharmacy_pharmacist_count`;

update store_survey ss
join store_casing sc on sc.store_survey_id = ss.store_survey_id
set ss.pharmacy_scripts_weekly = sc.pharmacy_scripts_weekly,
	ss.pharmacy_avg_dollars_per_script = sc.pharmacy_avg_dollars_per_script,
	ss.pharmacy_pharmacist_count = sc.pharmacy_pharmacist_count,
	ss.pharmacy_technician_count = sc.pharmacy_technician_count
where sc.store_casing_id is not null;

ALTER TABLE `store_casing`
	DROP COLUMN `pharmacy_scripts_weekly`,
	DROP COLUMN `pharmacy_avg_dollars_per_script`,
	DROP COLUMN `pharmacy_pharmacist_count`,
	DROP COLUMN `pharmacy_technician_count`;