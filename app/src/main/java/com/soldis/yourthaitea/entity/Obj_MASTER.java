package com.soldis.yourthaitea.entity;

import android.database.Cursor;
import android.util.Log;

import com.soldis.yourthaitea.AppConstant;

import java.util.ArrayList;


public class Obj_MASTER {
	private String PCODE = "" ;
	private String DATA1 = "" ;
	private String PCODENAME = "" ;
	private String UNIT1 = "" ;
	private String UNIT2 = "" ;
	private String UNIT3 = "" ;
	private long CONVUNIT2 = 0 ;
	private long CONVUNIT3 = 0 ;
	private double SELLPRICE1 = 0 ;
	private double SELLPRICE2 = 0 ;
	private double SELLPRICE3 = 0 ;
	private double PBMJUAL = 0 ;
	private long STOCK = 0 ;
	private long STOCK_IN = 0 ;
	private long STOCK_OUT = 0 ;
	private String XSTOCK = "" ;
	private String FLAGFOCUS = "" ;
	private String PRLIN = "" ;
	private long QTYJUAL = 0 ;
	private String XQTYJUAL = "" ;
	private double PPN = 0 ;
	private double PPNDP = 0 ;
	private String BRAND = "" ;
	private String MG1 = "" ;
	private String MG2 = "" ;
	private String MG3 = "" ;
	private String MG4 = "" ;
	private String MG5 = "" ;
	private int XTOP = 0 ;
	private String FLAGDISC;
	private long MAXDISC;
	private double NETWEIGHT = 0 ;
	private String FLAG_NEW = "";
	private String FLAG_SALES = "";
	private String PHOTO_NAME = "";

	private String INP_UOM1 = "";
	private String INP_UOM2 = "";
	private String INP_UOM3 = "";
	private double TOTAL = 0;
	private long QTY_PCS = 0 ;
	private long QTY_TRX = 0 ;
	private long STOCK_MOTORIS = 0 ;
	private double INVAMOUNT = 0;

	private int QTY_INPUT = 0 ;

	public long getSTOCK_IN() {
		return STOCK_IN;
	}

	public void setSTOCK_IN(long STOCK_IN) {
		this.STOCK_IN = STOCK_IN;
	}

	public long getSTOCK_OUT() {
		return STOCK_OUT;
	}

	public void setSTOCK_OUT(long STOCK_OUT) {
		this.STOCK_OUT = STOCK_OUT;
	}

	public String getFLAG_SALES() {
		return FLAG_SALES;
	}

	public void setFLAG_SALES(String FLAG_SALES) {
		this.FLAG_SALES = FLAG_SALES;
	}

	public int getQTY_INPUT() {
		return QTY_INPUT;
	}

	public void setQTY_INPUT(int QTY_INPUT) {
		this.QTY_INPUT = QTY_INPUT;
	}

	public double getINVAMOUNT() {
		return INVAMOUNT;
	}

	public void setINVAMOUNT(double INVAMOUNT) {
		this.INVAMOUNT = INVAMOUNT;
	}

	public long getQTY_TRX() {
		return QTY_TRX;
	}

	public void setQTY_TRX(long QTY_TRX) {
		this.QTY_TRX = QTY_TRX;
	}

	public long getSTOCK_MOTORIS() {
		return STOCK_MOTORIS;
	}

	public void setSTOCK_MOTORIS(long STOCK_MOTORIS) {
		this.STOCK_MOTORIS = STOCK_MOTORIS;
	}

	public long getQTY_PCS() {
		return QTY_PCS;
	}

	public void setQTY_PCS(long QTY_PCS) {
		this.QTY_PCS = QTY_PCS;
	}

	public double getTOTAL() {
		return TOTAL;
	}

	public void setTOTAL(double TOTAL) {
		this.TOTAL = TOTAL;
	}

	public String getINP_UOM1() {
		return INP_UOM1;
	}

	public void setINP_UOM1(String INP_UOM1) {
		this.INP_UOM1 = INP_UOM1;
	}

	public String getINP_UOM2() {
		return INP_UOM2;
	}

	public void setINP_UOM2(String INP_UOM2) {
		this.INP_UOM2 = INP_UOM2;
	}

	public String getINP_UOM3() {
		return INP_UOM3;
	}

	public void setINP_UOM3(String INP_UOM3) {
		this.INP_UOM3 = INP_UOM3;
	}

	public String getPHOTO_NAME() {
		return PHOTO_NAME;
	}

	public void setPHOTO_NAME(String PHOTO_NAME) {
		this.PHOTO_NAME = PHOTO_NAME;
	}

	public String getPCODE_URL() {
		return PCODE_URL;
	}

	public void setPCODE_URL(String PCODE_URL) {
		this.PCODE_URL = PCODE_URL;
	}

	private String FILE_NEW = "";
	private String TYPEOUT = "";
	private String PCODE_URL = "";
	private int jum = 0;

	public String TMP_QTYA;
	
	private int QTY = 0;

	public String tmp_price = "";
	
	public Obj_MASTER(){}

	public int getQty() {
		return QTY;
	}
	public void setQty(int sValue) {
		this.QTY = sValue;
	}
	
	public String getPCode() {
		return PCODE;
	}
	public void setPCode(String sValue) {
		this.PCODE = sValue;
	}
	
	public String getFILE_NEW() {
		return FILE_NEW;
	}
	public void setFILE_NEW(String sValue) {
		this.FILE_NEW = sValue;
	}
	
	public String getTYPEOUT() {
		return TYPEOUT;
	}
	public void setTYPEOUT(String sValue) {
		this.TYPEOUT = sValue;
	}
	
	public String getFLAG_NEW() {
		return FLAG_NEW;
	}
	public void setFLAG_NEW(String sValue) {
		this.FLAG_NEW = sValue;
	}
	
	public String getData01() {
		return DATA1;
	}
	public void setData01(String sValue) {
		this.DATA1 = sValue;
	}
	
	public String getPCodeName() {
		return PCODENAME;
	}
	public void setPCodeName(String sValue) {
		this.PCODENAME = sValue;
	}
	
	public String getUnit1() {
		return UNIT1;
	}
	public void setUnit1(String sValue) {
		this.UNIT1 = sValue;
	}
	
	public String getUnit2() {
		return UNIT2;
	}
	public void setUnit2(String sValue) {
		this.UNIT2 = sValue;
	}
	
	public String getUnit3() {
		return UNIT3;
	}
	public void setUnit3(String sValue) {
		this.UNIT3 = sValue;
	}
	
	public long getConvUnit2() {
		return CONVUNIT2;
	}
	public void setConvUnit2(long sValue) {
		this.CONVUNIT2 = sValue;
	}
	
	public long getConvUnit3() {
		return CONVUNIT3;
	}
	public void setConvUnit3(long sValue) {
		this.CONVUNIT3 = sValue;
	}
	
	public double getSellPrice1() {
		return SELLPRICE1;
	}
	public void setSellPrice1(double sValue) {
		this.SELLPRICE1 = sValue;
	}
	
	public double getSellPrice2() {
		return SELLPRICE2;
	}
	public void setSellPrice2(double sValue) {
		this.SELLPRICE2 = sValue;
	}
	
	public double getSellPrice3() {
		return SELLPRICE3;
	}
	public void setSellPrice3(double sValue) {
		this.SELLPRICE3 = sValue;
	}
	
	public double getPbmJual() {
		return PBMJUAL;
	}
	public void setPbmJual(double sValue) {
		this.PBMJUAL = sValue;
	}
	
	public long getStock() {
		return STOCK;
	}
	public void setStock(long sValue) {
		this.STOCK = sValue;
	}
	
	public String getXStock() {
		return XSTOCK;
	}
	public void setXStock(String sValue) {
		this.XSTOCK = sValue;
	}

	public String getFlagFocus() {
		return FLAGFOCUS;
	}
	public void setFlagFocus(String sValue) {
		this.FLAGFOCUS = sValue;
	}
	
	public String getPrLin() {
		return PRLIN;
	}
	public void setPrLin(String sValue) {
		this.PRLIN = sValue;
	}
	
	public long getQtyJual() {
		return QTYJUAL;
	}
	public void setQtyJual(long sValue) {
		this.QTYJUAL = sValue;
	}
	
	public String getXQtyJual() {
		return XQTYJUAL;
	}
	public void setXQtyJual(String sValue) {
		this.XQTYJUAL = sValue;
	}
	
	public double getPPN() {
		return PPN;
	}
	public void setPPN(double sValue) {
		this.PPN = sValue;
	}
	
	public double getPPNDP() {
		return PPNDP;
	}
	public void setPPNDP(double sValue) {
		this.PPNDP = sValue;
	}
	
	public String getBrand() {
		return BRAND;
	}
	public void setBrand(String sValue) {
		this.BRAND = sValue;
	}
	
	public String getMG1() {
		return MG1;
	}
	public void setMG1(String sValue) {
		this.MG1 = sValue;
	}
	
	public String getMG2() {
		return MG2;
	}
	public void setMG2(String sValue) {
		this.MG2 = sValue;
	}
	
	public String getMG3() {
		return MG3;
	}
	public void setMG3(String sValue) {
		this.MG3 = sValue;
	}
	
	public String getMG4() {
		return MG4;
	}
	public void setMG4(String sValue) {
		this.MG4 = sValue;
	}
	
	public String getMG5() {
		return MG5;
	}
	public void setMG5(String sValue) {
		this.MG5 = sValue;
	}
	
	public int getXTOP() {
		return XTOP;
	}
	public void setXTOP(int sValue) {
		this.XTOP = sValue;
	}
	
	public long getMAXDISC() {
		return MAXDISC;
	}
	public void setMAXDISC(long sValue) {
		this.MAXDISC = sValue;
	}
	
	public String getFlagDisc() {
		return FLAGDISC;
	}
	public void setFlagDisc(String sValue) {
		this.FLAGDISC = sValue;
	}
	
	public double getNETWEIGHT() {
		return NETWEIGHT;
	}
	public void setNETWEIGHT(double sValue) {
		this.NETWEIGHT = sValue;
	}
	
	public int getJum() {
		return jum;
	}
	public void setJum(int sValue) {
		this.jum = sValue;
	}
	
