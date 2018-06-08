-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.21-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for mtn_dev
CREATE DATABASE IF NOT EXISTS `mtn_dev` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `mtn_dev`;

-- Dumping structure for view active_stores_with_strengths
DROP VIEW IF EXISTS `active_stores_with_strengths`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `active_stores_with_strengths` (
	`store_id` INT(11) NOT NULL,
	`latitude` DOUBLE NOT NULL,
	`longitude` DOUBLE NOT NULL,
	`address_1` VARCHAR(255) NULL COLLATE 'utf8_unicode_ci',
	`address_2` VARCHAR(255) NULL COLLATE 'utf8_general_ci',
	`city` VARCHAR(255) NULL COLLATE 'utf8_unicode_ci',
	`county` VARCHAR(255) NULL COLLATE 'utf8_unicode_ci',
	`state` VARCHAR(255) NULL COLLATE 'utf8_unicode_ci',
	`postal_code` VARCHAR(255) NULL COLLATE 'utf8_unicode_ci',
	`quad` VARCHAR(255) NULL COLLATE 'utf8_unicode_ci',
	`intersection_street_primary` VARCHAR(255) NULL COLLATE 'utf8_unicode_ci',
	`intersection_street_secondary` VARCHAR(255) NULL COLLATE 'utf8_unicode_ci',
	`store_type` ENUM('HISTORICAL','ACTIVE','FUTURE','HYPOTHETICAL') NULL COLLATE 'utf8_general_ci',
	`store_name` VARCHAR(256) NULL COLLATE 'utf8_unicode_ci',
	`store_number` VARCHAR(48) NULL COLLATE 'utf8_general_ci',
	`store_status` VARCHAR(50) NULL COLLATE 'utf8_unicode_ci',
	`store_strength` DECIMAL(26,3) NULL,
	`store_fit` VARCHAR(255) NULL COLLATE 'utf8_bin',
	`using_default_fit` VARCHAR(4) NULL COLLATE 'utf8mb4_general_ci',
	`banner_id` INT(11) NULL,
	`banner_name` VARCHAR(128) NULL COLLATE 'utf8_unicode_ci',
	`company_id` INT(11) NULL,
	`company_name` VARCHAR(128) NULL COLLATE 'utf8_unicode_ci',
	`parent_company_id` INT(11) NULL,
	`parent_company_name` VARCHAR(128) NULL COLLATE 'utf8_unicode_ci',
	`area_sales` BIGINT(11) NULL,
	`area_total` INT(11) NULL,
	`volume_choice` VARCHAR(11) NULL COLLATE 'utf8mb4_general_ci',
	`volume_total` BIGINT(11) NULL,
	`volume_date` DATE NULL,
	`shopping_center_feature_score` DECIMAL(37,1) NULL,
	`rating_synergy` ENUM('POOR','FAIR','AVERAGE','GOOD','EXCELLENT') NULL COLLATE 'utf8_general_ci',
	`tenant_vacant_count` INT(11) NULL,
	`tenant_occupied_count` INT(11) NULL,
	`parking_space_count` INT(11) NULL,
	`legacy_location_id` INT(11) NULL
) ENGINE=MyISAM;

-- Dumping structure for view average_sc_score
DROP VIEW IF EXISTS `average_sc_score`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `average_sc_score` (
	`shopping_center_id` INT(11) NOT NULL,
	`score` DECIMAL(21,8) NULL
) ENGINE=MyISAM;

-- Dumping structure for view avg_by_banner
DROP VIEW IF EXISTS `avg_by_banner`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `avg_by_banner` (
	`banner_id` INT(11) NULL,
	`volume` DECIMAL(14,4) NULL,
	`area_sales` DECIMAL(14,4) NULL,
	`area_total` DECIMAL(14,4) NULL,
	`dpsf` DECIMAL(22,8) NULL
) ENGINE=MyISAM;

-- Dumping structure for view avg_by_banner_in_county
DROP VIEW IF EXISTS `avg_by_banner_in_county`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `avg_by_banner_in_county` (
	`banner_id` INT(11) NULL,
	`state` VARCHAR(255) NULL COLLATE 'utf8_unicode_ci',
	`county` VARCHAR(255) NULL COLLATE 'utf8_unicode_ci',
	`volume` DECIMAL(14,4) NULL,
	`area_sales` DECIMAL(14,4) NULL,
	`area_total` DECIMAL(14,4) NULL,
	`dpsf` DECIMAL(22,8) NULL
) ENGINE=MyISAM;

-- Dumping structure for view avg_by_county
DROP VIEW IF EXISTS `avg_by_county`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `avg_by_county` (
	`state` VARCHAR(255) NULL COLLATE 'utf8_unicode_ci',
	`county` VARCHAR(255) NULL COLLATE 'utf8_unicode_ci',
	`volume` DECIMAL(14,4) NULL,
	`area_sales` DECIMAL(14,4) NULL,
	`area_total` DECIMAL(14,4) NULL,
	`dpsf` DECIMAL(22,8) NULL
) ENGINE=MyISAM;

-- Dumping structure for view avg_by_fit
DROP VIEW IF EXISTS `avg_by_fit`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `avg_by_fit` (
	`store_fit` VARCHAR(255) NULL COLLATE 'utf8_unicode_ci',
	`volume` DECIMAL(14,4) NULL,
	`area_sales` DECIMAL(14,4) NULL,
	`area_total` DECIMAL(14,4) NULL,
	`dpsf` DECIMAL(22,8) NULL
) ENGINE=MyISAM;

-- Dumping structure for view avg_by_fit_in_county
DROP VIEW IF EXISTS `avg_by_fit_in_county`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `avg_by_fit_in_county` (
	`store_fit` VARCHAR(255) NULL COLLATE 'utf8_unicode_ci',
	`state` VARCHAR(255) NULL COLLATE 'utf8_unicode_ci',
	`county` VARCHAR(255) NULL COLLATE 'utf8_unicode_ci',
	`volume` DECIMAL(14,4) NULL,
	`area_sales` DECIMAL(14,4) NULL,
	`area_total` DECIMAL(14,4) NULL,
	`dpsf` DECIMAL(22,8) NULL
) ENGINE=MyISAM;

-- Dumping structure for view most_recent_shopping_center_casing
DROP VIEW IF EXISTS `most_recent_shopping_center_casing`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `most_recent_shopping_center_casing` (
	`shopping_center_casing_id` INT(11) NOT NULL,
	`shopping_center_id` INT(11) NOT NULL,
	`shopping_center_casing_date` DATE NULL,
	`shopping_center_casing_note` TEXT NULL COLLATE 'utf8_general_ci',
	`rating_parking_lot` ENUM('POOR','FAIR','AVERAGE','GOOD','EXCELLENT') NULL COLLATE 'utf8_general_ci',
	`rating_buildings` ENUM('POOR','FAIR','AVERAGE','GOOD','EXCELLENT') NULL COLLATE 'utf8_general_ci',
	`rating_lighting` ENUM('POOR','FAIR','AVERAGE','GOOD','EXCELLENT') NULL COLLATE 'utf8_general_ci',
	`rating_synergy` ENUM('POOR','FAIR','AVERAGE','GOOD','EXCELLENT') NULL COLLATE 'utf8_general_ci',
	`created_date` TIMESTAMP NOT NULL,
	`created_by` INT(11) NOT NULL,
	`updated_date` TIMESTAMP NOT NULL,
	`updated_by` INT(11) NOT NULL,
	`deleted_date` TIMESTAMP NULL,
	`deleted_by` INT(11) NULL,
	`version` INT(11) NOT NULL,
	`legacy_casing_id` INT(11) NULL,
	`shopping_center_survey_id` INT(11) NOT NULL
) ENGINE=MyISAM;

-- Dumping structure for view most_recent_shopping_center_survey
DROP VIEW IF EXISTS `most_recent_shopping_center_survey`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `most_recent_shopping_center_survey` (
	`shopping_center_survey_id` INT(11) NOT NULL,
	`shopping_center_id` INT(11) NOT NULL,
	`shopping_center_survey_date` DATE NULL,
	`center_type` VARCHAR(255) NULL COLLATE 'utf8_unicode_ci',
	`shopping_center_survey_note` TEXT NULL COLLATE 'utf8_general_ci',
	`flow_has_landscaping` TINYINT(4) NULL,
	`flow_has_speed_bumps` TINYINT(4) NULL,
	`flow_has_stop_signs` TINYINT(4) NULL,
	`flow_has_one_way_aisles` TINYINT(4) NULL,
	`parking_has_angled_spaces` TINYINT(4) NULL,
	`parking_has_parking_hog` TINYINT(4) NULL,
	`parking_is_poorly_lit` TINYINT(4) NULL,
	`parking_space_count` INT(11) NULL,
	`tenant_occupied_count` INT(11) NULL,
	`tenant_vacant_count` INT(11) NULL,
	`sq_ft_percent_occupied` FLOAT NULL,
	`created_date` TIMESTAMP NOT NULL,
	`created_by` INT(11) NOT NULL,
	`updated_date` TIMESTAMP NOT NULL,
	`updated_by` INT(11) NOT NULL,
	`deleted_date` TIMESTAMP NULL,
	`deleted_by` INT(11) NULL,
	`version` INT(11) NOT NULL,
	`legacy_casing_id` INT(11) NULL
) ENGINE=MyISAM;

-- Dumping structure for view most_recent_store_casing
DROP VIEW IF EXISTS `most_recent_store_casing`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `most_recent_store_casing` (
	`store_casing_id` INT(11) NOT NULL,
	`store_id` INT(11) NOT NULL,
	`store_casing_date` DATE NULL,
	`store_casing_note` TEXT NULL COLLATE 'utf8_general_ci',
	`store_status_id` INT(11) NULL,
	`condition_ceiling` ENUM('POOR','FAIR','AVERAGE','GOOD','EXCELLENT') NULL COLLATE 'utf8_general_ci',
	`condition_checkstands` ENUM('POOR','FAIR','AVERAGE','GOOD','EXCELLENT') NULL COLLATE 'utf8_general_ci',
	`condition_floors` ENUM('POOR','FAIR','AVERAGE','GOOD','EXCELLENT') NULL COLLATE 'utf8_general_ci',
	`condition_frozen_refrigerated` ENUM('POOR','FAIR','AVERAGE','GOOD','EXCELLENT') NULL COLLATE 'utf8_general_ci',
	`condition_shelving_gondolas` ENUM('POOR','FAIR','AVERAGE','GOOD','EXCELLENT') NULL COLLATE 'utf8_general_ci',
	`condition_walls` ENUM('POOR','FAIR','AVERAGE','GOOD','EXCELLENT') NULL COLLATE 'utf8_general_ci',
	`fuel_gallons_weekly` INT(11) NULL,
	`pharmacy_scripts_weekly` INT(11) NULL,
	`pharmacy_avg_dollars_per_script` FLOAT NULL,
	`pharmacy_pharmacist_count` INT(11) NULL,
	`pharmacy_technician_count` INT(11) NULL,
	`store_volume_id` INT(11) NULL,
	`created_date` TIMESTAMP NOT NULL,
	`created_by` INT(11) NOT NULL,
	`updated_date` TIMESTAMP NOT NULL,
	`updated_by` INT(11) NOT NULL,
	`deleted_date` TIMESTAMP NULL,
	`deleted_by` INT(11) NULL,
	`version` INT(11) NOT NULL,
	`legacy_casing_id` INT(11) NULL,
	`store_survey_id` INT(11) NOT NULL
) ENGINE=MyISAM;

-- Dumping structure for view most_recent_store_model
DROP VIEW IF EXISTS `most_recent_store_model`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `most_recent_store_model` (
	`store_model_id` INT(11) NOT NULL,
	`store_id` INT(11) NOT NULL,
	`project_id` INT(11) NOT NULL,
	`mapkey` VARCHAR(50) NULL COLLATE 'latin1_swedish_ci',
	`curve` FLOAT NULL,
	`pwta` FLOAT NULL,
	`power` FLOAT NULL,
	`fit_adjusted_power` FLOAT NULL,
	`model_type` ENUM('BALANCE','TACTICAL') NOT NULL COLLATE 'utf8_general_ci',
	`model_date` DATE NOT NULL,
	`created_date` TIMESTAMP NOT NULL,
	`created_by` INT(11) NOT NULL,
	`updated_date` TIMESTAMP NOT NULL,
	`updated_by` INT(11) NOT NULL,
	`deleted_date` TIMESTAMP NULL,
	`deleted_by` INT(11) NULL,
	`version` INT(11) NOT NULL,
	`legacy_casing_id` INT(11) NULL
) ENGINE=MyISAM;

