package com.batch.batch.processing.service;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class BatchServiceJob2 {

    @Qualifier("thirdJob")
    @Autowired
    Job thirdjob;

    @Autowired
    JobLauncher jobLauncher;
    @Scheduled(cron = "0 * * * * ?")
    public String runJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        Map<String, JobParameter<?>> map=new HashMap<>();
        map.put("time",new JobParameter<>(LocalDateTime.now().toString(),String.class));
        JobParameters jobParameters=new JobParameters(map);
        JobExecution jobExecution=jobLauncher.run(thirdjob,jobParameters);
        System.out.println("Ran the cron job"+jobExecution.getJobId());
        return "Third Job Ran!";
    }

}
