package org.doogle.springbatch.batches.writer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FourthItemWriter implements ItemWriter<Long> {

  @Override
  public void write(Chunk<? extends Long> chunk) throws Exception {
    log.info("Inside Item Writer");
    log.info("Writing chunk: {}", chunk);
  }
}
