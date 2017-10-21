package com.diaozhatian.zhazhanote.bean.event;

import com.diaozhatian.zhazhanote.annotation.NoteType;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/10/21.
 */
public class RequestRefreshNoteListEvent {
    public String noteType;

    public RequestRefreshNoteListEvent(@NoteType String noteType) {
        this.noteType = noteType;
    }
}
