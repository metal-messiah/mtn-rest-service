CREATE TABLE `resource_quota` (
	`resource_quota_id` INT NOT NULL,
	`resource_name` VARCHAR(128) NOT NULL,
	`period_start_date` TIMESTAMP NOT NULL,
	`query_count` INT NOT NULL,
	PRIMARY KEY (`resource_quota_id`)
)
COLLATE='utf8_general_ci'
;
