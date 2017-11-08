package com.diaozhatian.zhazhanote;

/**
 * 常量
 * Created by feiwei on 2016/5/20.
 */
public class Constants {
    public static String HOST;

    static {
        if (BuildConfig.DEBUG) {
            // PC直接安装
            HOST = "https://hellogood.top/hellogood_api/";

        } else {
            // 打包-发布
            HOST = "https://hellogood.top/hellogood_api/";
        }
    }

    public final static String API_CLIENT_TYPE = "Android";

    public final static String URL_SMS_CODE = HOST + "sms/code.do";// 获取验证码
    public final static String URL_AUTH_REGISTER = HOST + "auth/register.do";// 注册
    public final static String URL_AUTH_LOGIN = HOST + "auth/login.do";// 登录
    public final static String URL_AUTH_CHANGE_PASSWORD = HOST + "auth/changePassword.do";// 修改密码
    public final static String URL_AUTH_RESET_PASSWORD = HOST + "auth/resetPassword.do";// 修改密码_验证码
    public final static String URL_UPDATE_LOGIN_INFO = HOST + "auth/updateLoginInfo.do";// 记录版本信息
    public final static String URL_USER_GET_MY_INFO_BY_ID = HOST + "user/getMyInfoById/%d.do";// 个人资料，参数：userId

    public final static String URL_FOLDER_ADD = HOST + "folder/add.do";// 新建文件夹
    public final static String URL_FOLDER_GET_FOLDER_LIST = HOST + "folder/getFolderList.do";// 文件夹列表
    public final static String URL_NOTE_GET_NOTE_LIST = HOST + "note/getNoteList.do";// 便签列表
    public final static String URL_NOTE_ADD = HOST + "note/add.do";// 新增便签
    public final static String URL_NOTE_UPDATE = HOST + "note/update.do";// 修改便签
    public final static String URL_NOTE_SET_STATUS_BY_ID = HOST + "note/setFinish.do";// 设置完成
    public final static String URL_NOTE_DELETE_BY_ID = HOST + "note/deleteById/%d.do";// 删除便签，参数：id
    public final static String URL_NOTE_GET_NOTE_DETAIL = HOST + "note /get/%d.do";// 便签详细信息，参数：id
    public final static String URL_NOTE_SET_TOP = HOST + "note/setTop.do";// 计划置顶
}
