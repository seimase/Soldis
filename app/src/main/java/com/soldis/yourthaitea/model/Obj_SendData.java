package com.soldis.yourthaitea.model;

import android.util.Log;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.BuildConfig;
import com.soldis.yourthaitea.entity.Obj_COLLECTION;
import com.soldis.yourthaitea.entity.Obj_COMPLAINT;
import com.soldis.yourthaitea.entity.Obj_CUSTCARD;
import com.soldis.yourthaitea.entity.Obj_CUSTCARD1;
import com.soldis.yourthaitea.entity.Obj_CUSTMST;
import com.soldis.yourthaitea.entity.Obj_CUSTMST_GEN;
import com.soldis.yourthaitea.entity.Obj_DISPENSER;
import com.soldis.yourthaitea.entity.Obj_KAS;
import com.soldis.yourthaitea.entity.Obj_MAINTENANCE;
import com.soldis.yourthaitea.entity.Obj_MOTORIS;
import com.soldis.yourthaitea.entity.Obj_STOCK;
import com.soldis.yourthaitea.entity.Obj_STOCK_MAPPING;
import com.soldis.yourthaitea.entity.Obj_TRX_D;
import com.soldis.yourthaitea.entity.Obj_TRX_H;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by setia.n on 12/15/2016.
 */

public class Obj_SendData {
    public boolean error ;
    public String message ;
    public String docno ;
    public String tmbck ;
    public String tglback ;

    static JSONObject jsonObject = new JSONObject();

    public String ORDERNO = "";
    public JSONObject sendData_StartVisit() throws JSONException {
        String SLSNO = "",
                KODECABANG = "";
        Tbl_Karyawan tblMotoris = AppController.getInstance().getSessionManager().getUserProfile();
        if (tblMotoris != null){
            for(Tbl_Karyawan.Datum dat : tblMotoris.data){
                SLSNO = dat.SLSNO;
                KODECABANG = dat.MITRA_ID;
            }
        }


        jsonObject.put("SLSNO", SLSNO);
        jsonObject.put("MITRA_ID", KODECABANG);
        jsonObject.put("APP_VERSION", BuildConfig.VERSION_NAME);
        UpStock();
        return jsonObject;
    }

    public JSONObject sendData(String ORDERNO) throws JSONException {
        this.ORDERNO = ORDERNO;

        String SLSNO = "",
                TGL_TRX = "",
                KODECABANG = "";
        Tbl_Karyawan tblMotoris = AppController.getInstance().getSessionManager().getUserProfile();
        if (tblMotoris != null){
            for(Tbl_Karyawan.Datum dat : tblMotoris.data){
                SLSNO = dat.SLSNO;
                KODECABANG = dat.MITRA_ID;
            }
        }

        Obj_MOTORIS objMotoris = new Obj_MOTORIS() ;
        objMotoris = objMotoris.getData(SLSNO);

        if (objMotoris.getSLSNO() != null) TGL_TRX = objMotoris.getTRX_DATE();

        jsonObject.put("SLSNO", SLSNO);
        jsonObject.put("DOCNO", ORDERNO);
        jsonObject.put("TGL_TRX", TGL_TRX);
        jsonObject.put("MITRA_ID", KODECABANG);
        UpCustCard1();
        UpCustCard(ORDERNO);
        UpTrx_H(ORDERNO);

        return jsonObject;
    }

    public JSONObject sendDataAll(String TMBCK) throws JSONException {
        String SLSNO = "",
                TGL_TRX = "",
                KODECABANG = "";
        Tbl_Karyawan tblMotoris = AppController.getInstance().getSessionManager().getUserProfile();
        if (tblMotoris != null){
            for(Tbl_Karyawan.Datum dat : tblMotoris.data){
                SLSNO = dat.SLSNO;
                KODECABANG = dat.MITRA_ID;
            }
        }

        Obj_MOTORIS objMotoris = new Obj_MOTORIS() ;
        objMotoris = objMotoris.getData(SLSNO);

        if (objMotoris.getSLSNO() != null) TGL_TRX = objMotoris.getTRX_DATE();

        jsonObject.put("SLSNO", SLSNO);
        jsonObject.put("TIMEBCK", TMBCK);
        jsonObject.put("TGL_TRX", TGL_TRX);
        jsonObject.put("MITRA_ID", KODECABANG);

        UpCustMst();
        UpCustCard1();
        UpCustCard();
        UpStockNew();
        UpKas(TGL_TRX);
        UpTrx_H();
        UpTrx_D();
        UpCollection();
        UpComplaint();
        UpMaintenance();
        UpDispenser();
        return jsonObject;
    }

