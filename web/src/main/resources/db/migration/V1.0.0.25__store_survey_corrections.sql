ALTER TABLE store_survey
  ADD COLUMN store_survey_note TEXT;
ALTER TABLE store_survey
  RENAME fit TO store_fit;
ALTER TABLE store_survey
  RENAME format TO store_format;
ALTER TABLE store_survey
  ADD COLUMN store_is_open_24 BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN natural_foods_are_integrated BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN register_count_normal INT;
ALTER TABLE store_survey
  ADD COLUMN register_count_express INT;
ALTER TABLE store_survey
  ADD COLUMN register_count_self_checkout INT;
ALTER TABLE store_survey
  ADD COLUMN fuel_dispenser_count INT;
ALTER TABLE store_survey
  ADD COLUMN fuel_is_open_24 BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN pharmacy_is_open_24 BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN pharmacy_has_drive_through BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN department_bakery BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN department_bank BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN department_beer BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN department_bulk BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN department_cheese BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN department_coffee BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN department_deli BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN department_expanded_gm BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN department_extensive_prepared_foods BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN department_floral BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN department_fuel BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN department_hot_bar BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN department_in_store_restaurant BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN department_liquor BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN department_meat BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN department_natural BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN department_olive_bar BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN department_pharmacy BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN department_prepared_foods BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN department_salad_bar BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN department_seafood BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN department_seating BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN department_sushi BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN department_wine BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN accessibility_farthest_from_entrance BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN accessibility_main_intersection_has_traffic_light BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN accessibility_main_intersection_needs_traffic_light BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN accessibility_multiple_retailers_before_site BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN accessibility_set_back_twice_parking_length BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN accessibility_rating VARCHAR(64);
ALTER TABLE store_survey
  ADD COLUMN parking_outparcels_interfere_with_parking BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN parking_direct_access_to_parking BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN parking_small_parking_field BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN parking_has_t_spaces BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN parking_rating VARCHAR(64);
ALTER TABLE store_survey
  ADD COLUMN visibility_hill_depression_blocks_view BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN visibility_outparcels_block_view BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN visibility_sign_on_main BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN visibility_store_faces_main_road BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN visibility_trees_block_view BOOLEAN DEFAULT FALSE;
ALTER TABLE store_survey
  ADD COLUMN visibilty_rating VARCHAR(64);
ALTER TABLE store_survey
  ADD COLUMN seasonality_jan INT;
ALTER TABLE store_survey
  ADD COLUMN seasonality_feb INT;
ALTER TABLE store_survey
  ADD COLUMN seasonality_mar INT;
ALTER TABLE store_survey
  ADD COLUMN seasonality_apr INT;
ALTER TABLE store_survey
  ADD COLUMN seasonality_may INT;
ALTER TABLE store_survey
  ADD COLUMN seasonality_jun INT;
ALTER TABLE store_survey
  ADD COLUMN seasonality_jul INT;
ALTER TABLE store_survey
  ADD COLUMN seasonality_aug INT;
ALTER TABLE store_survey
  ADD COLUMN seasonality_sep INT;
ALTER TABLE store_survey
  ADD COLUMN seasonality_oct INT;
ALTER TABLE store_survey
  ADD COLUMN seasonality_nov INT;
ALTER TABLE store_survey
  ADD COLUMN seasonality_dec INT;
ALTER TABLE store_survey
  ADD COLUMN version INT NOT NULL DEFAULT 1;
ALTER TABLE store_survey
  ADD COLUMN legacy_casing_id INT;