package com.onestechsolution.onestechgoldsolution.Asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.onestechsolution.onestechgoldsolution.Activity.StockHomeActivity;
import com.onestechsolution.onestechgoldsolution.Model.Stock;
import com.onestechsolution.onestechgoldsolution.Utilities.SetURL;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Admin on 6/8/2017.
 */

public class SendSaleData extends AsyncTask<Stock, Integer, String> {
    private static String TAG = "SendSaleData";
    String message;
    private Context context;
    private ProgressDialog progressDialog;
    private String username, password;

    public SendSaleData(Context context, String username, String password) {
        this.context = context;
        this.username = username;
        this.password = password;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("Uploading data to the server..");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(Stock... params) {
        String line, data = "";
        //String link = "http://192.168.0.102:8080/ImageTesting/uploadSaleDetails.php";
        SetURL setURL = new SetURL(context);
        String link = setURL.SendSaleData;

        //String link = SetURL.SendSaleData;
        StringBuilder sb = null;
        URL url;
        HttpURLConnection conn;
        Stock stock = params[0];
        BufferedReader reader = null;

        try {
            url = new URL(link);
            sb = new StringBuilder();
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(conn.getOutputStream());

            data = "";
            data += URLEncoder.encode("username", "UTF-8")
                    + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8")
                    + "=" + URLEncoder.encode(password, "UTF-8");
            //Log.i(TAG, "doInBackground: username: " + username+ " password: "+password);

            data += "&" + URLEncoder.encode("json", "UTF-8")
                    + "=" + URLEncoder.encode(stock.getJSON(), "UTF-8");
            Log.i(TAG, "doInBackground: json: " + stock.getJSON());

            outputStreamWriter.write(data);
            outputStreamWriter.flush();

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            Log.i(TAG, "doInBackground: sb: " + sb);

            JSONObject jsonObject = new JSONObject(sb.toString());
            message = jsonObject.getString("message");

            Log.i(TAG, "doInBackground: message: " + message);
            return message;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onPostExecute: s: " + s);
        Intent intent = new Intent(context, StockHomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        ((Activity) context).finish();
        //context.startActivity(new Intent(context, StockHomeActivity.class));
    }
}
