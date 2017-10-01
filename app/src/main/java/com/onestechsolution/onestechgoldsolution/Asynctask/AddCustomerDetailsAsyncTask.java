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

/**
 * Created by psganesh on 01/10/17.
 */

public class AddCustomerDetailsAsyncTask extends AsyncTask<CustomerDeatils,String,String> {
    private final String TAG ="ADDCUSTOMERDETAILS";
    Context context;
    CustomerDeatils customerDeatils;
    private ProgressDialog progressDialog;


    AddCustomerDetailsAsyncTask(Context context)
    {
        this.context = context;
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
    protected String doInBackground(CustomerDeatils... data) {

        customerDeatils = data[0];



        return customerDeatils.getName();
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onPostExecute: s: " + s);

    }
}