-- Dumping structure for view most_recent_store_status
DROP VIEW IF EXISTS `most_recent_store_status`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `most_recent_store_status` (
	`store_status_id` INT(11) NOT NULL,
	`store_id` INT(11) NOT NULL,
	`status` VARCHAR(50) NOT NULL COLLATE 'utf8_unicode_ci',
	`status_start_date` TIMESTAMP NULL,
	`created_date` TIMESTAMP NOT NULL,
	`created_by` INT(11) NOT NULL,
	`updated_date` TIMESTAMP NOT NULL,
	`updated_by` INT(11) NOT NULL,
	`deleted_date` TIMESTAMP NULL,
	`deleted_by` INT(11) NULL,
	`version` INT(11) NOT NULL,
	`legacy_location_id` INT(11) NULL,
	`legacy_casing_id` INT(11) NULL
) ENGINE=MyISAM;

-- Dumping structure for view most_recent_store_survey
DROP VIEW IF EXISTS `most_recent_store_survey`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `most_recent_store_survey` (
	`store_survey_id` INT(11) NOT NULL,
	`store_id` INT(11) NULL,
	`store_survey_date` TIMESTAMP NULL,
	`store_survey_note` TEXT NULL COLLATE 'utf8_general_ci',
	`store_fit` VARCHAR(255) NULL COLLATE 'utf8_unicode_ci',
	`store_format` VARCHAR(255) NULL COLLATE 'utf8_unicode_ci',
	`store_is_open_24` TINYINT(4) NULL,
	`area_sales` INT(11) NULL,
	`area_sales_percent_of_total` FLOAT NULL,
	`area_total` INT(11) NULL,
	`area_is_estimate` TINYINT(4) NULL,
	`natural_foods_are_integrated` TINYINT(4) NULL,
	`register_count_normal` INT(11) NULL,
	`register_count_express` INT(11) NULL,
	`register_count_self_checkout` INT(11) NULL,
	`fuel_dispenser_count` INT(11) NULL,
	`fuel_is_open_24` TINYINT(4) NULL,
	`pharmacy_is_open_24` TINYINT(4) NULL,
	`pharmacy_has_drive_through` TINYINT(4) NULL,
	`department_bakery` TINYINT(4) NULL,
	`department_bank` TINYINT(4) NULL,
	`department_beer` TINYINT(4) NULL,
	`department_bulk` TINYINT(4) NULL,
	`department_cheese` TINYINT(4) NULL,
	`department_coffee` TINYINT(4) NULL,
	`department_deli` TINYINT(4) NULL,
	`department_expanded_gm` TINYINT(4) NULL,
	`department_extensive_prepared_foods` TINYINT(4) NULL,
	`department_floral` TINYINT(4) NULL,
	`department_fuel` TINYINT(4) NULL,
	`department_hot_bar` TINYINT(4) NULL,
	`department_in_store_restaurant` TINYINT(4) NULL,
	`department_liquor` TINYINT(4) NULL,
	`department_meat` TINYINT(4) NULL,
	`department_natural` TINYINT(4) NULL,
	`department_olive_bar` TINYINT(4) NULL,
	`department_online_pickup` TINYINT(4) NULL,
	`department_pharmacy` TINYINT(4) NULL,
	`department_prepared_foods` TINYINT(4) NULL,
	`department_salad_bar` TINYINT(4) NULL,
	`department_seafood` TINYINT(4) NULL,
	`department_seating` TINYINT(4) NULL,
	`department_sushi` TINYINT(4) NULL,
	`department_wine` TINYINT(4) NULL,
	`accessibility_farthest_from_entrance` TINYINT(4) NULL,
	`accessibility_main_intersection_has_traffic_light` TINYINT(4) NULL,
	`accessibility_main_intersection_needs_traffic_light` TINYINT(4) NULL,
	`accessibility_multiple_retailers_before_site` TINYINT(4) NULL,
	`accessibility_set_back_twice_parking_length` TINYINT(4) NULL,
	`accessibility_rating` ENUM('POOR','FAIR','AVERAGE','GOOD','EXCELLENT') NULL COLLATE 'utf8_general_ci',
	`parking_outparcels_interfere_with_parking` TINYINT(4) NULL,
	`parking_direct_access_to_parking` TINYINT(4) NULL,
	`parking_small_parking_field` TINYINT(4) NULL,
	`parking_has_t_spaces` TINYINT(4) NULL,
	`parking_rating` ENUM('POOR','FAIR','AVERAGE','GOOD','EXCELLENT') NULL COLLATE 'utf8_general_ci',
	`visibility_hill_depression_blocks_view` TINYINT(4) NULL,
	`visibility_outparcels_block_view` TINYINT(4) NULL,
	`visibility_sign_on_main` TINYINT(4) NULL,
	`visibility_store_faces_main_road` TINYINT(4) NULL,
	`visibility_trees_block_view` TINYINT(4) NULL,
	`visibility_rating` ENUM('POOR','FAIR','AVERAGE','GOOD','EXCELLENT') NULL COLLATE 'utf8_general_ci',
	`seasonality_jan` INT(11) NULL,
	`seasonality_feb` INT(11) NULL,
	`seasonality_mar` INT(11) NULL,
	`seasonality_apr` INT(11) NULL,
	`seasonality_may` INT(11) NULL,
	`seasonality_jun` INT(11) NULL,
	`seasonality_jul` INT(11) NULL,
	`seasonality_aug` INT(11) NULL,
	`seasonality_sep` INT(11) NULL,
	`seasonality_oct` INT(11) NULL,
	`seasonality_nov` INT(11) NULL,
	`seasonality_dec` INT(11) NULL,
	`created_date` TIMESTAMP NOT NULL,
	`created_by` INT(11) NOT NULL,
	`updated_date` TIMESTAMP NOT NULL,
	`updated_by` INT(11) NOT NULL,
	`deleted_date` TIMESTAMP NULL,
	`deleted_by` INT(11) NULL,
	`version` INT(11) NOT NULL,
	`legacy_casing_id` INT(10) UNSIGNED NULL
) ENGINE=MyISAM;

-- Dumping structure for view most_recent_store_volume
DROP VIEW IF EXISTS `most_recent_store_volume`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `most_recent_store_volume` (
	`store_volume_id` INT(11) NOT NULL,
	`store_id` INT(11) NOT NULL,
	`volume_total` INT(11) NOT NULL,
	`volume_date` DATE NOT NULL,
	`volume_type` ENUM('ACTUAL','ESTIMATE','PROJECTION','PLACEHOLDER','THIRD_PARTY') NOT NULL COLLATE 'utf8_general_ci',
	`source` VARCHAR(164) NULL COLLATE 'utf8_general_ci',
	`volume_grocery` INT(11) NULL,
	`volume_percent_grocery` FLOAT NULL,
	`volume_meat` INT(11) NULL,
	`volume_percent_meat` FLOAT NULL,
	`volume_non_food` INT(11) NULL,
	`volume_percent_non_food` FLOAT NULL,
	`volume_other` INT(11) NULL,
	`volume_percent_other` FLOAT NULL,
	`volume_produce` INT(11) NULL,
	`volume_percent_produce` FLOAT NULL,
	`volume_plus_minus` INT(11) NULL,
	`volume_note` TEXT NULL COLLATE 'utf8_general_ci',
	`volume_confidence` ENUM('LOW','MEDIUM','HIGH','VERY_HIGH') NULL COLLATE 'utf8_general_ci',
	`created_date` TIMESTAMP NOT NULL,
	`created_by` INT(11) NOT NULL,
	`updated_date` TIMESTAMP NOT NULL,
	`updated_by` INT(11) NOT NULL,
	`deleted_date` TIMESTAMP NULL,
	`deleted_by` INT(11) NULL,
	`version` INT(11) NOT NULL,
	`legacy_casing_id` INT(11) NULL
) ENGINE=MyISAM;

-- Dumping structure for view most_recent_store_volume_actual
DROP VIEW IF EXISTS `most_recent_store_volume_actual`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `most_recent_store_volume_actual` (
	`store_volume_id` INT(11) NOT NULL,
	`store_id` INT(11) NOT NULL,
	`volume_total` INT(11) NOT NULL,
	`volume_date` DATE NOT NULL,
	`volume_type` ENUM('ACTUAL','ESTIMATE','PROJECTION','PLACEHOLDER','THIRD_PARTY') NOT NULL COLLATE 'utf8_general_ci',
	`source` VARCHAR(164) NULL COLLATE 'utf8_general_ci',
	`created_date` TIMESTAMP NOT NULL,
	`created_by` INT(11) NOT NULL,
	`updated_date` TIMESTAMP NOT NULL,
	`updated_by` INT(11) NOT NULL,
	`deleted_date` TIMESTAMP NULL,
	`deleted_by` INT(11) NULL,
	`version` INT(11) NOT NULL,
	`legacy_casing_id` INT(11) NULL
) ENGINE=MyISAM;

-- Dumping structure for view most_recent_store_volume_estimate
DROP VIEW IF EXISTS `most_recent_store_volume_estimate`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `most_recent_store_volume_estimate` (
	`store_volume_id` INT(11) NOT NULL,
	`store_id` INT(11) NOT NULL,
	`volume_total` INT(11) NOT NULL,
	`volume_date` DATE NOT NULL,
	`volume_type` ENUM('ACTUAL','ESTIMATE','PROJECTION','PLACEHOLDER','THIRD_PARTY') NOT NULL COLLATE 'utf8_general_ci',
	`source` VARCHAR(164) NULL COLLATE 'utf8_general_ci',
	`created_date` TIMESTAMP NOT NULL,
	`created_by` INT(11) NOT NULL,
	`updated_date` TIMESTAMP NOT NULL,
	`updated_by` INT(11) NOT NULL,
	`deleted_date` TIMESTAMP NULL,
	`deleted_by` INT(11) NULL,
	`version` INT(11) NOT NULL,
	`legacy_casing_id` INT(11) NULL
) ENGINE=MyISAM;

-- Dumping structure for view most_recent_store_volume_projection
DROP VIEW IF EXISTS `most_recent_store_volume_projection`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `most_recent_store_volume_projection` (
	`store_volume_id` INT(11) NOT NULL,
	`store_id` INT(11) NOT NULL,
	`volume_total` INT(11) NOT NULL,
	`volume_date` DATE NOT NULL,
	`volume_type` ENUM('ACTUAL','ESTIMATE','PROJECTION','PLACEHOLDER','THIRD_PARTY') NOT NULL COLLATE 'utf8_general_ci',
	`source` VARCHAR(164) NULL COLLATE 'utf8_general_ci',
	`created_date` TIMESTAMP NOT NULL,
	`created_by` INT(11) NOT NULL,
	`updated_date` TIMESTAMP NOT NULL,
	`updated_by` INT(11) NOT NULL,
	`deleted_date` TIMESTAMP NULL,
	`deleted_by` INT(11) NULL,
	`version` INT(11) NOT NULL,
	`legacy_casing_id` INT(11) NULL
) ENGINE=MyISAM;

-- Dumping structure for view most_recent_store_volume_reported
DROP VIEW IF EXISTS `most_recent_store_volume_reported`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `most_recent_store_volume_reported` (
	`store_volume_id` INT(11) NOT NULL,
	`store_id` INT(11) NOT NULL,
	`volume_total` INT(11) NOT NULL,
	`volume_date` DATE NOT NULL,
	`volume_type` ENUM('ACTUAL','ESTIMATE','PROJECTION','PLACEHOLDER','THIRD_PARTY') NOT NULL COLLATE 'utf8_general_ci',
	`source` VARCHAR(164) NULL COLLATE 'utf8_general_ci',
	`created_date` TIMESTAMP NOT NULL,
	`created_by` INT(11) NOT NULL,
	`updated_date` TIMESTAMP NOT NULL,
	`updated_by` INT(11) NOT NULL,
	`deleted_date` TIMESTAMP NULL,
	`deleted_by` INT(11) NULL,
	`version` INT(11) NOT NULL,
	`legacy_casing_id` INT(11) NULL
) ENGINE=MyISAM;

-- Dumping structure for view most_recent_store_volume_third_party
DROP VIEW IF EXISTS `most_recent_store_volume_third_party`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `most_recent_store_volume_third_party` (
	`store_volume_id` INT(11) NOT NULL,
	`store_id` INT(11) NOT NULL,
	`volume_total` INT(11) NOT NULL,
	`volume_date` DATE NOT NULL,
	`volume_type` ENUM('ACTUAL','ESTIMATE','PROJECTION','PLACEHOLDER','THIRD_PARTY') NOT NULL COLLATE 'utf8_general_ci',
	`source` VARCHAR(164) NULL COLLATE 'utf8_general_ci',
	`created_date` TIMESTAMP NOT NULL,
	`created_by` INT(11) NOT NULL,
	`updated_date` TIMESTAMP NOT NULL,
	`updated_by` INT(11) NOT NULL,
	`deleted_date` TIMESTAMP NULL,
	`deleted_by` INT(11) NULL,
	`version` INT(11) NOT NULL,
	`legacy_casing_id` INT(11) NULL
) ENGINE=MyISAM;

