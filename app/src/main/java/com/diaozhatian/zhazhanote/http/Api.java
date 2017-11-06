package com.diaozhatian.zhazhanote.http;

import com.audienl.superlibrary.utils.AndroidUtils;
import com.diaozhatian.zhazhanote.BuildConfig;
import com.diaozhatian.zhazhanote.Constants;
import com.diaozhatian.zhazhanote.annotation.CodeType;
import com.diaozhatian.zhazhanote.annotation.Gender;
import com.diaozhatian.zhazhanote.annotation.NoteType;
import com.diaozhatian.zhazhanote.base.App;
import com.diaozhatian.zhazhanote.bean.Folder;
import com.diaozhatian.zhazhanote.bean.Note;
import com.diaozhatian.zhazhanote.bean.User;
import com.diaozhatian.zhazhanote.manager.UserManager;

import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * 描述：接口类。
 * 返回的都是实体类，不需要再 result.data 等操作了。
 * <p>
 * Created by audienl@qq.com on 2017/10/11.
 */
public class Api {
    private static final String TAG = "Api";

    /** 新建文件夹 */
    public static Observable<HttpResult> createFolder(int userId, String folderName) {
        return Observable.create((ObservableOnSubscribe<HttpResult>) e -> {
            final RequestParams params = new RequestParams(Constants.URL_FOLDER_ADD);
            JSONObject obj = new JSONObject();
            obj.put("userId", userId);
            obj.put("name", folderName);
            params.setBodyContent(obj.toString());
            new HttpHelper<>(HttpResult.class, e, "新建文件夹").post(params);
        });
    }

    /**
     * 文件夹列表
     */
    public static Observable<List<Folder>> getFolderList(int page, int pageSize) {
        return Observable.create((ObservableOnSubscribe<Folder>) e -> {
            final RequestParams params = new RequestParams(Constants.URL_FOLDER_GET_FOLDER_LIST);
            JSONObject obj = new JSONObject();

            User user = UserManager.getLoginUser();
            if (user != null) obj.put("userId", String.valueOf(user.userId));

            obj.put("page", String.valueOf(page));
            obj.put("pageSize", String.valueOf(pageSize));
            params.setBodyContent(obj.toString());
            new HttpHelper<>(Folder.class, e, "文件夹列表").post(params);
        }).map(note -> note.dataList);
    }

    /**
     * 便签列表
     * @param folderId 文件夹ID，-1为所有
     * @param top      1为置顶，0为非置顶，-1为所有
     * @param finish   1为已完成，0为未完成，-1为所有
     */
    public static Observable<List<Note>> getNoteList(String tag, int folderId, boolean display, int top, int finish, int page, int pageSize) {
        return Observable.create((ObservableOnSubscribe<Note>) e -> {
            final RequestParams params = new RequestParams(Constants.URL_NOTE_GET_NOTE_LIST);
            JSONObject obj = new JSONObject();

            User user = UserManager.getLoginUser();
            if (user != null) obj.put("userId", String.valueOf(user.userId));

            if (folderId != -1) obj.put("folderId", folderId);
            if (top != -1) obj.put("top", top);
            if (finish != -1) obj.put("finish", finish);

            obj.put("display", display ? "1" : "0");
            obj.put("phoneUniqueCode", AndroidUtils.getDeviceUniqueId(App.instance));
            obj.put("page", String.valueOf(page));
            obj.put("pageSize", String.valueOf(pageSize));
            params.setBodyContent(obj.toString());
            new HttpHelper<>(Note.class, e, tag).post(params);
        }).map(note -> note.dataList);
    }

    /**
     * 便签列表
     * @param folderId 文件夹ID，-1为所有
     */
    public static Observable<List<Note>> getMainNoteList(int folderId, int page, int pageSize) {
        return getNoteList("便签列表", folderId, true, -1, -1, page, pageSize);
    }

    /**
     * 收藏夹列表
     */
    public static Observable<List<Note>> getFavorNoteList(int page, int pageSize) {
        return getNoteList("收藏夹列表", -1, true, 1, -1, page, pageSize);
    }

    /**
     * 已完成列表
     */
    public static Observable<List<Note>> getFinishedNoteList(int page, int pageSize) {
        return getNoteList("已完成列表", -1, true, -1, 1, page, pageSize);
    }

