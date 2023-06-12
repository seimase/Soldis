package com.soldis.yourthaitea.entity;

import android.database.Cursor;
import android.util.Log;

import com.soldis.yourthaitea.AppConstant;

/**
 * Created by User on 8/24/2017.
 */

public class Obj_MOTORIS {

    public Obj_MOTORIS(){}
    private String SLSNO = "";
    private String SLSNAME = "";
    private String PASSWORD = "";
    private String KODECABANG = "";
    private String ADDRESS = "";
    private String CITY = "";
    private String PHONE = "";
    private String TRX_DATE = "";
    private String PREFIX_ID = "";
    private String GOOGLE_API_KEY = "";
    private double SEQNO = 0;
    private double TRXNO = 0;
    private int TARGET_EC = 0;
    private double TARGET_SALES = 0;
    private int TARGET_EC_MTD = 0;
    private double TARGET_SALES_MTD = 0;
    private int TOTAL_EC = 0;
    private double TOTAL_SALES = 0;
    private int TOTAL_CALL = 0;
    private int TARGET_CALL = 0;
    private int TARGET_CALL_MTD = 0;

    public String getPREFIX_ID() {
        return PREFIX_ID;
    }

    public void setPREFIX_ID(String PREFIX_ID) {
        this.PREFIX_ID = PREFIX_ID;
    }

    public String getGOOGLE_API_KEY() {
        return GOOGLE_API_KEY;
    }

    public void setGOOGLE_API_KEY(String GOOGLE_API_KEY) {
        this.GOOGLE_API_KEY = GOOGLE_API_KEY;
    }

    public int getTOTAL_CALL() {
        return TOTAL_CALL;
    }

    public void setTOTAL_CALL(int TOTAL_CALL) {
        this.TOTAL_CALL = TOTAL_CALL;
    }

    public int getTARGET_CALL() {
        return TARGET_CALL;
    }

    public void setTARGET_CALL(int TARGET_CALL) {
        this.TARGET_CALL = TARGET_CALL;
    }

    public int getTARGET_CALL_MTD() {
        return TARGET_CALL_MTD;
    }

    public void setTARGET_CALL_MTD(int TARGET_CALL_MTD) {
        this.TARGET_CALL_MTD = TARGET_CALL_MTD;
    }

    public String getTRX_DATE() {
        return TRX_DATE;
    }

    public void setTRX_DATE(String TRX_DATE) {
        this.TRX_DATE = TRX_DATE;
    }

    public double getTRXNO() {
        return TRXNO;
    }

    public void setTRXNO(double TRXNO) {
        this.TRXNO = TRXNO;
    }

    public double getSEQNO() {
        return SEQNO;
    }

