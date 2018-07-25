ALTER TABLE `shopping_center`
ADD COLUMN `center_type` VARCHAR(255) NULL DEFAULT NULL AFTER `owner`;

update shopping_center sc
join most_recent_shopping_center_survey scs on sc.shopping_center_id = scs.shopping_center_id
set sc.center_type = scs.center_type
where scs.center_type is not null;

ALTER TABLE `shopping_center_survey`
	DROP COLUMN `center_type`,
	DROP COLUMN `flow_has_landscaping`,
	DROP COLUMN `flow_has_stop_signs`,
	DROP COLUMN `flow_has_speed_bumps`;
	
UPDATE extraction_field SET `table`='ShoppingCenter', field_mapping='store.site.shoppingCenter.centerType' WHERE extraction_field_id=151;
DELETE e FROM extraction_field e WHERE e.extraction_field_id IN (152, 153, 154);

