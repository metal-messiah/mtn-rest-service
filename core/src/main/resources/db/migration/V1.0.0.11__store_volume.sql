ALTER TABLE `store_casing` CHANGE COLUMN `volume_total` `store_volume_id` INT(11) NULL DEFAULT NULL AFTER `volume_note`;
ALTER TABLE `store_casing`	ADD CONSTRAINT `FK_store_casing_store_volume` FOREIGN KEY (`store_volume_id`) REFERENCES `store_volume` (`store_volume_id`) ON UPDATE CASCADE ON DELETE SET NULL;	

ALTER TABLE `store_volume` ADD INDEX `Index 3` (`legacy_casing_id`);
	
ALTER TABLE `store` ADD INDEX `Index 5` (`store_name`);

ALTER TABLE `user_profile` ADD INDEX `Index 2` (`legacy_user_id`);
	
