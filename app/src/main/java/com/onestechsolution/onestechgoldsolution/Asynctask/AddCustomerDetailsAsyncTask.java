package com.onestechsolution.onestechgoldsolution.Asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.onestechsolution.onestechgoldsolution.Activity.WorkerHomeActivity;
import com.onestechsolution.onestechgoldsolution.Model.CustomerDeatils;
import com.onestechsolution.onestechgoldsolution.Utilities.SetURL;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import static android.R.attr.data;
import static android.R.id.message;

/**
 * Created by psganesh on 01/10/17.
 */

public class AddCustomerDetailsAsyncTask extends AsyncTask<CustomerDeatils,String,String> {
    private final String TAG ="ADDCUSTOMERDETAILS";
    Context context;
    CustomerDeatils customerDeatils;
    private ProgressDialog progressDialog;
    private String username, password;
    String message;



  public AddCustomerDetailsAsyncTask(Context context, String username, String password)
    {
        this.context = context;
        this.username = username;
        this.password = password;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("Uploading data to server");
        progressDialog.setIndeterminate(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(CustomerDeatils... customerdata) {
        StringBuilder sb = null;
        URL url;
        String data;
        String line;

        boolean status = true;

        SetURL setURL = new SetURL(context);
        String link = setURL.SendCustomeDetailsData;

        HttpURLConnection conn;
        customerDeatils = customerdata[0];



        BufferedReader reader = null;


        try {
            url = new URL(link);
            sb = new StringBuilder();

            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(conn.getOutputStream());

            data = "";
            data += URLEncoder.encode("username", "UTF-8")
                    + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8")
                    + "=" + URLEncoder.encode(password, "UTF-8");
            data += "&" + URLEncoder.encode("json", "UTF-8")
                    + "=" + URLEncoder.encode(customerDeatils.getJSON(), "UTF-8");
            Log.i(TAG, "doInBackground: json: " + customerDeatils.getJSON());

            Log.i(TAG, "doInBackground: data: " + data);
            outputStreamWriter.write(data);
            outputStreamWriter.flush();

            int responseCodeJSON = conn.getResponseCode();
            Log.i(TAG, "doInBackground: responseCode: "+responseCodeJSON);
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            JSONObject jsonObject = new JSONObject(sb.toString());
            message = jsonObject.getString("message");


            Log.i(TAG, "doInBackground: Text data Response: " + message);



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return message;
    }


    @Override
    protected void onPostExecute(String message) {
        super.onPostExecute(message);
        progressDialog.dismiss();
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onPostExecute: s: " + message);

    }
}
