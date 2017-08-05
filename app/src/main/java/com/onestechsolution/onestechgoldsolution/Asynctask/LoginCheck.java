package com.onestechsolution.onestechgoldsolution.Asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.onestechsolution.onestechgoldsolution.Activity.HomeActivity;
import com.onestechsolution.onestechgoldsolution.Utilities.SetURL;
import com.onestechsolution.onestechgoldsolution.Utilities.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by OnesTech on 13/07/2017.
 */

public class LoginCheck extends AsyncTask<String, String, String> {
    public static final String TAG = "LoginCheck";
    String role;
    String username;
    String password;
    private Context context;
    private ProgressDialog progressDialog;


    public LoginCheck(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Please Wait..");
        progressDialog.setMessage("Logging in");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {

        //String link = "http://onestechsolution.com/ImageTesting/loginCheck.php";
        String link = SetURL.LoginCheck;
        try {
            String data = "";

            username = params[0];
            password = params[1];

            Log.i(TAG, "doInBackground: username: " + username + " password: " + password);

            URL url = new URL(link);
            BufferedReader reader;
            StringBuilder sb = null;
            String line;
            URLConnection conn;

            boolean status = true;

            conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());


            data = URLEncoder.encode("username", "UTF-8") + "=" +
                    URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
                    URLEncoder.encode(password, "UTF-8");
            wr.write(data);
            wr.flush();

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            sb = new StringBuilder();
            line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            //Log.i(TAG, "doInBackground: sb " + sb);
            //Log.i(TAG, "doInBackground: Response: " + sb.toString());

            return sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (params.length == 0 || params[0] == null) {
            return null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        Log.i(TAG, "onPostExecute: Message: " + s);
        //Toast.makeText(context, "Message from server: " + s, Toast.LENGTH_SHORT).show();
        parseJSON(s);

    }

    private void parseJSON(String jsonResponse) {
           /* username = sharedpreferences.getString("username", "");
            password = sharedpreferences.getString("password", "");
            role = sharedpreferences.getString("role", "");*/
        Log.i(TAG, "parseJSON: sharedPreference username: " + username + " password: " + password + " role: " + role);
        Log.i(TAG, "parseJSON: " + jsonResponse);
        try {
            String message = "";
            String role;
            if (jsonResponse != null && !jsonResponse.isEmpty()) {
                JSONObject jsonObject = new JSONObject(jsonResponse);

                boolean error = jsonObject.getBoolean("status");
                message = jsonObject.getString("message");
                JSONObject roleObject = jsonObject.getJSONObject("data");
                role = roleObject.getString("role");
                if (!error) {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "parseJSON: error: " + error + " message: " + message + " role: " + role);
                    Log.i(TAG, "parseJSON: Before setting value into sharedpreferences username: " + username + " password: " + password);

                    Utility.setPreferences(context, "username", username);
                    Utility.setPreferences(context, "password", password);
                    Utility.setPreferences(context, "role", role);


                    Intent intent = new Intent(context, HomeActivity.class);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.i(TAG, "parseJSON: Issue with server");
                Toast.makeText(context, "There is some issue with server. Please contact support.", Toast.LENGTH_LONG).show();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}


