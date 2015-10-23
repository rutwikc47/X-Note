package com.rccorp.x_note;


import android.provider.BaseColumns;

public final class XNoteContract {
    public XNoteContract() {
    }


    public static abstract class NoteEntry implements BaseColumns {
        public static final String TABLE_NAME = "Note";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
    }


}