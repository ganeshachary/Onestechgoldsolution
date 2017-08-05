package com.onestechsolution.onestechgoldsolution.Asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.onestechsolution.onestechgoldsolution.Activity.WorkerHomeActivity;
import com.onestechsolution.onestechgoldsolution.Model.GoldInOut;
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

import static android.util.Log.i;

/**
 * Created by Admin on 6/7/2017.
 */

public class SendGoldOutData extends AsyncTask<GoldInOut, Integer, String> {
    private static String TAG = "SendGoldOutData";
    private String username, password;
    private String message;
    private ProgressDialog progressDialog;
    private Context context;

    public SendGoldOutData(Context context, String username, String password) {
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
        progressDialog.setMessage("Uploading data to server");
        progressDialog.show();

    }

    @Override
    protected String doInBackground(GoldInOut... params) {
        String line = "";
        String data = "";
        //String link = "http://192.168.0.102:8080/ImageTesting/uploadGoldOut.php";
        String link = SetURL.SendGoldOutData;

        StringBuilder sb = null;
        URL url;
        HttpURLConnection conn;
        GoldInOut goldInOut = params[0];
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
                    + "=" + URLEncoder.encode(goldInOut.getJSON(), "UTF-8");
            i(TAG, "doInBackground: json: " + goldInOut.getJSON());

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
        i(TAG, "onPostExecute: s: " + s);
        Intent intent = new Intent(context, WorkerHomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        ((Activity) context).finish();
        //context.startActivity(new Intent(context, WorkerHomeActivity.class));
    }
}