    public JSONObject sendDataCustNotGPS() throws JSONException {
        String SLSNO = "",
                TGL_TRX = "",
                KODECABANG = "";
        Tbl_Karyawan tblMotoris = AppController.getInstance().getSessionManager().getUserProfile();
        if (tblMotoris != null){
            for(Tbl_Karyawan.Datum dat : tblMotoris.data){
                SLSNO = dat.SLSNO;
                KODECABANG = dat.MITRA_ID;
            }
        }

        Obj_MOTORIS objMotoris = new Obj_MOTORIS() ;
        objMotoris = objMotoris.getData(SLSNO);

        if (objMotoris.getSLSNO() != null) TGL_TRX = objMotoris.getTRX_DATE();

        jsonObject.put("SLSNO", SLSNO);
        jsonObject.put("MITRA_ID", KODECABANG);
        UpCustMstNotGPS();
        return jsonObject;
    }

    static void UpStock(){
        Obj_STOCK objStock = new Obj_STOCK();
        JSONObject jsonObjectStock;
        JSONArray jsonArrayStock = new JSONArray();
        try{
            for (Obj_STOCK dat : objStock.getDataList()){
                try{
                    jsonObjectStock = new JSONObject();
                    jsonObjectStock.put("PCODE", dat.getPCODE());

                    jsonObjectStock.put("QTY_B", "0");
                    jsonObjectStock.put("QTY_T", "0");
                    jsonObjectStock.put("QTY_K", dat.getSTOCK());

                    //QTY TRX---------------------------------------------------------------------
                    String sXQTY = AppController.getInstance().convertQtyToString(
                            (int)dat.getCONVUNIT3(),
                            (int)dat.getCONVUNIT2(),
                            dat.getSTOCK());
                    if (dat.getUNIT1() != null && dat.getUNIT2() != null ){
                        if (dat.getUNIT1().toUpperCase().equals(dat.getUNIT2().toUpperCase())){
                            sXQTY = AppController.getInstance().convertQtyToString(
                                    (int)dat.getCONVUNIT2(),
                                    (int)dat.getCONVUNIT2(),
                                    dat.getSTOCK());
                        }
                    }

                    if (!sXQTY.equals("0/0/0")){
                        String[] lstQty = sXQTY.split("\\/");

                        jsonObjectStock.put("QTY_B", lstQty[0].toString());
                        jsonObjectStock.put("QTY_T", lstQty[1].toString());
                        jsonObjectStock.put("QTY_K", lstQty[2].toString());
                    }
                    jsonObjectStock.put("QTY_PCS", dat.getSTOCK());
                    jsonArrayStock.put(jsonObjectStock);
                }catch (Exception e){

                }
            }

            jsonObject.put("stock", jsonArrayStock);
        }catch (Exception e){

        }
    }

