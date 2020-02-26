package com.lrowy.dao;

import com.lrowy.pojo.email.Email;

public interface EmailDao {
    public int saveEmail(Email email);
    public Email findEmail(int emailId);
}
