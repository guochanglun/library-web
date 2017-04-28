package com.gcl.library.repository;

import com.gcl.library.bean.BorrowedWatcher;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by gcl on 2016/12/16.
 * <p>
 * UserRepository
 */
public interface BorrowedWatcherRepository extends JpaRepository<BorrowedWatcher, Long> {
}
