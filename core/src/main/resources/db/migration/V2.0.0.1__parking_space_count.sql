ALTER TABLE `store_survey`
	ADD COLUMN `parking_space_count` INT(11) NULL DEFAULT NULL AFTER `parking_rating`;

update shopping_center_survey scs
join shopping_center_casing scc on scc.shopping_center_survey_id = scs.shopping_center_survey_id
join store_casing stc on stc.shopping_center_casing_id = scc.shopping_center_casing_id
join store_survey sts on sts.store_survey_id = stc.store_survey_id
set sts.parking_space_count = scs.parking_space_count
where scs.parking_space_count is not null
and scs.parking_space_count > 0;

ALTER TABLE `shopping_center_survey`
	DROP COLUMN `parking_space_count`;
	
UPDATE extraction_field SET `table`='StoreSurvey', `field_mapping`='storeSurvey.parkingSpaceCount' WHERE  `extraction_field_id`=159;