package com.soldis.yourthaitea.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GPSGeocoder {
    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String knownName;
    Geocoder geocoder;
    List<Address> addresses;

    public GPSGeocoder(){}

    public void getFromLocation(Context context, double latitude,double longitude){
        geocoder = new Geocoder(context,Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            city = addresses.get(0).getLocality();
            state = addresses.get(0).getAdminArea();
            country = addresses.get(0).getCountryName();
            postalCode = addresses.get(0).getPostalCode();
            knownName = addresses.get(0).getFeatureName();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }







}
