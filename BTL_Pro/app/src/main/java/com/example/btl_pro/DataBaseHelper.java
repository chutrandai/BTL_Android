package com.example.btl_pro;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "qlbh_utc.sqlite";
    private static final int DATABASE_VERSION = 1;
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null  , DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE user (\n" +
                "    userName VARCHAR (100)   UNIQUE\n" +
                "                          NOT NULL,\n" +
                "    passWord   VARCHAR (30)  NOT NULL,\n" +
                "    status   INTEGER\n" +
                ");\n";
        db.execSQL(sql);

        sql = "INSERT INTO user (userName, passWord, status) VALUES ('daict', '1', 0)";
        db.execSQL(sql);
        sql = "INSERT INTO user (userName, passWord, status) VALUES ('hungnh', '1', 0)";
        db.execSQL(sql);
        sql = "INSERT INTO user (userName, passWord, status) VALUES ('admin', '1', 0)";
        db.execSQL(sql);

        sql = "CREATE TABLE product (\n" +
                "    product_id   INTEGER  UNIQUE NOT NULL,\n" +
                "    namePro VARCHAR (100) NOT NULL,\n" +
                "    namePer VARCHAR (100) NOT NULL,\n" +
                "    phone   VARCHAR (11)  NOT NULL,\n" +
                "    cost   INTEGER  NOT NULL,\n" +
                "    srcImage   VARCHAR (100)  NOT NULL,\n" +
                "    status   INTEGER\n" +
                ");\n";
        db.execSQL(sql);
//        sql = "INSERT INTO product (product_id, namePro, namePer, phone, cost, status) " +
//                "VALUES (1, 'Quat may', 'admin', '0972475812', 200000, null, 1)";
//        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
