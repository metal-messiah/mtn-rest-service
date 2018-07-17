package com.mtn.scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.mtn.service.PlannedGroceryService;
import com.mtn.service.SecurityService;
import com.mtn.util.MtnLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

	private final PlannedGroceryService plannedGroceryService;
	private final SecurityService securityService;

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Autowired
	public ScheduledTasks(PlannedGroceryService plannedGroceryService, SecurityService securityService) {
		this.plannedGroceryService = plannedGroceryService;
		this.securityService = securityService;
	}

	// Every Day at 11pm
	@Scheduled(cron = "0 0 23 * * *")
	public void reportCurrentTime() {
		// TODO run Planned Grocery and Chain XY source extractions
		MtnLogger.info("The time is now {}", dateFormat.format(new Date()));
		plannedGroceryService.addAndUpdateSourcesFromPlannedGrocery(securityService.getCurrentUser());
	}
}