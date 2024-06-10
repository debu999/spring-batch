package org.doogle.springbatch.batches.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCsv {

  private Long id;
  private String firstName;
  private String lastName;
  private String email;

}
