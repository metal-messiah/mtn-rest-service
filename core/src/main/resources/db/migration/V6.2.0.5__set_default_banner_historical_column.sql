ALTER TABLE `banner`
	CHANGE COLUMN `is_historical` `is_historical` TINYINT
(4) NOT NULL DEFAULT '0' AFTER `company_id`;