-- Dumping structure for view sc_condition_global_avg_score
DROP VIEW IF EXISTS `sc_condition_global_avg_score`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `sc_condition_global_avg_score` (
	`average` DECIMAL(17,4) NULL
) ENGINE=MyISAM;

-- Dumping structure for view sc_condition_index
DROP VIEW IF EXISTS `sc_condition_index`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `sc_condition_index` (
	`shopping_center_casing_id` INT(11) NOT NULL,
	`score_total` BIGINT(14) NOT NULL,
	`indexed` DECIMAL(21,0) NULL
) ENGINE=MyISAM;

-- Dumping structure for view sc_condition_score
DROP VIEW IF EXISTS `sc_condition_score`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `sc_condition_score` (
	`shopping_center_casing_id` INT(11) NOT NULL,
	`score` DECIMAL(17,4) NULL
) ENGINE=MyISAM;

-- Dumping structure for view shopping_center_access_front_main
DROP VIEW IF EXISTS `shopping_center_access_front_main`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `shopping_center_access_front_main` (
	`shopping_center_survey_id` INT(11) NOT NULL,
	`access_type` ENUM('FRONT_MAIN','SIDE_MAIN','NON_MAIN') NOT NULL COLLATE 'utf8_unicode_ci',
	`has_left_in` DECIMAL(25,0) NULL,
	`has_left_out` DECIMAL(25,0) NULL,
	`has_right_in` DECIMAL(25,0) NULL,
	`has_right_out` DECIMAL(25,0) NULL,
	`has_traffic_light` DECIMAL(25,0) NULL,
	`one_way_road` DECIMAL(25,0) NULL,
	`count_front_mains` BIGINT(21) NOT NULL
) ENGINE=MyISAM;

-- Dumping structure for view shopping_center_access_non_main
DROP VIEW IF EXISTS `shopping_center_access_non_main`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `shopping_center_access_non_main` (
	`shopping_center_survey_id` INT(11) NOT NULL,
	`access_type` ENUM('FRONT_MAIN','SIDE_MAIN','NON_MAIN') NOT NULL COLLATE 'utf8_unicode_ci',
	`has_left_in` DECIMAL(25,0) NULL,
	`has_left_out` DECIMAL(25,0) NULL,
	`has_right_in` DECIMAL(25,0) NULL,
	`has_right_out` DECIMAL(25,0) NULL,
	`has_traffic_light` DECIMAL(25,0) NULL,
	`one_way_road` DECIMAL(25,0) NULL,
	`count_side_mains` BIGINT(21) NOT NULL
) ENGINE=MyISAM;

-- Dumping structure for view shopping_center_access_side_main
DROP VIEW IF EXISTS `shopping_center_access_side_main`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `shopping_center_access_side_main` (
	`shopping_center_survey_id` INT(11) NOT NULL,
	`access_type` ENUM('FRONT_MAIN','SIDE_MAIN','NON_MAIN') NOT NULL COLLATE 'utf8_unicode_ci',
	`has_left_in` DECIMAL(25,0) NULL,
	`has_left_out` DECIMAL(25,0) NULL,
	`has_right_in` DECIMAL(25,0) NULL,
	`has_right_out` DECIMAL(25,0) NULL,
	`has_traffic_light` DECIMAL(25,0) NULL,
	`one_way_road` DECIMAL(25,0) NULL,
	`count_non_mains` BIGINT(21) NOT NULL
) ENGINE=MyISAM;

-- Dumping structure for view shopping_center_condition_scores
DROP VIEW IF EXISTS `shopping_center_condition_scores`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `shopping_center_condition_scores` (
	`shopping_center_casing_id` INT(11) NOT NULL,
	`rating_parking_lot` ENUM('POOR','FAIR','AVERAGE','GOOD','EXCELLENT') NULL COLLATE 'utf8_general_ci',
	`rating_buildings` ENUM('POOR','FAIR','AVERAGE','GOOD','EXCELLENT') NULL COLLATE 'utf8_general_ci',
	`rating_lighting` ENUM('POOR','FAIR','AVERAGE','GOOD','EXCELLENT') NULL COLLATE 'utf8_general_ci',
	`rating_synergy` ENUM('POOR','FAIR','AVERAGE','GOOD','EXCELLENT') NULL COLLATE 'utf8_general_ci',
	`score_parking_lot` INT(1) NULL,
	`score_buildings` INT(1) NULL,
	`score_lighting` INT(1) NULL,
	`score_synergy` INT(1) NULL
) ENGINE=MyISAM;

-- Dumping structure for view shopping_center_score
DROP VIEW IF EXISTS `shopping_center_score`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `shopping_center_score` (
	`shopping_center_id` INT(11) NOT NULL,
	`shopping_center_casing_id` INT(11) NULL,
	`shopping_center_survey_id` INT(11) NULL,
	`score` DECIMAL(37,1) NOT NULL
) ENGINE=MyISAM;

-- Dumping structure for view shopping_center_score_card
DROP VIEW IF EXISTS `shopping_center_score_card`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `shopping_center_score_card` (
	`shopping_center_id` INT(11) NOT NULL,
	`shopping_center_casing_id` INT(11) NULL,
	`shopping_center_survey_id` INT(11) NULL,
	`position_in_center` VARCHAR(255) NULL COLLATE 'utf8_unicode_ci',
	`inside_corner_or_mid_center` INT(2) NOT NULL,
	`is_mall_or_free_standing` INT(2) NOT NULL,
	`fm_no_left_in` INT(2) NOT NULL,
	`fm_no_left_out` INT(2) NOT NULL,
	`main_int_needs_light` INT(2) NOT NULL,
	`sm_no_left_in` DECIMAL(2,1) NOT NULL,
	`sm_no_left_out` DECIMAL(2,1) NOT NULL,
	`nm_no_left_in` DECIMAL(2,1) NOT NULL,
	`nm_no_left_out` DECIMAL(2,1) NOT NULL,
	`trees_block_view` INT(2) NOT NULL,
	`outparcels_block_view` INT(2) NOT NULL,
	`hill_dep_blocks_view` INT(2) NOT NULL,
	`store_not_facing_main` INT(2) NOT NULL,
	`no_sign_on_main` INT(2) NOT NULL,
	`excessive_landscaping_islands` DECIMAL(2,1) NOT NULL,
	`excessive_stop_signs` DECIMAL(2,1) NOT NULL,
	`center_has_parking_hog` DECIMAL(2,1) NOT NULL,
	`outparcels_interfere` DECIMAL(2,1) NOT NULL,
	`no_direct_access_to_parking` DECIMAL(2,1) NOT NULL,
	`angled_spaces` DECIMAL(2,1) NOT NULL,
	`t_spaces` DECIMAL(2,1) NOT NULL,
	`one_way` DECIMAL(2,1) NOT NULL,
	`poorly_lit` INT(2) NOT NULL,
	`set_back` INT(2) NOT NULL,
	`multiple_retailers_before` INT(2) NOT NULL,
	`farthest_from_entrance` INT(2) NOT NULL
) ENGINE=MyISAM;

-- Dumping structure for view store_best_volume
DROP VIEW IF EXISTS `store_best_volume`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `store_best_volume` (
	`store_id` INT(11) NULL,
	`decision` VARCHAR(11) NOT NULL COLLATE 'utf8mb4_general_ci',
	`volume_total` BIGINT(11) NULL,
	`volume_date` DATE NULL
) ENGINE=MyISAM;

-- Dumping structure for view store_strength
DROP VIEW IF EXISTS `store_strength`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `store_strength` (
	`store_id` INT(11) NULL,
	`strength` DECIMAL(26,3) NULL
) ENGINE=MyISAM;

-- Dumping structure for view store_volume_actual
DROP VIEW IF EXISTS `store_volume_actual`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `store_volume_actual` (
	`store_volume_id` INT(11) NOT NULL,
	`store_id` INT(11) NOT NULL,
	`volume_total` INT(11) NOT NULL,
	`volume_date` DATE NOT NULL,
	`volume_type` ENUM('ACTUAL','ESTIMATE','PROJECTION','PLACEHOLDER','THIRD_PARTY') NOT NULL COLLATE 'utf8_general_ci',
	`source` VARCHAR(164) NULL COLLATE 'utf8_general_ci',
	`created_date` TIMESTAMP NOT NULL,
	`created_by` INT(11) NOT NULL,
	`updated_date` TIMESTAMP NOT NULL,
	`updated_by` INT(11) NOT NULL,
	`deleted_date` TIMESTAMP NULL,
	`deleted_by` INT(11) NULL,
	`version` INT(11) NOT NULL,
	`legacy_casing_id` INT(11) NULL
) ENGINE=MyISAM;

-- Dumping structure for view store_volume_estimate
DROP VIEW IF EXISTS `store_volume_estimate`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `store_volume_estimate` (
	`store_volume_id` INT(11) NOT NULL,
	`store_id` INT(11) NOT NULL,
	`volume_total` INT(11) NOT NULL,
	`volume_date` DATE NOT NULL,
	`volume_type` ENUM('ACTUAL','ESTIMATE','PROJECTION','PLACEHOLDER','THIRD_PARTY') NOT NULL COLLATE 'utf8_general_ci',
	`source` VARCHAR(164) NULL COLLATE 'utf8_general_ci',
	`created_date` TIMESTAMP NOT NULL,
	`created_by` INT(11) NOT NULL,
	`updated_date` TIMESTAMP NOT NULL,
	`updated_by` INT(11) NOT NULL,
	`deleted_date` TIMESTAMP NULL,
	`deleted_by` INT(11) NULL,
	`version` INT(11) NOT NULL,
	`legacy_casing_id` INT(11) NULL
) ENGINE=MyISAM;

-- Dumping structure for view store_volume_projection
DROP VIEW IF EXISTS `store_volume_projection`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `store_volume_projection` (
	`store_volume_id` INT(11) NOT NULL,
	`store_id` INT(11) NOT NULL,
	`volume_total` INT(11) NOT NULL,
	`volume_date` DATE NOT NULL,
	`volume_type` ENUM('ACTUAL','ESTIMATE','PROJECTION','PLACEHOLDER','THIRD_PARTY') NOT NULL COLLATE 'utf8_general_ci',
	`source` VARCHAR(164) NULL COLLATE 'utf8_general_ci',
	`created_date` TIMESTAMP NOT NULL,
	`created_by` INT(11) NOT NULL,
	`updated_date` TIMESTAMP NOT NULL,
	`updated_by` INT(11) NOT NULL,
	`deleted_date` TIMESTAMP NULL,
	`deleted_by` INT(11) NULL,
	`version` INT(11) NOT NULL,
	`legacy_casing_id` INT(11) NULL
) ENGINE=MyISAM;

-- Dumping structure for view store_volume_reported
DROP VIEW IF EXISTS `store_volume_reported`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `store_volume_reported` (
	`store_volume_id` INT(11) NOT NULL,
	`store_id` INT(11) NOT NULL,
	`volume_total` INT(11) NOT NULL,
	`volume_date` DATE NOT NULL,
	`volume_type` ENUM('ACTUAL','ESTIMATE','PROJECTION','PLACEHOLDER','THIRD_PARTY') NOT NULL COLLATE 'utf8_general_ci',
	`source` VARCHAR(164) NULL COLLATE 'utf8_general_ci',
	`created_date` TIMESTAMP NOT NULL,
	`created_by` INT(11) NOT NULL,
	`updated_date` TIMESTAMP NOT NULL,
	`updated_by` INT(11) NOT NULL,
	`deleted_date` TIMESTAMP NULL,
	`deleted_by` INT(11) NULL,
	`version` INT(11) NOT NULL,
	`legacy_casing_id` INT(11) NULL
) ENGINE=MyISAM;

-- Dumping structure for view store_volume_third_party
DROP VIEW IF EXISTS `store_volume_third_party`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `store_volume_third_party` (
	`store_volume_id` INT(11) NOT NULL,
	`store_id` INT(11) NOT NULL,
	`volume_total` INT(11) NOT NULL,
	`volume_date` DATE NOT NULL,
	`volume_type` ENUM('ACTUAL','ESTIMATE','PROJECTION','PLACEHOLDER','THIRD_PARTY') NOT NULL COLLATE 'utf8_general_ci',
	`source` VARCHAR(164) NULL COLLATE 'utf8_general_ci',
	`created_date` TIMESTAMP NOT NULL,
	`created_by` INT(11) NOT NULL,
	`updated_date` TIMESTAMP NOT NULL,
	`updated_by` INT(11) NOT NULL,
	`deleted_date` TIMESTAMP NULL,
	`deleted_by` INT(11) NULL,
	`version` INT(11) NOT NULL,
	`legacy_casing_id` INT(11) NULL
) ENGINE=MyISAM;

-- Dumping structure for view volume_decision
DROP VIEW IF EXISTS `volume_decision`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `volume_decision` (
	`store_id` INT(11) NULL,
	`decision` VARCHAR(11) NOT NULL COLLATE 'utf8mb4_general_ci'
) ENGINE=MyISAM;

