ALTER TABLE resource_quota
	ADD COLUMN quota_limit INT NOT NULL DEFAULT '0';
