package com.rccorp.x_note;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class CustomAdapter extends CursorAdapter implements AdapterView.OnItemClickListener{
    protected Context mContext;

    public CustomAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        mContext = context;
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.custom_row, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView textView = (TextView) view.findViewById(R.id.textview5);
        String body = cursor.getString(cursor.getColumnIndexOrThrow(XNoteContract.NoteEntry.COLUMN_NAME_TITLE));
        // Populate fields with extracted properties
        textView.setText(body);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Cursor cursor = (Cursor) getItem(position);
        int xnoteId = cursor.getInt(cursor.getColumnIndexOrThrow(XNoteContract.NoteEntry._ID));
        String title = cursor.getString(cursor.getColumnIndexOrThrow(XNoteContract.NoteEntry.COLUMN_NAME_TITLE));
        String descr =cursor.getString(cursor.getColumnIndexOrThrow(XNoteContract.NoteEntry.COLUMN_NAME_DESCRIPTION));
        Intent intent = new Intent(mContext, NoteTaker.class);
        intent.putExtra("Title", title);
        intent.putExtra("Note", descr);
        intent.putExtra("updt",xnoteId);
        mContext.startActivity(intent);





    }

}