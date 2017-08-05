package com.onestechsolution.onestechgoldsolution.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.onestechsolution.onestechgoldsolution.Asynctask.SendLoanListDetailsData;
import com.onestechsolution.onestechgoldsolution.Model.NewLoan;
import com.onestechsolution.onestechgoldsolution.R;
import com.onestechsolution.onestechgoldsolution.Utilities.SetURL;
import com.onestechsolution.onestechgoldsolution.Utilities.Utility;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

public class LoanListDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static String TAG = "LoanListDetails";
    ImageView ivCustPhoto, ivItemPhoto;
    int noOfItems;
    int spnrEmiNoOfMonthsPaid = R.id.spnr_emiNoOfMonthsPaid_LoanListDetailsActivity;
    String username, password;
    int linearLayout[] = {R.id.ll_Item1_LoanListDetailsActivity, R.id.ll_Item2_LoanListDetailsActivity,
            R.id.ll_Item3_LoanListDetailsActivity, R.id.ll_Item4_LoanListDetailsActivity,
            R.id.ll_Item5_LoanListDetailsActivity, R.id.ll_Item6_LoanListDetailsActivity,
            R.id.ll_Item7_LoanListDetailsActivity, R.id.ll_Item8_LoanListDetailsActivity};
    int itemType[] = {R.id.tv_Item1Type_LoanListDetailsActivity, R.id.tv_Item2Type_LoanListDetailsActivity,
            R.id.tv_Item3Type_LoanListDetailsActivity, R.id.tv_Item4Type_LoanListDetailsActivity,
            R.id.tv_Item5Type_LoanListDetailsActivity, R.id.tv_Item6Type_LoanListDetailsActivity,
            R.id.tv_Item7Type_LoanListDetailsActivity, R.id.tv_Item8Type_LoanListDetailsActivity};
    int itemWeight[] = {R.id.tv_Item1Weight_LoanListDetailsActivity, R.id.tv_Item2Weight_LoanListDetailsActivity,
            R.id.tv_Item3Weight_LoanListDetailsActivity, R.id.tv_Item4Weight_LoanListDetailsActivity,
            R.id.tv_Item5Weight_LoanListDetailsActivity, R.id.tv_Item6Weight_LoanListDetailsActivity,
            R.id.tv_Item7Weight_LoanListDetailsActivity, R.id.tv_Item8Weight_LoanListDetailsActivity};
    int itemCount[] = {R.id.tv_Item1Count_LoanListDetailsActivity, R.id.tv_Item2Count_LoanListDetailsActivity,
            R.id.tv_Item3Count_LoanListDetailsActivity, R.id.tv_Item4Count_LoanListDetailsActivity,
            R.id.tv_Item5Count_LoanListDetailsActivity, R.id.tv_Item6Count_LoanListDetailsActivity,
            R.id.tv_Item7Count_LoanListDetailsActivity, R.id.tv_Item8Count_LoanListDetailsActivity};
    private NewLoan newLoan;
    private Spinner spinner;
    int topPenaltyView = R.id.view_TopPenalty_LoanListDetailsActivity;
    int bottomPenaltyView = R.id.view_BottomPenalty_LoanListDetailsActivity;
    int topEMIView = R.id.view_TopEMIDetails_LoanListDetailsActivity;
    int bottomEMIView = R.id.view_BottomEMIDetails_LoanListDetailsActivity;
    int topPartyView = R.id.view_TopPartyDetails_LoanListDetails;
    int bottomPartyView = R.id.view_BottomPartyDetails_LoanListDetails;
    int emiHeading = R.id.tv_EMIDetailsHeading_LoanListDetailsActivity;
    int penaltyHeading = R.id.tv_PenaltyHeading_LoanListDetailsActivity;
    int llPenaltyPayable = R.id.ll_PenaltyPayable_LoanListDetailsActivity;
    int llPenaltyPercentage = R.id.ll_PenaltyPercentage_LoanListDetailsActivity;
    int llPenaltyAmount = R.id.ll_PenaltyAmount_LoanListDetailsActivity;
    int llEmiPayable = R.id.ll_EMIPayable_LoanListDetailsActivity;
    int llEmiMonthsPending = R.id.ll_EMIMonthsPending_LoanListDetailsActivity;
    int llEmiMonthsPayingFor = R.id.ll_EMIMonthsPayingFor_LoanListDetailsActivity;
    int llEmiAmount = R.id.ll_EMIAmount_LoanListDetailsActivity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_list_details);
        username = Utility.getPreferences(this, "username");
        password = Utility.getPreferences(this, "password");
        Log.i(TAG, "onCreate: username: " + username + " password: " + password);

        newLoan = getIntent().getExtras().getParcelable("newLoan");
        ivCustPhoto = ((ImageView) findViewById(R.id.iv_CustomerPhoto_LoanListDetailsActivity));

        Log.i(TAG, "onCreate: newLoan.getCustomerName: " + newLoan.getCustomerName());
        Log.i(TAG, "onCreate: newLoan.getCustPhotoUri: " + newLoan.getCustPhotoUri());
        setSpinnerData();
        setTextViewValues();



    }

    private void setSpinnerData() {

        if(!(newLoan.getEmiNumberOfMonthsPending()).isEmpty()) {
            String emiMonths[] = new String[Integer.parseInt(newLoan.getEmiNumberOfMonthsPending())+1];
            for (int i = 0; i < emiMonths.length; i++) {
                emiMonths[i]=i+"";
            }
            setSpinnerAdapter(spnrEmiNoOfMonthsPaid, emiMonths);
        }

    }

    private void setSpinnerAdapter(int spinnerId, String[] emiMonthsArray) {
        spinner = (Spinner) findViewById(spinnerId);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, emiMonthsArray);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    public void setTextViewValues() {
        Log.i(TAG, "setTextViewValues: isPenalty? :" + (Double.valueOf(newLoan.getPenalty()) > 0));
        if (Double.valueOf(newLoan.getPenalty()) > 0 || Double.valueOf(newLoan.getPenalty_percentage()) > 0) {
            Log.i(TAG, "setTextViewValues: Penalty: " + newLoan.getPenalty());
            findViewById(topPenaltyView).setVisibility(View.VISIBLE);
            findViewById(bottomPenaltyView).setVisibility(View.VISIBLE);
            findViewById(penaltyHeading).setVisibility(View.VISIBLE);
            findViewById(llPenaltyAmount).setVisibility(View.VISIBLE);
            findViewById(llPenaltyPayable).setVisibility(View.VISIBLE);
            findViewById(llPenaltyPercentage).setVisibility(View.VISIBLE);
        }

        if (Double.valueOf(newLoan.getEmi_payable()) > 0 || Double.valueOf(newLoan.getEmiNumberOfMonthsPending()) > 0) {

            findViewById(topEMIView).setVisibility(View.VISIBLE);
            findViewById(bottomEMIView).setVisibility(View.VISIBLE);
            findViewById(emiHeading).setVisibility(View.VISIBLE);
            findViewById(llEmiAmount).setVisibility(View.VISIBLE);
            findViewById(llEmiMonthsPayingFor).setVisibility(View.VISIBLE);
            findViewById(llEmiMonthsPending).setVisibility(View.VISIBLE);
            findViewById(llEmiAmount).setVisibility(View.VISIBLE);
            findViewById(llEmiPayable).setVisibility(View.VISIBLE);
        }

        Log.i(TAG, "setTextViewValues: date: " + newLoan.getLoanDate());
        ((TextView) findViewById(R.id.tv_date_LoanListDetailsActivity)).setText(newLoan.getLoanDate());
        ((TextView) findViewById(R.id.tv_time_LoanListDetailsActivity)).setText(newLoan.getLoanTime());
        ((TextView) findViewById(R.id.tv_CustomerName_LoanListDetailsActivity)).setText(newLoan.getCustomerName());
        ((TextView) findViewById(R.id.tv_LoanId_LoanListDetailsActivity)).setText(newLoan.getLoanid());
        ((TextView) findViewById(R.id.tv_Contact_LoanListDetailsActivity)).setText(newLoan.getPhone());
        ((TextView) findViewById(R.id.tv_LoanAmount_LoanListDetailsActivity)).setText(newLoan.getAmount());
        ((TextView) findViewById(R.id.tv_Description_LoanListDetailsActivity)).setText(newLoan.getDescription());
        ((TextView) findViewById(R.id.tv_Percentage_LoanListDetailsActivity)).setText(newLoan.getPercentage());
        ((TextView) findViewById(R.id.tv_GrossWeight_LoanListDetailsActivity)).setText(newLoan.getGrossWeight());
        ((TextView) findViewById(R.id.tv_NetWeight_LoanListDetailsActivity)).setText(newLoan.getNetWeight());
        ((TextView) findViewById(R.id.tv_ReferenceNumber_LoanListDetailsActivity)).setText(newLoan.getPartyReferenceNumber());
        ((TextView) findViewById(R.id.tv_PartyCode_LoanListDetailsActivity)).setText(newLoan.getPartyCode());
        ((TextView) findViewById(R.id.tv_Penalty_LoanListDetailsActivity)).setText(newLoan.getPenalty());
        ((TextView) findViewById(R.id.tv_PenaltyPercentage_LoanListDetailsActivity)).setText(newLoan.getPenaltyPercentage());
        ((TextView) findViewById(R.id.tv_EmiAmountPayable_LoanListDetailsActivity)).setText(newLoan.getEmi());
        ((TextView) findViewById(R.id.tv_EmiNumberOfMonthsPending_LoanListDetailsActivity)).setText(newLoan.getEmiNumberOfMonths());
        ((TextView) findViewById(R.id.tv_PrincipleAmountPayable_LoanListDetailsActivity)).setText(newLoan.getPrinciplePayable());


        noOfItems = Integer.parseInt(newLoan.getTypesOfItems());
        Log.i(TAG, "setTextViewValues: noOfItems: " + noOfItems);
        TextView currentTextView;
        String type[] = newLoan.getItemType();
        String count[] = newLoan.getItemCount();
        String weight[] = newLoan.getItemWeight();

        for (int i = 0; i < noOfItems; i++) {
            ((LinearLayout) findViewById(linearLayout[i])).setVisibility(View.VISIBLE);

            currentTextView = ((TextView) findViewById(itemType[i]));
            currentTextView.setText(type[i]);

            currentTextView = ((TextView) findViewById(itemWeight[i]));
            currentTextView.setText(count[i]);

            currentTextView = ((TextView) findViewById(itemCount[i]));
            currentTextView.setText(weight[i]);
            Log.i(TAG, "setTextViewValues: " + Arrays.toString(newLoan.getItemType()));

            //Log.i(TAG, "setTextViewValues: getItemCount[i]"+ Arrays.toString(newLoan.getItemCount()));
        }

        Log.i(TAG, "setTextViewValues: CustPhotoUri: " + newLoan.getCustPhotoUri());
        //new DownloadImageTask(ivCustPhoto).execute("https://qsf.ec.quoracdn.net/-3-images.new_grid.profile_pic_default_small.png-26-902da2b339fedf49.png");
        //new DownloadImageTask(ivCustPhoto).execute(SetURL.URLUploads + newLoan.getCustPhotoUri());


        Picasso.with(this)
                .load(SetURL.URLUploads + newLoan.getCustPhotoUri())
                //.placeholder(R.drawable.ic_user)   // optional
                .noPlaceholder()
                .error(R.drawable.ic_customer)      // optional
                .resize(50, 50)                        // optional
                .into(ivCustPhoto);
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
        String penaltyAmount = ((EditText) findViewById(R.id.et_PenaltyAmount_LoanListDetailsActivity)).getText().toString();
        String emiAmount = ((EditText) findViewById(R.id.et_EmiAmount_LoanListDetailsActivity)).getText().toString();
        String principleAmount = ((EditText) findViewById(R.id.et_PrincipleAmountPaid_LoanListDetailsActivity)).getText().toString();
        String emiNoOfMonthsPaid = ((Spinner) findViewById(R.id.spnr_emiNoOfMonthsPaid_LoanListDetailsActivity)).getSelectedItem().toString();

        if (findViewById(llPenaltyAmount).getVisibility() == View.VISIBLE) {
            if (penaltyAmount.equals(null) || penaltyAmount.equalsIgnoreCase("") || penaltyAmount.isEmpty()) {
                Log.i(TAG, "onUpload: Penalty amount not inserted");
                Toast.makeText(this, "Please enter the value for Penalty amount.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (Double.valueOf(penaltyAmount) > Double.valueOf(newLoan.getPenalty())) {
                Toast.makeText(this, "Penalty paid cannot be greater than penalty payable. Please check penalty amount field", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            penaltyAmount = "0";
        }

        if (findViewById(llEmiAmount).getVisibility() == View.VISIBLE) {
            if (emiAmount.equals(null) || emiAmount.equalsIgnoreCase("") || emiAmount.isEmpty()) {
                Log.i(TAG, "onUpload: EMI amount not inserted");
                Toast.makeText(this, "Please enter the value for EMI amount", Toast.LENGTH_SHORT).show();
                return;
            }
            if(((Spinner)findViewById(spnrEmiNoOfMonthsPaid)).getSelectedItem().toString().equals("0")){
                Toast.makeText(this, "Please select the value for EMI no. of months paid", Toast.LENGTH_SHORT).show();
            }
            if (Double.valueOf(emiAmount) > Double.valueOf(newLoan.getEmi_payable())) {
                Toast.makeText(this, "EMI paid cannot be greater than emi payable. Please check EMI amount field", Toast.LENGTH_SHORT).show();
                return;
            }

        } else {
            emiAmount = "0";
            emiNoOfMonthsPaid = "0";
        }

        if (principleAmount.equalsIgnoreCase("") || principleAmount.isEmpty()) {
            Log.i(TAG, "onUpload: Principle amount not inserted");
            Toast.makeText(this, "Please enter the value for Principle amount", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Double.valueOf(principleAmount) > Double.valueOf(newLoan.getPrinciple_payable())) {
            Toast.makeText(this, "Principle paid cannot be greater than principle payable. Please check the principle amount field", Toast.LENGTH_SHORT).show();
            return;
        } else {

            Log.i(TAG, "onUpload: penaltyAmount: " + penaltyAmount + " emiAmount: " + emiAmount + " PrincipleAmount: " + principleAmount + " emiPaid: " + emiNoOfMonthsPaid);

            newLoan.setPenaltyAmount(penaltyAmount);
            newLoan.setEmiAmount(emiAmount);
            newLoan.setPrincipleAmount(principleAmount);
            newLoan.setEmiNumberOfMonthsPaid(emiNoOfMonthsPaid);
            newLoan.setPaymentDate(Utility.getDate());

            if (Utility.isNetworkAvailable(this)) {

                new AlertDialog.Builder(this)
                        .setTitle("Are you sure?")
                        .setMessage("All the details will be uploaded")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new SendLoanListDetailsData(LoanListDetailsActivity.this, username, password).execute(newLoan);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

            } else {
                Toast.makeText(this, "Network issue. Please switch on the internet.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == spnrEmiNoOfMonthsPaid) {
            double emipayable = Double.parseDouble(newLoan.getEmiPayable());
            double emimonthspending = Double.parseDouble(newLoan.getEmiNumberOfMonthsPending());
            double emipermonth = emipayable/emimonthspending;
            Log.i("data", parent.getItemAtPosition(position).toString()+"");
            double months = Double.parseDouble( parent.getItemAtPosition(position).toString());
            double emi = emipermonth*months;
            ((EditText)findViewById(R.id.et_EmiAmount_LoanListDetailsActivity)).setText(emi+"");
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
