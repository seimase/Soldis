package com.soldis.yourthaitea.model;

import java.util.List;

/**
 * Created by ftctest on 05/09/2017.
 */

public class Tbl_Promo_Banner {
    public List<Datum> data ;
    public List<Team> team;
    public boolean error ;
    public String message ;

    public class Datum
    {
        public String KODECABANG ;
        public String IMG_URL ;
        public String WEB_URL ;
    }

    public class Team
    {
        public String KODECABANG ;
        public String EMAIL_ID ;
        public String PHONE_NUMBER ;
    }

}
