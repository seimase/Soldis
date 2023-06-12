package com.soldis.yourthaitea.entity;

import android.database.Cursor;
import android.util.Log;

import com.soldis.yourthaitea.AppConstant;

import java.util.ArrayList;

/**
 * Created by ftctest on 04/09/2017.
 */

public class Obj_STOCK_TMP {
    private String PCODE = "";
    private int STOCK = 0;

    public Obj_STOCK_TMP(){}

    public String getPCODE() {
        return PCODE;
    }

    public void setPCODE(String PCODE) {
        this.PCODE = PCODE;
    }

    public int getSTOCK() {
        return STOCK;
    }

    public void setSTOCK(int STOCK) {
        this.STOCK = STOCK;
    }

    public ArrayList<Obj_STOCK_TMP> getDataList() {
        ArrayList<Obj_STOCK_TMP> AlisData = new ArrayList<Obj_STOCK_TMP>();

        try{
            String sSql = "SELECT * FROM Tbl_STOCK_TMP";
            Cursor cur = AppConstant.myDb.rawQuery(sSql, null);

            if (cur.getCount() > 0){
//			c.moveToFirst();
                while (cur.moveToNext()){
                    Obj_STOCK_TMP prodDat = new Obj_STOCK_TMP();
                    prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
                    prodDat.STOCK = cur.getInt(cur.getColumnIndex("STOCK"));
                    AlisData.add(prodDat);
                }

            }
            cur.close();

        }catch (Exception e){

        }
        return AlisData;
    }

    public void CreateDate(){
        try{
            String sSql = "INSERT INTO Tbl_STOCK_TMP " +
                    "   SELECT * FROM Tbl_STOCK"
                    ;

            AppConstant.myDb.execSQL(sSql);
        }catch (Exception e){
            Log.w("Insert Tbl_PRLIN", e.getMessage());
        }
    }

    public void DeleteData(){
        try{
            String sSql = "DELETE FROM Tbl_STOCK_TMP";

            AppConstant.myDb.execSQL(sSql);
        }catch (Exception e){
            Log.w("Insert Tbl_PRLIN", e.getMessage());
        }
    }
}
