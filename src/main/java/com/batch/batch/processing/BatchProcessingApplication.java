package com.batch.batch.processing;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class BatchProcessingApplication  {

//	@Autowired
//	private JobLauncher jobLauncher;
//
//	@Autowired
//	private Job firstJob;

	public static void main(String[] args) {
		SpringApplication.run(BatchProcessingApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		JobExecution jobExecution = jobLauncher.run(firstJob, new JobParameters());
//		System.out.println("Job Execution Status: " + jobExecution.getStatus());
//	}
}
