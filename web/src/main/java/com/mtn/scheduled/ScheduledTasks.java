package com.mtn.scheduled;

import com.mtn.service.ChainXYService;
import com.mtn.service.CommissaryService;
import com.mtn.service.PlannedGroceryService;
import com.mtn.service.SecurityService;
import com.mtn.util.MtnLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTasks {

	private final PlannedGroceryService plannedGroceryService;
	private final SecurityService securityService;
	private final CommissaryService commissaryService;
	private final ChainXYService chainXYService;

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Autowired
	public ScheduledTasks(PlannedGroceryService plannedGroceryService,
						  ChainXYService chainXYService,
						  CommissaryService commissaryService,
						  SecurityService securityService) {
		this.plannedGroceryService = plannedGroceryService;
		this.chainXYService = chainXYService;
		this.commissaryService = commissaryService;
		this.securityService = securityService;
	}

	// Every Day at Midnight MDT
	@Scheduled(cron = "0 0 6 * * *")
	public void syncPlannedGrocery() {
		MtnLogger.info("Syncing Planned Grocery Data at {}", dateFormat.format(new Date()));
		plannedGroceryService.addAndUpdateSourcesFromPlannedGrocery(securityService.getCurrentUser());
	}

	// Every Day at 1am MDT
	@Scheduled(cron = "0 0 7 * * *")
	public void syncChainXy() {
		MtnLogger.info("Syncing ChainXY Data at {}", dateFormat.format(new Date()));
		chainXYService.updateDbSources(securityService.getCurrentUser(), false);
	}

	// The first day of every month at 11pm MDT
	@Scheduled(cron = "0 0 5 1 * *")
	public void scrapeCommissary() {
		MtnLogger.info("Syncing Commissary Data at {}", dateFormat.format(new Date()));
		commissaryService.scrapeCommissaryData();
	}


}