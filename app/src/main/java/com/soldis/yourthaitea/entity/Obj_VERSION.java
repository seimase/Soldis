package com.soldis.yourthaitea.entity;

import android.database.Cursor;

import com.soldis.yourthaitea.AppConstant;

/**
 * Created by ftctest on 22/09/2017.
 */

public class Obj_VERSION {
    private String VERSION_DB = "";

    public Obj_VERSION(){}

    public String getVERSION_DB() {
        return VERSION_DB;
    }

    public void setVERSION_DB(String VERSION_DB) {
        this.VERSION_DB = VERSION_DB;
    }

    public Obj_VERSION getData(){
        try{
            Cursor cur = AppConstant.myDb.rawQuery("SELECT * FROM TBL_VERSION", null);
            if (cur.getCount() > 0){
                cur.moveToFirst();
                this.VERSION_DB = cur.getString(cur.getColumnIndex("VERSION_DB"));
            }
            cur.close();
        }catch (Exception e){

        }

        return this;
    }
}