	public Obj_MASTER getData(String sPCode){
		try{
			Cursor cur = AppConstant.myDb.rawQuery("SELECT * FROM Tbl_MASTER WHERE PCODE = '"+sPCode+"'", null);
			if (cur.getCount() > 0){
				cur.moveToFirst();
				this.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
				this.DATA1 = cur.getString(cur.getColumnIndex("DATA1"));
				this.PCODENAME = cur.getString(cur.getColumnIndex("PCODENAME"));
				this.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
				this.UNIT1 = cur.getString(cur.getColumnIndex("UNIT1"));
				this.UNIT2 = cur.getString(cur.getColumnIndex("UNIT2"));
				this.UNIT3 = cur.getString(cur.getColumnIndex("UNIT3"));
				this.CONVUNIT2 = cur.getLong(cur.getColumnIndex("CONVUNIT2"));
				this.CONVUNIT3 = cur.getLong(cur.getColumnIndex("CONVUNIT3"));
				this.SELLPRICE1 = cur.getDouble(cur.getColumnIndex("SELLPRICE1"));
				this.SELLPRICE2 = cur.getDouble(cur.getColumnIndex("SELLPRICE2"));
				this.SELLPRICE3 = cur.getDouble(cur.getColumnIndex("SELLPRICE3"));
				this.PBMJUAL = cur.getDouble(cur.getColumnIndex("PBMJUAL"));
				this.STOCK = cur.getLong(cur.getColumnIndex("STOCK"));
				this.XSTOCK = cur.getString(cur.getColumnIndex("XSTOCK"));
				this.FLAGFOCUS = cur.getString(cur.getColumnIndex("FLAGFOCUS"));
				this.PRLIN = cur.getString(cur.getColumnIndex("PRLIN"));
				this.QTYJUAL = cur.getLong(cur.getColumnIndex("QTYJUAL"));
				this.XQTYJUAL = cur.getString(cur.getColumnIndex("XQTYJUAL"));
				this.PPN = cur.getDouble(cur.getColumnIndex("PPN"));
				this.PPNDP = cur.getDouble(cur.getColumnIndex("PPNDP"));
				this.BRAND = cur.getString(cur.getColumnIndex("BRAND"));
				this.MG1 = cur.getString(cur.getColumnIndex("MG1"));
				this.MG2 = cur.getString(cur.getColumnIndex("MG2"));
				this.MG3 = cur.getString(cur.getColumnIndex("MG3"));
				this.MG4 = cur.getString(cur.getColumnIndex("MG4"));
				this.MG5 = cur.getString(cur.getColumnIndex("MG5"));
				this.XTOP = cur.getInt(cur.getColumnIndex("XTOP"));
				this.MAXDISC = cur.getLong(cur.getColumnIndex("MAXDISC"));
				this.FLAGDISC = cur.getString(cur.getColumnIndex("FLAGDISC"));
				this.NETWEIGHT = cur.getDouble(cur.getColumnIndex("NETWEIGHT"));
				this.FLAG_NEW = cur.getString(cur.getColumnIndex("FLAG_NEW"));
				this.FILE_NEW = cur.getString(cur.getColumnIndex("FILE_NEW"));
				this.PHOTO_NAME = cur.getString(cur.getColumnIndex("PHOTO_NAME"));
				this.INP_UOM1 = "";
				this.INP_UOM2 = "";
				this.INP_UOM3 = "";
				this.QTY_INPUT = 0;
				this.TOTAL = 0;
			}
			cur.close();
		}catch (Exception e){

		}

	
		return this;
	}

	public Obj_MASTER getDataStock(){
		try{
			Cursor cur = AppConstant.myDb.rawQuery("SELECT SUM(C.STOCK) AS STOCK_MOTORIS, SUM(B.QTY_PCS) AS QTY_PCS  FROM TBL_MASTER A" +
					"	LEFT JOIN (" +
					"	SELECT PCODE, SUM(QTY_PCS) QTY_PCS FROM TBL_TRX_D" +
					"	GROUP BY PCODE) B" +
					"	ON A.PCODE = B.PCODE" +
					"	LEFT JOIN TBL_STOCK C" +
					"	ON A.PCODE = C.PCODE" +
					"	WHERE A.FLAG_SALES = 'Y'", null);
			if (cur.getCount() > 0){
				cur.moveToFirst();
				this.STOCK_MOTORIS = cur.getLong(cur.getColumnIndex("STOCK_MOTORIS"));
				this.QTY_PCS = cur.getLong(cur.getColumnIndex("QTY_PCS"));
			}
			cur.close();
		}catch (Exception e){

		}


		return this;
	}

	public Obj_MASTER getDataStockTMP(){
		try{
			Cursor cur = AppConstant.myDb.rawQuery("SELECT SUM(C.STOCK) AS STOCK_MOTORIS, SUM(B.QTY_PCS) AS QTY_PCS  FROM TBL_MASTER A" +
					"	LEFT JOIN (" +
					"	SELECT PCODE, SUM(QTY_PCS) QTY_PCS FROM TBL_TRX_D" +
					"	GROUP BY PCODE) B" +
					"	ON A.PCODE = B.PCODE" +
					"	LEFT JOIN TBL_STOCK_TMP C" +
					"	ON A.PCODE = C.PCODE" +
					"	WHERE A.FLAG_SALES = 'Y'", null);
			if (cur.getCount() > 0){
				cur.moveToFirst();
				this.STOCK_MOTORIS = cur.getLong(cur.getColumnIndex("STOCK_MOTORIS"));
				this.QTY_PCS = cur.getLong(cur.getColumnIndex("QTY_PCS"));
			}
			cur.close();
		}catch (Exception e){

		}


		return this;
	}

	public ArrayList<Obj_MASTER> getDataListAwal(){
		ArrayList<Obj_MASTER> AlisData  = new ArrayList<Obj_MASTER>();

		try{
			Cursor cur = AppConstant.myDb.rawQuery("SELECT C.STOCK AS STOCK_MOTORIS, A.*, B.QTY_PCS, B.INVAMOUNT  FROM TBL_MASTER A" +
					"	LEFT JOIN (" +
					"	SELECT PCODE, '0' AS QTY_PCS, '0' AS INVAMOUNT FROM TBL_TRX_D" +
					"	GROUP BY PCODE) B" +
					"	ON A.PCODE = B.PCODE" +
					"	LEFT JOIN TBL_STOCK C" +
					"	ON A.PCODE = C.PCODE" +
					"	WHERE A.FLAG_SALES = 'Y'", null);

			if (cur.getCount() > 0){
//			c.moveToFirst();
				while (cur.moveToNext()){
					Obj_MASTER prodDat = new Obj_MASTER();
					prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
					prodDat.DATA1 = cur.getString(cur.getColumnIndex("DATA1"));
					prodDat.PCODENAME = cur.getString(cur.getColumnIndex("PCODENAME"));
					prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
					prodDat.UNIT1 = cur.getString(cur.getColumnIndex("UNIT1"));
					prodDat.UNIT2 = cur.getString(cur.getColumnIndex("UNIT2"));
					prodDat.UNIT3 = cur.getString(cur.getColumnIndex("UNIT3"));
					prodDat.CONVUNIT2 = cur.getLong(cur.getColumnIndex("CONVUNIT2"));
					prodDat.CONVUNIT3 = cur.getLong(cur.getColumnIndex("CONVUNIT3"));
					prodDat.SELLPRICE1 = cur.getDouble(cur.getColumnIndex("SELLPRICE1"));
					prodDat.SELLPRICE2 = cur.getDouble(cur.getColumnIndex("SELLPRICE2"));
					prodDat.SELLPRICE3 = cur.getDouble(cur.getColumnIndex("SELLPRICE3"));
					prodDat.PBMJUAL = cur.getDouble(cur.getColumnIndex("PBMJUAL"));
					prodDat.XSTOCK = cur.getString(cur.getColumnIndex("XSTOCK"));
					prodDat.STOCK = cur.getLong(cur.getColumnIndex("STOCK"));
					prodDat.QTY_PCS = cur.getLong(cur.getColumnIndex("QTY_PCS"));
					prodDat.FLAGFOCUS = cur.getString(cur.getColumnIndex("FLAGFOCUS"));
					prodDat.PRLIN = cur.getString(cur.getColumnIndex("PRLIN"));
					prodDat.QTYJUAL = cur.getLong(cur.getColumnIndex("QTYJUAL"));
					prodDat.XQTYJUAL = cur.getString(cur.getColumnIndex("XQTYJUAL"));
					prodDat.PPN = cur.getDouble(cur.getColumnIndex("PPN"));
					prodDat.PPNDP = cur.getDouble(cur.getColumnIndex("PPNDP"));
					prodDat.BRAND = cur.getString(cur.getColumnIndex("BRAND"));
					prodDat.MG1 = cur.getString(cur.getColumnIndex("MG1"));
					prodDat.MG2 = cur.getString(cur.getColumnIndex("MG2"));
					prodDat.MG3 = cur.getString(cur.getColumnIndex("MG3"));
					prodDat.MG4 = cur.getString(cur.getColumnIndex("MG4"));
					prodDat.MG5 = cur.getString(cur.getColumnIndex("MG5"));
					prodDat.XTOP = cur.getInt(cur.getColumnIndex("XTOP"));
					prodDat.MAXDISC = cur.getLong(cur.getColumnIndex("MAXDISC"));
					prodDat.FLAGDISC = cur.getString(cur.getColumnIndex("FLAGDISC"));
					prodDat.NETWEIGHT = cur.getDouble(cur.getColumnIndex("NETWEIGHT"));
					prodDat.PHOTO_NAME = cur.getString(cur.getColumnIndex("PHOTO_NAME"));
					prodDat.STOCK_MOTORIS = cur.getLong(cur.getColumnIndex("STOCK_MOTORIS"));
					prodDat.INP_UOM1 = "";
					prodDat.INP_UOM2 = "";
					prodDat.INP_UOM3 = "";
					prodDat.QTY_INPUT = 0;
					prodDat.TOTAL = 0;
					prodDat.INVAMOUNT = cur.getDouble(cur.getColumnIndex("INVAMOUNT"));

					AlisData.add(prodDat);
				}

			}
			cur.close();
		}catch (Exception e){

		}

		return AlisData;
	}

