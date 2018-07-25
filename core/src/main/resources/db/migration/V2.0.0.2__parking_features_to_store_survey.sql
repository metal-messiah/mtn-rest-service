ALTER TABLE `store_survey`
	ADD COLUMN `parking_has_angled_spaces` TINYINT(4) NULL DEFAULT NULL AFTER `parking_has_t_spaces`,
	ADD COLUMN `parking_has_parking_hog` TINYINT(4) NULL DEFAULT NULL AFTER `parking_has_angled_spaces`,
	ADD COLUMN `parking_is_poorly_lit` TINYINT(4) NULL DEFAULT NULL AFTER `parking_has_parking_hog`;

update shopping_center_survey scs
join shopping_center_casing scc on scc.shopping_center_survey_id = scs.shopping_center_survey_id
join store_casing stc on stc.shopping_center_casing_id = scc.shopping_center_casing_id
join store_survey sts on sts.store_survey_id = stc.store_survey_id
set sts.parking_has_angled_spaces = scs.parking_has_angled_spaces
where scs.parking_has_angled_spaces is not null;

update shopping_center_survey scs
join shopping_center_casing scc on scc.shopping_center_survey_id = scs.shopping_center_survey_id
join store_casing stc on stc.shopping_center_casing_id = scc.shopping_center_casing_id
join store_survey sts on sts.store_survey_id = stc.store_survey_id
set sts.parking_has_parking_hog = scs.parking_has_parking_hog
where scs.parking_has_parking_hog is not null;

update shopping_center_survey scs
join shopping_center_casing scc on scc.shopping_center_survey_id = scs.shopping_center_survey_id
join store_casing stc on stc.shopping_center_casing_id = scc.shopping_center_casing_id
join store_survey sts on sts.store_survey_id = stc.store_survey_id
set sts.parking_is_poorly_lit = scs.parking_is_poorly_lit
where scs.parking_is_poorly_lit is not null;

ALTER TABLE `shopping_center_survey`
	DROP COLUMN `parking_has_angled_spaces`,
	DROP COLUMN `parking_has_parking_hog`,
	DROP COLUMN `parking_is_poorly_lit`;
	
UPDATE `extraction_field` SET `table`='StoreSurvey', `field_mapping`='storeSurvey.parkingHasAngledSpaces' WHERE  `extraction_field_id`=156;
UPDATE `extraction_field` SET `table`='StoreSurvey', `field_mapping`='storeSurvey.parkingHasParkingHog' WHERE  `extraction_field_id`=157;
UPDATE `extraction_field` SET `table`='StoreSurvey', `field_mapping`='storeSurvey.parkingIsPoorlyLit' WHERE  `extraction_field_id`=158;
