package com.soldis.yourthaitea.entity;

import android.database.Cursor;
import android.util.Log;

import com.soldis.yourthaitea.AppConstant;

import java.util.ArrayList;

/**
 * Created by ftctest on 30/08/2017.
 */

public class Obj_CUSTCARD {
    private String TGL_TRX = "";
    private String CUSTNO = "";
    private String TIMEIN  = "";
    private String TIMEOUT = "";
    private String LATITUDE = "";
    private String LONGITUDE = "";
    private String FLAG_KIRIM = "";
    private String DOCNO = "";

    public Obj_CUSTCARD(){}

    public String getDOCNO() {
        return DOCNO;
    }

    public void setDOCNO(String DOCNO) {
        this.DOCNO = DOCNO;
    }

    public String getTGL_TRX() {
        return TGL_TRX;
    }

    public void setTGL_TRX(String TGL_TRX) {
        this.TGL_TRX = TGL_TRX;
    }

    public String getCUSTNO() {
        return CUSTNO;
    }

    public void setCUSTNO(String CUSTNO) {
        this.CUSTNO = CUSTNO;
    }

    public String getTIMEIN() {
        return TIMEIN;
    }

    public void setTIMEIN(String TIMEIN) {
        this.TIMEIN = TIMEIN;
    }

    public String getTIMEOUT() {
        return TIMEOUT;
    }

    public void setTIMEOUT(String TIMEOUT) {
        this.TIMEOUT = TIMEOUT;
    }

