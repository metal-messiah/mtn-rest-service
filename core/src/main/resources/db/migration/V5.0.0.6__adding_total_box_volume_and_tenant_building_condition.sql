ALTER TABLE `shopping_center_casing`
	ADD COLUMN `rating_tenant_buildings` ENUM('POOR','FAIR','AVERAGE','GOOD','EXCELLENT') NULL DEFAULT NULL AFTER `rating_synergy`;
	
ALTER TABLE `store_volume`
	ADD COLUMN `volume_box_total` INT(11) NULL AFTER `volume_total`;
