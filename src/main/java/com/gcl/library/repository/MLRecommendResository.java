package com.gcl.library.repository;

import com.gcl.library.bean.MLRecommend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by 14557 on 2017/4/28.
 */
public interface MLRecommendResository extends JpaRepository<MLRecommend, Long> {
    List<MLRecommend> findByUserid(long userid);
}
