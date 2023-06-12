package com.soldis.yourthaitea.entity;

import android.database.Cursor;
import android.util.Log;

import com.soldis.yourthaitea.AppConstant;

import java.util.ArrayList;

/**
 * Created by ftctest on 30/08/2017.
 */

public class Obj_TRX_D {
    private String ORDERNO = "";
    private String PCODE   = "";
    private int QTY_B = 0;
    private int QTY_T = 0;
    private int QTY_K = 0;
    private int QTY_PCS = 0;
    private double SELLPRICE1 = 0;
    private double SELLPRICE2 = 0;
    private double SELLPRICE3 = 0;
    private double AMOUNT = 0;

    public Obj_TRX_D(){}

    public String getORDERNO() {
        return ORDERNO;
    }

    public void setORDERNO(String ORDERNO) {
        this.ORDERNO = ORDERNO;
    }

    public String getPCODE() {
        return PCODE;
    }

    public void setPCODE(String PCODE) {
        this.PCODE = PCODE;
    }

    public int getQTY_B() {
        return QTY_B;
    }

    public void setQTY_B(int QTY_B) {
        this.QTY_B = QTY_B;
    }

    public int getQTY_T() {
        return QTY_T;
    }

    public void setQTY_T(int QTY_T) {
        this.QTY_T = QTY_T;
    }

    public int getQTY_K() {
        return QTY_K;
    }

    public void setQTY_K(int QTY_K) {
        this.QTY_K = QTY_K;
    }

    public int getQTY_PCS() {
        return QTY_PCS;
    }

    public void setQTY_PCS(int QTY_PCS) {
        this.QTY_PCS = QTY_PCS;
    }

    public double getSELLPRICE1() {
        return SELLPRICE1;
    }

    public void setSELLPRICE1(double SELLPRICE1) {
        this.SELLPRICE1 = SELLPRICE1;
    }

    public double getSELLPRICE2() {
        return SELLPRICE2;
    }

    public void setSELLPRICE2(double SELLPRICE2) {
        this.SELLPRICE2 = SELLPRICE2;
    }

    public double getSELLPRICE3() {
        return SELLPRICE3;
    }

    public void setSELLPRICE3(double SELLPRICE3) {
        this.SELLPRICE3 = SELLPRICE3;
    }

    public double getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(double AMOUNT) {
        this.AMOUNT = AMOUNT;
    }


    public ArrayList<Obj_TRX_D> getDataList(){
        ArrayList<Obj_TRX_D> AlisData  = new ArrayList<Obj_TRX_D>();

        try{
            Cursor cur = AppConstant.myDb.rawQuery("SELECT * FROM Tbl_TRX_D", null);

            if (cur.getCount() > 0){
//			c.moveToFirst();
                while (cur.moveToNext()){
                    Obj_TRX_D prodDat = new Obj_TRX_D();
                    prodDat.ORDERNO = cur.getString(cur.getColumnIndex("ORDERNO"));
                    prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
                    prodDat.QTY_B = cur.getInt(cur.getColumnIndex("QTY_B"));
                    prodDat.QTY_T = cur.getInt(cur.getColumnIndex("QTY_T"));
                    prodDat.QTY_K = cur.getInt(cur.getColumnIndex("QTY_K"));
                    prodDat.QTY_PCS = cur.getInt(cur.getColumnIndex("QTY_PCS"));
                    prodDat.SELLPRICE1 = cur.getDouble(cur.getColumnIndex("SELLPRICE1"));
                    prodDat.SELLPRICE2 = cur.getDouble(cur.getColumnIndex("SELLPRICE2"));
                    prodDat.SELLPRICE3 = cur.getDouble(cur.getColumnIndex("SELLPRICE3"));
                    prodDat.AMOUNT = cur.getDouble(cur.getColumnIndex("AMOUNT"));
                    AlisData.add(prodDat);
                }

            }
            cur.close();
        }catch (Exception e){

        }

        return AlisData;
    }

    public ArrayList<Obj_TRX_D> getDataList(String ORDERNO){
        ArrayList<Obj_TRX_D> AlisData  = new ArrayList<Obj_TRX_D>();

        try {
            Cursor cur = AppConstant.myDb.rawQuery("SELECT * FROM Tbl_TRX_D" +
                    " WHERE ORDERNO = '"+ORDERNO+"'", null);

            if (cur.getCount() > 0){
//			c.moveToFirst();
                while (cur.moveToNext()){
                    Obj_TRX_D prodDat = new Obj_TRX_D();
                    prodDat.ORDERNO = cur.getString(cur.getColumnIndex("ORDERNO"));
                    prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
                    prodDat.QTY_B = cur.getInt(cur.getColumnIndex("QTY_B"));
                    prodDat.QTY_T = cur.getInt(cur.getColumnIndex("QTY_T"));
                    prodDat.QTY_K = cur.getInt(cur.getColumnIndex("QTY_K"));
                    prodDat.QTY_PCS = cur.getInt(cur.getColumnIndex("QTY_PCS"));
                    prodDat.SELLPRICE1 = cur.getDouble(cur.getColumnIndex("SELLPRICE1"));
                    prodDat.SELLPRICE2 = cur.getDouble(cur.getColumnIndex("SELLPRICE2"));
                    prodDat.SELLPRICE3 = cur.getDouble(cur.getColumnIndex("SELLPRICE3"));
                    prodDat.AMOUNT = cur.getDouble(cur.getColumnIndex("AMOUNT"));
                    AlisData.add(prodDat);
                }

            }
            cur.close();
        }catch (Exception e){

        }

        return AlisData;
    }

    public void CreateData(Obj_TRX_D dat){
        String sQry = "INSERT INTO Tbl_TRX_D(" +
                "ORDERNO," +
                "PCODE," +
                "QTY_B," +
                "QTY_T," +
                "QTY_K," +
                "QTY_PCS," +
                "SELLPRICE1," +
                "SELLPRICE2," +
                "SELLPRICE3," +
                "AMOUNT" +
                ")VALUES(" +
                "'"+dat.ORDERNO+"'," +
                "'"+dat.PCODE+"'," +
                "'"+dat.QTY_B+"'," +
                "'"+dat.QTY_T+"'," +
                "'"+dat.QTY_K+"'," +
                "'"+dat.QTY_PCS+"'," +
                ""+dat.SELLPRICE1+"," +
                ""+dat.SELLPRICE2+"," +
                ""+dat.SELLPRICE3+"," +
                ""+dat.AMOUNT+")"
                ;

        try{
            AppConstant.myDb.execSQL(sQry);
        }catch (Exception e){
            Log.w("Error insertTrx_D", e.getMessage());
        }
    }

    public void DeleteData(){
        String sQry = "DELETE FROM TBL_TRX_D"
                ;

        try{
            AppConstant.myDb.execSQL(sQry);
        }catch (Exception e){
            Log.w("Error DeleteTrx_H", e.getMessage());
        }
    }

    public void DeleteData(String ORDERNO){
        String sQry = "DELETE FROM TBL_TRX_D" +
                "   WHERE ORDERNO = '"+ORDERNO+"'"
                ;

        try{
            AppConstant.myDb.execSQL(sQry);
        }catch (Exception e){
            Log.w("Error DeleteTrx_H", e.getMessage());
        }
    }
}
