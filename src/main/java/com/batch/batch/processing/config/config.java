package com.batch.batch.processing.config;


import com.batch.batch.processing.ItemProcessor.ItemProcessor1;
import com.batch.batch.processing.ItemReader.ItemReader1;
import com.batch.batch.processing.ItemWriter.ItemWriter1;
import com.batch.batch.processing.ItemWriter.ItemWriter2;
import com.batch.batch.processing.JobListeners.FirstJobListener;
import com.batch.batch.processing.SecondTasklet;
import com.batch.batch.processing.StepListeners.FirstStepListener;
import com.batch.batch.processing.entity.StudentCsv;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;

import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.config.Task;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.File;

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

    @Autowired
    ItemWriter2 itemWriter2;

    @Bean
    public Job firstJob(){
        return new JobBuilder("firstJob",jobRepository).incrementer(new RunIdIncrementer())
                .start(firstStep()).listener(firstJobListener).
                build();
    }


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



    @Bean
    public Job secondJob() {
        return new JobBuilder("secondJob",jobRepository).incrementer(new RunIdIncrementer()).start(secondStep()).build();
    }

    public Step secondStep(){
        return new StepBuilder("secondStep",jobRepository).<Integer,Long>chunk(3,platformTransactionManager)
                .reader(itemReader1).processor(itemProcessor1)
                .writer(itemWriter1).build();
    }


    @Bean
    public Job thirdJob(){
        return new JobBuilder("thirdJob",jobRepository).incrementer(new RunIdIncrementer()).
                start(thirdStep()).build();
    }

    Step thirdStep(){
        return new StepBuilder("thirdstep",jobRepository).tasklet(thirdtasklet(),platformTransactionManager).build();
    }

    Tasklet thirdtasklet(){
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("This is the Third Job!");
                return RepeatStatus.FINISHED;
            }
        };
    }

    @Bean
    public Job fourthJob() {
        return new JobBuilder("fourthJob",jobRepository).incrementer(new RunIdIncrementer()).start(fourthStep()).build();
    }

    public Step fourthStep(){
        return new StepBuilder("fourthStep",jobRepository).<StudentCsv,StudentCsv>chunk(3,platformTransactionManager)
                .reader(reader2())
                .writer(itemWriter2).build();
    }


    public FlatFileItemReader<StudentCsv> reader2(){
        FlatFileItemReader<StudentCsv> flatFileItemReader=new FlatFileItemReader<StudentCsv>();
        System.out.println("check!!");
        flatFileItemReader.setResource(new FileSystemResource(new File("/Users/vivekkapa/Desktop/students.csv")));
        flatFileItemReader.setLineMapper(createLineMapper());
        flatFileItemReader.setLinesToSkip(1);
        return flatFileItemReader;
    }
    public LineMapper createLineMapper(){
        DefaultLineMapper defaultLineMapper=new DefaultLineMapper();
        DelimitedLineTokenizer delimitedLineTokenizer=new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(",");
        delimitedLineTokenizer.setNames("ID","First Name","Last Name","Email");
        BeanWrapperFieldSetMapper<StudentCsv> beanWrapperFieldSetMapper=new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(StudentCsv.class);
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        return  defaultLineMapper;
    }

}
