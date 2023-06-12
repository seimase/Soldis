package com.soldis.yourthaitea.model;

import java.util.List;

/**
 * Created by User on 8/24/2017.
 */

public class Tbl_Master {

    public boolean error ;
    public String message ;
    public int total_row ;
    public List<Master> master ;
    public List<Category> category ;
    public List<ProductLine> product_line ;
    public List<MasterDispenser> master_dispenser;
    public List<Stock> stock;
    public List<StockMapping> stock_mapping;

    public class Master
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
        public String MITRA_ID ;
        public String TGL_TRX;
        public String TIMEIN;
        public String SLSNO;
        public int DOCNO;
        public String PCODE ;
        public int QTY ;
        public String FLAG_IN ;

    }

    public class StockMapping
    {
        public String PCODE_SALES ;
        public String PCODE ;
        public int QTY ;
    }

    public class MasterDispenser
    {
        public String PCODEUCI ;
        public String PCODEUCINAME ;
        public String PHOTO_NAME ;
    }

    public class ProductLine
    {
        public String PRLIN ;
        public String PRLINAME ;
        public String KOMPLAG ;
    }

    public class Category
    {
        public String CATEGORY_ID ;
        public String CATEGORY_NAME ;
    }

}
