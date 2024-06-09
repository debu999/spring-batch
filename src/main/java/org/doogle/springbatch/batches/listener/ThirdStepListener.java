package org.doogle.springbatch.batches.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class ThirdStepListener implements StepExecutionListener {


  @Override
  public void beforeStep(StepExecution stepExecution) {
    System.out.println("This is the before step listener." + stepExecution.getStepName()
        + stepExecution.getJobExecution().getJobInstance().getJobName());
    System.out.println(
        "This is the before step listener." + stepExecution.getJobExecution().getJobParameters());
    System.out.println("This is the before step listener." + stepExecution.getJobExecution()
        .getExecutionContext());
    stepExecution.getJobExecution().getExecutionContext().put("new_key1", "new_value1");
  }

  @Override
  public ExitStatus afterStep(StepExecution stepExecution) {
    System.out.println("This is the after step listener." + stepExecution.getStepName() + "---"
        + stepExecution.getJobExecution().getJobInstance().getJobName());
    System.out.println(
        "This is the after step listener." + stepExecution.getJobExecution().getJobParameters());
    System.out.println(
        "This is the after step listener." + stepExecution.getJobExecution().getExecutionContext());
    return ExitStatus.COMPLETED;
  }

}
