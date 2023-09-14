package com.guptaji.springBatchDbToFile.repository;

import com.guptaji.springBatchDbToFile.entity.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {}
