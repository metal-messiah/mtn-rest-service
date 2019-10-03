
CREATE OR REPLACE VIEW `avg_by_banner` AS select `st`.`banner_id` AS `banner_id`,round(avg(`sv`.`volume_total`),-(3)) AS `volume`,round(avg(`st`.`area_sales`),-(3)) AS `area_sales`,round(avg(`st`.`area_total`),-(3)) AS `area_total`,round(avg((`sv`.`volume_total` / `st`.`area_sales`)),2) AS `dpsf` from (`store` `st` join `store_volume` `sv` on((`sv`.`store_id` = `st`.`store_id`))) where ((`st`.`banner_id` is not null) and (`st`.`store_type` = 'ACTIVE') and (`st`.`is_float` = 0)) group by `st`.`banner_id`;

CREATE OR REPLACE VIEW `avg_by_banner_in_county` AS select uuid() AS `id`,`st`.`banner_id` AS `banner_id`,`si`.`state` AS `state`,`si`.`county` AS `county`,round(avg(`sv`.`volume_total`),-(3)) AS `volume`,round(avg(`st`.`area_sales`),-(3)) AS `area_sales`,round(avg(`st`.`area_total`),-(3)) AS `area_total`,round(avg((`sv`.`volume_total` / `st`.`area_sales`)),2) AS `dpsf` from ((`store` `st` join `site` `si` on((`st`.`site_id` = `si`.`site_id`))) join `store_volume` `sv` on((`sv`.`store_id` = `st`.`store_id`))) where ((`st`.`banner_id` is not null) and (`st`.`store_type` = 'ACTIVE') and (`st`.`is_float` = 0)) group by `st`.`banner_id`,`si`.`state`,`si`.`county`;

CREATE OR REPLACE VIEW `avg_by_county` AS select uuid() AS `id`,`si`.`state` AS `state`,`si`.`county` AS `county`,round(avg(`sv`.`volume_total`),-(3)) AS `volume`,round(avg(`st`.`area_sales`),-(3)) AS `area_sales`,round(avg(`st`.`area_total`),-(3)) AS `area_total`,round(avg((`sv`.`volume_total` / `st`.`area_sales`)),2) AS `dpsf`,round(min((`sv`.`volume_total` / `st`.`area_sales`)),2) AS `dpsf_min`,round(max((`sv`.`volume_total` / `st`.`area_sales`)),2) AS `dpsf_max` from ((`store` `st` join `site` `si` on((`st`.`site_id` = `si`.`site_id`))) join `store_volume` `sv` on((`sv`.`store_id` = `st`.`store_id`))) where ((`st`.`store_type` = 'ACTIVE') and (`st`.`is_float` = 0)) group by `si`.`state`,`si`.`county`;

CREATE OR REPLACE VIEW `avg_by_fit` AS select `st`.`store_fit` AS `store_fit`,round(avg(`sv`.`volume_total`),-(3)) AS `volume`,round(avg(`st`.`area_sales`),-(3)) AS `area_sales`,round(avg(`st`.`area_total`),-(3)) AS `area_total`,round(avg((`sv`.`volume_total` / `st`.`area_sales`)),2) AS `dpsf` from (`store` `st` join `store_volume` `sv` on((`sv`.`store_id` = `st`.`store_id`))) where ((`st`.`store_fit` is not null) and (`st`.`store_type` = 'ACTIVE') and (`st`.`is_float` = 0)) group by `st`.`store_fit`;

CREATE OR REPLACE VIEW `avg_by_fit_in_county` AS select uuid() AS `id`,`st`.`store_fit` AS `store_fit`,`si`.`state` AS `state`,`si`.`county` AS `county`,round(avg(`sv`.`volume_total`),-(3)) AS `volume`,round(avg(`st`.`area_sales`),-(3)) AS `area_sales`,round(avg(`st`.`area_total`),-(3)) AS `area_total`,round(avg((`sv`.`volume_total` / `st`.`area_sales`)),2) AS `dpsf` from ((`store` `st` join `site` `si` on((`st`.`site_id` = `si`.`site_id`))) join `store_volume` `sv` on((`sv`.`store_id` = `st`.`store_id`))) where ((`st`.`store_fit` is not null) and (`st`.`store_type` = 'ACTIVE') and (`st`.`is_float` = 0)) group by `st`.`store_fit`,`si`.`state`,`si`.`county`;

CREATE OR REPLACE VIEW `avg_sales_area_percent_by_banner` AS select `st`.`banner_id` AS `banner_id`,round(avg((`st`.`area_sales` / `st`.`area_total`)),2) AS `avg_sales_percent` from `store` `st` where ((`st`.`area_sales` is not null) and (`st`.`area_sales` <> 0) and (`st`.`area_total` is not null) and (`st`.`area_total` <> 0) and (`st`.`area_total` >= `st`.`area_sales`) and (`st`.`banner_id` is not null) and isnull(`st`.`deleted_date`) and (`st`.`is_float` = 0)) group by `st`.`banner_id`;

CREATE OR REPLACE VIEW `avg_sales_area_percent_by_fit` AS select `st`.`store_fit` AS `store_fit`,round(avg((`st`.`area_sales` / `st`.`area_total`)),2) AS `avg_sales_percent` from `store` `st` where ((`st`.`area_sales` is not null) and (`st`.`area_sales` <> 0) and (`st`.`area_total` is not null) and (`st`.`area_total` <> 0) and (`st`.`area_total` >= `st`.`area_sales`) and (`st`.`store_fit` is not null) and isnull(`st`.`deleted_date`) and (`st`.`is_float` = 0)) group by `st`.`store_fit`;

CREATE OR REPLACE VIEW `dps_stats` AS select max((`sb`.`volume_total` / coalesce(`st`.`area_sales`,`b`.`default_sales_area`))) AS `dps_max`,min((`sb`.`volume_total` / coalesce(`st`.`area_sales`,`b`.`default_sales_area`))) AS `dps_min` from ((`store_best_volume` `sb` join `store` `st` on((`st`.`store_id` = `sb`.`store_id`))) left join `banner` `b` on((`b`.`banner_id` = `st`.`banner_id`))) where ((`sb`.`volume_total` is not null) and (`st`.`store_type` = 'ACTIVE') and (coalesce(`st`.`area_sales`,`b`.`default_sales_area`) is not null) and isnull(`st`.`deleted_date`));

CREATE OR REPLACE VIEW empty_sites AS
SELECT
	si.site_id AS site_id,
	si.latitude,
	si.longitude,
	si.address_1 AS address,
	si.city,
	si.county,
	si.state,
	si.postal_code,
	si.intersection_type,
	si.quad,
	si.intersection_street_primary AS primary_intersection_street,
	si.intersection_street_secondary AS secondary_intersection_street,
	si.cbsa_id,
	f.store_id AS planned_store_id,
	f.store_name AS planned_store_name,
	f.date_opened AS planned_store_opening_date,
	f.area_total AS planned_store_total_area
FROM site si
LEFT JOIN (
	SELECT *
	FROM store st
	WHERE st.store_type = 'ACTIVE'
	AND st.deleted_date IS null
) a ON a.site_id = si.site_id
LEFT JOIN (
	SELECT *
	FROM store st
	WHERE st.store_type = 'FUTURE'
	AND st.deleted_date IS null
) f ON f.site_id = si.site_id
WHERE (si.backfilled_non_grocery IS NULL OR si.backfilled_non_grocery = 0)
AND si.deleted_date IS null
AND a.store_id IS NULL;