package ng.com.africasupport.alcwithgoogle;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

//The database class
public class DatabaseAccess {
    private DatabaseOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    //First constructor
    public DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    //Second constructor
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

//    Check for a value in the database
    public List<Event> checkWithDate(String date) {

        List<Event> eventList = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM EventTable WHERE date LIKE" + "'%" + date + "%'", null);
        cursor.moveToFirst();

        while (!(cursor.isAfterLast())) {

            eventList.add(new Event(cursor.getString(cursor.getColumnIndex("date")),
                    cursor.getString(cursor.getColumnIndex("event"))));
            cursor.moveToNext();
        }

        cursor.close();
        return eventList;
    }

//    Add a value to the database
    public boolean add(String date, String event) {
        try {

            //Adding a hymn to the favorites table
            ContentValues cv = new ContentValues();
            cv.put("date", date);
            cv.put("event", event);

            database.insert("EventTable", null, cv);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

//    Update a value in the database
    public boolean updateEvents(String editedtext, String date) {
        try {
            ContentValues cv = new ContentValues();
            cv.put("event", editedtext);
            database.update("EventTable", cv, "date LIKE" + "'%" + date + "%'", null);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

//    Delete a value from the database
    public boolean delete(String date) {
        try {
            database.delete("EventTable", "date =" + "'" + date + "'", null);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean storeFont(int fontIndex) {
        try {
            ContentValues cv = new ContentValues();
            cv.put("Font", fontIndex);
            database.update("Settings", cv, null, null);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getFont() {
        Cursor cursor = database.rawQuery("SELECT Font FROM Settings", null);
        cursor.moveToFirst();
        int fontIndex = cursor.getInt(cursor.getColumnIndex("Font"));
        cursor.moveToNext();
        cursor.close();
        return fontIndex;
    }
}
