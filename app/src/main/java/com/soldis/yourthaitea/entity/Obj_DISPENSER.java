package com.soldis.yourthaitea.entity;

import android.database.Cursor;
import android.util.Log;

import com.soldis.yourthaitea.AppConstant;

import java.util.ArrayList;

/**
 * Created by ftctest on 04/09/2017.
 */

public class Obj_DISPENSER {
    private String CUSTNO = "";
    private String PCODE = "";
    private int QTY = 0;

    public String getCUSTNO() {
        return CUSTNO;
    }

    public void setCUSTNO(String CUSTNO) {
        this.CUSTNO = CUSTNO;
    }

    public Obj_DISPENSER(){}

    public String getPCODE() {
        return PCODE;
    }

    public void setPCODE(String PCODE) {
        this.PCODE = PCODE;
    }

    public int getQTY() {
        return QTY;
    }

    public void setQTY(int QTY) {
        this.QTY = QTY;
    }

    public ArrayList<Obj_DISPENSER> getDataList() {
        ArrayList<Obj_DISPENSER> AlisData = new ArrayList<Obj_DISPENSER>();

        try{
            String sSql = "SELECT * FROM Tbl_DISPENSER";
            Cursor cur = AppConstant.myDb.rawQuery(sSql, null);

            if (cur.getCount() > 0){
//			c.moveToFirst();
                while (cur.moveToNext()){
                    Obj_DISPENSER prodDat = new Obj_DISPENSER();
                    prodDat.CUSTNO = cur.getString(cur.getColumnIndex("CUSTNO"));
                    prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
                    prodDat.QTY = cur.getInt(cur.getColumnIndex("QTY"));
                    AlisData.add(prodDat);
                }

            }
            cur.close();

        }catch (Exception e){

        }
        return AlisData;
    }

    public void CreateDate(Obj_DISPENSER dat){
        try{
            String sSql = "INSERT INTO Tbl_DISPENSER(" +
                    "CUSTNO," +
                    "PCODE," +
                    "QTY" +
                    ")VALUES(" +
                    "'"+dat.CUSTNO+"'," +
                    "'"+dat.PCODE+"'," +
                    ""+dat.QTY+")";

            AppConstant.myDb.execSQL(sSql);
        }catch (Exception e){
            Log.w("Insert Tbl_DISPENSER", e.getMessage());
        }
    }

    public void DeleteData(){
        try{
            String sSql = "DELETE FROM Tbl_DISPENSER";

            AppConstant.myDb.execSQL(sSql);
        }catch (Exception e){
            Log.w("Insert Tbl_DISPENSER", e.getMessage());
        }
    }

    public void DeleteData(String CUSTNO){
        try{
            String sSql = "DELETE FROM Tbl_DISPENSER" +
                    "   WHERE CUSTNO = '"+CUSTNO+"'";

            AppConstant.myDb.execSQL(sSql);
        }catch (Exception e){
            Log.w("Insert Tbl_DISPENSER", e.getMessage());
        }
    }
}
