package com.soldis.yourthaitea.entity;

import android.database.Cursor;
import android.util.Log;

import com.soldis.yourthaitea.AppConstant;

import java.util.ArrayList;

/**
 * Created by ftctest on 25/08/2017.
 */

public class Obj_COMPLAINT {
    private String CUSTNO = "";
    private String Q1 = "";
    private String Q2 = "";
    private String Q3 = "";
    private String Q4 = "";
    private String Q5 = "";

    public Obj_COMPLAINT(){}

    public String getQ5() {
        return Q5;
    }

    public void setQ5(String q5) {
        Q5 = q5;
    }

    public String getCUSTNO() {
        return CUSTNO;
    }

    public void setCUSTNO(String CUSTNO) {
        this.CUSTNO = CUSTNO;
    }

    public String getQ1() {
        return Q1;
    }

    public void setQ1(String q1) {
        Q1 = q1;
    }

    public String getQ2() {
        return Q2;
    }

    public void setQ2(String q2) {
        Q2 = q2;
    }

    public String getQ3() {
        return Q3;
    }

    public void setQ3(String q3) {
        Q3 = q3;
    }

    public String getQ4() {
        return Q4;
    }

    public void setQ4(String q4) {
        Q4 = q4;
    }

    public Obj_COMPLAINT getData(String CUSTNO){
        try{
            Cursor cur = AppConstant.myDb.rawQuery("SELECT * FROM TBL_COMPLAINT WHERE CUSTNO = '"+CUSTNO+"'", null);
            if (cur.getCount() > 0){
                cur.moveToFirst();
                this.CUSTNO = cur.getString(cur.getColumnIndex("CUSTNO"));
                this.Q1 = cur.getString(cur.getColumnIndex("Q1"));
                this.Q2 = cur.getString(cur.getColumnIndex("Q2"));
                this.Q3 = cur.getString(cur.getColumnIndex("Q3"));
                this.Q4 = cur.getString(cur.getColumnIndex("Q4"));
                this.Q5 = cur.getString(cur.getColumnIndex("Q5"));
            }
            cur.close();
        }catch (Exception e){

        }

        return this;
    }

    public ArrayList<Obj_COMPLAINT> getDataList() {
        ArrayList<Obj_COMPLAINT> AlisData = new ArrayList<Obj_COMPLAINT>();

        try{
            String sSql = "SELECT * FROM TBL_COMPLAINT";
            Cursor cur = AppConstant.myDb.rawQuery(sSql, null);

            if (cur.getCount() > 0){
//			c.moveToFirst();
                while (cur.moveToNext()){
                    Obj_COMPLAINT prodDat = new Obj_COMPLAINT();
                    prodDat.CUSTNO = cur.getString(cur.getColumnIndex("CUSTNO"));
                    prodDat.Q1 = cur.getString(cur.getColumnIndex("Q1"));
                    prodDat.Q2 = cur.getString(cur.getColumnIndex("Q2"));
                    prodDat.Q3 = cur.getString(cur.getColumnIndex("Q3"));
                    prodDat.Q3 = cur.getString(cur.getColumnIndex("Q4"));
                    prodDat.Q5 = cur.getString(cur.getColumnIndex("Q5"));
                    AlisData.add(prodDat);
                }

            }
            cur.close();

        }catch (Exception e){

        }
        return AlisData;
    }

    public void CreateData(Obj_COMPLAINT dat){
        try{
            String sSql = "INSERT INTO TBL_COMPLAINT(" +
                    "CUSTNO," +
                    "Q1," +
                    "Q2," +
                    "Q3," +
                    "Q4," +
                    "Q5" +
                    ")VALUES(" +
                    "'"+dat.getCUSTNO()+"'," +
                    "'"+dat.getQ1()+"'," +
                    "'"+dat.getQ2()+"'," +
                    "'"+dat.getQ3()+"'," +
                    "'"+dat.getQ4()+"'," +
                    "'"+dat.getQ5()+"')";

            AppConstant.myDb.execSQL(sSql);
        }catch (Exception e){
            Log.w("Insert TBL_COMPLAINT", e.getMessage());
        }
    }

    public void UpdateData(Obj_COMPLAINT dat){
        try{
            String sSql = "UPDATE TBL_COMPLAINT" +
                    " SET" +
                    " Q1 = '"+dat.getQ1()+"'," +
                    " Q2 = '"+dat.getQ2()+"'," +
                    " Q3 = '"+dat.getQ3()+"'," +
                    " Q4 = '"+dat.getQ4()+"'," +
                    " Q5 = '"+dat.getQ5()+"'" +
                    " WHERE CUSTNO = '"+dat.getCUSTNO()+"'";


            AppConstant.myDb.execSQL(sSql);
        }catch (Exception e){
            Log.w("Update TBL_COMPLAINT", e.getMessage());
        }
    }

    public void DeleteData(){
        try{
            String sSql = "DELETE FROM TBL_COMPLAINT" ;

            AppConstant.myDb.execSQL(sSql);
        }catch (Exception e){
            Log.w("Update TBL_COMPLAINT", e.getMessage());
        }
    }



}
