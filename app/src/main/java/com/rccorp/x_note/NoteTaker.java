package com.rccorp.x_note;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class NoteTaker extends ActionBarActivity {
    private NoteReaderDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_taker);

        mDbHelper = new NoteReaderDbHelper(this);



        Intent intent=getIntent();
        String title=intent.getStringExtra("Title");
        String note=intent.getStringExtra("Note");
        final int xnote=intent.getIntExtra("updt",-1);
        final EditText tackytitle=(EditText)findViewById(R.id.tackyTitle);
        EditText tackynote=(EditText)findViewById(R.id.tackyNote);
        tackytitle.setText(title);
        tackynote.setText(note);

        final EditText titleEditText = (EditText) findViewById(R.id.tackyTitle);
        final EditText descEditText = (EditText) findViewById(R.id.tackyNote);
        Button button = (Button) findViewById(R.id.tackyButton3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = titleEditText.getText().toString();
                String description = descEditText.getText().toString();
                Log.d("NoteTaker", "Title : " + title);
                Log.d("NoteTaker", "Desc : " + description);
                if (TextUtils.isEmpty(title) || title.length() < 1)
                {
                    titleEditText.setError("This Field cannot be Empty");
                    return;
                }
                SQLiteDatabase db = mDbHelper.getWritableDatabase();

                String selection = XNoteContract.NoteEntry._ID + " LIKE ?";
                String[] selectionArgs = {String.valueOf(xnote)};
                ContentValues contentValues = new ContentValues();
                // TODO write to database
                contentValues.put(XNoteContract.NoteEntry.COLUMN_NAME_TITLE, title);
                contentValues.put(XNoteContract.NoteEntry.COLUMN_NAME_DESCRIPTION, description);
                if(xnote==-1){
                    db.insert(XNoteContract.NoteEntry.TABLE_NAME,null,contentValues);
                } else {
                    contentValues.put(XNoteContract.NoteEntry._ID,xnote);
                    db.update(XNoteContract.NoteEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                }

                finish();





            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note_taker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Go Back?")
                .setMessage("Unsaved Data will be Lost")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        NoteTaker.super.onBackPressed();
                    }
                }).create().show();
    }
}
