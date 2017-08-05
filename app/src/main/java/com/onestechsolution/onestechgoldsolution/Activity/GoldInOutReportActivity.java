package com.onestechsolution.onestechgoldsolution.Activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.onestechsolution.onestechgoldsolution.Adapters.GoldInOutReportListAdapter;
import com.onestechsolution.onestechgoldsolution.Adapters.WorkerListAdapter;
import com.onestechsolution.onestechgoldsolution.Model.GoldInOut;
import com.onestechsolution.onestechgoldsolution.Model.GoldInOutReport;
import com.onestechsolution.onestechgoldsolution.Model.LoanReport;
import com.onestechsolution.onestechgoldsolution.Model.WorkerGold;
import com.onestechsolution.onestechgoldsolution.R;
import com.onestechsolution.onestechgoldsolution.Utilities.Utility;

import java.util.ArrayList;
import java.util.Arrays;

public class GoldInOutReportActivity extends AppCompatActivity {
    private static final String TAG = "GoldReportActivity";
    GoldInOutReport goldInOutReport;
    ArrayList<WorkerGold> workerGolds;
    GoldInOutReportListAdapter goldInOutReportListAdapter;
    ListView lvWorkerwiseReport;
    TextView tvTotalGoldOut, tvTotalGoldIn, tvTotalBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gold_in_out_report);
        tvTotalGoldOut = (TextView) findViewById(R.id.tv_totalGoldOut_GoldInOutReportActivity);
        tvTotalGoldIn = (TextView) findViewById(R.id.tv_totalGoldIn_GoldInOutReportActivity);
        tvTotalBalance = (TextView) findViewById(R.id.tv_totalBalance_GoldInOutReportActivity);
        lvWorkerwiseReport = (ListView) findViewById(R.id.lv_GoldInOutReportList_GoldInOutReportListActivity);
        goldInOutReport = new GoldInOutReport();
        goldInOutReport = getIntent().getExtras().getParcelable("goldInOutReport");
        Log.i(TAG, "onCreate: Total workers "+goldInOutReport.getTotalWorkers());



        workerGolds = goldInOutReport.getWorkerGolds();
        for (int i = 0; i < workerGolds.size(); i++) {
            Log.i(TAG, "onCreate: "+workerGolds.get(i).getWorkerLoginId());

        }

        if (workerGolds != null && workerGolds.size() > 0) {
            goldInOutReportListAdapter = new GoldInOutReportListAdapter(this, workerGolds);
            lvWorkerwiseReport.setAdapter(goldInOutReportListAdapter);
        } else {
            lvWorkerwiseReport.setAdapter(null);
            Log.i(TAG, "No record found");
        }

        setTextViewValues();
    }

    public void openGoldOutEntryActivity(View view) {
        if(Utility.isNetworkAvailable(this)) {
            Intent intent = new Intent(this, GoldInOutEntryReportActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Network issue. Please switch on the internet", Toast.LENGTH_SHORT).show();
        }
    }

    public void setTextViewValues() {
        tvTotalBalance.setText(goldInOutReport.getTotalBalance());
        tvTotalGoldOut.setText(goldInOutReport.getTotalGoldOut());
        tvTotalGoldIn.setText(goldInOutReport.getTotalGoldIn());

    }
}