    static void UpStockNew(){
        Obj_STOCK objStock = new Obj_STOCK();
        JSONObject jsonObjectStock;
        JSONArray jsonArrayStock = new JSONArray();
        try{
            for (Obj_STOCK dat : objStock.getDataListUpload()){
                try{
                    jsonObjectStock = new JSONObject();
                    jsonObjectStock.put("DOCNO", dat.getDOCNO());
                    jsonObjectStock.put("TGL_TRX", dat.getTGL_TRX());
                    jsonObjectStock.put("PCODE", dat.getPCODE());
                    jsonObjectStock.put("FLAG_IN", dat.getFLAG_IN());
                    jsonObjectStock.put("TIMEIN", dat.getTIMEIN());
                    jsonObjectStock.put("QTY_B", "0");
                    jsonObjectStock.put("QTY_T", "0");
                    jsonObjectStock.put("QTY_K", dat.getSTOCK());
                    jsonObjectStock.put("QTY", dat.getSTOCK());
                    jsonArrayStock.put(jsonObjectStock);
                }catch (Exception e){

                }
            }

            Obj_STOCK_MAPPING  objStockMapping = new Obj_STOCK_MAPPING();
            for (Obj_STOCK_MAPPING dat : objStockMapping.getDataListStockSales()){
                try{
                    jsonObjectStock = new JSONObject();
                    jsonObjectStock.put("DOCNO", "SALES");
                    jsonObjectStock.put("TGL_TRX", AppConstant.strTglTrx);
                    jsonObjectStock.put("PCODE", dat.getPCODE());
                    jsonObjectStock.put("FLAG_IN", "S");
                    jsonObjectStock.put("TIMEIN", AppController.getInstance().getTime());
                    jsonObjectStock.put("QTY_B", "0");
                    jsonObjectStock.put("QTY_T", "0");
                    jsonObjectStock.put("QTY_K", dat.getQTY());
                    jsonObjectStock.put("QTY", dat.getQTY());
                    jsonArrayStock.put(jsonObjectStock);
                }catch (Exception e){

                }
            }

            jsonObject.put("stock", jsonArrayStock);
        }catch (Exception e){

        }
    }

    static void UpKas(String TGL_TRX){
        Obj_KAS objKas = new Obj_KAS();
        JSONObject jsonObjectStock;
        JSONArray jsonArrayStock = new JSONArray();
        try{
            for (Obj_KAS dat : objKas.getDataList()){
                try{
                    jsonObjectStock = new JSONObject();
                    jsonObjectStock.put("TGL_TRX", TGL_TRX);
                    jsonObjectStock.put("DOCNO", dat.getDOCNO());
                    jsonObjectStock.put("REMARK", dat.getREMARK());
                    jsonObjectStock.put("TYPE_KAS", dat.getTYPE_KAS());
                    jsonObjectStock.put("TIMEIN", dat.getTIMEIN());
                    jsonObjectStock.put("AMOUNT", dat.getAMOUNT());
                    jsonArrayStock.put(jsonObjectStock);
                }catch (Exception e){

                }
            }

            jsonObject.put("kas", jsonArrayStock);
        }catch (Exception e){

        }
    }


