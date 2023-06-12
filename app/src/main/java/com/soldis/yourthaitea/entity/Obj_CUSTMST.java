package com.soldis.yourthaitea.entity;

import android.database.Cursor;
import android.util.Log;

import com.soldis.yourthaitea.AppConstant;

import java.util.ArrayList;

public class Obj_CUSTMST {
    private String CUSTNO = "" ;
	private String DATA01 = "" ;
	private String CUSTNAME = "" ;
	private String CUSTADD1 = "" ;
	private String CCONTACT = "" ;
	private String CPHONE1 = "" ;
	private String KELURAHAN = "" ;
	private String KECAMATAN = "" ;
	private String KABUPATEN = "" ;
	private String PROVINSI = "" ;
	private String KODEPOS = "" ;
	private String ALAMAT = "" ;
	private String LATITUDE = "" ;
	private String LONGITUDE = "" ;
	private String TYPEOUT = "" ;
	private String TYPENAME = "" ;

	private String PHOTO_NAME = "" ;
	private String PHOTO_NAME_SAMPING = "" ;
	private double INVAMOUNT = 0;
	private int SKU = 0;
	private String FLAG_KIRIM = "";

	private String ISI_TRANSAKSI = "";
	private int TOTAL_KUNJUNGAN = 0;
	private String LAST_TRANSACTION = "";
	private int TOTAL_INV = 0;
    private String ORDERNO = "" ;
	private String DATE_NEXT_VISIT = "" ;
	private int NUMBER_OF_BRANCH = 0;
	private String FLAGOUT = "";
	private String FLAGCUST = "";
	private String COMPANY_NAME = "" ;

	private String KTP_ID = "" ;
	private String KTP_NAME = "" ;
	private String KTP_ADDRESS = "" ;
	private String NPWP_ID = "" ;
	private String NPWP_NAME = "" ;
	private String NPWP_ADDRESS = "" ;
	private String PAYMENT_TYPE = "" ;
	private String PERIODE_ORDER = "" ;
	private String STATUS_CONTRACT = "" ;
	private String STARTDATE_CONTRACT = "" ;
	private String ENDDATE_CONTRACT = "" ;
	private String STATUS_DISPENSER = "" ;
	private int DISPENSER_JRT = 0 ;
	private int DISPENSER_HRT = 0 ;
	private int DISPENSER_MULTIFOLD = 0 ;
	private int DISPENSER_ROLL = 0 ;

	public Obj_CUSTMST (){}

	public String getSTATUS_DISPENSER() {
		return STATUS_DISPENSER;
	}

	public void setSTATUS_DISPENSER(String STATUS_DISPENSER) {
		this.STATUS_DISPENSER = STATUS_DISPENSER;
	}

	public int getDISPENSER_JRT() {
		return DISPENSER_JRT;
	}

	public void setDISPENSER_JRT(int DISPENSER_JRT) {
		this.DISPENSER_JRT = DISPENSER_JRT;
	}

	public int getDISPENSER_HRT() {
		return DISPENSER_HRT;
	}

	public void setDISPENSER_HRT(int DISPENSER_HRT) {
		this.DISPENSER_HRT = DISPENSER_HRT;
	}

	public int getDISPENSER_MULTIFOLD() {
		return DISPENSER_MULTIFOLD;
	}

	public void setDISPENSER_MULTIFOLD(int DISPENSER_MULTIFOLD) {
		this.DISPENSER_MULTIFOLD = DISPENSER_MULTIFOLD;
	}

	public int getDISPENSER_ROLL() {
		return DISPENSER_ROLL;
	}

	public void setDISPENSER_ROLL(int DISPENSER_ROLL) {
		this.DISPENSER_ROLL = DISPENSER_ROLL;
	}

	public String getTYPENAME() {
		return TYPENAME;
	}

	public void setTYPENAME(String TYPENAME) {
		this.TYPENAME = TYPENAME;
	}

	public String getFLAGCUST() {
		return FLAGCUST;
	}

	public void setFLAGCUST(String FLAGCUST) {
		this.FLAGCUST = FLAGCUST;
	}

	public String getPERIODE_ORDER() {
		return PERIODE_ORDER;
	}

	public void setPERIODE_ORDER(String PERIODE_ORDER) {
		this.PERIODE_ORDER = PERIODE_ORDER;
	}

	public String getKTP_ID() {
		return KTP_ID;
	}

	public void setKTP_ID(String KTP_ID) {
		this.KTP_ID = KTP_ID;
	}

	public String getKTP_NAME() {
		return KTP_NAME;
	}

	public void setKTP_NAME(String KTP_NAME) {
		this.KTP_NAME = KTP_NAME;
	}

	public String getKTP_ADDRESS() {
		return KTP_ADDRESS;
	}

	public void setKTP_ADDRESS(String KTP_ADDRESS) {
		this.KTP_ADDRESS = KTP_ADDRESS;
	}

	public String getNPWP_ID() {
		return NPWP_ID;
	}

	public void setNPWP_ID(String NPWP_ID) {
		this.NPWP_ID = NPWP_ID;
	}

	public String getNPWP_NAME() {
		return NPWP_NAME;
	}

	public void setNPWP_NAME(String NPWP_NAME) {
		this.NPWP_NAME = NPWP_NAME;
	}

	public String getNPWP_ADDRESS() {
		return NPWP_ADDRESS;
	}

	public void setNPWP_ADDRESS(String NPWP_ADDRESS) {
		this.NPWP_ADDRESS = NPWP_ADDRESS;
	}

	public String getPAYMENT_TYPE() {
		return PAYMENT_TYPE;
	}

	public void setPAYMENT_TYPE(String PAYMENT_TYPE) {
		this.PAYMENT_TYPE = PAYMENT_TYPE;
	}


	public String getSTATUS_CONTRACT() {
		return STATUS_CONTRACT;
	}

	public void setSTATUS_CONTRACT(String STATUS_CONTRACT) {
		this.STATUS_CONTRACT = STATUS_CONTRACT;
	}

	public String getSTARTDATE_CONTRACT() {
		return STARTDATE_CONTRACT;
	}

	public void setSTARTDATE_CONTRACT(String STARTDATE_CONTRACT) {
		this.STARTDATE_CONTRACT = STARTDATE_CONTRACT;
	}

	public String getENDDATE_CONTRACT() {
		return ENDDATE_CONTRACT;
	}

	public void setENDDATE_CONTRACT(String ENDDATE_CONTRACT) {
		this.ENDDATE_CONTRACT = ENDDATE_CONTRACT;
	}

	public String getFLAGOUT() {
		return FLAGOUT;
	}

	public void setFLAGOUT(String FLAGOUT) {
		this.FLAGOUT = FLAGOUT;
	}

	public String getCOMPANY_NAME() {
		return COMPANY_NAME;
	}

	public void setCOMPANY_NAME(String COMPANY_NAME) {
		this.COMPANY_NAME = COMPANY_NAME;
	}

	public String getTYPEOUT() {
		return TYPEOUT;
	}

	public void setTYPEOUT(String TYPEOUT) {
		this.TYPEOUT = TYPEOUT;
	}

	public int getNUMBER_OF_BRANCH() {
		return NUMBER_OF_BRANCH;
	}

	public void setNUMBER_OF_BRANCH(int NUMBER_OF_BRANCH) {
		this.NUMBER_OF_BRANCH = NUMBER_OF_BRANCH;
	}

	public String getDATE_NEXT_VISIT() {
		return DATE_NEXT_VISIT;
	}

	public void setDATE_NEXT_VISIT(String DATE_NEXT_VISIT) {
		this.DATE_NEXT_VISIT = DATE_NEXT_VISIT;
	}

	public String getORDERNO() {
        return ORDERNO;
    }

    public void setORDERNO(String ORDERNO) {
        this.ORDERNO = ORDERNO;
    }

    public int getTOTAL_INV() {
		return TOTAL_INV;
	}

	public void setTOTAL_INV(int TOTAL_INV) {
		this.TOTAL_INV = TOTAL_INV;
	}

	public int getTOTAL_KUNJUNGAN() {
		return TOTAL_KUNJUNGAN;
	}

	public void setTOTAL_KUNJUNGAN(int TOTAL_KUNJUNGAN) {
		this.TOTAL_KUNJUNGAN = TOTAL_KUNJUNGAN;
	}

	public String getLAST_TRANSACTION() {
		return LAST_TRANSACTION;
	}

	public void setLAST_TRANSACTION(String LAST_TRANSACTION) {
		this.LAST_TRANSACTION = LAST_TRANSACTION;
	}

	public String getISI_TRANSAKSI() {
		return ISI_TRANSAKSI;
	}

	public void setISI_TRANSAKSI(String ISI_TRANSAKSI) {
		this.ISI_TRANSAKSI = ISI_TRANSAKSI;
	}

	public int getSKU() {
		return SKU;
	}

	public void setSKU(int SKU) {
		this.SKU = SKU;
	}

	public String getCUSTADD1() {
		return CUSTADD1;
	}

	public void setCUSTADD1(String CUSTADD1) {
		this.CUSTADD1 = CUSTADD1;
	}

	public String getFLAG_KIRIM() {
		return FLAG_KIRIM;
	}

	public void setFLAG_KIRIM(String KIRIM) {
		this.FLAG_KIRIM = KIRIM;
	}

	public double getINVAMOUNT() {
		return INVAMOUNT;
	}

	public void setINVAMOUNT(double INVAMOUNT) {
		this.INVAMOUNT = INVAMOUNT;
	}

	public String getCUSTNO() {
		return CUSTNO;
	}

	public void setCUSTNO(String CUSTNO) {
		this.CUSTNO = CUSTNO;
	}

	public String getDATA01() {
		return DATA01;
	}

	public void setDATA01(String DATA01) {
		this.DATA01 = DATA01;
	}

	public String getCUSTNAME() {
		return CUSTNAME;
	}

