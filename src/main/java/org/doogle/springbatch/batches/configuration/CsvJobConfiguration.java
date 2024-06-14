package org.doogle.springbatch.batches.configuration;

import java.io.File;
import lombok.extern.slf4j.Slf4j;
import org.doogle.springbatch.batches.model.EmployeeCsv;
import org.doogle.springbatch.batches.writer.CsvChunkWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Slf4j
public class CsvJobConfiguration {

  JobRepository jobRepository;
  PlatformTransactionManager transactionManager;
  CsvChunkWriter csvChunkWriter;
  JobParametersIncrementer incrementer;

  public CsvJobConfiguration(JobRepository jobRepository,
      CsvChunkWriter csvChunkWriter,
      PlatformTransactionManager transactionManager, JobParametersIncrementer incrementer) {
    this.jobRepository = jobRepository;
    this.csvChunkWriter = csvChunkWriter;
    this.transactionManager = transactionManager;
    this.incrementer = incrementer;
  }

  public FlatFileItemReader<EmployeeCsv> flatFileItemReader() {
    log.info("Inside FlatFileItemReader");
    FlatFileItemReader<EmployeeCsv> reader = new FlatFileItemReader<>();
    reader.setResource(new FileSystemResource(new File("src/main/resources/files/employees.csv")));
    reader.setLineMapper(new DefaultLineMapper<>() {
      {
        setLineTokenizer(new DelimitedLineTokenizer() {
          {
            setNames("ID", "First Name", "Last Name", "Email");
          }
        });
        setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {
          {
            setTargetType(EmployeeCsv.class);
          }
        });
      }
    });
    reader.setLinesToSkip(1);
    return reader;
  }

  private Step cfvChunkStep() {
    return new StepBuilder("csvChunkStep", jobRepository).<EmployeeCsv, EmployeeCsv>chunk(10,
        transactionManager).reader(flatFileItemReader()).writer(csvChunkWriter).build();
  }

  @Bean
  public Job csvJob() {
    return new JobBuilder("csvJob", jobRepository).start(cfvChunkStep())
        .incrementer(incrementer).build();
  }
//
//  @Bean
//  @Scope(value = "job", proxyMode = ScopedProxyMode.TARGET_CLASS)
//  public Job fileGeneratorJob(
//      @Value("#{jobExecutionContext['dataElements']}") List<String> jobMetadata,
//      PlatformTransactionManager transactionManager, JobRepository jobRepository) {
//    SimpleJobBuilder simpleJobBuilder = new JobBuilder("fileGeneratorJob", jobRepository).start(
//            new StepBuilder("initializeFileGenerator", jobRepository).tasklet(
//                (contribution, chunkContext) -> {
//                  System.out.println("This is the  tasklet1 step.");
//                  return RepeatStatus.FINISHED;
//                }, transactionManager).build())
//        .next(new StepBuilder("step2", jobRepository).tasklet((contribution, chunkContext) -> {
//          System.out.println("This is the  tasklet2 step.");
//          return RepeatStatus.FINISHED;
//        }, transactionManager).build());
//    for (String stepId : jobMetadata) {
//      simpleJobBuilder = simpleJobBuilder.next(
//          new StepBuilder(stepId, jobRepository).tasklet((contribution, chunkContext) -> {
//            System.out.printf("This is the tasklet %s step.%n", stepId);
//            return RepeatStatus.FINISHED;
//          }, transactionManager).build());
//    }
//    simpleJobBuilder.next(
//            new StepBuilder("afterdynamicstep", jobRepository).tasklet((contribution, chunkContext) -> {
//              System.out.println("This is the  tasklet afterdynamicstep step.");
//              return RepeatStatus.FINISHED;
//            }, transactionManager).build()).next(
//            new StepBuilder("afterdynamicstep2", jobRepository).tasklet(
//                (contribution, chunkContext) -> {
//                  System.out.println("This is the tasklet afterdynamicstep2 step.");
//                  return RepeatStatus.FINISHED;
//                }, transactionManager).build()).incrementer(new RunIdIncrementer())
//        .preventRestart(); // Remove prevent restart when enabling job to restart, restart of export job is not fully tested yet; so, it's prevented as of now...
//    return simpleJobBuilder.build();
//  }
//
//  @Bean
//  public Job fileExporterJob(PlatformTransactionManager transactionManager,
//      JobRepository jobRepository) {
//    return new JobBuilder("fileExporterJob", jobRepository).start(
//            new StepBuilder("fileexporterstep", jobRepository).tasklet((contribution, chunkContext) -> {
//              System.out.println("This is the  fileexporterstep step.");
//              return RepeatStatus.FINISHED;
//            }, transactionManager).build()).next(
//            new StepBuilder("createdirectorystep", jobRepository).tasklet(
//                (contribution, chunkContext) -> {
//                  System.out.println("This is the createdirectorystep step.");
//                  return RepeatStatus.FINISHED;
//                }, transactionManager).build()).next(
//            new StepBuilder("fileGenerator", jobRepository).job(fileGeneratorJob(null, null, null))
//                .parametersExtractor(new JobParametersExtractor() {
//                  @Override
//                  public JobParameters getJobParameters(Job job, StepExecution stepExecution) {
//
//                    return new JobParametersBuilder().addString("jobId",
//                        String.valueOf(System.currentTimeMillis())).toJobParameters();
//                  }
//                }).build())
//        .next(new StepBuilder("s3Copy", jobRepository).tasklet((contribution, chunkContext) -> {
//          System.out.println("This is the s3Copy step.");
//          return RepeatStatus.FINISHED;
//        }, transactionManager).build()).incrementer(new RunIdIncrementer()).build();
//  }

}
