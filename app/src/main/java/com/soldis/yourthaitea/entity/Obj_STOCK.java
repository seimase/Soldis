package com.soldis.yourthaitea.entity;

import android.database.Cursor;
import android.util.Log;

import com.soldis.yourthaitea.AppConstant;

import java.util.ArrayList;

/**
 * Created by ftctest on 04/09/2017.
 */

public class Obj_STOCK {
    private int DOCNO = 0;
    private String TGL_TRX= "";
    private String TIMEIN = "";
    private String FLAG_IN = "";
    private String PCODE = "";
    private String UNIT1 = "" ;
    private String UNIT2 = "" ;
    private String UNIT3 = "" ;
    private long CONVUNIT2 = 0 ;
    private long CONVUNIT3 = 0 ;
    private double SELLPRICE1 = 0 ;
    private double SELLPRICE2 = 0 ;
    private double SELLPRICE3 = 0 ;
    private int STOCK = 0;
    private int STOCK_IN = 0;
    private int STOCK_OUT = 0;

    public Obj_STOCK(){}

    public int getDOCNO() {
        return DOCNO;
    }

    public void setDOCNO(int DOCNO) {
        this.DOCNO = DOCNO;
    }

    public String getTGL_TRX() {
        return TGL_TRX;
    }

    public void setTGL_TRX(String TGL_TRX) {
        this.TGL_TRX = TGL_TRX;
    }

    public String getTIMEIN() {
        return TIMEIN;
    }

    public void setTIMEIN(String TIMEIN) {
        this.TIMEIN = TIMEIN;
    }

    public String getFLAG_IN() {
        return FLAG_IN;
    }

    public void setFLAG_IN(String FLAG_IN) {
        this.FLAG_IN = FLAG_IN;
    }

    public int getSTOCK_IN() {
        return STOCK_IN;
    }

    public void setSTOCK_IN(int STOCK_IN) {
        this.STOCK_IN = STOCK_IN;
    }

    public int getSTOCK_OUT() {
        return STOCK_OUT;
    }

    public void setSTOCK_OUT(int STOCK_OUT) {
        this.STOCK_OUT = STOCK_OUT;
    }

    public double getSELLPRICE1() {
        return SELLPRICE1;
    }


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

    public long getCONVUNIT2() {
        return CONVUNIT2;
    }

    public void setCONVUNIT2(long CONVUNIT2) {
        this.CONVUNIT2 = CONVUNIT2;
    }

    public long getCONVUNIT3() {
        return CONVUNIT3;
    }

    public void setCONVUNIT3(long CONVUNIT3) {
        this.CONVUNIT3 = CONVUNIT3;
    }

    public String getUNIT1() {
        return UNIT1;
    }

    public void setUNIT1(String UNIT1) {
        this.UNIT1 = UNIT1;
    }

    public String getUNIT2() {
        return UNIT2;
    }

    public void setUNIT2(String UNIT2) {
        this.UNIT2 = UNIT2;
    }

    public String getUNIT3() {
        return UNIT3;
    }

    public void setUNIT3(String UNIT3) {
        this.UNIT3 = UNIT3;
    }

    public ArrayList<Obj_STOCK> getDataList() {
        ArrayList<Obj_STOCK> AlisData = new ArrayList<Obj_STOCK>();

        try{
            String sSql = "SELECT A.*, B.CONVUNIT2, B.CONVUNIT3, B.UNIT1, B.UNIT2,B.UNIT3, B.SELLPRICE1, B.SELLPRICE2, B.SELLPRICE3 FROM Tbl_STOCK A" +
                    "   INNER JOIN Tbl_MASTER B" +
                    "   ON A.PCODE = B.PCODE";
            Cursor cur = AppConstant.myDb.rawQuery(sSql, null);

            if (cur.getCount() > 0){
//			c.moveToFirst();
                while (cur.moveToNext()){
                    Obj_STOCK prodDat = new Obj_STOCK();
                    prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
                    prodDat.STOCK = cur.getInt(cur.getColumnIndex("STOCK"));
                    prodDat.STOCK_IN = cur.getInt(cur.getColumnIndex("STOCK_IN"));
                    prodDat.STOCK_OUT = cur.getInt(cur.getColumnIndex("STOCK_OUT"));
                    prodDat.UNIT1 = cur.getString(cur.getColumnIndex("UNIT1"));
                    prodDat.UNIT2 = cur.getString(cur.getColumnIndex("UNIT2"));
                    prodDat.UNIT3 = cur.getString(cur.getColumnIndex("UNIT3"));
                    prodDat.CONVUNIT2 = cur.getLong(cur.getColumnIndex("CONVUNIT2"));
                    prodDat.CONVUNIT3 = cur.getLong(cur.getColumnIndex("CONVUNIT3"));
                    prodDat.SELLPRICE1 = cur.getDouble(cur.getColumnIndex("SELLPRICE1"));
                    prodDat.SELLPRICE2 = cur.getDouble(cur.getColumnIndex("SELLPRICE2"));
                    prodDat.SELLPRICE3 = cur.getDouble(cur.getColumnIndex("SELLPRICE3"));
                    AlisData.add(prodDat);
                }

            }
            cur.close();

        }catch (Exception e){

        }
        return AlisData;
    }

