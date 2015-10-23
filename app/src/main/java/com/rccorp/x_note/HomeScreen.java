package com.rccorp.x_note;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class HomeScreen extends ActionBarActivity {

    private AlertDialog.Builder build;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        //NoteReaderDbHelper  is a SQLiteOpenHelper class connecting to SQLite







    }

    @Override
    protected void onResume() {
        super.onResume();
        NoteReaderDbHelper handler = new NoteReaderDbHelper(this);
// Get access to the underlying writeable database
        final SQLiteDatabase db = handler.getWritableDatabase();
// Query for items from the database and get a cursor back
        Cursor todoCursor = db.rawQuery("SELECT  * FROM "+ XNoteContract.NoteEntry.TABLE_NAME, null);
        final ListView listview = (ListView) findViewById(R.id.listView2);
// Setup cursor adapter using cursor from last step
        final CustomAdapter todoAdapter = new CustomAdapter(this, todoCursor);
// Attach cursor adapter to the ListView
        listview.setAdapter(todoAdapter);
        listview.setOnItemClickListener(todoAdapter);


        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final Cursor cursor = (Cursor) listview.getAdapter().getItem(position);

                int noteId = cursor.getInt(cursor.getColumnIndexOrThrow(XNoteContract.NoteEntry._ID));
                final String selection = XNoteContract.NoteEntry._ID + " LIKE ?";
                final String[] selectionArgs = {String.valueOf(noteId) };
                build =new AlertDialog.Builder(HomeScreen.this);
                build.setTitle("Delete the Note?" );
                build.setMessage("Are you Sure?");
                build.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.delete(
                                XNoteContract.NoteEntry.TABLE_NAME,
                                selection,
                                selectionArgs
                        );
                        Cursor cursor2=db.rawQuery("SELECT  * FROM "+ XNoteContract.NoteEntry.TABLE_NAME, null);
                        todoAdapter.swapCursor(cursor2);
                        todoAdapter.notifyDataSetChanged();
                    }
                });

                build.create().show();

                return true;
            }
        });

    }

    public void onClick2(View view){
        Intent i=new Intent(this,NoteTaker.class);
        startActivity(i);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,ContextMenu.ContextMenuInfo menuInfo){
        //super.onCreateContextMenu(menu,view,menuInfo);
        menu.setHeaderTitle("Note Options");
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);


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






}