	public ArrayList<Obj_MASTER> getDataListDispenser(){
		ArrayList<Obj_MASTER> AlisData  = new ArrayList<Obj_MASTER>();

		try{
			Cursor cur = AppConstant.myDb.rawQuery("SELECT C.STOCK AS STOCK_MOTORIS, A.*, B.QTY_PCS, B.INVAMOUNT  FROM TBL_MASTER A" +
					"	LEFT JOIN (" +
					"	SELECT PCODE, '0' AS QTY_PCS, '0' AS INVAMOUNT FROM TBL_TRX_D" +
					"	GROUP BY PCODE) B" +
					"	ON A.PCODE = B.PCODE" +
					"	LEFT JOIN TBL_STOCK C" +
					"	ON A.PCODE = C.PCODE" +
					"	WHERE A.FLAG_SALES = 'N'", null);

			if (cur.getCount() > 0){
//			c.moveToFirst();
				while (cur.moveToNext()){
					Obj_MASTER prodDat = new Obj_MASTER();
					prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
					prodDat.DATA1 = cur.getString(cur.getColumnIndex("DATA1"));
					prodDat.PCODENAME = cur.getString(cur.getColumnIndex("PCODENAME"));
					prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
					prodDat.UNIT1 = cur.getString(cur.getColumnIndex("UNIT1"));
					prodDat.UNIT2 = cur.getString(cur.getColumnIndex("UNIT2"));
					prodDat.UNIT3 = cur.getString(cur.getColumnIndex("UNIT3"));
					prodDat.CONVUNIT2 = cur.getLong(cur.getColumnIndex("CONVUNIT2"));
					prodDat.CONVUNIT3 = cur.getLong(cur.getColumnIndex("CONVUNIT3"));
					prodDat.SELLPRICE1 = cur.getDouble(cur.getColumnIndex("SELLPRICE1"));
					prodDat.SELLPRICE2 = cur.getDouble(cur.getColumnIndex("SELLPRICE2"));
					prodDat.SELLPRICE3 = cur.getDouble(cur.getColumnIndex("SELLPRICE3"));
					prodDat.PBMJUAL = cur.getDouble(cur.getColumnIndex("PBMJUAL"));
					prodDat.XSTOCK = cur.getString(cur.getColumnIndex("XSTOCK"));
					prodDat.STOCK = cur.getLong(cur.getColumnIndex("STOCK"));
					prodDat.QTY_PCS = cur.getLong(cur.getColumnIndex("QTY_PCS"));
					prodDat.FLAGFOCUS = cur.getString(cur.getColumnIndex("FLAGFOCUS"));
					prodDat.PRLIN = cur.getString(cur.getColumnIndex("PRLIN"));
					prodDat.QTYJUAL = cur.getLong(cur.getColumnIndex("QTYJUAL"));
					prodDat.XQTYJUAL = cur.getString(cur.getColumnIndex("XQTYJUAL"));
					prodDat.PPN = cur.getDouble(cur.getColumnIndex("PPN"));
					prodDat.PPNDP = cur.getDouble(cur.getColumnIndex("PPNDP"));
					prodDat.BRAND = cur.getString(cur.getColumnIndex("BRAND"));
					prodDat.MG1 = cur.getString(cur.getColumnIndex("MG1"));
					prodDat.MG2 = cur.getString(cur.getColumnIndex("MG2"));
					prodDat.MG3 = cur.getString(cur.getColumnIndex("MG3"));
					prodDat.MG4 = cur.getString(cur.getColumnIndex("MG4"));
					prodDat.MG5 = cur.getString(cur.getColumnIndex("MG5"));
					prodDat.XTOP = cur.getInt(cur.getColumnIndex("XTOP"));
					prodDat.MAXDISC = cur.getLong(cur.getColumnIndex("MAXDISC"));
					prodDat.FLAGDISC = cur.getString(cur.getColumnIndex("FLAGDISC"));
					prodDat.NETWEIGHT = cur.getDouble(cur.getColumnIndex("NETWEIGHT"));
					prodDat.PHOTO_NAME = cur.getString(cur.getColumnIndex("PHOTO_NAME"));
					prodDat.STOCK_MOTORIS = cur.getLong(cur.getColumnIndex("STOCK_MOTORIS"));
					prodDat.INP_UOM1 = "";
					prodDat.INP_UOM2 = "";
					prodDat.INP_UOM3 = "";
					prodDat.QTY_INPUT = 0;
					prodDat.TOTAL = 0;
					prodDat.INVAMOUNT = cur.getDouble(cur.getColumnIndex("INVAMOUNT"));

					AlisData.add(prodDat);
				}

			}
			cur.close();
		}catch (Exception e){

		}

		return AlisData;
	}


	public ArrayList<Obj_MASTER> getDataListTrx(){
		ArrayList<Obj_MASTER> AlisData  = new ArrayList<Obj_MASTER>();

		try{
			Cursor cur = AppConstant.myDb.rawQuery("SELECT C.STOCK AS STOCK_MOTORIS, A.*, B.QTY_PCS, B.INVAMOUNT  FROM TBL_MASTER A" +
					"	LEFT JOIN (" +
					"	SELECT PCODE, SUM(QTY_PCS) QTY_PCS, SUM(AMOUNT) AS INVAMOUNT FROM TBL_TRX_D" +
					"	GROUP BY PCODE) B" +
					"	ON A.PCODE = B.PCODE" +
					"	LEFT JOIN TBL_STOCK C" +
					"	ON A.PCODE = C.PCODE" +
					"  WHERE A.FLAG_SALES = 'Y'", null);

			if (cur.getCount() > 0){
//			c.moveToFirst();
				while (cur.moveToNext()){
					Obj_MASTER prodDat = new Obj_MASTER();
					prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
					prodDat.DATA1 = cur.getString(cur.getColumnIndex("DATA1"));
					prodDat.PCODENAME = cur.getString(cur.getColumnIndex("PCODENAME"));
					prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
					prodDat.UNIT1 = cur.getString(cur.getColumnIndex("UNIT1"));
					prodDat.UNIT2 = cur.getString(cur.getColumnIndex("UNIT2"));
					prodDat.UNIT3 = cur.getString(cur.getColumnIndex("UNIT3"));
					prodDat.CONVUNIT2 = cur.getLong(cur.getColumnIndex("CONVUNIT2"));
					prodDat.CONVUNIT3 = cur.getLong(cur.getColumnIndex("CONVUNIT3"));
					prodDat.SELLPRICE1 = cur.getDouble(cur.getColumnIndex("SELLPRICE1"));
					prodDat.SELLPRICE2 = cur.getDouble(cur.getColumnIndex("SELLPRICE2"));
					prodDat.SELLPRICE3 = cur.getDouble(cur.getColumnIndex("SELLPRICE3"));
					prodDat.PBMJUAL = cur.getDouble(cur.getColumnIndex("PBMJUAL"));
					prodDat.XSTOCK = cur.getString(cur.getColumnIndex("XSTOCK"));
					prodDat.STOCK = cur.getLong(cur.getColumnIndex("STOCK"));
					prodDat.QTY_PCS = cur.getLong(cur.getColumnIndex("QTY_PCS"));
					prodDat.FLAGFOCUS = cur.getString(cur.getColumnIndex("FLAGFOCUS"));
					prodDat.PRLIN = cur.getString(cur.getColumnIndex("PRLIN"));
					prodDat.QTYJUAL = cur.getLong(cur.getColumnIndex("QTYJUAL"));
					prodDat.XQTYJUAL = cur.getString(cur.getColumnIndex("XQTYJUAL"));
					prodDat.PPN = cur.getDouble(cur.getColumnIndex("PPN"));
					prodDat.PPNDP = cur.getDouble(cur.getColumnIndex("PPNDP"));
					prodDat.BRAND = cur.getString(cur.getColumnIndex("BRAND"));
					prodDat.MG1 = cur.getString(cur.getColumnIndex("MG1"));
					prodDat.MG2 = cur.getString(cur.getColumnIndex("MG2"));
					prodDat.MG3 = cur.getString(cur.getColumnIndex("MG3"));
					prodDat.MG4 = cur.getString(cur.getColumnIndex("MG4"));
					prodDat.MG5 = cur.getString(cur.getColumnIndex("MG5"));
					prodDat.XTOP = cur.getInt(cur.getColumnIndex("XTOP"));
					prodDat.MAXDISC = cur.getLong(cur.getColumnIndex("MAXDISC"));
					prodDat.FLAGDISC = cur.getString(cur.getColumnIndex("FLAGDISC"));
					prodDat.NETWEIGHT = cur.getDouble(cur.getColumnIndex("NETWEIGHT"));
					prodDat.PHOTO_NAME = cur.getString(cur.getColumnIndex("PHOTO_NAME"));
					prodDat.STOCK_MOTORIS = cur.getLong(cur.getColumnIndex("STOCK_MOTORIS"));
					prodDat.INP_UOM1 = "";
					prodDat.INP_UOM2 = "";
					prodDat.INP_UOM3 = "";
					prodDat.QTY_INPUT = 0;
					prodDat.TOTAL = 0;
					prodDat.INVAMOUNT = cur.getDouble(cur.getColumnIndex("INVAMOUNT"));

					AlisData.add(prodDat);
				}

			}
			cur.close();
		}catch (Exception e){

		}

		return AlisData;
	}

	public ArrayList<Obj_MASTER> getDataListSumTrx(){
		ArrayList<Obj_MASTER> AlisData  = new ArrayList<Obj_MASTER>();

		try{
			Cursor cur = AppConstant.myDb.rawQuery("SELECT A.QTY_TRX, A.INVAMOUNT, B.* FROM (" +
					"	SELECT A.PCODE, SUM(A.QTY_PCS) QTY_TRX, SUM(A.AMOUNT) INVAMOUNT" +
					"	FROM TBL_TRX_D A INNER JOIN TBL_TRX_H B" +
					"   ON A.ORDERNO = B.ORDERNO WHERE B.FLAG_VOID = 'N' " +
					"	GROUP BY A.PCODE" +
					"	)A LEFT JOIN TBL_MASTER B" +
					"	ON A.PCODE = B.PCODE ", null);

			if (cur.getCount() > 0){
//			c.moveToFirst();
				while (cur.moveToNext()){
					Obj_MASTER prodDat = new Obj_MASTER();
					prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
					prodDat.DATA1 = cur.getString(cur.getColumnIndex("DATA1"));
					prodDat.PCODENAME = cur.getString(cur.getColumnIndex("PCODENAME"));
					prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
					prodDat.UNIT1 = cur.getString(cur.getColumnIndex("UNIT1"));
					prodDat.UNIT2 = cur.getString(cur.getColumnIndex("UNIT2"));
					prodDat.UNIT3 = cur.getString(cur.getColumnIndex("UNIT3"));
					prodDat.CONVUNIT2 = cur.getLong(cur.getColumnIndex("CONVUNIT2"));
					prodDat.CONVUNIT3 = cur.getLong(cur.getColumnIndex("CONVUNIT3"));
					prodDat.SELLPRICE1 = cur.getDouble(cur.getColumnIndex("SELLPRICE1"));
					prodDat.SELLPRICE2 = cur.getDouble(cur.getColumnIndex("SELLPRICE2"));
					prodDat.SELLPRICE3 = cur.getDouble(cur.getColumnIndex("SELLPRICE3"));
					prodDat.QTY_TRX = cur.getLong(cur.getColumnIndex("QTY_TRX"));
					prodDat.MAXDISC = cur.getLong(cur.getColumnIndex("MAXDISC"));
					prodDat.FLAGDISC = cur.getString(cur.getColumnIndex("FLAGDISC"));
					prodDat.NETWEIGHT = cur.getDouble(cur.getColumnIndex("NETWEIGHT"));
					prodDat.PHOTO_NAME = cur.getString(cur.getColumnIndex("PHOTO_NAME"));
					prodDat.INVAMOUNT = cur.getDouble(cur.getColumnIndex("INVAMOUNT"));

					AlisData.add(prodDat);
				}

			}
			cur.close();
		}catch (Exception e){

		}

		return AlisData;
	}


