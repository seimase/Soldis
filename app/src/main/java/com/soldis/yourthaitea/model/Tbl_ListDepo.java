package com.soldis.yourthaitea.model;

import java.util.List;

/**
 * Created by snugrah4 on 11/29/2017.
 */

public class Tbl_ListDepo {
    public String tgl ;
    public List<Datum> data ;
    public boolean error ;
    public String message ;

    public class Datum
    {
        public String KODECABANG ;
        public String NAMACABANG ;
        public String JUMLAH_MOTORIS ;
        public String JUMLAH_HADIR ;
        public String TOTAL_CALL ;
        public String TOTAL_EC ;
        public double INVAMOUNT ;
        public String JUMLAH_MOTORIS_AFH ;
        public String JUMLAH_HADIR_AFH ;
        public String TOTAL_CALL_AFH ;
        public String TOTAL_EC_AFH ;
        public long TOTAL_MIX ;
        public double INVAMOUNT_AFH ;
        public double TARGET_EC_MTD ;
        public double TARGET_CALL_MTD ;
        public double TARGET_SALES_MTD ;
        public double TARGET_EC ;
        public double TARGET_CALL ;
        public double TARGET_SALES ;
        public boolean BROW ;
    }

}
