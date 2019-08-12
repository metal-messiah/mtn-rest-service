CREATE OR REPLACE VIEW dps_stats as
SELECT 
	max(sb.volume_total / COALESCE(st.area_sales, b.default_sales_area)) AS dps_max,
	min(sb.volume_total / COALESCE(st.area_sales, b.default_sales_area)) AS dps_min
FROM store_best_volume sb
JOIN store st ON st.store_id = sb.store_id
LEFT JOIN banner b ON b.banner_id = st.banner_id
WHERE sb.volume_total IS NOT NULL
AND st.store_type = 'ACTIVE'
AND COALESCE(st.area_sales, b.default_sales_area) IS NOT null
AND st.deleted_date IS NULL;

CREATE OR REPLACE VIEW avg_by_banner as
SELECT `st`.`banner_id` AS `banner_id`, 
	round(AVG(`sv`.`volume_total`), -3) AS `volume`, 
	round(AVG(`st`.`area_sales`), -3) AS `area_sales`, 
	round(AVG(`st`.`area_total`), -3) AS `area_total`,
	round(AVG(`sv`.`volume_total` / `st`.`area_sales`), 2) AS `dpsf`
FROM (`store` `st`
JOIN `store_volume` `sv` ON((`sv`.`store_id` = `st`.`store_id`)))
WHERE (`st`.`banner_id` IS NOT NULL)
and st.store_type = 'ACTIVE'
GROUP BY `st`.`banner_id`;

CREATE OR REPLACE VIEW avg_by_banner_in_county as
SELECT `st`.`banner_id` AS `banner_id`,`si`.`state` AS `state`,`si`.`county` AS `county`, 
	round(AVG(`sv`.`volume_total`), -3) AS `volume`, 
	round(AVG(`st`.`area_sales`), -3) AS `area_sales`, 
	round(AVG(`st`.`area_total`), -3) AS `area_total`,
	round(AVG(`sv`.`volume_total` / `st`.`area_sales`), 2) AS `dpsf`
FROM ((`store` `st`
JOIN `site` `si` ON((`st`.`site_id` = `si`.`site_id`)))
JOIN `store_volume` `sv` ON((`sv`.`store_id` = `st`.`store_id`)))
WHERE (`st`.`banner_id` IS NOT NULL)
and st.store_type = 'ACTIVE'
GROUP BY `st`.`banner_id`,`si`.`state`,`si`.`county`;

CREATE OR REPLACE VIEW avg_by_county as
SELECT `si`.`state` AS `state`,`si`.`county` AS `county`, 
	round(AVG(`sv`.`volume_total`), -3) AS `volume`, 
	round(AVG(`st`.`area_sales`), -3) AS `area_sales`, 
	round(AVG(`st`.`area_total`), -3) AS `area_total`,
	round(AVG(`sv`.`volume_total` / `st`.`area_sales`), 2) AS `dpsf`
FROM ((`store` `st`
JOIN `site` `si` ON((`st`.`site_id` = `si`.`site_id`)))
JOIN `store_volume` `sv` ON((`sv`.`store_id` = `st`.`store_id`)))
WHERE store_type = 'ACTIVE'
GROUP BY `si`.`state`,`si`.`county`;

CREATE OR REPLACE VIEW avg_by_fit as
SELECT `st`.`store_fit` AS `store_fit`, 
	round(AVG(`sv`.`volume_total`), -3) AS `volume`, 
	round(AVG(`st`.`area_sales`), -3) AS `area_sales`, 
	round(AVG(`st`.`area_total`), -3) AS `area_total`,
	round(AVG(`sv`.`volume_total` / `st`.`area_sales`), 2) AS `dpsf`
FROM (`store` `st`
JOIN `store_volume` `sv` ON((`sv`.`store_id` = `st`.`store_id`)))
WHERE (`st`.`store_fit` IS NOT NULL)
and st.store_type = 'ACTIVE'
GROUP BY `st`.`store_fit`;

CREATE OR REPLACE VIEW avg_by_fit_in_county AS 
SELECT `st`.`store_fit` AS `store_fit`,`si`.`state` AS `state`,`si`.`county` AS `county`, 
	round(AVG(`sv`.`volume_total`), -3) AS `volume`, 
	round(AVG(`st`.`area_sales`), -3) AS `area_sales`, 
	round(AVG(`st`.`area_total`), -3) AS `area_total`,
	round(AVG(`sv`.`volume_total` / `st`.`area_sales`), 2) AS `dpsf`
FROM ((`store` `st`
JOIN `site` `si` ON((`st`.`site_id` = `si`.`site_id`)))
JOIN `store_volume` `sv` ON((`sv`.`store_id` = `st`.`store_id`)))
WHERE (`st`.`store_fit` IS NOT NULL)
and st.store_type = 'ACTIVE'
GROUP BY `st`.`store_fit`,`si`.`state`,`si`.`county`;



