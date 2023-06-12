package com.soldis.yourthaitea.entity;

import android.database.Cursor;
import android.util.Log;

import com.soldis.yourthaitea.AppConstant;

import java.util.ArrayList;

/**
 * Created by ftctest on 25/08/2017.
 */

public class Obj_TYPEOUT {
    private String TYPEOUT = "";
    private String TYPENAME = "";
    private String GROUP_CODE = "";
    private String GROUP_NAME = "";

    public Obj_TYPEOUT(){}

    public String getGROUP_CODE() {
        return GROUP_CODE;
    }

    public void setGROUP_CODE(String GROUP_CODE) {
        this.GROUP_CODE = GROUP_CODE;
    }

    public String getGROUP_NAME() {
        return GROUP_NAME;
    }

    public void setGROUP_NAME(String GROUP_NAME) {
        this.GROUP_NAME = GROUP_NAME;
    }

    public String getTYPEOUT() {
        return TYPEOUT;
    }

    public void setTYPEOUT(String TYPEOUT) {
        this.TYPEOUT = TYPEOUT;
    }

    public String getTYPENAME() {
        return TYPENAME;
    }

    public void setTYPENAME(String TYPENAME) {
        this.TYPENAME = TYPENAME;
    }

    public Obj_TYPEOUT getData(String TYPEOUT){
        try{
            Cursor cur = AppConstant.myDb.rawQuery("SELECT * FROM TBL_TYPEOUT WHERE TYPEOUT = '"+TYPEOUT+"'", null);
            if (cur.getCount() > 0){
                cur.moveToFirst();
                this.TYPEOUT = cur.getString(cur.getColumnIndex("TYPEOUT"));
                this.TYPENAME = cur.getString(cur.getColumnIndex("TYPENAME"));
                this.GROUP_CODE = cur.getString(cur.getColumnIndex("GROUP_CODE"));
                this.GROUP_NAME = cur.getString(cur.getColumnIndex("GROUP_NAME"));
            }
            cur.close();
        }catch (Exception e){

        }

        return this;
    }

    public ArrayList<Obj_TYPEOUT> getDataList() {
        ArrayList<Obj_TYPEOUT> AlisData = new ArrayList<Obj_TYPEOUT>();

        try{
            String sSql = "SELECT * FROM TBL_TYPEOUT";
            Cursor cur = AppConstant.myDb.rawQuery(sSql, null);

            if (cur.getCount() > 0){
//			c.moveToFirst();
                while (cur.moveToNext()){
                    Obj_TYPEOUT prodDat = new Obj_TYPEOUT();
                    prodDat.TYPEOUT = cur.getString(cur.getColumnIndex("TYPEOUT"));
                    prodDat.TYPENAME = cur.getString(cur.getColumnIndex("TYPENAME"));
                    prodDat.GROUP_CODE = cur.getString(cur.getColumnIndex("GROUP_CODE"));
                    prodDat.GROUP_NAME = cur.getString(cur.getColumnIndex("GROUP_NAME"));
                    AlisData.add(prodDat);
                }

            }
            cur.close();

        }catch (Exception e){

        }
        return AlisData;
    }

    public ArrayList<Obj_TYPEOUT> getDataList(String GROUP_CODE) {
        ArrayList<Obj_TYPEOUT> AlisData = new ArrayList<Obj_TYPEOUT>();

        try{
            String sSql = "SELECT * FROM TBL_TYPEOUT" +
                    " WHERE GROUP_CODE = '"+GROUP_CODE+"'";
            Cursor cur = AppConstant.myDb.rawQuery(sSql, null);

            if (cur.getCount() > 0){
//			c.moveToFirst();
                while (cur.moveToNext()){
                    Obj_TYPEOUT prodDat = new Obj_TYPEOUT();
                    prodDat.TYPEOUT = cur.getString(cur.getColumnIndex("TYPEOUT"));
                    prodDat.TYPENAME = cur.getString(cur.getColumnIndex("TYPENAME"));
                    prodDat.GROUP_CODE = cur.getString(cur.getColumnIndex("GROUP_CODE"));
                    prodDat.GROUP_NAME = cur.getString(cur.getColumnIndex("GROUP_NAME"));
                    AlisData.add(prodDat);
                }

            }
            cur.close();

        }catch (Exception e){

        }
        return AlisData;
    }

    public ArrayList<Obj_TYPEOUT> getDataListGroup() {
        ArrayList<Obj_TYPEOUT> AlisData = new ArrayList<Obj_TYPEOUT>();

        try{
            String sSql = "SELECT GROUP_CODE,  GROUP_NAME FROM TBL_TYPEOUT" +
                    " GROUP BY GROUP_CODE,  GROUP_NAME";
            Cursor cur = AppConstant.myDb.rawQuery(sSql, null);

            if (cur.getCount() > 0){
//			c.moveToFirst();
                while (cur.moveToNext()){
                    Obj_TYPEOUT prodDat = new Obj_TYPEOUT();
                    prodDat.GROUP_CODE = cur.getString(cur.getColumnIndex("GROUP_CODE"));
                    prodDat.GROUP_NAME = cur.getString(cur.getColumnIndex("GROUP_NAME"));
                    AlisData.add(prodDat);
                }

            }
            cur.close();

        }catch (Exception e){

        }
        return AlisData;
    }

    public void CreateData(Obj_TYPEOUT dat){
        try{
            String sSql = "INSERT INTO TBL_TYPEOUT(" +
                    "TYPEOUT," +
                    "TYPENAME," +
                    "GROUP_CODE," +
                    "GROUP_NAME" +
                    ")VALUES(" +
                    "'"+dat.getTYPEOUT()+"'," +
                    "'"+dat.getTYPENAME()+"'," +
                    "'"+dat.getGROUP_CODE()+"'," +
                    "'"+dat.getGROUP_NAME()+"')";
                    ;


            AppConstant.myDb.execSQL(sSql);
        }catch (Exception e){
            Log.w("Insert Tbl_TYPEOUT", e.getMessage());
        }
    }

    public void DeleteData(){
        try{
            String sSql = "DELETE FROM Tbl_TYPEOUT";

            AppConstant.myDb.execSQL(sSql);
        }catch (Exception e){
            Log.w("Insert Tbl_TYPEOUT", e.getMessage());
        }
    }
}