	public ArrayList<Obj_MASTER> getDataList(){
		ArrayList<Obj_MASTER> AlisData  = new ArrayList<Obj_MASTER>();

		try{
			Cursor cur = AppConstant.myDb.rawQuery("SELECT C.STOCK AS STOCK_MOTORIS, A.*, B.QTY_PCS, B.INVAMOUNT  FROM TBL_MASTER A" +
					"	LEFT JOIN (" +
					"	SELECT PCODE, SUM(QTY_PCS) QTY_PCS, SUM(AMOUNT) AS INVAMOUNT FROM TBL_TRX_D" +
					"	GROUP BY PCODE) B" +
					"	ON A.PCODE = B.PCODE" +
					"	LEFT JOIN TBL_STOCK C" +
					"	ON A.PCODE = C.PCODE" +
					" WHERE A.FLAG_SALES = 'Y'", null);

			if (cur.getCount() > 0){
//			c.moveToFirst();
				while (cur.moveToNext()){
					Obj_MASTER prodDat = new Obj_MASTER();
					prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
					prodDat.DATA1 = cur.getString(cur.getColumnIndex("DATA1"));
					prodDat.PCODENAME = cur.getString(cur.getColumnIndex("PCODENAME"));
					prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
					prodDat.UNIT1 = cur.getString(cur.getColumnIndex("UNIT1"));
					prodDat.UNIT2 = cur.getString(cur.getColumnIndex("UNIT2"));
					prodDat.UNIT3 = cur.getString(cur.getColumnIndex("UNIT3"));
					prodDat.CONVUNIT2 = cur.getLong(cur.getColumnIndex("CONVUNIT2"));
					prodDat.CONVUNIT3 = cur.getLong(cur.getColumnIndex("CONVUNIT3"));
					prodDat.SELLPRICE1 = cur.getDouble(cur.getColumnIndex("SELLPRICE1"));
					prodDat.SELLPRICE2 = cur.getDouble(cur.getColumnIndex("SELLPRICE2"));
					prodDat.SELLPRICE3 = cur.getDouble(cur.getColumnIndex("SELLPRICE3"));
					prodDat.PBMJUAL = cur.getDouble(cur.getColumnIndex("PBMJUAL"));
					prodDat.XSTOCK = cur.getString(cur.getColumnIndex("XSTOCK"));
					prodDat.STOCK = cur.getLong(cur.getColumnIndex("STOCK"));
					prodDat.QTY_PCS = cur.getLong(cur.getColumnIndex("QTY_PCS"));
					prodDat.FLAGFOCUS = cur.getString(cur.getColumnIndex("FLAGFOCUS"));
					prodDat.PRLIN = cur.getString(cur.getColumnIndex("PRLIN"));
					prodDat.QTYJUAL = cur.getLong(cur.getColumnIndex("QTYJUAL"));
					prodDat.XQTYJUAL = cur.getString(cur.getColumnIndex("XQTYJUAL"));
					prodDat.PPN = cur.getDouble(cur.getColumnIndex("PPN"));
					prodDat.PPNDP = cur.getDouble(cur.getColumnIndex("PPNDP"));
					prodDat.BRAND = cur.getString(cur.getColumnIndex("BRAND"));
					prodDat.MG1 = cur.getString(cur.getColumnIndex("MG1"));
					prodDat.MG2 = cur.getString(cur.getColumnIndex("MG2"));
					prodDat.MG3 = cur.getString(cur.getColumnIndex("MG3"));
					prodDat.MG4 = cur.getString(cur.getColumnIndex("MG4"));
					prodDat.MG5 = cur.getString(cur.getColumnIndex("MG5"));
					prodDat.XTOP = cur.getInt(cur.getColumnIndex("XTOP"));
					prodDat.MAXDISC = cur.getLong(cur.getColumnIndex("MAXDISC"));
					prodDat.FLAGDISC = cur.getString(cur.getColumnIndex("FLAGDISC"));
					prodDat.NETWEIGHT = cur.getDouble(cur.getColumnIndex("NETWEIGHT"));
					prodDat.PHOTO_NAME = cur.getString(cur.getColumnIndex("PHOTO_NAME"));
					prodDat.STOCK_MOTORIS = cur.getLong(cur.getColumnIndex("STOCK_MOTORIS"));
					prodDat.INP_UOM1 = "";
					prodDat.INP_UOM2 = "";
					prodDat.INP_UOM3 = "";
					prodDat.QTY_INPUT = 0;
					prodDat.TOTAL = 0;
					prodDat.INVAMOUNT = cur.getDouble(cur.getColumnIndex("INVAMOUNT"));

					AlisData.add(prodDat);
				}

			}
			cur.close();
		}catch (Exception e){

		}

		return AlisData;
	}

	public ArrayList<Obj_MASTER> getDataListSortBy(String CATEGORY_ID){
		ArrayList<Obj_MASTER> AlisData  = new ArrayList<Obj_MASTER>();

		try{
			Cursor cur = AppConstant.myDb.rawQuery("SELECT C.STOCK AS STOCK_MOTORIS, A.*, B.QTY_PCS, B.INVAMOUNT  FROM TBL_MASTER A" +
					"	LEFT JOIN (" +
					"	SELECT PCODE, SUM(QTY_PCS) QTY_PCS, SUM(AMOUNT) AS INVAMOUNT FROM TBL_TRX_D" +
					"	GROUP BY PCODE) B" +
					"	ON A.PCODE = B.PCODE" +
					"	LEFT JOIN TBL_STOCK C" +
					"	ON A.PCODE = C.PCODE" +
					"   WHERE A.PRLIN = '"+ CATEGORY_ID +"' AND A.FLAG_SALES = 'Y'", null);

			if (cur.getCount() > 0){
//			c.moveToFirst();
				while (cur.moveToNext()){
					Obj_MASTER prodDat = new Obj_MASTER();
					prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
					prodDat.DATA1 = cur.getString(cur.getColumnIndex("DATA1"));
					prodDat.PCODENAME = cur.getString(cur.getColumnIndex("PCODENAME"));
					prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
					prodDat.UNIT1 = cur.getString(cur.getColumnIndex("UNIT1"));
					prodDat.UNIT2 = cur.getString(cur.getColumnIndex("UNIT2"));
					prodDat.UNIT3 = cur.getString(cur.getColumnIndex("UNIT3"));
					prodDat.CONVUNIT2 = cur.getLong(cur.getColumnIndex("CONVUNIT2"));
					prodDat.CONVUNIT3 = cur.getLong(cur.getColumnIndex("CONVUNIT3"));
					prodDat.SELLPRICE1 = cur.getDouble(cur.getColumnIndex("SELLPRICE1"));
					prodDat.SELLPRICE2 = cur.getDouble(cur.getColumnIndex("SELLPRICE2"));
					prodDat.SELLPRICE3 = cur.getDouble(cur.getColumnIndex("SELLPRICE3"));
					prodDat.PBMJUAL = cur.getDouble(cur.getColumnIndex("PBMJUAL"));
					prodDat.XSTOCK = cur.getString(cur.getColumnIndex("XSTOCK"));
					prodDat.STOCK = cur.getLong(cur.getColumnIndex("STOCK"));
					prodDat.QTY_PCS = cur.getLong(cur.getColumnIndex("QTY_PCS"));
					prodDat.FLAGFOCUS = cur.getString(cur.getColumnIndex("FLAGFOCUS"));
					prodDat.PRLIN = cur.getString(cur.getColumnIndex("PRLIN"));
					prodDat.QTYJUAL = cur.getLong(cur.getColumnIndex("QTYJUAL"));
					prodDat.XQTYJUAL = cur.getString(cur.getColumnIndex("XQTYJUAL"));
					prodDat.PPN = cur.getDouble(cur.getColumnIndex("PPN"));
					prodDat.PPNDP = cur.getDouble(cur.getColumnIndex("PPNDP"));
					prodDat.BRAND = cur.getString(cur.getColumnIndex("BRAND"));
					prodDat.MG1 = cur.getString(cur.getColumnIndex("MG1"));
					prodDat.MG2 = cur.getString(cur.getColumnIndex("MG2"));
					prodDat.MG3 = cur.getString(cur.getColumnIndex("MG3"));
					prodDat.MG4 = cur.getString(cur.getColumnIndex("MG4"));
					prodDat.MG5 = cur.getString(cur.getColumnIndex("MG5"));
					prodDat.XTOP = cur.getInt(cur.getColumnIndex("XTOP"));
					prodDat.MAXDISC = cur.getLong(cur.getColumnIndex("MAXDISC"));
					prodDat.FLAGDISC = cur.getString(cur.getColumnIndex("FLAGDISC"));
					prodDat.NETWEIGHT = cur.getDouble(cur.getColumnIndex("NETWEIGHT"));
					prodDat.PHOTO_NAME = cur.getString(cur.getColumnIndex("PHOTO_NAME"));
					prodDat.STOCK_MOTORIS = cur.getLong(cur.getColumnIndex("STOCK_MOTORIS"));
					prodDat.INP_UOM1 = "";
					prodDat.INP_UOM2 = "";
					prodDat.INP_UOM3 = "";
					prodDat.QTY_INPUT = 0;
					prodDat.TOTAL = 0;
					prodDat.INVAMOUNT = cur.getDouble(cur.getColumnIndex("INVAMOUNT"));

					AlisData.add(prodDat);
				}

			}
			cur.close();
		}catch (Exception e){

		}

		return AlisData;
	}

	public ArrayList<Obj_MASTER> getDataListStockNew(){
		ArrayList<Obj_MASTER> AlisData  = new ArrayList<Obj_MASTER>();

		try{
			Cursor cur = AppConstant.myDb.rawQuery("SELECT C.STOCK AS STOCK_MOTORIS, A.*, B.QTY_PCS, B.INVAMOUNT  FROM TBL_MASTER A" +
					"	LEFT JOIN (" +
					"	SELECT PCODE, SUM(QTY_PCS) QTY_PCS, SUM(AMOUNT) AS INVAMOUNT FROM TBL_TRX_D" +
					"	GROUP BY PCODE) B" +
					"	ON A.PCODE = B.PCODE" +
					"	LEFT JOIN TBL_STOCK C" +
					"	ON A.PCODE = C.PCODE" +
					"   WHERE C.STOCK > 0 AND A.FLAG_SALES = 'Y'", null);

			if (cur.getCount() > 0){
//			c.moveToFirst();
				while (cur.moveToNext()){
					Obj_MASTER prodDat = new Obj_MASTER();
					prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
					prodDat.DATA1 = cur.getString(cur.getColumnIndex("DATA1"));
					prodDat.PCODENAME = cur.getString(cur.getColumnIndex("PCODENAME"));
					prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
					prodDat.UNIT1 = cur.getString(cur.getColumnIndex("UNIT1"));
					prodDat.UNIT2 = cur.getString(cur.getColumnIndex("UNIT2"));
					prodDat.UNIT3 = cur.getString(cur.getColumnIndex("UNIT3"));
					prodDat.CONVUNIT2 = cur.getLong(cur.getColumnIndex("CONVUNIT2"));
					prodDat.CONVUNIT3 = cur.getLong(cur.getColumnIndex("CONVUNIT3"));
					prodDat.SELLPRICE1 = cur.getDouble(cur.getColumnIndex("SELLPRICE1"));
					prodDat.SELLPRICE2 = cur.getDouble(cur.getColumnIndex("SELLPRICE2"));
					prodDat.SELLPRICE3 = cur.getDouble(cur.getColumnIndex("SELLPRICE3"));
					prodDat.PBMJUAL = cur.getDouble(cur.getColumnIndex("PBMJUAL"));
					prodDat.XSTOCK = cur.getString(cur.getColumnIndex("XSTOCK"));
					prodDat.STOCK = cur.getLong(cur.getColumnIndex("STOCK"));
					prodDat.QTY_PCS = cur.getLong(cur.getColumnIndex("QTY_PCS"));
					prodDat.FLAGFOCUS = cur.getString(cur.getColumnIndex("FLAGFOCUS"));
					prodDat.PRLIN = cur.getString(cur.getColumnIndex("PRLIN"));
					prodDat.QTYJUAL = cur.getLong(cur.getColumnIndex("QTYJUAL"));
					prodDat.XQTYJUAL = cur.getString(cur.getColumnIndex("XQTYJUAL"));
					prodDat.PPN = cur.getDouble(cur.getColumnIndex("PPN"));
					prodDat.PPNDP = cur.getDouble(cur.getColumnIndex("PPNDP"));
					prodDat.BRAND = cur.getString(cur.getColumnIndex("BRAND"));
					prodDat.MG1 = cur.getString(cur.getColumnIndex("MG1"));
					prodDat.MG2 = cur.getString(cur.getColumnIndex("MG2"));
					prodDat.MG3 = cur.getString(cur.getColumnIndex("MG3"));
					prodDat.MG4 = cur.getString(cur.getColumnIndex("MG4"));
					prodDat.MG5 = cur.getString(cur.getColumnIndex("MG5"));
					prodDat.XTOP = cur.getInt(cur.getColumnIndex("XTOP"));
					prodDat.MAXDISC = cur.getLong(cur.getColumnIndex("MAXDISC"));
					prodDat.FLAGDISC = cur.getString(cur.getColumnIndex("FLAGDISC"));
					prodDat.NETWEIGHT = cur.getDouble(cur.getColumnIndex("NETWEIGHT"));
					prodDat.PHOTO_NAME = cur.getString(cur.getColumnIndex("PHOTO_NAME"));
					prodDat.STOCK_MOTORIS = cur.getLong(cur.getColumnIndex("STOCK_MOTORIS"));
					prodDat.INP_UOM1 = "";
					prodDat.INP_UOM2 = "";
					prodDat.INP_UOM3 = "";
					prodDat.QTY_INPUT = 0;
					prodDat.TOTAL = 0;
					prodDat.INVAMOUNT = cur.getDouble(cur.getColumnIndex("INVAMOUNT"));

					AlisData.add(prodDat);
				}

			}
			cur.close();
		}catch (Exception e){

		}

		return AlisData;
	}

