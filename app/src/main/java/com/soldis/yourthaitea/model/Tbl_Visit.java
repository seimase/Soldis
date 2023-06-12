package com.soldis.yourthaitea.model;

import java.util.List;

public class Tbl_Visit {

    public boolean error ;
    public String message ;
    public String trxno ;
    public String tgltrx ;
    public String tmgo ;
    public List<Datum> data ;
    public List<Histcust> histcust ;
    public List<Typeout> typeout ;
    public List<Category> category ;
    public List<Rute> rute ;
    public List<Stock> stock ;
    public List<Product> product;

    public class Rute
    {
        public String CUSTNO ;
    }

    public class Datum
    {
        public String SLSNO ;
        public String SLSNAME ;
        public String PASSWORD ;
        public String KODECABANG ;
        public String ADDRESS ;
        public String CITY ;
        public String PHONE ;
        public String SEQNO ;
        public String TARGET_EC ;
        public String TARGET_EC_MTD ;
        public String TARGET_SALES ;
        public String TARGET_SALES_MTD ;
        public String TOTAL_EC ;
        public String TOTAL_CALL ;
        public String TARGET_CALL ;
        public String TARGET_CALL_MTD ;
        public String TOTAL_SALES ;
    }

    public class Histcust
    {
        public String KODECABANG ;
        public String SLSNO ;
        public String CUSTNO ;
        public String ORDERDATE ;
        public String CUSTNAME ;
        public String CUSTADD1 ;
        public String CCONTACT ;
    }

    public class Typeout
    {
        public String TYPEOUT ;
        public String TYPENAME ;
        public String GROUP_CODE ;
        public String GROUP_NAME ;
    }

    public class Category
    {
        public String CATEGORY_ID ;
        public String CATEGORY_NAME ;
    }

    public class Product
    {
        public String PCODE ;
        public String PCODENAME ;
        public String PHOTO_NAME ;
        public String UNIT1 ;
        public String UNIT2 ;
        public String UNIT3 ;
        public long CONVUNIT2 ;
        public long CONVUNIT3 ;
        public String FLAG_SALES ;
        public double BASICPRICE ;
        public double SELLPRICE ;
        public String CATEGORY_ID ;
    }

    public class Stock
    {
        public String PCODE ;
        public int QTY ;
    }

}
