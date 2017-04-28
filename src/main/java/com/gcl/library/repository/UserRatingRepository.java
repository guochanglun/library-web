package com.gcl.library.repository;

import com.gcl.library.bean.UserLabel;
import com.gcl.library.bean.UserRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by gcl on 2016/12/16.
 * <p>
 * AdviceRepository
 */
public interface UserRatingRepository extends JpaRepository<UserRating, Long> {

    List<UserRating> findByUserid(int userid);
}
