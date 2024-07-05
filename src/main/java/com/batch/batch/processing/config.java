package com.batch.batch.processing;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;

import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class config {

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    PlatformTransactionManager platformTransactionManager;

    @Autowired
    SecondTasklet secondTasklet;

    @Autowired
    FirstJobListener firstJobListener;

    @Bean
    public Job firstJob(){
        return new JobBuilder("firstJob",jobRepository).incrementer(new RunIdIncrementer())
                .start(firstStep()).next(secondStep()).listener(firstJobListener).
                build();
    }


    public Step firstStep(){
        return new StepBuilder("firstStep",jobRepository).tasklet(firstTask(),platformTransactionManager).build();
    }

    public Tasklet firstTask(){
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("First Task");
                return RepeatStatus.FINISHED;
            }
        };
    }


    public Step secondStep(){
        return new StepBuilder("secondStep",jobRepository).tasklet(secondTasklet,platformTransactionManager).build();
    }

//    public Tasklet secondTask(){
//        return new Tasklet() {
//            @Override
//            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//                System.out.println("second task!");
//                return RepeatStatus.FINISHED;
//            }
//        };
//    }
}
