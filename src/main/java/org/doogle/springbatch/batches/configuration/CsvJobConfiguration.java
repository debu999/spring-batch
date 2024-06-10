package org.doogle.springbatch.batches.configuration;

import java.io.File;
import lombok.extern.slf4j.Slf4j;
import org.doogle.springbatch.batches.model.EmployeeCsv;
import org.doogle.springbatch.batches.writer.CsvChunkWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
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

  public CsvJobConfiguration(JobRepository jobRepository, CsvChunkWriter csvChunkWriter,
      PlatformTransactionManager transactionManager) {
    this.jobRepository = jobRepository;
    this.csvChunkWriter = csvChunkWriter;
    this.transactionManager = transactionManager;
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
    return new JobBuilder("csvJob", jobRepository).start(cfvChunkStep()).build();
  }

}
