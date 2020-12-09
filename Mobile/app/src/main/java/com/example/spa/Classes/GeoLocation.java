package com.example.spa.Classes;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeoLocation {

    public static  void getAddress(final String locationAddress, final Context context, final Handler handler){
        Thread thread = new Thread(){
            @Override
            public void run(){
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                double longitude = 0;
                double latitude = 0;
                List<Address> addressList;
                try {

                    addressList = geocoder.getFromLocationName(locationAddress, 1);
                    while (addressList.size() == 0) {
                        addressList = geocoder.getFromLocationName(locationAddress, 1);
                    }
                    if(addressList != null && addressList.size() > 0){
                        Address address = (Address) addressList.get(0);

                         longitude = address.getLongitude();
                         latitude = address.getLatitude();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                        Message message = Message.obtain();
                        message.setTarget(handler);
                        if(longitude != 0 || latitude != 0){
                            message.what = 1;
                            Bundle bundle = new Bundle();
                            bundle.putDouble("addressL",longitude);
                            bundle.putDouble("addressL2", latitude);
                            message.setData(bundle);
                        }
                        message.sendToTarget();
                    }
                }
            };
        thread.start();
    }

}
