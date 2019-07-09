CREATE TABLE merged_store_reference (
	deleted_store_id INT(11) NOT NULL,
	remaining_store_id INT(11) NULL DEFAULT NULL
);

INSERT INTO merged_store_reference
SELECT store_id AS deleted_store_id, NULL AS remaining_store_id
FROM store st 
WHERE st.deleted_date IS NOT NULL;

DROP TABLE if EXISTS merged_stores_with_one_option;
CREATE TEMPORARY TABLE merged_stores_with_one_option AS 
SELECT m.deleted_store_id
	FROM merged_store_reference m
	JOIN store st ON st.store_id = m.deleted_store_id
	JOIN store st2 ON st2.site_id = st.site_id AND st2.deleted_date IS NULL
	GROUP BY m.deleted_store_id
	HAVING COUNT(*) = 1;


UPDATE merged_store_reference m 
JOIN store st ON m.deleted_store_id = st.store_id
JOIN store st2 ON st2.site_id = st.site_id AND st2.deleted_date IS NULL
SET m.remaining_store_id = st2.store_id
WHERE m.deleted_store_id IN (
	SELECT deleted_store_id	FROM merged_stores_with_one_option
);

DROP TABLE if EXISTS merged_stores_with_one_option;