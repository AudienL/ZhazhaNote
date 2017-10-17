package com.diaozhatian.zhazhanote.http;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/10/13.
 */
public class HttpResult {
    private static final String SUCCESS = "success";
    private static final String FAILED = "failed";
    private static final String ERROR = "error";

    public String status;
    public String message;// 错误描述
    public Object data;
    public Object dataList;

    public boolean isSuccess() {
        return SUCCESS.equals(status);
    }
}
