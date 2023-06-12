package com.soldis.yourthaitea.entity;

import android.database.Cursor;
import android.util.Log;

import com.soldis.yourthaitea.AppConstant;

import java.util.ArrayList;

/**
 * Created by ftctest on 04/09/2017.
 */

public class Obj_KAS {
    private String DOCNO = "";
    private String REMARK= "";
    private String TYPE_KAS = "";
    private String TIMEIN = "";
    private long AMOUNT = 0 ;


    public Obj_KAS(){}

    public String getDOCNO() {
        return DOCNO;
    }

    public void setDOCNO(String DOCNO) {
        this.DOCNO = DOCNO;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

    public String getTYPE_KAS() {
        return TYPE_KAS;
    }

    public void setTYPE_KAS(String TYPE_KAS) {
        this.TYPE_KAS = TYPE_KAS;
    }

    public String getTIMEIN() {
        return TIMEIN;
    }

    public void setTIMEIN(String TIMEIN) {
        this.TIMEIN = TIMEIN;
    }

    public long getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(long AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

    public ArrayList<Obj_KAS> getDataList() {
        ArrayList<Obj_KAS> AlisData = new ArrayList<Obj_KAS>();

        try{
            String sSql = "SELECT * FROM Tbl_KAS";
            Cursor cur = AppConstant.myDb.rawQuery(sSql, null);

            if (cur.getCount() > 0){
//			c.moveToFirst();
                while (cur.moveToNext()){
                    Obj_KAS prodDat = new Obj_KAS();
                    prodDat.DOCNO = cur.getString(cur.getColumnIndex("DOCNO"));
                    prodDat.REMARK = cur.getString(cur.getColumnIndex("REMARK"));
                    prodDat.TYPE_KAS = cur.getString(cur.getColumnIndex("TYPE_KAS"));
                    prodDat.TIMEIN = cur.getString(cur.getColumnIndex("TIMEIN"));
                    prodDat.AMOUNT = cur.getLong(cur.getColumnIndex("AMOUNT"));

                    AlisData.add(prodDat);
                }

            }
            cur.close();

        }catch (Exception e){

        }
        return AlisData;
    }


    public void CreateDate(Obj_KAS dat){
        try{
            String sSql = "INSERT INTO Tbl_KAS(" +
                    "REMARK," +
                    "TYPE_KAS," +
                    "TIMEIN," +
                    "AMOUNT" +
                    ")VALUES(" +
                    "'"+dat.REMARK+"'," +
                    "'"+dat.TYPE_KAS+"'," +
                    "'"+dat.TIMEIN+"'," +
                    ""+dat.AMOUNT+")";

            AppConstant.myDb.execSQL(sSql);
        }catch (Exception e){
            Log.w("Insert Tbl_KAS", e.getMessage());
        }
    }

    public void DeleteData(String DOCNO){
        try{
            String sSql = "DELETE FROM Tbl_KAS" +
                    "  WHERE DOCNO = '"+DOCNO+"'";

            AppConstant.myDb.execSQL(sSql);
        }catch (Exception e){
            Log.w("Insert Tbl_PRLIN", e.getMessage());
        }
    }

    public void DeleteData(){
        try{
            String sSql = "DELETE FROM Tbl_KAS";

            AppConstant.myDb.execSQL(sSql);
        }catch (Exception e){
            Log.w("Insert Tbl_PRLIN", e.getMessage());
        }
    }
}
