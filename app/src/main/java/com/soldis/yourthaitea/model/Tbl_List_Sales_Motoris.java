package com.soldis.yourthaitea.model;

import java.util.List;

/**
 * Created by snugrah4 on 11/2/2017.
 */

public class Tbl_List_Sales_Motoris {
    public String tgl ;
    public List<Datum> data ;
    public List<Trx> trx ;
    public List<TrxD> trx_d ;
    public List<TrxNotEc> trx_not_ec;
    public List<TmVisit> tm_visit;
    public List<DataNoo> data_noo;
    public List<DataDispenser> data_dispenser;
    public boolean error ;
    public String message ;

    public class Datum
    {
        public String KEHADIRAN ;
        public String KEHADIRAN_AFH ;
        public String TOTAL_MOTORIS ;
        public String TOTAL_MOTORIS_AFH ;
        public String TOTAL_CALL ;
        public String CALL_AFH ;
        public String CALL_MTD ;
        public String CALL_AFH_MTD ;
        public String EC ;
        public String EC_AFH ;
        public String EC_MTD ;
        public String EC_AFH_MTD ;
        public double SALES ;
        public double SALES_MTD ;
        public double SALES_AFH ;
        public double SALES_AFH_MTD ;
        public String NOO ;
        public String NOO_AFH ;
        public String NOO_MTD ;
        public String NOO_AFH_MTD ;
    }

    public class Trx
    {
        public String KODECABANG ;
        public String PCODE ;
        public String PCODENAME ;
        public int TOTAL_TOKO ;
        public int TOTAL_TOKO_MTD ;
    }


    public class TrxD
    {
        public String KODECABANG ;
        public String SLSNO ;
        public String SLSNAME ;
        public String ORDERDATE ;
        public String CUSTNO ;
        public String CUSTNAME ;
        public String CUSTADD1 ;
        public String PCODE ;
        public String PCODENAME ;
        public int QTY ;
        public long AMOUNT ;
        public String TGL ;
        public String KELURAHAN ;
        public String KECAMATAN ;
        public String KABUPATEN ;
        public String PROVINSI ;
        public String GOOGLE_PROVINSI ;
        public String GOOGLE_KABUPATEN ;
        public String GOOGLE_KECAMATAN ;
        public String GOOGLE_KELURAHAN ;
        public String GOOGLE_KODEPOS ;
        public String LATITUDE ;
        public String LONGITUDE ;
        public String GOOGLE_ALAMAT ;
        public String CCONTACT ;
    }

    public class TrxNotEc
    {
        public String TGL_TRX ;
        public String SLSNO ;
        public String SLSNAME ;
        public String CUSTNO ;
        public String CUSTNAME ;
        public String CUSTADD1 ;
        public String KELURAHAN ;
        public String GOOGLE_ALAMAT ;
        public String GOOGLE_KELURAHAN ;
        public String GOOGLE_KECAMATAN ;
        public String GOOGLE_KABUPATEN ;
        public String GOOGLE_KODEPOS ;
        public String LATITUDE ;
        public String LONGITUDE ;
        public String CCONTACT ;
    }

    public class TmVisit
    {
        public String SLSNO ;
        public String SLSNAME ;
        public String TMGO ;
        public String TGLVISIT ;
        public String TMBCK ;
        public String TGLBACK ;
        public String SALES_TYPE ;
        public long TOTAL_HADIR ;
        public long TOTAL_EC ;
        public long TOTAL_CALL ;
        public long NOO ;
        public long INVAMOUNT ;
    }

    public class DataNoo
    {
        public String TGL ;
        public String SLSNO ;
        public String SLSNAME ;
        public String CUSTNO ;
        public String CUSTNAME ;
        public String CUSTADD1 ;
        public String KELURAHAN ;
        public String GOOGLE_ALAMAT ;
        public String GOOGLE_KELURAHAN ;
        public String GOOGLE_KECAMATAN ;
        public String GOOGLE_KABUPATEN ;
        public String GOOGLE_KODEPOS ;
        public String CCONTACT ;
        public String LATITUDE ;
        public String LONGITUDE ;
    }

    public class DataDispenser
    {
        public String TGL_TRX ;
        public String KODECABANG ;
        public String SLSNO ;
        public String CUSTNO ;
        public String CUSTNAME ;
        public String PCODE ;
        public int QTY ;
        public String SLSNAME ;
        public String PCODEUCINAME ;
    }

}
