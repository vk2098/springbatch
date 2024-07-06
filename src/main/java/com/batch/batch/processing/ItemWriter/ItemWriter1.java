package com.batch.batch.processing.ItemWriter;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ItemWriter1 implements ItemWriter<Long> {
    @Override
    public void write(Chunk chunk) throws Exception {
        System.out.println("Inside the item writer");
        chunk.getItems().stream().forEach(System.out::println);

    }
}
