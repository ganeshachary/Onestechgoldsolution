package com.onestechsolution.onestechgoldsolution.Utilities;

import android.content.Context;
import android.util.Log;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by Admin on 08-Jun-17.
 */

public class SetURL {
    private static final String TAG = "SetURL";
    private String environment;
    private Context context;

    public final String URLUploads;
    public final String LoginCheck;

    //FOR FETCHING VALUES FROM THE SERVER
    public final String FetchWorkerDetails;
    public final String FetchStockIds;
    public final String SendWorkerAttendanceData;
    public final String FetchWorkerLoginIdsForAttendance;
    public final String FetchLoanDetails;
    public final String FetchGoldInOutDetails;
    public final String FetchStockReportDetails;
    public final String FetchLoanReportDetails;
    public final String FetchGoldInOutReportDetails;
    public final String FetchAttendanceReportDetails;

    //FOR SENDING VALUES TO THE SERVER

    public final String SendAddWorkerData;
    public final String SendGoldInData;
    public final String SendGoldOutData;
    public final String SendNewLoanData;
    public final String SendLoanListDetailsData;
    public final String SendSaleData;
    public final String SendStockData;
    public final String SendCustomeDetailsData;

    //PROD URL
    private static String prodWebUrl = "http://103.231.8.162/ImageTesting/";

    //TEST URL
    private static String testWebUrl= "http://devwellupsolutions.com/GBVJewellers/ImageTesting/";

    public SetURL(Context context) {
        this.context = context;
        String currentWebURL = setWebServiceURL(context);
        Log.i(TAG, "SetURL: currentWebURL: "+currentWebURL);

        URLUploads = currentWebURL + "uploads/";
        LoginCheck = currentWebURL + "loginCheck.php";

        FetchWorkerDetails = currentWebURL + "fetchWorkerDetails.php";
        FetchStockIds = currentWebURL + "getIdFromStockForSKU.php";
        FetchWorkerLoginIdsForAttendance = currentWebURL + "fetchWorkerLoginIds.php";
        FetchLoanDetails = currentWebURL + "fetchLoanDetails.php";
        FetchGoldInOutDetails = currentWebURL + "fetchGoldInOutDetails.php";
        FetchStockReportDetails = currentWebURL + "fetchStockReportDetails.php";
        FetchLoanReportDetails = currentWebURL + "fetchLoanReportDetails.php";
        FetchGoldInOutReportDetails = currentWebURL + "fetchGoldInOutReportDetails.php";
        FetchAttendanceReportDetails = currentWebURL + "fetchAttendanceReportDetails.php";

        SendWorkerAttendanceData = currentWebURL + "uploadAttendance.php";
        SendAddWorkerData = currentWebURL + "uploadWorkerDetails.php";
        SendGoldInData = currentWebURL + "uploadGoldIn.php";
        SendGoldOutData = currentWebURL + "uploadGoldOut.php";
        SendNewLoanData = currentWebURL + "uploadNewLoanDetails.php";
        SendLoanListDetailsData = currentWebURL + "uploadLoanListDetails.php";
        SendSaleData = currentWebURL + "uploadSaleDetails.php";
        SendStockData = currentWebURL + "uploadStockDetails.php";
        SendCustomeDetailsData = currentWebURL + "uploadCustomerDetails.php";

    }

    public String setWebServiceURL(Context context) {
        environment = Utility.getPreferences(context, "environment");

        if(environment!=null) {
            Log.i(TAG, "setWebServiceURL: context: "+context + " environment: "+environment);
            String sharedPrefEnv = environment;
            Log.i(TAG, "setWebServiceURL: sharedPrefEnv: "+sharedPrefEnv);
            if(sharedPrefEnv.equalsIgnoreCase("TEST")) {
                Log.i(TAG, "setWebServiceURL: TEST URL :"+testWebUrl);
                return testWebUrl;
            }
            Log.i(TAG, "setWebServiceURL: PROD URL : "+ prodWebUrl);
            return prodWebUrl;

        } else {
            Log.i(TAG, "setWebServiceURL: environment: "+environment);
        }
        return null;
    }

  //public static final String SendSaleDataTesting = URL + "uploadSaleDetailsTesting.php";

}