    static void UpCustMst(){

        JSONObject jsonObjectCC;
        JSONArray jsonArrayCC = new JSONArray();
        Obj_CUSTMST objCustmst = new Obj_CUSTMST();
        ArrayList<Obj_CUSTMST> objCustmsts = new ArrayList<>();

        try{
            objCustmsts = objCustmst.getDataListFlagKirim("N");
            for (Obj_CUSTMST dat : objCustmsts) {
                try {
                    jsonObjectCC = new JSONObject();
                    jsonObjectCC.put("CUSTNO", dat.getCUSTNO());
                    jsonObjectCC.put("CUSTNAME", ((dat.getCUSTNAME() == null) ? "" : dat.getCUSTNAME()));
                    jsonObjectCC.put("CONTACT", ((dat.getCCONTACT() == null) ? "" : dat.getCCONTACT()));
                    jsonObjectCC.put("ADDRESS", ((dat.getCUSTADD1() == null) ? "" : dat.getCUSTADD1()));
                    jsonObjectCC.put("PHONE", ((dat.getCPHONE1() == null) ? "" : dat.getCPHONE1()));
                    jsonObjectCC.put("KELURAHAN", ((dat.getKELURAHAN() == null) ? "" : dat.getKELURAHAN()));
                    jsonObjectCC.put("PHOTO", ((dat.getPHOTO_NAME() == null) ? "" : dat.getPHOTO_NAME()));
                    jsonObjectCC.put("PHOTO_SAMPING", ((dat.getPHOTO_NAME_SAMPING() == null) ? "" : dat.getPHOTO_NAME_SAMPING()));
                    jsonObjectCC.put("LATITUDE", ((dat.getLATITUDE() == null) ? "" : dat.getLATITUDE()));
                    jsonObjectCC.put("LONGITUDE", ((dat.getLONGITUDE() == null) ? "" : dat.getLONGITUDE()));
                    jsonObjectCC.put("DATE_NEXT_VISIT", ((dat.getDATE_NEXT_VISIT() == null) ? "" : dat.getDATE_NEXT_VISIT()));
                    jsonObjectCC.put("NUMBER_OF_BRANCH", dat.getNUMBER_OF_BRANCH());

                    jsonObjectCC.put("COMPANY_NAME", ((dat.getCOMPANY_NAME() == null) ? "" : dat.getCOMPANY_NAME()));
                    jsonObjectCC.put("TYPEOUT", ((dat.getTYPEOUT() == null) ? "" : dat.getTYPEOUT()));
                    jsonObjectCC.put("FLAGOUT", ((dat.getFLAGOUT() == null) ? "" : dat.getFLAGOUT()));
                    jsonObjectCC.put("FLAGCUST", ((dat.getFLAGCUST() == null) ? "" : dat.getFLAGCUST()));
                    jsonObjectCC.put("KTP_ID", ((dat.getKTP_ID() == null) ? "" : dat.getKTP_ID()));
                    jsonObjectCC.put("KTP_NAME", ((dat.getKTP_NAME() == null) ? "" : dat.getKTP_NAME()));
                    jsonObjectCC.put("KTP_ADDRESS", ((dat.getKTP_ADDRESS() == null) ? "" : dat.getKTP_ADDRESS()));
                    jsonObjectCC.put("NPWP_ID", ((dat.getNPWP_ID() == null) ? "" : dat.getNPWP_ID()));
                    jsonObjectCC.put("NPWP_NAME", ((dat.getNPWP_NAME() == null) ? "" : dat.getNPWP_NAME()));
                    jsonObjectCC.put("NPWP_ADDRESS", ((dat.getNPWP_ADDRESS() == null) ? "" : dat.getNPWP_ADDRESS()));
                    jsonObjectCC.put("PAYMENT_TYPE", ((dat.getPAYMENT_TYPE() == null) ? "" : dat.getPAYMENT_TYPE()));
                    jsonObjectCC.put("PERIODE_ORDER", ((dat.getPERIODE_ORDER() == null) ? "" : dat.getPERIODE_ORDER()));
                    jsonObjectCC.put("STATUS_CONTRACT", ((dat.getSTATUS_CONTRACT() == null) ? "" : dat.getSTATUS_CONTRACT()));
                    jsonObjectCC.put("STARTDATE_CONTRACT", ((dat.getSTARTDATE_CONTRACT() == null) ? "" : dat.getSTARTDATE_CONTRACT()));
                    jsonObjectCC.put("ENDDATE_CONTRACT", ((dat.getENDDATE_CONTRACT() == null) ? "" : dat.getENDDATE_CONTRACT()));

                    jsonObjectCC.put("STATUS_DISPENSER", ((dat.getSTATUS_DISPENSER() == null) ? "" : dat.getSTATUS_DISPENSER()));
                    jsonObjectCC.put("DISPENSER_JRT", dat.getDISPENSER_JRT());
                    jsonObjectCC.put("DISPENSER_HRT", dat.getDISPENSER_HRT());
                    jsonObjectCC.put("DISPENSER_MULTIFOLD", dat.getDISPENSER_MULTIFOLD());
                    jsonObjectCC.put("DISPENSER_ROLL", dat.getDISPENSER_ROLL());
                    jsonArrayCC.put(jsonObjectCC);

                } catch (Exception e) {

                }
            }
            jsonObject.put("custmst", jsonArrayCC);
        }catch (Exception e){

        }
    }

    static void UpCustCard(){
        Obj_CUSTCARD objCustcard = new Obj_CUSTCARD();

        JSONObject jsonObjectCC;
        JSONArray jsonArrayCC = new JSONArray();
        ArrayList<Obj_CUSTCARD> objCustcards = new ArrayList<>();

        try{
            objCustcards = objCustcard.getDataList(true);
            for(Obj_CUSTCARD dat : objCustcards){
                try{
                    jsonObjectCC = new JSONObject();
                    jsonObjectCC.put("DOCNO", dat.getDOCNO());
                    jsonObjectCC.put("CUSTNO", dat.getCUSTNO());
                    jsonObjectCC.put("TIMEIN", ((dat.getTIMEIN() == null) ? "" : dat.getTIMEIN()));
                    jsonObjectCC.put("TIMEOUT", ((dat.getTIMEOUT() == null) ? "" : dat.getTIMEOUT()));
                    jsonObjectCC.put("LATITUDE", ((dat.getLATITUDE() == null) ? "" : dat.getLATITUDE()));
                    jsonObjectCC.put("LONGITUDE", ((dat.getLONGITUDE() == null) ? "" : dat.getLONGITUDE()));
                    jsonArrayCC.put(jsonObjectCC);

                    //UpTrx_H(dat.getDOCNO());
                }catch (Exception e){

                }
            }
            if (objCustcards.size() == 0){
                jsonObject.put("trx_h", jsonArrayCC);
                jsonObject.put("trx_d", jsonArrayCC);
            }
            jsonObject.put("custcard", jsonArrayCC);
        }catch (Exception e){

        }
    }



