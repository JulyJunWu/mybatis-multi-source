package com.multisource.demo.usermapper;

import com.multisource.demo.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User selectOne();

    int insert(User user);
}