-- Dumping structure for view volume_decision_params
DROP VIEW IF EXISTS `volume_decision_params`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `volume_decision_params` (
	`store_id` INT(11) NOT NULL,
	`has_collected_data` INT(1) NOT NULL,
	`has_casing` INT(1) NOT NULL,
	`old_casing` INT(1) NOT NULL,
	`stale_model` INT(1) NOT NULL,
	`has_actual` INT(1) NOT NULL,
	`old_actual` INT(1) NOT NULL,
	`has_reported` INT(1) NOT NULL,
	`old_reported` INT(1) NOT NULL,
	`reported_newer_than_actual` INT(1) NOT NULL,
	`untrusted_reported` INT(1) NOT NULL,
	`has_projection` INT(1) NOT NULL,
	`volume_actual` INT(11) NULL,
	`volume_actual_date` DATE NULL,
	`volume_estimate` INT(11) NULL,
	`volume_estimate_date` DATE NULL,
	`volume_projection` INT(11) NULL,
	`volume_projection_date` DATE NULL,
	`volume_reported` INT(11) NULL,
	`volume_reported_date` DATE NULL,
	`volume_third_party` INT(11) NULL,
	`volume_third_party_date` DATE NULL
) ENGINE=MyISAM;

-- Dumping structure for view active_stores_with_strengths
DROP VIEW IF EXISTS `active_stores_with_strengths`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `active_stores_with_strengths`;
CREATE VIEW `active_stores_with_strengths` AS select `st`.`store_id` AS `store_id`,`si`.`latitude` AS `latitude`,`si`.`longitude` AS `longitude`,`si`.`address_1` AS `address_1`,`si`.`address_2` AS `address_2`,`si`.`city` AS `city`,`si`.`county` AS `county`,`si`.`state` AS `state`,`si`.`postal_code` AS `postal_code`,`si`.`quad` AS `quad`,`si`.`intersection_street_primary` AS `intersection_street_primary`,`si`.`intersection_street_secondary` AS `intersection_street_secondary`,`st`.`store_type` AS `store_type`,`st`.`store_name` AS `store_name`,`st`.`store_number` AS `store_number`,`stat`.`status` AS `store_status`,(case when (`strength`.`strength` > 1) then 1 when (`strength`.`strength` < 0) then 0 else `strength`.`strength` end) AS `store_strength`,coalesce(`mrss`.`store_fit`,`b`.`default_store_fit`,'Conventional') AS `store_fit`,(case when (isnull(`mrss`.`store_fit`) and isnull(`b`.`default_store_fit`)) then 'TRUE' else NULL end) AS `using_default_fit`,`b`.`banner_id` AS `banner_id`,`b`.`banner_name` AS `banner_name`,`c1`.`company_id` AS `company_id`,`c1`.`company_name` AS `company_name`,`c2`.`company_id` AS `parent_company_id`,`c2`.`company_name` AS `parent_company_name`,coalesce(`mrss`.`area_sales`,`b`.`default_sales_area`) AS `area_sales`,`mrss`.`area_total` AS `area_total`,`sbv`.`decision` AS `volume_choice`,`sbv`.`volume_total` AS `volume_total`,`sbv`.`volume_date` AS `volume_date`,`scs`.`score` AS `shopping_center_feature_score`,`mrscc`.`rating_synergy` AS `rating_synergy`,`mrscs`.`tenant_vacant_count` AS `tenant_vacant_count`,`mrscs`.`tenant_occupied_count` AS `tenant_occupied_count`,`mrscs`.`parking_space_count` AS `parking_space_count`,`st`.`legacy_location_id` AS `legacy_location_id` from ((((((((((((`site` `si` join `store` `st` on((`si`.`site_id` = `st`.`site_id`))) left join `banner` `b` on((`b`.`banner_id` = `st`.`banner_id`))) left join `company` `c1` on((`c1`.`company_id` = `b`.`company_id`))) left join `company` `c2` on((`c2`.`company_id` = `c1`.`parent_company_id`))) left join `store_strength` `strength` on((`strength`.`store_id` = `st`.`store_id`))) left join `most_recent_store_survey` `mrss` on((`mrss`.`store_id` = `st`.`store_id`))) left join `most_recent_store_casing` `mrsc` on((`mrsc`.`store_id` = `st`.`store_id`))) left join `store_best_volume` `sbv` on((`sbv`.`store_id` = `st`.`store_id`))) left join `most_recent_store_status` `stat` on((`stat`.`store_id` = `st`.`store_id`))) left join `most_recent_shopping_center_survey` `mrscs` on((`mrscs`.`shopping_center_id` = `si`.`shopping_center_id`))) left join `most_recent_shopping_center_casing` `mrscc` on((`mrscc`.`shopping_center_id` = `si`.`shopping_center_id`))) left join `shopping_center_score` `scs` on((`scs`.`shopping_center_survey_id` = `mrscs`.`shopping_center_survey_id`))) where (`st`.`store_type` = 'ACTIVE');

-- Dumping structure for view average_sc_score
DROP VIEW IF EXISTS `average_sc_score`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `average_sc_score`;
CREATE VIEW `average_sc_score` AS select `scc`.`shopping_center_id` AS `shopping_center_id`,avg(`s`.`score`) AS `score` from (`sc_condition_score` `s` join `shopping_center_casing` `scc` on((`scc`.`shopping_center_casing_id` = `s`.`shopping_center_casing_id`))) group by `scc`.`shopping_center_id`;

-- Dumping structure for view avg_by_banner
DROP VIEW IF EXISTS `avg_by_banner`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `avg_by_banner`;
CREATE VIEW `avg_by_banner` AS select `st`.`banner_id` AS `banner_id`,avg(`sv`.`volume_total`) AS `volume`,avg(`ss`.`area_sales`) AS `area_sales`,avg(`ss`.`area_total`) AS `area_total`,(avg(`sv`.`volume_total`) / avg(`ss`.`area_sales`)) AS `dpsf` from ((`store` `st` join `store_volume` `sv` on((`sv`.`store_id` = `st`.`store_id`))) left join `most_recent_store_survey` `ss` on((`st`.`store_id` = `ss`.`store_id`))) where (`st`.`banner_id` is not null) group by `st`.`banner_id`;

-- Dumping structure for view avg_by_banner_in_county
DROP VIEW IF EXISTS `avg_by_banner_in_county`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `avg_by_banner_in_county`;
CREATE VIEW `avg_by_banner_in_county` AS select `st`.`banner_id` AS `banner_id`,`si`.`state` AS `state`,`si`.`county` AS `county`,avg(`sv`.`volume_total`) AS `volume`,avg(`ss`.`area_sales`) AS `area_sales`,avg(`ss`.`area_total`) AS `area_total`,(avg(`sv`.`volume_total`) / avg(`ss`.`area_sales`)) AS `dpsf` from (((`store` `st` join `site` `si` on((`st`.`site_id` = `si`.`site_id`))) join `store_volume` `sv` on((`sv`.`store_id` = `st`.`store_id`))) left join `most_recent_store_survey` `ss` on((`st`.`store_id` = `ss`.`store_id`))) where (`st`.`banner_id` is not null) group by `st`.`banner_id`,`si`.`state`,`si`.`county`;

-- Dumping structure for view avg_by_county
DROP VIEW IF EXISTS `avg_by_county`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `avg_by_county`;
CREATE VIEW `avg_by_county` AS select `si`.`state` AS `state`,`si`.`county` AS `county`,avg(`sv`.`volume_total`) AS `volume`,avg(`ss`.`area_sales`) AS `area_sales`,avg(`ss`.`area_total`) AS `area_total`,(avg(`sv`.`volume_total`) / avg(`ss`.`area_sales`)) AS `dpsf` from (((`store` `st` join `site` `si` on((`st`.`site_id` = `si`.`site_id`))) join `store_volume` `sv` on((`sv`.`store_id` = `st`.`store_id`))) left join `most_recent_store_survey` `ss` on((`st`.`store_id` = `ss`.`store_id`))) group by `si`.`state`,`si`.`county`;

-- Dumping structure for view avg_by_fit
DROP VIEW IF EXISTS `avg_by_fit`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `avg_by_fit`;
CREATE VIEW `avg_by_fit` AS select (`ss`.`store_fit` collate utf8_unicode_ci) AS `store_fit`,avg(`sv`.`volume_total`) AS `volume`,avg(`ss`.`area_sales`) AS `area_sales`,avg(`ss`.`area_total`) AS `area_total`,(avg(`sv`.`volume_total`) / avg(`ss`.`area_sales`)) AS `dpsf` from ((`store` `st` join `store_volume` `sv` on((`sv`.`store_id` = `st`.`store_id`))) left join `most_recent_store_survey` `ss` on((`ss`.`store_id` = `st`.`store_id`))) where (`ss`.`store_fit` is not null) group by `ss`.`store_fit`;

-- Dumping structure for view avg_by_fit_in_county
DROP VIEW IF EXISTS `avg_by_fit_in_county`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `avg_by_fit_in_county`;
CREATE VIEW `avg_by_fit_in_county` AS select (`ss`.`store_fit` collate utf8_unicode_ci) AS `store_fit`,`si`.`state` AS `state`,`si`.`county` AS `county`,avg(`sv`.`volume_total`) AS `volume`,avg(`ss`.`area_sales`) AS `area_sales`,avg(`ss`.`area_total`) AS `area_total`,(avg(`sv`.`volume_total`) / avg(`ss`.`area_sales`)) AS `dpsf` from (((`store` `st` join `site` `si` on((`st`.`site_id` = `si`.`site_id`))) join `store_volume` `sv` on((`sv`.`store_id` = `st`.`store_id`))) left join `most_recent_store_survey` `ss` on((`ss`.`store_id` = `st`.`store_id`))) where (`ss`.`store_fit` is not null) group by `ss`.`store_fit`,`si`.`state`,`si`.`county`;

-- Dumping structure for view most_recent_shopping_center_casing
DROP VIEW IF EXISTS `most_recent_shopping_center_casing`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `most_recent_shopping_center_casing`;
CREATE VIEW `most_recent_shopping_center_casing` AS select `s1`.`shopping_center_casing_id` AS `shopping_center_casing_id`,`s1`.`shopping_center_id` AS `shopping_center_id`,`s1`.`shopping_center_casing_date` AS `shopping_center_casing_date`,`s1`.`shopping_center_casing_note` AS `shopping_center_casing_note`,`s1`.`rating_parking_lot` AS `rating_parking_lot`,`s1`.`rating_buildings` AS `rating_buildings`,`s1`.`rating_lighting` AS `rating_lighting`,`s1`.`rating_synergy` AS `rating_synergy`,`s1`.`created_date` AS `created_date`,`s1`.`created_by` AS `created_by`,`s1`.`updated_date` AS `updated_date`,`s1`.`updated_by` AS `updated_by`,`s1`.`deleted_date` AS `deleted_date`,`s1`.`deleted_by` AS `deleted_by`,`s1`.`version` AS `version`,`s1`.`legacy_casing_id` AS `legacy_casing_id`,`s1`.`shopping_center_survey_id` AS `shopping_center_survey_id` from (`shopping_center_casing` `s1` left join `shopping_center_casing` `s2` on(((`s1`.`shopping_center_id` = `s2`.`shopping_center_id`) and ((`s1`.`shopping_center_casing_date` < `s2`.`shopping_center_casing_date`) or ((`s1`.`shopping_center_casing_date` = `s2`.`shopping_center_casing_date`) and (`s1`.`shopping_center_casing_id` < `s2`.`shopping_center_casing_id`)))))) where isnull(`s2`.`shopping_center_id`);

-- Dumping structure for view most_recent_shopping_center_survey
DROP VIEW IF EXISTS `most_recent_shopping_center_survey`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `most_recent_shopping_center_survey`;
CREATE VIEW `most_recent_shopping_center_survey` AS select `s1`.`shopping_center_survey_id` AS `shopping_center_survey_id`,`s1`.`shopping_center_id` AS `shopping_center_id`,`s1`.`shopping_center_survey_date` AS `shopping_center_survey_date`,`s1`.`center_type` AS `center_type`,`s1`.`shopping_center_survey_note` AS `shopping_center_survey_note`,`s1`.`flow_has_landscaping` AS `flow_has_landscaping`,`s1`.`flow_has_speed_bumps` AS `flow_has_speed_bumps`,`s1`.`flow_has_stop_signs` AS `flow_has_stop_signs`,`s1`.`flow_has_one_way_aisles` AS `flow_has_one_way_aisles`,`s1`.`parking_has_angled_spaces` AS `parking_has_angled_spaces`,`s1`.`parking_has_parking_hog` AS `parking_has_parking_hog`,`s1`.`parking_is_poorly_lit` AS `parking_is_poorly_lit`,`s1`.`parking_space_count` AS `parking_space_count`,`s1`.`tenant_occupied_count` AS `tenant_occupied_count`,`s1`.`tenant_vacant_count` AS `tenant_vacant_count`,`s1`.`sq_ft_percent_occupied` AS `sq_ft_percent_occupied`,`s1`.`created_date` AS `created_date`,`s1`.`created_by` AS `created_by`,`s1`.`updated_date` AS `updated_date`,`s1`.`updated_by` AS `updated_by`,`s1`.`deleted_date` AS `deleted_date`,`s1`.`deleted_by` AS `deleted_by`,`s1`.`version` AS `version`,`s1`.`legacy_casing_id` AS `legacy_casing_id` from (`shopping_center_survey` `s1` left join `shopping_center_survey` `s2` on(((`s1`.`shopping_center_id` = `s2`.`shopping_center_id`) and ((`s1`.`shopping_center_survey_date` < `s2`.`shopping_center_survey_date`) or ((`s1`.`shopping_center_survey_date` = `s2`.`shopping_center_survey_date`) and (`s1`.`shopping_center_survey_id` < `s2`.`shopping_center_survey_id`)))))) where isnull(`s2`.`shopping_center_id`);

