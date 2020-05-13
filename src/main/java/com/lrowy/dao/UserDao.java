package com.lrowy.dao;

import com.lrowy.pojo.user.User;

public interface UserDao {
    public int saveUser(User user);
    public User findUserByEmail(String email);
    public User findUserByOrigin(String origin, String originId);
}