	public ArrayList<Obj_MASTER> getDataListTMP(){
		ArrayList<Obj_MASTER> AlisData  = new ArrayList<Obj_MASTER>();

		try{
			Cursor cur = AppConstant.myDb.rawQuery("SELECT C.STOCK AS STOCK_MOTORIS, A.*, B.QTY_PCS, B.INVAMOUNT  FROM TBL_MASTER A" +
					"	LEFT JOIN (" +
					"	SELECT PCODE, SUM(QTY_PCS) QTY_PCS, SUM(AMOUNT) AS INVAMOUNT FROM TBL_TRX_D" +
					"	GROUP BY PCODE) B" +
					"	ON A.PCODE = B.PCODE" +
					"	LEFT JOIN TBL_STOCK_TMP C" +
					"	ON A.PCODE = C.PCODE" +
					"  WHERE A.FLAG_SALES = 'Y'", null);

			if (cur.getCount() > 0){
//			c.moveToFirst();
				while (cur.moveToNext()){
					Obj_MASTER prodDat = new Obj_MASTER();
					prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
					prodDat.DATA1 = cur.getString(cur.getColumnIndex("DATA1"));
					prodDat.PCODENAME = cur.getString(cur.getColumnIndex("PCODENAME"));
					prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
					prodDat.UNIT1 = cur.getString(cur.getColumnIndex("UNIT1"));
					prodDat.UNIT2 = cur.getString(cur.getColumnIndex("UNIT2"));
					prodDat.UNIT3 = cur.getString(cur.getColumnIndex("UNIT3"));
					prodDat.CONVUNIT2 = cur.getLong(cur.getColumnIndex("CONVUNIT2"));
					prodDat.CONVUNIT3 = cur.getLong(cur.getColumnIndex("CONVUNIT3"));
					prodDat.SELLPRICE1 = cur.getDouble(cur.getColumnIndex("SELLPRICE1"));
					prodDat.SELLPRICE2 = cur.getDouble(cur.getColumnIndex("SELLPRICE2"));
					prodDat.SELLPRICE3 = cur.getDouble(cur.getColumnIndex("SELLPRICE3"));
					prodDat.PBMJUAL = cur.getDouble(cur.getColumnIndex("PBMJUAL"));
					prodDat.XSTOCK = cur.getString(cur.getColumnIndex("XSTOCK"));
					prodDat.STOCK = cur.getLong(cur.getColumnIndex("STOCK"));
					prodDat.QTY_PCS = cur.getLong(cur.getColumnIndex("QTY_PCS"));
					prodDat.FLAGFOCUS = cur.getString(cur.getColumnIndex("FLAGFOCUS"));
					prodDat.PRLIN = cur.getString(cur.getColumnIndex("PRLIN"));
					prodDat.QTYJUAL = cur.getLong(cur.getColumnIndex("QTYJUAL"));
					prodDat.XQTYJUAL = cur.getString(cur.getColumnIndex("XQTYJUAL"));
					prodDat.PPN = cur.getDouble(cur.getColumnIndex("PPN"));
					prodDat.PPNDP = cur.getDouble(cur.getColumnIndex("PPNDP"));
					prodDat.BRAND = cur.getString(cur.getColumnIndex("BRAND"));
					prodDat.MG1 = cur.getString(cur.getColumnIndex("MG1"));
					prodDat.MG2 = cur.getString(cur.getColumnIndex("MG2"));
					prodDat.MG3 = cur.getString(cur.getColumnIndex("MG3"));
					prodDat.MG4 = cur.getString(cur.getColumnIndex("MG4"));
					prodDat.MG5 = cur.getString(cur.getColumnIndex("MG5"));
					prodDat.XTOP = cur.getInt(cur.getColumnIndex("XTOP"));
					prodDat.MAXDISC = cur.getLong(cur.getColumnIndex("MAXDISC"));
					prodDat.FLAGDISC = cur.getString(cur.getColumnIndex("FLAGDISC"));
					prodDat.NETWEIGHT = cur.getDouble(cur.getColumnIndex("NETWEIGHT"));
					prodDat.PHOTO_NAME = cur.getString(cur.getColumnIndex("PHOTO_NAME"));
					prodDat.STOCK_MOTORIS = cur.getLong(cur.getColumnIndex("STOCK_MOTORIS"));
					prodDat.INP_UOM1 = "";
					prodDat.INP_UOM2 = "";
					prodDat.INP_UOM3 = "";
					prodDat.QTY_INPUT = 0;
					prodDat.TOTAL = 0;
					prodDat.INVAMOUNT = cur.getDouble(cur.getColumnIndex("INVAMOUNT"));

					AlisData.add(prodDat);
				}

			}
			cur.close();
		}catch (Exception e){

		}

		return AlisData;
	}

	public ArrayList<Obj_MASTER> getDataList(String CUSTNO){
		ArrayList<Obj_MASTER> AlisData  = new ArrayList<Obj_MASTER>();

		try{
			Cursor cur = AppConstant.myDb.rawQuery("SELECT D.QTY_PCS AS QTY_TRX,C.STOCK AS STOCK_MOTORIS, A.*, B.QTY_PCS  FROM TBL_MASTER A" +
					"	LEFT JOIN (" +
					"	SELECT PCODE, SUM(QTY_PCS) QTY_PCS FROM TBL_TRX_D" +
					"	GROUP BY PCODE) B" +
					"	ON A.PCODE = B.PCODE" +
					"	LEFT JOIN TBL_STOCK_TMP C" +
					"	ON A.PCODE = C.PCODE" +
					"	LEFT JOIN (SELECT A.PCODE, A.QTY_PCS FROM TBL_TRX_D A" +
					"	INNER JOIN TBL_TRX_H B" +
					"	ON A.ORDERNO = B.ORDERNO" +
					"	WHERE B.CUSTNO = '"+CUSTNO+"') D" +
					"	ON A.PCODE = D.PCODE" +
					"  WHERE A.FLAG_SALES = 'Y'", null);

			if (cur.getCount() > 0){
//			c.moveToFirst();
				while (cur.moveToNext()){
					Obj_MASTER prodDat = new Obj_MASTER();
					prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
					prodDat.DATA1 = cur.getString(cur.getColumnIndex("DATA1"));
					prodDat.PCODENAME = cur.getString(cur.getColumnIndex("PCODENAME"));
					prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
					prodDat.UNIT1 = cur.getString(cur.getColumnIndex("UNIT1"));
					prodDat.UNIT2 = cur.getString(cur.getColumnIndex("UNIT2"));
					prodDat.UNIT3 = cur.getString(cur.getColumnIndex("UNIT3"));
					prodDat.CONVUNIT2 = cur.getLong(cur.getColumnIndex("CONVUNIT2"));
					prodDat.CONVUNIT3 = cur.getLong(cur.getColumnIndex("CONVUNIT3"));
					prodDat.SELLPRICE1 = cur.getDouble(cur.getColumnIndex("SELLPRICE1"));
					prodDat.SELLPRICE2 = cur.getDouble(cur.getColumnIndex("SELLPRICE2"));
					prodDat.SELLPRICE3 = cur.getDouble(cur.getColumnIndex("SELLPRICE3"));
					prodDat.PBMJUAL = cur.getDouble(cur.getColumnIndex("PBMJUAL"));
					prodDat.XSTOCK = cur.getString(cur.getColumnIndex("XSTOCK"));
					prodDat.STOCK = cur.getLong(cur.getColumnIndex("STOCK"));
					prodDat.QTY_PCS = cur.getLong(cur.getColumnIndex("QTY_PCS"));

					prodDat.FLAGFOCUS = cur.getString(cur.getColumnIndex("FLAGFOCUS"));
					prodDat.PRLIN = cur.getString(cur.getColumnIndex("PRLIN"));
					prodDat.QTYJUAL = cur.getLong(cur.getColumnIndex("QTYJUAL"));
					prodDat.XQTYJUAL = cur.getString(cur.getColumnIndex("XQTYJUAL"));
					prodDat.PPN = cur.getDouble(cur.getColumnIndex("PPN"));
					prodDat.PPNDP = cur.getDouble(cur.getColumnIndex("PPNDP"));
					prodDat.BRAND = cur.getString(cur.getColumnIndex("BRAND"));
					prodDat.MG1 = cur.getString(cur.getColumnIndex("MG1"));
					prodDat.MG2 = cur.getString(cur.getColumnIndex("MG2"));
					prodDat.MG3 = cur.getString(cur.getColumnIndex("MG3"));
					prodDat.MG4 = cur.getString(cur.getColumnIndex("MG4"));
					prodDat.MG5 = cur.getString(cur.getColumnIndex("MG5"));
					prodDat.XTOP = cur.getInt(cur.getColumnIndex("XTOP"));
					prodDat.MAXDISC = cur.getLong(cur.getColumnIndex("MAXDISC"));
					prodDat.FLAGDISC = cur.getString(cur.getColumnIndex("FLAGDISC"));
					prodDat.NETWEIGHT = cur.getDouble(cur.getColumnIndex("NETWEIGHT"));
					prodDat.PHOTO_NAME = cur.getString(cur.getColumnIndex("PHOTO_NAME"));
					prodDat.STOCK_MOTORIS = cur.getLong(cur.getColumnIndex("STOCK_MOTORIS"));
					prodDat.INP_UOM1 = "";
					prodDat.INP_UOM2 = "";
					prodDat.INP_UOM3 = "";
					prodDat.QTY_INPUT = 0;
					prodDat.QTY_TRX = 0;
					prodDat.TOTAL = 0;
					AlisData.add(prodDat);
				}

			}
			cur.close();
		}catch (Exception e){

		}

		return AlisData;
	}

