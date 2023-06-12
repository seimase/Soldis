package com.soldis.yourthaitea.entity;

import android.database.Cursor;
import android.util.Log;

import com.soldis.yourthaitea.AppConstant;

import java.util.ArrayList;

public class Obj_CUSTMST_GEN {
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
	private String PHOTO_NAME = "" ;
	private String PHOTO_NAME_SAMPING = "" ;
	private String FLAG_KIRIM = "";
	private String TGL = "";
	private String SLSNO = "";
	private String KODECABANG = "";


	public Obj_CUSTMST_GEN(){}

	public String getSLSNO() {
		return SLSNO;
	}

	public void setSLSNO(String SLSNO) {
		this.SLSNO = SLSNO;
	}

	public String getKODECABANG() {
		return KODECABANG;
	}

	public void setKODECABANG(String KODECABANG) {
		this.KODECABANG = KODECABANG;
	}

	public String getTGL() {
		return TGL;
	}

	public void setTGL(String TGL) {
		this.TGL = TGL;
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

	public Obj_CUSTMST_GEN getData(String sCustNo){

		try{
			Cursor cur = AppConstant.myDb.rawQuery("SELECT * FROM TBL_CUSTMSTNEW_GEN WHERE CUSTNO = '"+sCustNo+"'", null);
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
				this.TGL = cur.getString(cur.getColumnIndex("TGL"));
				this.SLSNO = cur.getString(cur.getColumnIndex("SLSNO"));
				this.KODECABANG = cur.getString(cur.getColumnIndex("KODECABANG"));
				this.PHOTO_NAME_SAMPING = cur.getString(cur.getColumnIndex("PHOTO_NAME_SAMPING"));

			}
			cur.close();

		}catch (Exception e){

		}

		return this;
	}

	public Obj_CUSTMST_GEN getDataGPS(){

		try{
			Cursor cur = AppConstant.myDb.rawQuery("SELECT * FROM TBL_CUSTMSTNEW_GEN" +
					" WHERE (ALAMAT IS NULL OR ALAMAT = '' OR ALAMAT = 'null') AND LATITUDE != '0.0'" , null);
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
				//this.TGL = cur.getString(cur.getColumnIndex("TGL"));
				this.SLSNO = cur.getString(cur.getColumnIndex("SLSNO"));
				this.KODECABANG = cur.getString(cur.getColumnIndex("KODECABANG"));
				this.PHOTO_NAME_SAMPING = cur.getString(cur.getColumnIndex("PHOTO_NAME_SAMPING"));

			}
			cur.close();

		}catch (Exception e){

		}

