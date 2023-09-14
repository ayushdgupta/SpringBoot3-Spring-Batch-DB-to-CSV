package com.guptaji.springBatchDbToFile.service;

import com.guptaji.springBatchDbToFile.constant.AiwaiConstant;
import com.guptaji.springBatchDbToFile.entity.CustomerCopy;
import com.guptaji.springBatchDbToFile.repository.CustomerCopyRepo;

import java.util.*;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomWriter implements ItemWriter<CustomerCopy> {

  private static int counter = 0;

  @Autowired private CustomerCopyRepo customerCopyRepo;

  @Autowired private AiwaiConstant aiwaiConstant;

  @Override
  public void write(Chunk<? extends CustomerCopy> chunk) throws Exception {
    counter = counter + chunk.getItems().size();
    //    System.out.println(counter);
    //    System.out.println(chunk.getItems().get(0).getFirstName());
    List<Integer> setValues = chunk.getItems().stream().map(CustomerCopy::getId).sorted().toList();
    aiwaiConstant.setData(setValues);

    //    customerCopyRepo.saveAll(chunk);
  }
}
