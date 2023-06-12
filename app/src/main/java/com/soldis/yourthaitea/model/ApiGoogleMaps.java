package com.soldis.yourthaitea.model;

import java.util.List;

/**
 * Created by ftctest on 25/08/2017.
 */

public class ApiGoogleMaps {
    public List<Result> results ;
    public String status ;
    public String error_message;
    
    public class AddressComponent
    {
        public String long_name ;
        public String short_name ;
        public List<String> types ;
    }

    public class Location
    {
        public double lat ;
        public double lng ;
    }

    public class Northeast
    {
        public double lat ;
        public double lng ;
    }

    public class Southwest
    {
        public double lat ;
        public double lng ;
    }

    public class Viewport
    {
        public Northeast northeast ;
        public Southwest southwest ;
    }

    public class Northeast2
    {
        public double lat ;
        public double lng ;
    }

    public class Southwest2
    {
        public double lat ;
        public double lng ;
    }

    public class Bounds
    {
        public Northeast2 northeast ;
        public Southwest2 southwest ;
    }

    public class Geometry
    {
        public Location location ;
        public String location_type ;
        public Viewport viewport ;
        public Bounds bounds ;
    }

    public class Result
    {
        public List<AddressComponent> address_components ;
        public String formatted_address ;
        public Geometry geometry ;
        public String place_id ;
        public List<String> types ;
    }

}
