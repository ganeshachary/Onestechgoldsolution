package com.onestechsolution.onestechgoldsolution.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.onestechsolution.onestechgoldsolution.Asynctask.FetchStockIds;
import com.onestechsolution.onestechgoldsolution.R;
import com.onestechsolution.onestechgoldsolution.Utilities.Utility;

import org.json.JSONObject;

public class StockHomeActivity extends AppCompatActivity implements FetchStockIds.AsyncResponse {
    private static final String TAG = "StockHomeActivity";
    String username, password;
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_home);
        username = Utility.getPreferences(this, "username");
        password = Utility.getPreferences(this, "password");
        Log.i(TAG, "onCreate: username: " + username + " password: " + password);
    }

    public void openEnterStockDetailsActivity(View view) {
        if (Utility.isNetworkAvailable(this)) {
            FetchStockIds asyncTask = new FetchStockIds(this, username, password);
            asyncTask.delegate = this;
            asyncTask.execute();
        } else {
            Toast.makeText(this, "Network issue. Please switch on the internet.", Toast.LENGTH_SHORT).show();
        }
    }

    public void openEnterSaleDetailsActivity(View view) {
        if (Utility.isNetworkAvailable(this)) {
            Intent intent = new Intent(this, EnterSaleDetailsActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Network issue. Please switch on the internet.", Toast.LENGTH_SHORT).show();
        }
    }

    //processFinish() is a method which is declared inside the interface FetchStockIds.AsyncResponse

    @Override
    public void processFinish(String output) {
        String stockId = parseJSON(output);
        Log.i(TAG, "processFinish: stockId: " + output);
        Intent intent = new Intent(this, EnterStockDetailsActivity.class);
        intent.putExtra("stockId", stockId);
        startActivity(intent);
    }

    private String parseJSON(String myJSON) {
        String stockId;
        try {
            Log.i(TAG, "parseJSON: myJSON: " + myJSON);
            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonRoot = new JSONObject(myJSON);
                if (!jsonRoot.getBoolean("status")) {
                    stockId = jsonRoot.getString("data");
                    Log.i(TAG, "showList: message: " + jsonRoot.get("message"));
                    return stockId;
                } else {
                    Toast.makeText(this, jsonRoot.getString("message"), Toast.LENGTH_SHORT).show();
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
