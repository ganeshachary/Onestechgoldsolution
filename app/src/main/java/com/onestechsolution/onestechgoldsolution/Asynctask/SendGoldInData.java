package com.onestechsolution.onestechgoldsolution.Asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.onestechsolution.onestechgoldsolution.Activity.WorkerHomeActivity;
import com.onestechsolution.onestechgoldsolution.Utilities.SetURL;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Admin on 6/7/2017.
 */

public class SendGoldInData extends AsyncTask<String, Integer, String> {
    private static String TAG = "SendGoldInData";
    private String message;
    private String username, password;
    private Context context;
    private ProgressDialog progressDialog;

    public SendGoldInData(Context context, String username, String password) {
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
    protected String doInBackground(String... params) {
        String line = "";
        String data = "";
        //String link = "http://192.168.0.102:8080/ImageTesting/uploadGoldIn.php";
        String link = SetURL.SendGoldInData;

        StringBuilder sb = null;
        URL url;
        HttpURLConnection conn;
        String goldIn = params[0];
        String goldInDate = params[1];
        String goldInTime = params[2];
        String billNo = params[3];
        String billDate = params[4];

        Log.i(TAG, "doInBackground: goldIn: " + goldIn + " billNo: " + billNo +
        " goldInDate: "+goldInDate+ " goldInTime: "+goldInTime+ " billDate: "+billDate);
        BufferedReader reader = null;

        try {
            url = new URL(link);
            sb = new StringBuilder();
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(conn.getOutputStream());

            data = URLEncoder.encode("goldIn", "UTF-8")
                    + "=" + URLEncoder.encode(goldIn, "UTF-8");
            data += "&" + URLEncoder.encode("username", "UTF-8")
                    + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8")
                    + "=" + URLEncoder.encode(password, "UTF-8");
            data += "&" + URLEncoder.encode("billNo", "UTF-8")
                    + "=" + URLEncoder.encode(billNo, "UTF-8");
            data += "&" + URLEncoder.encode("goldInDate", "UTF-8")
                    + "=" + URLEncoder.encode(goldInDate, "UTF-8");
            data += "&" + URLEncoder.encode("goldInTime", "UTF-8")
                    + "=" + URLEncoder.encode(goldInTime, "UTF-8");
            data += "&" + URLEncoder.encode("billDate", "UTF-8")
                    + "=" + URLEncoder.encode(billDate, "UTF-8");

            Log.i(TAG, "doInBackground: data: " + data);
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
        Intent intent = new Intent(context, WorkerHomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        ((Activity) context).finish();
        //context.startActivity(new Intent(context, WorkerHomeActivity.class));
    }
}