    /**
     * 回收站列表
     */
    public static Observable<List<Note>> getDeletedNoteList(int page, int pageSize) {
        return getNoteList("回收站列表", -1, false, -1, -1, page, pageSize);
    }

    /**
     * 计划置顶
     * @param top 1为置顶，0为取消置顶
     */
    public static Observable<HttpResult> setNoteTopStatus(int id, int top) {
        return Observable.create((ObservableOnSubscribe<HttpResult>) e -> {
            final RequestParams params = new RequestParams(Constants.URL_NOTE_SET_TOP);
            JSONObject obj = new JSONObject();
            User user = UserManager.getLoginUser();
            if (user != null) obj.put("userId", String.valueOf(user.userId));
            obj.put("id", String.valueOf(id));
            obj.put("top", String.valueOf(top));
            obj.put("phoneUniqueCode", AndroidUtils.getDeviceUniqueId(App.instance));
            params.setBodyContent(obj.toString());
            new HttpHelper<>(HttpResult.class, e, "计划置顶").post(params);
        });
    }

    /** 便签详细信息 */
    public static Observable<Note> getNoteDetail(int id) {
        return Observable.create((ObservableOnSubscribe<Note>) e -> {
            String url = String.format(Locale.CHINA, Constants.URL_NOTE_GET_NOTE_DETAIL, id);
            final RequestParams params = new RequestParams(url);
            new HttpHelper<>(Note.class, e, "便签详细信息").get(params);
        }).map(note -> note.data);
    }

    /** 删除便签 */
    public static Observable<HttpResult> deleteNote(int id) {
        return Observable.create((ObservableOnSubscribe<HttpResult>) e -> {
            String url = String.format(Locale.CHINA, Constants.URL_NOTE_DELETE_BY_ID, id);
            final RequestParams params = new RequestParams(url);
            new HttpHelper<>(HttpResult.class, e, "删除便签").get(params);
        });
    }

    /**
     * 设置完成
     * @param finish 1为已完成，0为未完成
     */
    public static Observable<HttpResult> setFinished(int id, int finish) {
        return Observable.create((ObservableOnSubscribe<HttpResult>) e -> {
            final RequestParams params = new RequestParams(Constants.URL_NOTE_SET_STATUS_BY_ID);
            JSONObject obj = new JSONObject();
            User user = UserManager.getLoginUser();
            if (user != null) obj.put("userId", String.valueOf(user.userId));
            obj.put("id", String.valueOf(id));
            obj.put("finish", String.valueOf(finish));
            obj.put("phoneUniqueCode", AndroidUtils.getDeviceUniqueId(App.instance));
            params.setBodyContent(obj.toString());
            new HttpHelper<>(HttpResult.class, e, "设置完成").post(params);
        });
    }

    /**
     * 修改便签
     * @param color #FFFFFF
     */
    public static Observable<HttpResult> updateNote(String noteId, String content, @NoteType String type, String color) {
        return Observable.create(e -> {
            final RequestParams params = new RequestParams(Constants.URL_NOTE_UPDATE);
            JSONObject obj = new JSONObject();
            User user = UserManager.getLoginUser();
            if (user != null) obj.put("userId", String.valueOf(user.userId));
            obj.put("id", noteId);
            obj.put("phoneUniqueCode", AndroidUtils.getDeviceUniqueId(App.instance));
            obj.put("content", content);
            obj.put("type", type);
            obj.put("color", color);
            params.setBodyContent(obj.toString());
            new HttpHelper<>(HttpResult.class, e, "修改便签").post(params);
        });
    }

    /**
     * 新增便签
     * @param color #FFFFFF
     */
    public static Observable<HttpResult> addNote(String userId, String content, @NoteType String type, String color) {
        return Observable.create(e -> {
            final RequestParams params = new RequestParams(Constants.URL_NOTE_ADD);
            JSONObject obj = new JSONObject();
            obj.put("userId", userId);
            obj.put("phoneUniqueCode", AndroidUtils.getDeviceUniqueId(App.instance));
            obj.put("content", content);
            obj.put("type", type);
            obj.put("color", color);
            params.setBodyContent(obj.toString());
            new HttpHelper<>(HttpResult.class, e, "新增便签").post(params);
        });
    }

