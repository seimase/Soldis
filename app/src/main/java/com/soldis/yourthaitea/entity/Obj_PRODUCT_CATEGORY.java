package com.soldis.yourthaitea.entity;

import android.database.Cursor;
import android.util.Log;

import com.soldis.yourthaitea.AppConstant;

import java.util.ArrayList;

/**
 * Created by ftctest on 25/08/2017.
 */

public class Obj_PRODUCT_CATEGORY {
    private String CATEGORY_ID = "";
    private String CATEGORY_NAME = "";

    public Obj_PRODUCT_CATEGORY(){}

    public String getCATEGORY_ID() {
        return CATEGORY_ID;
    }

    public void setCATEGORY_ID(String CATEGORY_ID) {
        this.CATEGORY_ID = CATEGORY_ID;
    }

    public String getCATEGORY_NAME() {
        return CATEGORY_NAME;
    }

    public void setCATEGORY_NAME(String CATEGORY_NAME) {
        this.CATEGORY_NAME = CATEGORY_NAME;
    }

    public Obj_PRODUCT_CATEGORY getData(String CATEGORY_ID){
        try{
            Cursor cur = AppConstant.myDb.rawQuery("SELECT * FROM TBL_PRODUCT_CATEGORY WHERE CATEGORY_ID = '"+CATEGORY_ID+"'", null);
            if (cur.getCount() > 0){
                cur.moveToFirst();
                this.CATEGORY_ID = cur.getString(cur.getColumnIndex("CATEGORY_ID"));
                this.CATEGORY_NAME = cur.getString(cur.getColumnIndex("CATEGORY_NAME"));            }
            cur.close();
        }catch (Exception e){

        }

        return this;
    }

    public ArrayList<Obj_PRODUCT_CATEGORY> getDataList() {
        ArrayList<Obj_PRODUCT_CATEGORY> AlisData = new ArrayList<Obj_PRODUCT_CATEGORY>();

        try{
            String sSql = "SELECT * FROM TBL_PRODUCT_CATEGORY";
            Cursor cur = AppConstant.myDb.rawQuery(sSql, null);

            if (cur.getCount() > 0){
//			c.moveToFirst();
                while (cur.moveToNext()){
                    Obj_PRODUCT_CATEGORY prodDat = new Obj_PRODUCT_CATEGORY();
                    prodDat.CATEGORY_ID = cur.getString(cur.getColumnIndex("CATEGORY_ID"));
                    prodDat.CATEGORY_NAME = cur.getString(cur.getColumnIndex("CATEGORY_NAME"));
                    AlisData.add(prodDat);
                }

            }
            cur.close();

        }catch (Exception e){

        }
        return AlisData;
    }

    public void CreateDate(Obj_PRODUCT_CATEGORY dat){
        try{
            String sSql = "INSERT INTO TBL_PRODUCT_CATEGORY(" +
                    "CATEGORY_ID," +
                    "CATEGORY_NAME" +
                    ")VALUES(" +
                    "'"+dat.getCATEGORY_ID()+"'," +
                    "'"+dat.getCATEGORY_NAME()+"')";

            AppConstant.myDb.execSQL(sSql);
        }catch (Exception e){
            Log.w("Insert Tbl_PRLIN", e.getMessage());
        }
    }

    public void DeleteData(){
        String sSql = "";
        try {
            sSql = "DELETE FROM TBL_PRODUCT_CATEGORY ";
            AppConstant.myDb.execSQL(sSql);


        } catch (Exception e) {
            // TODO: handle exception
            Log.i("", e.getMessage());
        }
    }

}
