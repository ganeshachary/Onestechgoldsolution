package com.onestechsolution.onestechgoldsolution.Asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.onestechsolution.onestechgoldsolution.Utilities.SetURL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by OnesTech on 03/08/2017.
 */

public class FetchGoldInOutReportDetails extends AsyncTask<String, String, String>{
    private static String TAG = "FetchGoldReportDetails";
    public FetchGoldInOutReportDetails.GoldInOutReportInterface goldInOutInterface = null;
    Context context;
    String message;
    private ProgressDialog progressDialog;

    String username, password;

    public FetchGoldInOutReportDetails(Context context,  String username, String password) {
        this.context = context;
        this.username = username;
        this.password = password;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Please wait..");
        progressDialog.setMessage("Fetching some information...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();

    }

    @Override
    protected String doInBackground(String... params) {

        BufferedReader reader = null;

        String data = "";
        try {

            SetURL setURL = new SetURL(context);
            String link = setURL.FetchGoldInOutReportDetails;
            //String link = SetURL.FetchGoldInOutReportDetails;
            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(conn.getOutputStream());

            data = "";
            data += URLEncoder.encode("username", "UTF-8")
                    + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8")
                    + "=" + URLEncoder.encode(password, "UTF-8");
            Log.i(TAG, "doInBackground: username: " + username + " password: " + password);

            outputStreamWriter.write(data);
            outputStreamWriter.flush();

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
                //sb.append(line);
            }
            Log.i(TAG, "doInBackground: sb: " + sb);

            return sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        goldInOutInterface.goldInOutProcessFinish(s);
        Log.i(TAG, "onPostExecute: JSON: " + s);
    }

    public interface GoldInOutReportInterface {
        void goldInOutProcessFinish(String output);
    }
}