    static void UpCustCard(String CUSTNO){
        Obj_CUSTCARD objCustcard = new Obj_CUSTCARD();

        JSONObject jsonObjectCC;
        JSONArray jsonArrayCC = new JSONArray();

        try{
            for(Obj_CUSTCARD dat : objCustcard.getDataList(CUSTNO)){
                try{
                    jsonObjectCC = new JSONObject();
                    jsonObjectCC.put("DOCNO", dat.getDOCNO());
                    jsonObjectCC.put("CUSTNO", dat.getCUSTNO());
                    jsonObjectCC.put("TIMEIN", ((dat.getTIMEIN() == null) ? "" : dat.getTIMEIN()));
                    jsonObjectCC.put("TIMEOUT", ((dat.getTIMEOUT() == null) ? "" : dat.getTIMEOUT()));
                    jsonObjectCC.put("LATITUDE", ((dat.getLATITUDE() == null) ? "" : dat.getLATITUDE()));
                    jsonObjectCC.put("LONGITUDE", ((dat.getLONGITUDE() == null) ? "" : dat.getLONGITUDE()));
                    jsonArrayCC.put(jsonObjectCC);


                }catch (Exception e){

                }
            }

            jsonObject.put("custcard", jsonArrayCC);
        }catch (Exception e){

        }
    }

    static void UpCustCard1(){
        Obj_CUSTCARD1 objCustcard1 = new Obj_CUSTCARD1();
        JSONObject jsonObjectCC;
        JSONArray jsonArrayCC = new JSONArray();
        ArrayList<Obj_CUSTCARD1> objCustcard1s = new ArrayList<>();

        try{
            objCustcard1s = objCustcard1.getDataList();
            for(Obj_CUSTCARD1 dat : objCustcard1s){
                try{
                    jsonObjectCC = new JSONObject();
                    jsonObjectCC.put("FLAG_EC", ((dat.getFLAG_EC() == null) ? "" : dat.getFLAG_EC()));
                    jsonObjectCC.put("CUSTNO", ((dat.getCUSTNO() == null) ? "" : dat.getCUSTNO()));
                    jsonObjectCC.put("TIMEIN", ((dat.getTIMEIN() == null) ? "" : dat.getTIMEIN()));
                    jsonObjectCC.put("TIMEOUT", ((dat.getTIMEOUT() == null) ? "" : dat.getTIMEOUT()));
                    jsonObjectCC.put("REASON", ((dat.getREASON() == null) ? "" : dat.getREASON()));
                    jsonObjectCC.put("LATITUDE", ((dat.getLATITUDE() == null) ? "" : dat.getLATITUDE()));
                    jsonObjectCC.put("LONGITUDE", ((dat.getLONGITUDE() == null) ? "" : dat.getLONGITUDE()));
                    jsonArrayCC.put(jsonObjectCC);
                }catch (Exception e){

                }
            }

            if (objCustcard1s.size() == 0){
                jsonObject.put("custcard1", jsonArrayCC);
            }else{
                jsonObject.put("custcard1", jsonArrayCC);
            }

        }catch (Exception e){

        }
    }