	public ArrayList<Obj_MASTER> getDataListStock(){
		ArrayList<Obj_MASTER> AlisData  = new ArrayList<Obj_MASTER>();

		try {
			Cursor cur = AppConstant.myDb.rawQuery("SELECT A.*, B.STOCK_IN, C.STOCK_OUT , D.QTYJUAL  FROM TBL_MASTER A" +
					"	LEFT JOIN (" +
					"	SELECT PCODE, SUM(STOCK) STOCK_IN FROM TBL_STOCK" +
                    "   WHERE FLAG_IN = 'Y' OR FLAG_IN = 'S' OR FLAG_IN = 'A'" +
					"	GROUP BY PCODE) B" +
					"	ON A.PCODE = B.PCODE" +
					"	LEFT JOIN (" +
					"	SELECT PCODE, SUM(STOCK) STOCK_OUT FROM TBL_STOCK" +
                    "   WHERE FLAG_IN = 'N'" +
					"	GROUP BY PCODE) C" +
					"	ON A.PCODE = C.PCODE" +
					"	LEFT JOIN (" +
                    "   SELECT A.PCODE, SUM(A.QTY)QTYJUAL from(SELECT B.PCODE, (B.QTY * A.QTY_PCS)QTY " +
					"   FROM (SELECT A.PCODE, SUM(A.QTY_PCS) QTY_PCS FROM TBL_TRX_D A" +
					"   INNER JOIN TBL_TRX_H B ON A.ORDERNO = B.ORDERNO  WHERE B.FLAG_VOID = 'N' GROUP BY PCODE)A" +
                    "   INNER JOIN TBL_STOCK_MAPPING B ON A.PCODE = B.PCODE_SALES ) A" +
                    "   GROUP BY A.PCODE) D" +
					"	ON A.PCODE = D.PCODE" +
					"   WHERE A.FLAG_SALES = 'N'" +
					"	ORDER BY PCODENAME ASC", null);

			if (cur.getCount() > 0){
//			c.moveToFirst();
				while (cur.moveToNext()){
					Obj_MASTER prodDat = new Obj_MASTER();
					prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
					prodDat.DATA1 = cur.getString(cur.getColumnIndex("DATA1"));
					prodDat.PCODENAME = cur.getString(cur.getColumnIndex("PCODENAME"));
					prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
					prodDat.UNIT1 = cur.getString(cur.getColumnIndex("UNIT1"));
					prodDat.UNIT2 = cur.getString(cur.getColumnIndex("UNIT2"));
					prodDat.UNIT3 = cur.getString(cur.getColumnIndex("UNIT3"));
					prodDat.CONVUNIT2 = cur.getLong(cur.getColumnIndex("CONVUNIT2"));
					prodDat.CONVUNIT3 = cur.getLong(cur.getColumnIndex("CONVUNIT3"));
					prodDat.SELLPRICE1 = cur.getDouble(cur.getColumnIndex("SELLPRICE1"));
					prodDat.SELLPRICE2 = cur.getDouble(cur.getColumnIndex("SELLPRICE2"));
					prodDat.SELLPRICE3 = cur.getDouble(cur.getColumnIndex("SELLPRICE3"));
					prodDat.PBMJUAL = cur.getDouble(cur.getColumnIndex("PBMJUAL"));
					prodDat.XSTOCK = cur.getString(cur.getColumnIndex("XSTOCK"));
					prodDat.STOCK_IN = cur.getLong(cur.getColumnIndex("STOCK_IN"));
					prodDat.STOCK_OUT = cur.getLong(cur.getColumnIndex("STOCK_OUT"));
					prodDat.FLAGFOCUS = cur.getString(cur.getColumnIndex("FLAGFOCUS"));
					prodDat.PRLIN = cur.getString(cur.getColumnIndex("PRLIN"));
					prodDat.QTYJUAL = cur.getLong(cur.getColumnIndex("QTYJUAL"));
					prodDat.XQTYJUAL = cur.getString(cur.getColumnIndex("XQTYJUAL"));
					prodDat.PPN = cur.getDouble(cur.getColumnIndex("PPN"));
					prodDat.PPNDP = cur.getDouble(cur.getColumnIndex("PPNDP"));
					prodDat.BRAND = cur.getString(cur.getColumnIndex("BRAND"));
					prodDat.MG1 = cur.getString(cur.getColumnIndex("MG1"));
					prodDat.MG2 = cur.getString(cur.getColumnIndex("MG2"));
					prodDat.MG3 = cur.getString(cur.getColumnIndex("MG3"));
					prodDat.MG4 = cur.getString(cur.getColumnIndex("MG4"));
					prodDat.MG5 = cur.getString(cur.getColumnIndex("MG5"));
					prodDat.XTOP = cur.getInt(cur.getColumnIndex("XTOP"));
					prodDat.MAXDISC = cur.getLong(cur.getColumnIndex("MAXDISC"));
					prodDat.FLAGDISC = cur.getString(cur.getColumnIndex("FLAGDISC"));
					prodDat.NETWEIGHT = cur.getDouble(cur.getColumnIndex("NETWEIGHT"));
					prodDat.PHOTO_NAME = cur.getString(cur.getColumnIndex("PHOTO_NAME"));

					AlisData.add(prodDat);
				}

			}
			cur.close();
		}catch (Exception e){

		}

		return AlisData;
	}


	public ArrayList<Obj_MASTER> getDataListStock(String CUSTNO){
		ArrayList<Obj_MASTER> AlisData  = new ArrayList<Obj_MASTER>();

		try{
			Cursor cur = AppConstant.myDb.rawQuery("SELECT D.QTY_PCS AS QTY_TRX,C.STOCK AS STOCK_MOTORIS, A.*, B.QTY_PCS  FROM TBL_MASTER A" +
					"	LEFT JOIN (" +
					"	SELECT PCODE, SUM(QTY_PCS) QTY_PCS FROM TBL_TRX_D" +
					"	GROUP BY PCODE) B" +
					"	ON A.PCODE = B.PCODE" +
					"	LEFT JOIN TBL_STOCK_TMP C" +
					"	ON A.PCODE = C.PCODE" +
					"	LEFT JOIN (SELECT A.PCODE, A.QTY_PCS FROM TBL_TRX_D A" +
					"	INNER JOIN TBL_TRX_H B" +
					"	ON A.ORDERNO = B.ORDERNO" +
					"	WHERE B.CUSTNO = '"+CUSTNO+"'  ) D" +
					"	ON A.PCODE = D.PCODE" +
					"  WHERE A.FLAG_SALES = 'Y'" , null);


			if (cur.getCount() > 0){
//			c.moveToFirst();
				while (cur.moveToNext()){
					Obj_MASTER prodDat = new Obj_MASTER();
					prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
					prodDat.DATA1 = cur.getString(cur.getColumnIndex("DATA1"));
					prodDat.PCODENAME = cur.getString(cur.getColumnIndex("PCODENAME"));
					prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
					prodDat.UNIT1 = cur.getString(cur.getColumnIndex("UNIT1"));
					prodDat.UNIT2 = cur.getString(cur.getColumnIndex("UNIT2"));
					prodDat.UNIT3 = cur.getString(cur.getColumnIndex("UNIT3"));
					prodDat.CONVUNIT2 = cur.getLong(cur.getColumnIndex("CONVUNIT2"));
					prodDat.CONVUNIT3 = cur.getLong(cur.getColumnIndex("CONVUNIT3"));
					prodDat.SELLPRICE1 = cur.getDouble(cur.getColumnIndex("SELLPRICE1"));
					prodDat.SELLPRICE2 = cur.getDouble(cur.getColumnIndex("SELLPRICE2"));
					prodDat.SELLPRICE3 = cur.getDouble(cur.getColumnIndex("SELLPRICE3"));
					prodDat.PBMJUAL = cur.getDouble(cur.getColumnIndex("PBMJUAL"));
					prodDat.XSTOCK = cur.getString(cur.getColumnIndex("XSTOCK"));
					prodDat.STOCK = cur.getLong(cur.getColumnIndex("STOCK"));
					prodDat.QTY_PCS = cur.getLong(cur.getColumnIndex("QTY_PCS"));
					prodDat.QTY_TRX = cur.getLong(cur.getColumnIndex("QTY_TRX"));
					prodDat.FLAGFOCUS = cur.getString(cur.getColumnIndex("FLAGFOCUS"));
					prodDat.PRLIN = cur.getString(cur.getColumnIndex("PRLIN"));
					prodDat.QTYJUAL = cur.getLong(cur.getColumnIndex("QTYJUAL"));
					prodDat.XQTYJUAL = cur.getString(cur.getColumnIndex("XQTYJUAL"));
					prodDat.PPN = cur.getDouble(cur.getColumnIndex("PPN"));
					prodDat.PPNDP = cur.getDouble(cur.getColumnIndex("PPNDP"));
					prodDat.BRAND = cur.getString(cur.getColumnIndex("BRAND"));
					prodDat.MG1 = cur.getString(cur.getColumnIndex("MG1"));
					prodDat.MG2 = cur.getString(cur.getColumnIndex("MG2"));
					prodDat.MG3 = cur.getString(cur.getColumnIndex("MG3"));
					prodDat.MG4 = cur.getString(cur.getColumnIndex("MG4"));
					prodDat.MG5 = cur.getString(cur.getColumnIndex("MG5"));
					prodDat.XTOP = cur.getInt(cur.getColumnIndex("XTOP"));
					prodDat.MAXDISC = cur.getLong(cur.getColumnIndex("MAXDISC"));
					prodDat.FLAGDISC = cur.getString(cur.getColumnIndex("FLAGDISC"));
					prodDat.NETWEIGHT = cur.getDouble(cur.getColumnIndex("NETWEIGHT"));
					prodDat.PHOTO_NAME = cur.getString(cur.getColumnIndex("PHOTO_NAME"));
					prodDat.STOCK_MOTORIS = cur.getLong(cur.getColumnIndex("STOCK_MOTORIS"));
					prodDat.INP_UOM1 = "";
					prodDat.INP_UOM2 = "";
					prodDat.INP_UOM3 = "";
					prodDat.QTY_INPUT = 0;
					prodDat.TOTAL = 0;
					AlisData.add(prodDat);
				}

			}
			cur.close();
		}catch (Exception e){
			Log.w("Data Master", e.getMessage());
		}

		return AlisData;
	}

