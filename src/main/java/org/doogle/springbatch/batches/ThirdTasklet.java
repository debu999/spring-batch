package org.doogle.springbatch.batches;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;

@Service
public class ThirdTasklet implements Tasklet {


  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
      throws Exception {
    System.out.println(
        "This is the third tasklet step." + chunkContext.getStepContext().getJobExecutionContext());
    return RepeatStatus.FINISHED;
  }
}
