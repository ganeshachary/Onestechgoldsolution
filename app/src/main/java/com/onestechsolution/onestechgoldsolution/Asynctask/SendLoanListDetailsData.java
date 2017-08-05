package com.onestechsolution.onestechgoldsolution.Asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.onestechsolution.onestechgoldsolution.Activity.LoanHomeActivity;
import com.onestechsolution.onestechgoldsolution.Model.NewLoan;
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
 * Created by OnesTech on 27/06/2017.
 */

public class SendLoanListDetailsData extends AsyncTask<NewLoan, Integer, String> {
    private static String TAG = "SendGoldOutData";
    String username, password;
    String message;
    private Context context;
    private ProgressDialog progressDialog;

    public SendLoanListDetailsData(Context context, String username, String password) {
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
        progressDialog.setMessage("Uploading data to server");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(NewLoan... params) {
        String line = "";
        String data = "";

        String link = SetURL.SendLoanListDetailsData;
        //String link = "";
        StringBuilder sb = null;
        URL url;
        HttpURLConnection conn;
        NewLoan newLoan = params[0];
        Log.i(TAG, "doInBackground: penaltyAmount: "+newLoan.getPenaltyAmount());
        Log.i(TAG, "doInBackground: emiAmount: "+newLoan.getEmiAmount());
        Log.i(TAG, "doInBackground: principleAmount: "+newLoan.getPrincipleAmount());
        Log.i(TAG, "doInBackground: emiNoOfMonthsPaid: "+newLoan.getEmiNumberOfMonthsPaid());
        Log.i(TAG, "doInBackground: paymentDate: "+newLoan.getPaymentDate());
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
            data += "&" + URLEncoder.encode("loanUniqueId", "UTF-8")
                    + "=" + URLEncoder.encode(newLoan.getLoanid(), "UTF-8");
            i(TAG, "doInBackground: loanId: " + newLoan.getLoanid());

            data += "&" + URLEncoder.encode("penaltyAmount", "UTF-8")
                    + "=" + URLEncoder.encode(newLoan.getPenaltyAmount(), "UTF-8");
            i(TAG, "doInBackground: penaltyAmount: " + newLoan.getPenaltyAmount());

            data += "&" + URLEncoder.encode("emiAmount", "UTF-8")
                    + "=" + URLEncoder.encode(newLoan.getEmiAmount(), "UTF-8");
            i(TAG, "doInBackground: emiAmount: " + newLoan.getEmiAmount());

            data += "&" + URLEncoder.encode("principleAmount", "UTF-8")
                    + "=" + URLEncoder.encode(newLoan.getPrincipleAmount(), "UTF-8");
            i(TAG, "doInBackground: principleAmount: " + newLoan.getPrincipleAmount());

            data += "&" + URLEncoder.encode("emiNoOfMonthsPaid", "UTF-8")
                    + "=" + URLEncoder.encode(newLoan.getEmiNumberOfMonthsPaid(), "UTF-8");
            i(TAG, "doInBackground: emiNumberOfMonthsPaid: " + newLoan.getEmiNumberOfMonthsPaid());

            data += "&" + URLEncoder.encode("paymentDate", "UTF-8")
                    + "=" + URLEncoder.encode(newLoan.getPaymentDate(), "UTF-8");
            i(TAG, "doInBackground: paymentDate: " + newLoan.getPaymentDate());

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
        Log.i(TAG, "onPostExecute: Response "+s);
        Intent intent = new Intent(context, LoanHomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        ((Activity) context).finish();
        //context.startActivity(new Intent(context, LoanHomeActivity.class));
    }
}