    public ArrayList<Obj_STOCK> getDataList(String TGL_TRX) {
        ArrayList<Obj_STOCK> AlisData = new ArrayList<Obj_STOCK>();

        try{
            String sSql = "SELECT A.*, B.CONVUNIT2, B.CONVUNIT3, B.UNIT1, B.UNIT2,B.UNIT3, B.SELLPRICE1, B.SELLPRICE2, B.SELLPRICE3 FROM Tbl_STOCK A" +
                    "   INNER JOIN Tbl_MASTER B" +
                    "   ON A.PCODE = B.PCODE" +
                    "   WHERE A.TGL_TRX = '"+TGL_TRX+"'";
            Cursor cur = AppConstant.myDb.rawQuery(sSql, null);

            if (cur.getCount() > 0){
//			c.moveToFirst();
                while (cur.moveToNext()){
                    Obj_STOCK prodDat = new Obj_STOCK();
                    prodDat.TGL_TRX = cur.getString(cur.getColumnIndex("TGL_TRX"));
                    prodDat.DOCNO = cur.getInt(cur.getColumnIndex("DOCNO"));
                    prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
                    prodDat.STOCK = cur.getInt(cur.getColumnIndex("STOCK"));
                    prodDat.FLAG_IN = cur.getString(cur.getColumnIndex("FLAG_IN"));
                    prodDat.TIMEIN = cur.getString(cur.getColumnIndex("TIMEIN"));
                    prodDat.UNIT1 = cur.getString(cur.getColumnIndex("UNIT1"));
                    prodDat.UNIT2 = cur.getString(cur.getColumnIndex("UNIT2"));
                    prodDat.UNIT3 = cur.getString(cur.getColumnIndex("UNIT3"));
                    prodDat.CONVUNIT2 = cur.getLong(cur.getColumnIndex("CONVUNIT2"));
                    prodDat.CONVUNIT3 = cur.getLong(cur.getColumnIndex("CONVUNIT3"));
                    prodDat.SELLPRICE1 = cur.getDouble(cur.getColumnIndex("SELLPRICE1"));
                    prodDat.SELLPRICE2 = cur.getDouble(cur.getColumnIndex("SELLPRICE2"));
                    prodDat.SELLPRICE3 = cur.getDouble(cur.getColumnIndex("SELLPRICE3"));
                    AlisData.add(prodDat);
                }

            }
            cur.close();

        }catch (Exception e){

        }
        return AlisData;
    }

    public ArrayList<Obj_STOCK> getDataListPCode(String PCODE) {
        ArrayList<Obj_STOCK> AlisData = new ArrayList<Obj_STOCK>();

        try{
            String sSql = "SELECT A.*, B.CONVUNIT2, B.CONVUNIT3, B.UNIT1, B.UNIT2,B.UNIT3, B.SELLPRICE1, B.SELLPRICE2, B.SELLPRICE3 FROM Tbl_STOCK A" +
                    "   INNER JOIN Tbl_MASTER B" +
                    "   ON A.PCODE = B.PCODE" +
                    "   WHERE A.PCODE = '"+PCODE+"'";
            Cursor cur = AppConstant.myDb.rawQuery(sSql, null);

            if (cur.getCount() > 0){
//			c.moveToFirst();
                while (cur.moveToNext()){
                    Obj_STOCK prodDat = new Obj_STOCK();
                    prodDat.TGL_TRX = cur.getString(cur.getColumnIndex("TGL_TRX"));
                    prodDat.DOCNO = cur.getInt(cur.getColumnIndex("DOCNO"));
                    prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
                    prodDat.STOCK = cur.getInt(cur.getColumnIndex("STOCK"));
                    prodDat.FLAG_IN = cur.getString(cur.getColumnIndex("FLAG_IN"));
                    prodDat.TIMEIN = cur.getString(cur.getColumnIndex("TIMEIN"));
                    prodDat.UNIT1 = cur.getString(cur.getColumnIndex("UNIT1"));
                    prodDat.UNIT2 = cur.getString(cur.getColumnIndex("UNIT2"));
                    prodDat.UNIT3 = cur.getString(cur.getColumnIndex("UNIT3"));
                    prodDat.CONVUNIT2 = cur.getLong(cur.getColumnIndex("CONVUNIT2"));
                    prodDat.CONVUNIT3 = cur.getLong(cur.getColumnIndex("CONVUNIT3"));
                    prodDat.SELLPRICE1 = cur.getDouble(cur.getColumnIndex("SELLPRICE1"));
                    prodDat.SELLPRICE2 = cur.getDouble(cur.getColumnIndex("SELLPRICE2"));
                    prodDat.SELLPRICE3 = cur.getDouble(cur.getColumnIndex("SELLPRICE3"));
                    AlisData.add(prodDat);
                }

            }
            cur.close();

        }catch (Exception e){

        }
        return AlisData;
    }

