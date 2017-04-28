package com.gcl.library.repository;

import com.gcl.library.bean.KeyWords;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by 14557 on 2017/4/28.
 */
public interface KeywordsRepository extends JpaRepository<KeyWords, Long> {

    KeyWords findByUserIDAndKeywords(long userid, String keyword);

    List<KeyWords> findByUserID(Long userID);
}
