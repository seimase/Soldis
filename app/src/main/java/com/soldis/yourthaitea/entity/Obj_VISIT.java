package com.soldis.yourthaitea.entity;

import android.database.Cursor;
import android.util.Log;

import com.soldis.yourthaitea.AppConstant;

/**
 * Created by ftctest on 05/09/2017.
 */

public class Obj_VISIT {
    private String TMGO = "";
    private String TMBCK = "";
    private String TGL_TRX = "";

    public Obj_VISIT(){}

    public String getTMGO() {
        return TMGO;
    }

    public void setTMGO(String TMGO) {
        this.TMGO = TMGO;
    }

    public String getTMBCK() {
        return TMBCK;
    }

    public void setTMBCK(String TMBCK) {
        this.TMBCK = TMBCK;
    }

    public String getTGL_TRX() {
        return TGL_TRX;
    }

    public void setTGL_TRX(String TGL_TRX) {
        this.TGL_TRX = TGL_TRX;
    }

    public Obj_VISIT getData(){
        try{
            Cursor cur = AppConstant.myDb.rawQuery("SELECT * FROM TBL_VISIT", null);
            if (cur.getCount() > 0){
                cur.moveToFirst();
                this.TMGO = cur.getString(cur.getColumnIndex("TMGO"));
                this.TMBCK = cur.getString(cur.getColumnIndex("TMBCK"));
                this.TGL_TRX = cur.getString(cur.getColumnIndex("TGL_TRX"));
            }
            cur.close();
        }catch (Exception e){

        }

        return this;
    }

    public void CreateTimeStartVisit(Obj_VISIT dat){
        try {
            String sSql = "INSERT INTO TBL_VISIT(" +
                    "TMGO" +
                    ")VALUES(" +
                    "'"+dat.TMGO+"'" +
                    ")";

            AppConstant.myDb.execSQL(sSql);
        } catch (Exception e) {
            // TODO: handle exception
            Log.w("","");
        }
    }

    public void CreateTimeBackVisit(Obj_VISIT dat){

        try {
            String sSql = "UPDATE TBL_VISIT" +
                    "   SET TMBCK = '"+dat.TMBCK+"'," +
                    "   TGL_TRX = '"+dat.TGL_TRX+"'"
                    ;

            AppConstant.myDb.execSQL(sSql);
        } catch (Exception e) {
            // TODO: handle exception
            Log.w("","");
        }
    }

    public void DeleteData(){
        try {
            String sSql = "DELETE FROM TBL_VISIT";

            AppConstant.myDb.execSQL(sSql);
        } catch (Exception e) {
            // TODO: handle exception
            Log.w("","");
        }
    }
}
