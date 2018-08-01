-- Correct column types
ALTER TABLE `site` CHANGE COLUMN `location` `location` POINT NULL AFTER `cbsa_id`;	
ALTER TABLE `boundary` CHANGE COLUMN `boundary` `boundary` GEOMETRY NULL AFTER `boundary_id`;
	
-- Update site locations with SRID 4326 and in case any have been moved
update site si
set si.location = ST_PointFromText(concat('Point(', si.longitude, ' ', si.latitude, ')'), 4326)
where si.latitude is not null;

-- Update boundaris with SRID 4326
update boundary b 
set b.boundary = ST_GeomFromGeoJson(b.geojson, 1, 4326)
where b.geojson is not null;

-- Make columns not null to enable spatial indexes
ALTER TABLE `site` CHANGE COLUMN `location` `location` POINT NOT NULL AFTER `cbsa_id`;	
ALTER TABLE `boundary` CHANGE COLUMN `boundary` `boundary` GEOMETRY NOT NULL AFTER `boundary_id`;

-- Add Spatial Indexes
ALTER TABLE `site` ADD SPATIAL INDEX `Index 6` (`location`);
ALTER TABLE `boundary` ADD SPATIAL INDEX `Index 2` (`boundary`);
	
-- Create triggers before insert and before update
delimiter //

CREATE TRIGGER `site_before_insert` BEFORE INSERT ON `site` FOR EACH ROW BEGIN
	set NEW.location = ST_PointFromText(concat('Point(', NEW.longitude, ' ', NEW.latitude, ')'), 4326);
END //

CREATE TRIGGER `site_before_update` BEFORE UPDATE ON `site` FOR EACH ROW BEGIN
	set NEW.location = ST_PointFromText(concat('Point(', NEW.longitude, ' ', NEW.latitude, ')'), 4326);
END //

CREATE TRIGGER `boundary_before_insert` BEFORE INSERT ON `boundary` FOR EACH ROW BEGIN
	SET NEW.boundary = ST_GeomFromGeoJson(NEW.geojson, 1, 4326);
END //

CREATE TRIGGER `boundary_before_update` BEFORE UPDATE ON `boundary` FOR EACH ROW BEGIN
	SET NEW.boundary = ST_GeomFromGeoJson(NEW.geojson, 1, 4326);
END //

delimiter ;
