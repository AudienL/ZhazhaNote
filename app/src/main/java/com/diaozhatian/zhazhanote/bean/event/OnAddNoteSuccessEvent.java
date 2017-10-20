package com.diaozhatian.zhazhanote.bean.event;

import com.diaozhatian.zhazhanote.annotation.NoteType;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/10/20.
 */
public class OnAddNoteSuccessEvent {
    public String noteType;

    public OnAddNoteSuccessEvent(@NoteType String noteType) {
        this.noteType = noteType;
    }
}
