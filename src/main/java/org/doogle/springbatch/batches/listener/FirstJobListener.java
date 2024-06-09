package org.doogle.springbatch.batches.listener;

import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class FirstJobListener implements JobExecutionListener {

  @Override
  public void beforeJob(org.springframework.batch.core.JobExecution jobExecution) {
    System.out.println(
        "This is the before job listener." + jobExecution.getJobInstance().getJobName());
    System.out.println("This is the before job listener." + jobExecution.getJobParameters());
    System.out.println("This is the before job listener." + jobExecution.getExecutionContext());
    jobExecution.getExecutionContext().put("new_key", "new_value");
  }

  @Override
  public void afterJob(org.springframework.batch.core.JobExecution jobExecution) {
    System.out.println(
        "This is the after job listener." + jobExecution.getJobInstance().getJobName());
    System.out.println("This is the after job listener." + jobExecution.getJobParameters());
    System.out.println("This is the after job listener." + jobExecution.getExecutionContext());
  }

}
