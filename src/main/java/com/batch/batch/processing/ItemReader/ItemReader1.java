package com.batch.batch.processing.ItemReader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class ItemReader1 implements ItemReader<Integer> {
    List<Integer>  list= Arrays.asList(1,2,3,4,5,6,7,8,9,10);
    Integer i=0;
    Integer k=0;
    @Override
    public Integer read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if(i<list.size()){
            System.out.println("Inside the Item Reader");
            k=list.get(i);
            i++;
            return k;

        }
        return null;
    }
}
