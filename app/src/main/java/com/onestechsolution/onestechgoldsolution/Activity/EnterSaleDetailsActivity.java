package com.onestechsolution.onestechgoldsolution.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.onestechsolution.onestechgoldsolution.Asynctask.SendSaleData;
import com.onestechsolution.onestechgoldsolution.Model.Stock;
import com.onestechsolution.onestechgoldsolution.R;
import com.onestechsolution.onestechgoldsolution.Utilities.Utility;

public class EnterSaleDetailsActivity extends AppCompatActivity {
    private static String TAG = "EnterSaleDetails";
    String username, password;
    Stock stock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_sale_details);
        username = Utility.getPreferences(this, "username");
        password = Utility.getPreferences(this, "password");
        setDate();
    }

    public void setDate() {
        ((TextView) findViewById(R.id.tv_Date_EnterSaleDetailsActivity)).setText(Utility.getDate());
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_attention)
                .setTitle("Are you sure you want to go back?")
                .setMessage("All the changes will be lost")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void onUpload(View view) {
        String date = ((TextView) findViewById(R.id.tv_Date_EnterSaleDetailsActivity)).getText().toString();
        String billNo = ((EditText) findViewById(R.id.et_BillNo_EnterSaleDetailsActivity)).getText().toString();
        String SKU = ((EditText) findViewById(R.id.et_SKU_EnterSaleDetailsActivity)).getText().toString().toUpperCase();

        if (date.isEmpty() || date.equals(null)) {
            Toast.makeText(this, "Date cannot be empty. Please contact support.", Toast.LENGTH_SHORT).show();
        } else if (billNo.isEmpty() || billNo.equals(null)) {
            Toast.makeText(this, "Please enter the Bill number.", Toast.LENGTH_SHORT).show();
        } else if (SKU.isEmpty() || SKU.equals(null)) {
            Toast.makeText(this, "Please enter SKU", Toast.LENGTH_SHORT).show();
        } else {

            stock = new Stock();
            stock.setDate(date);
            stock.setBillNo(billNo);
            stock.setSku(SKU);

            Log.i(TAG, "onUpload: date: " + date + " billNo: " + billNo + " sku: " + SKU);
            if (Utility.isNetworkAvailable(this)) {
                new AlertDialog.Builder(this)
                        .setTitle("Are you sure?")
                        .setMessage("All the details will be uploaded")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new SendSaleData(EnterSaleDetailsActivity.this, username, password).execute(stock);

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();


            } else {
                Toast.makeText(this, "Network issue. Please switch on the internet.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
