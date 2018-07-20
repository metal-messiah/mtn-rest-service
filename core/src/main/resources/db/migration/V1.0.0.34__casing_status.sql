ALTER TABLE `store_casing` ADD COLUMN `store_status` VARCHAR(50) NULL DEFAULT NULL AFTER `store_status_id`;

update store_casing c
join store_status s on c.store_status_id = s.store_status_id
set c.store_status = s.`status`
where c.store_status is null;

ALTER TABLE `store_casing`
DROP COLUMN `store_status_id`,
	DROP FOREIGN KEY `FK_store_casing_store_status`;