-- Dumping structure for view most_recent_store_casing
DROP VIEW IF EXISTS `most_recent_store_casing`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `most_recent_store_casing`;
CREATE VIEW `most_recent_store_casing` AS select `s1`.`store_casing_id` AS `store_casing_id`,`s1`.`store_id` AS `store_id`,`s1`.`store_casing_date` AS `store_casing_date`,`s1`.`store_casing_note` AS `store_casing_note`,`s1`.`store_status_id` AS `store_status_id`,`s1`.`condition_ceiling` AS `condition_ceiling`,`s1`.`condition_checkstands` AS `condition_checkstands`,`s1`.`condition_floors` AS `condition_floors`,`s1`.`condition_frozen_refrigerated` AS `condition_frozen_refrigerated`,`s1`.`condition_shelving_gondolas` AS `condition_shelving_gondolas`,`s1`.`condition_walls` AS `condition_walls`,`s1`.`fuel_gallons_weekly` AS `fuel_gallons_weekly`,`s1`.`pharmacy_scripts_weekly` AS `pharmacy_scripts_weekly`,`s1`.`pharmacy_avg_dollars_per_script` AS `pharmacy_avg_dollars_per_script`,`s1`.`pharmacy_pharmacist_count` AS `pharmacy_pharmacist_count`,`s1`.`pharmacy_technician_count` AS `pharmacy_technician_count`,`s1`.`store_volume_id` AS `store_volume_id`,`s1`.`created_date` AS `created_date`,`s1`.`created_by` AS `created_by`,`s1`.`updated_date` AS `updated_date`,`s1`.`updated_by` AS `updated_by`,`s1`.`deleted_date` AS `deleted_date`,`s1`.`deleted_by` AS `deleted_by`,`s1`.`version` AS `version`,`s1`.`legacy_casing_id` AS `legacy_casing_id`,`s1`.`store_survey_id` AS `store_survey_id` from (`store_casing` `s1` left join `store_casing` `s2` on(((`s1`.`store_id` = `s2`.`store_id`) and ((`s1`.`store_casing_date` < `s2`.`store_casing_date`) or ((`s1`.`store_casing_date` = `s2`.`store_casing_date`) and (`s1`.`store_casing_id` < `s2`.`store_casing_id`)))))) where isnull(`s2`.`store_id`);

-- Dumping structure for view most_recent_store_model
DROP VIEW IF EXISTS `most_recent_store_model`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `most_recent_store_model`;
CREATE VIEW `most_recent_store_model` AS select `s1`.`store_model_id` AS `store_model_id`,`s1`.`store_id` AS `store_id`,`s1`.`project_id` AS `project_id`,`s1`.`mapkey` AS `mapkey`,`s1`.`curve` AS `curve`,`s1`.`pwta` AS `pwta`,`s1`.`power` AS `power`,`s1`.`fit_adjusted_power` AS `fit_adjusted_power`,`s1`.`model_type` AS `model_type`,`s1`.`model_date` AS `model_date`,`s1`.`created_date` AS `created_date`,`s1`.`created_by` AS `created_by`,`s1`.`updated_date` AS `updated_date`,`s1`.`updated_by` AS `updated_by`,`s1`.`deleted_date` AS `deleted_date`,`s1`.`deleted_by` AS `deleted_by`,`s1`.`version` AS `version`,`s1`.`legacy_casing_id` AS `legacy_casing_id` from (`store_model` `s1` left join `store_model` `s2` on(((`s1`.`store_id` = `s2`.`store_id`) and ((`s1`.`model_date` < `s2`.`model_date`) or ((`s1`.`model_date` = `s2`.`model_date`) and (`s1`.`store_model_id` < `s2`.`store_model_id`)))))) where isnull(`s2`.`store_id`);

-- Dumping structure for view most_recent_store_status
DROP VIEW IF EXISTS `most_recent_store_status`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `most_recent_store_status`;
CREATE VIEW `most_recent_store_status` AS select `ss1`.`store_status_id` AS `store_status_id`,`ss1`.`store_id` AS `store_id`,`ss1`.`status` AS `status`,`ss1`.`status_start_date` AS `status_start_date`,`ss1`.`created_date` AS `created_date`,`ss1`.`created_by` AS `created_by`,`ss1`.`updated_date` AS `updated_date`,`ss1`.`updated_by` AS `updated_by`,`ss1`.`deleted_date` AS `deleted_date`,`ss1`.`deleted_by` AS `deleted_by`,`ss1`.`version` AS `version`,`ss1`.`legacy_location_id` AS `legacy_location_id`,`ss1`.`legacy_casing_id` AS `legacy_casing_id` from (`store_status` `ss1` left join `store_status` `ss2` on(((`ss1`.`store_id` = `ss2`.`store_id`) and (`ss1`.`status_start_date` < `ss2`.`status_start_date`)))) where isnull(`ss2`.`store_status_id`);

-- Dumping structure for view most_recent_store_survey
DROP VIEW IF EXISTS `most_recent_store_survey`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `most_recent_store_survey`;
CREATE VIEW `most_recent_store_survey` AS select `s1`.`store_survey_id` AS `store_survey_id`,`s1`.`store_id` AS `store_id`,`s1`.`store_survey_date` AS `store_survey_date`,`s1`.`store_survey_note` AS `store_survey_note`,`s1`.`store_fit` AS `store_fit`,`s1`.`store_format` AS `store_format`,`s1`.`store_is_open_24` AS `store_is_open_24`,`s1`.`area_sales` AS `area_sales`,`s1`.`area_sales_percent_of_total` AS `area_sales_percent_of_total`,`s1`.`area_total` AS `area_total`,`s1`.`area_is_estimate` AS `area_is_estimate`,`s1`.`natural_foods_are_integrated` AS `natural_foods_are_integrated`,`s1`.`register_count_normal` AS `register_count_normal`,`s1`.`register_count_express` AS `register_count_express`,`s1`.`register_count_self_checkout` AS `register_count_self_checkout`,`s1`.`fuel_dispenser_count` AS `fuel_dispenser_count`,`s1`.`fuel_is_open_24` AS `fuel_is_open_24`,`s1`.`pharmacy_is_open_24` AS `pharmacy_is_open_24`,`s1`.`pharmacy_has_drive_through` AS `pharmacy_has_drive_through`,`s1`.`department_bakery` AS `department_bakery`,`s1`.`department_bank` AS `department_bank`,`s1`.`department_beer` AS `department_beer`,`s1`.`department_bulk` AS `department_bulk`,`s1`.`department_cheese` AS `department_cheese`,`s1`.`department_coffee` AS `department_coffee`,`s1`.`department_deli` AS `department_deli`,`s1`.`department_expanded_gm` AS `department_expanded_gm`,`s1`.`department_extensive_prepared_foods` AS `department_extensive_prepared_foods`,`s1`.`department_floral` AS `department_floral`,`s1`.`department_fuel` AS `department_fuel`,`s1`.`department_hot_bar` AS `department_hot_bar`,`s1`.`department_in_store_restaurant` AS `department_in_store_restaurant`,`s1`.`department_liquor` AS `department_liquor`,`s1`.`department_meat` AS `department_meat`,`s1`.`department_natural` AS `department_natural`,`s1`.`department_olive_bar` AS `department_olive_bar`,`s1`.`department_online_pickup` AS `department_online_pickup`,`s1`.`department_pharmacy` AS `department_pharmacy`,`s1`.`department_prepared_foods` AS `department_prepared_foods`,`s1`.`department_salad_bar` AS `department_salad_bar`,`s1`.`department_seafood` AS `department_seafood`,`s1`.`department_seating` AS `department_seating`,`s1`.`department_sushi` AS `department_sushi`,`s1`.`department_wine` AS `department_wine`,`s1`.`accessibility_farthest_from_entrance` AS `accessibility_farthest_from_entrance`,`s1`.`accessibility_main_intersection_has_traffic_light` AS `accessibility_main_intersection_has_traffic_light`,`s1`.`accessibility_main_intersection_needs_traffic_light` AS `accessibility_main_intersection_needs_traffic_light`,`s1`.`accessibility_multiple_retailers_before_site` AS `accessibility_multiple_retailers_before_site`,`s1`.`accessibility_set_back_twice_parking_length` AS `accessibility_set_back_twice_parking_length`,`s1`.`accessibility_rating` AS `accessibility_rating`,`s1`.`parking_outparcels_interfere_with_parking` AS `parking_outparcels_interfere_with_parking`,`s1`.`parking_direct_access_to_parking` AS `parking_direct_access_to_parking`,`s1`.`parking_small_parking_field` AS `parking_small_parking_field`,`s1`.`parking_has_t_spaces` AS `parking_has_t_spaces`,`s1`.`parking_rating` AS `parking_rating`,`s1`.`visibility_hill_depression_blocks_view` AS `visibility_hill_depression_blocks_view`,`s1`.`visibility_outparcels_block_view` AS `visibility_outparcels_block_view`,`s1`.`visibility_sign_on_main` AS `visibility_sign_on_main`,`s1`.`visibility_store_faces_main_road` AS `visibility_store_faces_main_road`,`s1`.`visibility_trees_block_view` AS `visibility_trees_block_view`,`s1`.`visibility_rating` AS `visibility_rating`,`s1`.`seasonality_jan` AS `seasonality_jan`,`s1`.`seasonality_feb` AS `seasonality_feb`,`s1`.`seasonality_mar` AS `seasonality_mar`,`s1`.`seasonality_apr` AS `seasonality_apr`,`s1`.`seasonality_may` AS `seasonality_may`,`s1`.`seasonality_jun` AS `seasonality_jun`,`s1`.`seasonality_jul` AS `seasonality_jul`,`s1`.`seasonality_aug` AS `seasonality_aug`,`s1`.`seasonality_sep` AS `seasonality_sep`,`s1`.`seasonality_oct` AS `seasonality_oct`,`s1`.`seasonality_nov` AS `seasonality_nov`,`s1`.`seasonality_dec` AS `seasonality_dec`,`s1`.`created_date` AS `created_date`,`s1`.`created_by` AS `created_by`,`s1`.`updated_date` AS `updated_date`,`s1`.`updated_by` AS `updated_by`,`s1`.`deleted_date` AS `deleted_date`,`s1`.`deleted_by` AS `deleted_by`,`s1`.`version` AS `version`,`s1`.`legacy_casing_id` AS `legacy_casing_id` from (`store_survey` `s1` left join `store_survey` `s2` on(((`s1`.`store_id` = `s2`.`store_id`) and ((`s1`.`store_survey_date` < `s2`.`store_survey_date`) or ((`s1`.`store_survey_date` = `s2`.`store_survey_date`) and (`s1`.`store_survey_id` < `s2`.`store_survey_id`)))))) where isnull(`s2`.`store_id`);

-- Dumping structure for view most_recent_store_volume
DROP VIEW IF EXISTS `most_recent_store_volume`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `most_recent_store_volume`;
CREATE VIEW `most_recent_store_volume` AS select `s1`.`store_volume_id` AS `store_volume_id`,`s1`.`store_id` AS `store_id`,`s1`.`volume_total` AS `volume_total`,`s1`.`volume_date` AS `volume_date`,`s1`.`volume_type` AS `volume_type`,`s1`.`source` AS `source`,`s1`.`volume_grocery` AS `volume_grocery`,`s1`.`volume_percent_grocery` AS `volume_percent_grocery`,`s1`.`volume_meat` AS `volume_meat`,`s1`.`volume_percent_meat` AS `volume_percent_meat`,`s1`.`volume_non_food` AS `volume_non_food`,`s1`.`volume_percent_non_food` AS `volume_percent_non_food`,`s1`.`volume_other` AS `volume_other`,`s1`.`volume_percent_other` AS `volume_percent_other`,`s1`.`volume_produce` AS `volume_produce`,`s1`.`volume_percent_produce` AS `volume_percent_produce`,`s1`.`volume_plus_minus` AS `volume_plus_minus`,`s1`.`volume_note` AS `volume_note`,`s1`.`volume_confidence` AS `volume_confidence`,`s1`.`created_date` AS `created_date`,`s1`.`created_by` AS `created_by`,`s1`.`updated_date` AS `updated_date`,`s1`.`updated_by` AS `updated_by`,`s1`.`deleted_date` AS `deleted_date`,`s1`.`deleted_by` AS `deleted_by`,`s1`.`version` AS `version`,`s1`.`legacy_casing_id` AS `legacy_casing_id` from (`store_volume` `s1` left join `store_volume` `s2` on(((`s1`.`store_id` = `s2`.`store_id`) and ((`s1`.`volume_date` < `s2`.`volume_date`) or ((`s1`.`volume_date` = `s2`.`volume_date`) and (`s1`.`store_volume_id` < `s2`.`store_volume_id`)))))) where isnull(`s2`.`store_id`);

