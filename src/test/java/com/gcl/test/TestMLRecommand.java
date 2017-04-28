package com.gcl.test;

import com.gcl.library.Application;
import com.gcl.library.bean.MLRecommend;
import com.gcl.library.repository.MLRecommendResository;
import com.gcl.library.service.BatchService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Created by 14557 on 2017/4/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class TestMLRecommand {

    @Autowired
    private BatchService service;

    @Autowired
    private MLRecommendResository mlRecommendResository;

    @Test
    public void testUpdate() {
        service.updateMLRecommendBook();
    }

    @Ignore
    @Test
    public void testRead() {
        List<MLRecommend> recommends = mlRecommendResository.findByUserid(7);
        recommends.forEach(mlRecommend -> {
            System.out.println(mlRecommend.getBook());
        });
    }
}
