package org.doogle.springbatch.batches.controller;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.doogle.springbatch.batches.model.JobParamsRequest;
import org.doogle.springbatch.batches.service.JobService;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/job")
@Slf4j
public class JobController {

  JobService jobService;
  JobOperator jobOperator;
  JobExplorer jobExplorer;

  public JobController(JobService jobService, JobOperator jobOperator, JobExplorer jobExplorer) {
    this.jobService = jobService;
    this.jobOperator = jobOperator;
    this.jobExplorer = jobExplorer;
  }

  @PostMapping("/start/{jobName}")
  public String startJob(@PathVariable String jobName, @RequestParam boolean addRunId,
      @RequestBody List<JobParamsRequest> jobParamsRequestList) {
    jobService.startJob(jobName, jobParamsRequestList, addRunId);
    return "Job - " + jobName + " submitted";
  }

  @DeleteMapping("/stop/{jobExecutionId}")
  public String stopJob(@PathVariable long jobExecutionId) {
    try {
      jobOperator.stop(jobExecutionId);
    } catch (Exception e) {
      log.error("Error stopping job: {}", e.getMessage());
      return "Error stopping job";
    }
    return "Job stopped...";
  }

  @GetMapping("/{jobName}/lastinstance")
  public JobExecution getJobLastInstanceByJobName(@PathVariable String jobName) {
    JobInstance jobInstance = null;
    JobExecution jobExecution = null;
    try {
      jobInstance = jobExplorer.getLastJobInstance(jobName);
      jobExecution = jobExplorer.getLastJobExecution(jobInstance);
    } catch (Exception e) {
      log.error("Error getting details for jobName {}, error: {} error: {}", jobName,
          e.getMessage());
    }
    return jobExecution;
  }
}
