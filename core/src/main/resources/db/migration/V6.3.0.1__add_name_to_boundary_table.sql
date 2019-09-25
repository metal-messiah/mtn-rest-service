ALTER TABLE `boundary` ADD COLUMN `boundary_name` VARCHAR(255) AFTER `boundary`;

ALTER TABLE `user_boundary` DROP COLUMN `boundary_name`;

UPDATE boundary
INNER JOIN project ON boundary.legacy_project_id = project.project_id
SET boundary.boundary_name = project.project_name;

UPDATE `boundary`
SET `boundary_name`= CASE
   WHEN `boundary_name` IS NULL THEN CONCAT("BOUNDARY_",DATE_FORMAT(`created_date`, "%M-%d-%Y"))
   ELSE `boundary_name`
END