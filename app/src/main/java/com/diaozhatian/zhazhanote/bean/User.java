package com.diaozhatian.zhazhanote.bean;

import com.diaozhatian.zhazhanote.http.HttpResult;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/10/17.
 */
public class User extends HttpResult {
    public int userId;
    public String userCode;
    public String token;


    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", userCode='" + userCode + '\'' + ", token='" + token + '\'' + '}';
    }
}
