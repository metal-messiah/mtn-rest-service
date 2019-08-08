CREATE OR REPLACE VIEW avg_sales_area_percent_by_banner AS
select st.banner_id, round(avg(st.area_sales / st.area_total), 2) as avg_sales_percent
from store st
WHERE (st.area_sales is not null and st.area_sales != 0) -- valid sales area
AND (st.area_total is not null and st.area_total != 0) -- valid total area
AND (st.area_total >= st.area_sales) -- total area must be greater than sales area
and st.banner_id is not NULL -- only for banners
AND st.deleted_date IS null
group by st.banner_id;

CREATE OR REPLACE VIEW avg_sales_area_percent_by_fit AS 
select st.store_fit, round(avg(st.area_sales / st.area_total), 2) as avg_sales_percent
from store st 
WHERE (st.area_sales is not null and st.area_sales != 0) -- valid sales area
AND (st.area_total is not null and st.area_total != 0) -- valid total area
and st.area_total >= st.area_sales -- total area must be greater than sales area
and st.store_fit is not NULL -- only for fit categories
AND st.deleted_date IS null
group by st.store_fit;