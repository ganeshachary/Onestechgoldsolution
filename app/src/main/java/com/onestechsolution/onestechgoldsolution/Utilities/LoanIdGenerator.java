package com.onestechsolution.onestechgoldsolution.Utilities;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by OnesTech on 20/05/2017.
 */

public class LoanIdGenerator {
    static private String uidDate, uidTime;
    static private  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


    private  static  String getCurrentDateAndTime() {
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        //Log.i("Time", "getCurrentDateAndTime: "+date);
        String[] splitDate = currentDate.split("\\s");

        for(int i = 0; i<splitDate.length; i++) {
            if(i==0) {
                uidDate = splitDate[i];
            }
            if(i==1) {
                uidTime = splitDate[i];
            }
        }

        String uidDateArray[] = uidDate.split("/");
        String uidTimeArray[] = uidTime.split(":");
        uidDate = "";
        uidTime = "";
        for(String d:uidDateArray) {
            uidDate += d;
        }

        for(String t: uidTimeArray) {
            uidTime += t;
        }
        return uidDate+uidTime;

    }

    private static int getRandomNumber() {
        return (int)(Math.random()*50+1);

    }

    public static String generateUUId() {

        String uniqueID = "GBV"+getRandomNumber()+"_"+getCurrentDateAndTime();
        System.out.println("Creating a unique id: "+uniqueID);
        return uniqueID;
    }
}
