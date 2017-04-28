package com.gcl.library.repository;

import com.gcl.library.bean.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by gcl on 2016/12/16.
 * <p>
 * UserRepository
 */
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Subscription findByUserIdAndKeywordsAndIndes(long userId, String key, int indes);

    List<Subscription> findByUserId(long userId);
}
