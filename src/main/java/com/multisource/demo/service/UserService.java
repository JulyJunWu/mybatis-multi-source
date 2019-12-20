package com.multisource.demo.service;

import com.multisource.demo.model.User;
import com.multisource.demo.usermapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author JunWu
 * user service class
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User selectOne() {
        return userMapper.selectOne();
    }


    @Transactional(transactionManager = "userTransaction", rollbackFor = Exception.class)
    public int insert(User user) {
        int a = userMapper.insert(user);
        //int b = 1 / 0;
        return a;
    }
}
