package com.gcl.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gcl on 2016/12/18.
 */
public class TestForEach {

    @Test
    public void forEachTest() {
        List<String> list = new ArrayList<>();
        for(int i=0; i<10; i++){
            list.add("title"+i);
        }

        list.forEach(s->{
            if(s.equals("title3")) return;
            System.out.println(s);
        });
    }
}
