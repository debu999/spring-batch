package org.doogle.springbatch.batches.scheduler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FirstJobScheduler {

  JobLauncher jobLauncher;
  Job firstJob;

  public FirstJobScheduler(JobLauncher jobLauncher, Job firstJob) {
    this.jobLauncher = jobLauncher;
    this.firstJob = firstJob;
    System.out.println("First Job Scheduler Constructor");
  }

  @Scheduled(cron = "0 0/1 * 1/1 * ?")
  public void firstJobScheduler() {
    System.out.println("First Job Scheduler");
    Map<String, JobParameter<?>> jobParametersMap = new HashMap<>();
    jobParametersMap.put("time", new JobParameter<>(Instant.now().toString(), String.class));

    JobParameters jobParameters = new JobParameters(jobParametersMap);
    try {
      JobExecution jobExecution = jobLauncher.run(firstJob, jobParameters);
      log.info("Job Execution ID is {}", jobExecution.getId());
    } catch (Exception e) {
      log.error("Error starting job: {}", e.getMessage());
    }
  }
}