    static void UpTrx_H(String ORDERNO){
        Obj_TRX_H objTrxH = new Obj_TRX_H();

        JSONObject jsonObjectCC;
        JSONArray jsonArrayCC = new JSONArray();
        ArrayList<Obj_TRX_H> objTrxHs = new ArrayList<>();

        try{
            objTrxHs = objTrxH.getDataList(ORDERNO);
            for(Obj_TRX_H dat : objTrxHs){
                try{
                    jsonObjectCC = new JSONObject();
                    jsonObjectCC.put("ORDERDATE", dat.getORDERDATE());
                    jsonObjectCC.put("CUSTNO", dat.getCUSTNO());
                    jsonObjectCC.put("ORDERNO", ((dat.getORDERNO() == null) ? "" : dat.getORDERNO()));
                    jsonObjectCC.put("REMARK", ((dat.getREMARK() == null) ? "" : dat.getREMARK()));
                    jsonObjectCC.put("FLAG_VOID", ((dat.getFLAG_VOID() == null) ? "N" : dat.getFLAG_VOID()));
                    jsonObjectCC.put("SKU", dat.getSKU());
                    jsonObjectCC.put("INVAMOUNT", dat.getINVAMOUNT());

                    jsonArrayCC.put(jsonObjectCC);

                    UpTrx_D((dat.getORDERNO() == null) ? "" : dat.getORDERNO());
                }catch (Exception e){

                }
            }
            jsonObject.put("trx_h", jsonArrayCC);
            if (objTrxHs.size() == 0){
                jsonObject.put("trx_d", jsonArrayCC);
            }

        }catch (Exception e){

        }
    }

    static void UpTrx_H(){
        Obj_TRX_H objTrxH = new Obj_TRX_H();

        JSONObject jsonObjectCC;
        JSONArray jsonArrayCC = new JSONArray();
        ArrayList<Obj_TRX_H> objTrxHs = new ArrayList<>();

        try{
            objTrxHs = objTrxH.getDataList();
            for(Obj_TRX_H dat : objTrxHs){
                try{
                    jsonObjectCC = new JSONObject();
                    jsonObjectCC.put("ORDERDATE", dat.getORDERDATE());
                    jsonObjectCC.put("CUSTNO", dat.getCUSTNO());
                    jsonObjectCC.put("ORDERNO", ((dat.getORDERNO() == null) ? "" : dat.getORDERNO()));
                    jsonObjectCC.put("REMARK", ((dat.getREMARK() == null) ? "" : dat.getREMARK()));
                    jsonObjectCC.put("FLAG_VOID", ((dat.getFLAG_VOID() == null) ? "N" : dat.getFLAG_VOID()));
                    jsonObjectCC.put("SKU", dat.getSKU());
                    jsonObjectCC.put("INVAMOUNT", dat.getINVAMOUNT());

                    jsonArrayCC.put(jsonObjectCC);

                    //UpTrx_D((dat.getORDERNO() == null) ? "" : dat.getORDERNO());
                }catch (Exception e){

                }
            }
            jsonObject.put("trx_h", jsonArrayCC);
            if (objTrxHs.size() == 0){
                jsonObject.put("trx_d", jsonArrayCC);
            }

        }catch (Exception e){

        }
    }

    static void UpTrx_D(String ORDERNO){
        Obj_TRX_D objTrxD = new Obj_TRX_D();

        JSONObject jsonObjectCC;
        JSONArray jsonArrayCC = new JSONArray();
        try{
            for(Obj_TRX_D dat : objTrxD.getDataList(ORDERNO)){
                try{
                    jsonObjectCC = new JSONObject();

                    jsonObjectCC.put("ORDERNO", ((dat.getORDERNO() == null) ? "" : dat.getORDERNO()));
                    jsonObjectCC.put("PCODE", ((dat.getPCODE() == null) ? "" : dat.getPCODE()));
                    jsonObjectCC.put("QTY_B", dat.getQTY_B());
                    jsonObjectCC.put("QTY_T", dat.getQTY_T());
                    jsonObjectCC.put("QTY_K", dat.getQTY_K());
                    jsonObjectCC.put("QTY_PCS", dat.getQTY_PCS());
                    jsonObjectCC.put("SELLPRICE1", dat.getSELLPRICE1());
                    jsonObjectCC.put("SELLPRICE2", dat.getSELLPRICE2());
                    jsonObjectCC.put("SELLPRICE3", dat.getSELLPRICE3());
                    jsonObjectCC.put("AMOUNT", dat.getAMOUNT());

                    jsonArrayCC.put(jsonObjectCC);
                }catch (Exception e){

                }
            }

            jsonObject.put("trx_d", jsonArrayCC);
        }catch (Exception e){

        }
    }