    /** 个人资料 */
    public static Observable<User> getUserInfo(int userId, String token) {
        return Observable.create((ObservableOnSubscribe<User>) e -> {
            String url = String.format(Locale.CHINA, Constants.URL_USER_GET_MY_INFO_BY_ID, userId);
            final RequestParams params = new RequestParams(url);
            new HttpHelper<>(User.class, e, "个人资料").get(params);
        }).map(user -> {
            user = user.data;
            UserManager.saveLoginUser(user);
            return user;
        });
    }

    /** 记录版本信息 */
    public static Observable<HttpResult> updateVersionInfo(int userId) {
        return Observable.create(e -> {
            final RequestParams params = new RequestParams(Constants.URL_UPDATE_LOGIN_INFO);
            JSONObject obj = new JSONObject();
            obj.put("userId", userId);
            obj.put("apkVersion", BuildConfig.VERSION_CODE);
            obj.put("clientInfo", AndroidUtils.getDeviceUniqueId(App.instance));
            params.setBodyContent(obj.toString());
            new HttpHelper<>(HttpResult.class, e, "记录版本信息").post(params);
        });
    }

    /** 修改密码_验证码 */
    public static Observable<User> resetPasswordByPhoneCode(String mobile, String code, String password) {
        return Observable.create(e -> {
            final RequestParams params = new RequestParams(Constants.URL_AUTH_RESET_PASSWORD);
            JSONObject obj = new JSONObject();
            obj.put("mobile", mobile);
            obj.put("code", code);
            obj.put("password", ApiEncryptUtils.encrypt(password));
            obj.put("clientType", Constants.API_CLIENT_TYPE);
            params.setBodyContent(obj.toString());
            new HttpHelper<>(User.class, e, "修改密码_验证码").post(params);
        });
    }

    /** 修改密码 */
    public static Observable<User> resetPassword(int userId, String oldPassword, String newPassword) {
        return Observable.create(e -> {
            final RequestParams params = new RequestParams(Constants.URL_AUTH_CHANGE_PASSWORD);
            JSONObject obj = new JSONObject();
            obj.put("userId", String.valueOf(userId));
            obj.put("oldPassword", ApiEncryptUtils.encrypt(oldPassword));
            obj.put("newPassword", ApiEncryptUtils.encrypt(newPassword));
            obj.put("clientType", Constants.API_CLIENT_TYPE);
            params.setBodyContent(obj.toString());
            new HttpHelper<>(User.class, e, "修改密码").post(params);
        });
    }

    /**
     * 登录
     * @return {@link User} 不需要 user.data
     */
    public static Observable<User> login(String mobile, String password) {
        return Observable.create((ObservableOnSubscribe<User>) e -> {
            final RequestParams params = new RequestParams(Constants.URL_AUTH_LOGIN);
            JSONObject obj = new JSONObject();
            obj.put("mobile", mobile);
            obj.put("password", ApiEncryptUtils.encrypt(password));
            obj.put("clientType", Constants.API_CLIENT_TYPE);
            params.setBodyContent(obj.toString());
            new HttpHelper<>(User.class, e, "登录").post(params);
        });
    }

    /** 注册 */
    public static Observable<User> register(String mobile, String password, String smsCode) {
        return Observable.create(e -> {
            final RequestParams params = new RequestParams(Constants.URL_AUTH_REGISTER);
            JSONObject obj = new JSONObject();
            obj.put("mobile", mobile);
            obj.put("sex", Gender.FEMALE);
            obj.put("smsCode", smsCode);
            obj.put("clientType", Constants.API_CLIENT_TYPE);
            obj.put("password", ApiEncryptUtils.encrypt(password));
            params.setBodyContent(obj.toString());
            new HttpHelper<>(User.class, e, "注册").post(params);
        });
    }

    /** 获取验证码 */
    public static Observable<HttpResult> getCode(String mobile, @CodeType String type) {
        return Observable.create(e -> {
            final RequestParams params = new RequestParams(Constants.URL_SMS_CODE);
            params.addQueryStringParameter("mobile", mobile);
            params.addQueryStringParameter("type", type);
            new HttpHelper<>(HttpResult.class, e, "获取验证码").get(params);
        });
    }
}
