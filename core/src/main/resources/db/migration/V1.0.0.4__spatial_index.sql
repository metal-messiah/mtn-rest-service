ALTER TABLE mtn_dev.site CHANGE COLUMN `location` `location` POINT NOT NULL AFTER `cbsa_id`,	ADD SPATIAL INDEX `Index 4` (`location`);