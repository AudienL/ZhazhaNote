package com.diaozhatian.zhazhanote.bean;

import com.diaozhatian.zhazhanote.http.HttpResult;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/11/6.
 */
public class Folder extends HttpResult<Folder> {
    public int id;
    public int validStatus;// 1为有效，0为无效
    public int systemFolder;// 1为是，0为否
    public long createTime;
    public long updateTime;
    public String name;

    public boolean isSelected = false;
}
