package com.gcl.library.repository;

import com.gcl.library.bean.Advice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by gcl on 2016/12/16.
 * <p>
 * AdviceRepository
 */
public interface AdviceRepository extends JpaRepository<Advice, Long> {
}
