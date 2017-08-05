package com.onestechsolution.onestechgoldsolution.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.onestechsolution.onestechgoldsolution.Adapters.GoldInOutListAdapter;
import com.onestechsolution.onestechgoldsolution.Model.GoldInOut;
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

public class GoldInOutListActivity extends AppCompatActivity {
    private static String TAG = "GoldInOutListActivity";
    ListView listView;
    GoldInOutListAdapter goldInOutListAdapter;
    GoldInOut dataitem = null;
    ArrayList<GoldInOut> goldInOutArrayList;
    EditText etSearch;
    String username, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_gold_in_out);
        etSearch = (EditText) findViewById(R.id.et_Search_SearchGoldInOutActivity);
        listView = (ListView) findViewById(R.id.lv_GoldInOutList_GoldInOutListActivity);
        goldInOutArrayList = new ArrayList<>();
        username = Utility.getPreferences(this, "username");
        password = Utility.getPreferences(this, "password");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (Utility.isNetworkAvailable(getApplication())) {
                    Intent intent = new Intent(GoldInOutListActivity.this, GoldInActivity.class);
                    Bundle bundle = new Bundle();
                    dataitem = goldInOutArrayList.get(position);
                    Log.i(TAG, "onItemClick: billNo: " + dataitem.getBillNo());
                    bundle.putParcelable("GoldInOut", dataitem);
                    intent.putExtras(bundle);
                    GoldInOutListActivity.this.finish();
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplication(), "Network issue. Please switch on the internet.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    public void onSearch(View view) {
        if (Utility.isNetworkAvailable(this)) {
            Utility.hideKeyboard(view, this);
            String searchValue = etSearch.getText().toString();
            Log.i(TAG, "onSearch: searchValue: " + searchValue);
            if (searchValue.equals(null) || searchValue.isEmpty()) {
                Toast.makeText(this, "Please enter some value to search for", Toast.LENGTH_SHORT).show();
            } else {
                new FetchGoldInOutDetails().execute(searchValue);
            }
        } else {
            Toast.makeText(this, "Network issue. Please switch on the internet.", Toast.LENGTH_SHORT).show();
        }
    }

    protected void showList(String myJSON) {
        goldInOutArrayList.clear();
        try {
            Log.i("json", "list data " + myJSON);
            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonRoot = new JSONObject(myJSON);
                if (!jsonRoot.getBoolean("status")) {
                    JSONArray jsonArray = jsonRoot.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String billNo = jsonObject.get("bill_no").toString();
                        String billBook = jsonObject.get("bill_book").toString();
                        String billDate = jsonObject.get("bill_date").toString();
                        String deliveryDate = jsonObject.get("delivery_date").toString();
                        String goldIn = jsonObject.get("gold_in").toString();
                        String goldOut = jsonObject.get("gold_out").toString();
                        String goldInDate = jsonObject.get("goldin_date").toString();
                        String goldInTime = jsonObject.get("goldin_time").toString();
                        String workerLoginId = jsonObject.get("worker_login_id").toString();
                        String description = jsonObject.get("description").toString();

                        dataitem = new GoldInOut(billNo, billBook, billDate, deliveryDate, goldIn, goldOut, goldInDate, goldInTime, workerLoginId, description);
                        Log.i("JSON", "list data " + dataitem.getBillNo());

                        goldInOutArrayList.add(dataitem);
                    }

                    Log.i(TAG, "showList: message: " + jsonRoot.get("message"));

                } else {
                    Toast.makeText(this, jsonRoot.getString("message"), Toast.LENGTH_SHORT).show();
                }

                if (goldInOutArrayList != null && goldInOutArrayList.size() > 0) {
                    goldInOutListAdapter = new GoldInOutListAdapter(this, goldInOutArrayList);
                    listView.setAdapter(goldInOutListAdapter);
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

    public class FetchGoldInOutDetails extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

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
                //String link = "http://192.168.0.101:8080/ImageTesting/fetchGoldInOutDetails.php";
                String link = SetURL.FetchGoldInOutDetails;
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                conn.setDoInput(true);

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                data = URLEncoder.encode("search_value", "UTF-8") + "=" +
                        URLEncoder.encode(searchValue, "UTF-8");
                data += "&" + URLEncoder.encode("username", "UTF-8")
                        + "=" + URLEncoder.encode(username, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8")
                        + "=" + URLEncoder.encode(password, "UTF-8");
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
