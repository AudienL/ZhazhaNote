package com.diaozhatian.zhazhanote.bean;

import com.diaozhatian.zhazhanote.annotation.NoteType;
import com.diaozhatian.zhazhanote.http.HttpResult;

import java.util.Date;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/10/18.
 */
public class Note extends HttpResult<Note> {
    public int id;
    public int validStatus;// 1为有效，0为无效
    public int top;// 1为置顶，0非置顶
    @NoteType public String type;
    public String color;// #FFFFFF
    public String userCode;
    public String userName;
    public String phone;
    public String phoneUniqueCode;
    public String content;
    public Date createTime;
    public Date updateTime;
}
