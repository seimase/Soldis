package com.soldis.yourthaitea.model;

import java.util.List;

/**
 * Created by User on 8/24/2017.
 */

public class Tbl_Karyawan {

    public List<Datum> data ;
    public boolean error ;
    public String message ;

    public class Datum
    {
        public String SLSNO ;
        public String SLSNAME ;
        public String PASSWORD ;
        public String MITRA_ID ;
        public String ADDRESS ;
        public String CPHONE ;
        public String DEVICE_ID ;
        public String LEVEL_ID ;
        public String PREFIX_ID ;
        public String PHOTO ;
        public double UANG_MAKAN ;
    }
    
}
