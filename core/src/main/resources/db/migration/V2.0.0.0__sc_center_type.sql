ALTER TABLE `shopping_center`
ADD COLUMN `center_type` VARCHAR(255) NULL DEFAULT NULL AFTER `owner`;

create temporary table temp_latest_sc_survey as
SELECT s1.*
FROM `shopping_center_survey` `s1`
LEFT JOIN `shopping_center_survey` `s2` ON(((`s1`.`shopping_center_id` = `s2`.`shopping_center_id`) AND ((`s1`.`shopping_center_survey_date` < `s2`.`shopping_center_survey_date`) OR ((`s1`.`shopping_center_survey_date` = `s2`.`shopping_center_survey_date`) AND (`s1`.`shopping_center_survey_id` < `s2`.`shopping_center_survey_id`)))))
WHERE ISNULL(`s2`.`shopping_center_id`);

update shopping_center sc
join temp_latest_sc_survey scs on sc.shopping_center_id = scs.shopping_center_id
set sc.center_type = scs.center_type
where scs.center_type is not null;

ALTER TABLE `shopping_center_survey`
	DROP COLUMN `center_type`,
	DROP COLUMN `flow_has_landscaping`,
	DROP COLUMN `flow_has_stop_signs`,
	DROP COLUMN `flow_has_speed_bumps`;
	
UPDATE extraction_field SET `table`='ShoppingCenter', field_mapping='store.site.shoppingCenter.centerType' WHERE extraction_field_id=151;
DELETE e FROM extraction_field e WHERE e.extraction_field_id IN (152, 153, 154);