	public void setCUSTNAME(String CUSTNAME) {
		this.CUSTNAME = CUSTNAME;
	}

	public String getCCONTACT() {
		return CCONTACT;
	}

	public void setCCONTACT(String CCONTACT) {
		this.CCONTACT = CCONTACT;
	}

	public String getCPHONE1() {
		return CPHONE1;
	}

	public void setCPHONE1(String CPHONE1) {
		this.CPHONE1 = CPHONE1;
	}

	public String getKELURAHAN() {
		return KELURAHAN;
	}

	public void setKELURAHAN(String KELURAHAN) {
		this.KELURAHAN = KELURAHAN;
	}

	public String getKECAMATAN() {
		return KECAMATAN;
	}

	public void setKECAMATAN(String KECAMATAN) {
		this.KECAMATAN = KECAMATAN;
	}

	public String getKABUPATEN() {
		return KABUPATEN;
	}

	public void setKABUPATEN(String KABUPATEN) {
		this.KABUPATEN = KABUPATEN;
	}

	public String getPROVINSI() {
		return PROVINSI;
	}

	public void setPROVINSI(String PROVINSI) {
		this.PROVINSI = PROVINSI;
	}

	public String getKODEPOS() {
		return KODEPOS;
	}

	public void setKODEPOS(String KODEPOS) {
		this.KODEPOS = KODEPOS;
	}

	public String getALAMAT() {
		return ALAMAT;
	}

