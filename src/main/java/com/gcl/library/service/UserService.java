package com.gcl.library.service;

import com.gcl.library.bean.User;
import com.gcl.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Created by gcl on 2016/12/16.
 * UserService
 */
@Service
@Scope("session")
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 登录
     */
    public User login(User user) {
        User u = userRepository.findByEmail(user.getEmail());
        if (u != null && u.getPassword().equals(user.getPassword())) {
            return u;
        }
        return null;
    }

    /**
     * 注册
     */
    public boolean register(User user) {
        User u = userRepository.findByEmail(user.getEmail());
        if (u != null) {
            return false;
        }
        userRepository.save(user);
        return true;
    }

    /**
     * 修改信息
     */
    public boolean change(User user) {
        userRepository.save(user);
        return true;
    }
}
