package com.batch.batch.processing.StepListeners;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class FirstStepListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("before 1st step"+" "+stepExecution.getStepName());
        System.out.println("job exec con"+" "+stepExecution.getJobExecution().getExecutionContext());
        System.out.println("step exec con"+" "+stepExecution.getExecutionContext());

        }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("after 1st step"+" "+stepExecution.getStepName());
        System.out.println("job exec con"+" "+stepExecution.getJobExecution().getExecutionContext());
        System.out.println("step exec con"+" "+stepExecution.getExecutionContext());
        return ExitStatus.COMPLETED;
    }
}
