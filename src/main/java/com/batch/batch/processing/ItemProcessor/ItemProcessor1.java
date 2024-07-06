package com.batch.batch.processing.ItemProcessor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ItemProcessor1 implements ItemProcessor<Integer,Long> {

    @Override
    public Long process(Integer item) throws Exception {
        System.out.println("Inside the Item Processor");
        return Long.valueOf(item)+20;
    }
}