-- Dumping structure for view most_recent_store_volume_actual
DROP VIEW IF EXISTS `most_recent_store_volume_actual`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `most_recent_store_volume_actual`;
CREATE VIEW `most_recent_store_volume_actual` AS select `s1`.`store_volume_id` AS `store_volume_id`,`s1`.`store_id` AS `store_id`,`s1`.`volume_total` AS `volume_total`,`s1`.`volume_date` AS `volume_date`,`s1`.`volume_type` AS `volume_type`,`s1`.`source` AS `source`,`s1`.`created_date` AS `created_date`,`s1`.`created_by` AS `created_by`,`s1`.`updated_date` AS `updated_date`,`s1`.`updated_by` AS `updated_by`,`s1`.`deleted_date` AS `deleted_date`,`s1`.`deleted_by` AS `deleted_by`,`s1`.`version` AS `version`,`s1`.`legacy_casing_id` AS `legacy_casing_id` from (`store_volume_actual` `s1` left join `store_volume_actual` `s2` on(((`s1`.`store_id` = `s2`.`store_id`) and (`s1`.`volume_date` < `s2`.`volume_date`)))) where isnull(`s2`.`store_id`) order by `s1`.`store_id`;

-- Dumping structure for view most_recent_store_volume_estimate
DROP VIEW IF EXISTS `most_recent_store_volume_estimate`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `most_recent_store_volume_estimate`;
CREATE VIEW `most_recent_store_volume_estimate` AS select `s1`.`store_volume_id` AS `store_volume_id`,`s1`.`store_id` AS `store_id`,`s1`.`volume_total` AS `volume_total`,`s1`.`volume_date` AS `volume_date`,`s1`.`volume_type` AS `volume_type`,`s1`.`source` AS `source`,`s1`.`created_date` AS `created_date`,`s1`.`created_by` AS `created_by`,`s1`.`updated_date` AS `updated_date`,`s1`.`updated_by` AS `updated_by`,`s1`.`deleted_date` AS `deleted_date`,`s1`.`deleted_by` AS `deleted_by`,`s1`.`version` AS `version`,`s1`.`legacy_casing_id` AS `legacy_casing_id` from (`store_volume_estimate` `s1` left join `store_volume_estimate` `s2` on(((`s1`.`store_id` = `s2`.`store_id`) and (`s1`.`volume_date` < `s2`.`volume_date`)))) where isnull(`s2`.`store_id`) order by `s1`.`store_id`;

-- Dumping structure for view most_recent_store_volume_projection
DROP VIEW IF EXISTS `most_recent_store_volume_projection`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `most_recent_store_volume_projection`;
CREATE VIEW `most_recent_store_volume_projection` AS select `s1`.`store_volume_id` AS `store_volume_id`,`s1`.`store_id` AS `store_id`,`s1`.`volume_total` AS `volume_total`,`s1`.`volume_date` AS `volume_date`,`s1`.`volume_type` AS `volume_type`,`s1`.`source` AS `source`,`s1`.`created_date` AS `created_date`,`s1`.`created_by` AS `created_by`,`s1`.`updated_date` AS `updated_date`,`s1`.`updated_by` AS `updated_by`,`s1`.`deleted_date` AS `deleted_date`,`s1`.`deleted_by` AS `deleted_by`,`s1`.`version` AS `version`,`s1`.`legacy_casing_id` AS `legacy_casing_id` from (`store_volume_projection` `s1` left join `store_volume_projection` `s2` on(((`s1`.`store_id` = `s2`.`store_id`) and (`s1`.`volume_date` < `s2`.`volume_date`)))) where isnull(`s2`.`store_id`) order by `s1`.`store_id`;

-- Dumping structure for view most_recent_store_volume_reported
DROP VIEW IF EXISTS `most_recent_store_volume_reported`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `most_recent_store_volume_reported`;
CREATE VIEW `most_recent_store_volume_reported` AS select `s1`.`store_volume_id` AS `store_volume_id`,`s1`.`store_id` AS `store_id`,`s1`.`volume_total` AS `volume_total`,`s1`.`volume_date` AS `volume_date`,`s1`.`volume_type` AS `volume_type`,`s1`.`source` AS `source`,`s1`.`created_date` AS `created_date`,`s1`.`created_by` AS `created_by`,`s1`.`updated_date` AS `updated_date`,`s1`.`updated_by` AS `updated_by`,`s1`.`deleted_date` AS `deleted_date`,`s1`.`deleted_by` AS `deleted_by`,`s1`.`version` AS `version`,`s1`.`legacy_casing_id` AS `legacy_casing_id` from (`store_volume_reported` `s1` left join `store_volume_reported` `s2` on(((`s1`.`store_id` = `s2`.`store_id`) and (`s1`.`volume_date` < `s2`.`volume_date`)))) where isnull(`s2`.`store_id`) order by `s1`.`store_id`;

-- Dumping structure for view most_recent_store_volume_third_party
DROP VIEW IF EXISTS `most_recent_store_volume_third_party`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `most_recent_store_volume_third_party`;
CREATE VIEW `most_recent_store_volume_third_party` AS select `s1`.`store_volume_id` AS `store_volume_id`,`s1`.`store_id` AS `store_id`,`s1`.`volume_total` AS `volume_total`,`s1`.`volume_date` AS `volume_date`,`s1`.`volume_type` AS `volume_type`,`s1`.`source` AS `source`,`s1`.`created_date` AS `created_date`,`s1`.`created_by` AS `created_by`,`s1`.`updated_date` AS `updated_date`,`s1`.`updated_by` AS `updated_by`,`s1`.`deleted_date` AS `deleted_date`,`s1`.`deleted_by` AS `deleted_by`,`s1`.`version` AS `version`,`s1`.`legacy_casing_id` AS `legacy_casing_id` from (`store_volume_third_party` `s1` left join `store_volume_third_party` `s2` on(((`s1`.`store_id` = `s2`.`store_id`) and (`s1`.`volume_date` < `s2`.`volume_date`)))) where isnull(`s2`.`store_id`) order by `s1`.`store_id`;

-- Dumping structure for view sc_condition_global_avg_score
DROP VIEW IF EXISTS `sc_condition_global_avg_score`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `sc_condition_global_avg_score`;
CREATE VIEW `sc_condition_global_avg_score` AS select avg((((`sccs`.`score_parking_lot` + `sccs`.`score_buildings`) + `sccs`.`score_lighting`) + `sccs`.`score_synergy`)) AS `average` from `shopping_center_condition_scores` `sccs`;

-- Dumping structure for view sc_condition_index
DROP VIEW IF EXISTS `sc_condition_index`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `sc_condition_index`;
CREATE VIEW `sc_condition_index` AS select `s`.`shopping_center_casing_id` AS `shopping_center_casing_id`,(((ifnull(`s`.`score_parking_lot`,0) + ifnull(`s`.`score_buildings`,0)) + ifnull(`s`.`score_lighting`,0)) + ifnull(`s`.`score_synergy`,0)) AS `score_total`,round((((((ifnull(`s`.`score_parking_lot`,0) + ifnull(`s`.`score_buildings`,0)) + ifnull(`s`.`score_lighting`,0)) + ifnull(`s`.`score_synergy`,0)) / `a`.`average`) * 100),0) AS `indexed` from (`shopping_center_condition_scores` `s` join `sc_condition_global_avg_score` `a`) where ((((ifnull(`s`.`score_parking_lot`,0) + ifnull(`s`.`score_buildings`,0)) + ifnull(`s`.`score_lighting`,0)) + ifnull(`s`.`score_synergy`,0)) > 0);

-- Dumping structure for view sc_condition_score
DROP VIEW IF EXISTS `sc_condition_score`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `sc_condition_score`;
CREATE VIEW `sc_condition_score` AS select `s`.`shopping_center_casing_id` AS `shopping_center_casing_id`,((((ifnull(`s`.`score_parking_lot`,0) + ifnull(`s`.`score_buildings`,0)) + ifnull(`s`.`score_lighting`,0)) + ifnull(`s`.`score_synergy`,0)) / (((if(isnull(`s`.`rating_parking_lot`),0,5) + if(isnull(`s`.`rating_buildings`),0,5)) + if(isnull(`s`.`rating_lighting`),0,5)) + if(isnull(`s`.`rating_synergy`),0,5))) AS `score` from `shopping_center_condition_scores` `s` where ((`s`.`rating_parking_lot` is not null) or (`s`.`rating_buildings` is not null) or (`s`.`rating_lighting` is not null) or (`s`.`rating_synergy` is not null));

-- Dumping structure for view shopping_center_access_front_main
DROP VIEW IF EXISTS `shopping_center_access_front_main`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `shopping_center_access_front_main`;
CREATE VIEW `shopping_center_access_front_main` AS select `sca`.`shopping_center_survey_id` AS `shopping_center_survey_id`,`sca`.`access_type` AS `access_type`,sum(`sca`.`has_left_in`) AS `has_left_in`,sum(`sca`.`has_left_out`) AS `has_left_out`,sum(`sca`.`has_right_in`) AS `has_right_in`,sum(`sca`.`has_right_out`) AS `has_right_out`,sum(`sca`.`has_traffic_light`) AS `has_traffic_light`,sum(`sca`.`one_way_road`) AS `one_way_road`,count(0) AS `count_front_mains` from `shopping_center_access` `sca` where (`sca`.`access_type` = 'FRONT_MAIN') group by `sca`.`shopping_center_survey_id`,`sca`.`access_type`;

-- Dumping structure for view shopping_center_access_non_main
DROP VIEW IF EXISTS `shopping_center_access_non_main`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `shopping_center_access_non_main`;
CREATE VIEW `shopping_center_access_non_main` AS select `sca`.`shopping_center_survey_id` AS `shopping_center_survey_id`,`sca`.`access_type` AS `access_type`,sum(`sca`.`has_left_in`) AS `has_left_in`,sum(`sca`.`has_left_out`) AS `has_left_out`,sum(`sca`.`has_right_in`) AS `has_right_in`,sum(`sca`.`has_right_out`) AS `has_right_out`,sum(`sca`.`has_traffic_light`) AS `has_traffic_light`,sum(`sca`.`one_way_road`) AS `one_way_road`,count(0) AS `count_side_mains` from `shopping_center_access` `sca` where (`sca`.`access_type` = 'NON_MAIN') group by `sca`.`shopping_center_survey_id`,`sca`.`access_type`;

-- Dumping structure for view shopping_center_access_side_main
DROP VIEW IF EXISTS `shopping_center_access_side_main`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `shopping_center_access_side_main`;
CREATE VIEW `shopping_center_access_side_main` AS select `sca`.`shopping_center_survey_id` AS `shopping_center_survey_id`,`sca`.`access_type` AS `access_type`,sum(`sca`.`has_left_in`) AS `has_left_in`,sum(`sca`.`has_left_out`) AS `has_left_out`,sum(`sca`.`has_right_in`) AS `has_right_in`,sum(`sca`.`has_right_out`) AS `has_right_out`,sum(`sca`.`has_traffic_light`) AS `has_traffic_light`,sum(`sca`.`one_way_road`) AS `one_way_road`,count(0) AS `count_non_mains` from `shopping_center_access` `sca` where (`sca`.`access_type` = 'SIDE_MAIN') group by `sca`.`shopping_center_survey_id`,`sca`.`access_type`;

-- Dumping structure for view shopping_center_condition_scores
DROP VIEW IF EXISTS `shopping_center_condition_scores`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `shopping_center_condition_scores`;
CREATE VIEW `shopping_center_condition_scores` AS select `scc`.`shopping_center_casing_id` AS `shopping_center_casing_id`,`scc`.`rating_parking_lot` AS `rating_parking_lot`,`scc`.`rating_buildings` AS `rating_buildings`,`scc`.`rating_lighting` AS `rating_lighting`,`scc`.`rating_synergy` AS `rating_synergy`,(case `scc`.`rating_parking_lot` when 'POOR' then 1 when 'FAIR' then 2 when 'AVERAGE' then 3 when 'GOOD' then 4 when 'EXCELLENT' then 5 end) AS `score_parking_lot`,(case `scc`.`rating_buildings` when 'POOR' then 1 when 'FAIR' then 2 when 'AVERAGE' then 3 when 'GOOD' then 4 when 'EXCELLENT' then 5 end) AS `score_buildings`,(case `scc`.`rating_lighting` when 'POOR' then 1 when 'FAIR' then 2 when 'AVERAGE' then 3 when 'GOOD' then 4 when 'EXCELLENT' then 5 end) AS `score_lighting`,(case `scc`.`rating_synergy` when 'POOR' then 1 when 'FAIR' then 2 when 'AVERAGE' then 3 when 'GOOD' then 4 when 'EXCELLENT' then 5 end) AS `score_synergy` from `shopping_center_casing` `scc`;

