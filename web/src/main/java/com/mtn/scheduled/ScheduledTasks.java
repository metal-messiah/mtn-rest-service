package com.mtn.scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.mtn.util.MtnLogger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	// Every Day at 11pm
	@Scheduled(cron = "0 0 23 * * *")
	public void reportCurrentTime() {
		// TODO run Planned Grocery and Chain XY source extractions
		MtnLogger.info("The time is now {}", dateFormat.format(new Date()));
	}
}