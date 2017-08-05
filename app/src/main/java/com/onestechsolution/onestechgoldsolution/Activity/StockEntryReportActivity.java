package com.onestechsolution.onestechgoldsolution.Activity;

import android.app.ProgressDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.onestechsolution.onestechgoldsolution.R;

public class StockEntryReportActivity extends AppCompatActivity {
    WebView webView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_entry_report);
        webView = (WebView) findViewById(R.id.wv_StockWebview_StockEntryReport);
        progressBar = (ProgressBar) findViewById(R.id.pb_ProgressBar_StockEntryReport);

        webView.setWebViewClient(new AppWebViewClients(progressBar));
        webView.loadUrl("http://onestechsolution.com/GBVJewellers/displayStockDetails.php");
        webView.getSettings().setSupportZoom(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.getSettings().setBuiltInZoomControls(true);
        //webView.getSettings().setDisplayZoomControls(true);
    }

    public class AppWebViewClients extends WebViewClient {
        private ProgressBar progressBar;

        public AppWebViewClients(ProgressBar progressBar) {
            this.progressBar = progressBar;
            progressBar.setVisibility(View.VISIBLE);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }
}