-- Dumping structure for view shopping_center_score
DROP VIEW IF EXISTS `shopping_center_score`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `shopping_center_score`;
CREATE VIEW `shopping_center_score` AS select `sc`.`shopping_center_id` AS `shopping_center_id`,`sc`.`shopping_center_casing_id` AS `shopping_center_casing_id`,`sc`.`shopping_center_survey_id` AS `shopping_center_survey_id`,((((((((((((((((((((((((((100 + `sc`.`inside_corner_or_mid_center`) + `sc`.`is_mall_or_free_standing`) + `sc`.`fm_no_left_in`) + `sc`.`fm_no_left_out`) + `sc`.`main_int_needs_light`) + `sc`.`sm_no_left_in`) + `sc`.`sm_no_left_out`) + `sc`.`nm_no_left_in`) + `sc`.`nm_no_left_out`) + `sc`.`trees_block_view`) + `sc`.`outparcels_block_view`) + `sc`.`hill_dep_blocks_view`) + `sc`.`store_not_facing_main`) + `sc`.`no_sign_on_main`) + `sc`.`excessive_landscaping_islands`) + `sc`.`excessive_stop_signs`) + `sc`.`center_has_parking_hog`) + `sc`.`outparcels_interfere`) + `sc`.`no_direct_access_to_parking`) + `sc`.`angled_spaces`) + `sc`.`t_spaces`) + `sc`.`one_way`) + `sc`.`poorly_lit`) + `sc`.`set_back`) + `sc`.`multiple_retailers_before`) + `sc`.`farthest_from_entrance`) AS `score` from `shopping_center_score_card` `sc`;

-- Dumping structure for view shopping_center_score_card
DROP VIEW IF EXISTS `shopping_center_score_card`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `shopping_center_score_card`;
CREATE VIEW `shopping_center_score_card` AS select `sc`.`shopping_center_id` AS `shopping_center_id`,`mrscc`.`shopping_center_casing_id` AS `shopping_center_casing_id`,`mrscs`.`shopping_center_survey_id` AS `shopping_center_survey_id`,`si`.`position_in_center` AS `position_in_center`,(case `si`.`position_in_center` when 'Inside Corner' then -(4) when 'Mid-Center' then -(1) else 0 end) AS `inside_corner_or_mid_center`,(case when (isnull(`mrscs`.`center_type`) or (`mrscs`.`center_type` in ('Free Standing','Free Standing - Alone','Free Standing - Center','FS','Regional Mall'))) then -(4) else 0 end) AS `is_mall_or_free_standing`,(case when (`fm_access`.`has_left_in` = 0) then -(5) else 0 end) AS `fm_no_left_in`,(case when (`fm_access`.`has_left_out` = 0) then -(5) else 0 end) AS `fm_no_left_out`,(case when ((not((`si`.`intersection_type` like 'Hard Corner'))) or ((`mrss`.`accessibility_main_intersection_needs_traffic_light` = 1) and (`mrss`.`accessibility_main_intersection_has_traffic_light` = 0))) then -(3) else 0 end) AS `main_int_needs_light`,(case when ((not((`si`.`intersection_type` like 'Hard Corner'))) or (`sm_access`.`has_left_in` = 0)) then -(2.5) else 0 end) AS `sm_no_left_in`,(case when ((not((`si`.`intersection_type` like 'Hard Corner'))) or (`sm_access`.`has_left_out` = 0)) then -(2.5) else 0 end) AS `sm_no_left_out`,(case when (`si`.`intersection_type` like 'Hard Corner') then 0 when (`si`.`intersection_type` like 'Mid%Block') then -(2.5) when ((`si`.`intersection_type` like 'Soft Corner') and (`nm_access`.`has_left_in` = 0)) then -(2.5) else 0 end) AS `nm_no_left_in`,(case when (`si`.`intersection_type` like 'Hard Corner') then 0 when (`si`.`intersection_type` like 'Mid%Block') then -(2.5) when ((`si`.`intersection_type` like 'Soft Corner') and (`nm_access`.`has_left_out` = 0)) then -(2.5) else 0 end) AS `nm_no_left_out`,(case when (`mrss`.`visibility_trees_block_view` = 1) then -(3) else 0 end) AS `trees_block_view`,(case when (`mrss`.`visibility_outparcels_block_view` = 1) then -(3) else 0 end) AS `outparcels_block_view`,(case when (`mrss`.`visibility_hill_depression_blocks_view` = 1) then -(4) else 0 end) AS `hill_dep_blocks_view`,(case when (`mrss`.`visibility_store_faces_main_road` = 0) then -(2) else 0 end) AS `store_not_facing_main`,(case when (`mrss`.`visibility_sign_on_main` = 0) then -(2) else 0 end) AS `no_sign_on_main`,(case when (`mrscs`.`flow_has_landscaping` = 1) then -(0.5) else 0 end) AS `excessive_landscaping_islands`,(case when ((`mrscs`.`flow_has_stop_signs` = 1) or (`mrscs`.`flow_has_speed_bumps` = 1)) then -(0.5) else 0 end) AS `excessive_stop_signs`,(case when (`mrscs`.`parking_has_parking_hog` = 1) then -(0.5) else 0 end) AS `center_has_parking_hog`,(case when `mrss`.`parking_outparcels_interfere_with_parking` then -(0.5) else 0 end) AS `outparcels_interfere`,(case when (`mrss`.`parking_direct_access_to_parking` = 0) then -(0.5) else 0 end) AS `no_direct_access_to_parking`,(case when (`mrscs`.`parking_has_angled_spaces` = 1) then -(0.5) else 0 end) AS `angled_spaces`,(case when (`mrss`.`parking_has_t_spaces` = 1) then -(0.5) else 0 end) AS `t_spaces`,(case when (`mrscs`.`flow_has_one_way_aisles` = 1) then -(0.5) else 0 end) AS `one_way`,(case when (`mrscs`.`parking_is_poorly_lit` = 1) then -(2) else 0 end) AS `poorly_lit`,(case when (`mrss`.`accessibility_set_back_twice_parking_length` = 1) then -(1) else 0 end) AS `set_back`,(case when (`mrss`.`accessibility_multiple_retailers_before_site` = 1) then -(1) else 0 end) AS `multiple_retailers_before`,(case when (`mrss`.`accessibility_farthest_from_entrance` = 1) then -(1) else 0 end) AS `farthest_from_entrance` from ((((((((`shopping_center` `sc` join `site` `si` on((`si`.`shopping_center_id` = `sc`.`shopping_center_id`))) join `store` `st` on((`st`.`site_id` = `si`.`site_id`))) left join `most_recent_shopping_center_casing` `mrscc` on((`sc`.`shopping_center_id` = `mrscc`.`shopping_center_casing_id`))) left join `most_recent_shopping_center_survey` `mrscs` on((`sc`.`shopping_center_id` = `mrscs`.`shopping_center_survey_id`))) left join `most_recent_store_survey` `mrss` on((`mrss`.`store_id` = `st`.`store_id`))) left join `shopping_center_access_front_main` `fm_access` on((`fm_access`.`shopping_center_survey_id` = `mrscs`.`shopping_center_survey_id`))) left join `shopping_center_access_side_main` `sm_access` on((`sm_access`.`shopping_center_survey_id` = `mrscs`.`shopping_center_survey_id`))) left join `shopping_center_access_non_main` `nm_access` on((`nm_access`.`shopping_center_survey_id` = `mrscs`.`shopping_center_survey_id`))) where (`st`.`store_type` <> 'HISTORICAL');

-- Dumping structure for view store_best_volume
DROP VIEW IF EXISTS `store_best_volume`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `store_best_volume`;
CREATE VIEW `store_best_volume` AS select `vd`.`store_id` AS `store_id`,`vd`.`decision` AS `decision`,(case `vd`.`decision` when 'ACTUAL' then `v`.`volume_actual` when 'ESTIMATE' then `v`.`volume_estimate` when 'PROJECTION' then `v`.`volume_projection` when 'REPORTED' then `v`.`volume_reported` when 'THIRD_PARTY' then `v`.`volume_third_party` else NULL end) AS `volume_total`,(case `vd`.`decision` when 'ACTUAL' then `v`.`volume_actual_date` when 'ESTIMATE' then `v`.`volume_estimate_date` when 'PROJECTION' then `v`.`volume_projection_date` when 'REPORTED' then `v`.`volume_reported_date` when 'THIRD_PARTY' then `v`.`volume_third_party_date` else NULL end) AS `volume_date` from (`volume_decision` `vd` left join `volume_decision_params` `v` on((`vd`.`store_id` = `v`.`store_id`)));

-- Dumping structure for view store_strength
DROP VIEW IF EXISTS `store_strength`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `store_strength`;
CREATE VIEW `store_strength` AS select `sb`.`store_id` AS `store_id`,round((((`sb`.`volume_total` / coalesce(`mrss`.`area_sales`,`b`.`default_sales_area`)) - 0.95) / 149.05),3) AS `strength` from (((`store` `st` join `store_best_volume` `sb` on((`st`.`store_id` = `sb`.`store_id`))) join `most_recent_store_survey` `mrss` on((`mrss`.`store_id` = `st`.`store_id`))) left join `banner` `b` on((`b`.`banner_id` = `st`.`banner_id`))) order by `sb`.`store_id`;

-- Dumping structure for view store_volume_actual
DROP VIEW IF EXISTS `store_volume_actual`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `store_volume_actual`;
CREATE VIEW `store_volume_actual` AS select `store_volume`.`store_volume_id` AS `store_volume_id`,`store_volume`.`store_id` AS `store_id`,`store_volume`.`volume_total` AS `volume_total`,`store_volume`.`volume_date` AS `volume_date`,`store_volume`.`volume_type` AS `volume_type`,`store_volume`.`source` AS `source`,`store_volume`.`created_date` AS `created_date`,`store_volume`.`created_by` AS `created_by`,`store_volume`.`updated_date` AS `updated_date`,`store_volume`.`updated_by` AS `updated_by`,`store_volume`.`deleted_date` AS `deleted_date`,`store_volume`.`deleted_by` AS `deleted_by`,`store_volume`.`version` AS `version`,`store_volume`.`legacy_casing_id` AS `legacy_casing_id` from `store_volume` where (`store_volume`.`volume_type` = 'ACTUAL');

-- Dumping structure for view store_volume_estimate
DROP VIEW IF EXISTS `store_volume_estimate`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `store_volume_estimate`;
CREATE VIEW `store_volume_estimate` AS select `store_volume`.`store_volume_id` AS `store_volume_id`,`store_volume`.`store_id` AS `store_id`,`store_volume`.`volume_total` AS `volume_total`,`store_volume`.`volume_date` AS `volume_date`,`store_volume`.`volume_type` AS `volume_type`,`store_volume`.`source` AS `source`,`store_volume`.`created_date` AS `created_date`,`store_volume`.`created_by` AS `created_by`,`store_volume`.`updated_date` AS `updated_date`,`store_volume`.`updated_by` AS `updated_by`,`store_volume`.`deleted_date` AS `deleted_date`,`store_volume`.`deleted_by` AS `deleted_by`,`store_volume`.`version` AS `version`,`store_volume`.`legacy_casing_id` AS `legacy_casing_id` from `store_volume` where (`store_volume`.`volume_type` = 'ESTIMATE');

-- Dumping structure for view store_volume_projection
DROP VIEW IF EXISTS `store_volume_projection`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `store_volume_projection`;
CREATE VIEW `store_volume_projection` AS select `store_volume`.`store_volume_id` AS `store_volume_id`,`store_volume`.`store_id` AS `store_id`,`store_volume`.`volume_total` AS `volume_total`,`store_volume`.`volume_date` AS `volume_date`,`store_volume`.`volume_type` AS `volume_type`,`store_volume`.`source` AS `source`,`store_volume`.`created_date` AS `created_date`,`store_volume`.`created_by` AS `created_by`,`store_volume`.`updated_date` AS `updated_date`,`store_volume`.`updated_by` AS `updated_by`,`store_volume`.`deleted_date` AS `deleted_date`,`store_volume`.`deleted_by` AS `deleted_by`,`store_volume`.`version` AS `version`,`store_volume`.`legacy_casing_id` AS `legacy_casing_id` from `store_volume` where (`store_volume`.`volume_type` = 'PROJECTION');

