package com.soldis.yourthaitea.model;

import java.util.List;

/**
 * Created by snugrah4 on 12/27/2017.
 */


public class Tbl_SendDataCustNotGPS
{
    public List<Datum> data ;
    public boolean error ;
    public String message ;

    public class Datum
    {
        public String ZONEID ;
    }
}


