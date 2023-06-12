package com.soldis.yourthaitea.entity;

import android.database.Cursor;
import android.util.Log;

import com.soldis.yourthaitea.AppConstant;

import java.util.ArrayList;

/**
 * Created by ftctest on 30/08/2017.
 */

public class Obj_CUSTCARD1 {
    private String CUSTNO = "";
    private String TIMEIN  = "";
    private String TIMEOUT = "";
    private String FLAG_EC = "";
    private String REASON = "";
    private String LATITUDE = "";
    private String LONGITUDE = "";

    public Obj_CUSTCARD1(){}

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

    public String getFLAG_EC() {
        return FLAG_EC;
    }

    public void setFLAG_EC(String FLAG_EC) {
        this.FLAG_EC = FLAG_EC;
    }

    public String getREASON() {
        return REASON;
    }

    public void setREASON(String REASON) {
        this.REASON = REASON;
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


    public Obj_CUSTCARD1 getData(String CUSTNO){
        try{
            Cursor cur = AppConstant.myDb.rawQuery("SELECT * FROM Tbl_CUSTCARD1 WHERE CUSTNO = '"+CUSTNO+"'", null);
            if (cur.getCount() > 0){
                cur.moveToFirst();
                this.CUSTNO = cur.getString(cur.getColumnIndex("CUSTNO"));
                this.TIMEIN = cur.getString(cur.getColumnIndex("TIMEIN"));
                this.TIMEOUT = cur.getString(cur.getColumnIndex("TIMEOUT"));
                this.FLAG_EC = cur.getString(cur.getColumnIndex("FLAG_EC"));
                this.REASON = cur.getString(cur.getColumnIndex("REASON"));
                this.LATITUDE = cur.getString(cur.getColumnIndex("LATITUDE"));
                this.LONGITUDE = cur.getString(cur.getColumnIndex("LONGITUDE"));
                Log.w("CUSCTARD", "CUSTNO " + CUSTNO);
                Log.w("CUSCTARD", "TIMEIN " + TIMEIN);

            }
            cur.close();
        }catch (Exception e){

        }

        return this;
    }


    public ArrayList<Obj_CUSTCARD1> getDataList(){
        ArrayList<Obj_CUSTCARD1> AlisData  = new ArrayList<Obj_CUSTCARD1>();
        Cursor cur;

        try{
            cur = AppConstant.myDb.rawQuery("SELECT * FROM Tbl_CUSTCARD1", null);

            if (cur.getCount() > 0){
//			c.moveToFirst();
                while (cur.moveToNext()){
                    Obj_CUSTCARD1 prodDat = new Obj_CUSTCARD1();
                    prodDat.FLAG_EC = cur.getString(cur.getColumnIndex("FLAG_EC"));
                    prodDat.REASON = cur.getString(cur.getColumnIndex("REASON"));
                    prodDat.CUSTNO = cur.getString(cur.getColumnIndex("CUSTNO"));
                    prodDat.TIMEIN = cur.getString(cur.getColumnIndex("TIMEIN"));
                    prodDat.TIMEOUT = cur.getString(cur.getColumnIndex("TIMEOUT"));
                    prodDat.LATITUDE = cur.getString(cur.getColumnIndex("LATITUDE"));
                    prodDat.LONGITUDE = cur.getString(cur.getColumnIndex("LONGITUDE"));
                    AlisData.add(prodDat);
                }

            }
            cur.close();
        }catch (Exception e){

        }

        return AlisData;
    }

    public ArrayList<Obj_CUSTCARD1> getDataListEC(){
        ArrayList<Obj_CUSTCARD1> AlisData  = new ArrayList<Obj_CUSTCARD1>();
        Cursor cur;

        try{
            cur = AppConstant.myDb.rawQuery("SELECT * FROM Tbl_CUSTCARD1" +
                    "   WHERE FLAG_EC = 'Y'", null);

            if (cur.getCount() > 0){
//			c.moveToFirst();
                while (cur.moveToNext()){
                    Obj_CUSTCARD1 prodDat = new Obj_CUSTCARD1();
                    prodDat.FLAG_EC = cur.getString(cur.getColumnIndex("FLAG_EC"));
                    prodDat.REASON = cur.getString(cur.getColumnIndex("REASON"));
                    prodDat.CUSTNO = cur.getString(cur.getColumnIndex("CUSTNO"));
                    prodDat.TIMEIN = cur.getString(cur.getColumnIndex("TIMEIN"));
                    prodDat.TIMEOUT = cur.getString(cur.getColumnIndex("TIMEOUT"));
                    prodDat.LATITUDE = cur.getString(cur.getColumnIndex("LATITUDE"));
                    prodDat.LONGITUDE = cur.getString(cur.getColumnIndex("LONGITUDE"));
                    AlisData.add(prodDat);
                }

            }
            cur.close();
        }catch (Exception e){

        }

        return AlisData;
    }


    public void CreateData(Obj_CUSTCARD1 dat){
        String sQry = "INSERT INTO Tbl_CUSTCARD1(" +
                "FLAG_EC," +
                "REASON," +
                "CUSTNO," +
                "LATITUDE," +
                "LONGITUDE," +
                "TIMEIN," +
                "TIMEOUT" +
                ")VALUES(" +
                "'"+dat.FLAG_EC+"'," +
                "'"+dat.REASON+"'," +
                "'"+dat.CUSTNO+"'," +
                "'"+dat.LATITUDE+"'," +
                "'"+dat.LONGITUDE+"'," +
                "'"+dat.TIMEIN+"'," +
                "'"+dat.TIMEOUT+"')"
                ;

        try{
            AppConstant.myDb.execSQL(sQry);
            Log.w("Custcard", sQry);
        }catch (Exception e){
            Log.w("Error", e.getMessage());
        }
    }

    public void DeleteData(){
        String sQry = "DELETE FROM TBL_CUSTCARD1"
                ;

        try{
            AppConstant.myDb.execSQL(sQry);
        }catch (Exception e){
            Log.w("Error Insert", e.getMessage());
        }
    }


    public void UpdateFlagEC(Obj_CUSTCARD1 dat){
        String sQry = "UPDATE TBL_CUSTCARD1" +
                "   SET REASON = '"+dat.REASON+"'," +
                "   LATITUDE = '"+dat.LATITUDE+"'," +
                "   LONGITUDE = '"+dat.LONGITUDE+"'," +
                "   FLAG_EC = '"+dat.FLAG_EC+"',"+
                "   TIMEOUT = '"+dat.TIMEOUT+"'"+
                "   WHERE CUSTNO = '"+dat.CUSTNO+"'"
                ;

        try{
            AppConstant.myDb.execSQL(sQry);
        }catch (Exception e){
            Log.w("Error Update", e.getMessage());
        }
    }

    public double TotalNotFinish(){
        String sSql = "Select count(*) as TotOut FROM TBL_CUSTCARD1" +
                "   WHERE TIMEOUT IS NULL OR TIMEOUT = ''";

        double dTotInv = 0;
        try {
            Cursor c = AppConstant.myDb.rawQuery(sSql, null);
            if (c.moveToFirst())
                dTotInv = c.getDouble(c.getColumnIndex("TotOut"));
            c.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return dTotInv;
    }
}
