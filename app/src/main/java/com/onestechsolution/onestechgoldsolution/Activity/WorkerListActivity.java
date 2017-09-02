package com.onestechsolution.onestechgoldsolution.Activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.onestechsolution.onestechgoldsolution.Adapters.WorkerListAdapter;
import com.onestechsolution.onestechgoldsolution.Model.Worker;
import com.onestechsolution.onestechgoldsolution.R;
import com.onestechsolution.onestechgoldsolution.Utilities.SetURL;
import com.onestechsolution.onestechgoldsolution.Utilities.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class WorkerListActivity extends AppCompatActivity {
    private static String TAG = "WorkerListActivity";
    ListView listView;

    WorkerListAdapter workerListAdapter;

    ArrayList<Worker> workerArrayList;
    EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_list);
        etSearch = (EditText) findViewById(R.id.et_Search_WorkerListActivity);
        listView = (ListView) findViewById(R.id.lv_WorkerList_WorkerListActivity);
        workerArrayList = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(WorkerListActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void showList(String myJSON) {
        workerArrayList.clear();
        try {
            Log.i("json", "list data " + myJSON);
            if (myJSON != null && !myJSON.isEmpty()) {
                //JSONArray jsonArray = new JSONArray(myJSON);
                JSONObject jsonRoot = new JSONObject(myJSON);
                if (!jsonRoot.getBoolean("status")) {
                    JSONArray jsonArray = jsonRoot.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Worker dataitem = null;
                        String custName = jsonObject.get("name").toString();
                        String pan_no = jsonObject.get("pan_no").toString();
                        String aadhar_no = jsonObject.get("aadhar_no").toString();
                        String salary = jsonObject.get("salary").toString();
                        String joining_date = jsonObject.get("joining_date").toString();
                        String birth_date = jsonObject.get("birth_date").toString();
                        String phone = jsonObject.get("phone").toString();
                        String description = jsonObject.get("description").toString();
                        String address = jsonObject.get("address").toString();
                        String worker_login_id = jsonObject.get("worker_login_id").toString();
                        String photo = jsonObject.get("photo").toString();


                        dataitem = new Worker(custName, pan_no, aadhar_no, salary, joining_date, phone, birth_date, address, description, worker_login_id, photo);
                        //loanData = new NewLoan(jsonObject.get("customerName").toString(),jsonObject.get("phone").toString(),"100000","GBV_12sadfks",jsonObject.get("photo").toString());
                        Log.i("JSON", "list data " + dataitem.getName());
                        //Log.i(TAG, "showList: data " + loanData.getCustomerName());
                        workerArrayList.add(dataitem);
                        //loanListItems.add(loanData);
                    }
                    Log.i(TAG, "showList: message: " + jsonRoot.get("message"));
                } else {
                    Toast.makeText(this, jsonRoot.getString("message"), Toast.LENGTH_SHORT).show();
                }

                if (workerArrayList != null && workerArrayList.size() > 0) {
                    workerListAdapter = new WorkerListAdapter(this, workerArrayList);
                    listView.setAdapter(workerListAdapter);
                } else {
                    listView.setAdapter(null);
                    Log.i(TAG, "showList: No such record found");
                    //Toast.makeText(this, "No such record found", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "JSON response is empty", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onSearch(View view) {
        if (Utility.isNetworkAvailable(this)) {
            Utility.hideKeyboard(view, this);
            String searchValue = etSearch.getText().toString();
            Log.i(TAG, "onSearch: searchValue: " + searchValue);
            if (searchValue.equals(null) || searchValue.isEmpty()) {
                Toast.makeText(this, "Please enter some value to search for", Toast.LENGTH_SHORT).show();
            } else {
                new FetchWorkerDetails(this).execute(searchValue);
            }
        } else {
            Toast.makeText(this, "Network issue. Please switch on the internet.", Toast.LENGTH_SHORT).show();
        }
    }


    public class FetchWorkerDetails extends AsyncTask<String, String, String> {
        Context context;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        public FetchWorkerDetails(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {
            String searchValue = params[0];
            String data = "";
            Log.i(TAG, "doInBackground: searchValue: " + searchValue);
            String text = "";
            BufferedReader reader = null;
            try {
                //String link = "http://onestechsolution.com/ImageTesting/fetchLoanDetails.php";
                //String link = "http://192.168.0.102:8080/ImageTesting/fetchWorkerDetails.php";
                SetURL setURL = new SetURL(context);
                String link = setURL.FetchWorkerDetails;
                //String link = SetURL.FetchWorkerDetails;
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                conn.setDoInput(true);

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                data = URLEncoder.encode("username", "UTF-8")
                        + "=" + URLEncoder.encode(Utility.getPreferences(getApplicationContext(), "username"), "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8")
                        + "=" + URLEncoder.encode(Utility.getPreferences(getApplicationContext(), "password"), "UTF-8");
                data += "&" + URLEncoder.encode("search_value", "UTF-8") + "=" +
                        URLEncoder.encode(searchValue, "UTF-8");
                Log.i(TAG, "doInBackground: data: " + data);
                wr.write(data);
                wr.flush();

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line + "\n");
                    //sb.append(line);
                }
                text = sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "doInBackground: text: " + text);
            return text;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            showList(s);
            Log.i("json", "JSON: " + s);
        }
    }
}
