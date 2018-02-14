DROP TABLE IF EXISTS api_client;
CREATE TABLE IF NOT EXISTS api_client (
  api_client_id int NOT NULL AUTO_INCREMENT,
  storeName varchar(64) NOT NULL,
  client_id varchar(64) NOT NULL,
  client_secret varchar(64) NOT NULL,
  PRIMARY KEY (api_client_id),
  UNIQUE(client_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS auth_group;
CREATE TABLE IF NOT EXISTS auth_group (
  auth_group_id int NOT NULL AUTO_INCREMENT,
  display_name varchar(64) NOT NULL,
  description varchar(512) DEFAULT NULL,
  created_by int NOT NULL,
  created_date timestamp NULL DEFAULT NULL,
  deleted_by int DEFAULT NULL,
  deleted_date timestamp NULL DEFAULT NULL,
  updated_by int NOT NULL,
  updated_date timestamp NULL DEFAULT NULL,
  version int DEFAULT NULL,
  PRIMARY KEY (auth_group_id),
  UNIQUE(display_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS auth_permission;
CREATE TABLE IF NOT EXISTS auth_permission (
  auth_permission_id int NOT NULL AUTO_INCREMENT,
  system_name varchar(64) NOT NULL,
  display_name varchar(64) NOT NULL,
  description varchar(512) DEFAULT NULL,
  subject varchar(64) DEFAULT NULL,
  action varchar(16) DEFAULT NULL,
  created_by int NOT NULL DEFAULT '1',
  created_date timestamp NULL DEFAULT NULL,
  deleted_by int DEFAULT NULL,
  deleted_date timestamp NULL DEFAULT NULL,
  updated_by int NOT NULL DEFAULT '1',
  updated_date timestamp NULL DEFAULT NULL,
  version int NOT NULL DEFAULT '1',
  PRIMARY KEY (auth_permission_id),
  UNIQUE(system_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS auth_role;
CREATE TABLE IF NOT EXISTS auth_role (
  auth_role_id int NOT NULL AUTO_INCREMENT,
  display_name varchar(64) NOT NULL,
  description varchar(512) DEFAULT NULL,
  created_by int NOT NULL,
  created_date timestamp NULL DEFAULT NULL,
  deleted_by int DEFAULT NULL,
  deleted_date timestamp NULL DEFAULT NULL,
  updated_by int NOT NULL,
  updated_date timestamp NULL DEFAULT NULL,
  version int NOT NULL DEFAULT '1',
  PRIMARY KEY (auth_role_id),
  UNIQUE(display_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS auth_role_auth_permission;
CREATE TABLE IF NOT EXISTS auth_role_auth_permission (
  auth_role_id int DEFAULT NULL,
  auth_permission_id int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS company;
CREATE TABLE IF NOT EXISTS company (
  company_id int NOT NULL AUTO_INCREMENT,
  company_name varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  website_url varchar(255) DEFAULT NULL,
  parent_company_id int DEFAULT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by int DEFAULT NULL,
  updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_by int DEFAULT NULL,
  deleted_date TIMESTAMP NULL DEFAULT NULL,
  deleted_by int DEFAULT NULL,
  version int NOT NULL DEFAULT '1',
  PRIMARY KEY (company_id),
  KEY Index_2 (company_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS banner;
CREATE TABLE IF NOT EXISTS banner (
  banner_id int NOT NULL AUTO_INCREMENT,
  banner_name varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  company_id int DEFAULT NULL,
  is_historical tinyint NOT NULL,
  default_store_fit varchar(128) DEFAULT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by int DEFAULT NULL,
  updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_by int DEFAULT NULL,
  deleted_date TIMESTAMP NULL DEFAULT NULL,
  deleted_by int DEFAULT NULL,
  default_sales_area int DEFAULT NULL,
  PRIMARY KEY (banner_id),
  KEY index_banner_name (banner_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

DROP TABLE IF EXISTS grocer_class_commentary;
CREATE TABLE IF NOT EXISTS grocer_class_commentary (
  category_name varchar(50) DEFAULT NULL,
  score int DEFAULT NULL,
  commentary varchar(50) DEFAULT NULL,
  KEY Index_1 (category_name),
  KEY Index_2 (score)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS interaction;
CREATE TABLE IF NOT EXISTS interaction (
  interaction_id int NOT NULL AUTO_INCREMENT,
  project_id int DEFAULT '0',
  shopping_center_survey_id int DEFAULT '0',
  shopping_center_casing_id int DEFAULT '0',
  store_survey_id int DEFAULT '0',
  store_casing_id int DEFAULT '0',
  interaction_date DATETIME DEFAULT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by int NOT NULL DEFAULT '0',
  updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_by int NOT NULL DEFAULT '0',
  deleted_date TIMESTAMP NULL DEFAULT NULL,
  deleted_by int DEFAULT NULL,
  version int NOT NULL DEFAULT '1',
  legacy_casing_id int DEFAULT '0',
  shopping_center_id int DEFAULT '0',
  store_id int DEFAULT '0',
  PRIMARY KEY (interaction_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS project;
CREATE TABLE IF NOT EXISTS project (
  project_id int NOT NULL AUTO_INCREMENT,
  project_name varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  metro_area varchar(255) DEFAULT NULL,
  client_name varchar(255) DEFAULT NULL,
  project_year smallint DEFAULT NULL,
  project_month tinyint DEFAULT NULL,
  is_active int NOT NULL,
  is_primary_data int DEFAULT '0',
  date_started date DEFAULT NULL,
  date_completed date DEFAULT NULL,
  source varchar(160) COLLATE utf8_unicode_ci DEFAULT NULL,
  boundary text,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by int NOT NULL DEFAULT '0',
  updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_by int NOT NULL DEFAULT '0',
  deleted_date TIMESTAMP NULL DEFAULT NULL,
  deleted_by int DEFAULT NULL,
  version int NOT NULL DEFAULT '1',
  legacy_project_id int DEFAULT '0',
  data_type varchar(50) DEFAULT NULL,
  PRIMARY KEY (project_id),
  KEY Index_2 (legacy_project_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS shopping_center;
CREATE TABLE IF NOT EXISTS shopping_center (
  shopping_center_id int NOT NULL AUTO_INCREMENT,
  storeName varchar(128) CHARACTER SET latin1 DEFAULT NULL,
  owner varchar(128) CHARACTER SET latin1 DEFAULT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by int NOT NULL,
  updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_by int NOT NULL,
  deleted_date TIMESTAMP NULL DEFAULT NULL,
  deleted_by int DEFAULT NULL,
  version int NOT NULL DEFAULT '1',
  legacy_sc_id int DEFAULT NULL,
  legacy_location_id int DEFAULT NULL,
  PRIMARY KEY (shopping_center_id),
  KEY Index_2 (legacy_location_id),
  KEY Index_3 (legacy_sc_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS shopping_center_access;
CREATE TABLE IF NOT EXISTS shopping_center_access (
  shopping_center_access_id int NOT NULL AUTO_INCREMENT,
  shopping_center_survey_id int NOT NULL DEFAULT '0',
  access_type enum('FRONT_MAIN','SIDE_MAIN','NON_MAIN') COLLATE utf8_unicode_ci NOT NULL,
  has_left_in tinyint DEFAULT '0',
  has_left_out tinyint DEFAULT '0',
  has_traffic_light tinyint DEFAULT '0',
  one_way_road tinyint DEFAULT '0',
  has_right_in tinyint DEFAULT '0',
  has_right_out tinyint DEFAULT '0',
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by int NOT NULL DEFAULT '0',
  updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_by int NOT NULL DEFAULT '0',
  deleted_date TIMESTAMP NULL DEFAULT NULL,
  deleted_by int DEFAULT NULL,
  version int NOT NULL DEFAULT '1',
  legacy_casing_id int DEFAULT NULL,
  PRIMARY KEY (shopping_center_access_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS shopping_center_casing;
CREATE TABLE IF NOT EXISTS shopping_center_casing (
  shopping_center_casing_id int NOT NULL AUTO_INCREMENT,
  shopping_center_id int NOT NULL DEFAULT '0',
  shopping_center_casing_date date DEFAULT NULL,
  shopping_center_casing_notes text,
  rating_parking_lot enum('POOR','FAIR','AVERAGE','GOOD','EXCELLENT') DEFAULT NULL,
  rating_buildings enum('POOR','FAIR','AVERAGE','GOOD','EXCELLENT') DEFAULT NULL,
  rating_lighting enum('POOR','FAIR','AVERAGE','GOOD','EXCELLENT') DEFAULT NULL,
  rating_synergy enum('POOR','FAIR','AVERAGE','GOOD','EXCELLENT') DEFAULT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by int NOT NULL DEFAULT '0',
  updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_by int NOT NULL DEFAULT '0',
  deleted_date TIMESTAMP NULL DEFAULT NULL,
  deleted_by int DEFAULT NULL,
  version int NOT NULL DEFAULT '0',
  legacy_casing_id int DEFAULT '0',
  PRIMARY KEY (shopping_center_casing_id),
  KEY Index_3 (legacy_casing_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS shopping_center_survey;
CREATE TABLE IF NOT EXISTS shopping_center_survey (
  shopping_center_survey_id int NOT NULL AUTO_INCREMENT,
  shopping_center_id int NOT NULL DEFAULT '0',
  shopping_center_survey_date date DEFAULT NULL,
  center_type varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  shopping_center_survey_notes text,
  flow_has_landscaping tinyint DEFAULT NULL,
  flow_has_speed_bumps tinyint DEFAULT NULL,
  flow_has_stop_signs tinyint DEFAULT NULL,
  flow_has_one_way_aisles tinyint DEFAULT NULL,
  parking_has_angled_spaces tinyint DEFAULT NULL,
  parking_has_parking_hog tinyint DEFAULT NULL,
  parking_is_poorly_lit tinyint DEFAULT NULL,
  parking_space_count int DEFAULT NULL,
  tenant_occupied_count int DEFAULT NULL,
  tenant_vacant_count int DEFAULT NULL,
  sq_ft_percent_occupied float DEFAULT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by int NOT NULL DEFAULT '0',
  updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_by int NOT NULL DEFAULT '0',
  deleted_date TIMESTAMP NULL DEFAULT NULL,
  deleted_by int DEFAULT NULL,
  version int NOT NULL DEFAULT '0',
  legacy_casing_id int DEFAULT '0',
  PRIMARY KEY (shopping_center_survey_id),
  KEY Index_3 (legacy_casing_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS shopping_center_tenant;
CREATE TABLE IF NOT EXISTS shopping_center_tenant (
  shopping_center_tenant_id int NOT NULL AUTO_INCREMENT,
  shopping_center_survey_id int NOT NULL,
  tenant_name varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  tenant_sqft int DEFAULT NULL,
  is_anchor tinyint DEFAULT NULL,
  is_outparcel tinyint DEFAULT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by int NOT NULL DEFAULT '0',
  updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_by int NOT NULL DEFAULT '0',
  deleted_date TIMESTAMP NULL DEFAULT NULL,
  deleted_by int DEFAULT NULL,
  version int NOT NULL DEFAULT '1',
  legacy_casing_id int DEFAULT NULL,
  PRIMARY KEY (shopping_center_tenant_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS site;
CREATE TABLE IF NOT EXISTS site (
  site_id int NOT NULL AUTO_INCREMENT,
  shopping_center_id int NOT NULL DEFAULT '0',
  latitude double NOT NULL,
  longitude double NOT NULL,
  storeType enum('PLACEHOLDER','ANCHOR','DEFAULT') DEFAULT 'DEFAULT',
  footprint_sqft int DEFAULT NULL,
  position_in_center varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  address_1 varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  address_2 varchar(255) DEFAULT NULL,
  city varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  county varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  state varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  postal_code varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  country varchar(255) DEFAULT NULL,
  intersection_type varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  quad varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  intersection_street_primary varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  intersection_street_secondary varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by int DEFAULT NULL,
  updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_by int DEFAULT NULL,
  deleted_date TIMESTAMP NULL DEFAULT NULL,
  deleted_by int DEFAULT NULL,
  version int NOT NULL DEFAULT '0',
  legacy_location_id int DEFAULT '0',
  cbsa_id int DEFAULT NULL,
  PRIMARY KEY (site_id),
  KEY Index_2 (legacy_location_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS source;
CREATE TABLE IF NOT EXISTS source (
  source_id int NOT NULL AUTO_INCREMENT,
  source_name varchar(255) CHARACTER SET latin1 NOT NULL DEFAULT '',
  source_native_id varchar(128) CHARACTER SET latin1 NOT NULL DEFAULT '',
  source_url text,
  reference_id int NOT NULL,
  reference_type enum('STORE','SHOPPING_CENTER') NOT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by int DEFAULT NULL,
  updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_by int DEFAULT NULL,
  deleted_date TIMESTAMP NULL DEFAULT NULL,
  deleted_by int DEFAULT NULL,
  version int NOT NULL DEFAULT '1',
  legacy_source_id int DEFAULT NULL,
  PRIMARY KEY (source_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS store;
CREATE TABLE IF NOT EXISTS store (
  store_id int NOT NULL AUTO_INCREMENT,
  site_id int NOT NULL DEFAULT '0',
  store_type enum('HISTORICAL','ACTIVE','FUTURE','HYPOTHETICAL') DEFAULT NULL,
  store_name varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  store_number varchar(48) DEFAULT NULL,
  date_closed date DEFAULT NULL,
  date_opened date DEFAULT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by int DEFAULT NULL,
  updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_by int DEFAULT NULL,
  deleted_date TIMESTAMP NULL DEFAULT NULL,
  deleted_by int DEFAULT NULL,
  version int NOT NULL DEFAULT '1',
  legacy_location_id int DEFAULT NULL,
  banner_id int DEFAULT NULL,
  base_rent int DEFAULT NULL,
  lease_expiration_date date DEFAULT NULL,
  PRIMARY KEY (store_id),
  KEY Index_3 (legacy_location_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS store_casing;
CREATE TABLE IF NOT EXISTS store_casing (
  store_casing_id int NOT NULL AUTO_INCREMENT,
  store_id int NOT NULL,
  store_casing_date date DEFAULT NULL,
  store_casing_notes text,
  store_status varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  condition_ceiling enum('POOR','FAIR','AVERAGE','GOOD','EXCELLENT') DEFAULT NULL,
  condition_checkstands enum('POOR','FAIR','AVERAGE','GOOD','EXCELLENT') DEFAULT NULL,
  condition_floors enum('POOR','FAIR','AVERAGE','GOOD','EXCELLENT') DEFAULT NULL,
  condition_frozen_refrigerated enum('POOR','FAIR','AVERAGE','GOOD','EXCELLENT') DEFAULT NULL,
  condition_shelving_gondolas enum('POOR','FAIR','AVERAGE','GOOD','EXCELLENT') DEFAULT NULL,
  condition_walls enum('POOR','FAIR','AVERAGE','GOOD','EXCELLENT') DEFAULT NULL,
  fuel_gallons_weekly int DEFAULT NULL,
  pharmacy_scripts_weekly int DEFAULT NULL,
  pharmacy_avg_dollars_per_script float DEFAULT NULL,
  pharmacy_pharmacist_count int DEFAULT NULL,
  pharmacy_technician_count int DEFAULT NULL,
  volume_grocery int DEFAULT NULL,
  volume_percent_grocery float DEFAULT NULL,
  volume_meat int DEFAULT NULL,
  volume_percent_meat float DEFAULT NULL,
  volume_non_food int DEFAULT NULL,
  volume_percent_non_food float DEFAULT NULL,
  volume_other int DEFAULT NULL,
  volume_percent_other float DEFAULT NULL,
  volume_produce int DEFAULT NULL,
  volume_percent_produce float DEFAULT NULL,
  volume_plus_minus int DEFAULT NULL,
  volume_notes text,
  volume_total int DEFAULT NULL,
  volume_confidence enum('LOW','MEDIUM','HIGH','VERY_HIGH') DEFAULT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by int NOT NULL DEFAULT '0',
  updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_by int NOT NULL DEFAULT '0',
  deleted_date TIMESTAMP NULL DEFAULT NULL,
  deleted_by int DEFAULT NULL,
  version int NOT NULL DEFAULT '1',
  legacy_casing_id int DEFAULT '0',
  PRIMARY KEY (store_casing_id),
  KEY Index_3 (legacy_casing_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS store_model;
CREATE TABLE IF NOT EXISTS store_model (
  store_model_id int NOT NULL AUTO_INCREMENT,
  store_id int NOT NULL DEFAULT '0',
  project_id int NOT NULL DEFAULT '0',
  mapkey varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  curve float DEFAULT NULL,
  pwta float DEFAULT NULL,
  power float DEFAULT NULL,
  fit_adjusted_power float DEFAULT NULL,
  model_type enum('BALANCE','TACTICAL') NOT NULL,
  model_date date NOT NULL,
  created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  created_by int NOT NULL DEFAULT '0',
  updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_by int NOT NULL DEFAULT '0',
  deleted_date TIMESTAMP NULL DEFAULT NULL,
  deleted_by int DEFAULT NULL,
  version int NOT NULL DEFAULT '1',
  legacy_casing_id int DEFAULT NULL,
  PRIMARY KEY (store_model_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS store_survey;
CREATE TABLE IF NOT EXISTS store_survey (
  store_survey_id int NOT NULL AUTO_INCREMENT,
  store_id int DEFAULT '0',
  store_survey_date date DEFAULT NULL,
  store_survey_notes text,
  store_fit varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  store_format varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  store_is_open_24 tinyint DEFAULT NULL,
  area_sales int DEFAULT NULL,
  area_sales_percent_of_total float DEFAULT NULL,
  area_total int DEFAULT NULL,
  area_is_estimate tinyint DEFAULT NULL,
  natural_foods_are_integrated tinyint DEFAULT NULL,
  register_count_normal int DEFAULT NULL,
  register_count_express int DEFAULT NULL,
  register_count_self_checkout int DEFAULT NULL,
  fuel_dispenser_count int DEFAULT NULL,
  fuel_is_open_24 tinyint DEFAULT NULL,
  pharmacy_is_open_24 tinyint DEFAULT NULL,
  pharmacy_has_drive_through tinyint DEFAULT NULL,
  department_bakery tinyint DEFAULT NULL,
  department_bank tinyint DEFAULT NULL,
  department_beer tinyint DEFAULT NULL,
  department_bulk tinyint DEFAULT NULL,
  department_cheese tinyint DEFAULT NULL,
  department_coffee tinyint DEFAULT NULL,
  department_deli tinyint DEFAULT NULL,
  department_expanded_gm tinyint DEFAULT NULL,
  department_extensive_prepared_foods tinyint DEFAULT NULL,
  department_floral tinyint DEFAULT NULL,
  department_fuel tinyint DEFAULT NULL,
  department_hot_bar tinyint DEFAULT NULL,
  department_in_store_restaurant tinyint DEFAULT NULL,
  department_liquor tinyint DEFAULT NULL,
  department_meat tinyint DEFAULT NULL,
  department_natural tinyint DEFAULT NULL,
  department_olive_bar tinyint DEFAULT NULL,
  department_pharmacy tinyint DEFAULT NULL,
  department_pepared_foods tinyint DEFAULT NULL,
  department_salad_bar tinyint DEFAULT NULL,
  department_seafood tinyint DEFAULT NULL,
  department_seating tinyint DEFAULT NULL,
  department_sushi tinyint DEFAULT NULL,
  department_wine tinyint DEFAULT NULL,
  accessibility_farthest_from_entrance tinyint DEFAULT NULL,
  accessibility_main_intersection_has_traffic_light tinyint DEFAULT NULL,
  accessibility_main_intersection_needs_traffic_light tinyint DEFAULT NULL,
  accessibility_multiple_retailers_before_site tinyint DEFAULT NULL,
  accessibility_set_back_twice_parking_length tinyint DEFAULT NULL,
  accessibility_rating enum('POOR','FAIR','AVERAGE','GOOD','EXCELLENT') DEFAULT NULL,
  parking_outparcels_interfere_with_parking tinyint DEFAULT NULL,
  parking_direct_access_to_parking tinyint DEFAULT NULL,
  parking_small_parking_field tinyint DEFAULT NULL,
  parking_has_t_spaces tinyint DEFAULT NULL,
  parking_rating enum('POOR','FAIR','AVERAGE','GOOD','EXCELLENT') DEFAULT NULL,
  visibility_hill_depression_blocks_view tinyint DEFAULT NULL,
  visibility_outparcels_block_view tinyint DEFAULT NULL,
  visibility_sign_on_main tinyint DEFAULT NULL,
  visibility_store_faces_main_road tinyint DEFAULT NULL,
  visibility_trees_block_view tinyint DEFAULT NULL,
  visibility_rating enum('POOR','FAIR','AVERAGE','GOOD','EXCELLENT') DEFAULT NULL,
  seasonality_jan int DEFAULT NULL,
  seasonality_feb int DEFAULT NULL,
  seasonality_mar int DEFAULT NULL,
  seasonality_apr int DEFAULT NULL,
  seasonality_may int DEFAULT NULL,
  seasonality_jun int DEFAULT NULL,
  seasonality_jul int DEFAULT NULL,
  seasonality_aug int DEFAULT NULL,
  seasonality_sep int DEFAULT NULL,
  seasonality_oct int DEFAULT NULL,
  seasonality_nov int DEFAULT NULL,
  seasonality_dec int DEFAULT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by int NOT NULL DEFAULT '0',
  updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_by int NOT NULL DEFAULT '0',
  deleted_date TIMESTAMP NULL DEFAULT NULL,
  deleted_by int DEFAULT NULL,
  version int NOT NULL DEFAULT '1',
  legacy_casing_id int unsigned DEFAULT '0',
  department_online_pickup tinyint DEFAULT NULL,
  PRIMARY KEY (store_survey_id),
  KEY Index_3 (legacy_casing_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS store_volume;
CREATE TABLE IF NOT EXISTS store_volume (
  store_volume_id int NOT NULL AUTO_INCREMENT,
  store_id int NOT NULL DEFAULT '0',
  volume_total int NOT NULL,
  volume_date date NOT NULL,
  volume_type varchar(50) NOT NULL,
  source varchar(164) DEFAULT '',
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by int NOT NULL DEFAULT '0',
  updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_by int NOT NULL DEFAULT '0',
  deleted_date TIMESTAMP NULL DEFAULT NULL,
  deleted_by int DEFAULT NULL,
  version int NOT NULL DEFAULT '1',
  legacy_casing_id int DEFAULT '0',
  PRIMARY KEY (store_volume_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS user_profile;
CREATE TABLE IF NOT EXISTS user_profile (
  user_profile_id int NOT NULL AUTO_INCREMENT,
  email varchar(64) NOT NULL,
  first_name varchar(64) DEFAULT NULL,
  last_name varchar(64) DEFAULT NULL,
  auth_role_id int DEFAULT NULL,
  auth_group_id int DEFAULT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by int NOT NULL DEFAULT '0',
  updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_by int NOT NULL DEFAULT '0',
  deleted_date TIMESTAMP NULL DEFAULT NULL,
  deleted_by int DEFAULT NULL,
  version int NOT NULL DEFAULT '1',
  PRIMARY KEY (user_profile_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE auth_role_auth_permission ADD FOREIGN KEY (auth_permission_id) REFERENCES auth_permission(auth_permission_id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE auth_role_auth_permission ADD FOREIGN KEY (auth_role_id) REFERENCES auth_role (auth_role_id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE banner ADD FOREIGN KEY (company_id) REFERENCES company(company_id) ON DELETE SET NULL ON UPDATE CASCADE;
ALTER TABLE interaction ADD FOREIGN KEY (project_id) REFERENCES project(project_id) ON DELETE SET NULL ON UPDATE CASCADE;
ALTER TABLE interaction ADD FOREIGN KEY (shopping_center_id) REFERENCES shopping_center(shopping_center_id);
ALTER TABLE interaction ADD FOREIGN KEY (shopping_center_casing_id) REFERENCES shopping_center_casing(shopping_center_casing_id);
ALTER TABLE interaction ADD FOREIGN KEY (shopping_center_survey_id) REFERENCES shopping_center_survey(shopping_center_survey_id);
ALTER TABLE interaction ADD FOREIGN KEY (store_id) REFERENCES store(store_id);
ALTER TABLE interaction ADD FOREIGN KEY (store_casing_id) REFERENCES store_casing(store_casing_id);
ALTER TABLE interaction ADD FOREIGN KEY (store_survey_id) REFERENCES store_survey(store_survey_id);
ALTER TABLE shopping_center_access ADD FOREIGN KEY (shopping_center_survey_id) REFERENCES shopping_center_survey(shopping_center_survey_id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE shopping_center_casing ADD FOREIGN KEY (shopping_center_id) REFERENCES shopping_center(shopping_center_id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE shopping_center_survey ADD FOREIGN KEY (shopping_center_id) REFERENCES shopping_center(shopping_center_id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE shopping_center_tenant ADD FOREIGN KEY (shopping_center_survey_id) REFERENCES shopping_center_survey(shopping_center_survey_id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE site ADD FOREIGN KEY (shopping_center_id) REFERENCES shopping_center(shopping_center_id);
ALTER TABLE store ADD FOREIGN KEY (banner_id) REFERENCES banner(banner_id);
ALTER TABLE store ADD FOREIGN KEY (site_id) REFERENCES site(site_id);
ALTER TABLE store_casing ADD FOREIGN KEY (store_id) REFERENCES store(store_id);
ALTER TABLE store_model ADD FOREIGN KEY (project_id) REFERENCES project(project_id);
ALTER TABLE store_model ADD FOREIGN KEY (store_id) REFERENCES store(store_id);
ALTER TABLE store_survey ADD FOREIGN KEY (store_id) REFERENCES store(store_id);
ALTER TABLE store_volume ADD FOREIGN KEY (store_id) REFERENCES store(store_id);
