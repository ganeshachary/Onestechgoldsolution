package com.onestechsolution.onestechgoldsolution.Activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.onestechsolution.onestechgoldsolution.Asynctask.SendGoldOutData;
import com.onestechsolution.onestechgoldsolution.Model.GoldInOut;
import com.onestechsolution.onestechgoldsolution.R;
import com.onestechsolution.onestechgoldsolution.Utilities.Utility;

import java.io.DataOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class GoldOutActivity extends AppCompatActivity {
    private static final String TAG = "GoldOutActivity";
    GoldInOut goldInOutObj;
    Spinner spinner;
    ArrayList<String> workerLoginIds;
    TextView tvDate, tvDeliveryDate;
    String username, password;
    Calendar calendar = Calendar.getInstance();


    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            updateDateTextView();
        }
    };

    DatePickerDialog.OnDateSetListener deliveryDate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDeliveryDateTextView();
        }
    };
    int spnrWorkerLoginIds = R.id.spnr_WorkerLoginIds_GoldOutActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gold_out);
        username = Utility.getPreferences(this, "username");
        password = Utility.getPreferences(this, "password");
        Log.i(TAG, "onCreate: username: " + username + " password: " + password);

        workerLoginIds = getIntent().getStringArrayListExtra("workerLoginIds");
        Log.i(TAG, "onCreate: workerLoginIds: " + workerLoginIds.toString());
        tvDate = (TextView) findViewById(R.id.tv_Date_GoldOutActivity);
        tvDeliveryDate = (TextView) findViewById(R.id.tv_DeliveryDate_GoldOutActivity);
        setSpinnerData();
    }


    private void setSpinnerData() {
        setSpinnerAdapter(spnrWorkerLoginIds, workerLoginIds);
    }

    private void setSpinnerAdapter(int spinnerId, ArrayList<String> list) {
        spinner = (Spinner) findViewById(spinnerId);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        spinner.setAdapter(adapter);
    }

    public void setDate(View view) {
        if (view == findViewById(R.id.btn_Date_GoldOutActivity)) {
            new DatePickerDialog(this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        } else if (view == findViewById(R.id.btn_DeliveryDate_GoldOutActivity)) {
            new DatePickerDialog(this, deliveryDate, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_attention)
                .setTitle("Are you sure you want to go back?")
                .setMessage("All the changes will be lost")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void updateDateTextView() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.getDefault());
        tvDate.setText(simpleDateFormat.format(calendar.getTime()));
    }

    private void updateDeliveryDateTextView() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.getDefault());
        tvDeliveryDate.setText(simpleDateFormat.format(calendar.getTime()));
    }

    public void onUpload(View view) {
        Date goldOutDate=null, orderDeliveryDate = null;
        String billNumber = ((EditText) findViewById(R.id.et_BillNo_GoldOutActivity)).getText().toString();
        String billBook = ((EditText) findViewById(R.id.et_BillBook_GoldOutActivity)).getText().toString();
        String date = ((TextView) findViewById(R.id.tv_Date_GoldOutActivity)).getText().toString();
        String deliveryDate = ((TextView) findViewById(R.id.tv_DeliveryDate_GoldOutActivity)).getText().toString();
        String workerLoginId = ((Spinner) findViewById(R.id.spnr_WorkerLoginIds_GoldOutActivity)).getSelectedItem().toString();
        String description = ((EditText) findViewById(R.id.et_Description_GoldOutActivity)).getText().toString();
        String goldOut = ((EditText) findViewById(R.id.et_GoldOut_GoldOutActivity)).getText().toString();

        if(!date.isEmpty() && !date.equalsIgnoreCase("date") && !deliveryDate.isEmpty() && !deliveryDate.equalsIgnoreCase("date")) {
            Log.i(TAG, "onUpload: date: " + date + " deliveryDate: " + deliveryDate);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            try {
                goldOutDate = sdf.parse(date);
                orderDeliveryDate = sdf.parse(deliveryDate);
                Log.i(TAG, "onUpload: goldOutDate: " + sdf.format(goldOutDate) + " orderDeliveryDate: " + sdf.format(orderDeliveryDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if(billNumber.isEmpty() || billNumber.equals(null)) {
            Toast.makeText(this, "Please enter bill number", Toast.LENGTH_SHORT).show();
        } else if(billBook.isEmpty() || billBook.equals(null)) {
            Toast.makeText(this, "Please enter bill book", Toast.LENGTH_SHORT).show();
        } else if(date.isEmpty() || date.equals(null) || date.equalsIgnoreCase("date")) {
            Toast.makeText(this, "Please select gold out date", Toast.LENGTH_SHORT).show();
        } else if(deliveryDate.isEmpty() || deliveryDate.equals(null) || deliveryDate.equalsIgnoreCase("date")) {
            Toast.makeText(this, "Please select delivery date", Toast.LENGTH_SHORT).show();
        } else if(goldOut.isEmpty() || goldOut.equals(null)) {
            Toast.makeText(this, "Please enter gold out value", Toast.LENGTH_SHORT).show();
        } else if(goldOutDate.compareTo(orderDeliveryDate)>0) {
            Toast.makeText(this, "Delivery date cannot be before gold out date", Toast.LENGTH_SHORT).show();
        } else {

            goldInOutObj = new GoldInOut();
            goldInOutObj.setBillNo(billNumber);
            goldInOutObj.setBillBook(billBook);
            goldInOutObj.setDate(date);
            goldInOutObj.setDeliveryDate(deliveryDate);
            goldInOutObj.setWorkerLoginId(workerLoginId);
            goldInOutObj.setDescription(description);
            goldInOutObj.setGoldOut(goldOut);

            Log.i(TAG, "onUpload: billno: " + goldInOutObj.getBillNo() +
                    " billBook: " + goldInOutObj.getBillBook() +
                    " date: " + goldInOutObj.getDate() +
                    " delivery date: " + goldInOutObj.getDeliveryDate() +
                    " workerId: " + goldInOutObj.getWorkerLoginId() +
                    " description: " + goldInOutObj.getDescription() +
                    "goldOut: " + goldInOutObj.getGoldOut());

            if (Utility.isNetworkAvailable(this)) {
                new AlertDialog.Builder(this)
                        .setTitle("Are you sure?")
                        .setMessage("All the details will be uploaded, please once check all the values before uploading")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new SendGoldOutData(GoldOutActivity.this, username, password).execute(goldInOutObj);

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();


            } else {
                Toast.makeText(this, "Network issue. Please switch on the internet.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
