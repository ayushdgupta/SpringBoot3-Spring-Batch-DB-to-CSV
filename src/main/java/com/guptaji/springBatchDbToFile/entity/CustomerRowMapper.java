package com.guptaji.springBatchDbToFile.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CustomerRowMapper implements RowMapper<CustomerCopy> {

  @Override
  public CustomerCopy mapRow(ResultSet rs, int rowNum) throws SQLException {
    CustomerCopy customer = new CustomerCopy();
    customer.setId(rs.getInt("id"));
    customer.setCountry(rs.getString("country"));
    customer.setContactNo(rs.getString("contact_no"));
    customer.setFirstName(rs.getString("first_name") + rs.getString("last_name"));
    return customer;
  }
}
