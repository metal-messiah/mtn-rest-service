CREATE TABLE IF NOT EXISTS `extraction_field` (
  `extraction_field_id` int(11) NOT NULL AUTO_INCREMENT,
  `table` varchar(50) DEFAULT NULL,
  `display_name` varchar(255) NOT NULL,
  `field_mapping` varchar(255) NOT NULL,
  `header` varchar(64) NOT NULL,
  `extraction_data_type` varchar(50) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` int(11) DEFAULT NULL,
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_by` int(11) DEFAULT NULL,
  `deleted_date` timestamp NULL DEFAULT NULL,
  `deleted_by` int(11) DEFAULT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`extraction_field_id`)
) ENGINE=InnoDB AUTO_INCREMENT=177 DEFAULT CHARSET=utf8;

INSERT INTO `extraction_field` (`extraction_field_id`, `table`, `display_name`, `field_mapping`, `header`, `extraction_data_type`, `created_date`, `created_by`, `updated_date`, `updated_by`, `deleted_date`, `deleted_by`, `version`) VALUES
	(1, 'Project', 'Projects', 'projects', 'projects', 'PROJECT_LIST', '2018-07-09 14:44:10', 7, '2018-07-09 14:44:10', 7, NULL, NULL, 1),
	(2, 'StoreCasing', 'Casing ID', 'id', 'casing_id', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(3, 'StoreCasing', 'Casing Date', 'casingDate', 'casing_date', 'DATE_TIME', '2018-07-09 14:44:10', 7, '2018-07-09 15:29:46', 7, NULL, NULL, 1),
	(4, 'StoreCasing', 'Casing Notes', 'note', 'notes', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(5, 'StoreCasing', 'Condition: Ceiling', 'conditionCeiling', 'condition_ceiling', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 15:38:40', 7, NULL, NULL, 1),
	(6, 'StoreCasing', 'Condition: Checkstands', 'conditionCheckstands', 'condition_checkstands', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 15:38:46', 7, NULL, NULL, 1),
	(7, 'StoreCasing', 'Condition: Floors', 'conditionFloors', 'condition_floors', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 15:38:51', 7, NULL, NULL, 1),
	(8, 'StoreCasing', 'Condition: Frozen/Refrigerated', 'conditionFrozenRefrigerated', 'condition_frozen_refrigerated', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 15:39:02', 7, NULL, NULL, 1),
	(9, 'StoreCasing', 'Condition: Shelving/Gondolas', 'conditionShelvingGondolas', 'condition_shelving_gondolas', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 15:39:13', 7, NULL, NULL, 1),
	(10, 'StoreCasing', 'Condition: Walls', 'conditionWalls', 'condition_walls', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 15:39:29', 7, NULL, NULL, 1),
	(11, 'StoreCasing', 'Fuel - Num Gallons Weekly', 'fuelGallonsWeekly', 'fuel_num_gallons_weekly', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(12, 'StoreCasing', 'Rx - Num Scripts Weekly', 'pharmacyScriptsWeekly', 'rx_num_scripts_weekly', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(13, 'StoreCasing', 'Rx - Avg Script Price', 'pharmacyAvgDollarsPerScript', 'rx_avg_script_price', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(14, 'StoreCasing', 'Rx - Num Pharmacists', 'pharmacyPharmacistCount', 'rx_num_pharmacists', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(15, 'StoreCasing', 'Rx - Num Technicians', 'pharmacyTechnicianCount', 'rx_num_technicians', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(16, 'StoreCasing', 'Legacy Casing ID', 'legacyCasingId', 'legacy_casing_id', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(17, 'StoreStatus', 'Store Status', 'storeStatus.status', 'store_status', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(18, 'StoreStatus', 'Status Start Date', 'storeStatus.statusStartDate', 'status_start_date', 'DATE_TIME', '2018-07-09 14:44:10', 7, '2018-07-09 15:29:47', 7, NULL, NULL, 1),
	(19, 'StoreVolume', 'Volume ID', 'storeVolume.id', 'volume_id', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(20, 'StoreVolume', 'Volume (Total)', 'storeVolume.volumeTotal', 'volume_total', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(21, 'StoreVolume', 'Volume Date', 'storeVolume.volumeDate', 'volume_date', 'DATE', '2018-07-09 14:44:10', 7, '2018-07-09 14:44:10', 7, NULL, NULL, 1),
	(22, 'StoreVolume', 'Volume Type', 'storeVolume.volumeType', 'volume_type', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(23, 'StoreVolume', 'Volume Source', 'storeVolume.source', 'volume_source', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(24, 'StoreVolume', 'Volume: Grocery', 'storeVolume.volumeGrocery', 'volume_grocery', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(25, 'StoreVolume', 'Volume: % Grocery', 'storeVolume.volumePercentGrocery', 'volume_percent_grocery', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 15:24:52', 7, NULL, NULL, 1),
	(26, 'StoreVolume', 'Volume: Meat', 'storeVolume.volumeMeat', 'volume_meat', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(27, 'StoreVolume', 'Volume: % Meat', 'storeVolume.volumePercentMeat', 'volume_percent_meat', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(28, 'StoreVolume', 'Volume: Non-food', 'storeVolume.volumeNonFood', 'volume_non_food', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(29, 'StoreVolume', 'Volume: % Non-food', 'storeVolume.volumePercentNonFood', 'volume_percent_non_food', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(30, 'StoreVolume', 'Volume: Other', 'storeVolume.volumeOther', 'volume_other', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(31, 'StoreVolume', 'Volume: % Other', 'storeVolume.volumePercentOther', 'volume_percent_other', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(32, 'StoreVolume', 'Volume: Produce', 'storeVolume.volumeProduce', 'volume_produce', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(33, 'StoreVolume', 'Volume: % Produce', 'storeVolume.volumePercentProduce', 'volume_percent_produce', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(34, 'StoreVolume', 'Volume +/-', 'storeVolume.volumePlusMinus', 'volume_plus_minus', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(35, 'StoreVolume', 'Volume Notes', 'storeVolume.volumeNote', 'volume_note', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(36, 'StoreVolume', 'Volume Confidence', 'storeVolume.volumeConfidence', 'volume_confidence', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(37, 'Store', 'Store ID', 'store.id', 'store_id', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(38, 'Store', 'Store Name', 'store.storeName', 'store_name', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(39, 'Store', 'Store Number', 'store.storeNumber', 'store_number', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(40, 'Store', 'Store Type', 'store.storeType', 'store_type', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(41, 'Store', 'Store Opening Date', 'store.dateOpened', 'store_opening_date', 'DATE_TIME', '2018-07-09 14:44:10', 7, '2018-07-09 15:29:48', 7, NULL, NULL, 1),
	(42, 'Store', 'Store Closing Date', 'store.dateClosed', 'store_closing_date', 'DATE_TIME', '2018-07-09 14:44:10', 7, '2018-07-09 15:29:49', 7, NULL, NULL, 1),
	(43, 'Store', 'Store is Float', 'store.floating', 'store_is_float', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(44, 'Store', 'Legacy Location ID', 'store.legacyLocationId', 'legacy_location_id', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(45, 'Banner', 'Banner ID', 'store.banner.id', 'banner_id', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(46, 'Banner', 'Banner Name', 'store.banner.bannerName', 'banner_name', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 15:25:39', 7, NULL, NULL, 1),
	(47, 'Banner', 'Banner Logo Filename', 'store.banner.logoFileName', 'banner_file_name', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 15:25:59', 7, NULL, NULL, 1),
	(48, 'Company', 'Company', 'store.banner.company.companyName', 'company_name', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(49, 'Company', 'Parent Company', 'store.banner.company.parentCompany.companyName', 'parent_company_name', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(50, 'Site', 'Site ID', 'store.site.id', 'site_id', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(51, 'Site', 'Latitude', 'store.site.latitude', 'latitude', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(52, 'Site', 'Longitude', 'store.site.longitude', 'longitude', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(53, 'Site', 'Site Type', 'store.site.type', 'site_type', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(54, 'Site', 'Site Footprint Sqft', 'store.site.footprintSqft', 'site_footprint_sqft', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(55, 'Site', 'Site Position in SC', 'store.site.positionInCenter', 'site_position_in_center', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(56, 'Site', 'Address', 'store.site.address1', 'address', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(57, 'Site', 'Address Line 2', 'store.site.address2', 'address_line_2', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(58, 'Site', 'City', 'store.site.city', 'city', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(59, 'Site', 'County', 'store.site.county', 'county', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(60, 'Site', 'State / Province', 'store.site.state', 'state_province', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(61, 'Site', 'Zip / Postal Code', 'store.site.postalCode', 'zip_postal_code', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(62, 'Site', 'Country', 'store.site.country', 'country', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(63, 'Site', 'Intersection Type', 'store.site.intersectionType', 'intersection_type', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(64, 'Site', 'Quad', 'store.site.quad', 'quad', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(65, 'Site', 'Intersection Street (Primary)', 'store.site.intersectionStreetPrimary', 'intersection_street_primary', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(66, 'Site', 'Intersection Street (Secondary)', 'store.site.intersectionStreetSecondary', 'intersection_street_secondary', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(67, 'Site', 'Intersection (Formatted)', 'store.site', 'intersection', 'INTERSECTION', '2018-07-09 14:44:10', 7, '2018-07-09 14:44:10', 7, NULL, NULL, 1),
	(68, 'Site', 'Site is duplicate', 'store.site.duplicate', 'site_is_duplicate', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(69, 'Site', 'Assigned To', 'store.site.assignee.email', 'assigned_to', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(70, 'ShoppingCenter', 'Shopping Center ID', 'store.site.shoppingCenter.id', 'shopping_center_id', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(71, 'ShoppingCenter', 'Shopping Center Name', 'store.site.shoppingCenter.name', 'shopping_center_name', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(72, 'ShoppingCenter', 'Shopping Center Owner', 'store.site.shoppingCenter.owner', 'shopping_center_owner', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 15:26:31', 7, NULL, NULL, 1),
	(73, 'StoreSurvey', 'Store Survey ID', 'storeSurvey.id', 'store_survey_id', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(74, 'StoreSurvey', 'Store Fit', 'storeSurvey.fit', 'store_fit', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(75, 'StoreSurvey', 'Store Format', 'storeSurvey.format', 'store_format', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(76, 'StoreSurvey', 'Sales Area', 'storeSurvey.areaSales', 'sales_area', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(77, 'StoreSurvey', 'Salre Area % of Total Area', 'storeSurvey.areaSalesPercentOfTotal', 'sales_area_percent_of_total', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(78, 'StoreSurvey', 'Total Area', 'storeSurvey.areaTotal', 'total_area', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 15:43:30', 7, NULL, NULL, 1),
	(79, 'StoreSurvey', 'Area is Estimate', 'storeSurvey.areaIsEstimate', 'area_is_estimate', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(80, 'StoreSurvey', 'Store is Open 24hr', 'storeSurvey.storeIsOpen24', 'store_is_open_24hr', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(81, 'StoreSurvey', 'Natural Foods are Integrated', 'storeSurvey.naturalFoodsAreIntegrated', 'natural_foods_are_integrated', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(82, 'StoreSurvey', 'Registers: Normal', 'storeSurvey.registerCountNormal', 'registers_normal', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(83, 'StoreSurvey', 'Registers: Express', 'storeSurvey.registerCountExpress', 'registers_express', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(84, 'StoreSurvey', 'Registers: Self Checkout', 'storeSurvey.registerCountSelfCheckout', 'registers_self_checkout', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(85, 'StoreSurvey', 'Number of Fuel Dispensers', 'storeSurvey.fuelDispenserCount', 'number_of_fuel_dispensers', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(86, 'StoreSurvey', 'Fuel is Open 24hr', 'storeSurvey.fuelIsOpen24', 'fuel_is_open_24hr', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(87, 'StoreSurvey', 'Pharmacy is Open 24hr', 'storeSurvey.pharmacyIsOpen24', 'pharmacy_is_open_24hr', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(88, 'StoreSurvey', 'Pharmacy has drive through', 'storeSurvey.pharmacyHasDriveThrough', 'pharmacy_has_drive_through', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(89, 'StoreSurvey', 'Departments: Bakery', 'storeSurvey.departmentBakery', 'depts_bakery', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(90, 'StoreSurvey', 'Departments: Bank', 'storeSurvey.departmentBank', 'depts_bank', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(91, 'StoreSurvey', 'Departments: Beer', 'storeSurvey.departmentBeer', 'depts_beer', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(92, 'StoreSurvey', 'Departments: Bulk', 'storeSurvey.departmentBulk', 'depts_bulk', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(93, 'StoreSurvey', 'Departments: Cheese', 'storeSurvey.departmentCheese', 'depts_cheese', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(94, 'StoreSurvey', 'Departments: Coffee', 'storeSurvey.departmentCoffee', 'depts_coffee', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(95, 'StoreSurvey', 'Departments: Deli', 'storeSurvey.departmentDeli', 'depts_deli', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(96, 'StoreSurvey', 'Departments: Expanded General Merchandise', 'storeSurvey.departmentExpandedGm', 'depts_exp_gm', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(97, 'StoreSurvey', 'Departments: Extensive Prepared Foods', 'storeSurvey.departmentExtensivePreparedFoods', 'depts_ext_prepared_foods', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(98, 'StoreSurvey', 'Departments: Floral', 'storeSurvey.departmentFloral', 'depts_floral', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(99, 'StoreSurvey', 'Departments: Fuel', 'storeSurvey.departmentFuel', 'depts_fuel', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(100, 'StoreSurvey', 'Departments: Hotbar', 'storeSurvey.departmentHotBar', 'depts_hotbar', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(101, 'StoreSurvey', 'Departments: In Store Restaurant', 'storeSurvey.departmentInStoreRestaurant', 'depts_in_store_restaurant', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(102, 'StoreSurvey', 'Departments: Liquor', 'storeSurvey.departmentLiquor', 'depts_liquor', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(103, 'StoreSurvey', 'Departments: Meat', 'storeSurvey.departmentMeat', 'depts_meat', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(104, 'StoreSurvey', 'Departments: Natural/Organic Foods', 'storeSurvey.departmentNatural', 'depts_natural_organic', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(105, 'StoreSurvey', 'Departments: Olive Bar', 'storeSurvey.departmentOliveBar', 'depts_olive_bar', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(106, 'StoreSurvey', 'Departments: Online Pickup', 'storeSurvey.departmentOnlinePickup', 'depts_online_pickup', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(107, 'StoreSurvey', 'Departments: Pharmacy', 'storeSurvey.departmentPharmacy', 'depts_pharmacy', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(108, 'StoreSurvey', 'Departments: Prepared Foods', 'storeSurvey.departmentPreparedFoods', 'depts_prepared_foods', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(109, 'StoreSurvey', 'Departments: Salad Bar', 'storeSurvey.departmentSaladBar', 'depts_salad_bar', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(110, 'StoreSurvey', 'Departments: Seafood', 'storeSurvey.departmentSeafood', 'depts_seafood', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(111, 'StoreSurvey', 'Departments: Seating', 'storeSurvey.departmentSeating', 'depts_seating', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(112, 'StoreSurvey', 'Departments: Sushi', 'storeSurvey.departmentSushi', 'depts_sushi', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(113, 'StoreSurvey', 'Departments: Wine', 'storeSurvey.departmentWine', 'depts_wine', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(114, 'StoreSurvey', 'Accessibility: Farthest From SC Entrance', 'storeSurvey.accessibilityFarthestFromEntrance', 'accessibility_farthest_from_sc_entrance', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(115, 'StoreSurvey', 'Accessibility: Main Intersection Has Traffic Light', 'storeSurvey.accessibilityMainIntersectionHasTrafficLight', 'accessibility_main_intersection_has_traffic_light', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(116, 'StoreSurvey', 'Accessibility: Main Intersection Needs Traffic Light', 'storeSurvey.accessibilityMainIntersectionNeedsTrafficLight', 'accessibility_main_intersection_needs_traffic_light', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(117, 'StoreSurvey', 'Accessibility: Multiple Retailers Before Site', 'storeSurvey.accessibilityMultipleRetailersBeforeSite', 'accessibility_multiple_retailers_before_site', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(118, 'StoreSurvey', 'Accessibility: Set Back Twice Parking Length', 'storeSurvey.accessibilitySetBackTwiceParkingLength', 'accessibility_set_back_twice_parking_length', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(119, 'StoreSurvey', 'Accessibility: Rating', 'storeSurvey.accessibilityRating', 'accessibility_rating', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(120, 'StoreSurvey', 'Parking: Outparcels Interfere With Parking', 'storeSurvey.parkingOutparcelsInterfereWithParking', 'parking_outparcels_interfere_with_parking', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(121, 'StoreSurvey', 'Parking: Direct Access To Parking', 'storeSurvey.parkingDirectAccessToParking', 'parking_direct_access_to_parking', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(122, 'StoreSurvey', 'Parking: Small Parking Field', 'storeSurvey.parkingSmallParkingField', 'parking_small_parking_field', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(123, 'StoreSurvey', 'Parking: Has T-spaces', 'storeSurvey.parkingHasTSpaces', 'parking_has_t_spaces', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(124, 'StoreSurvey', 'Parking: Rating', 'storeSurvey.parkingRating', 'parking_rating', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(125, 'StoreSurvey', 'Visibility: Hill/Depression Blocks View', 'storeSurvey.visibilityHillDepressionBlocksView', 'visibility_hill_depression_blocks_view', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(126, 'StoreSurvey', 'Visibility: Outparcels Block SC View', 'storeSurvey.visibilityOutparcelsBlockView', 'visibility_outparcels_block_sc_view', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(127, 'StoreSurvey', 'Visibility: Sign On Main', 'storeSurvey.visibilitySignOnMain', 'visibility_sign_on_main', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(128, 'StoreSurvey', 'Visibility: Store Faces Main Road', 'storeSurvey.visibilityStoreFacesMainRoad', 'visibility_store_faces_main_road', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(129, 'StoreSurvey', 'Visibility: Trees Block View', 'storeSurvey.visibilityTreesBlockView', 'visibility_trees_block_view', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(130, 'StoreSurvey', 'Visibility: Rating', 'storeSurvey.visibilityRating', 'visibility_rating', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(131, 'StoreSurvey', 'Seasonality: Jan', 'storeSurvey.seasonalityJan', 'seasonality_jan', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(132, 'StoreSurvey', 'Seasonality: Feb', 'storeSurvey.seasonalityFeb', 'seasonality_feb', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(133, 'StoreSurvey', 'Seasonality: Mar', 'storeSurvey.seasonalityMar', 'seasonality_mar', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(134, 'StoreSurvey', 'Seasonality: Apr', 'storeSurvey.seasonalityApr', 'seasonality_apr', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(135, 'StoreSurvey', 'Seasonality: May', 'storeSurvey.seasonalityMay', 'seasonality_may', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(136, 'StoreSurvey', 'Seasonality: Jun', 'storeSurvey.seasonalityJun', 'seasonality_jun', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(137, 'StoreSurvey', 'Seasonality: Jul', 'storeSurvey.seasonalityJul', 'seasonality_jul', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(138, 'StoreSurvey', 'Seasonality: Aug', 'storeSurvey.seasonalityAug', 'seasonality_aug', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(139, 'StoreSurvey', 'Seasonality: Sep', 'storeSurvey.seasonalitySep', 'seasonality_sep', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(140, 'StoreSurvey', 'Seasonality: Oct', 'storeSurvey.seasonalityOct', 'seasonality_oct', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(141, 'StoreSurvey', 'Seasonality: Nov', 'storeSurvey.seasonalityNov', 'seasonality_nov', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(142, 'StoreSurvey', 'Seasonality: Dec', 'storeSurvey.seasonalityDec', 'seasonality_dec', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(143, 'StoreSurvey', 'Seasonality: Notes', 'storeSurvey.seasonalityNotes', 'seasonality_notes', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(144, 'ShoppingCenterCasing', 'Shopping Center Casing ID', 'shoppingCenterCasing.id', 'shopping_center_casing_id', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(145, 'ShoppingCenterCasing', 'Shopping Center Notes', 'shoppingCenterCasing.note', 'shopping_center_notes', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(146, 'ShoppingCenterCasing', 'Rating: Parking Lot', 'shoppingCenterCasing.ratingParkingLot', 'rating_parking_lot', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(147, 'ShoppingCenterCasing', 'Rating: Building Conditions', 'shoppingCenterCasing.ratingBuildings', 'rating_building_conditions', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(148, 'ShoppingCenterCasing', 'Rating: Parking Lot Lighting', 'shoppingCenterCasing.ratingLighting', 'rating_parking_lot_lighting', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(149, 'ShoppingCenterCasing', 'Rating: SC Synergy', 'shoppingCenterCasing.ratingSynergy', 'rating_sc_synergy', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(150, 'ShoppingCenterSurvey', 'Shopping Center Survey ID', 'shoppingCenterCasing.shoppingCenterSurvey.id', 'shopping_center_survey_id', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 15:27:03', 7, NULL, NULL, 1),
	(151, 'ShoppingCenterSurvey', 'Shopping Center Type', 'shoppingCenterCasing.ShoppingCenterSurvey.centerType', 'shopping_center_type', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(152, 'ShoppingCenterSurvey', 'Flow: Has Landscaping', 'shoppingCenterCasing.ShoppingCenterSurvey.flowHasLandscaping', 'flow_has_landscaping', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(153, 'ShoppingCenterSurvey', 'Flow: Has Speed Bumps', 'shoppingCenterCasing.ShoppingCenterSurvey.flowHasSpeedBumps', 'flow_has_speed_bumps', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(154, 'ShoppingCenterSurvey', 'Flow: Has Stop Signs', 'shoppingCenterCasing.ShoppingCenterSurvey.flowHasStopSigns', 'flow_has_stop_signs', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(155, 'ShoppingCenterSurvey', 'Flow: Has One Way Aisles', 'shoppingCenterCasing.ShoppingCenterSurvey.flowHasOneWayAisles', 'flow_has_one_way_aisles', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(156, 'ShoppingCenterSurvey', 'Parking: Has Angled Spaces', 'shoppingCenterCasing.ShoppingCenterSurvey.parkingHasAngledSpaces', 'parking_has_angled_spaces', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(157, 'ShoppingCenterSurvey', 'Parking: Has Parking Hog', 'shoppingCenterCasing.ShoppingCenterSurvey.parkingHasParkingHog', 'parking_has_parking_hog', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(158, 'ShoppingCenterSurvey', 'Parking: Is Poorly Lit', 'shoppingCenterCasing.ShoppingCenterSurvey.parkingIsPoorlyLit', 'parking_is_poorly_lit', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(159, 'ShoppingCenterSurvey', 'Parking: Store Parking Space Count', 'shoppingCenterCasing.ShoppingCenterSurvey.parkingSpaceCount', 'parking_store_parking_spaces', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(160, 'ShoppingCenterSurvey', 'Tenants: # Occupied', 'shoppingCenterCasing.ShoppingCenterSurvey.tenantOccupiedCount', 'occupied_tenants', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(161, 'ShoppingCenterSurvey', 'Tenants: # Vacant', 'shoppingCenterCasing.ShoppingCenterSurvey.tenantVacantCount', 'vacant_tenants', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(162, 'ShoppingCenterSurvey', 'SC SqFt % Occupied', 'shoppingCenterCasing.ShoppingCenterSurvey.sqFtPercentOccupied', 'sc_sqft_percent_occupied', NULL, '2018-07-09 14:44:10', 7, '2018-07-09 14:44:57', 7, NULL, NULL, 1),
	(163, 'ShoppingCenterAccess', 'Accesses: Total', 'shoppingCenterCasing.ShoppingCenterSurvey.accesses', 'accesses_total', 'ACCESS_COUNT', '2018-07-09 14:44:10', 7, '2018-07-09 14:57:31', 7, NULL, NULL, 1),
	(164, 'ShoppingCenterAccess', 'Accesses: Front Main', 'shoppingCenterCasing.ShoppingCenterSurvey.accesses', 'accesses_front_main', 'ACCESS_FRONT_MAIN_COUNT', '2018-07-09 14:44:10', 7, '2018-07-09 14:57:30', 7, NULL, NULL, 1),
	(165, 'ShoppingCenterAccess', 'Accesses: Side Main', 'shoppingCenterCasing.ShoppingCenterSurvey.accesses', 'accesses_side_main', 'ACCESS_SIDE_MAIN_COUNT', '2018-07-09 14:44:10', 7, '2018-07-09 14:57:30', 7, NULL, NULL, 1),
	(166, 'ShoppingCenterAccess', 'Accesses: Non Main', 'shoppingCenterCasing.ShoppingCenterSurvey.accesses', 'accesses_non_main', 'ACCESS_NON_MAIN_COUNT', '2018-07-09 14:44:10', 7, '2018-07-09 14:57:30', 7, NULL, NULL, 1),
	(167, 'ShoppingCenterTenant', 'Tenants: Total', 'shoppingCenterCasing.ShoppingCenterSurvey.tenants', 'tenants_total', 'TENANT_COUNT', '2018-07-09 14:44:10', 7, '2018-07-09 14:57:23', 7, NULL, NULL, 1),
	(168, 'ShoppingCenterTenant', 'Tenants: Inline Count', 'shoppingCenterCasing.ShoppingCenterSurvey.tenants', 'tenants_inline_count', 'TENANT_INLINE_COUNT', '2018-07-09 14:44:10', 7, '2018-07-09 14:57:22', 7, NULL, NULL, 1),
	(169, 'ShoppingCenterTenant', 'Tenants: Inline List', 'shoppingCenterCasing.ShoppingCenterSurvey.tenants', 'tenants_inline_list', 'TENANT_INLINE_LIST', '2018-07-09 14:44:10', 7, '2018-07-09 14:57:21', 7, NULL, NULL, 1),
	(170, 'ShoppingCenterTenant', 'Tenants: Outparcel Count', 'shoppingCenterCasing.ShoppingCenterSurvey.tenants', 'tenants_outparcel_count', 'TENANT_OUTPARCEL_COUNT', '2018-07-09 14:44:10', 7, '2018-07-09 14:57:21', 7, NULL, NULL, 1),
	(171, 'ShoppingCenterTenant', 'Tenants: Outparcel List', 'shoppingCenterCasing.ShoppingCenterSurvey.tenants', 'tenants_outparcel_list', 'TENANT_OUTPARCEL_LIST', '2018-07-09 14:44:10', 7, '2018-07-09 14:57:13', 7, NULL, NULL, 1),
	(172, 'Placeholder', 'Map Key (Placeholder)', 'id', 'map_key', 'BLANK', '2018-07-09 14:44:10', 7, '2018-07-09 14:44:10', 7, NULL, NULL, 1),
	(173, 'Placeholder', 'Curve (Placeholder)', 'id', 'curve', 'BLANK', '2018-07-09 14:44:10', 7, '2018-07-09 14:44:10', 7, NULL, NULL, 1),
	(174, 'Placeholder', 'PWTA (Placeholder)', 'id', 'pwta', 'BLANK', '2018-07-09 14:44:10', 7, '2018-07-09 14:44:10', 7, NULL, NULL, 1),
	(175, 'Placeholder', 'Power (Placeholder)', 'id', 'power', 'BLANK', '2018-07-09 14:44:10', 7, '2018-07-09 14:44:10', 7, NULL, NULL, 1),
	(176, 'Placeholder', 'Radius (Placeholder)', 'id', 'radius', 'BLANK', '2018-07-09 14:44:10', 7, '2018-07-09 14:44:10', 7, NULL, NULL, 1);

