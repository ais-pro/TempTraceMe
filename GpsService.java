package com.example.root.traceme;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Al Imran Suvro on 2/17/16.
 */
public class GpsService extends Service {
    LocationManager locationManager;
    Criteria criteria;
    String provider;
    String dateTime,imei;

    DbHelper dbHelper=new DbHelper(this);


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public GpsService(){
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {




        //imei no info
        TelephonyManager tm=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        imei=tm.getDeviceId();
        //imei no end

        //location info
        locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        criteria=new Criteria();
        provider=locationManager.getBestProvider(criteria,false);
        GPSLocationListener gpsLocationListener=new GPSLocationListener();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return 0;
        }
        locationManager.requestLocationUpdates(provider,10000,1,gpsLocationListener);
        //end location info



        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    public class GPSLocationListener implements LocationListener{
        double lat,lng, accuracy;
        @Override
        public void onLocationChanged(Location location) {

            dateTime=currentTime();

            lat=location.getLatitude();
            lng=location.getLongitude();
            accuracy=location.getAccuracy();
            //provider=location.getProvider();

            long l=dbHelper.insertLocation(lat,lng, imei, dateTime, accuracy);

            Toast.makeText(GpsService.this,"TraceME Status:"+ dateTime,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }


        public String currentTime(){
            // (1) get today's date
            Date today = Calendar.getInstance().getTime();

            // (2) create a date "formatter" (the date format we want)
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");

            // (3) create a new String using the date format we want
            String folderName = formatter.format(today);

            return folderName;
        }
    }
}


