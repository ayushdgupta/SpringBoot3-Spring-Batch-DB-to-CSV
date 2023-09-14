package com.guptaji.springBatchDbToFile.constant;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class AiwaiConstant {

  private List<Integer> data = new ArrayList<>();

  public List<Integer> getData() {
    return data;
  }

  public void setData(List<Integer> data) {
    this.data.addAll(data);
  }
}
