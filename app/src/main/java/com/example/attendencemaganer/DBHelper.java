package com.example.attendencemaganer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Db1";
    private static final int DATABASE_VERSION = 1;


    public DBHelper(@Nullable Context applicationContext) {
        super(applicationContext, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE STUDENT(subject TEXT, attendedHours INTEGER, conductedHours INTEGER, totalHours FLOAT)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS STUDENT");


    }

    public void addSubject(String subName,int hrs) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        int i = 0;
        values.put("subject", subName);
        values.put("attendedHours", i);
        values.put("conductedHours", i);
        values.put("totalHours",hrs);
        db.insert("STUDENT", null, values);
        db.close();
    }

    public ArrayList<String> getSubjectNameHelper() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] colnames = {"subject", "attendedHours", "conductedHours","totalHours"};
        Cursor cursor = db.query("STUDENT", colnames, null, null, null, null, null);
        ArrayList<String> subs = new ArrayList<String>();
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            String fn = cursor.getString(cursor.getColumnIndexOrThrow("subject"));
            subs.add(fn);
        }
        return subs;
    }

    public ArrayList<Integer> getAttendedHelper() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] colnames = {"subject", "attendedHours", "conductedHours","totalHours"};
        Cursor cursor = db.query("STUDENT", colnames, null, null, null, null, null);
        ArrayList<Integer> attended = new ArrayList<Integer>();
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            int an = cursor.getInt(cursor.getColumnIndexOrThrow("attendedHours"));
            attended.add(an);
        }
        return attended;
    }

    public ArrayList<Integer> getConductedHelper() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] colnames = {"subject", "attendedHours", "conductedHours","totalHours"};
        Cursor cursor = db.query("STUDENT", colnames, null, null, null, null, null);
        ArrayList<Integer> conducted = new ArrayList<Integer>();
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            int cn = cursor.getInt(cursor.getColumnIndexOrThrow("conductedHours"));
            conducted.add(cn);
        }
        return conducted;
    }

    public int incrementAttendHelper(String s) {
        SQLiteDatabase dbRead = this.getReadableDatabase();
        SQLiteDatabase dbWrite = this.getWritableDatabase();

        String[] colnames = {"subject", "attendedHours", "conductedHours","totalHours"};
        Cursor cursor = dbRead.query("STUDENT", colnames, "subject=?", new String[]{s}, null, null, null);
        int an = 0;
        int cn = 0;
        if (cursor.moveToNext())
        {
            an= cursor.getInt(cursor.getColumnIndexOrThrow("attendedHours"));
            cn= cursor.getInt(cursor.getColumnIndexOrThrow("conductedHours"));
            an = an + 1;
            cn=cn+1;
            ContentValues values = new ContentValues();
            values.put("attendedHours",an);
            values.put("conductedHours",cn);
            dbWrite.update("STUDENT",values,"subject=?",new String[]{s});
        }
        return an;

    }

    public int incrementBunkHelper(String s) {
        SQLiteDatabase dbRead = this.getReadableDatabase();
        SQLiteDatabase dbWrite = this.getWritableDatabase();

        String[] colnames = {"subject", "attendedHours", "conductedHours","totalHours"};
        Cursor cursor = dbRead.query("STUDENT", colnames, "subject=?", new String[]{s}, null, null, null);
        int cn = 0;
        if (cursor.moveToNext())
        {
            cn= cursor.getInt(cursor.getColumnIndexOrThrow("conductedHours"));
            cn=cn+1;
            ContentValues values = new ContentValues();
            values.put("conductedHours",cn);
            dbWrite.update("STUDENT",values,"subject=?",new String[]{s});
        }
        return cn;
    }

    public void delSubjectHelper(String s) {
        SQLiteDatabase dbdelread=this.getReadableDatabase();
        SQLiteDatabase dbdelwrite=this.getWritableDatabase();

        dbdelwrite.delete("STUDENT","subject=?",new String[]{s});
    }

    public ArrayList<Integer> getTotalHoursHelper() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] colnames = {"subject", "attendedHours", "conductedHours","totalHours"};
        Cursor cursor = db.query("STUDENT", colnames, null, null, null, null, null);
        ArrayList<Integer> totalHours = new ArrayList<Integer>();
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            int an = cursor.getInt(cursor.getColumnIndexOrThrow("totalHours"));
            totalHours.add(an);
        }
        return totalHours;

    }
}
