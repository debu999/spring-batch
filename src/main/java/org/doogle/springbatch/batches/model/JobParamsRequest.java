package org.doogle.springbatch.batches.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobParamsRequest {

  public String paramKey;
  public String paramValue;

}
