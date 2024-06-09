package org.doogle.springbatch.batches;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JobConfiguration {

  JobRepository jobRepository;
  PlatformTransactionManager transactionManager;

  ThirdTasklet thirdTasklet;

  public JobConfiguration(JobRepository jobRepository,
      PlatformTransactionManager transactionManager, ThirdTasklet thirdTasklet) {
    this.jobRepository = jobRepository;
    this.transactionManager = transactionManager;
    this.thirdTasklet = thirdTasklet;
  }

  @Bean
  public Job firstJob(JobParametersIncrementer incrementer, Step firstStep, Step secondStep) {
    return new JobBuilder("firstJob", jobRepository).start(firstStep)
        .incrementer(incrementer) // add this line
        .next(secondStep).next(
            new StepBuilder("thirdStep", jobRepository).tasklet(thirdTasklet, transactionManager)
                .build()).build();
  }

  @Bean
  public JobParametersIncrementer incrementer() {
    return new RunIdIncrementer();
  }

  @Bean
  public Step firstStep() {
    return new StepBuilder("firstStep", jobRepository).tasklet((contribution, chunkContext) -> {
      System.out.println("This is the first tasklet step.");
      return RepeatStatus.FINISHED;
    }, transactionManager).build();
  }

  @Bean
  public Step secondStep() {
    return new StepBuilder("secondStep", jobRepository).tasklet((contribution, chunkContext) -> {
      System.out.println("This is the Second tasklet step.");
      return RepeatStatus.FINISHED;
    }, transactionManager).build();
  }
}
