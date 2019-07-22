package com.lrowy.pojo;

public class User {
    private int user_id;
    private String isSuperUser;
    private String email;
    private String password;
    private String name;
    private String info;
    private String photo;

    //注意这个空的默认构造函数是必须的写上的
    public User() {

    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getIsSuperUser() {
        return isSuperUser;
    }

    public void setIsSuperUser(String isSuperUser) {
        this.isSuperUser = isSuperUser;
    }
}
