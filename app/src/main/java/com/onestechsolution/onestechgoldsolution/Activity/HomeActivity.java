package com.onestechsolution.onestechgoldsolution.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.onestechsolution.onestechgoldsolution.R;
import com.onestechsolution.onestechgoldsolution.Utilities.Utility;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_logout:
                //Toast.makeText(this, "Logout button pressed", Toast.LENGTH_SHORT).show();
                Utility.setPreferences(this, "username", "");
                Utility.setPreferences(this, "password","");
                Log.i(TAG, "onOptionsItemSelected: username: "+Utility.getPreferences(this, "username")+ " password: "+Utility.getPreferences(this, "password"));
                Intent intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;

            case R.id.action_update:
                Uri uri = Uri.parse("http://onestechsolution.com/GBVJewellers/apk/GAudit.apk");
                Intent intent1 = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent1);
            default:
                break;
        }
        return true;
    }

    public void openLoanActivity(View view) {

        if (Utility.isNetworkAvailable(this)) {
            Intent intent = new Intent(this, LoanHomeActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Network issue. Please switch on your internet", Toast.LENGTH_SHORT).show();
        }

    }

    public void openOrderActivity(View view) {

    }

    public void openWorkerActivity(View view) {

        if (Utility.isNetworkAvailable(this)) {
            Intent intent = new Intent(this, WorkerHomeActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Network issue. Please switch on your internet", Toast.LENGTH_SHORT).show();
        }
    }

    public void openQRCodeActivity(View view) {

    }

    public void openStockSaleActivity(View view) {

        if (Utility.isNetworkAvailable(this)) {
            Intent intent = new Intent(this, StockHomeActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Network issue. Please switch on your internet", Toast.LENGTH_SHORT).show();
        }

    }

    public void openReportsActivity(View view) {
        if(Utility.isNetworkAvailable(this)) {
            startActivity(new Intent(this, ReportsActivity.class));
        } else {
            Toast.makeText(this, "Network issue. Please switch on your internet.", Toast.LENGTH_SHORT).show();
        }
    }
}
