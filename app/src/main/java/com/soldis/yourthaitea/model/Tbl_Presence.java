package com.soldis.yourthaitea.model;

import java.util.List;

/**
 * Created by snugrah4 on 3/9/2018.
 */

public class Tbl_Presence {
    public String tgl_range ;
    public String tgl_start ;
    public String tgl_end ;
    public boolean error ;
    public String message ;
    public String tgl ;
    public List<DataH> data_h ;
    public List<DataD> data_d ;
    
    public class DataH
    {
        public String SLSNO ;
        public String SLSNAME ;
        public String ADDRESS ;
        public String PHOTO ;
        public String APP_VERSION ;
        public int TOTAL_VISIT;
        public String TGLVISIT ;
        public boolean BROW;
    }

    public class DataD
    {
        public String TGLVISIT ;
        public String SLSNO ;
        public String TMGO ;
        public String TMBCK ;
        public String LATITUDE_GO ;
        public String LONGITUDE_GO ;
        public String TGLBACK ;
        public String KODECABANG ;
        public String LATITUDE_BCK ;
        public String LONGITUDE_BCK ;
    }

}
