package com.lrowy.service;

import com.lrowy.dao.UserDao;
import com.lrowy.pojo.common.http.HttpResult;
import com.lrowy.pojo.user.GithubUser;
import com.lrowy.pojo.user.User;
import com.lrowy.utils.JsonUtil;
import com.lrowy.utils.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class OAuthService {
    @Value("${github.oauth.clientId}")
    private String clientId;
    @Value("${github.oauth.clientSecret}")
    private String clientSecret;
    @Autowired
    private UserDao userDao;
    @Resource
    private HttpAPIService httpAPIService;
    @Autowired
    private ImageService imageService;

    GithubUser deserialize(String content) throws IOException {
        return JsonUtil.parse(content, GithubUser.class);
    }

    private String getToken(String code) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("code", code);
        HttpResult hr = httpAPIService.doPost("https://github.com/login/oauth/access_token", params);
        return hr.getEntityString();
    }

    public User initUser(String code) throws Exception {
        String paramsString = getToken(code);
        Map<String, Object> params = UrlUtil.parse(paramsString);
        HttpResult hr = httpAPIService.doGet("https://api.github.com/user", params);
        String content = hr.getEntityString();
        GithubUser githubUser = deserialize(content);
        User user = userDao.findUserByOrigin("Github", githubUser.getId());
        if (user == null) {
            user = new User();
            user.setType("User");
            user.setName(githubUser.getLogin());
            user.setEmail(githubUser.getEmail());
            user.setWebsite(githubUser.getBlog());
            user.setOrigin("Github");
            user.setOriginId(githubUser.getId());
            user.setAvatarVersion(1);
            userDao.saveUser(user);
            imageService.saveAvatar(githubUser.getAvatar_url(), user.getUserId());
        }
        return user;
    }
}