    public ArrayList<Obj_STOCK> getDataListUpload() {
        ArrayList<Obj_STOCK> AlisData = new ArrayList<Obj_STOCK>();

        try{
            String sSql = "SELECT A.*, B.CONVUNIT2, B.CONVUNIT3, B.UNIT1, B.UNIT2,B.UNIT3, B.SELLPRICE1, B.SELLPRICE2, B.SELLPRICE3 FROM Tbl_STOCK A" +
                    "   INNER JOIN Tbl_MASTER B" +
                    "   ON A.PCODE = B.PCODE" +
                    "   WHERE A.DOCNO != '9999' AND A.FLAG_IN != 'A'" ;
            Cursor cur = AppConstant.myDb.rawQuery(sSql, null);

            if (cur.getCount() > 0){
//			c.moveToFirst();
                while (cur.moveToNext()){
                    Obj_STOCK prodDat = new Obj_STOCK();
                    prodDat.TGL_TRX = cur.getString(cur.getColumnIndex("TGL_TRX"));
                    prodDat.DOCNO = cur.getInt(cur.getColumnIndex("DOCNO"));
                    prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
                    prodDat.STOCK = cur.getInt(cur.getColumnIndex("STOCK"));
                    prodDat.FLAG_IN = cur.getString(cur.getColumnIndex("FLAG_IN"));
                    prodDat.TIMEIN = cur.getString(cur.getColumnIndex("TIMEIN"));
                    prodDat.UNIT1 = cur.getString(cur.getColumnIndex("UNIT1"));
                    prodDat.UNIT2 = cur.getString(cur.getColumnIndex("UNIT2"));
                    prodDat.UNIT3 = cur.getString(cur.getColumnIndex("UNIT3"));
                    prodDat.CONVUNIT2 = cur.getLong(cur.getColumnIndex("CONVUNIT2"));
                    prodDat.CONVUNIT3 = cur.getLong(cur.getColumnIndex("CONVUNIT3"));
                    prodDat.SELLPRICE1 = cur.getDouble(cur.getColumnIndex("SELLPRICE1"));
                    prodDat.SELLPRICE2 = cur.getDouble(cur.getColumnIndex("SELLPRICE2"));
                    prodDat.SELLPRICE3 = cur.getDouble(cur.getColumnIndex("SELLPRICE3"));
                    AlisData.add(prodDat);
                }

            }
            cur.close();

        }catch (Exception e){

        }
        return AlisData;
    }


    public void CreateDate(Obj_STOCK dat){
        try{
            String sSql = "INSERT INTO Tbl_STOCK(" +
                    "TGL_TRX," +
                    "TIMEIN," +
                    "FLAG_IN," +
                    "PCODE," +
                    "STOCK" +
                    ")VALUES(" +
                    "'"+dat.TGL_TRX+"'," +
                    "'"+dat.TIMEIN+"'," +
                    "'"+dat.FLAG_IN+"'," +
                    "'"+dat.PCODE+"'," +
                    ""+dat.STOCK+")";

            AppConstant.myDb.execSQL(sSql);
        }catch (Exception e){
            Log.w("Insert Tbl_PRLIN", e.getMessage());
        }
    }

    public void DeleteData(){
        try{
            String sSql = "DELETE FROM Tbl_STOCK";

            AppConstant.myDb.execSQL(sSql);
        }catch (Exception e){
            Log.w("Insert Tbl_PRLIN", e.getMessage());
        }
    }

    public void DeleteData(String TGL_TRX, String TIMEIN){
        try{
            String sSql = "DELETE FROM Tbl_STOCK" +
                    "   WHERE TGL_TRX = '"+TGL_TRX+"'" +
                    "   AND TIMEIN = '"+TIMEIN+"'";

            AppConstant.myDb.execSQL(sSql);
        }catch (Exception e){
            Log.w("Insert Tbl_PRLIN", e.getMessage());
        }
    }
}