    static void UpTrx_D(){
        Obj_TRX_D objTrxD = new Obj_TRX_D();

        JSONObject jsonObjectCC;
        JSONArray jsonArrayCC = new JSONArray();
        try{
            for(Obj_TRX_D dat : objTrxD.getDataList()){
                try{
                    jsonObjectCC = new JSONObject();

                    jsonObjectCC.put("ORDERNO", ((dat.getORDERNO() == null) ? "" : dat.getORDERNO()));
                    jsonObjectCC.put("PCODE", ((dat.getPCODE() == null) ? "" : dat.getPCODE()));
                    jsonObjectCC.put("QTY_B", dat.getQTY_B());
                    jsonObjectCC.put("QTY_T", dat.getQTY_T());
                    jsonObjectCC.put("QTY_K", dat.getQTY_K());
                    jsonObjectCC.put("QTY_PCS", dat.getQTY_PCS());
                    jsonObjectCC.put("SELLPRICE1", dat.getSELLPRICE1());
                    jsonObjectCC.put("SELLPRICE2", dat.getSELLPRICE2());
                    jsonObjectCC.put("SELLPRICE3", dat.getSELLPRICE3());
                    jsonObjectCC.put("AMOUNT", dat.getAMOUNT());

                    jsonArrayCC.put(jsonObjectCC);
                }catch (Exception e){

                }
            }

            jsonObject.put("trx_d", jsonArrayCC);
        }catch (Exception e){

        }
    }


    static void UpCustMstNotGPS(){

        JSONObject jsonObjectCC;
        JSONArray jsonArrayCC = new JSONArray();
        Obj_CUSTMST_GEN objCustmst = new Obj_CUSTMST_GEN();
        ArrayList<Obj_CUSTMST_GEN> objCustmsts = new ArrayList<>();

        try{
            objCustmsts = objCustmst.getDataListALL();
            for (Obj_CUSTMST_GEN dat : objCustmsts) {
                try {
                    jsonObjectCC = new JSONObject();
                    jsonObjectCC.put("CUSTNO", dat.getCUSTNO());
                    jsonObjectCC.put("SLSNO", dat.getSLSNO());
                    jsonObjectCC.put("TGL", dat.getTGL());
                    jsonObjectCC.put("MITRA_ID", dat.getKODECABANG());
                    jsonObjectCC.put("PROVINSI", ((dat.getPROVINSI() == null) ? "" : dat.getPROVINSI()));
                    jsonObjectCC.put("KABUPATEN", ((dat.getKABUPATEN() == null) ? "" : dat.getKABUPATEN()));
                    jsonObjectCC.put("KECAMATAN", ((dat.getKECAMATAN() == null) ? "" : dat.getKECAMATAN()));
                    jsonObjectCC.put("KODEPOS", ((dat.getKODEPOS() == null) ? "" : dat.getKODEPOS()));
                    jsonObjectCC.put("KELURAHAN", ((dat.getKELURAHAN() == null) ? "" : dat.getKELURAHAN()));
                    jsonObjectCC.put("ALAMAT", ((dat.getALAMAT() == null) ? "" : dat.getALAMAT()));

                    jsonArrayCC.put(jsonObjectCC);

                } catch (Exception e) {

                }
            }
            jsonObject.put("custmst", jsonArrayCC);
        }catch (Exception e){

        }
    }

    static void UpCollection(){
        Obj_COLLECTION objCollection = new Obj_COLLECTION();

        JSONObject jsonObjectCC;
        JSONArray jsonArrayCC = new JSONArray();
        ArrayList<Obj_COLLECTION> objCollections = new ArrayList<>();

        try{
            objCollections = objCollection.getDataList();
            for(Obj_COLLECTION dat : objCollections){
                try{
                    jsonObjectCC = new JSONObject();
                    jsonObjectCC.put("CUSTNO", dat.getCUSTNO());
                    jsonObjectCC.put("Q1", ((dat.getQ1() == null) ? "" : dat.getQ1()));
                    jsonObjectCC.put("Q2", ((dat.getQ2() == null) ? "" : dat.getQ2()));
                    jsonObjectCC.put("Q3", ((dat.getQ3() == null) ? "" : dat.getQ3()));
                    jsonArrayCC.put(jsonObjectCC);

                    //UpTrx_H(dat.getDOCNO());
                }catch (Exception e){

                }
            }
            jsonObject.put("collection", jsonArrayCC);
        }catch (Exception e){

        }
    }

