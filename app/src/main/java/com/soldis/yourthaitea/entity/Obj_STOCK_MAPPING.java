package com.soldis.yourthaitea.entity;

import android.database.Cursor;
import android.util.Log;

import com.soldis.yourthaitea.AppConstant;

import java.util.ArrayList;

/**
 * Created by ftctest on 04/09/2017.
 */

public class Obj_STOCK_MAPPING {
    private String PCODE_SALES = "";
    private String PCODE = "";
    private int QTY = 0;

    public Obj_STOCK_MAPPING(){}

    public String getPCODE_SALES() {
        return PCODE_SALES;
    }

    public void setPCODE_SALES(String PCODE_SALES) {
        this.PCODE_SALES = PCODE_SALES;
    }

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

    public ArrayList<Obj_STOCK_MAPPING> getDataList() {
        ArrayList<Obj_STOCK_MAPPING> AlisData = new ArrayList<Obj_STOCK_MAPPING>();

        try{
            String sSql = "SELECT * FROM TBL_STOCK_MAPPING";
            Cursor cur = AppConstant.myDb.rawQuery(sSql, null);

            if (cur.getCount() > 0){
//			c.moveToFirst();
                while (cur.moveToNext()){
                    Obj_STOCK_MAPPING prodDat = new Obj_STOCK_MAPPING();
                    prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
                    prodDat.PCODE_SALES = cur.getString(cur.getColumnIndex("PCODE_SALES"));
                    prodDat.QTY = cur.getInt(cur.getColumnIndex("QTY"));
                    AlisData.add(prodDat);
                }

            }
            cur.close();

        }catch (Exception e){

        }
        return AlisData;
    }

    public ArrayList<Obj_STOCK_MAPPING> getDataListStockSales() {
        ArrayList<Obj_STOCK_MAPPING> AlisData = new ArrayList<Obj_STOCK_MAPPING>();
        try{
            String sSql = "SELECT A.PCODE, SUM(A.QTY) QTY from (SELECT B.PCODE,  (B.QTY * A.QTY_PCS) QTY FROM " +
                    " (SELECT A.PCODE, SUM(A.QTY_PCS) QTY_PCS FROM TBL_TRX_D A" +
                    " INNER JOIN TBL_TRX_H B ON A.ORDERNO = B.ORDERNO  WHERE B.FLAG_VOID = 'N' GROUP BY PCODE)A" +
                    " INNER JOIN TBL_STOCK_MAPPING B ON A.PCODE = B.PCODE_SALES ) A" +
                    " GROUP BY A.PCODE";
            Cursor cur = AppConstant.myDb.rawQuery(sSql, null);

            if (cur.getCount() > 0){
//			c.moveToFirst();
                while (cur.moveToNext()){
                    Obj_STOCK_MAPPING prodDat = new Obj_STOCK_MAPPING();
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

    public void CreateDate(Obj_STOCK_MAPPING dat){
        try{
            String sSql = "INSERT INTO Tbl_STOCK_MAPPING(" +
                    "PCODE_SALES," +
                    "PCODE," +
                    "QTY" +
                    ")VALUES(" +
                    "'"+dat.PCODE_SALES+"'," +
                    "'"+dat.PCODE+"'," +
                    ""+dat.QTY+")" ;

            AppConstant.myDb.execSQL(sSql);
        }catch (Exception e){
            Log.w("Insert Tbl_STOCK", e.getMessage());
        }
    }

    public void DeleteData(){
        try{
            String sSql = "DELETE FROM Tbl_STOCK_MAPPING";

            AppConstant.myDb.execSQL(sSql);
        }catch (Exception e){
            Log.w("Insert Tbl_PRLIN", e.getMessage());
        }
    }

    public int TotalQty(String sPCODE){
        String sSql = "SELECT A.PCODE, SUM(A.QTY) QTYJUAL from (SELECT B.PCODE,  (B.QTY * A.QTY_PCS) QTY FROM " +
                " (SELECT A.PCODE, SUM(A.QTY_PCS) QTY_PCS FROM TBL_TRX_D A" +
                " INNER JOIN TBL_TRX_H B ON A.ORDERNO = B.ORDERNO  WHERE B.FLAG_VOID = 'N' GROUP BY PCODE)A" +
                " INNER JOIN TBL_STOCK_MAPPING B ON A.PCODE = B.PCODE_SALES ) A" +
                " WHERE A.PCODE = '"+ sPCODE +"'" +
                " GROUP BY A.PCODE";

        int dTotInv = 0;
        try {
            Cursor c = AppConstant.myDb.rawQuery(sSql, null);
            if (c.moveToFirst())
                dTotInv = c.getInt(c.getColumnIndex("QTYJUAL"));
            c.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return dTotInv;
    }

}