    public void setSEQNO(double SEQNO) {
        this.SEQNO = SEQNO;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getKODECABANG() {
        return KODECABANG;
    }

    public void setKODECABANG(String KODECABANG) {
        this.KODECABANG = KODECABANG;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getSLSNAME() {
        return SLSNAME;
    }

    public void setSLSNAME(String SLSNAME) {
        this.SLSNAME = SLSNAME;
    }

    public String getSLSNO() {
        return SLSNO;
    }

    public void setSLSNO(String SLSNO) {
        this.SLSNO = SLSNO;
    }

    public int getTARGET_EC() {
        return TARGET_EC;
    }

    public void setTARGET_EC(int TARGET_EC) {
        this.TARGET_EC = TARGET_EC;
    }

    public double getTARGET_SALES() {
        return TARGET_SALES;
    }

    public void setTARGET_SALES(double TARGET_SALES) {
        this.TARGET_SALES = TARGET_SALES;
    }

    public int getTARGET_EC_MTD() {
        return TARGET_EC_MTD;
    }

    public void setTARGET_EC_MTD(int TARGET_EC_MTD) {
        this.TARGET_EC_MTD = TARGET_EC_MTD;
    }

    public double getTARGET_SALES_MTD() {
        return TARGET_SALES_MTD;
    }

    public void setTARGET_SALES_MTD(double TARGET_SALES_MTD) {
        this.TARGET_SALES_MTD = TARGET_SALES_MTD;
    }

    public int getTOTAL_EC() {
        return TOTAL_EC;
    }

    public void setTOTAL_EC(int TOTAL_EC) {
        this.TOTAL_EC = TOTAL_EC;
    }

    public double getTOTAL_SALES() {
        return TOTAL_SALES;
    }

    public void setTOTAL_SALES(double TOTAL_SALES) {
        this.TOTAL_SALES = TOTAL_SALES;
    }

    public Obj_MOTORIS getData(String SLSNO){
        try{
            Cursor cur = AppConstant.myDb.rawQuery("SELECT * FROM TBL_MOTORIS WHERE SLSNO = '"+SLSNO+"'", null);
            if (cur.getCount() > 0){
                cur.moveToFirst();
                this.SLSNO = cur.getString(cur.getColumnIndex("SLSNO"));
                this.SLSNAME = cur.getString(cur.getColumnIndex("SLSNAME"));
                this.PASSWORD = cur.getString(cur.getColumnIndex("PASSWORD"));
                this.ADDRESS = cur.getString(cur.getColumnIndex("ADDRESS"));
                this.CITY = cur.getString(cur.getColumnIndex("CITY"));
                this.PHONE = cur.getString(cur.getColumnIndex("PHONE"));
                this.SEQNO = cur.getDouble(cur.getColumnIndex("SEQNO"));
                this.TARGET_EC = cur.getInt(cur.getColumnIndex("TARGET_EC"));
                this.TARGET_EC_MTD = cur.getInt(cur.getColumnIndex("TARGET_EC_MTD"));
                this.TRXNO = cur.getDouble(cur.getColumnIndex("TRXNO"));
                this.TARGET_SALES = cur.getDouble(cur.getColumnIndex("TARGET_SALES"));
                this.TARGET_SALES_MTD = cur.getDouble(cur.getColumnIndex("TARGET_SALES_MTD"));
                this.TOTAL_EC = cur.getInt(cur.getColumnIndex("TOTAL_EC"));
                this.TOTAL_CALL = cur.getInt(cur.getColumnIndex("TOTAL_CALL"));
                this.TOTAL_SALES = cur.getDouble(cur.getColumnIndex("TOTAL_SALES"));
                this.TARGET_CALL = cur.getInt(cur.getColumnIndex("TARGET_CALL"));
                this.TARGET_CALL_MTD = cur.getInt(cur.getColumnIndex("TARGET_CALL_MTD"));
                this.TRX_DATE = cur.getString(cur.getColumnIndex("TRX_DATE"));
                this.GOOGLE_API_KEY = cur.getString(cur.getColumnIndex("GOOGLE_API_KEY"));
                this.PREFIX_ID = cur.getString(cur.getColumnIndex("PREFIX_ID"));
            }
            cur.close();
        }catch (Exception e){
            Log.w("Login", e.getMessage());
        }

        return this;
    }

    public Obj_MOTORIS getData(String SLSNO, String PASSWORD){

        try{
            Cursor cur = AppConstant.myDb.rawQuery("SELECT * FROM TBL_MOTORIS " +
                    " WHERE SLSNO = '"+SLSNO+"' AND PASSWORD = '"+PASSWORD+"'", null);
            if (cur.getCount() > 0){
                cur.moveToFirst();
                this.SLSNO = cur.getString(cur.getColumnIndex("SLSNO"));
                this.SLSNAME = cur.getString(cur.getColumnIndex("SLSNAME"));
                this.PASSWORD = cur.getString(cur.getColumnIndex("PASSWORD"));
                this.ADDRESS = cur.getString(cur.getColumnIndex("ADDRESS"));
                this.CITY = cur.getString(cur.getColumnIndex("CITY"));
                this.PHONE = cur.getString(cur.getColumnIndex("PHONE"));
                this.SEQNO = cur.getDouble(cur.getColumnIndex("SEQNO"));
                this.TARGET_EC = cur.getInt(cur.getColumnIndex("TARGET_EC"));
                this.TARGET_EC_MTD = cur.getInt(cur.getColumnIndex("TARGET_EC_MTD"));
                this.TRXNO = cur.getDouble(cur.getColumnIndex("TRXNO"));
                this.TARGET_SALES = cur.getDouble(cur.getColumnIndex("TARGET_SALES"));
                this.TARGET_SALES_MTD = cur.getDouble(cur.getColumnIndex("TARGET_SALES_MTD"));
                this.TOTAL_CALL = cur.getInt(cur.getColumnIndex("TOTAL_CALL"));
                this.TOTAL_EC = cur.getInt(cur.getColumnIndex("TOTAL_EC"));
                this.TOTAL_SALES = cur.getDouble(cur.getColumnIndex("TOTAL_SALES"));
                this.TARGET_CALL = cur.getInt(cur.getColumnIndex("TARGET_CALL"));
                this.TARGET_CALL_MTD = cur.getInt(cur.getColumnIndex("TARGET_CALL_MTD"));
                this.TRX_DATE = cur.getString(cur.getColumnIndex("TRX_DATE"));
                this.GOOGLE_API_KEY = cur.getString(cur.getColumnIndex("GOOGLE_API_KEY"));
                this.PREFIX_ID = cur.getString(cur.getColumnIndex("PREFIX_ID"));
            }
            cur.close();
        }catch (Exception e){
            Log.w("Error getMotoris", e.getMessage());
        }

        return this;
    }

    public void CreateData(Obj_MOTORIS dat){
        String sQry = "INSERT INTO TBL_MOTORIS(" +
                "SLSNO," +
                "SLSNAME," +
                "PASSWORD," +
                "ADDRESS," +
                "GOOGLE_API_KEY," +
                "CITY," +
                "PHONE," +
                "TRX_DATE," +
                "PREFIX_ID," +
                "TRXNO," +
                "SEQNO," +
                "TOTAL_CALL," +
                "TARGET_EC," +
                "TARGET_EC_MTD," +
                "TARGET_SALES," +
                "TARGET_SALES_MTD" +
                ")VALUES(" +
                "'"+dat.SLSNO+"'," +
                "'"+dat.SLSNAME+"'," +
                "'"+dat.PASSWORD+"'," +
                "'"+dat.ADDRESS+"'," +
                "'"+dat.GOOGLE_API_KEY+"'," +
                "'"+dat.CITY+"'," +
                "'"+dat.TRX_DATE+"'," +
                "'"+dat.PREFIX_ID+"'," +
                "'"+dat.PHONE+"'," +
                "'"+dat.TRXNO+"'," +
                "'"+dat.SEQNO+"'," +
                "'"+dat.TOTAL_CALL+"'," +
                "'"+dat.TARGET_EC+"'," +
                "'"+dat.TARGET_EC_MTD+"'," +
                "'"+dat.TARGET_SALES+"'," +
                "'"+dat.TARGET_SALES_MTD+"')"
                ;

        try{
            AppConstant.myDb.execSQL(sQry);
        }catch (Exception e){
            Log.w("Error insertMotoris", e.getMessage());
        }
    }

    public void UpdateSeqNo(Obj_MOTORIS dat){
        String sQry = "UPDATE TBL_MOTORIS SET" +
                " SEQNO = '"+dat.getSEQNO()+"'"
                ;

        try{
            AppConstant.myDb.execSQL(sQry);
        }catch (Exception e){
            Log.w("Error insertMotoris", e.getMessage());
        }
    }

    public void UpdateTrxNo(Obj_MOTORIS dat){
        String sQry = "UPDATE TBL_MOTORIS SET" +
                " TRXNO = '"+dat.getTRXNO()+"'"
                ;

        try{
            AppConstant.myDb.execSQL(sQry);
        }catch (Exception e){
            Log.w("Error insertMotoris", e.getMessage());
        }
    }

    public void DeleteData(){
        String sQry = "DELETE FROM TBL_MOTORIS"
                ;

        try{
            AppConstant.myDb.execSQL(sQry);
        }catch (Exception e){
            Log.w("Error insertMotoris", e.getMessage());
        }
    }

    public void UpdateTrxNoSeqNo(Obj_MOTORIS dat){
        String sQry = "UPDATE TBL_MOTORIS SET" +
                " TRXNO = '"+dat.getTRXNO()+"'," +
                " TRX_DATE = '"+dat.getTRX_DATE()+"'," +
                " TOTAL_EC = '"+dat.getTOTAL_EC()+"'," +
                " TARGET_CALL = "+dat.getTARGET_CALL()+"," +
                " TARGET_CALL_MTD = "+dat.getTARGET_CALL_MTD()+"," +
                " TARGET_EC = '"+dat.getTARGET_EC()+"'," +
                " TARGET_EC_MTD = '"+dat.getTARGET_EC_MTD()+"'," +
                " TARGET_SALES = "+dat.getTARGET_SALES()+"," +
                " TARGET_SALES_MTD = "+dat.getTARGET_SALES_MTD()+"," +
                " TOTAL_EC = '"+dat.getTOTAL_EC()+"'," +
                " TOTAL_SALES = "+dat.getTOTAL_SALES()+"," +
                " SEQNO = '"+dat.getSEQNO()+"'"
                ;

        try{
            AppConstant.myDb.execSQL(sQry);
        }catch (Exception e){
            Log.w("Error insertMotoris", e.getMessage());
        }
    }
}
