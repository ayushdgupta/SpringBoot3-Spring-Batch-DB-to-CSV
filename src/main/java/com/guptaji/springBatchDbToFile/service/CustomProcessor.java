package com.guptaji.springBatchDbToFile.service;

import com.guptaji.springBatchDbToFile.entity.CustomerCopy;

import org.springframework.batch.item.ItemProcessor;

public class CustomProcessor implements ItemProcessor<CustomerCopy, CustomerCopy> {

  @Override
  public CustomerCopy process(CustomerCopy item) throws Exception {
    item.setStatus("Completed");
    return item;
  }
}
