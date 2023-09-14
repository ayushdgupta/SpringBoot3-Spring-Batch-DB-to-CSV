package com.guptaji.springBatchDbToFile.controller;

import com.guptaji.springBatchDbToFile.constant.AiwaiConstant;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jobs")
public class JobController {

  @Autowired private JobLauncher jobLauncher;

  @Autowired private Job job;

  @Autowired private AiwaiConstant aiwaiConstant;

  @PostMapping("/csvToDb")
  public void importCsvToDb()
      throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
          JobParametersInvalidException, JobRestartException {
    JobParameters parameters =
        new JobParametersBuilder()
            .addLocalDateTime("startAt", LocalDateTime.now())
            .toJobParameters();

    jobLauncher.run(job, parameters);

    List<Integer> data = aiwaiConstant.getData();
    Collections.sort(data);
    Set<Integer> dataSet = new HashSet<>(data);
    System.out.println(dataSet);
    System.out.println(dataSet.size());
  }
}
