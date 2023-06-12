package com.soldis.yourthaitea.model;

import java.util.List;

/**
 * Created by User on 8/24/2017.
 */

public class Tbl_CustmstGen {

    public boolean error ;
    public String message ;
    public List<Datum> data;

    public class Datum
    {
        public String TGL ;
        public String SLSNO ;
        public String KODECABANG ;
        public String CUSTNO ;
        public String LATITUDE ;
        public String LONGITUDE ;
    }
}
