package com.guptaji.springBatchDbToFile.repository;

import com.guptaji.springBatchDbToFile.entity.CustomerCopy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerCopyRepo extends JpaRepository<CustomerCopy, Integer> {}
