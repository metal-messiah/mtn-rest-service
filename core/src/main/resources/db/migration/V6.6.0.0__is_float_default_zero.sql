update store st
SET st.is_float = 0
WHERE st.is_float IS NULL;

ALTER TABLE `store`
	CHANGE COLUMN `is_float` `is_float` TINYINT(4) NOT NULL DEFAULT '0' AFTER `current_store_survey_id`;
