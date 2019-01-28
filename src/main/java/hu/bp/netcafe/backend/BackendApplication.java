package hu.bp.netcafe.backend;

import hu.bp.netcafe.backend.db.job.UpdateRemainingTimeJob;
import org.quartz.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public JobDetail updateRemainingTimeJobDetail() {
		return JobBuilder.newJob(UpdateRemainingTimeJob.class).withIdentity("updateRemainingTimeJob")
				.usingJobData("name", "World").storeDurably().build();
	}

	@Bean
	public Trigger updateRemainigTimeJobTrigger() {
		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
				.withIntervalInSeconds(2).repeatForever();

		return TriggerBuilder.newTrigger().forJob(updateRemainingTimeJobDetail())
				.withIdentity("updateRemainingTimeTrigger").withSchedule(scheduleBuilder).build();
	}

}

