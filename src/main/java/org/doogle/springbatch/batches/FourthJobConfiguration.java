package org.doogle.springbatch.batches;

import org.doogle.springbatch.batches.processor.FourthItemProcessor;
import org.doogle.springbatch.batches.reader.FourthItemReader;
import org.doogle.springbatch.batches.writer.FourthItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class FourthJobConfiguration {

  JobRepository jobRepository;
  FourthItemReader reader;
  FourthItemProcessor processor;
  FourthItemWriter writer;
  PlatformTransactionManager transactionManager;
  JobParametersIncrementer incrementer;
  Step secondStep;

  public FourthJobConfiguration(JobRepository jobRepository, FourthItemReader reader,
      FourthItemProcessor processor, FourthItemWriter writer,
      PlatformTransactionManager transactionManager, JobParametersIncrementer incrementer,
      Step secondStep) {
    this.jobRepository = jobRepository;
    this.reader = reader;
    this.processor = processor;
    this.writer = writer;
    this.transactionManager = transactionManager;
    this.incrementer = incrementer;
    this.secondStep = secondStep;
  }

  @Bean
  public Job fourthJob(Step fourthStep, Step secondStep) {
    return new JobBuilder("fourthJob", jobRepository).incrementer(incrementer).start(secondStep)
        .next(fourthStep).build();
  }

  @Bean
  public Step fourthStep() {
    return new StepBuilder("fourthStep", jobRepository).<Integer, Long>chunk(10, transactionManager)
        .reader(reader).processor(processor).writer(writer).build();
  }

}
