package com.onestechsolution.onestechgoldsolution.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.onestechsolution.onestechgoldsolution.R;
import com.onestechsolution.onestechgoldsolution.Utilities.SetURL;
import com.onestechsolution.onestechgoldsolution.Utilities.Utility;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    String role;
    SetURL setUrl;
    //MenuItem changeEnvironment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setUrl = new SetURL(this);
        //changeEnvironment = (MenuItem) findViewById(R.id.group_environment);
        Log.i(TAG, " username: " + Utility.getPreferences(this, "username") + " password: " + Utility.getPreferences(this, "password")
                + " role: " + Utility.getPreferences(this, "role") + " environment: " + Utility.getPreferences(this, "environment"));
        role = Utility.getPreferences(this, "role");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        if (role.equalsIgnoreCase("admin")) {
            //changeEnvironment.setVisible(true);
          //  menu.setGroupVisible(R.id.group_environment,true);

        } else {
            //changeEnvironment.setVisible(false);
            //menu.setGroupVisible(R.id.group_environment, false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                //Toast.makeText(this, "Logout button pressed", Toast.LENGTH_SHORT).show();
                Utility.setPreferences(this, "username", "");
                Utility.setPreferences(this, "password", "");
                Intent intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;

            case R.id.action_update:
                Uri uri = Uri.parse("http://103.231.8.162/apk/GAudit.apk");
                Intent intent1 = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent1);
                break;

            /*case R.id.action_test:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                findViewById(R.id.tv_TestEnv_HomeActivity).setVisibility(View.VISIBLE);
                Utility.setPreferences(this, "environment", "TEST");
                Toast.makeText(this, "Environment changed to TEST", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onOptionsItemSelected: environment: " + Utility.getPreferences(this, "environment"));
                return true;

            case R.id.action_prod:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                findViewById(R.id.tv_TestEnv_HomeActivity).setVisibility(View.GONE);
                Utility.setPreferences(this, "environment", "PROD");
                Toast.makeText(this, "Environment changed to PROD", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onOptionsItemSelected: environment: " + Utility.getPreferences(this, "environment"));
                return true;*/

            default:
                return super.onOptionsItemSelected(item);

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
        if (Utility.isNetworkAvailable(this)) {
            startActivity(new Intent(this, ReportsActivity.class));
        } else {
            Toast.makeText(this, "Network issue. Please switch on your internet.", Toast.LENGTH_SHORT).show();
        }
    }

    public void openAddCustomerActivity(View view) {
            if(Utility.isNetworkAvailable(this))
            {
                startActivity(new Intent(this,AddNewCustomerDetails.class));
            }
            else{
                Toast.makeText(this, "Network issue. Please switch on your internet", Toast.LENGTH_SHORT).show();
            }

    }
}
