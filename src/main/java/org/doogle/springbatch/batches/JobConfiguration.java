package org.doogle.springbatch.batches;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
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

    public JobConfiguration(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    public Job firstJob() {
        return new JobBuilder("firstJob", jobRepository).start(firstStep()).build();
    }

    @Bean
    public Step firstStep() {
        return new StepBuilder("firstStep", jobRepository).tasklet((contribution, chunkContext) -> {
            System.out.println("This is the first tasklet step.");
            return RepeatStatus.FINISHED;
        }, transactionManager).build();
    }
}
