package com.onestechsolution.onestechgoldsolution.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.onestechsolution.onestechgoldsolution.Asynctask.SendGoldInData;
import com.onestechsolution.onestechgoldsolution.Model.GoldInOut;
import com.onestechsolution.onestechgoldsolution.R;
import com.onestechsolution.onestechgoldsolution.Utilities.Utility;

import java.util.Date;

import static com.onestechsolution.onestechgoldsolution.R.id.et_GoldOut_GoldOutActivity;
import static com.onestechsolution.onestechgoldsolution.R.id.tv_BillBook_GoldInActivity;
import static com.onestechsolution.onestechgoldsolution.R.id.tv_BillNo_GoldInActivity;
import static com.onestechsolution.onestechgoldsolution.R.id.tv_Date_GoldInActivity;
import static com.onestechsolution.onestechgoldsolution.R.id.tv_DeliveryDate_GoldInActivity;
import static com.onestechsolution.onestechgoldsolution.R.id.tv_Description_GoldInActivity;
import static com.onestechsolution.onestechgoldsolution.R.id.tv_GoldOut_GoldInActivity;
import static com.onestechsolution.onestechgoldsolution.R.id.tv_WorkerId_GoldInActivity;

public class GoldInActivity extends AppCompatActivity {
    private static final String TAG = "GoldInActivity";
    GoldInOut goldInOut;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gold_in);
        username = Utility.getPreferences(this, "username");
        password = Utility.getPreferences(this, "password");

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            goldInOut = bundle.getParcelable("GoldInOut");
            //Log.i(TAG, "onCreate: goldInOut.BillNo: "+goldInOut.getBillNo());
            ((TextView) findViewById(tv_BillNo_GoldInActivity)).setText(goldInOut.getBillNo());
            ((TextView) findViewById(tv_BillBook_GoldInActivity)).setText(goldInOut.getBillBook());
            ((TextView) findViewById(tv_Date_GoldInActivity)).setText(goldInOut.getDate());
            ((TextView) findViewById(tv_DeliveryDate_GoldInActivity)).setText(goldInOut.getDeliveryDate());
            ((TextView) findViewById(tv_WorkerId_GoldInActivity)).setText(goldInOut.getWorkerLoginId());
            ((TextView) findViewById(tv_Description_GoldInActivity)).setText(goldInOut.getDescription());
            ((TextView) findViewById(tv_GoldOut_GoldInActivity)).setText(goldInOut.getGoldOut());

            if(!goldInOut.getGoldIn().equals("0.000")) {
                ((LinearLayout) findViewById(R.id.ll_GoldInDateTime_GoldInActivity)).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.tv_GoldInDateTime_GoldInActivity)).setText(goldInOut.getGoldInDate()+" "+goldInOut.getGoldInTime());

                ((EditText) findViewById(R.id.et_GoldIn_GoldInActivity)).setText(goldInOut.getGoldIn());
                ((EditText) findViewById(R.id.et_GoldIn_GoldInActivity)).setEnabled(false);

                ((Button) findViewById(R.id.btn_Upload_GoldInActivity)).setVisibility(View.GONE);

            }

        } else {
            Log.i(TAG, "onCreate: bundle is null");
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

    public void onUpload(View view) {

        Double goldIn = Double.valueOf(((EditText) findViewById(R.id.et_GoldIn_GoldInActivity)).getText().toString());
        Double goldOut = Double.valueOf(((TextView) findViewById(R.id.tv_GoldOut_GoldInActivity)).getText().toString());


        if(goldIn.equals("")) {
            Toast.makeText(this, "Please enter the value for Gold In", Toast.LENGTH_SHORT).show();
        } else if(goldIn.compareTo(goldOut)>0) {
            Toast.makeText(this, "Gold In cannot be greater than Gold out", Toast.LENGTH_SHORT).show();
        } else {

            if (Utility.isNetworkAvailable(this)) {
                new AlertDialog.Builder(this)
                        .setTitle("Are you sure?")
                        .setMessage("All the details will be uploaded")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String goldIn = ((EditText) findViewById(R.id.et_GoldIn_GoldInActivity)).getText().toString();
                                String goldInDate = Utility.getDate();
                                String goldInTime = Utility.getTime();
                                if (goldIn != null && !goldIn.isEmpty()) {
                                    goldInOut.setGoldIn(goldIn);
                                    goldInOut.setGoldInDate(goldInDate);
                                    goldInOut.setGoldInTime(goldInTime);
                                    //Log.i(TAG, "onClick: Utility.getDateFromString: "+Utility.getDateFromString(goldInOut.getDate()));
                                    new SendGoldInData(GoldInActivity.this, username, password).execute(goldInOut.getGoldIn(), goldInOut.getGoldInDate(), goldInOut.getGoldInTime(), goldInOut.getBillNo(), goldInOut.getDate());
                                } else {
                                    Toast.makeText(getApplication(), "Please enter the value for Gold In", Toast.LENGTH_SHORT).show();
                                }
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
