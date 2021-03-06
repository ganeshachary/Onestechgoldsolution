package com.onestechsolution.onestechgoldsolution.Asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.onestechsolution.onestechgoldsolution.Utilities.SetURL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Admin on 6/7/2017.
 */

public class FetchWorkerLoginIds extends AsyncTask<String, String, String> {
    private static String TAG = "FetchWorkerLoginIds";
    public AsyncResponse delegate = null;
    Context context;
    String message;
    private ProgressDialog progressDialog;
    View view;
    String username, password;

    public FetchWorkerLoginIds(Context context, View view, String username, String password) {
        this.context = context;
        this.view = view;
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
        Log.i(TAG, "doInBackground: view: " + view);
    }

    @Override
    protected String doInBackground(String... params) {

        BufferedReader reader = null;

        String data = "";
        try {
            //String link = "http://192.168.0.102:8080/ImageTesting/fetchWorkerLoginIds.php";
            SetURL setURL = new SetURL(context);
            String link = setURL.FetchWorkerLoginIdsForAttendance;

            //String link = SetURL.FetchWorkerLoginIdsForAttendance;
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

           /* JSONObject jsonObject = new JSONObject(sb.toString());
            message = jsonObject.getString("message");*/

            Log.i(TAG, "doInBackground: message: " + message);
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
        delegate.processFinish(s, view);
        Log.i(TAG, "onPostExecute: JSON: " + s);
    }

    public interface AsyncResponse {
        void processFinish(String output, View view);
    }

}
