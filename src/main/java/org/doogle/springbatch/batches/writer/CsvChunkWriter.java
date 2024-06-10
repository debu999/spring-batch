package org.doogle.springbatch.batches.writer;

import lombok.extern.slf4j.Slf4j;
import org.doogle.springbatch.batches.model.EmployeeCsv;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CsvChunkWriter implements ItemWriter<EmployeeCsv> {

  @Override
  public void write(Chunk<? extends EmployeeCsv> chunk) throws Exception {
    log.info("Inside Item Writer");
    log.info("Writing chunk: {}", chunk);
  }
}
