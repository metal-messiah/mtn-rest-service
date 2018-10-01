ALTER TABLE `store`
	ADD COLUMN `store_fit` VARCHAR(255) NULL DEFAULT NULL AFTER `date_opened`,
	ADD COLUMN `store_format` VARCHAR(255) NULL DEFAULT NULL AFTER `store_fit`,
	ADD COLUMN `store_is_open_24` TINYINT(4) NULL DEFAULT NULL AFTER `store_format`,
	ADD COLUMN `area_sales` INT(11) NULL DEFAULT NULL AFTER `store_is_open_24`,
	ADD COLUMN `area_sales_percent_of_total` FLOAT NULL DEFAULT NULL AFTER `area_sales`,
	ADD COLUMN `area_total` INT(11) NULL DEFAULT NULL AFTER `area_sales_percent_of_total`,
	ADD COLUMN `area_is_estimate` TINYINT(4) NULL DEFAULT NULL AFTER `area_total`,
	ADD COLUMN `natural_foods_are_integrated` TINYINT(4) NULL DEFAULT NULL AFTER `area_is_estimate`;

update store st
join most_recent_store_survey mrss on st.store_id = mrss.store_id
set st.store_fit = mrss.store_fit,
	st.store_format = mrss.store_format,
	st.store_is_open_24 = mrss.store_is_open_24,
	st.area_sales = mrss.area_sales,
	st.area_sales_percent_of_total = mrss.area_sales_percent_of_total,
	st.area_total = mrss.area_total,
	st.area_is_estimate = mrss.area_is_estimate,
	st.natural_foods_are_integrated = mrss.natural_foods_are_integrated
where mrss.store_survey_id is not null;

ALTER TABLE `store_survey`
	DROP COLUMN `store_fit`,
	DROP COLUMN `store_format`,
	DROP COLUMN `store_is_open_24`,
	DROP COLUMN `area_sales`,
	DROP COLUMN `area_sales_percent_of_total`,
	DROP COLUMN `area_total`,
	DROP COLUMN `area_is_estimate`,
	DROP COLUMN `natural_foods_are_integrated`;
	
UPDATE `extraction_field` SET `table`='Store', `field_mapping`='store.fit' WHERE  `extraction_field_id`=74;
UPDATE `extraction_field` SET `table`='Store', `field_mapping`='store.format' WHERE  `extraction_field_id`=75;
UPDATE `extraction_field` SET `table`='Store', `field_mapping`='store.areaSales' WHERE  `extraction_field_id`=76;
UPDATE `extraction_field` SET `table`='Store', `field_mapping`='store.areaSalesPercentOfTotal' WHERE  `extraction_field_id`=77;
UPDATE `extraction_field` SET `table`='Store', `field_mapping`='store.areaTotal' WHERE  `extraction_field_id`=78;
UPDATE `extraction_field` SET `table`='Store', `field_mapping`='store.areaIsEstimate' WHERE  `extraction_field_id`=79;
UPDATE `extraction_field` SET `table`='Store', `field_mapping`='store.storeIsOpen24' WHERE  `extraction_field_id`=80;
UPDATE `extraction_field` SET `table`='Store', `field_mapping`='store.naturalFoodsAreIntegrated' WHERE  `extraction_field_id`=81;


