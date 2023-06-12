package com.soldis.yourthaitea.entity;

import android.database.Cursor;
import android.util.Log;

import com.soldis.yourthaitea.AppConstant;

import java.util.ArrayList;

/**
 * Created by ftctest on 30/08/2017.
 */

public class Obj_TRX_H {
    private String ORDERNO = "";
    private String CUSTNO  = "";
    private String ORDERDATE = "";
    private String REMARK = "";
    private String FLAG_KIRIM = "";
    private String FLAG_VOID = "";
    private int SKU = 0;
    private double INVAMOUNT = 0;
    private double PAYAMOUNT = 0;

    public  Obj_TRX_H(){}

    public double getPAYAMOUNT() {
        return PAYAMOUNT;
    }

    public void setPAYAMOUNT(double PAYAMOUNT) {
        this.PAYAMOUNT = PAYAMOUNT;
    }

    public String getFLAG_VOID() {
        return FLAG_VOID;
    }

    public void setFLAG_VOID(String FLAG_VOID) {
        this.FLAG_VOID = FLAG_VOID;
    }

    public String getFLAG_KIRIM() {
        return FLAG_KIRIM;
    }

    public void setFLAG_KIRIM(String FLAG_KIRIM) {
        this.FLAG_KIRIM = FLAG_KIRIM;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

    public String getORDERNO() {
        return ORDERNO;
    }

    public void setORDERNO(String ORDERNO) {
        this.ORDERNO = ORDERNO;
    }

    public String getCUSTNO() {
        return CUSTNO;
    }

    public void setCUSTNO(String CUSTNO) {
        this.CUSTNO = CUSTNO;
    }

    public String getORDERDATE() {
        return ORDERDATE;
    }

    public void setORDERDATE(String ORDERDATE) {
        this.ORDERDATE = ORDERDATE;
    }

    public int getSKU() {
        return SKU;
    }

    public void setSKU(int SKU) {
        this.SKU = SKU;
    }

    public double getINVAMOUNT() {
        return INVAMOUNT;
    }

    public void setINVAMOUNT(double INVAMOUNT) {
        this.INVAMOUNT = INVAMOUNT;
    }

    public Obj_TRX_H getData(String ORDERNO){
        try {
            Cursor cur = AppConstant.myDb.rawQuery("SELECT * FROM Tbl_TRX_H WHERE ORDERNO = '"+ORDERNO+"'", null);
            if (cur.getCount() > 0){
                cur.moveToFirst();
                this.ORDERNO = cur.getString(cur.getColumnIndex("ORDERNO"));
                this.CUSTNO = cur.getString(cur.getColumnIndex("CUSTNO"));
                this.ORDERDATE = cur.getString(cur.getColumnIndex("ORDERDATE"));
                this.SKU = cur.getInt(cur.getColumnIndex("SKU"));
                this.INVAMOUNT = cur.getDouble(cur.getColumnIndex("INVAMOUNT"));
                this.REMARK = cur.getString(cur.getColumnIndex("REMARK"));
                this.FLAG_VOID = cur.getString(cur.getColumnIndex("FLAG_VOID"));
                this.PAYAMOUNT = cur.getDouble(cur.getColumnIndex("PAYAMOUNT"));
            }
            cur.close();
        }catch (Exception e){

        }


        return this;
    }

    public Obj_TRX_H getDataCustNo(String CUSTNO){
        try{
            Cursor cur = AppConstant.myDb.rawQuery("SELECT * FROM Tbl_TRX_H WHERE CUSTNO = '"+CUSTNO+"'", null);
            if (cur.getCount() > 0){
                cur.moveToFirst();
                this.ORDERNO = cur.getString(cur.getColumnIndex("ORDERNO"));
                this.CUSTNO = cur.getString(cur.getColumnIndex("CUSTNO"));
                this.ORDERDATE = cur.getString(cur.getColumnIndex("ORDERDATE"));
                this.FLAG_VOID = cur.getString(cur.getColumnIndex("FLAG_VOID"));
                this.SKU = cur.getInt(cur.getColumnIndex("SKU"));
                this.INVAMOUNT = cur.getDouble(cur.getColumnIndex("INVAMOUNT"));
                this.REMARK = cur.getString(cur.getColumnIndex("REMARK"));
                this.FLAG_VOID = cur.getString(cur.getColumnIndex("FLAG_VOID"));
                this.PAYAMOUNT = cur.getDouble(cur.getColumnIndex("PAYAMOUNT"));
            }
            cur.close();
        }catch (Exception e){

        }


        return this;
    }

    public ArrayList<Obj_TRX_H> getDataList(){
        ArrayList<Obj_TRX_H> AlisData  = new ArrayList<Obj_TRX_H>();

        try{
            Cursor cur = AppConstant.myDb.rawQuery("SELECT * FROM Tbl_TRX_H", null);

            if (cur.getCount() > 0){
//			c.moveToFirst();
                while (cur.moveToNext()){
                    Obj_TRX_H prodDat = new Obj_TRX_H();
                    prodDat.ORDERNO = cur.getString(cur.getColumnIndex("ORDERNO"));
                    prodDat.CUSTNO = cur.getString(cur.getColumnIndex("CUSTNO"));
                    prodDat.ORDERDATE = cur.getString(cur.getColumnIndex("ORDERDATE"));
                    prodDat.FLAG_VOID = cur.getString(cur.getColumnIndex("FLAG_VOID"));
                    prodDat.SKU = cur.getInt(cur.getColumnIndex("SKU"));
                    prodDat.INVAMOUNT = cur.getDouble(cur.getColumnIndex("INVAMOUNT"));
                    prodDat.REMARK = cur.getString(cur.getColumnIndex("REMARK"));
                    prodDat.FLAG_VOID = cur.getString(cur.getColumnIndex("FLAG_VOID"));
                    prodDat.PAYAMOUNT = cur.getDouble(cur.getColumnIndex("PAYAMOUNT"));
                    AlisData.add(prodDat);
                }

            }
            cur.close();
        }catch (Exception e){

        }

        return AlisData;
    }

    public ArrayList<Obj_TRX_H> getDataListDashboard(){
        ArrayList<Obj_TRX_H> AlisData  = new ArrayList<Obj_TRX_H>();

        try{
            Cursor cur = AppConstant.myDb.rawQuery("SELECT CUSTNO, ORDERDATE, SUM(SKU) SKU, SUM(INVAMOUNT) INVAMOUNT FROM Tbl_TRX_H" +
                    " GROUP BY CUSTNO, ORDERDATE", null);

            if (cur.getCount() > 0){
//			c.moveToFirst();
                while (cur.moveToNext()){
                    Obj_TRX_H prodDat = new Obj_TRX_H();
                    prodDat.CUSTNO = cur.getString(cur.getColumnIndex("CUSTNO"));
                    prodDat.ORDERDATE = cur.getString(cur.getColumnIndex("ORDERDATE"));
                    prodDat.SKU = cur.getInt(cur.getColumnIndex("SKU"));
                    prodDat.INVAMOUNT = cur.getDouble(cur.getColumnIndex("INVAMOUNT"));
                    AlisData.add(prodDat);
                }

            }
            cur.close();
        }catch (Exception e){

        }

        return AlisData;
    }

    public ArrayList<Obj_TRX_H> getDataList(String ORDERNO){
        ArrayList<Obj_TRX_H> AlisData  = new ArrayList<Obj_TRX_H>();

        try{
            Cursor cur = AppConstant.myDb.rawQuery("SELECT * FROM Tbl_TRX_H" +
                    " WHERE ORDERNO = '"+ORDERNO+"'", null);

            if (cur.getCount() > 0){
//			c.moveToFirst();
                while (cur.moveToNext()){
                    Obj_TRX_H prodDat = new Obj_TRX_H();
                    prodDat.ORDERNO = cur.getString(cur.getColumnIndex("ORDERNO"));
                    prodDat.CUSTNO = cur.getString(cur.getColumnIndex("CUSTNO"));
                    prodDat.ORDERDATE = cur.getString(cur.getColumnIndex("ORDERDATE"));
                    prodDat.FLAG_VOID = cur.getString(cur.getColumnIndex("FLAG_VOID"));
                    prodDat.SKU = cur.getInt(cur.getColumnIndex("SKU"));
                    prodDat.INVAMOUNT = cur.getDouble(cur.getColumnIndex("INVAMOUNT"));
                    prodDat.REMARK = cur.getString(cur.getColumnIndex("REMARK"));
                    prodDat.PAYAMOUNT = cur.getDouble(cur.getColumnIndex("PAYAMOUNT"));
                    AlisData.add(prodDat);
                }

            }
            cur.close();
        }catch (Exception e){

        }

        return AlisData;
    }

    public ArrayList<Obj_TRX_H> getDataListCustNo(String CUSTNO){
        ArrayList<Obj_TRX_H> AlisData  = new ArrayList<Obj_TRX_H>();

        try{
            Cursor cur = AppConstant.myDb.rawQuery("SELECT A.*, B.FLAG_KIRIM FROM Tbl_TRX_H A" +
                    " LEFT JOIN Tbl_CUSTCARD B" +
                    " ON A.CUSTNO = B.CUSTNO AND A.ORDERNO = B.DOCNO" +
                    " WHERE A.CUSTNO = '"+CUSTNO+"' " +
                    " ORDER BY A.ORDERNO", null);

            if (cur.getCount() > 0){
//			c.moveToFirst();
                while (cur.moveToNext()){
                    Obj_TRX_H prodDat = new Obj_TRX_H();
                    prodDat.ORDERNO = cur.getString(cur.getColumnIndex("ORDERNO"));
                    prodDat.CUSTNO = cur.getString(cur.getColumnIndex("CUSTNO"));
                    prodDat.ORDERDATE = cur.getString(cur.getColumnIndex("ORDERDATE"));
                    prodDat.FLAG_VOID = cur.getString(cur.getColumnIndex("FLAG_VOID"));
                    prodDat.SKU = cur.getInt(cur.getColumnIndex("SKU"));
                    prodDat.INVAMOUNT = cur.getDouble(cur.getColumnIndex("INVAMOUNT"));
                    prodDat.REMARK = cur.getString(cur.getColumnIndex("REMARK"));
                    prodDat.FLAG_KIRIM = cur.getString(cur.getColumnIndex("FLAG_KIRIM"));
                    prodDat.PAYAMOUNT = cur.getDouble(cur.getColumnIndex("PAYAMOUNT"));
                    AlisData.add(prodDat);
                }

            }
            cur.close();
        }catch (Exception e){

        }

        return AlisData;
    }

    public ArrayList<Obj_TRX_H> getDataListTrx(){
        ArrayList<Obj_TRX_H> AlisData  = new ArrayList<Obj_TRX_H>();

        try{
            Cursor cur = AppConstant.myDb.rawQuery("SELECT * FROM Tbl_TRX_H" +
                    " GROUP BY CUSTNO", null);

            if (cur.getCount() > 0){
//			c.moveToFirst();
                while (cur.moveToNext()){
                    Obj_TRX_H prodDat = new Obj_TRX_H();
                    prodDat.ORDERNO = cur.getString(cur.getColumnIndex("ORDERNO"));
                    prodDat.CUSTNO = cur.getString(cur.getColumnIndex("CUSTNO"));
                    prodDat.ORDERDATE = cur.getString(cur.getColumnIndex("ORDERDATE"));
                    prodDat.FLAG_VOID = cur.getString(cur.getColumnIndex("FLAG_VOID"));
                    prodDat.SKU = cur.getInt(cur.getColumnIndex("SKU"));
                    prodDat.INVAMOUNT = cur.getDouble(cur.getColumnIndex("INVAMOUNT"));
                    prodDat.REMARK = cur.getString(cur.getColumnIndex("REMARK"));
                    prodDat.PAYAMOUNT = cur.getDouble(cur.getColumnIndex("PAYAMOUNT"));
                    AlisData.add(prodDat);
                }

            }
            cur.close();
        }catch (Exception e){

        }

        return AlisData;
    }

    public void CreateData(Obj_TRX_H dat){
        String sQry = "INSERT INTO Tbl_TRX_H(" +
                "ORDERNO," +
                "CUSTNO," +
                "REMARK," +
                "ORDERDATE," +
                "FLAG_VOID," +
                "SKU," +
                "PAYAMOUNT," +
                "INVAMOUNT" +
                ")VALUES(" +
                "'"+dat.ORDERNO+"'," +
                "'"+dat.CUSTNO+"'," +
                "'"+dat.REMARK+"'," +
                "'"+dat.ORDERDATE+"'," +
                "'N'," +
                "'"+dat.SKU+"'," +
                ""+dat.PAYAMOUNT+","+
                ""+dat.INVAMOUNT+")"
                ;

        try{
            AppConstant.myDb.execSQL(sQry);
        }catch (Exception e){
            Log.w("Error insertTrx_H", e.getMessage());
        }
    }

    public void DeleteData(){
        String sQry = "DELETE FROM TBL_TRX_H"
                ;

        try{
            AppConstant.myDb.execSQL(sQry);
        }catch (Exception e){
            Log.w("Error insertTrx_H", e.getMessage());
        }
    }

    public void UpdateFlagVoid(String ORDERNO){
        String sQry = "UPDATE TBL_TRX_H" +
                " SET FLAG_VOID = 'Y'" +
                " WHERE ORDERNO = '"+ORDERNO+"'"

                ;

        try{
            AppConstant.myDb.execSQL(sQry);
        }catch (Exception e){
            Log.w("Error insertTrx_H", e.getMessage());
        }
    }

    public void DeleteData(String ORDERNO){
        String sQry = "DELETE FROM TBL_TRX_H" +
                " WHERE ORDERNO = '"+ORDERNO+"'"
                ;

        try{
            AppConstant.myDb.execSQL(sQry);
        }catch (Exception e){
            Log.w("Error insertTrx_H", e.getMessage());
        }
    }

    public double TotalInvAmount(){
        String sSql = "Select sum(AMOUNT) as TotInv FROM TBL_TRX_D";

        double dTotInv = 0;
        try {
            Cursor c = AppConstant.myDb.rawQuery(sSql, null);
            if (c.moveToFirst())
                dTotInv = c.getDouble(c.getColumnIndex("TotInv"));
            c.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return dTotInv;
    }

    public double TotalQty(){
        String sSql = "Select sum(QTY_PCS) as TotQty FROM TBL_TRX_D";

        double dTotInv = 0;
        try {
            Cursor c = AppConstant.myDb.rawQuery(sSql, null);
            if (c.moveToFirst())
                dTotInv = c.getDouble(c.getColumnIndex("TotQty"));
            c.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return dTotInv;
    }

    public double TotalQtySales(){
        String sSql = "SELECT A.PCODE, SUM(A.QTY_PCS) TotQty FROM TBL_TRX_D A " +
                "   INNER JOIN TBL_TRX_H B ON A.ORDERNO = B.ORDERNO  WHERE B.FLAG_VOID = 'N' GROUP BY PCODE";

        double dTotInv = 0;
        try {
            Cursor c = AppConstant.myDb.rawQuery(sSql, null);
            if (c.moveToFirst())
                dTotInv = c.getDouble(c.getColumnIndex("TotQty"));
            c.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return dTotInv;
    }
}
