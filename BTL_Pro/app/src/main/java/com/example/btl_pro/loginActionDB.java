package com.example.btl_pro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class loginActionDB {
    Context context;
    SQLiteDatabase mDB;

    public loginActionDB(Context context) {
        this.context = context;
        // mo ket noi
        DataBaseHelper helper = new DataBaseHelper(context);
        // tham chieu den doi tuong thao tac voi db
        mDB = helper.getWritableDatabase();
    }

    // select
    public ArrayList<userBean> processSearch() {
        ArrayList<userBean> userList = new ArrayList<>();
        StringBuilder sqlQuery = new StringBuilder(" select * from user ");
        Cursor c = mDB.rawQuery(sqlQuery.toString(), null);
        while (c.moveToNext()){
            String userName = c.getString(c.getColumnIndex("userName"));
            String passWord = c.getString(c.getColumnIndex("passWord"));
            userBean user = new userBean(userName, passWord);
            userList.add(user);
        }
        return userList;
    }

    // select condition like name
    public ArrayList<userBean> processSearch(String strName) {
        ArrayList<userBean> tbList = new ArrayList<>();
        Cursor c = mDB.query("user", new String[] {"userName", "passWord"},
                "userName" + " LIKE '%" + strName + "'%",
                null, null, null, null);
        while (c.moveToNext()){
            String userName = c.getString(c.getColumnIndex("userName"));
            String passWord = c.getString(c.getColumnIndex("passWord"));
            userBean user = new userBean(userName, passWord);
            tbList.add(user);
        }
        return tbList;
    }

    // insert
    public long insert(userBean user) {
        ContentValues values = new ContentValues();
        values.put("userName", user.getUserName());
        values.put("passWord", user.getPassWord());
        values.put("status", 0);
        long newId = mDB.insert("user", null, values);
        return newId;
    }
    // update
    public void update(userBean user) {
        ContentValues values = new ContentValues();
        values.put("userName", user.getUserName());
        values.put("passWord", user.getPassWord());
        values.put("status", 0);
        String strCondition = " userName = ? ";
        String paramList[] = {String.valueOf(user.getUserName())};
        long newId = mDB.update("user", values, strCondition, paramList);
    }

    // findByid
//    public thietbi findById(int tbId) {
//        thietbi tb = new thietbi();
//        StringBuilder sqlQuery = new StringBuilder(" select * from thietbi where id = "
//                + String.valueOf(tbId));
//        Cursor c = mDB.rawQuery(sqlQuery.toString(), null);
//        while (c.moveToNext()){
//            int id = c.getColumnIndex("id");
//            String ten = c.getString(c.getColumnIndex("ten"));
//            String thongso = c.getString(c.getColumnIndex("thongso"));
//            int trangthai = c.getColumnIndex("trangthai");
//            boolean checked = trangthai == 1? true : false;
//            tb = new thietbi(id, ten, thongso, checked);
//        }
//        return tb;
//    }

    // delete
    public void delete(String name) {
        String strCondition = " userName = ? ";
        String paramList[] = {name};
        int row = mDB.delete("user", strCondition, paramList);
    }
}
