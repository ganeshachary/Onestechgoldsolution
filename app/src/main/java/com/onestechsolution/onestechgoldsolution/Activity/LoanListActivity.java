package com.onestechsolution.onestechgoldsolution.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.onestechsolution.onestechgoldsolution.Adapters.LoanListViewAdapter;
import com.onestechsolution.onestechgoldsolution.Model.NewLoan;
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

public class LoanListActivity extends AppCompatActivity {
    private static String TAG = "LoanListActivity";
    ListView listView;

    LoanListViewAdapter loanListViewAdapter;
    NewLoan dataitem = null;
    //ArrayList<LoanListItem> loanListItems ;
    EditText etSearch;
    ArrayList<NewLoan> loanListItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_list);
        etSearch = (EditText) findViewById(R.id.et_Search_LoanListActivity);
        listView = (ListView) findViewById(R.id.lv_LoanList_LoanListActivity);
        loanListItems = new ArrayList<>();

        //Onclick listener on listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Object obj= listView.getItemAtPosition(position);
                String currentItem = (String)obj;
                Toast.makeText(LoanListActivity.this, currentItem, Toast.LENGTH_SHORT).show();*/
                //Log.i(TAG, "onItemClick: customer name: "+dataitem.getCustomerName());
                dataitem = loanListItems.get(position);
                Log.i(TAG, "onItemClick: getId: "+((TextView)view.findViewById(R.id.tv_LoginId_WorkerListView)).getText().toString());
                if (Utility.isNetworkAvailable(getApplication())) {
                    Intent intent = new Intent(getApplicationContext(), LoanListDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("newLoan", dataitem);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplication(), "Network issue. Please switch on the internet.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //new FetchLoanDetails().execute("start");
    }


    protected void showList(String myJSON) {
        loanListItems.clear();
        try {
            Log.i("showList", "list data " + myJSON);
            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonRoot = new JSONObject(myJSON);
                if (!jsonRoot.getBoolean("status")) {
                    JSONArray jsonArray = jsonRoot.getJSONArray("data");
                    //JSONArray jsonArray = new JSONArray(myJSON);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject rootObject = jsonArray.getJSONObject(i);

                        //NewLoan loanData = null;
                        //dataitem=new LoanListItem(jsonObject.get("customerName").toString(),jsonObject.get("contact").toString(),jsonObject.get("amount").toString(),jsonObject.get("loan_id").toString(),"url");
                        String custName = rootObject.get("name").toString();
                        String phone = rootObject.get("phone").toString();
                        String custPhotoUri = rootObject.get("cust_photo").toString();
                        String date = rootObject.get("date").toString();
                        String time = rootObject.get("time").toString();
                        String amount = rootObject.get("amount").toString();
                        String loanUniqueId = rootObject.get("loan_unique_id").toString();
                        String interest = rootObject.get("interest").toString();
                        String noOfItems = rootObject.get("number_items").toString();
                        String grossWeight = rootObject.get("gross_weight").toString();
                        String status = rootObject.get("status").toString();
                        String profitLossStatus = rootObject.get("profitloss_status").toString();
                        String lastEmiPaidDate = rootObject.get("last_emi_paid_date").toString();
                        String currentGoldRate = rootObject.get("current_gold_rate").toString();
                        String description = rootObject.get("description").toString();
                        String netWeight = rootObject.get("net_weight").toString();
                        String partyCode = rootObject.get("party_code").toString();
                        String renewalDate = rootObject.get("renewal_date").toString();
                        String party_ref_number= rootObject.get("party_ref_number").toString();

                        String penalty_payable = rootObject.get("penalty_payable").toString();
                        String penalty_percentage = rootObject.get("penalty_percentage").toString();
                        String emi_payable = rootObject.get("emi_payable").toString();
                        String emi_months_pending = rootObject.get("emi_months_pending").toString();
                        String principle_payable = rootObject.get("principle_payable").toString();




                       /* String type = jsonObject.get("type").toString();
                        String count = jsonObject.get("count").toString();
                        String weight = jsonObject.get("weight").toString();
                        String itemPhoto = jsonObject.get("item_photo").toString();*/
                       JSONArray items = rootObject.getJSONArray("items");
                        /*for (int j = 0; j < items.length(); j++) {
                            JSONObject jsonObject = items.getJSONObject(j);
                            String item_type = jsonObject.getString("type");
                            String item_count = jsonObject.getString("count");
                            String item_weight = jsonObject.getString("weight");
                            String item_photo = jsonObject.getString("item_photo");
                        }*/




                        dataitem = new NewLoan(custName, phone, custPhotoUri, date, time, amount,
                                loanUniqueId, interest, grossWeight, noOfItems, netWeight, items, description,
                                status, profitLossStatus, lastEmiPaidDate, currentGoldRate, partyCode, renewalDate,
                                party_ref_number, penalty_payable, penalty_percentage, emi_payable, emi_months_pending,
                                principle_payable);
                        //loanData = new NewLoan(jsonObject.get("customerName").toString(),jsonObject.get("phone").toString(),"100000","GBV_12sadfks",jsonObject.get("photo").toString());
                        Log.i(TAG, "showList: customerName: " + dataitem.getCustomerName());
                        Log.i(TAG, "showList: customerPhotoUri: "+dataitem.getCustPhotoUri());
                        //Log.i(TAG, "showList: data " + loanData.getCustomerName());
                        /*int numberOfItems = Integer.parseInt(noOfItems);
                        Log.i(TAG, "showList: numberOfItems: "+noOfItems);
                        for (int j = 0; j < numberOfItems; j++) {
                            Log.i(TAG, "showList: dataItem.getItemCount() "+ Arrays.toString(dataitem.getItemCount()));
                        }*/

                        //loanListItems is an ArrayList<NewLoan>
                        loanListItems.add(dataitem);
                        //loanListItems.add(loanData);
                    }
                    Log.i(TAG, "showList: message: " + jsonRoot.get("message"));
                } else {
                    Toast.makeText(this, jsonRoot.getString("message"), Toast.LENGTH_SHORT).show();
                }


                if (loanListItems != null && loanListItems.size() > 0) {
                    loanListViewAdapter = new LoanListViewAdapter(this, loanListItems);
                    listView.setAdapter(loanListViewAdapter);
                } else {
                    /*loanListItems.clear();
                    loanListViewAdapter.notifyDataSetChanged();*/
                    listView.setAdapter(null);
                    Log.i(TAG, "showList: No such record fouund");
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
            if (searchValue.equals(null) || searchValue.isEmpty()) {
                Toast.makeText(this, "Please enter some value to search for", Toast.LENGTH_SHORT).show();
            } else {
                new FetchLoanDetails(this).execute(searchValue);
            }
        } else {
            Toast.makeText(this, "Network issue. Please switch on the internet.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }



    public class FetchLoanDetails extends AsyncTask<String, String, String> {
        private static final String TAG = "FetchLoanDetails";
        Context context;
        ArrayList<NewLoan> loanListItems;

        public FetchLoanDetails(Context context) {
            this.context = context;
        }

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
                //String link = "http://192.168.0.102:8080/ImageTesting/fetchLoanDetails.php";
                SetURL setURL = new SetURL(context);
                String link = setURL.FetchLoanDetails;
                //String link = SetURL.FetchLoanDetails;
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                conn.setDoInput(true);

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                data = URLEncoder.encode("search_value", "UTF-8") + "=" +
                        URLEncoder.encode(searchValue, "UTF-8");
                data += "&" + URLEncoder.encode("username", "UTF-8")
                        + "=" + URLEncoder.encode(Utility.getPreferences(context, "username"), "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8")
                        + "=" + URLEncoder.encode(Utility.getPreferences(context, "password"), "UTF-8");
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
