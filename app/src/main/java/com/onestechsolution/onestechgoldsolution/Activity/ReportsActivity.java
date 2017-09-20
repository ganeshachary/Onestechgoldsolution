package com.onestechsolution.onestechgoldsolution.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.onestechsolution.onestechgoldsolution.Asynctask.FetchGoldInOutReportDetails;
import com.onestechsolution.onestechgoldsolution.Asynctask.FetchLoanReportDetails;
import com.onestechsolution.onestechgoldsolution.Asynctask.FetchStockReportDetails;
import com.onestechsolution.onestechgoldsolution.Asynctask.FetchWorkerLoginIds;
import com.onestechsolution.onestechgoldsolution.Model.*;
import com.onestechsolution.onestechgoldsolution.Model.LoanReport;
import com.onestechsolution.onestechgoldsolution.R;
import com.onestechsolution.onestechgoldsolution.Utilities.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class ReportsActivity extends AppCompatActivity implements FetchWorkerLoginIds.AsyncResponse, FetchStockReportDetails.StockReportInterface, FetchLoanReportDetails.LoanReportInterface, FetchGoldInOutReportDetails.GoldInOutReportInterface {

    String username, password;

    StockReport stockReport;
    LoanReport loanReport;
    GoldInOutReport goldInOutReport;
    WorkerGold workerGold;

    ArrayList<String> workerLoginIds;
    ArrayList<WorkerGold> workerGoldArrayList;

    public static String TAG = "ReportsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        username = Utility.getPreferences(this, "username");
        password = Utility.getPreferences(this, "password");
        Log.i(TAG, "onCreate: Username : "+username+" Password: "+password);

        workerLoginIds = new ArrayList<>();
        workerGoldArrayList = new ArrayList<>();

    }

    public void checkLoanReport(View view) {
        if(Utility.isNetworkAvailable(this)) {

            FetchLoanReportDetails asyncTask = new FetchLoanReportDetails(this, username, password);
            asyncTask.loanInterface = this;
            asyncTask.execute();
        } else {
            Toast.makeText(this, "Network issue. Please switch on the internet", Toast.LENGTH_SHORT).show();
        }
    }

    public void checkStockReport(View view) {
        if(Utility.isNetworkAvailable(this)) {

            FetchStockReportDetails asyncTask = new FetchStockReportDetails(this, username, password);
            asyncTask.stockInterface = this;
            asyncTask.execute();
        } else {
            Toast.makeText(this, "Network issue. Please switch on the internet", Toast.LENGTH_SHORT).show();
        }
    }

    public void checkGoldInOutReport(View view) {
        if(Utility.isNetworkAvailable(this)) {
            FetchGoldInOutReportDetails asyncTask = new FetchGoldInOutReportDetails(this, username, password);
            asyncTask.goldInOutInterface = this;
            asyncTask.execute();
        } else {
            Toast.makeText(this, "Network issue. Please switch on the internet", Toast.LENGTH_SHORT).show();
        }
    }

    public void checkAttendanceReport(View view) {
        if(Utility.isNetworkAvailable(this)) {
            FetchWorkerLoginIds asyncTask = new FetchWorkerLoginIds(this, view, username, password);
            asyncTask.delegate = this;
            asyncTask.execute();
        } else {
            Toast.makeText(this, "Network issue. Please switch on the internet", Toast.LENGTH_SHORT).show();
        }

    }

    private void parseStockJSON(String myJSON) {
        //stockReportArrayList.clear();
        try {
            Log.i(TAG, "parseStockJSON: myJSON: "+myJSON);
            if(myJSON !=null && !myJSON.isEmpty()) {
                JSONObject jsonRoot = new JSONObject(myJSON);
                if(!jsonRoot.getBoolean("status")) {
                    JSONArray jsonArray = jsonRoot.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        stockReport = new StockReport();
                        String itemsInStock = jsonObject.get("items_instock").toString();
                        Log.i(TAG, "parseStockJSON: ItemsInstock: "+itemsInStock);
                        stockReport.setInStockItems(jsonObject.get("items_instock").toString());
                        stockReport.setSoldItems(jsonObject.get("items_sold").toString());
                        stockReport.setTotalGrossWeight(jsonObject.get("total_gross_weight").toString());
                        stockReport.setTotalNetWeight(jsonObject.get("total_net_weight").toString());
                        stockReport.setTotalStoneWeight(jsonObject.get("total_stone_weight").toString());
                        stockReport.setTotalNoOfStones(jsonObject.get("total_no_of_stones").toString());
                        stockReport.setTotalRings(jsonObject.get("total_rings").toString());
                        stockReport.setTotalChains(jsonObject.get("total_chains").toString());
                        stockReport.setTotalStuds(jsonObject.get("total_studs").toString());
                        stockReport.setTotalNecklaces(jsonObject.get("total_necklaces").toString());
                        stockReport.setTotalEarrings(jsonObject.get("total_earrings").toString());
                        stockReport.setTotalBracelets(jsonObject.get("total_bracelets").toString());
                        stockReport.setTotalKalamanis(jsonObject.get("total_kalamanis").toString());
                        stockReport.setTotalEarchains(jsonObject.get("total_earchains").toString());
                        stockReport.setTotalDollars(jsonObject.get("total_dollars").toString());
                        stockReport.setTotalHaars(jsonObject.get("total_haars").toString());
                        stockReport.setTotalBangles(jsonObject.get("total_bangles").toString());
                        stockReport.setTotalOthers(jsonObject.get("total_others").toString());
                        Log.i(TAG, "parseStockJSON: stockReport.getInStockItems: "+stockReport.getInStockItems());
                        //stockReportArrayList.add(stockReport);
                    }
                    Log.i(TAG, "parseStockJSON: message: "+jsonRoot.get("message"));
                } else {
                    Toast.makeText(this, jsonRoot.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "JSON response is empty", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void parseLoanJSON(String myJSON) {
        try {
            Log.i(TAG, "parseLoanJSON: myJSON: "+myJSON);
            if(myJSON !=null && !myJSON.isEmpty()) {
                JSONObject jsonRoot = new JSONObject(myJSON);
                if(!jsonRoot.getBoolean("status")) {
                    JSONArray jsonArray = jsonRoot.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        loanReport = new LoanReport();
                        String totalCustomers = jsonObject.get("total_customers").toString();
                        Log.i(TAG, "parseLoanJSON: TotalCustomers: "+totalCustomers);
                        loanReport.setTotalCustomer(jsonObject.get("total_customers").toString());
                        loanReport.setTotalLoanAmount(jsonObject.get("total_loan_amount").toString());
                        loanReport.setTotalPrinciplePaid(jsonObject.get("total_principle_paid").toString());
                        loanReport.setTotalPrinciplePending(jsonObject.get("total_principle_pending").toString());
                        loanReport.setTotalGrossWeight(jsonObject.get("total_gross_weight").toString());
                        loanReport.setTotalNetWeight(jsonObject.get("total_net_weight").toString());
                        loanReport.setTotalRings(jsonObject.get("total_rings").toString());
                        loanReport.setTotalChains(jsonObject.get("total_chains").toString());
                        loanReport.setTotalStuds(jsonObject.get("total_studs").toString());
                        loanReport.setTotalNecklaces(jsonObject.get("total_necklaces").toString());
                        loanReport.setTotalEarrings(jsonObject.get("total_earrings").toString());
                        loanReport.setTotalBracelets(jsonObject.get("total_bracelets").toString());
                        loanReport.setTotalKalamanis(jsonObject.get("total_kalamanis").toString());
                        loanReport.setTotalEarchains(jsonObject.get("total_earchains").toString());
                        loanReport.setTotalDollars(jsonObject.get("total_dollars").toString());
                        loanReport.setTotalHaars(jsonObject.get("total_haars").toString());
                        loanReport.setTotalBangles(jsonObject.get("total_bangles").toString());
                        loanReport.setTotalOthers(jsonObject.get("total_others").toString());
                        loanReport.setTotalPenaltyAmountPaid(jsonObject.get("total_penalty_amount_paid").toString());
                        loanReport.setTotalDiscount(jsonObject.get("total_discount").toString());
                        loanReport.setTotalEMIPaid(jsonObject.get("total_emi_paid").toString());
                        loanReport.setTotalEMIAmountPending(jsonObject.get("total_emi_pending").toString());
                        loanReport.setTotalEMIMonthsPending3(jsonObject.get("total_emi_pending_3").toString());
                        loanReport.setTotalEMIMonthsPending6(jsonObject.get("total_emi_pending_6").toString());
                        loanReport.setTotalNewLoans(jsonObject.get("total_new_loan").toString());
                        loanReport.setTotalClosedLoan(jsonObject.get("total_closed_loan").toString());

                        Log.i(TAG, "parseLoanJSON: loanReport.getTotalBangles(): "+loanReport.getTotalBangles());
                        //stockReportArrayList.add(stockReport);
                    }
                    Log.i(TAG, "parseStockJSON: message: "+jsonRoot.get("message"));
                } else {
                    Toast.makeText(this, jsonRoot.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "JSON response is empty", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void parseGoldInOutJSON(String myJSON) {
        workerGoldArrayList.clear();
        try {
            Log.i(TAG, "parseGoldInOutJSON: myJSON: "+myJSON);
            if(myJSON !=null && !myJSON.isEmpty()) {
                JSONObject jsonRoot = new JSONObject(myJSON);
                if(!jsonRoot.getBoolean("status")) {
                    JSONArray jsonArray = jsonRoot.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        goldInOutReport = new GoldInOutReport();
                        String totalWorkers = jsonObject.get("total_workers").toString();
                        Log.i(TAG, "parseGoldInOutJSON: TotalWorkers: "+totalWorkers);
                        goldInOutReport.setTotalWorkers(jsonObject.get("total_workers").toString());
                        goldInOutReport.setTotalGoldIn(jsonObject.get("total_worker_goldin").toString());
                        Log.i(TAG, "parseGoldInOutJSON: TotalGoldIn: "+jsonObject.get("total_worker_goldin").toString());
                        goldInOutReport.setTotalGoldOut(jsonObject.get("total_worker_goldout").toString());
                        goldInOutReport.setTotalBalance(jsonObject.get("total_balance").toString());

                        JSONArray workerwiseReportArray = jsonObject.getJSONArray("workerwise_report");
                        for (int j = 0; j < workerwiseReportArray.length(); j++) {
                            JSONObject workerLoginIdObject = workerwiseReportArray.getJSONObject(j);
                            workerGold = new WorkerGold();
                            workerGold.setWorkerLoginId(workerLoginIdObject.getString("worker_login_id"));
                            Log.i(TAG, "parseGoldInOutJSON: WorkerLoginId: "+workerLoginIdObject.getString("worker_login_id"));
                            workerGold.setGoldOut(workerLoginIdObject.getString("total_workerwise_goldout"));
                            workerGold.setGoldIn(workerLoginIdObject.getString("total_workerwise_goldin"));
                            workerGold.setBalance(workerLoginIdObject.getString("total_workerwise_balance"));
                            workerGoldArrayList.add(workerGold);
                            //Log.i(TAG, "parseGoldInOutJSON: goldInOutReport.getTotalGoldOut()"+goldInOutReport.getTotalGoldOut());
                        }

                        goldInOutReport.setWorkerGolds(workerGoldArrayList);

                    }

                    Log.i(TAG, "parseGoldInOutJSON: message: "+jsonRoot.get("message"));
                } else {
                    Toast.makeText(this, jsonRoot.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "JSON response is empty", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> parseJSON(String myJSON) {
        workerLoginIds.clear();
        try {
            Log.i(TAG, "parseJSON: myJSON: " + myJSON);
            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonRoot = new JSONObject(myJSON);
                if (!jsonRoot.getBoolean("status")) {
                    JSONArray jsonArray = jsonRoot.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String workerLoginIds = jsonObject.get("worker_login_id").toString();
                        this.workerLoginIds.add(workerLoginIds);
                    }

                    Log.i(TAG, "showList: message: " + jsonRoot.get("message"));

                } else {
                    Toast.makeText(this, jsonRoot.getString("message"), Toast.LENGTH_SHORT).show();
                }

            }
            return workerLoginIds;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void processFinish(String output) {
        parseStockJSON(output);
        Intent intent = new Intent(this, StockReportActivity.class);
        intent.putExtra("stockReport",stockReport);
        startActivity(intent);
    }

    //for fetching worker login ids
    @Override
    public void processFinish(String output, View view) {
       /* parseJSON(output);
       if (view == findViewById(R.id.btn_AttendanceReport_ReportsActivity)) {
            Intent intent = new Intent(this, AttendanceReportActivity.class);
            Log.i(TAG, "openAttendanceReportActivity: workerLoginIds: " + workerLoginIds.toString());
            intent.putStringArrayListExtra("workerLoginIds", workerLoginIds);
            startActivity(intent);
        }*/
    }

    @Override
    public void loanProcessFinish(String output) {
        parseLoanJSON(output);
        Intent intent = new Intent(this, LoanReportActivity.class);
        intent.putExtra("loanReport", loanReport);
        startActivity(intent);
    }

    @Override
    public void goldInOutProcessFinish(String output) {
        parseGoldInOutJSON(output);
        Intent intent = new Intent(this, GoldInOutReportActivity.class);
        Log.i(TAG, "goldInOutProcessFinish: Total Balance: "+goldInOutReport.getTotalBalance());
        intent.putExtra("goldInOutReport",goldInOutReport);
        startActivity(intent);
    }
}
