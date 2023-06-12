package com.soldis.yourthaitea.entity;

import android.database.Cursor;
import android.util.Log;

import com.soldis.yourthaitea.AppConstant;

import java.util.ArrayList;

/**
 * Created by ftctest on 25/08/2017.
 */

public class Obj_PRLIN {
    private String PRLIN = "";
    private String PRLINAME = "";
    private String KOMPFLAG = "";

    public Obj_PRLIN(){}

    public String getPRLIN() {
        return PRLIN;
    }

    public void setPRLIN(String PRLIN) {
        this.PRLIN = PRLIN;
    }

    public String getPRLINAME() {
        return PRLINAME;
    }

    public void setPRLINAME(String PRLINAME) {
        this.PRLINAME = PRLINAME;
    }

    public String getKOMPFLAG() {
        return KOMPFLAG;
    }

    public void setKOMPFLAG(String KOMPFLAG) {
        this.KOMPFLAG = KOMPFLAG;
    }

    public Obj_PRLIN getData(String PRLIN){
        try{
            Cursor cur = AppConstant.myDb.rawQuery("SELECT * FROM FPRLIN WHERE PRLIN = '"+PRLIN+"'", null);
            if (cur.getCount() > 0){
                cur.moveToFirst();
                this.PRLIN = cur.getString(cur.getColumnIndex("PRLIN"));
                this.PRLINAME = cur.getString(cur.getColumnIndex("PRLINAME"));
                this.KOMPFLAG = cur.getString(cur.getColumnIndex("KOMPFLAG"));
            }
            cur.close();
        }catch (Exception e){

        }

        return this;
    }

    public ArrayList<Obj_PRLIN> getDataList() {
        ArrayList<Obj_PRLIN> AlisData = new ArrayList<Obj_PRLIN>();

        try{
            String sSql = "SELECT * FROM Tbl_FPRLIN";
            Cursor cur = AppConstant.myDb.rawQuery(sSql, null);

            if (cur.getCount() > 0){
//			c.moveToFirst();
                while (cur.moveToNext()){
                    Obj_PRLIN prodDat = new Obj_PRLIN();
                    prodDat.PRLIN = cur.getString(cur.getColumnIndex("PRLIN"));
                    prodDat.PRLINAME = cur.getString(cur.getColumnIndex("PRLINAME"));
                    prodDat.KOMPFLAG = cur.getString(cur.getColumnIndex("KOMPFLAG"));
                    AlisData.add(prodDat);
                }

            }
            cur.close();

        }catch (Exception e){

        }
        return AlisData;
    }

    public void CreateDate(Obj_PRLIN dat){
        try{
            String sSql = "INSERT INTO Tbl_PRLIN(" +
                    "PRLIN," +
                    "PRLINAME," +
                    "KOMPFLAG" +
                    ")VALUES(" +
                    "'"+dat.getPRLIN()+"'," +
                    "'"+dat.getPRLINAME()+"'," +
                    "'"+dat.getKOMPFLAG()+"')";

            AppConstant.myDb.execSQL(sSql);
        }catch (Exception e){
            Log.w("Insert Tbl_PRLIN", e.getMessage());
        }
    }


}