	public void setALAMAT(String ALAMAT) {
		this.ALAMAT = ALAMAT;
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

	public String getPHOTO_NAME() {
		return PHOTO_NAME;
	}

	public void setPHOTO_NAME(String PHOTO_NAME) {
		this.PHOTO_NAME = PHOTO_NAME;
	}

	public String getPHOTO_NAME_SAMPING() {
		return PHOTO_NAME_SAMPING;
	}

	public void setPHOTO_NAME_SAMPING(String PHOTO_NAME_SAMPING) {
		this.PHOTO_NAME_SAMPING = PHOTO_NAME_SAMPING;
	}

	public Obj_CUSTMST getData(String sCustNo){

		try{
			Cursor cur = AppConstant.myDb.rawQuery("SELECT * FROM TBL_CUSTMSTNEW WHERE CUSTNO = '"+sCustNo+"'", null);
			if (cur.getCount() > 0){
				cur.moveToFirst();
				this.CUSTNO = cur.getString(cur.getColumnIndex("CUSTNO"));
				this.DATA01 = cur.getString(cur.getColumnIndex("DATA01"));
				this.CUSTNAME = cur.getString(cur.getColumnIndex("CUSTNAME"));
				this.CUSTADD1 = cur.getString(cur.getColumnIndex("CUSTADD1"));
				this.CCONTACT = cur.getString(cur.getColumnIndex("CCONTACT"));
				this.CPHONE1 = cur.getString(cur.getColumnIndex("CPHONE1"));
				this.KELURAHAN = cur.getString(cur.getColumnIndex("KELURAHAN"));
				this.KECAMATAN = cur.getString(cur.getColumnIndex("KECAMATAN"));
				this.KABUPATEN = cur.getString(cur.getColumnIndex("KABUPATEN"));
				this.PROVINSI = cur.getString(cur.getColumnIndex("PROVINSI"));
				this.KODEPOS = cur.getString(cur.getColumnIndex("KODEPOS"));
				this.ALAMAT = cur.getString(cur.getColumnIndex("ALAMAT"));
				this.LATITUDE = cur.getString(cur.getColumnIndex("LATITUDE"));
				this.LONGITUDE = cur.getString(cur.getColumnIndex("LONGITUDE"));
				this.PHOTO_NAME = cur.getString(cur.getColumnIndex("PHOTO_NAME"));
				this.PHOTO_NAME_SAMPING = cur.getString(cur.getColumnIndex("PHOTO_NAME_SAMPING"));
				this.DATE_NEXT_VISIT = cur.getString(cur.getColumnIndex("DATE_NEXT_VISIT"));
				this.NUMBER_OF_BRANCH = cur.getInt(cur.getColumnIndex("NUMBER_OF_BRANCH"));
				this.TYPEOUT = cur.getString(cur.getColumnIndex("TYPEOUT"));
				this.COMPANY_NAME = cur.getString(cur.getColumnIndex("COMPANY_NAME"));
				this.FLAGOUT = cur.getString(cur.getColumnIndex("FLAGOUT"));
				this.FLAGCUST = cur.getString(cur.getColumnIndex("FLAGCUST"));
				this.KTP_ID = cur.getString(cur.getColumnIndex("KTP_ID"));
				this.KTP_NAME = cur.getString(cur.getColumnIndex("KTP_NAME"));
				this.KTP_ADDRESS = cur.getString(cur.getColumnIndex("KTP_ADDRESS"));
				this.NPWP_ID = cur.getString(cur.getColumnIndex("NPWP_ID"));
				this.NPWP_NAME = cur.getString(cur.getColumnIndex("NPWP_NAME"));
				this.NPWP_ADDRESS = cur.getString(cur.getColumnIndex("NPWP_ADDRESS"));
				this.PAYMENT_TYPE = cur.getString(cur.getColumnIndex("PAYMENT_TYPE"));
				this.PERIODE_ORDER = cur.getString(cur.getColumnIndex("PERIODE_ORDER"));
				this.STATUS_CONTRACT = cur.getString(cur.getColumnIndex("STATUS_CONTRACT"));
				this.STARTDATE_CONTRACT = cur.getString(cur.getColumnIndex("STARTDATE_CONTRACT"));
				this.ENDDATE_CONTRACT = cur.getString(cur.getColumnIndex("ENDDATE_CONTRACT"));
				this.STATUS_DISPENSER = cur.getString(cur.getColumnIndex("STATUS_CONTRACT"));
				this.DISPENSER_JRT = cur.getInt(cur.getColumnIndex("DISPENSER_JRT"));
				this.DISPENSER_HRT = cur.getInt(cur.getColumnIndex("DISPENSER_HRT"));
				this.DISPENSER_MULTIFOLD = cur.getInt(cur.getColumnIndex("DISPENSER_MULTIFOLD"));
				this.DISPENSER_ROLL = cur.getInt(cur.getColumnIndex("DISPENSER_ROLL"));
			}
			cur.close();

		}catch (Exception e){

		}

		return this;
	}


	public ArrayList<Obj_CUSTMST> getDataListFlagKirim(String sKeyWord){
		ArrayList<Obj_CUSTMST> AlisData  = new ArrayList<Obj_CUSTMST>();

		Cursor cur;

		try{
			cur = AppConstant.myDb.rawQuery("SELECT * FROM Tbl_CUSTMSTNEW" +
					" WHERE FLAG_KIRIM = '"+sKeyWord+"'", null);

			if (cur.getCount() > 0){
//			c.moveToFirst();
				while (cur.moveToNext()){
					Obj_CUSTMST prodDat = new Obj_CUSTMST();
					prodDat.CUSTNO = cur.getString(cur.getColumnIndex("CUSTNO"));
					prodDat.DATA01 = cur.getString(cur.getColumnIndex("DATA01"));
					prodDat.CUSTNAME = cur.getString(cur.getColumnIndex("CUSTNAME"));
					prodDat.CUSTADD1 = cur.getString(cur.getColumnIndex("CUSTADD1"));
					prodDat.CCONTACT = cur.getString(cur.getColumnIndex("CCONTACT"));
					prodDat.CPHONE1 = cur.getString(cur.getColumnIndex("CPHONE1"));
					prodDat.KELURAHAN = cur.getString(cur.getColumnIndex("KELURAHAN"));
					prodDat.KECAMATAN = cur.getString(cur.getColumnIndex("KECAMATAN"));
					prodDat.KABUPATEN = cur.getString(cur.getColumnIndex("KABUPATEN"));
					prodDat.PROVINSI = cur.getString(cur.getColumnIndex("PROVINSI"));
					prodDat.KODEPOS = cur.getString(cur.getColumnIndex("KODEPOS"));
					prodDat.ALAMAT = cur.getString(cur.getColumnIndex("ALAMAT"));
					prodDat.LATITUDE = cur.getString(cur.getColumnIndex("LATITUDE"));
					prodDat.LONGITUDE = cur.getString(cur.getColumnIndex("LONGITUDE"));
					prodDat.PHOTO_NAME = cur.getString(cur.getColumnIndex("PHOTO_NAME"));
					prodDat.PHOTO_NAME_SAMPING = cur.getString(cur.getColumnIndex("PHOTO_NAME_SAMPING"));
					prodDat.DATE_NEXT_VISIT = cur.getString(cur.getColumnIndex("DATE_NEXT_VISIT"));
					prodDat.NUMBER_OF_BRANCH = cur.getInt(cur.getColumnIndex("NUMBER_OF_BRANCH"));
					prodDat.TYPEOUT = cur.getString(cur.getColumnIndex("TYPEOUT"));
					prodDat.COMPANY_NAME = cur.getString(cur.getColumnIndex("COMPANY_NAME"));
					prodDat.FLAGOUT = cur.getString(cur.getColumnIndex("FLAGOUT"));
					prodDat.FLAGCUST = cur.getString(cur.getColumnIndex("FLAGCUST"));
					prodDat.KTP_ID = cur.getString(cur.getColumnIndex("KTP_ID"));
					prodDat.KTP_NAME = cur.getString(cur.getColumnIndex("KTP_NAME"));
					prodDat.KTP_ADDRESS = cur.getString(cur.getColumnIndex("KTP_ADDRESS"));
					prodDat.NPWP_ID = cur.getString(cur.getColumnIndex("NPWP_ID"));
					prodDat.NPWP_NAME = cur.getString(cur.getColumnIndex("NPWP_NAME"));
					prodDat.NPWP_ADDRESS = cur.getString(cur.getColumnIndex("NPWP_ADDRESS"));
					prodDat.PAYMENT_TYPE = cur.getString(cur.getColumnIndex("PAYMENT_TYPE"));
					prodDat.PERIODE_ORDER = cur.getString(cur.getColumnIndex("PERIODE_ORDER"));
					prodDat.STATUS_CONTRACT = cur.getString(cur.getColumnIndex("STATUS_CONTRACT"));
					prodDat.STARTDATE_CONTRACT = cur.getString(cur.getColumnIndex("STARTDATE_CONTRACT"));
					prodDat.ENDDATE_CONTRACT = cur.getString(cur.getColumnIndex("ENDDATE_CONTRACT"));
					prodDat.STATUS_DISPENSER = cur.getString(cur.getColumnIndex("STATUS_CONTRACT"));
					prodDat.DISPENSER_JRT = cur.getInt(cur.getColumnIndex("DISPENSER_JRT"));
					prodDat.DISPENSER_HRT = cur.getInt(cur.getColumnIndex("DISPENSER_HRT"));
					prodDat.DISPENSER_MULTIFOLD = cur.getInt(cur.getColumnIndex("DISPENSER_MULTIFOLD"));
					prodDat.DISPENSER_ROLL = cur.getInt(cur.getColumnIndex("DISPENSER_ROLL"));
					AlisData.add(prodDat);
				}

			}
			cur.close();
		}catch (Exception e){

		}

		return AlisData;
	}

	public ArrayList<Obj_CUSTMST> getDataListReg(String sKeyWord){
		ArrayList<Obj_CUSTMST> AlisData  = new ArrayList<Obj_CUSTMST>();

		Cursor cur;

		try{
			if (sKeyWord.equals("0")){
				cur = AppConstant.myDb.rawQuery("SELECT A.*, B.TYPENAME FROM Tbl_CUSTMSTNEW A" +
						" LEFT JOIN TBL_TYPEOUT B" +
						" ON A.TYPEOUT = B.TYPEOUT" +
						" ORDER BY A.FLAGOUT" , null);
			}else {
				cur = AppConstant.myDb.rawQuery("SELECT A.*, B.TYPENAME FROM Tbl_CUSTMSTNEW A" +
						" LEFT JOIN TBL_TYPEOUT B" +
						" ON A.TYPEOUT = B.TYPEOUT" +
						" WHERE A.FLAGOUT = '"+sKeyWord+"'" +
						" ORDER BY A.FLAGOUT ", null);
			}


			if (cur.getCount() > 0){
//			c.moveToFirst();
				while (cur.moveToNext()){
					Obj_CUSTMST prodDat = new Obj_CUSTMST();
					prodDat.CUSTNO = cur.getString(cur.getColumnIndex("CUSTNO"));
					prodDat.DATA01 = cur.getString(cur.getColumnIndex("DATA01"));
					prodDat.CUSTNAME = cur.getString(cur.getColumnIndex("CUSTNAME"));
					prodDat.CUSTADD1 = cur.getString(cur.getColumnIndex("CUSTADD1"));
					prodDat.CCONTACT = cur.getString(cur.getColumnIndex("CCONTACT"));
					prodDat.CPHONE1 = cur.getString(cur.getColumnIndex("CPHONE1"));
					prodDat.KELURAHAN = cur.getString(cur.getColumnIndex("KELURAHAN"));
					prodDat.KECAMATAN = cur.getString(cur.getColumnIndex("KECAMATAN"));
					prodDat.KABUPATEN = cur.getString(cur.getColumnIndex("KABUPATEN"));
					prodDat.PROVINSI = cur.getString(cur.getColumnIndex("PROVINSI"));
					prodDat.KODEPOS = cur.getString(cur.getColumnIndex("KODEPOS"));
					prodDat.ALAMAT = cur.getString(cur.getColumnIndex("ALAMAT"));
					prodDat.LATITUDE = cur.getString(cur.getColumnIndex("LATITUDE"));
					prodDat.LONGITUDE = cur.getString(cur.getColumnIndex("LONGITUDE"));
					prodDat.PHOTO_NAME = cur.getString(cur.getColumnIndex("PHOTO_NAME"));
					prodDat.PHOTO_NAME_SAMPING = cur.getString(cur.getColumnIndex("PHOTO_NAME_SAMPING"));
					prodDat.DATE_NEXT_VISIT = cur.getString(cur.getColumnIndex("DATE_NEXT_VISIT"));
					prodDat.NUMBER_OF_BRANCH = cur.getInt(cur.getColumnIndex("NUMBER_OF_BRANCH"));
					prodDat.TYPEOUT = cur.getString(cur.getColumnIndex("TYPEOUT"));
					prodDat.TYPENAME = cur.getString(cur.getColumnIndex("TYPENAME"));
					prodDat.COMPANY_NAME = cur.getString(cur.getColumnIndex("COMPANY_NAME"));
					prodDat.FLAGOUT = cur.getString(cur.getColumnIndex("FLAGOUT"));
					prodDat.FLAGCUST = cur.getString(cur.getColumnIndex("FLAGCUST"));
					prodDat.KTP_ID = cur.getString(cur.getColumnIndex("KTP_ID"));
					prodDat.KTP_NAME = cur.getString(cur.getColumnIndex("KTP_NAME"));
					prodDat.KTP_ADDRESS = cur.getString(cur.getColumnIndex("KTP_ADDRESS"));
					prodDat.NPWP_ID = cur.getString(cur.getColumnIndex("NPWP_ID"));
					prodDat.NPWP_NAME = cur.getString(cur.getColumnIndex("NPWP_NAME"));
					prodDat.NPWP_ADDRESS = cur.getString(cur.getColumnIndex("NPWP_ADDRESS"));
					prodDat.PAYMENT_TYPE = cur.getString(cur.getColumnIndex("PAYMENT_TYPE"));
					prodDat.PERIODE_ORDER = cur.getString(cur.getColumnIndex("PERIODE_ORDER"));
					prodDat.STATUS_CONTRACT = cur.getString(cur.getColumnIndex("STATUS_CONTRACT"));
					prodDat.STARTDATE_CONTRACT = cur.getString(cur.getColumnIndex("STARTDATE_CONTRACT"));
					prodDat.ENDDATE_CONTRACT = cur.getString(cur.getColumnIndex("ENDDATE_CONTRACT"));
					prodDat.STATUS_DISPENSER = cur.getString(cur.getColumnIndex("STATUS_CONTRACT"));
					prodDat.DISPENSER_JRT = cur.getInt(cur.getColumnIndex("DISPENSER_JRT"));
					prodDat.DISPENSER_HRT = cur.getInt(cur.getColumnIndex("DISPENSER_HRT"));
					prodDat.DISPENSER_MULTIFOLD = cur.getInt(cur.getColumnIndex("DISPENSER_MULTIFOLD"));
					prodDat.DISPENSER_ROLL = cur.getInt(cur.getColumnIndex("DISPENSER_ROLL"));
					AlisData.add(prodDat);
				}

			}
			cur.close();
		}catch (Exception e){

		}

		return AlisData;
	}


	public ArrayList<Obj_CUSTMST> getDataListNoo(String sKeyWord, String KELURAHAN){
		ArrayList<Obj_CUSTMST> AlisData  = new ArrayList<Obj_CUSTMST>();
		
		Cursor cur;

		try{
			if (sKeyWord.equals("")){

				if (!KELURAHAN.toUpperCase().equals("SEMUA") && !KELURAHAN.equals("")){
					cur = AppConstant.myDb.rawQuery("SELECT A.*, B.TYPENAME from (SELECT A.*, B.FLAG_EC AS ISI_TRANSAKSI FROM TBL_CUSTMSTNEW A" +
									" LEFT JOIN (SELECT CUSTNO, FLAG_EC from TBL_CUSTCARD1" +
									" GROUP BY CUSTNO) B" +
									" ON A.CUSTNO = B.CUSTNO)A" +
									" LEFT JOIN TBL_TYPEOUT B" +
									" ON A.TYPEOUT = B.TYPEOUT" +
									" WHERE A.KELURAHAN = '"+KELURAHAN+"'" +
									" ORDER BY A.FLAGOUT", null);
				}else{
					cur = AppConstant.myDb.rawQuery("SELECT A.*, B.TYPENAME from (SELECT A.*, B.FLAG_EC AS ISI_TRANSAKSI FROM TBL_CUSTMSTNEW A" +
							" LEFT JOIN (SELECT CUSTNO, FLAG_EC from TBL_CUSTCARD1" +
							" GROUP BY CUSTNO) B" +
							" LEFT JOIN TBL_TYPEOUT B" +
							" ON A.TYPEOUT = B.TYPEOUT" +
							" ON A.CUSTNO = B.CUSTNO)A" +
									" ORDER BY A.FLAGOUT"
							, null);
				}
			}else {
				if (!KELURAHAN.toUpperCase().equals("SEMUA")){
					cur = AppConstant.myDb.rawQuery("SELECT A.*, B.TYPENAME from (SELECT A.*, B.FLAG_EC AS ISI_TRANSAKSI FROM TBL_CUSTMSTNEW A" +
							" LEFT JOIN (SELECT CUSTNO, FLAG_EC from TBL_CUSTCARD1" +
							" GROUP BY CUSTNO) B" +
							" ON A.CUSTNO = B.CUSTNO)A" +
							" LEFT JOIN TBL_TYPEOUT B" +
							" ON A.TYPEOUT = B.TYPEOUT" +
							" WHERE A.KELURAHAN = '"+KELURAHAN+"' AND" +
							" (A.CUSTNO LIKE '%"+sKeyWord+"%'" +
							" OR CUSTNAME LIKE '%"+sKeyWord+"%'" +
							" OR CUSTADD1 LIKE '%"+sKeyWord+"%'" +
							" OR CPHONE1 LIKE '%"+sKeyWord+"%')" +
							" ORDER BY A.FLAGOUT", null);
				}else{
					cur = AppConstant.myDb.rawQuery("SELECT A.*, B.TYPENAME from (SELECT A.*, B.FLAG_EC AS ISI_TRANSAKSI FROM TBL_CUSTMSTNEW A" +
							" LEFT JOIN (SELECT CUSTNO, FLAG_EC from TBL_CUSTCARD1" +
							" GROUP BY CUSTNO) B" +
							" ON A.CUSTNO = B.CUSTNO) A" +
							" LEFT JOIN TBL_TYPEOUT B" +
							" ON A.TYPEOUT = B.TYPEOUT" +
							" WHERE " +
							" (A.CUSTNO LIKE '%"+sKeyWord+"%'" +
							" OR CUSTNAME LIKE '%"+sKeyWord+"%'" +
							" OR CUSTADD1 LIKE '%"+sKeyWord+"%'" +
							" OR CPHONE1 LIKE '%"+sKeyWord+"%')" +
							" ORDER BY A.FLAGOUT", null);
				}

			}


			if (cur.getCount() > 0){
//			c.moveToFirst();
				while (cur.moveToNext()){
					Obj_CUSTMST prodDat = new Obj_CUSTMST();
					prodDat.CUSTNO = cur.getString(cur.getColumnIndex("CUSTNO"));
					prodDat.CUSTNO = cur.getString(cur.getColumnIndex("CUSTNO"));
					prodDat.DATA01 = cur.getString(cur.getColumnIndex("DATA01"));
					prodDat.CUSTNAME = cur.getString(cur.getColumnIndex("CUSTNAME"));
					prodDat.CUSTADD1 = cur.getString(cur.getColumnIndex("CUSTADD1"));
					prodDat.CCONTACT = cur.getString(cur.getColumnIndex("CCONTACT"));
					prodDat.CPHONE1 = cur.getString(cur.getColumnIndex("CPHONE1"));
					prodDat.KELURAHAN = cur.getString(cur.getColumnIndex("KELURAHAN"));
					prodDat.KECAMATAN = cur.getString(cur.getColumnIndex("KECAMATAN"));
					prodDat.KABUPATEN = cur.getString(cur.getColumnIndex("KABUPATEN"));
					prodDat.PROVINSI = cur.getString(cur.getColumnIndex("PROVINSI"));
					prodDat.KODEPOS = cur.getString(cur.getColumnIndex("KODEPOS"));
					prodDat.ALAMAT = cur.getString(cur.getColumnIndex("ALAMAT"));
					prodDat.LATITUDE = cur.getString(cur.getColumnIndex("LATITUDE"));
					prodDat.LONGITUDE = cur.getString(cur.getColumnIndex("LONGITUDE"));
					prodDat.PHOTO_NAME = cur.getString(cur.getColumnIndex("PHOTO_NAME"));
					prodDat.PHOTO_NAME_SAMPING = cur.getString(cur.getColumnIndex("PHOTO_NAME_SAMPING"));
					prodDat.ISI_TRANSAKSI = cur.getString(cur.getColumnIndex("ISI_TRANSAKSI"));
					prodDat.TOTAL_KUNJUNGAN = cur.getInt(cur.getColumnIndex("TOTAL_KUNJUNGAN"));
					prodDat.LAST_TRANSACTION = cur.getString(cur.getColumnIndex("LAST_TRANSACTION"));
					prodDat.DATE_NEXT_VISIT = cur.getString(cur.getColumnIndex("DATE_NEXT_VISIT"));
					prodDat.NUMBER_OF_BRANCH = cur.getInt(cur.getColumnIndex("NUMBER_OF_BRANCH"));
					prodDat.TYPEOUT = cur.getString(cur.getColumnIndex("TYPEOUT"));
					prodDat.TYPENAME = cur.getString(cur.getColumnIndex("TYPENAME"));
					prodDat.COMPANY_NAME = cur.getString(cur.getColumnIndex("COMPANY_NAME"));
					prodDat.FLAGOUT = cur.getString(cur.getColumnIndex("FLAGOUT"));
					prodDat.FLAGCUST = cur.getString(cur.getColumnIndex("FLAGCUST"));
					prodDat.KTP_ID = cur.getString(cur.getColumnIndex("KTP_ID"));
					prodDat.KTP_NAME = cur.getString(cur.getColumnIndex("KTP_NAME"));
					prodDat.KTP_ADDRESS = cur.getString(cur.getColumnIndex("KTP_ADDRESS"));
					prodDat.NPWP_ID = cur.getString(cur.getColumnIndex("NPWP_ID"));
					prodDat.NPWP_NAME = cur.getString(cur.getColumnIndex("NPWP_NAME"));
					prodDat.NPWP_ADDRESS = cur.getString(cur.getColumnIndex("NPWP_ADDRESS"));
					prodDat.PAYMENT_TYPE = cur.getString(cur.getColumnIndex("PAYMENT_TYPE"));
					prodDat.PERIODE_ORDER = cur.getString(cur.getColumnIndex("PERIODE_ORDER"));
					prodDat.STATUS_CONTRACT = cur.getString(cur.getColumnIndex("STATUS_CONTRACT"));
					prodDat.STARTDATE_CONTRACT = cur.getString(cur.getColumnIndex("STARTDATE_CONTRACT"));
					prodDat.ENDDATE_CONTRACT = cur.getString(cur.getColumnIndex("ENDDATE_CONTRACT"));
					prodDat.STATUS_DISPENSER = cur.getString(cur.getColumnIndex("STATUS_CONTRACT"));
					prodDat.DISPENSER_JRT = cur.getInt(cur.getColumnIndex("DISPENSER_JRT"));
					prodDat.DISPENSER_HRT = cur.getInt(cur.getColumnIndex("DISPENSER_HRT"));
					prodDat.DISPENSER_MULTIFOLD = cur.getInt(cur.getColumnIndex("DISPENSER_MULTIFOLD"));
					prodDat.DISPENSER_ROLL = cur.getInt(cur.getColumnIndex("DISPENSER_ROLL"));
					AlisData.add(prodDat);
				}

			}
			cur.close();
		}catch (Exception e){

		}

		return AlisData;
	}

	public ArrayList<Obj_CUSTMST> getDataList(String sKeyWord, String KELURAHAN, String FLAG_PJP){
		ArrayList<Obj_CUSTMST> AlisData  = new ArrayList<Obj_CUSTMST>();

		Cursor cur;

		try{
			if (sKeyWord.equals("")){

				if (!KELURAHAN.toUpperCase().equals("SEMUA") && !KELURAHAN.equals("")){
					cur = AppConstant.myDb.rawQuery("SELECT A.*, B.TYPENAME from (SELECT A.*, B.FLAG_EC AS ISI_TRANSAKSI FROM TBL_CUSTMSTNEW A" +
							" LEFT JOIN (SELECT CUSTNO, FLAG_EC from TBL_CUSTCARD1" +
							" GROUP BY CUSTNO) B" +
							" ON A.CUSTNO = B.CUSTNO)A" +
							" LEFT JOIN TBL_TYPEOUT B" +
							" ON A.TYPEOUT = B.TYPEOUT" +
							" WHERE A.KELURAHAN = '"+KELURAHAN+"' AND A.FLAG_PJP = '"+FLAG_PJP+"'", null);
				}else{
					cur = AppConstant.myDb.rawQuery("SELECT A.*, B.TYPENAME  from (SELECT A.*, B.FLAG_EC AS ISI_TRANSAKSI FROM TBL_CUSTMSTNEW A" +
							" LEFT JOIN (SELECT CUSTNO, FLAG_EC from TBL_CUSTCARD1" +
							" GROUP BY CUSTNO) B" +
							" ON A.CUSTNO = B.CUSTNO)A" +
							" LEFT JOIN TBL_TYPEOUT B" +
							" ON A.TYPEOUT = B.TYPEOUT" +
							" WHERE FLAGOUT = '2' AND A.FLAG_PJP = '"+FLAG_PJP+"'", null);
				}
			}else {
				if (!KELURAHAN.toUpperCase().equals("SEMUA")){
					cur = AppConstant.myDb.rawQuery("SELECT A.*, B.TYPENAME  from (SELECT A.*, B.FLAG_EC AS ISI_TRANSAKSI FROM TBL_CUSTMSTNEW A" +
							" LEFT JOIN (SELECT CUSTNO, FLAG_EC from TBL_CUSTCARD1" +
							" GROUP BY CUSTNO) B" +
							" ON A.CUSTNO = B.CUSTNO)A" +
							" LEFT JOIN TBL_TYPEOUT B" +
							" ON A.TYPEOUT = B.TYPEOUT" +
							" WHERE A.KELURAHAN = '"+KELURAHAN+"' AND" +
							" (A.CUSTNO LIKE '%"+sKeyWord+"%'" +
							" OR CUSTNAME LIKE '%"+sKeyWord+"%'" +
							" OR CUSTADD1 LIKE '%"+sKeyWord+"%'" +
							" OR CPHONE1 LIKE '%"+sKeyWord+"%')" +
							" AND FLAGOUT = '2' ", null);
				}else{
					cur = AppConstant.myDb.rawQuery("SELECT A.*, B.TYPENAME  from (SELECT A.*, B.FLAG_EC AS ISI_TRANSAKSI FROM TBL_CUSTMSTNEW A" +
							" LEFT JOIN (SELECT CUSTNO, FLAG_EC from TBL_CUSTCARD1" +
							" GROUP BY CUSTNO) B" +
							" ON A.CUSTNO = B.CUSTNO) A" +
							" LEFT JOIN TBL_TYPEOUT B" +
							" ON A.TYPEOUT = B.TYPEOUT" +
							" WHERE " +
							" (A.CUSTNO LIKE '%"+sKeyWord+"%'" +
							" OR CUSTNAME LIKE '%"+sKeyWord+"%'" +
							" OR CUSTADD1 LIKE '%"+sKeyWord+"%'" +
							" OR CPHONE1 LIKE '%"+sKeyWord+"%')" +
							" AND FLAGOUT = '2'", null);
				}

			}


			if (cur.getCount() > 0){
//			c.moveToFirst();
				while (cur.moveToNext()){
					Obj_CUSTMST prodDat = new Obj_CUSTMST();
					prodDat.CUSTNO = cur.getString(cur.getColumnIndex("CUSTNO"));
					prodDat.CUSTNO = cur.getString(cur.getColumnIndex("CUSTNO"));
					prodDat.DATA01 = cur.getString(cur.getColumnIndex("DATA01"));
					prodDat.CUSTNAME = cur.getString(cur.getColumnIndex("CUSTNAME"));
					prodDat.CUSTADD1 = cur.getString(cur.getColumnIndex("CUSTADD1"));
					prodDat.CCONTACT = cur.getString(cur.getColumnIndex("CCONTACT"));
					prodDat.CPHONE1 = cur.getString(cur.getColumnIndex("CPHONE1"));
					prodDat.KELURAHAN = cur.getString(cur.getColumnIndex("KELURAHAN"));
					prodDat.KECAMATAN = cur.getString(cur.getColumnIndex("KECAMATAN"));
					prodDat.KABUPATEN = cur.getString(cur.getColumnIndex("KABUPATEN"));
					prodDat.PROVINSI = cur.getString(cur.getColumnIndex("PROVINSI"));
					prodDat.KODEPOS = cur.getString(cur.getColumnIndex("KODEPOS"));
					prodDat.ALAMAT = cur.getString(cur.getColumnIndex("ALAMAT"));
					prodDat.LATITUDE = cur.getString(cur.getColumnIndex("LATITUDE"));
					prodDat.LONGITUDE = cur.getString(cur.getColumnIndex("LONGITUDE"));
					prodDat.PHOTO_NAME = cur.getString(cur.getColumnIndex("PHOTO_NAME"));
					prodDat.PHOTO_NAME_SAMPING = cur.getString(cur.getColumnIndex("PHOTO_NAME_SAMPING"));
					prodDat.ISI_TRANSAKSI = cur.getString(cur.getColumnIndex("ISI_TRANSAKSI"));
					prodDat.TOTAL_KUNJUNGAN = cur.getInt(cur.getColumnIndex("TOTAL_KUNJUNGAN"));
					prodDat.LAST_TRANSACTION = cur.getString(cur.getColumnIndex("LAST_TRANSACTION"));
					prodDat.DATE_NEXT_VISIT = cur.getString(cur.getColumnIndex("DATE_NEXT_VISIT"));
					prodDat.NUMBER_OF_BRANCH = cur.getInt(cur.getColumnIndex("NUMBER_OF_BRANCH"));
					prodDat.TYPEOUT = cur.getString(cur.getColumnIndex("TYPEOUT"));
					prodDat.TYPENAME = cur.getString(cur.getColumnIndex("TYPENAME"));
					prodDat.COMPANY_NAME = cur.getString(cur.getColumnIndex("COMPANY_NAME"));
					prodDat.FLAGOUT = cur.getString(cur.getColumnIndex("FLAGOUT"));
					prodDat.FLAGCUST = cur.getString(cur.getColumnIndex("FLAGCUST"));
					prodDat.KTP_ID = cur.getString(cur.getColumnIndex("KTP_ID"));
					prodDat.KTP_NAME = cur.getString(cur.getColumnIndex("KTP_NAME"));
					prodDat.KTP_ADDRESS = cur.getString(cur.getColumnIndex("KTP_ADDRESS"));
					prodDat.NPWP_ID = cur.getString(cur.getColumnIndex("NPWP_ID"));
					prodDat.NPWP_NAME = cur.getString(cur.getColumnIndex("NPWP_NAME"));
					prodDat.NPWP_ADDRESS = cur.getString(cur.getColumnIndex("NPWP_ADDRESS"));
					prodDat.PAYMENT_TYPE = cur.getString(cur.getColumnIndex("PAYMENT_TYPE"));
					prodDat.PERIODE_ORDER = cur.getString(cur.getColumnIndex("PERIODE_ORDER"));
					prodDat.STATUS_CONTRACT = cur.getString(cur.getColumnIndex("STATUS_CONTRACT"));
					prodDat.STARTDATE_CONTRACT = cur.getString(cur.getColumnIndex("STARTDATE_CONTRACT"));
					prodDat.ENDDATE_CONTRACT = cur.getString(cur.getColumnIndex("ENDDATE_CONTRACT"));
					prodDat.STATUS_DISPENSER = cur.getString(cur.getColumnIndex("STATUS_CONTRACT"));
					prodDat.DISPENSER_JRT = cur.getInt(cur.getColumnIndex("DISPENSER_JRT"));
					prodDat.DISPENSER_HRT = cur.getInt(cur.getColumnIndex("DISPENSER_HRT"));
					prodDat.DISPENSER_MULTIFOLD = cur.getInt(cur.getColumnIndex("DISPENSER_MULTIFOLD"));
					prodDat.DISPENSER_ROLL = cur.getInt(cur.getColumnIndex("DISPENSER_ROLL"));
					AlisData.add(prodDat);
				}

			}
			cur.close();
		}catch (Exception e){

		}

		return AlisData;
	}

	public ArrayList<Obj_CUSTMST> getDataListTransaction(boolean TRANSACTION){
		ArrayList<Obj_CUSTMST> AlisData  = new ArrayList<Obj_CUSTMST>();

		Cursor cur;

		try{
			if (TRANSACTION){
				cur = AppConstant.myDb.rawQuery("SELECT A.*, B.TYPENAME  from (SELECT A.*, B.FLAG_EC AS ISI_TRANSAKSI FROM TBL_CUSTMSTNEW A" +
						" LEFT JOIN (SELECT CUSTNO, FLAG_EC from TBL_CUSTCARD1" +
						" GROUP BY CUSTNO) B" +
						" ON A.CUSTNO = B.CUSTNO)A" +
						" LEFT JOIN TBL_TYPEOUT B" +
						" ON A.TYPEOUT = B.TYPEOUT" +
						" WHERE A.ISI_TRANSAKSI NOT NULL AND A.FLAGOUT = '2'", null);
			}else{
				cur = AppConstant.myDb.rawQuery("SELECT A.*, B.TYPENAME  from (SELECT A.*, B.FLAG_EC AS ISI_TRANSAKSI FROM TBL_CUSTMSTNEW A" +
						" LEFT JOIN (SELECT CUSTNO, FLAG_EC from TBL_CUSTCARD1" +
						" GROUP BY CUSTNO) B" +
						" ON A.CUSTNO = B.CUSTNO)A" +
						" LEFT JOIN TBL_TYPEOUT B" +
						" ON A.TYPEOUT = B.TYPEOUT" +
						" WHERE A.ISI_TRANSAKSI IS NULL AND A.FLAGOUT = '2'", null);
			}



			if (cur.getCount() > 0){
//			c.moveToFirst();
				while (cur.moveToNext()){
					Obj_CUSTMST prodDat = new Obj_CUSTMST();
					prodDat.CUSTNO = cur.getString(cur.getColumnIndex("CUSTNO"));
					prodDat.CUSTNO = cur.getString(cur.getColumnIndex("CUSTNO"));
					prodDat.DATA01 = cur.getString(cur.getColumnIndex("DATA01"));
					prodDat.CUSTNAME = cur.getString(cur.getColumnIndex("CUSTNAME"));
					prodDat.CUSTADD1 = cur.getString(cur.getColumnIndex("CUSTADD1"));
					prodDat.CCONTACT = cur.getString(cur.getColumnIndex("CCONTACT"));
					prodDat.CPHONE1 = cur.getString(cur.getColumnIndex("CPHONE1"));
					prodDat.KELURAHAN = cur.getString(cur.getColumnIndex("KELURAHAN"));
					prodDat.KECAMATAN = cur.getString(cur.getColumnIndex("KECAMATAN"));
					prodDat.KABUPATEN = cur.getString(cur.getColumnIndex("KABUPATEN"));
					prodDat.PROVINSI = cur.getString(cur.getColumnIndex("PROVINSI"));
					prodDat.KODEPOS = cur.getString(cur.getColumnIndex("KODEPOS"));
					prodDat.ALAMAT = cur.getString(cur.getColumnIndex("ALAMAT"));
					prodDat.LATITUDE = cur.getString(cur.getColumnIndex("LATITUDE"));
					prodDat.LONGITUDE = cur.getString(cur.getColumnIndex("LONGITUDE"));
					prodDat.PHOTO_NAME = cur.getString(cur.getColumnIndex("PHOTO_NAME"));
					prodDat.PHOTO_NAME_SAMPING = cur.getString(cur.getColumnIndex("PHOTO_NAME_SAMPING"));
					prodDat.ISI_TRANSAKSI = cur.getString(cur.getColumnIndex("ISI_TRANSAKSI"));
					prodDat.TOTAL_KUNJUNGAN = cur.getInt(cur.getColumnIndex("TOTAL_KUNJUNGAN"));
					prodDat.LAST_TRANSACTION = cur.getString(cur.getColumnIndex("LAST_TRANSACTION"));
					prodDat.DATE_NEXT_VISIT = cur.getString(cur.getColumnIndex("DATE_NEXT_VISIT"));
					prodDat.NUMBER_OF_BRANCH = cur.getInt(cur.getColumnIndex("NUMBER_OF_BRANCH"));
					prodDat.TYPEOUT = cur.getString(cur.getColumnIndex("TYPEOUT"));
					prodDat.COMPANY_NAME = cur.getString(cur.getColumnIndex("COMPANY_NAME"));
					prodDat.FLAGOUT = cur.getString(cur.getColumnIndex("FLAGOUT"));
					prodDat.FLAGCUST = cur.getString(cur.getColumnIndex("FLAGCUST"));
					prodDat.KTP_ID = cur.getString(cur.getColumnIndex("KTP_ID"));
					prodDat.KTP_NAME = cur.getString(cur.getColumnIndex("KTP_NAME"));
					prodDat.KTP_ADDRESS = cur.getString(cur.getColumnIndex("KTP_ADDRESS"));
					prodDat.NPWP_ID = cur.getString(cur.getColumnIndex("NPWP_ID"));
					prodDat.NPWP_NAME = cur.getString(cur.getColumnIndex("NPWP_NAME"));
					prodDat.NPWP_ADDRESS = cur.getString(cur.getColumnIndex("NPWP_ADDRESS"));
					prodDat.PAYMENT_TYPE = cur.getString(cur.getColumnIndex("PAYMENT_TYPE"));
					prodDat.PERIODE_ORDER = cur.getString(cur.getColumnIndex("PERIODE_ORDER"));
					prodDat.STATUS_CONTRACT = cur.getString(cur.getColumnIndex("STATUS_CONTRACT"));
					prodDat.STARTDATE_CONTRACT = cur.getString(cur.getColumnIndex("STARTDATE_CONTRACT"));
					prodDat.ENDDATE_CONTRACT = cur.getString(cur.getColumnIndex("ENDDATE_CONTRACT"));
					prodDat.STATUS_DISPENSER = cur.getString(cur.getColumnIndex("STATUS_CONTRACT"));
					prodDat.DISPENSER_JRT = cur.getInt(cur.getColumnIndex("DISPENSER_JRT"));
					prodDat.DISPENSER_HRT = cur.getInt(cur.getColumnIndex("DISPENSER_HRT"));
					prodDat.DISPENSER_MULTIFOLD = cur.getInt(cur.getColumnIndex("DISPENSER_MULTIFOLD"));
					prodDat.DISPENSER_ROLL = cur.getInt(cur.getColumnIndex("DISPENSER_ROLL"));
					AlisData.add(prodDat);
				}

			}
			cur.close();
		}catch (Exception e){
			Log.w("Error ", e.getMessage());
		}

		return AlisData;
	}

	public ArrayList<Obj_CUSTMST> getDataListNotComplete(){
		ArrayList<Obj_CUSTMST> AlisData  = new ArrayList<Obj_CUSTMST>();

		Cursor cur;

		try{
			cur = AppConstant.myDb.rawQuery("SELECT A.*, B.TYPENAME from (SELECT A.*, B.FLAG_EC AS ISI_TRANSAKSI, B.TIMEOUT FROM TBL_CUSTMSTNEW A" +
					" LEFT JOIN (SELECT CUSTNO, FLAG_EC, TIMEOUT from TBL_CUSTCARD1" +
					" GROUP BY CUSTNO) B" +
					" ON A.CUSTNO = B.CUSTNO)A" +
					" LEFT JOIN TBL_TYPEOUT B" +
					" ON A.TYPEOUT = B.TYPEOUT" +
					" WHERE A.ISI_TRANSAKSI NOT NULL AND A.FLAGOUT = '2' " +
					" AND (A.TIMEOUT IS NULL OR A.TIMEOUT = '')", null);

			if (cur.getCount() > 0){;
//			c.moveToFirst();
				while (cur.moveToNext()){
					Obj_CUSTMST prodDat = new Obj_CUSTMST();
					prodDat.CUSTNO = cur.getString(cur.getColumnIndex("CUSTNO"));
					prodDat.CUSTNO = cur.getString(cur.getColumnIndex("CUSTNO"));
					prodDat.DATA01 = cur.getString(cur.getColumnIndex("DATA01"));
					prodDat.CUSTNAME = cur.getString(cur.getColumnIndex("CUSTNAME"));
					prodDat.CUSTADD1 = cur.getString(cur.getColumnIndex("CUSTADD1"));
					prodDat.CCONTACT = cur.getString(cur.getColumnIndex("CCONTACT"));
					prodDat.CPHONE1 = cur.getString(cur.getColumnIndex("CPHONE1"));
					prodDat.KELURAHAN = cur.getString(cur.getColumnIndex("KELURAHAN"));
					prodDat.KECAMATAN = cur.getString(cur.getColumnIndex("KECAMATAN"));
					prodDat.KABUPATEN = cur.getString(cur.getColumnIndex("KABUPATEN"));
					prodDat.PROVINSI = cur.getString(cur.getColumnIndex("PROVINSI"));
					prodDat.KODEPOS = cur.getString(cur.getColumnIndex("KODEPOS"));
					prodDat.ALAMAT = cur.getString(cur.getColumnIndex("ALAMAT"));
					prodDat.LATITUDE = cur.getString(cur.getColumnIndex("LATITUDE"));
					prodDat.LONGITUDE = cur.getString(cur.getColumnIndex("LONGITUDE"));
					prodDat.PHOTO_NAME = cur.getString(cur.getColumnIndex("PHOTO_NAME"));
					prodDat.PHOTO_NAME_SAMPING = cur.getString(cur.getColumnIndex("PHOTO_NAME_SAMPING"));
					prodDat.ISI_TRANSAKSI = cur.getString(cur.getColumnIndex("ISI_TRANSAKSI"));
					prodDat.TOTAL_KUNJUNGAN = cur.getInt(cur.getColumnIndex("TOTAL_KUNJUNGAN"));
					prodDat.LAST_TRANSACTION = cur.getString(cur.getColumnIndex("LAST_TRANSACTION"));
					prodDat.DATE_NEXT_VISIT = cur.getString(cur.getColumnIndex("DATE_NEXT_VISIT"));
					prodDat.NUMBER_OF_BRANCH = cur.getInt(cur.getColumnIndex("NUMBER_OF_BRANCH"));
					prodDat.TYPEOUT = cur.getString(cur.getColumnIndex("TYPEOUT"));
					prodDat.COMPANY_NAME = cur.getString(cur.getColumnIndex("COMPANY_NAME"));
					prodDat.FLAGOUT = cur.getString(cur.getColumnIndex("FLAGOUT"));
					prodDat.FLAGCUST = cur.getString(cur.getColumnIndex("FLAGCUST"));
					prodDat.KTP_ID = cur.getString(cur.getColumnIndex("KTP_ID"));
					prodDat.KTP_NAME = cur.getString(cur.getColumnIndex("KTP_NAME"));
					prodDat.KTP_ADDRESS = cur.getString(cur.getColumnIndex("KTP_ADDRESS"));
					prodDat.NPWP_ID = cur.getString(cur.getColumnIndex("NPWP_ID"));
					prodDat.NPWP_NAME = cur.getString(cur.getColumnIndex("NPWP_NAME"));
					prodDat.NPWP_ADDRESS = cur.getString(cur.getColumnIndex("NPWP_ADDRESS"));
					prodDat.PAYMENT_TYPE = cur.getString(cur.getColumnIndex("PAYMENT_TYPE"));
					prodDat.PERIODE_ORDER = cur.getString(cur.getColumnIndex("PERIODE_ORDER"));
					prodDat.STATUS_CONTRACT = cur.getString(cur.getColumnIndex("STATUS_CONTRACT"));
					prodDat.STARTDATE_CONTRACT = cur.getString(cur.getColumnIndex("STARTDATE_CONTRACT"));
					prodDat.ENDDATE_CONTRACT = cur.getString(cur.getColumnIndex("ENDDATE_CONTRACT"));
					prodDat.STATUS_DISPENSER = cur.getString(cur.getColumnIndex("STATUS_CONTRACT"));
					prodDat.DISPENSER_JRT = cur.getInt(cur.getColumnIndex("DISPENSER_JRT"));
					prodDat.DISPENSER_HRT = cur.getInt(cur.getColumnIndex("DISPENSER_HRT"));
					prodDat.DISPENSER_MULTIFOLD = cur.getInt(cur.getColumnIndex("DISPENSER_MULTIFOLD"));
					prodDat.DISPENSER_ROLL = cur.getInt(cur.getColumnIndex("DISPENSER_ROLL"));
					AlisData.add(prodDat);
				}

			}
			cur.close();
		}catch (Exception e){
			Log.w("Error ", e.getMessage());
		}

		return AlisData;
	}

	public ArrayList<Obj_CUSTMST> getDataListDashboard(){
		ArrayList<Obj_CUSTMST> AlisData  = new ArrayList<Obj_CUSTMST>();

		Cursor cur;

		try{
			cur = AppConstant.myDb.rawQuery("SELECT A.*,B.CUSTNAME, B.CUSTADD1, C.FLAG_KIRIM FROM " +
					" (SELECT CUSTNO, COUNT(CUSTNO) TOTAL_INV, SUM(SKU) SKU, " +
					" SUM(INVAMOUNT) INVAMOUNT, ORDERNO FROM TBL_TRX_H" +
					" GROUP BY CUSTNO) A" +
					" LEFT JOIN TBL_CUSTMSTNEW B " +
					" ON A.CUSTNO = B.CUSTNO " +
					" LEFT JOIN tbl_custcard C " +
					" ON A.CUSTNO = C.CUSTNO AND A.ORDERNO = C.DOCNO", null);


			if (cur.getCount() > 0){
//			c.moveToFirst();
				while (cur.moveToNext()){
					Obj_CUSTMST prodDat = new Obj_CUSTMST();
					prodDat.CUSTNO = cur.getString(cur.getColumnIndex("CUSTNO"));
					prodDat.ORDERNO = cur.getString(cur.getColumnIndex("ORDERNO"));
					prodDat.CUSTNAME = cur.getString(cur.getColumnIndex("CUSTNAME"));
					prodDat.CUSTADD1 = cur.getString(cur.getColumnIndex("CUSTADD1"));
					prodDat.INVAMOUNT = cur.getDouble(cur.getColumnIndex("INVAMOUNT"));
					prodDat.SKU = cur.getInt(cur.getColumnIndex("SKU"));
					prodDat.FLAG_KIRIM = cur.getString(cur.getColumnIndex("FLAG_KIRIM"));
					prodDat.TOTAL_INV = cur.getInt(cur.getColumnIndex("TOTAL_INV"));
					AlisData.add(prodDat);
				}

			}

			cur = AppConstant.myDb.rawQuery("SELECT A.CUSTNO,B.CUSTNAME, B.CUSTADD1 , '0' AS TOTAL_INV," +
					"'0' AS SKU, '0' AS INVAMOUNT, '' AS ORDERNO, 'N' AS FLAG_KIRIM FROM TBL_CUSTCARD1 A " +
					"LEFT JOIN TBL_CUSTMSTNEW B " +
					"ON A.CUSTNO = B.CUSTNO " +
					"WHERE A.FLAG_EC = 'N'", null);

			if (cur.getCount() > 0){
//			c.moveToFirst();
				while (cur.moveToNext()){
					Obj_CUSTMST prodDat = new Obj_CUSTMST();
					prodDat.CUSTNO = cur.getString(cur.getColumnIndex("CUSTNO"));
					prodDat.CUSTNAME = cur.getString(cur.getColumnIndex("CUSTNAME"));
					prodDat.CUSTADD1 = cur.getString(cur.getColumnIndex("CUSTADD1"));
					prodDat.INVAMOUNT = cur.getDouble(cur.getColumnIndex("INVAMOUNT"));
					prodDat.SKU = cur.getInt(cur.getColumnIndex("SKU"));
					prodDat.FLAG_KIRIM = cur.getString(cur.getColumnIndex("FLAG_KIRIM"));
					prodDat.TOTAL_INV = cur.getInt(cur.getColumnIndex("TOTAL_INV"));
					AlisData.add(prodDat);
				}

			}
			cur.close();
		}catch (Exception e){

		}

		return AlisData;
	}


	public ArrayList<Obj_CUSTMST> getDataListDashboardComplaint(){
		ArrayList<Obj_CUSTMST> AlisData  = new ArrayList<Obj_CUSTMST>();

		Cursor cur;

		try{
			cur = AppConstant.myDb.rawQuery("SELECT A.CUSTNO,B.CUSTNAME, B.CUSTADD1 , '0' AS TOTAL_INV," +
					"'0' AS SKU, '0' AS INVAMOUNT, '' AS ORDERNO, 'N' AS FLAG_KIRIM FROM TBL_COMPLAINT A " +
					"LEFT JOIN TBL_CUSTMSTNEW B " +
					"ON A.CUSTNO = B.CUSTNO " , null);

			if (cur.getCount() > 0){
//			c.moveToFirst();
				while (cur.moveToNext()){
					Obj_CUSTMST prodDat = new Obj_CUSTMST();
					prodDat.CUSTNO = cur.getString(cur.getColumnIndex("CUSTNO"));
					prodDat.CUSTNAME = cur.getString(cur.getColumnIndex("CUSTNAME"));
					prodDat.CUSTADD1 = cur.getString(cur.getColumnIndex("CUSTADD1"));
					prodDat.INVAMOUNT = cur.getDouble(cur.getColumnIndex("INVAMOUNT"));
					prodDat.SKU = cur.getInt(cur.getColumnIndex("SKU"));
					prodDat.FLAG_KIRIM = cur.getString(cur.getColumnIndex("FLAG_KIRIM"));
					prodDat.TOTAL_INV = cur.getInt(cur.getColumnIndex("TOTAL_INV"));
					AlisData.add(prodDat);
				}

			}
			cur.close();
		}catch (Exception e){

		}

		return AlisData;
	}

	public ArrayList<Obj_CUSTMST> getDataListDashboardMaintenance(){
		ArrayList<Obj_CUSTMST> AlisData  = new ArrayList<Obj_CUSTMST>();

		Cursor cur;

		try{
			cur = AppConstant.myDb.rawQuery("SELECT A.CUSTNO,B.CUSTNAME, B.CUSTADD1 , '0' AS TOTAL_INV," +
					"'0' AS SKU, '0' AS INVAMOUNT, '' AS ORDERNO, 'N' AS FLAG_KIRIM FROM TBL_MAINTENANCE A " +
					"LEFT JOIN TBL_CUSTMSTNEW B " +
					"ON A.CUSTNO = B.CUSTNO " , null);

			if (cur.getCount() > 0){
//			c.moveToFirst();
				while (cur.moveToNext()){
					Obj_CUSTMST prodDat = new Obj_CUSTMST();
					prodDat.CUSTNO = cur.getString(cur.getColumnIndex("CUSTNO"));
					prodDat.CUSTNAME = cur.getString(cur.getColumnIndex("CUSTNAME"));
					prodDat.CUSTADD1 = cur.getString(cur.getColumnIndex("CUSTADD1"));
					prodDat.INVAMOUNT = cur.getDouble(cur.getColumnIndex("INVAMOUNT"));
					prodDat.SKU = cur.getInt(cur.getColumnIndex("SKU"));
					prodDat.FLAG_KIRIM = cur.getString(cur.getColumnIndex("FLAG_KIRIM"));
					prodDat.TOTAL_INV = cur.getInt(cur.getColumnIndex("TOTAL_INV"));
					AlisData.add(prodDat);
				}

			}
			cur.close();
		}catch (Exception e){

		}

		return AlisData;
	}

	public ArrayList<Obj_CUSTMST> getDataListDashboardByCustno(String CUSTNO){
		ArrayList<Obj_CUSTMST> AlisData  = new ArrayList<Obj_CUSTMST>();

		Cursor cur;

		try{
			cur = AppConstant.myDb.rawQuery("Select A.*, B.CUSTNO, B.CUSTNAME, B.CUSTADD1, C.FLAG_KIRIM from tbl_trx_h A" +
							" LEFT JOIN TBL_CUSTMSTNEW B" +
							" ON A.CUSTNO = B.CUSTNO" +
							" LEFT JOIN tbl_custcard C" +
							" ON A.CUSTNO = C.CUSTNO AND A.ORDERNO = C.DOCNO" +
							" WHERE A.CUSTNO = '"+CUSTNO+"'", null);


			if (cur.getCount() > 0){
//			c.moveToFirst();
				while (cur.moveToNext()){
					Obj_CUSTMST prodDat = new Obj_CUSTMST();
					prodDat.CUSTNO = cur.getString(cur.getColumnIndex("CUSTNO"));
					prodDat.CUSTNAME = cur.getString(cur.getColumnIndex("CUSTNAME"));
					prodDat.CUSTADD1 = cur.getString(cur.getColumnIndex("CUSTADD1"));
					prodDat.INVAMOUNT = cur.getDouble(cur.getColumnIndex("INVAMOUNT"));
					prodDat.SKU = cur.getInt(cur.getColumnIndex("SKU"));
					prodDat.FLAG_KIRIM = cur.getString(cur.getColumnIndex("FLAG_KIRIM"));
                    prodDat.ORDERNO = cur.getString(cur.getColumnIndex("ORDERNO"));
                    AlisData.add(prodDat);
				}

			}
			cur.close();
		}catch (Exception e){

		}

		return AlisData;
	}


	public ArrayList<Obj_CUSTMST> getDataListKelurahan(){
		ArrayList<Obj_CUSTMST> AlisData  = new ArrayList<Obj_CUSTMST>();

		Cursor cur;

		try{
			cur = AppConstant.myDb.rawQuery("Select KELURAHAN from TBL_CUSTMSTNEW" +
					" GROUP BY KELURAHAN", null);


			if (cur.getCount() > 0){
//			c.moveToFirst();
				while (cur.moveToNext()){
					Obj_CUSTMST prodDat = new Obj_CUSTMST();
					prodDat.KELURAHAN = cur.getString(cur.getColumnIndex("KELURAHAN"));
					AlisData.add(prodDat);
				}

			}
			cur.close();
		}catch (Exception e){

		}

		return AlisData;
	}

	public void CreateData(Obj_CUSTMST dat){
        String strQuery = "";
        try {
			strQuery = "INSERT INTO TBL_CUSTMSTNEW(" +
					"CUSTNO," +
					"DATA01," +
					"CUSTNAME," +
					"CCONTACT," +
					"CUSTADD1," +
					"CPHONE1," +
					"TYPEOUT," +
					"COMPANY_NAME," +
					"KELURAHAN," +
					"KECAMATAN," +
					"KABUPATEN," +
					"PROVINSI," +
					"KODEPOS," +
					"ALAMAT," +
					"LATITUDE," +
					"LONGITUDE," +
					"TOTAL_KUNJUNGAN," +
					"LAST_TRANSACTION," +
					"FLAG_KIRIM," +
					"FLAGOUT," +
					"FLAGCUST," +
					"FLAG_PJP," +
					"PHOTO_NAME," +
					"PHOTO_NAME_SAMPING," +
					"DATE_NEXT_VISIT," +
					"FLAG_KIRIM, " +
					"KTP_ID , " +
					"KTP_NAME , "+
					"KTP_ADDRESS , "+
					"NPWP_ID , "+
					"NPWP_NAME , "+
					"NPWP_ADDRESS , "+
					"PAYMENT_TYPE , "+
					"PERIODE_ORDER , "+
					"STATUS_DISPENSER , "+
					"DISPENSER_JRT , "+
					"DISPENSER_HRT , "+
					"DISPENSER_MULTIFOLD , "+
					"DISPENSER_ROLL , "+
					"STATUS_CONTRACT , "+
					"STARTDATE_CONTRACT , "+
					"ENDDATE_CONTRACT,"+
					"NUMBER_OF_BRANCH" +
					")VALUES(" +
					"'"+dat.getCUSTNO()+"'," +
					"'"+dat.getDATA01()+"'," +
					"'"+dat.getCUSTNAME()+"'," +
					"'"+dat.getCCONTACT()+"'," +
                    "'"+dat.getCUSTADD1()+"'," +
					"'"+dat.getCPHONE1()+"'," +
					"'"+dat.getTYPEOUT()+"'," +
					"'"+dat.getCOMPANY_NAME()+"'," +
					"'"+dat.getKELURAHAN()+"'," +
					"'"+dat.getKECAMATAN()+"'," +
					"'"+dat.getKABUPATEN()+"'," +
					"'"+dat.getPROVINSI()+"'," +
					"'"+dat.getKODEPOS()+"'," +
					"'"+dat.getALAMAT()+"'," +
					"'"+dat.getLATITUDE()+"'," +
					"'"+dat.getLONGITUDE()+"'," +
					""+dat.getTOTAL_KUNJUNGAN()+"," +
					"'"+dat.getLAST_TRANSACTION()+"'," +
					"'"+dat.getFLAG_KIRIM()+"'," +
					"'"+dat.getFLAGOUT()+"'," +
					"'"+dat.getFLAGCUST()+"'," +
					"''," +  //Flag_PJP
					"'"+dat.getPHOTO_NAME()+"'," +
					"'"+dat.getPHOTO_NAME_SAMPING()+"'," +
					"'"+dat.getDATE_NEXT_VISIT()+"'," +
					"'Y'," +
					"'"+dat.getKTP_ID()+"', " +
					"'"+dat.getKTP_NAME()+"', "+
					"'"+dat.getKTP_ADDRESS()+"', "+
					"'"+dat.getNPWP_ID()+"', "+
					"'"+dat.getNPWP_NAME()+"', "+
					"'"+dat.getNPWP_ADDRESS()+"', "+
					"'"+dat.getPAYMENT_TYPE()+"', "+
					"'"+dat.getPERIODE_ORDER()+"', "+
					"'"+dat.getSTATUS_DISPENSER()+"', "+
					"'"+dat.getDISPENSER_JRT()+"', "+
					"'"+dat.getDISPENSER_HRT()+"', "+
					"'"+dat.getDISPENSER_MULTIFOLD()+"', "+
					"'"+dat.getDISPENSER_JRT()+"', "+
					"'"+dat.getSTATUS_CONTRACT()+"', "+
					"'"+dat.getSTARTDATE_CONTRACT()+"', "+
					"'"+dat.getENDDATE_CONTRACT()+"', "+
					""+dat.getNUMBER_OF_BRANCH()+"" +
					")"
			;
			
			AppConstant.myDb.execSQL(strQuery);
		} catch (Exception e) {
			// TODO: handle exception
            Log.w("Insert Outlet ",strQuery + " " + e.getMessage() );

		}
	}

	public void UpdateDataFlagKirim(String CUSTNO){
		String sSql = "";
		try {
			sSql = "UPDATE TBL_CUSTMSTNEW " +
					" SET FLAG_KIRIM = 'Y' "+
					" WHERE CUSTNO = '"+CUSTNO+"'";
			AppConstant.myDb.execSQL(sSql);


		} catch (Exception e) {
			// TODO: handle exception
			Log.i("", e.getMessage());
		}
	}

	public void UpdateDataReg(Obj_CUSTMST dat){
		String sSql = "";
		try {
			sSql = "UPDATE TBL_CUSTMSTNEW SET" +
					" FLAGOUT = '2', " + //Register Outlet
					" FLAG_KIRIM = 'N', " +
					" KTP_ID = '"+dat.getKTP_ID()+"', " +
					" KTP_NAME = '"+dat.getKTP_NAME()+"', "+
					" KTP_ADDRESS = '"+dat.getKTP_ADDRESS()+"', "+
					" NPWP_ID = '"+dat.getNPWP_ID()+"', "+
					" NPWP_NAME = '"+dat.getNPWP_NAME()+"', "+
					" NPWP_ADDRESS = '"+dat.getNPWP_ADDRESS()+"', "+
					" PAYMENT_TYPE = '"+dat.getPAYMENT_TYPE()+"', "+
					" PERIODE_ORDER = '"+dat.getPERIODE_ORDER()+"', "+
					" STATUS_DISPENSER= '"+dat.getSTATUS_DISPENSER()+"', "+
					" DISPENSER_JRT = '"+dat.getDISPENSER_JRT()+"', "+
					" DISPENSER_HRT = '"+dat.getDISPENSER_HRT()+"', "+
					" DISPENSER_MULTIFOLD = '"+dat.getDISPENSER_MULTIFOLD()+"', "+
					" DISPENSER_ROLL = '"+dat.getDISPENSER_ROLL()+"', "+
					" STATUS_CONTRACT = '"+dat.getSTATUS_CONTRACT()+"', "+
					" STARTDATE_CONTRACT = '"+dat.getSTARTDATE_CONTRACT()+"', "+
					" ENDDATE_CONTRACT = '"+dat.getENDDATE_CONTRACT()+"' "+
					" WHERE CUSTNO = '"+dat.getCUSTNO()+"'";
			AppConstant.myDb.execSQL(sSql);
			Log.w("STATUS_OUTLET", sSql);
		} catch (Exception e) {
			// TODO: handle exception
			Log.w("UpdateReg","Error : " + e.getMessage());
		}
	}

	public void UpdateDataRegExisting(Obj_CUSTMST dat){
		String sSql = "";
		try {
			sSql = "UPDATE TBL_CUSTMSTNEW SET" +
					" FLAGOUT = '2', " + //Register Outlet
					" FLAG_KIRIM = 'N', " +
					" LATITUDE = '"+dat.getLATITUDE()+"', " +
					" LONGITUDE = '"+dat.getLONGITUDE()+"', " +
					" KTP_ID = '"+dat.getKTP_ID()+"', " +
					" KTP_ID = '"+dat.getKTP_ID()+"', " +
					" KTP_NAME = '"+dat.getKTP_NAME()+"', "+
					" KTP_ADDRESS = '"+dat.getKTP_ADDRESS()+"', "+
					" NPWP_ID = '"+dat.getNPWP_ID()+"', "+
					" NPWP_NAME = '"+dat.getNPWP_NAME()+"', "+
					" NPWP_ADDRESS = '"+dat.getNPWP_ADDRESS()+"', "+
					" PAYMENT_TYPE = '"+dat.getPAYMENT_TYPE()+"', "+
					" PERIODE_ORDER = '"+dat.getPERIODE_ORDER()+"', "+
					" STATUS_CONTRACT = '"+dat.getSTATUS_CONTRACT()+"', "+
					" STARTDATE_CONTRACT = '"+dat.getSTARTDATE_CONTRACT()+"', "+
					" COMPANY_NAME = '"+dat.getCOMPANY_NAME()+"', "+
					" NUMBER_OF_BRANCH = '"+dat.getNUMBER_OF_BRANCH()+"', "+
					" ENDDATE_CONTRACT = '"+dat.getENDDATE_CONTRACT()+"' "+
					" WHERE CUSTNO = '"+dat.getCUSTNO()+"'";
			AppConstant.myDb.execSQL(sSql);

		} catch (Exception e) {
			// TODO: handle exception
			Log.w("UpdateReg","Error : " + e.getMessage());
		}
	}


	public void UpdateDataFlagKirim(){
		String sSql = "";
		try {
			sSql = "UPDATE TBL_CUSTMSTNEW " +
					" SET FLAG_KIRIM = 'Y' ";
			AppConstant.myDb.execSQL(sSql);


		} catch (Exception e) {
			// TODO: handle exception
			Log.i("", e.getMessage());
		}
	}

	public void DeleteData(String strCustNo){
		String sSql = "DELETE FROM TBL_CUSTMSTNEW ";
		sSql = sSql + " WHERE CUSTNO = '" + strCustNo + "'";
		try {
			AppConstant.myDb.execSQL(sSql);
		} catch (Exception e) {
			// TODO: handle exception
			Log.i("", e.getMessage());
		}
	}

	public void DeleteData(){
		String sSql = "";
		try {
			sSql = "DELETE FROM TBL_CUSTMSTNEW  ";
			AppConstant.myDb.execSQL(sSql);


		} catch (Exception e) {
			// TODO: handle exception
			Log.i("", e.getMessage());
		}
	}

	public void UpdateFlagPJP(){
		String sSql = "";
		try {
			sSql = "UPDATE TBL_CUSTMSTNEW SET FLAG_PJP = ''";
			AppConstant.myDb.execSQL(sSql);


		} catch (Exception e) {
			// TODO: handle exception
			Log.i("", e.getMessage());
		}
	}

	public void UpdateFlagPJP(String CUSTNO){
		String sSql = "";
		try {
			sSql = "UPDATE TBL_CUSTMSTNEW SET FLAG_PJP = 'Y'" +
					" WHERE CUSTNO = '"+CUSTNO+"'";
			AppConstant.myDb.execSQL(sSql);


		} catch (Exception e) {
			// TODO: handle exception
			Log.i("", e.getMessage());
		}
	}

	public void UpdatePhoto(String CUSTNO, String PHOTO_NAME){
		String sSql = "";
		try {
			sSql = "UPDATE TBL_CUSTMSTNEW SET PHOTO_NAME = '"+PHOTO_NAME+"'" +
					" WHERE CUSTNO = '"+CUSTNO+"'";
			AppConstant.myDb.execSQL(sSql);


		} catch (Exception e) {
			// TODO: handle exception
			Log.i("", e.getMessage());
		}
	}

	public double TotalOut(){
		String sSql = "Select COUNT(*) as TotInv FROM TBL_CUSTMSTNEW";

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

	public double TotalOutPreReg(){
		String sSql = "Select COUNT(*) as TotInv FROM TBL_CUSTMSTNEW" +
				" WHERE FLAGOUT = '1'";

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

	public double TotalOutRegister(){
		String sSql = "Select COUNT(*) as TotInv FROM TBL_CUSTMSTNEW" +
				" WHERE FLAGOUT <> '1'";

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

	public double TotalOutPJP(){
		String sSql = "Select COUNT(*) as TotInv FROM TBL_CUSTMSTNEW" +
				" WHERE FLAG_PJP = 'Y'";

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

	public double TotalOutNonPJP(){
		String sSql = "Select COUNT(*) as TotInv FROM TBL_CUSTMSTNEW" +
				" WHERE FLAG_PJP != 'Y'";

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
}
