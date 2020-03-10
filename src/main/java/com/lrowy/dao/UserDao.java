package com.lrowy.dao;

import com.lrowy.pojo.user.User;

public interface UserDao {
    public User findUserByEmail(String email);
}
