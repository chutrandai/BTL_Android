package com.example.btl_pro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.facebook.Profile;

import java.util.ArrayList;

public class productActionDB {
    Context context;
    SQLiteDatabase mDB;

    public productActionDB(Context context) {
        this.context = context;
        // mo ket noi
        DataBaseHelper helper = new DataBaseHelper(context);
        // tham chieu den doi tuong thao tac voi db
        mDB = helper.getWritableDatabase();
    }

    // select
    public ArrayList<productBean> processSearch() {
        ArrayList<productBean> productList = new ArrayList<>();
        StringBuilder sqlQuery = new StringBuilder(" select * from product ");
        Cursor c = mDB.rawQuery(sqlQuery.toString(), null);
        while (c.moveToNext()){
            Integer productId = c.getInt(c.getColumnIndex("product_id"));
            String namePro = c.getString(c.getColumnIndex("namePro"));
            String namePer = c.getString(c.getColumnIndex("namePer"));
            String phone = c.getString(c.getColumnIndex("phone"));
            Integer cost = c.getInt(c.getColumnIndex("cost"));
            String srcImage = c.getString(c.getColumnIndex("srcImage"));
            Integer statusDB = c.getInt(c.getColumnIndex("status"));
            boolean status;
            if (statusDB.equals(1)) {
                status = true;
            } else {
                status = false;
            }
            productBean product = new productBean(productId, namePro, namePer, phone, cost, srcImage, status);
            productList.add(product);
        }
        return productList;
    }

    // insert
    public long insert(productBean product) {
        if (commonUtil.isNullOrEmpTy(product.getNamePer())) {
            Profile profile = Profile.getCurrentProfile();
            if (profile != null) {
                commonUtil.userName = profile.getName();
            }
        }
        ContentValues values = new ContentValues();
        values.put("product_id", product.getProductId());
        values.put("namePro", product.getNamePro());
        values.put("namePer", commonUtil.userName);
        values.put("phone", product.getPhone());
        values.put("cost", product.getCost());
        values.put("srcImage", product.getSrcImage());
        values.put("status", 1);
        long newId = mDB.insert("product", null, values);
        return newId;
    }
    // update
    public long update(productBean productBean) {
        ContentValues values = new ContentValues();
        values.put("namePro", productBean.getNamePro());
        values.put("phone", productBean.getPhone());
        values.put("cost", productBean.getCost());
        values.put("srcImage", productBean.getSrcImage());
        String strCondition = " namePro = ? AND srcImage = ? AND phone = ?";
        String paramList[] = {productBean.getNamePro(), productBean.getSrcImage(), productBean.getPhone()};
        long newId = mDB.update("product", values, strCondition, paramList);
        return newId;
    }
    // delete
    public void delete(int product_id) {
        String strCondition = " product_id = ? ";
        String paramList[] = {String.valueOf(product_id)};
        int row = mDB.delete("product", strCondition, paramList);
    }

    // get id of new product
    public int getIdNewProduct() {
//        Cursor c = mDB.query("product", new String[] {"product_id"},
//                "product_id" + " =  select MAX (product_id) from product ",
//                null, null, null, null);
//        int id = c.getInt(c.getColumnIndex("product_id"));
        Cursor cursor = mDB.rawQuery("SELECT MAX("+"product_id"+") FROM "+"product", null);
        int maxid = (cursor.moveToFirst() ? cursor.getInt(0) : 0);
        return maxid+1;
    }

}