-- Dumping structure for view store_volume_reported
DROP VIEW IF EXISTS `store_volume_reported`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `store_volume_reported`;
CREATE VIEW `store_volume_reported` AS select `store_volume`.`store_volume_id` AS `store_volume_id`,`store_volume`.`store_id` AS `store_id`,`store_volume`.`volume_total` AS `volume_total`,`store_volume`.`volume_date` AS `volume_date`,`store_volume`.`volume_type` AS `volume_type`,`store_volume`.`source` AS `source`,`store_volume`.`created_date` AS `created_date`,`store_volume`.`created_by` AS `created_by`,`store_volume`.`updated_date` AS `updated_date`,`store_volume`.`updated_by` AS `updated_by`,`store_volume`.`deleted_date` AS `deleted_date`,`store_volume`.`deleted_by` AS `deleted_by`,`store_volume`.`version` AS `version`,`store_volume`.`legacy_casing_id` AS `legacy_casing_id` from `store_volume` where (`store_volume`.`volume_type` = 'REPORTED');

-- Dumping structure for view store_volume_third_party
DROP VIEW IF EXISTS `store_volume_third_party`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `store_volume_third_party`;
CREATE VIEW `store_volume_third_party` AS select `store_volume`.`store_volume_id` AS `store_volume_id`,`store_volume`.`store_id` AS `store_id`,`store_volume`.`volume_total` AS `volume_total`,`store_volume`.`volume_date` AS `volume_date`,`store_volume`.`volume_type` AS `volume_type`,`store_volume`.`source` AS `source`,`store_volume`.`created_date` AS `created_date`,`store_volume`.`created_by` AS `created_by`,`store_volume`.`updated_date` AS `updated_date`,`store_volume`.`updated_by` AS `updated_by`,`store_volume`.`deleted_date` AS `deleted_date`,`store_volume`.`deleted_by` AS `deleted_by`,`store_volume`.`version` AS `version`,`store_volume`.`legacy_casing_id` AS `legacy_casing_id` from `store_volume` where (`store_volume`.`volume_type` = 'THIRD_PARTY');

-- Dumping structure for view volume_decision
DROP VIEW IF EXISTS `volume_decision`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `volume_decision`;
CREATE VIEW `volume_decision` AS select `v`.`store_id` AS `store_id`,(case when (`v`.`has_collected_data` = 0) then 'THIRD_PARTY' when ((`v`.`has_collected_data` = 1) and (`v`.`has_casing` = 0) and (`v`.`has_actual` = 0)) then 'REPORTED' when ((`v`.`has_collected_data` = 1) and (`v`.`has_casing` = 0) and (`v`.`has_actual` = 1) and (`v`.`old_actual` = 0)) then 'ACTUAL' when ((`v`.`has_collected_data` = 1) and (`v`.`has_casing` = 1) and (`v`.`stale_model` = 0) and (`v`.`old_casing` = 0)) then 'ESTIMATE' when ((`v`.`has_collected_data` = 1) and (`v`.`has_casing` = 0) and (`v`.`has_actual` = 1) and (`v`.`old_actual` = 1) and (`v`.`has_reported` = 0)) then 'ACTUAL' when ((`v`.`has_collected_data` = 1) and (`v`.`has_casing` = 1) and (`v`.`stale_model` = 0) and (`v`.`old_casing` = 1) and (`v`.`has_actual` = 0) and (`v`.`has_projection` = 0)) then 'ESTIMATE' when ((`v`.`has_collected_data` = 1) and (`v`.`has_casing` = 1) and (`v`.`stale_model` = 0) and (`v`.`old_casing` = 1) and (`v`.`has_actual` = 0) and (`v`.`has_projection` = 1)) then 'PROJECTION' when ((`v`.`has_collected_data` = 1) and (`v`.`has_casing` = 1) and (`v`.`stale_model` = 1) and (`v`.`has_actual` = 0) and (`v`.`has_reported` = 0) and (`v`.`has_projection` = 0)) then 'ESTIMATE' when ((`v`.`has_collected_data` = 1) and (`v`.`has_casing` = 1) and (`v`.`stale_model` = 1) and (`v`.`has_actual` = 0) and (`v`.`has_reported` = 0) and (`v`.`has_projection` = 1)) then 'PROJECTION' when ((`v`.`has_collected_data` = 1) and (`v`.`has_casing` = 1) and (`v`.`stale_model` = 1) and (`v`.`has_actual` = 1) and (`v`.`old_actual` = 0)) then 'ACTUAL' when ((`v`.`has_collected_data` = 1) and (`v`.`has_casing` = 0) and (`v`.`has_actual` = 1) and (`v`.`old_actual` = 1) and (`v`.`has_reported` = 1) and (`v`.`reported_newer_than_actual` = 0)) then 'ACTUAL' when ((`v`.`has_collected_data` = 1) and (`v`.`has_casing` = 0) and (`v`.`has_actual` = 1) and (`v`.`old_actual` = 1) and (`v`.`has_reported` = 1) and (`v`.`reported_newer_than_actual` = 1)) then 'REPORTED' when ((`v`.`has_collected_data` = 1) and (`v`.`has_casing` = 1) and (`v`.`stale_model` = 0) and (`v`.`old_casing` = 1) and (`v`.`has_actual` = 1) and (`v`.`old_actual` = 0)) then 'ACTUAL' when ((`v`.`has_collected_data` = 1) and (`v`.`has_casing` = 1) and (`v`.`stale_model` = 0) and (`v`.`old_casing` = 1) and (`v`.`has_actual` = 1) and (`v`.`old_actual` = 1) and (`v`.`has_projection` = 0)) then 'ACTUAL' when ((`v`.`has_collected_data` = 1) and (`v`.`has_casing` = 1) and (`v`.`stale_model` = 0) and (`v`.`old_casing` = 1) and (`v`.`has_actual` = 1) and (`v`.`old_actual` = 1) and (`v`.`has_projection` = 1)) then 'PROJECTION' when ((`v`.`has_collected_data` = 1) and (`v`.`has_casing` = 1) and (`v`.`stale_model` = 1) and (`v`.`has_actual` = 0) and (`v`.`has_reported` = 1) and (`v`.`old_reported` = 1) and (`v`.`has_projection` = 0)) then 'REPORTED' when ((`v`.`has_collected_data` = 1) and (`v`.`has_casing` = 1) and (`v`.`stale_model` = 1) and (`v`.`has_actual` = 0) and (`v`.`has_reported` = 1) and (`v`.`old_reported` = 1) and (`v`.`has_projection` = 1)) then 'PROJECTION' when ((`v`.`has_collected_data` = 1) and (`v`.`has_casing` = 1) and (`v`.`stale_model` = 1) and (`v`.`has_actual` = 1) and (`v`.`old_actual` = 1) and (`v`.`has_reported` = 0) and (`v`.`has_projection` = 0)) then 'ACTUAL' when ((`v`.`has_collected_data` = 1) and (`v`.`has_casing` = 1) and (`v`.`stale_model` = 1) and (`v`.`has_actual` = 1) and (`v`.`old_actual` = 1) and (`v`.`has_reported` = 0) and (`v`.`has_projection` = 1)) then 'PROJECTION' when ((`v`.`has_collected_data` = 1) and (`v`.`has_casing` = 1) and (`v`.`stale_model` = 1) and (`v`.`has_actual` = 0) and (`v`.`has_reported` = 1) and (`v`.`old_reported` = 0) and (`v`.`untrusted_reported` = 0)) then 'REPORTED' when ((`v`.`has_collected_data` = 1) and (`v`.`has_casing` = 1) and (`v`.`stale_model` = 1) and (`v`.`has_actual` = 0) and (`v`.`has_reported` = 1) and (`v`.`old_reported` = 0) and (`v`.`untrusted_reported` = 1) and (`v`.`has_projection` = 0)) then 'REPORTED' when ((`v`.`has_collected_data` = 1) and (`v`.`has_casing` = 1) and (`v`.`stale_model` = 1) and (`v`.`has_actual` = 0) and (`v`.`has_reported` = 1) and (`v`.`old_reported` = 0) and (`v`.`untrusted_reported` = 1) and (`v`.`has_projection` = 1)) then 'PROJECTION' when ((`v`.`has_collected_data` = 1) and (`v`.`has_casing` = 1) and (`v`.`stale_model` = 1) and (`v`.`has_actual` = 1) and (`v`.`old_actual` = 1) and (`v`.`has_reported` = 1) and (`v`.`old_reported` = 1) and (`v`.`has_projection` = 0)) then 'REPORTED' when ((`v`.`has_collected_data` = 1) and (`v`.`has_casing` = 1) and (`v`.`stale_model` = 1) and (`v`.`has_actual` = 1) and (`v`.`old_actual` = 1) and (`v`.`has_reported` = 1) and (`v`.`old_reported` = 1) and (`v`.`has_projection` = 1)) then 'PROJECTION' when ((`v`.`has_collected_data` = 1) and (`v`.`has_casing` = 1) and (`v`.`stale_model` = 1) and (`v`.`has_actual` = 1) and (`v`.`old_actual` = 1) and (`v`.`has_reported` = 1) and (`v`.`old_reported` = 0) and (`v`.`untrusted_reported` = 0)) then 'REPORTED' when ((`v`.`has_collected_data` = 1) and (`v`.`has_casing` = 1) and (`v`.`stale_model` = 1) and (`v`.`has_actual` = 1) and (`v`.`old_actual` = 1) and (`v`.`has_reported` = 1) and (`v`.`old_reported` = 0) and (`v`.`untrusted_reported` = 1) and (`v`.`has_projection` = 0)) then 'REPORTED' when ((`v`.`has_collected_data` = 1) and (`v`.`has_casing` = 1) and (`v`.`stale_model` = 1) and (`v`.`has_actual` = 1) and (`v`.`old_actual` = 1) and (`v`.`has_reported` = 1) and (`v`.`old_reported` = 0) and (`v`.`untrusted_reported` = 1) and (`v`.`has_projection` = 1)) then 'PROJECTION' else 'UNKNOWN' end) AS `decision` from (`store` `st` left join `volume_decision_params` `v` on((`st`.`store_id` = `v`.`store_id`)));

-- Dumping structure for view volume_decision_params
DROP VIEW IF EXISTS `volume_decision_params`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `volume_decision_params`;
CREATE VIEW `volume_decision_params` AS select `st`.`store_id` AS `store_id`,ifnull(((`va`.`volume_total` is not null) or (`ve`.`volume_total` is not null) or (`vp`.`volume_total` is not null) or (`vr`.`volume_total` is not null)),0) AS `has_collected_data`,ifnull((`ve`.`volume_total` is not null),0) AS `has_casing`,ifnull(((to_days(curdate()) - to_days(`ve`.`volume_date`)) > 365),0) AS `old_casing`,ifnull(((to_days(curdate()) - to_days(`ve`.`volume_date`)) > 730),0) AS `stale_model`,ifnull((`va`.`volume_total` is not null),0) AS `has_actual`,ifnull(((to_days(curdate()) - to_days(`va`.`volume_date`)) > 365),0) AS `old_actual`,ifnull((`vr`.`volume_total` is not null),0) AS `has_reported`,ifnull(((to_days(curdate()) - to_days(`vr`.`volume_date`)) > 365),0) AS `old_reported`,ifnull(((to_days(`vr`.`volume_date`) - to_days(`va`.`volume_date`)) > 182),0) AS `reported_newer_than_actual`,ifnull(((abs((`vr`.`volume_total` - `vp`.`volume_total`)) / `vr`.`volume_total`) > 0.15),0) AS `untrusted_reported`,ifnull((`vp`.`volume_total` is not null),0) AS `has_projection`,`va`.`volume_total` AS `volume_actual`,`va`.`volume_date` AS `volume_actual_date`,`ve`.`volume_total` AS `volume_estimate`,`ve`.`volume_date` AS `volume_estimate_date`,`vp`.`volume_total` AS `volume_projection`,`vp`.`volume_date` AS `volume_projection_date`,`vr`.`volume_total` AS `volume_reported`,`vr`.`volume_date` AS `volume_reported_date`,`vt`.`volume_total` AS `volume_third_party`,`vt`.`volume_date` AS `volume_third_party_date` from ((((((`store` `st` left join `banner` `b` on((`b`.`banner_id` = `st`.`banner_id`))) left join `most_recent_store_volume_actual` `va` on((`st`.`store_id` = `va`.`store_id`))) left join `most_recent_store_volume_estimate` `ve` on((`st`.`store_id` = `ve`.`store_id`))) left join `most_recent_store_volume_projection` `vp` on((`st`.`store_id` = `vp`.`store_id`))) left join `most_recent_store_volume_reported` `vr` on((`st`.`store_id` = `vr`.`store_id`))) left join `most_recent_store_volume_third_party` `vt` on((`st`.`store_id` = `vt`.`store_id`))) order by `st`.`store_id`;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
