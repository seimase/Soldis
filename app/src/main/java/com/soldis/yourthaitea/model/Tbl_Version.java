package com.soldis.yourthaitea.model;

import java.util.List;

/**
 * Created by ftctest on 20/10/2017.
 */

public class Tbl_Version {
    public List<Datum> data ;
    public boolean error ;
    public String message ;
    
    public class Datum
    {
        public String VERSION_ID ;
        public String APK_NAME ;
        public String APPLICATION_TYPE ;
    }
    
}
