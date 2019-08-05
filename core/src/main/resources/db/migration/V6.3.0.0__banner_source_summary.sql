CREATE OR REPLACE VIEW banner_source_summary as
SELECT 
	bs.source_id,
	bs.source_name, 
	bs.source_banner_name, 
	total_store_sources, 
	coalesce(matched_store_sources, 0) AS matched_store_sources,
	round(coalesce(matched_store_sources, 0) / total_store_sources, 2) as percent_complete,
	if(bs.source_deleted_date IS NOT null, 'DELETED', if(matched_store_sources = total_store_sources, 'COMPLETE', 'INCOMPLETE')) AS matching_status,
	bs.banner_id,
	b.banner_name,
	b.logo_file_name,
	bs.validated_date,
	bs.source_url
FROM banner_source bs
LEFT JOIN banner b ON b.banner_id = bs.banner_id
left JOIN (
	SELECT bs.source_id, COUNT(*) as total_store_sources
	FROM banner_source bs
	JOIN store_source ss ON ss.banner_source_id = bs.source_id
	GROUP BY bs.source_id, bs.source_banner_name
) t ON t.source_id = bs.source_id
left JOIN (
	SELECT bs.source_id, COUNT(*) AS matched_store_sources
	FROM banner_source bs
	JOIN store_source ss ON ss.banner_source_id = bs.source_id
	WHERE ss.store_id IS not null
	GROUP BY bs.source_id, bs.source_banner_name
) m ON m.source_id = bs.source_id
WHERE bs.deleted_date IS null
GROUP BY bs.source_id, bs.source_banner_name
ORDER BY bs.source_name, bs.source_banner_name;