ALTER TABLE `site`
	CHANGE COLUMN `location` `location` POINT NULL AFTER `cbsa_id`,
	DROP INDEX `Index 4`;