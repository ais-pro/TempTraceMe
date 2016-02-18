package com.example.root.traceme;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Al Imran Suvro on 2/18/16.
 */
public class DbHelper extends SQLiteOpenHelper {
    public static final String dbName="location_info.db";

    public static final String locationTbl="location";
    public static final String lCol1="lID";
    public static final String lCol2="lat";
    public static final String lCol3="lng";
    public static final String lCol4="imei";
    public static final String lCol5="created_at";
    public static final String lCol6="accuracy";

    public DbHelper(Context context) {
        super(context, dbName, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table "+locationTbl+" ("+lCol1+" integer primary key autoincrement, "+lCol2+" text, "+lCol3+" text,"+lCol4+" text,"+lCol5+" text,"+lCol6+" text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+locationTbl);
        onCreate(db);
    }

    public long insertLocation(double lat, double lng, String imei, String dateTime, double accuracy){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(lCol2,lat);
        contentValues.put(lCol3,lng);
        contentValues.put(lCol4,imei);
        contentValues.put(lCol5, dateTime);
        contentValues.put(lCol6, accuracy);

        return db.insert(locationTbl,null,contentValues);

    }
}
