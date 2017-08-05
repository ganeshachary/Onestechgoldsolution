package com.onestechsolution.onestechgoldsolution.Asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.onestechsolution.onestechgoldsolution.Activity.WorkerHomeActivity;
import com.onestechsolution.onestechgoldsolution.Model.Attendance;
import com.onestechsolution.onestechgoldsolution.Utilities.SetURL;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Admin on 6/7/2017.
 */

public class SendWorkerAttendanceData extends AsyncTask<Attendance, Integer, String> {
    private static String TAG = "SendWorkerAttendance";
    String message;
    private String username, password;
    private Context context;
    private ProgressDialog progressDialog;


    public SendWorkerAttendanceData(Context context, String username, String password) {
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
        progressDialog.setTitle("Please wait..");
        progressDialog.setMessage("Uploading data to server..");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(Attendance... params) {
        String line = "";
        String data = "";
        //String link = "http://192.168.0.102:8080/ImageTesting/UploadAttendance.php";
        String link = SetURL.SendWorkerAttendanceData;
        StringBuilder sb = null;
        URL url;
        HttpURLConnection conn;
        Attendance attendance = params[0];
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
                    + "=" + URLEncoder.encode(attendance.getJSON(), "UTF-8");
            Log.i(TAG, "doInBackground: json: " + attendance.getJSON());

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


        } catch (UnsupportedEncodingException e) {
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
        Intent intent = new Intent(context, WorkerHomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        ((Activity) context).finish();
        //context.startActivity(new Intent(context, WorkerHomeActivity.class));
    }
}