	public ArrayList<Obj_MASTER> getDataListStockDispenser(String CUSTNO){
		ArrayList<Obj_MASTER> AlisData  = new ArrayList<Obj_MASTER>();

		try{
			Cursor cur = AppConstant.myDb.rawQuery("SELECT B.QTY AS QTY_TRX, A.* FROM TBL_MASTER A LEFT JOIN " +
					"	(SELECT * FROM TBL_DISPENSER WHERE CUSTNO = '"+CUSTNO+"') B" +
					"	ON A.PCODE = B.PCODE" +
					"  	WHERE A.FLAG_SALES = 'N'" , null);


			if (cur.getCount() > 0){
//			c.moveToFirst();
				while (cur.moveToNext()){
					Obj_MASTER prodDat = new Obj_MASTER();
					prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
					prodDat.DATA1 = cur.getString(cur.getColumnIndex("DATA1"));
					prodDat.PCODENAME = cur.getString(cur.getColumnIndex("PCODENAME"));
					prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
					prodDat.UNIT1 = cur.getString(cur.getColumnIndex("UNIT1"));
					prodDat.UNIT2 = cur.getString(cur.getColumnIndex("UNIT2"));
					prodDat.UNIT3 = cur.getString(cur.getColumnIndex("UNIT3"));
					prodDat.CONVUNIT2 = cur.getLong(cur.getColumnIndex("CONVUNIT2"));
					prodDat.CONVUNIT3 = cur.getLong(cur.getColumnIndex("CONVUNIT3"));
					prodDat.SELLPRICE1 = cur.getDouble(cur.getColumnIndex("SELLPRICE1"));
					prodDat.SELLPRICE2 = cur.getDouble(cur.getColumnIndex("SELLPRICE2"));
					prodDat.SELLPRICE3 = cur.getDouble(cur.getColumnIndex("SELLPRICE3"));
					prodDat.PBMJUAL = cur.getDouble(cur.getColumnIndex("PBMJUAL"));
					prodDat.XSTOCK = cur.getString(cur.getColumnIndex("XSTOCK"));
					prodDat.STOCK = cur.getLong(cur.getColumnIndex("STOCK"));
					prodDat.QTY_TRX = cur.getLong(cur.getColumnIndex("QTY_TRX"));
					prodDat.FLAGFOCUS = cur.getString(cur.getColumnIndex("FLAGFOCUS"));
					prodDat.PRLIN = cur.getString(cur.getColumnIndex("PRLIN"));
					prodDat.PPN = cur.getDouble(cur.getColumnIndex("PPN"));
					prodDat.PPNDP = cur.getDouble(cur.getColumnIndex("PPNDP"));
					prodDat.BRAND = cur.getString(cur.getColumnIndex("BRAND"));
					prodDat.MG1 = cur.getString(cur.getColumnIndex("MG1"));
					prodDat.MG2 = cur.getString(cur.getColumnIndex("MG2"));
					prodDat.MG3 = cur.getString(cur.getColumnIndex("MG3"));
					prodDat.MG4 = cur.getString(cur.getColumnIndex("MG4"));
					prodDat.MG5 = cur.getString(cur.getColumnIndex("MG5"));
					prodDat.XTOP = cur.getInt(cur.getColumnIndex("XTOP"));
					prodDat.MAXDISC = cur.getLong(cur.getColumnIndex("MAXDISC"));
					prodDat.FLAGDISC = cur.getString(cur.getColumnIndex("FLAGDISC"));
					prodDat.NETWEIGHT = cur.getDouble(cur.getColumnIndex("NETWEIGHT"));
					prodDat.PHOTO_NAME = cur.getString(cur.getColumnIndex("PHOTO_NAME"));
					prodDat.INP_UOM1 = "";
					prodDat.INP_UOM2 = "";
					prodDat.INP_UOM3 = "";
					prodDat.QTY_INPUT = 0;
					prodDat.TOTAL = 0;
					AlisData.add(prodDat);

				}

				Log.w("Data Master", AlisData.toString());
			}
			cur.close();
		}catch (Exception e){
			Log.w("Data Master", e.getMessage());
		}

		return AlisData;
	}

	public ArrayList<Obj_MASTER> getDataListStock(String CUSTNO, String ORDERNO){
		ArrayList<Obj_MASTER> AlisData  = new ArrayList<Obj_MASTER>();

		try{
			Cursor cur = AppConstant.myDb.rawQuery("SELECT D.QTY_PCS AS QTY_TRX,C.STOCK AS STOCK_MOTORIS, A.*, B.QTY_PCS  FROM TBL_MASTER A" +
					"	LEFT JOIN (" +
					"	SELECT PCODE, SUM(QTY_PCS) QTY_PCS FROM TBL_TRX_D" +
					"	GROUP BY PCODE) B" +
					"	ON A.PCODE = B.PCODE" +
					"	LEFT JOIN TBL_STOCK_TMP C" +
					"	ON A.PCODE = C.PCODE" +
					"	LEFT JOIN (SELECT A.PCODE, A.QTY_PCS FROM TBL_TRX_D A" +
					"	INNER JOIN TBL_TRX_H B" +
					"	ON A.ORDERNO = B.ORDERNO" +
					"	WHERE B.CUSTNO = '"+CUSTNO+"' AND B.ORDERNO = '"+ORDERNO+"' ) D" +
					"	ON A.PCODE = D.PCODE" +
					"   WHERE A.FLAG_SALES = 'Y'", null);

			if (cur.getCount() > 0){
//			c.moveToFirst();
				while (cur.moveToNext()){
					Obj_MASTER prodDat = new Obj_MASTER();
					prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
					prodDat.DATA1 = cur.getString(cur.getColumnIndex("DATA1"));
					prodDat.PCODENAME = cur.getString(cur.getColumnIndex("PCODENAME"));
					prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
					prodDat.UNIT1 = cur.getString(cur.getColumnIndex("UNIT1"));
					prodDat.UNIT2 = cur.getString(cur.getColumnIndex("UNIT2"));
					prodDat.UNIT3 = cur.getString(cur.getColumnIndex("UNIT3"));
					prodDat.CONVUNIT2 = cur.getLong(cur.getColumnIndex("CONVUNIT2"));
					prodDat.CONVUNIT3 = cur.getLong(cur.getColumnIndex("CONVUNIT3"));
					prodDat.SELLPRICE1 = cur.getDouble(cur.getColumnIndex("SELLPRICE1"));
					prodDat.SELLPRICE2 = cur.getDouble(cur.getColumnIndex("SELLPRICE2"));
					prodDat.SELLPRICE3 = cur.getDouble(cur.getColumnIndex("SELLPRICE3"));
					prodDat.PBMJUAL = cur.getDouble(cur.getColumnIndex("PBMJUAL"));
					prodDat.XSTOCK = cur.getString(cur.getColumnIndex("XSTOCK"));
					prodDat.STOCK = cur.getLong(cur.getColumnIndex("STOCK"));
					prodDat.QTY_PCS = cur.getLong(cur.getColumnIndex("QTY_PCS"));
					prodDat.QTY_TRX = cur.getLong(cur.getColumnIndex("QTY_TRX"));
					prodDat.FLAGFOCUS = cur.getString(cur.getColumnIndex("FLAGFOCUS"));
					prodDat.PRLIN = cur.getString(cur.getColumnIndex("PRLIN"));
					prodDat.QTYJUAL = cur.getLong(cur.getColumnIndex("QTYJUAL"));
					prodDat.XQTYJUAL = cur.getString(cur.getColumnIndex("XQTYJUAL"));
					prodDat.PPN = cur.getDouble(cur.getColumnIndex("PPN"));
					prodDat.PPNDP = cur.getDouble(cur.getColumnIndex("PPNDP"));
					prodDat.BRAND = cur.getString(cur.getColumnIndex("BRAND"));
					prodDat.MG1 = cur.getString(cur.getColumnIndex("MG1"));
					prodDat.MG2 = cur.getString(cur.getColumnIndex("MG2"));
					prodDat.MG3 = cur.getString(cur.getColumnIndex("MG3"));
					prodDat.MG4 = cur.getString(cur.getColumnIndex("MG4"));
					prodDat.MG5 = cur.getString(cur.getColumnIndex("MG5"));
					prodDat.XTOP = cur.getInt(cur.getColumnIndex("XTOP"));
					prodDat.MAXDISC = cur.getLong(cur.getColumnIndex("MAXDISC"));
					prodDat.FLAGDISC = cur.getString(cur.getColumnIndex("FLAGDISC"));
					prodDat.NETWEIGHT = cur.getDouble(cur.getColumnIndex("NETWEIGHT"));
					prodDat.PHOTO_NAME = cur.getString(cur.getColumnIndex("PHOTO_NAME"));
					prodDat.STOCK_MOTORIS = cur.getLong(cur.getColumnIndex("STOCK_MOTORIS"));
					prodDat.QTY_INPUT = 0;
					prodDat.INP_UOM1 = "";
					prodDat.INP_UOM2 = "";
					prodDat.INP_UOM3 = "";
					prodDat.TOTAL = 0;
					AlisData.add(prodDat);
				}

			}
			cur.close();
		}catch (Exception e){

		}

		return AlisData;
	}


