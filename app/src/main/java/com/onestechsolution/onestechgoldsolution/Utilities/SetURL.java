package com.onestechsolution.onestechgoldsolution.Utilities;

/**
 * Created by Admin on 08-Jun-17.
 */

public class SetURL {
    //private static final String URL = "http://192.168.0.104:8081/GBVJewellers/ImageTesting/";
    private static final String URL = "http://www.onestechsolution.com/GBVJewellers/ImageTesting/";
   // private static final String URL = "http://devwellupsolutions.com/GBVJewellers/ImageTesting/";
    public static final String URLUploads = URL+"uploads/";
    public static final String LoginCheck = URL + "loginCheck.php";

    //FOR FETCHING VALUES FROM THE SERVER
    public static final String FetchWorkerDetails = URL + "fetchWorkerDetails.php";
    public static final String FetchStockIds = URL + "getIdFromStockForSKU.php";
    public static final String FetchWorkerLoginIdsForAttendance = URL + "fetchWorkerLoginIds.php";
    public static final String FetchLoanDetails = URL + "fetchLoanDetails.php";
    public static final String FetchGoldInOutDetails = URL + "fetchGoldInOutDetails.php";
    public static final String FetchStockReportDetails = URL + "fetchStockReportDetails.php";
    public static final String FetchLoanReportDetails = URL + "fetchLoanReportDetails.php";
    public static final String FetchGoldInOutReportDetails = URL + "fetchGoldInOutReportDetails.php";
    public static final String FetchAttendanceReportDetails = URL + "fetchAttendanceReportDetails.php";

    //FOR SENDING VALUES TO THE SERVER

    public static final String SendAddWorkerData = URL + "uploadWorkerDetails.php";
    public static final String SendGoldInData = URL + "uploadGoldIn.php";
    public static final String SendGoldOutData = URL + "uploadGoldOut.php";
    public static final String SendNewLoanData = URL + "uploadNewLoanDetails.php";
    public static final String SendLoanListDetailsData = URL + "uploadLoanListDetails.php";
    public static final String SendSaleData = URL + "uploadSaleDetails.php";
    public static final String SendStockData = URL + "uploadStockDetails.php";
    public static final String SendWorkerAttendanceData = URL + "uploadAttendance.php";


    //public static final String SendSaleDataTesting = URL + "uploadSaleDetailsTesting.php";



}
