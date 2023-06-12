package com.soldis.yourthaitea.model;

import java.util.List;

/**
 * Created by snugrah4 on 12/27/2017.
 */


public class Tbl_ListZone
{
    public List<Datum> data ;
    public List<Target> target;
    public boolean error ;
    public String message ;

    public class Datum
    {
        public String ZONEID ;
        public double TOTAL_EC_REGULAR ;
        public double TOTAL_EC_AFH ;
        public double TOTAL_CALL_REGULAR ;
        public double TOTAL_CALL_AFH ;
        public double TOTAL_SALES_REGULAR ;
        public double TOTAL_SALES_AFH ;
        public double TOTAL_MOTORIS_REGULAR ;
        public double TOTAL_MOTORIS_AFH ;
        public double KEHADIRAN_REGULAR ;
        public double KEHADIRAN_AFH ;
        public double TOTAL_RO ;
        public boolean BROW ;
    }

    public class Target
    {
        public double TARGET_EC ;
        public double TARGET_SALES ;
        public double TARGET_EC_MTD ;
        public double TARGET_SALES_MTD ;
        public double TARGET_CALL ;
        public double TARGET_CALL_MTD ;
    }
}


