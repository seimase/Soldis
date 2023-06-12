package com.soldis.yourthaitea.model;

import java.util.List;

public class Tbl_ListSummarySales_ByDepo {
    public List<DataAll> data_all ;
    public List<DataOrg> data_org ;
    public boolean error ;
    public String tgl_trx ;
    public String message ;


    public class DataAll
    {
        public String NAMACABANG ;
        public String KODECABANG ;
        public double TOTAL_SALESMAN ;
        public double TOTAL_EC ;
        public double TOTAL_CALL ;
        public double TARGET_CALL ;
        public double TARGET_EC ;
        public double TOTAL_HADIR ;
        public boolean BROW;
    }

    public class DataOrg
    {
        public String NAMACABANG ;
        public String KODECABANG ;
        public double TOTAL_SALESMAN ;
        public double TOTAL_EC ;
        public double TOTAL_CALL ;
        public double TARGET_CALL ;
        public double TARGET_EC ;
        public double TOTAL_HADIR ;
    }
}

