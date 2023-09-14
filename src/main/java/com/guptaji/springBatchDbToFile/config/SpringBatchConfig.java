package com.guptaji.springBatchDbToFile.config;

import com.guptaji.springBatchDbToFile.entity.CustomerCopy;
import com.guptaji.springBatchDbToFile.entity.CustomerRowMapper;
import com.guptaji.springBatchDbToFile.repository.CustomerCopyRepo;
import com.guptaji.springBatchDbToFile.service.CustomProcessor;
import com.guptaji.springBatchDbToFile.service.CustomWriter;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.PostgresPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SpringBatchConfig {

  @Autowired private DataSource dataSource;

  @Autowired private CustomerCopyRepo customerCopyRepo;

  //  @Bean
  //  public JdbcCursorItemReader<CustomerCopy> itemReader() {
  //    return new JdbcCursorItemReaderBuilder<CustomerCopy>()
  //        .dataSource(this.dataSource)
  //        .name("customerReader")
  //        .sql("select * from CUSTOMER")
  //        .rowMapper(new CustomerRowMapper())
  //        //        .verifyCursorPosition(false)
  //        .build();
  //  }

  @Bean
  public JdbcPagingItemReader<CustomerCopy> itemReader() {
    //    Map<String, Object> parameterValues = new HashMap<>();
    //    parameterValues.put("status", "NEW");

    PostgresPagingQueryProvider postgresPagingQueryProvider = new PostgresPagingQueryProvider();
    postgresPagingQueryProvider.setSelectClause("select *");
    postgresPagingQueryProvider.setFromClause("from CUSTOMER");
    postgresPagingQueryProvider.setSortKeys(Map.of("id", Order.ASCENDING));

    return new JdbcPagingItemReaderBuilder<CustomerCopy>()
        .name("customerReader")
        .dataSource(dataSource)
        .queryProvider(postgresPagingQueryProvider)
        //            .parameterValues(parameterValues)
        .rowMapper(new CustomerRowMapper())
        .pageSize(10)
        .build();
  }

  //  @Bean
  //  public SqlPagingQueryProviderFactoryBean queryProvider() {
  //    SqlPagingQueryProviderFactoryBean provider = new SqlPagingQueryProviderFactoryBean();
  //
  //    provider.setSelectClause("select *");
  //    provider.setFromClause("from CUSTOMER");
  //    //    provider.setWhereClause("where status=:status");
  //    provider.setSortKey("id");
  //
  //    return provider;
  //  }

  @Bean
  public CustomProcessor customProcessor() {
    return new CustomProcessor();
  }

  @Bean
  public CustomWriter customWriter() {
    return new CustomWriter();
  }

  //  @Bean
  //  public RepositoryItemWriter<CustomerCopy> customWriter() {
  //    RepositoryItemWriter<CustomerCopy> writer = new RepositoryItemWriter<>();
  //    writer.setRepository(customerCopyRepo);
  //    writer.setMethodName("saveAll");
  //    return writer;
  //  }

  @Bean
  public Step step1(
      JobRepository jobRepository,
      PlatformTransactionManager transactionManager,
      JdbcPagingItemReader<CustomerCopy> itemReader) {
    return new StepBuilder("step1", jobRepository)
        .<CustomerCopy, CustomerCopy>chunk(10, transactionManager)
        .reader(itemReader)
        .processor(customProcessor())
        .writer(customWriter())
        .taskExecutor(taskExecutor())
        .build();
  }

  @Bean
  public Job importCustomerJob(
      JobRepository jobRepository, PlatformTransactionManager transactionManager, Step step1) {
    return new JobBuilder("importCustomerJob", jobRepository).start(step1).build();
  }

  // For Multithreading
  @Bean
  public TaskExecutor taskExecutor() {
    SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor();
    executor.setConcurrencyLimit(10);
    return executor;
  }
}
