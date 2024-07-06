package com.batch.batch.processing.config;


import com.batch.batch.processing.ItemProcessor.ItemProcessor1;
import com.batch.batch.processing.ItemReader.ItemReader1;
import com.batch.batch.processing.ItemWriter.ItemWriter1;
import com.batch.batch.processing.JobListeners.FirstJobListener;
import com.batch.batch.processing.SecondTasklet;
import com.batch.batch.processing.StepListeners.FirstStepListener;
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

    @Autowired
    FirstStepListener firstStepListener;

    @Autowired
    ItemWriter1 itemWriter1;
    @Autowired
    ItemReader1 itemReader1;

    @Autowired
    ItemProcessor1 itemProcessor1;

//    @Bean
//    public Job firstJob(){
//        return new JobBuilder("firstJob",jobRepository).incrementer(new RunIdIncrementer())
//                .start(firstStep()).next(secondStep()).listener(firstJobListener).
//                build();
//    }
//

    public Step firstStep(){
        return new StepBuilder("firstStep",jobRepository)
                .tasklet(firstTask(),platformTransactionManager)
                .listener(firstStepListener)
                .build();
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


//    public Step secondStep(){
//        return new StepBuilder("secondStep",jobRepository).tasklet(secondTasklet,platformTransactionManager).build();
//    }

    @Bean
    public Job secondJob() {
        return new JobBuilder("secondjob",jobRepository).incrementer(new RunIdIncrementer()).start(secondStep()).build();
    }

    public Step secondStep(){
        return new StepBuilder("secondStep",jobRepository).<Integer,Long>chunk(3,platformTransactionManager)
                .reader(itemReader1).processor(itemProcessor1)
                .writer(itemWriter1).build();
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
