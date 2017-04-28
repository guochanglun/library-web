package com.gcl.library.repository;

import com.gcl.library.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by gcl on 2016/12/16.
 * <p>
 * UserRepository
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据email查找
     */
    User findByEmail(String email);
}
