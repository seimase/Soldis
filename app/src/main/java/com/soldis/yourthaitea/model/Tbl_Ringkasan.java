package com.soldis.yourthaitea.model;

import java.util.List;

public class Tbl_Ringkasan {
    public String tgl ;
    public List<DataSale> data_sales ;
    public List<DataKa> data_kas ;
    public List<DataUang> data_uangmakan ;
    public List<DataStock> data_stock ;
    public boolean error ;
    public String message ;

    public class DataSale
    {
        public String ORDERDATE ;
        public String PCODE ;
        public String PCODENAME ;
        public String QTY ;
        public long SELLPRICE1 ;
        public long AMOUNT ;
    }

    public class DataStock
    {
        public String PCODE ;
        public String PCODENAME ;
        public long QTY ;
        public String FLAG_IN ;
    }

    public class DataKa
    {
        public String MITRA_ID ;
        public String TGL_TRX ;
        public String DOCNO ;
        public String TIMEIN ;
        public String SLSNO ;
        public String REMARK ;
        public long AMOUNT ;
        public String TYPE_KAS ;
    }

    public class DataUang
    {
        public String SLSNO ;
        public long UANG_MAKAN ;
    }
}
