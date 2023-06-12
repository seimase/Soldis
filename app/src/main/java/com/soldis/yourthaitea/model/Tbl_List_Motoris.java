package com.soldis.yourthaitea.model;

import java.util.List;

/**
 * Created by ftctest on 29/09/2017.
 */

public class Tbl_List_Motoris {
    public String tgl ;
    public List<Datum> data ;
    public List<Trx> trx ;
    public List<TrxD> trx_d;
    public List<Kehadiran> kehadiran;
    public boolean error ;
    public String message ;
    
    public class Datum
    {
        public String KODECABANG ;
        public String LEADER_ID ;
        public String SLSNO ;
        public String PHOTO ;
        public String SALES_TYPE ;
        public double TOTAL_SALES ;
        public double TOTAL_EC ;
        public double TOTAL_CALL ;
        public double TARGET_EC ;
        public double TARGET_SALES ;
        public double TARGET_EC_MTD ;
        public double TARGET_CALL ;
        public double TARGET_CALL_MTD ;
        public double TARGET_SALES_MTD ;
        public String SLSNAME ;
    }

    public class Trx
    {
        public String KODECABANG ;
        public String SLSNO ;
        public String ORDERNO ;
        public String CUSTNO ;
        public String CUSTNAME ;
        public String CUSTADD1 ;
        public int SKU ;
        public double INVAMOUNT ;
        public String ORDERDATE ;
        public String PHOTO ;
        public String REMARK ;
    }


    public class TrxD
    {
        public String KODECABANG ;
        public String SLSNO ;
        public String ORDERNO ;
        public String PCODE ;
        public String PHOTO_NAME ;
        public String UNIT1 ;
        public String UNIT2 ;
        public String UNIT3 ;
        public int QTY_B ;
        public int QTY_T ;
        public int QTY_K ;
        public int QTY_PCS ;
        public double SELLPRICE1 ;
        public double SELLPRICE2 ;
        public double SELLPRICE3 ;
        public double AMOUNT ;
        public String ORDERDATE ;
        public String PCODENAME ;
    }

    public class Kehadiran
    {
        public String KODECABANG ;
        public String SLSNO ;
        public String TGLVISIT;
    }
    
}
