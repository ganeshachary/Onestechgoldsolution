package com.onestechsolution.onestechgoldsolution.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.onestechsolution.onestechgoldsolution.Asynctask.FetchWorkerLoginIds;
import com.onestechsolution.onestechgoldsolution.R;
import com.onestechsolution.onestechgoldsolution.Utilities.Utility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class WorkerHomeActivity extends AppCompatActivity implements FetchWorkerLoginIds.AsyncResponse {
    private static String TAG = "WorkerHomeActivity";
    String username, password;
    ArrayList<String> workerLoginIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_home);
        username = Utility.getPreferences(this, "username");
        password = Utility.getPreferences(this, "password");
        Log.i(TAG, "onCreate: username: " + username + " password: " + password);
        workerLoginIds = new ArrayList<>();
    }

    public void openAddWorkerActivity(View view) {
        if (Utility.isNetworkAvailable(this)) {
            Intent intent = new Intent(this, AddWorkerActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Network issue. Please switch on the internet.", Toast.LENGTH_SHORT).show();
        }
    }

    public void openExistingWorkerActivity(View view) {
        if (Utility.isNetworkAvailable(this)) {
            Intent intent = new Intent(this, WorkerListActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Network issue. Please switch on the internet.", Toast.LENGTH_SHORT).show();
        }
    }

    public void openAttendanceActivity(View view) {
        if (Utility.isNetworkAvailable(this)) {
            FetchWorkerLoginIds asyncTask = new FetchWorkerLoginIds(this, view, username, password);
            asyncTask.delegate = this;
            asyncTask.execute();
        } else {
            Toast.makeText(this, "Network issue. Please switch on the internet.", Toast.LENGTH_SHORT).show();
        }

    }

    public void openGoldOutActivity(View view) {
        if (Utility.isNetworkAvailable(this)) {
            FetchWorkerLoginIds asyncTask = new FetchWorkerLoginIds(this, view, username, password);
            asyncTask.delegate = this;
            asyncTask.execute();
        } else {
            Toast.makeText(this, "Network issue. Please switch on the internet.", Toast.LENGTH_SHORT).show();
        }
    }

    public void openSearchGoldInOutActivity(View view) {
        if (Utility.isNetworkAvailable(this)) {
            Intent intent = new Intent(this, GoldInOutListActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Network issue. Please switch on the internet.", Toast.LENGTH_SHORT).show();
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
                /*JSONArray jsonArray = new JSONArray(myJSON);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String workerLoginIds = jsonObject.get("worker_login_id").toString();
                    this.workerLoginIds.add(workerLoginIds);
                }
                Log.i(TAG, "parseJSON: workerLoginIds: " + workerLoginIds.toString());
            } else {
                Toast.makeText(this, "JSON response is empty", Toast.LENGTH_SHORT).show();
            }*/

            }
            return workerLoginIds;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void processFinish(String output, View view) {
        parseJSON(output);
        if (view == findViewById(R.id.btn_OpenAttendance_WorkerActivity)) {
            Intent intent = new Intent(this, AttendanceActivity.class);
            Log.i(TAG, "openAttendanceActivity: workerLoginIds: " + workerLoginIds.toString());
            intent.putStringArrayListExtra("workerLoginIds", workerLoginIds);
            startActivity(intent);
        }
        if (view == findViewById(R.id.btn_GoldOut_WorkerActivity)) {
            Intent intent = new Intent(this, GoldOutActivity.class);
            Log.i(TAG, "processFinish: workerLoginIds: " + workerLoginIds.toString());
            intent.putStringArrayListExtra("workerLoginIds", workerLoginIds);
            startActivity(intent);
        }

    }
}