    public String getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(String LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public String getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(String LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public String getFLAG_KIRIM() {
        return FLAG_KIRIM;
    }

    public void setFLAG_KIRIM(String FLAG_KIRIM) {
        this.FLAG_KIRIM = FLAG_KIRIM;
    }

    public Obj_CUSTCARD getData(String CUSTNO){
        try{
            Cursor cur = AppConstant.myDb.rawQuery("SELECT * FROM Tbl_CUSTCARD WHERE CUSTNO = '"+CUSTNO+"'", null);
            if (cur.getCount() > 0){
                cur.moveToFirst();
                this.TGL_TRX = cur.getString(cur.getColumnIndex("TGL_TRX"));
                this.DOCNO = cur.getString(cur.getColumnIndex("DOCNO"));
                this.CUSTNO = cur.getString(cur.getColumnIndex("CUSTNO"));
                this.TIMEIN = cur.getString(cur.getColumnIndex("TIMEIN"));
                this.TIMEOUT = cur.getString(cur.getColumnIndex("TIMEOUT"));
                this.LATITUDE = cur.getString(cur.getColumnIndex("LATITUDE"));
                this.LONGITUDE = cur.getString(cur.getColumnIndex("LONGITUDE"));
                this.FLAG_KIRIM = cur.getString(cur.getColumnIndex("FLAG_KIRIM"));

            }
            cur.close();
        }catch (Exception e){

        }

        return this;
    }

    public Obj_CUSTCARD getData(String CUSTNO, String ORDERNO){
        try{
            Cursor cur = AppConstant.myDb.rawQuery("SELECT * FROM Tbl_CUSTCARD" +
                    " WHERE CUSTNO = '"+CUSTNO+"'" +
                    " AND DOCNO = '"+ORDERNO+"'", null);
            if (cur.getCount() > 0){
                cur.moveToFirst();
                this.TGL_TRX = cur.getString(cur.getColumnIndex("TGL_TRX"));
                this.DOCNO = cur.getString(cur.getColumnIndex("DOCNO"));
                this.CUSTNO = cur.getString(cur.getColumnIndex("CUSTNO"));
                this.TIMEIN = cur.getString(cur.getColumnIndex("TIMEIN"));
                this.TIMEOUT = cur.getString(cur.getColumnIndex("TIMEOUT"));
                this.LATITUDE = cur.getString(cur.getColumnIndex("LATITUDE"));
                this.LONGITUDE = cur.getString(cur.getColumnIndex("LONGITUDE"));
                this.FLAG_KIRIM = cur.getString(cur.getColumnIndex("FLAG_KIRIM"));

            }
            cur.close();
        }catch (Exception e){

        }

        return this;
    }

    public Obj_CUSTCARD getDataNotProses(String CUSTNO){
        try{
            Cursor cur = AppConstant.myDb.rawQuery("SELECT * FROM Tbl_CUSTCARD WHERE CUSTNO = '"+CUSTNO+"'" +
                    " AND FLAG_KIRIM = 'S'", null);
            if (cur.getCount() > 0){
                cur.moveToFirst();
                this.TGL_TRX = cur.getString(cur.getColumnIndex("TGL_TRX"));
                this.DOCNO = cur.getString(cur.getColumnIndex("DOCNO"));
                this.CUSTNO = cur.getString(cur.getColumnIndex("CUSTNO"));
                this.TIMEIN = cur.getString(cur.getColumnIndex("TIMEIN"));
                this.TIMEOUT = cur.getString(cur.getColumnIndex("TIMEOUT"));
                this.LATITUDE = cur.getString(cur.getColumnIndex("LATITUDE"));
                this.LONGITUDE = cur.getString(cur.getColumnIndex("LONGITUDE"));
                this.FLAG_KIRIM = cur.getString(cur.getColumnIndex("FLAG_KIRIM"));

            }
            cur.close();
        }catch (Exception e){

        }

        return this;
    }

    public Obj_CUSTCARD getDataNotProses(String CUSTNO, String ORDERNO){
        try{
            Cursor cur = AppConstant.myDb.rawQuery("SELECT * FROM Tbl_CUSTCARD WHERE CUSTNO = '"+CUSTNO+"'" +
                    " AND FLAG_KIRIM = 'S' " +
                    " AND DOCNO = '"+ORDERNO+"'", null);
            if (cur.getCount() > 0){
                cur.moveToFirst();
                this.TGL_TRX = cur.getString(cur.getColumnIndex("TGL_TRX"));
                this.DOCNO = cur.getString(cur.getColumnIndex("DOCNO"));
                this.CUSTNO = cur.getString(cur.getColumnIndex("CUSTNO"));
                this.TIMEIN = cur.getString(cur.getColumnIndex("TIMEIN"));
                this.TIMEOUT = cur.getString(cur.getColumnIndex("TIMEOUT"));
                this.LATITUDE = cur.getString(cur.getColumnIndex("LATITUDE"));
                this.LONGITUDE = cur.getString(cur.getColumnIndex("LONGITUDE"));
                this.FLAG_KIRIM = cur.getString(cur.getColumnIndex("FLAG_KIRIM"));
            }
            cur.close();
        }catch (Exception e){

        }

        return this;
    }

    public ArrayList<Obj_CUSTCARD> getDataList(boolean FLAG_KIRIM){
        ArrayList<Obj_CUSTCARD> AlisData  = new ArrayList<Obj_CUSTCARD>();
        Cursor cur;

        try{
            if (FLAG_KIRIM){
                cur = AppConstant.myDb.rawQuery("SELECT * FROM Tbl_CUSTCARD", null);
            }else{
                cur = AppConstant.myDb.rawQuery("SELECT A.* FROM Tbl_CUSTCARD A " +
                        " INNER JOIN Tbl_CUSTMSTNEW B ON A.CUSTNO = B.CUSTNO" +
                        " INNER JOIN Tbl_CUSTCARD1 C ON A.CUSTNO = C.CUSTNO" +
                        " WHERE A.FLAG_KIRIM = 'N'" +
                        " AND B.FLAG_KIRIM = 'Y'" +
                        " AND (C.TIMEOUT != '' AND C.TIMEOUT is not null)", null);
            }


            if (cur.getCount() > 0){
//			c.moveToFirst();
                while (cur.moveToNext()){
                    Obj_CUSTCARD prodDat = new Obj_CUSTCARD();
                    prodDat.TGL_TRX = cur.getString(cur.getColumnIndex("TGL_TRX"));
                    prodDat.DOCNO = cur.getString(cur.getColumnIndex("DOCNO"));
                    prodDat.CUSTNO = cur.getString(cur.getColumnIndex("CUSTNO"));
                    prodDat.TIMEIN = cur.getString(cur.getColumnIndex("TIMEIN"));
                    prodDat.TIMEOUT = cur.getString(cur.getColumnIndex("TIMEOUT"));
                    prodDat.LATITUDE = cur.getString(cur.getColumnIndex("LATITUDE"));
                    prodDat.LONGITUDE = cur.getString(cur.getColumnIndex("LONGITUDE"));
                    prodDat.FLAG_KIRIM = cur.getString(cur.getColumnIndex("FLAG_KIRIM"));
                    AlisData.add(prodDat);
                }

            }
            cur.close();
        }catch (Exception e){

        }

        return AlisData;
    }

    public ArrayList<Obj_CUSTCARD> getDataList(String ORDERNO){
        ArrayList<Obj_CUSTCARD> AlisData  = new ArrayList<Obj_CUSTCARD>();
        Cursor cur;
        try{
            cur = AppConstant.myDb.rawQuery("SELECT * FROM Tbl_CUSTCARD " +
                    " WHERE DOCNO = '"+ORDERNO+"'", null);

            if (cur.getCount() > 0){
//			c.moveToFirst();
                while (cur.moveToNext()){
                    Obj_CUSTCARD prodDat = new Obj_CUSTCARD();
                    prodDat.TGL_TRX = cur.getString(cur.getColumnIndex("TGL_TRX"));
                    prodDat.DOCNO = cur.getString(cur.getColumnIndex("DOCNO"));
                    prodDat.CUSTNO = cur.getString(cur.getColumnIndex("CUSTNO"));
                    prodDat.TIMEIN = cur.getString(cur.getColumnIndex("TIMEIN"));
                    prodDat.TIMEOUT = cur.getString(cur.getColumnIndex("TIMEOUT"));
                    prodDat.LATITUDE = cur.getString(cur.getColumnIndex("LATITUDE"));
                    prodDat.LONGITUDE = cur.getString(cur.getColumnIndex("LONGITUDE"));
                    prodDat.FLAG_KIRIM = cur.getString(cur.getColumnIndex("FLAG_KIRIM"));
                    AlisData.add(prodDat);
                }

            }
            cur.close();
        }catch (Exception e){

        }

        return AlisData;
    }

    public void CreateData(Obj_CUSTCARD dat){
        String sQry = "INSERT INTO Tbl_CUSTCARD(" +
                "TGL_TRX," +
                "DOCNO," +
                "CUSTNO," +
                "TIMEIN," +
                "TIMEOUT," +
                "LATITUDE," +
                "LONGITUDE," +
                "FLAG_KIRIM" +
                ")VALUES(" +
                "'"+dat.TGL_TRX+"'," +
                "'"+dat.DOCNO+"'," +
                "'"+dat.CUSTNO+"'," +
                "'"+dat.TIMEIN+"'," +
                "'"+dat.TIMEOUT+"'," +
                "'"+dat.LATITUDE+"'," +
                "'"+dat.LONGITUDE+"'," +
                "'"+dat.FLAG_KIRIM+"')"
                ;

        try{
            AppConstant.myDb.execSQL(sQry);
        }catch (Exception e){
            Log.w("Error", e.getMessage());
        }
    }

    public void DeleteData(){
        String sQry = "DELETE FROM TBL_CUSTCARD"
                ;

        try{
            AppConstant.myDb.execSQL(sQry);
        }catch (Exception e){
            Log.w("Error Insert", e.getMessage());
        }
    }

    public void DeleteData(String DOCNO){
        String sQry = "DELETE FROM TBL_CUSTCARD" +
                " WHERE DOCNO = '"+DOCNO+"'"
                ;

        try{
            AppConstant.myDb.execSQL(sQry);
        }catch (Exception e){
            Log.w("Error Insert", e.getMessage());
        }
    }



    public void UpdateFlagKirim(String DOCNO){
        String sQry = "UPDATE TBL_CUSTCARD" +
                "   SET FLAG_KIRIM = 'Y'" +
                "   WHERE DOCNO = '"+DOCNO+"'"
                ;

        try{
            AppConstant.myDb.execSQL(sQry);
        }catch (Exception e){
            Log.w("Error Update", e.getMessage());
        }
    }

    public void UpdateFlagProses(String CUSTNO){
        String sQry = "UPDATE TBL_CUSTCARD" +
                "   SET FLAG_KIRIM = 'N'" +
                "   WHERE CUSTNO = '"+CUSTNO+"'"
                ;

        try{
            AppConstant.myDb.execSQL(sQry);
        }catch (Exception e){
            Log.w("Error Update", e.getMessage());
        }
    }
}
