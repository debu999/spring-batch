package org.doogle.springbatch.batches.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FourthItemProcessor implements ItemProcessor<Integer, Long> {

  @Override
  public Long process(Integer item) throws Exception {
    log.info("Processing item: {} inside Item reader", item);
    return 20L + item.longValue();
  }

}