		return this;
	}

	public ArrayList<Obj_CUSTMST_GEN> getDataList(){
		ArrayList<Obj_CUSTMST_GEN> AlisData  = new ArrayList<Obj_CUSTMST_GEN>();
		
		Cursor cur;

		try{
			cur = AppConstant.myDb.rawQuery("SELECT * FROM TBL_CUSTMSTNEW_GEN " +
					" WHERE (ALAMAT IS NULL OR ALAMAT = '' OR ALAMAT = 'null') AND LATITUDE != '0.0'" +
					" LIMIT 100", null);


			if (cur.getCount() > 0){
//			c.moveToFirst();
				while (cur.moveToNext()){
					Obj_CUSTMST_GEN prodDat = new Obj_CUSTMST_GEN();
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
					prodDat.TGL = cur.getString(cur.getColumnIndex("TGL"));
					prodDat.SLSNO = cur.getString(cur.getColumnIndex("SLSNO"));
					prodDat.KODECABANG = cur.getString(cur.getColumnIndex("KODECABANG"));
					AlisData.add(prodDat);
				}

			}
			cur.close();
		}catch (Exception e){

		}

		return AlisData;
	}


	public ArrayList<Obj_CUSTMST_GEN> getDataListALL(){
		ArrayList<Obj_CUSTMST_GEN> AlisData  = new ArrayList<Obj_CUSTMST_GEN>();

		Cursor cur;

		try{
			cur = AppConstant.myDb.rawQuery("SELECT * FROM TBL_CUSTMSTNEW_GEN ", null);


			if (cur.getCount() > 0){
//			c.moveToFirst();
				while (cur.moveToNext()){
					Obj_CUSTMST_GEN prodDat = new Obj_CUSTMST_GEN();
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
					prodDat.TGL = cur.getString(cur.getColumnIndex("TGL"));
					prodDat.SLSNO = cur.getString(cur.getColumnIndex("SLSNO"));
					prodDat.KODECABANG = cur.getString(cur.getColumnIndex("KODECABANG"));
					AlisData.add(prodDat);
				}

			}
			cur.close();
		}catch (Exception e){

		}

		return AlisData;
	}


	public void CreateData(Obj_CUSTMST_GEN dat){

		try {
			String strQuery = "INSERT INTO TBL_CUSTMSTNEW_GEN (" +
					"CUSTNO," +
					"TGL," +
					"SLSNO," +
					"KODECABANG," +
					"LATITUDE," +
					"LONGITUDE" +
					")VALUES(" +
					"'"+dat.getCUSTNO()+"'," +
					"'"+dat.getTGL()+"'," +
					"'"+dat.getSLSNO()+"'," +
					"'"+dat.getKODECABANG()+"'," +
                    "'"+dat.getLATITUDE()+"'," +
					"'"+dat.getLONGITUDE()+"'" +
					")"
			;
			Log.w("GenGPS",  strQuery );

			AppConstant.myDb.execSQL(strQuery);
		} catch (Exception e) {
			// TODO: handle exception

		}
	}

	public void UpdateData(Obj_CUSTMST_GEN dat){

		try {
			String strQuery = "UPDATE TBL_CUSTMSTNEW_GEN SET" +
					" KELURAHAN = '"+dat.getKELURAHAN().replace("'","")+"'," +
					" KECAMATAN = '"+dat.getKECAMATAN().replace("'","")+"'," +
					" KABUPATEN = '"+dat.getKABUPATEN().replace("'","")+"'," +
					" PROVINSI = '"+dat.getPROVINSI().replace("'","")+"'," +
					" KODEPOS = '"+dat.getKODEPOS()+"'," +
					" ALAMAT = '"+dat.getALAMAT().replace("'","")+"'" +
					" WHERE CUSTNO = '"+dat.getCUSTNO()+"'" +
					" AND KODECABANG = '"+dat.getKODECABANG()+"'"
					;

			Log.w("GenGPS",  strQuery );
			AppConstant.myDb.execSQL(strQuery);
		} catch (Exception e) {
			// TODO: handle exception

		}
	}

	public void DeleteData(){
		String sSql = "";
		try {
			sSql = "DELETE FROM TBL_CUSTMSTNEW_GEN ";
			AppConstant.myDb.execSQL(sSql);


		} catch (Exception e) {
			// TODO: handle exception
			Log.i("", e.getMessage());
		}
	}

	public long TotalData(){
		String sSql = "Select count(*) TotRow FROM TBL_CUSTMSTNEW_GEN";

		long lTotRow = 0;
		try {
			Cursor c = AppConstant.myDb.rawQuery(sSql, null);
			if (c.moveToFirst())
				lTotRow = c.getLong(c.getColumnIndex("TotRow"));
			c.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return lTotRow;
	}

	public long TotalDataNotGPS(){
		String sSql = "Select count(*) TotRow FROM TBL_CUSTMSTNEW_GEN " +
				" WHERE ALAMAT IS NULL OR ALAMAT = '' OR ALAMAT = 'null'";

		long lTotRow = 0;
		try {
			Cursor c = AppConstant.myDb.rawQuery(sSql, null);
			if (c.moveToFirst())
				lTotRow = c.getLong(c.getColumnIndex("TotRow"));
			c.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return lTotRow;
	}
}
