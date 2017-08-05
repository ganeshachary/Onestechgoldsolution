package com.onestechsolution.onestechgoldsolution.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.onestechsolution.onestechgoldsolution.Model.StockReport;
import com.onestechsolution.onestechgoldsolution.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class StockReportActivity extends AppCompatActivity {
    StockReport stockReport;
    public static String TAG = "StockReportActivity";
    TextView tvInstock, tvSold, tvGrossWeight, tvNetWeight, tvStoneWeight, tvNoOfStones, tvRingCount, tvChainCount, tvStudCount, tvNecklaceCount, tvEarringsCount, tvBraceletCount, tvKalamaniCount, tvEarchainCount,
            tvDollarCount, tvHaarCount, tvBanglesCount, tvOthersCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_report);

        stockReport = new StockReport();
        stockReport = getIntent().getExtras().getParcelable("stockReport");
        Log.i(TAG, "onCreate: stockReport.getInstock: " + stockReport.getInStockItems());

        tvInstock = (TextView) findViewById(R.id.tv_Instock_StockReportActivity);
        tvSold = (TextView) findViewById(R.id.tv_Sold_StockReportActivity);
        tvGrossWeight = (TextView) findViewById(R.id.tv_GrossWeight_StockReportActivity);
        tvNetWeight = (TextView) findViewById(R.id.tv_NetWeight_StockReportActivity);
        tvStoneWeight = (TextView) findViewById(R.id.tv_StoneWeight_StockReportActivity);
        tvNoOfStones = (TextView) findViewById(R.id.tv_NoOfStones_StockReportActivity);
        tvRingCount = (TextView) findViewById(R.id.tv_RingCount_StockReportActivity);
        tvChainCount = (TextView) findViewById(R.id.tv_ChainCount_StockReportActivity);
        tvStudCount = (TextView) findViewById(R.id.tv_StudCount_StockReportActivity);
        tvNecklaceCount = (TextView) findViewById(R.id.tv_NecklaceCount_StockReportActivity);
        tvEarringsCount = (TextView) findViewById(R.id.tv_EarringsCount_StockReportActivity);
        tvBraceletCount = (TextView) findViewById(R.id.tv_BraceletCount_StockReportActivity);
        tvKalamaniCount = (TextView) findViewById(R.id.tv_KalamaniCount_StockReportActivity);
        tvEarringsCount = (TextView) findViewById(R.id.tv_EarringsCount_StockReportActivity);
        tvDollarCount = (TextView) findViewById(R.id.tv_DollarCount_StockReportActivity);
        tvHaarCount = (TextView) findViewById(R.id.tv_HaarCount_StockReportActivity);
        tvBanglesCount = (TextView) findViewById(R.id.tv_BanglesCount_StockReportActivity);
        tvOthersCount = (TextView) findViewById(R.id.tv_OthersCount_StockReportActivity);

        setTextViewValues();
    }

    public void setTextViewValues() {
        tvInstock.setText(stockReport.getInStockItems());
        tvSold.setText(stockReport.getSoldItems());
        tvGrossWeight.setText(stockReport.getTotalGrossWeight());
        tvNetWeight.setText(stockReport.getTotalNetWeight());
        tvStoneWeight.setText(stockReport.getTotalStoneWeight());
        tvNoOfStones.setText(stockReport.getTotalNoOfStones());
        tvRingCount.setText(stockReport.getTotalRings());
        tvChainCount.setText(stockReport.getTotalChains());
        tvStudCount.setText(stockReport.getTotalStuds());
        tvNecklaceCount.setText(stockReport.getTotalNecklaces());
        tvEarringsCount.setText(stockReport.getTotalEarrings());
        tvBraceletCount.setText(stockReport.getTotalBracelets());
        tvKalamaniCount.setText(stockReport.getTotalKalamanis());
        tvEarringsCount.setText(stockReport.getTotalEarrings());
        tvDollarCount.setText(stockReport.getTotalDollars());
        tvHaarCount.setText(stockReport.getTotalHaars());
        tvBanglesCount.setText(stockReport.getTotalBangles());
        tvOthersCount.setText(stockReport.getTotalOthers());

    }


    public void openStockEntryReport(View view) {
        Intent intent = new Intent(this, StockEntryReportActivity.class);
        startActivity(intent);
    }
}
