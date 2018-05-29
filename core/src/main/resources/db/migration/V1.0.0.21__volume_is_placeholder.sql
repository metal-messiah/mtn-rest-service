ALTER TABLE `store_casing`
	ADD COLUMN `volume_is_placeholder` TINYINT NULL DEFAULT '0' AFTER `volume_confidence`;