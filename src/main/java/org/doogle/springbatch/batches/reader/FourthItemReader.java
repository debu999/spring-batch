package org.doogle.springbatch.batches.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FourthItemReader implements ItemReader<Integer> {

  List<Integer> integerList = new ArrayList<>(IntStream.range(0, 100).boxed().toList());

  @Override
  public Integer read() throws Exception {
    log.info("Reading item inside Item reader");
    if (integerList.isEmpty()) {
      return null;
    }
    return integerList.removeFirst();
  }

}
