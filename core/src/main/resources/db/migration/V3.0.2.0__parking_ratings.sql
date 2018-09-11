update shopping_center_casing scc
join store_casing stc on stc.shopping_center_casing_id = scc.shopping_center_casing_id
join store_survey sts on sts.store_survey_id = stc.store_survey_id
set sts.parking_rating = scc.rating_parking_lot
where sts.parking_rating is null
and scc.rating_parking_lot is not null;

alter table shopping_center_casing
drop column rating_parking_lot;

alter table store_survey
drop column accessibility_main_intersection_needs_traffic_light;