	public ArrayList<Obj_MASTER> getDataListTRX(String CUSTNO, String ORDERNO){
		ArrayList<Obj_MASTER> AlisData  = new ArrayList<Obj_MASTER>();

		try{
			Cursor cur = AppConstant.myDb.rawQuery("SELECT D.QTY_PCS AS QTY_TRX,C.STOCK AS STOCK_MOTORIS, A.*, B.QTY_PCS  FROM TBL_MASTER A" +
					"	LEFT JOIN (" +
					"	SELECT PCODE, SUM(QTY_PCS) QTY_PCS FROM TBL_TRX_D" +
					"	GROUP BY PCODE) B" +
					"	ON A.PCODE = B.PCODE" +
					"	LEFT JOIN TBL_STOCK_TMP C" +
					"	ON A.PCODE = C.PCODE" +
					"	LEFT JOIN (SELECT A.PCODE, A.QTY_PCS FROM TBL_TRX_D A" +
					"	INNER JOIN TBL_TRX_H B" +
					"	ON A.ORDERNO = B.ORDERNO" +
					"	WHERE B.CUSTNO = '"+CUSTNO+"' AND B.ORDERNO = '"+ORDERNO+"' ) D" +
					"	ON A.PCODE = D.PCODE" +
					"	WHERE D.QTY_PCS > 0" +
					"   AND A.FLAG_SALES = 'Y'", null);

			if (cur.getCount() > 0){
//			c.moveToFirst();
				while (cur.moveToNext()){
					Obj_MASTER prodDat = new Obj_MASTER();
					prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
					prodDat.DATA1 = cur.getString(cur.getColumnIndex("DATA1"));
					prodDat.PCODENAME = cur.getString(cur.getColumnIndex("PCODENAME"));
					prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
					prodDat.UNIT1 = cur.getString(cur.getColumnIndex("UNIT1"));
					prodDat.UNIT2 = cur.getString(cur.getColumnIndex("UNIT2"));
					prodDat.UNIT3 = cur.getString(cur.getColumnIndex("UNIT3"));
					prodDat.CONVUNIT2 = cur.getLong(cur.getColumnIndex("CONVUNIT2"));
					prodDat.CONVUNIT3 = cur.getLong(cur.getColumnIndex("CONVUNIT3"));
					prodDat.SELLPRICE1 = cur.getDouble(cur.getColumnIndex("SELLPRICE1"));
					prodDat.SELLPRICE2 = cur.getDouble(cur.getColumnIndex("SELLPRICE2"));
					prodDat.SELLPRICE3 = cur.getDouble(cur.getColumnIndex("SELLPRICE3"));
					prodDat.PBMJUAL = cur.getDouble(cur.getColumnIndex("PBMJUAL"));
					prodDat.XSTOCK = cur.getString(cur.getColumnIndex("XSTOCK"));
					prodDat.STOCK = cur.getLong(cur.getColumnIndex("STOCK"));
					prodDat.QTY_PCS = cur.getLong(cur.getColumnIndex("QTY_PCS"));
					prodDat.QTY_TRX = cur.getLong(cur.getColumnIndex("QTY_TRX"));
					prodDat.FLAGFOCUS = cur.getString(cur.getColumnIndex("FLAGFOCUS"));
					prodDat.PRLIN = cur.getString(cur.getColumnIndex("PRLIN"));
					prodDat.QTYJUAL = cur.getLong(cur.getColumnIndex("QTYJUAL"));
					prodDat.XQTYJUAL = cur.getString(cur.getColumnIndex("XQTYJUAL"));
					prodDat.PPN = cur.getDouble(cur.getColumnIndex("PPN"));
					prodDat.PPNDP = cur.getDouble(cur.getColumnIndex("PPNDP"));
					prodDat.BRAND = cur.getString(cur.getColumnIndex("BRAND"));
					prodDat.MG1 = cur.getString(cur.getColumnIndex("MG1"));
					prodDat.MG2 = cur.getString(cur.getColumnIndex("MG2"));
					prodDat.MG3 = cur.getString(cur.getColumnIndex("MG3"));
					prodDat.MG4 = cur.getString(cur.getColumnIndex("MG4"));
					prodDat.MG5 = cur.getString(cur.getColumnIndex("MG5"));
					prodDat.XTOP = cur.getInt(cur.getColumnIndex("XTOP"));
					prodDat.MAXDISC = cur.getLong(cur.getColumnIndex("MAXDISC"));
					prodDat.FLAGDISC = cur.getString(cur.getColumnIndex("FLAGDISC"));
					prodDat.NETWEIGHT = cur.getDouble(cur.getColumnIndex("NETWEIGHT"));
					prodDat.PHOTO_NAME = cur.getString(cur.getColumnIndex("PHOTO_NAME"));
					prodDat.STOCK_MOTORIS = cur.getLong(cur.getColumnIndex("STOCK_MOTORIS"));
					prodDat.QTY_INPUT = 0;
					prodDat.INP_UOM1 = "";
					prodDat.INP_UOM2 = "";
					prodDat.INP_UOM3 = "";
					prodDat.TOTAL = 0;
					AlisData.add(prodDat);
				}

			}
			cur.close();
		}catch (Exception e){

		}

		return AlisData;
	}

	public ArrayList<Obj_MASTER> getDataListTRX(String CUSTNO){
		ArrayList<Obj_MASTER> AlisData  = new ArrayList<Obj_MASTER>();

		try{
			Cursor cur = AppConstant.myDb.rawQuery("SELECT D.QTY_PCS AS QTY_TRX,C.STOCK AS STOCK_MOTORIS, A.*, B.QTY_PCS  FROM TBL_MASTER A" +
					"	LEFT JOIN (" +
					"	SELECT PCODE, SUM(QTY_PCS) QTY_PCS FROM TBL_TRX_D" +
					"	GROUP BY PCODE) B" +
					"	ON A.PCODE = B.PCODE" +
					"	LEFT JOIN TBL_STOCK_TMP C" +
					"	ON A.PCODE = C.PCODE" +
					"	LEFT JOIN (SELECT A.PCODE, A.QTY_PCS FROM TBL_TRX_D A" +
					"	INNER JOIN TBL_TRX_H B" +
					"	ON A.ORDERNO = B.ORDERNO" +
					"	WHERE B.CUSTNO = '"+CUSTNO+"'  ) D" +
					"	ON A.PCODE = D.PCODE" +
					"	WHERE D.QTY_PCS  > 0" +
					"   AND A.FLAG_SALES = 'Y'" , null);


			if (cur.getCount() > 0){
//			c.moveToFirst();
				while (cur.moveToNext()){
					Obj_MASTER prodDat = new Obj_MASTER();
					prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
					prodDat.DATA1 = cur.getString(cur.getColumnIndex("DATA1"));
					prodDat.PCODENAME = cur.getString(cur.getColumnIndex("PCODENAME"));
					prodDat.PCODE = cur.getString(cur.getColumnIndex("PCODE"));
					prodDat.UNIT1 = cur.getString(cur.getColumnIndex("UNIT1"));
					prodDat.UNIT2 = cur.getString(cur.getColumnIndex("UNIT2"));
					prodDat.UNIT3 = cur.getString(cur.getColumnIndex("UNIT3"));
					prodDat.CONVUNIT2 = cur.getLong(cur.getColumnIndex("CONVUNIT2"));
					prodDat.CONVUNIT3 = cur.getLong(cur.getColumnIndex("CONVUNIT3"));
					prodDat.SELLPRICE1 = cur.getDouble(cur.getColumnIndex("SELLPRICE1"));
					prodDat.SELLPRICE2 = cur.getDouble(cur.getColumnIndex("SELLPRICE2"));
					prodDat.SELLPRICE3 = cur.getDouble(cur.getColumnIndex("SELLPRICE3"));
					prodDat.PBMJUAL = cur.getDouble(cur.getColumnIndex("PBMJUAL"));
					prodDat.XSTOCK = cur.getString(cur.getColumnIndex("XSTOCK"));
					prodDat.STOCK = cur.getLong(cur.getColumnIndex("STOCK"));
					prodDat.QTY_PCS = cur.getLong(cur.getColumnIndex("QTY_PCS"));
					prodDat.QTY_TRX = cur.getLong(cur.getColumnIndex("QTY_TRX"));
					prodDat.FLAGFOCUS = cur.getString(cur.getColumnIndex("FLAGFOCUS"));
					prodDat.PRLIN = cur.getString(cur.getColumnIndex("PRLIN"));
					prodDat.QTYJUAL = cur.getLong(cur.getColumnIndex("QTYJUAL"));
					prodDat.XQTYJUAL = cur.getString(cur.getColumnIndex("XQTYJUAL"));
					prodDat.PPN = cur.getDouble(cur.getColumnIndex("PPN"));
					prodDat.PPNDP = cur.getDouble(cur.getColumnIndex("PPNDP"));
					prodDat.BRAND = cur.getString(cur.getColumnIndex("BRAND"));
					prodDat.MG1 = cur.getString(cur.getColumnIndex("MG1"));
					prodDat.MG2 = cur.getString(cur.getColumnIndex("MG2"));
					prodDat.MG3 = cur.getString(cur.getColumnIndex("MG3"));
					prodDat.MG4 = cur.getString(cur.getColumnIndex("MG4"));
					prodDat.MG5 = cur.getString(cur.getColumnIndex("MG5"));
					prodDat.XTOP = cur.getInt(cur.getColumnIndex("XTOP"));
					prodDat.MAXDISC = cur.getLong(cur.getColumnIndex("MAXDISC"));
					prodDat.FLAGDISC = cur.getString(cur.getColumnIndex("FLAGDISC"));
					prodDat.NETWEIGHT = cur.getDouble(cur.getColumnIndex("NETWEIGHT"));
					prodDat.PHOTO_NAME = cur.getString(cur.getColumnIndex("PHOTO_NAME"));
					prodDat.STOCK_MOTORIS = cur.getLong(cur.getColumnIndex("STOCK_MOTORIS"));
					prodDat.INP_UOM1 = "";
					prodDat.INP_UOM2 = "";
					prodDat.INP_UOM3 = "";
					prodDat.QTY_INPUT = 0;
					prodDat.TOTAL = 0;
					AlisData.add(prodDat);
				}

			}
			cur.close();
		}catch (Exception e){
			Log.w("Data Master", e.getMessage());
		}

		return AlisData;
	}

	public void CreateMaster(Obj_MASTER dat){
		String strQuery = "";
		try{

			 strQuery = "INSERT INTO Tbl_MASTER (" +
					"PCODE," +
					"DATA1," +
					"PCODENAME," +
					"UNIT1," +
					"UNIT2," +
					"UNIT3," +
					"CONVUNIT2," +
					"CONVUNIT3," +
					"SELLPRICE1," +
					"SELLPRICE2," +
					"SELLPRICE3," +
					"PHOTO_NAME," +
					 "FLAG_SALES," +
					"PBMJUAL," +
					"STOCK," +
					"XSTOCK," +
					"FLAGFOCUS," +
					"PRLIN," +
					"BRAND," +
					"XTOP," +
					"PPN," +
					"PPNDP" +
					")VALUES(" +
					"'"+dat.getPCode()+"'," +
					"'"+dat.getData01()+"'," +
					"'"+dat.getPCodeName()+"'," +
					"'"+dat.getUnit1()+"'," +
					"'"+dat.getUnit2()+"'," +
					"'"+dat.getUnit3()+"'," +
					"'"+dat.getConvUnit2()+"'," +
					"'"+dat.getConvUnit3()+"'," +
					"'"+dat.getSellPrice1()+"'," +
					"'"+dat.getSellPrice2()+"'," +
					"'"+dat.getSellPrice3()+"'," +
					"'"+dat.getPHOTO_NAME()+"'," +
					 "'"+dat.getFLAG_SALES()+"'," +
					"'0'," +
					"'0'," +
					"''," +
					"''," +
					"'"+dat.getPrLin()+"'," +
					"'0'," +
					"'0'," +
					"'"+dat.getPPN()+"'," +
					"'"+dat.getPPNDP()+"')"

					;

			Log.w("SyncProduct", "PCODE : " + strQuery);
			AppConstant.myDb.execSQL(strQuery);
		}catch(Exception e){
			Log.w("TblMaster", e.getMessage() + strQuery);
		}
	}


	public void DeleteMaster(){
		try{
			String strQuery = "DELETE FROM Tbl_MASTER" ;
			AppConstant.myDb.execSQL(strQuery);

			strQuery = "Delete from Tbl_PRLIN";
			AppConstant.myDb.execSQL(strQuery);
		}catch(Exception e){
			
		}
	}


	public void UpdateStock(Obj_MASTER dat){
		try{
			String strQuery = "UPDATE Tbl_MASTER " +
					"	SET STOCK = "+dat.STOCK+"" +
					"	WHERE PCODE = "+dat.PCODE+"" ;
			AppConstant.myDb.execSQL(strQuery);
		}catch(Exception e){

		}
	}


}
