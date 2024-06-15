package org.doogle.springbatch.batches.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.doogle.springbatch.batches.model.JobParamsRequest;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JobService {

  Job firstJob;
  Job fourthJob;
  JobLauncher jobLauncher;
  Job csvJob;
  JobParametersIncrementer incrementer;
  JobExplorer jobExplorer;
//  Job fileExporterJob;

  public JobService(@Qualifier("firstJob") Job firstJob, @Qualifier("fourthJob") Job fourthJob,
      JobLauncher jobLauncher, @Qualifier("csvJob") Job csvJob,
//      @Qualifier("fileExporterJob") Job fileExporterJob
      JobParametersIncrementer incrementer, JobExplorer jobExplorer) {
    this.firstJob = firstJob;
    this.fourthJob = fourthJob;
    this.jobLauncher = jobLauncher;
    this.csvJob = csvJob;
//    this.fileExporterJob = fileExporterJob;
    this.incrementer = incrementer;
    this.jobExplorer = jobExplorer;
    log.info("JobService bean created");
  }

  @Async
  public void startJob(String jobName, List<JobParamsRequest> jobParamsRequestList,
      boolean addRunId) {
    Map<String, JobParameter<?>> jobParametersMap = new HashMap<>();
//    jobParametersMap.put("time", new JobParameter<>(Instant.now().toString(), String.class));
    jobParamsRequestList.forEach(jobParamsRequest -> {
      jobParametersMap.put(jobParamsRequest.getParamKey(),
          new JobParameter<>(jobParamsRequest.getParamValue(), String.class));
    });
    if (addRunId) {
      Long runId = 1L;
      JobInstance jobInstance = jobExplorer.getLastJobInstance(jobName);
      if (jobInstance != null) {
        JobExecution jobExecution = jobExplorer.getLastJobExecution(jobInstance);
        if (jobExecution != null) {
          runId = jobExecution.getJobParameters().getLong("run.id", 0L) + 1L;
        }
      }
      jobParametersMap.put("run.id", new JobParameter<>(runId, Long.class));
    }
    JobParameters jobParameters = new JobParameters(jobParametersMap);
    try {
      JobExecution jobExecution = null;
      if (jobName.equals("firstJob")) {
        jobExecution = jobLauncher.run(firstJob, jobParameters);
      } else if (jobName.equals("fourthJob")) {
        jobExecution = jobLauncher.run(fourthJob, jobParameters);
      } else if (jobName.equals("csvJob")) {
        jobExecution = jobLauncher.run(csvJob, jobParameters);
      }
//      else if (jobName.equals("fileExporterJob")) {
//        jobExecution = jobLauncher.run(fileExporterJob, jobParameters);
//      }
      assert jobExecution != null;
      log.info("Job Execution ID is {}", jobExecution.getId());
    } catch (Exception e) {
      log.error("Error starting job: {}", e.getMessage());
    }
  }

}
