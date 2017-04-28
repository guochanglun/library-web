package com.gcl.library.repository;

import com.gcl.library.bean.Advice;
import com.gcl.library.bean.UserLabel;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by gcl on 2016/12/16.
 * <p>
 * AdviceRepository
 */
public interface UserLabelRepository extends JpaRepository<UserLabel, Long> {

    @Query(value = "select id, label, userid from user_label where label = :label limit 0, :limit", nativeQuery = true)
    List<UserLabel> findUserLabelLimit(@Param("label") int label, @Param("limit") int limit);
}
