ALTER TABLE `shopping_center_survey`
	ADD COLUMN `flow_rating` ENUM('POOR','FAIR','AVERAGE','GOOD','EXCELLENT') NULL DEFAULT NULL AFTER `flow_has_one_way_aisles`;
