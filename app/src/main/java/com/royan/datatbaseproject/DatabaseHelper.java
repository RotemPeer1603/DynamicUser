package com.royan.datatbaseproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "contacts_db.db";
    public static final String TABLE_NAME = "SUPER_CONTACTS";
    public static final String COL_1_ID = "ID";
    public static final String COL_TITLE = "title";
    public static final String COL_FIRST = "first";
    public static final String COL_LAST = "last";
    public static final String COL_GENDER = "gender";
    public static final String COL_STREET = "street";
    public static final String COL_CITY = "city";
    public static final String COL_COUNTRY = "country";
    public static final String COL_POSTCODE = "postcode";


    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("create table ").append(TABLE_NAME);
        sb.append("(");
        sb.append(COL_1_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,");
        sb.append(COL_TITLE).append(" TEXT,");
        sb.append(COL_FIRST).append(" TEXT,");
        sb.append(COL_LAST).append(" TEXT,");
        sb.append(COL_GENDER).append(" TEXT,");
        sb.append(COL_STREET).append(" TEXT,");
        sb.append(COL_CITY).append(" TEXT,");
        sb.append(COL_COUNTRY).append(" TEXT,");
        sb.append(COL_POSTCODE).append(" TEXT");
        sb.append(")");
        sqLiteDatabase.execSQL(sb.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        // when updragin your software
        // and you need to run some sql queries
        // sql query to add paypal column
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(User user)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_TITLE, user.getTitle());
        contentValues.put(COL_FIRST, user.getFirst());
        contentValues.put(COL_LAST, user.getLast());
        contentValues.put(COL_GENDER, user.getGender());
        contentValues.put(COL_STREET, user.getStreet());
        contentValues.put(COL_CITY, user.getCity());
        contentValues.put(COL_COUNTRY, user.getCountry());
        contentValues.put(COL_POSTCODE, user.getPostcode());
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
        {
            return false;
        }
        return true;
    }

    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        return cursor;
    }

    public int deleteUser(User user)
    {
        String firstName = user.getFirst();
        String lastName = user.getLast();
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "first = ? and last = ?", new String[]{firstName,lastName});
    }

    public Cursor getSpecificById(String id)
    {
        return null;
    }

    public User getUser(String first, String last)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String strSql = "select * from " + TABLE_NAME;
        strSql += " Where first = '" + first + "'";
        strSql += " and last = '" + last + "'";
        Cursor cursor = db.rawQuery(strSql, null);


        User user = null;
        if (cursor.moveToNext())
        {
            String title = cursor.getString(1);
            String gender = cursor.getString(4);
            String street = cursor.getString(5);
            String city = cursor.getString(6);
            String country = cursor.getString(7);
            String postcode = cursor.getString(8);
            user = new User(title, first, last, gender, street, city, country, postcode);

        }
        return user;
    }

    public int updateUser(User user, String first, String last)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_TITLE, user.getTitle());
        values.put(COL_FIRST, user.getFirst());
        values.put(COL_LAST, user.getLast());
        values.put(COL_GENDER, user.getGender());
        values.put(COL_STREET, user.getStreet());
        values.put(COL_CITY, user.getCity());
        values.put(COL_COUNTRY, user.getCountry());
        values.put(COL_POSTCODE, user.getPostcode());

        String[] strArr = new String[2];
        strArr[0] = String.valueOf(first);
        strArr[1] = String.valueOf(last);

        return db.update(TABLE_NAME, values, "first = ? and last = ?", strArr);

    }
}