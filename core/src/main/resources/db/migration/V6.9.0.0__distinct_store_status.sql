ALTER TABLE `store_status`
	ADD UNIQUE INDEX `Index 5` (`store_id`, `status`, `status_start_date`);