CREATE OR REPLACE VIEW banner_source_summary as
SELECT 
	`bs`.`source_id` AS `source_id`,
	`bs`.`source_name` AS `source_name`,
	`bs`.`source_banner_name` AS `source_banner_name`,
	`t`.`total_store_sources` AS `total_store_sources`, 
	COALESCE(`m`.`matched_store_sources`,0) AS `matched_store_sources`, 
	ROUND((COALESCE(`m`.`matched_store_sources`,0) / `t`.`total_store_sources`),2) AS `percent_complete`, 
	IF((`bs`.`source_deleted_date` IS NOT NULL),'DELETED', IF((`m`.`matched_store_sources` = `t`.`total_store_sources`),'COMPLETE','INCOMPLETE')) AS `matching_status`,
	`bs`.`banner_id` AS `banner_id`,
	`b`.`banner_name` AS `banner_name`,
	`b`.`logo_file_name` AS `logo_file_name`,
	`bs`.`validated_date` AS `validated_date`,
	`bs`.`source_url` AS `source_url`
FROM (((`banner_source` `bs`
LEFT JOIN `banner` `b` ON((`b`.`banner_id` = `bs`.`banner_id`)))
LEFT JOIN (
	SELECT `bs`.`source_id` AS `source_id`, COUNT(0) AS `total_store_sources`
	FROM (`banner_source` `bs`
	JOIN `store_source` `ss` ON((`ss`.`banner_source_id` = `bs`.`source_id`)))
	GROUP BY `bs`.`source_id`,`bs`.`source_banner_name`
) `t` ON((`t`.`source_id` = `bs`.`source_id`)))
LEFT JOIN (
	SELECT `bs`.`source_id` AS `source_id`, COUNT(0) AS `matched_store_sources`
	FROM (`banner_source` `bs`
	JOIN `store_source` `ss` ON((`ss`.`banner_source_id` = `bs`.`source_id`)))
	WHERE ss.validated_date IS NOT NULL
	GROUP BY `bs`.`source_id`,`bs`.`source_banner_name`
) `m` ON((`m`.`source_id` = `bs`.`source_id`)))
WHERE ISNULL(`bs`.`deleted_date`)
GROUP BY `bs`.`source_id`,`bs`.`source_banner_name`
ORDER BY `bs`.`source_name`,`bs`.`source_banner_name`