    static void UpComplaint(){
        Obj_COMPLAINT objComplaint = new Obj_COMPLAINT();

        JSONObject jsonObjectCC;
        JSONArray jsonArrayCC = new JSONArray();
        ArrayList<Obj_COMPLAINT> objComplaints = new ArrayList<>();

        try{
            objComplaints = objComplaint.getDataList();
            for(Obj_COMPLAINT dat : objComplaints){
                try{
                    jsonObjectCC = new JSONObject();
                    jsonObjectCC.put("CUSTNO", dat.getCUSTNO());
                    jsonObjectCC.put("Q1", ((dat.getQ1() == null) ? "" : dat.getQ1()));
                    jsonObjectCC.put("Q2", ((dat.getQ2() == null) ? "" : dat.getQ2()));
                    jsonObjectCC.put("Q3", ((dat.getQ3() == null) ? "" : dat.getQ3()));
                    jsonObjectCC.put("Q4", ((dat.getQ4() == null) ? "" : dat.getQ4()));
                    jsonObjectCC.put("Q5", ((dat.getQ5() == null) ? "" : dat.getQ5()));
                    jsonArrayCC.put(jsonObjectCC);

                    //UpTrx_H(dat.getDOCNO());
                }catch (Exception e){

                }
            }
            jsonObject.put("complaint", jsonArrayCC);
        }catch (Exception e){

        }
    }

    static void UpMaintenance(){
        Obj_MAINTENANCE objMaintenance = new Obj_MAINTENANCE();

        JSONObject jsonObjectCC;
        JSONArray jsonArrayCC = new JSONArray();
        ArrayList<Obj_MAINTENANCE> objMaintenances = new ArrayList<>();

        try{
            objMaintenances = objMaintenance.getDataList();
            for(Obj_MAINTENANCE dat : objMaintenances){
                try{
                    jsonObjectCC = new JSONObject();
                    jsonObjectCC.put("CUSTNO", dat.getCUSTNO());
                    jsonObjectCC.put("Q1", ((dat.getQ1() == null) ? "" : dat.getQ1()));
                    jsonObjectCC.put("Q2", ((dat.getQ2() == null) ? "" : dat.getQ2()));
                    jsonObjectCC.put("Q3", ((dat.getQ3() == null) ? "" : dat.getQ3()));
                    jsonObjectCC.put("Q4", ((dat.getQ4() == null) ? "" : dat.getQ4()));
                    jsonObjectCC.put("Q5", ((dat.getQ5() == null) ? "" : dat.getQ5()));
                    jsonArrayCC.put(jsonObjectCC);

                    //UpTrx_H(dat.getDOCNO());
                }catch (Exception e){

                }
            }
            jsonObject.put("maintenance", jsonArrayCC);
        }catch (Exception e){

        }
    }

    static void UpDispenser(){
        Obj_DISPENSER objDispenser = new Obj_DISPENSER();

        JSONObject jsonObjectCC;
        JSONArray jsonArrayCC = new JSONArray();
        ArrayList<Obj_DISPENSER> objDispensers = new ArrayList<>();

        try{
            objDispensers = objDispenser.getDataList();
            Log.w("dispenser", "size " + objDispensers.size());
            for(Obj_DISPENSER dat : objDispensers){
                try{
                    jsonObjectCC = new JSONObject();
                    jsonObjectCC.put("CUSTNO", ((dat.getCUSTNO() == null) ? "" : dat.getCUSTNO()));
                    jsonObjectCC.put("PCODE", ((dat.getPCODE() == null) ? "" : dat.getPCODE()));
                    jsonObjectCC.put("QTY", dat.getQTY());
                    jsonArrayCC.put(jsonObjectCC);
                }catch (Exception e){
                    Log.w("dispenser", "error " + e.getMessage());
                }
            }

            Log.w("dispenser", jsonArrayCC.toString());
            jsonObject.put("dispenser", jsonArrayCC);
        }catch (Exception e){

        }
    }
}
