package com.diaozhatian.zhazhanote.bean;

import com.diaozhatian.zhazhanote.http.HttpResult;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/10/17.
 */
public class User extends HttpResult<User> {
    public String token;
    public int userId;
    public String userCode;// 账号
    public int id;// 用户id
    public String phone;// 电话号码
    public String username;// 姓名
    public String headPhotoName;// 头像名称
    public String originalPhotoName;// 上传头像时原始名称
    public String sex;// 性别
    public String degree;// 学历
    public String nativePlace;// 籍贯
    public String nationality;// 民族
    public String maritalStatus;// 婚姻状况
    public String liveCity;// 居住城市
    public String constellation;// 星座
    public String characteristicSignature;// 个性签名
    public String company;// 公司
    public String school;
    public String weixinName;// 微信号
    public int age;
    public int height;// 高度
    public int weight;// 体重
    public int validStatus;
    public long createTime;
    public long updateTime;
    public long birthday;
}
