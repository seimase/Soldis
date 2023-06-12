package com.soldis.yourthaitea.model;

import java.util.List;

public class Tbl_Stock {
    public List<DataH> data_h ;
    public List<DataD> data_d ;
    public boolean error ;
    public String message ;

    public class DataH
    {
        public String MITRA_ID ;
        public String MITRA_NAME ;
    }

    public class DataD
    {
        public String MITRA_ID ;
        public String TGL_TRX ;
        public String PCODE ;
        public String PCODENAME ;
        public String QTY ;
        public String PHOTO_NAME ;
        public String QTY_OUT ;
    }
    
}
