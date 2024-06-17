package org.doogle.springbatch.batches.configuration;

import java.util.List;
import lombok.Data;
import org.springframework.aop.scope.ScopedProxyFactoryBean;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@StepScope
@Data
public class StepContextHolder {

  public List<String> dataElements;

  @BeforeStep
  public void beforeStep(StepExecution stepExecution) {
    dataElements = stepExecution.getJobExecution().getExecutionContext()
        .get("dataElements", List.class);
  }

  @Bean
  public ScopedProxyFactoryBean stepScopedProxy() {
    ScopedProxyFactoryBean proxyFactoryBean = new ScopedProxyFactoryBean();
    proxyFactoryBean.setTargetBeanName("stepContextHolder");
    proxyFactoryBean.setProxyTargetClass(true);
    proxyFactoryBean.setExposeProxy(true);
    return proxyFactoryBean;
  }
}
