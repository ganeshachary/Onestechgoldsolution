package com.onestechsolution.onestechgoldsolution.Activity;

import android.app.DatePickerDialog;
import java.util.Calendar;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import com.bumptech.glide.util.Util;
import com.onestechsolution.onestechgoldsolution.Model.CustomerDeatils;
import com.onestechsolution.onestechgoldsolution.R;
import com.onestechsolution.onestechgoldsolution.Utilities.Utility;

public class AddNewCustomerDetails extends AppCompatActivity {

    EditText etName , etMobile , etEmail , etState , etCity , etPincode ,etComments ,etBirthDate ,etAnniversaryDate;
    EditText[] editTextsArray;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private DatePickerDialog.OnDateSetListener mDateSetLisenerBirthDate;
    private DatePickerDialog.OnDateSetListener mDateSetLisenerAnniversaryDate;





    CustomerDeatils customerDeatils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_customer_details);
        setUpUIReferrence();

        customerDeatils = new CustomerDeatils();
        customerDeatils.setGender("Male");
        customerDeatils.setAnniversaydate("NA");
        customerDeatils.setBirthdate("NA");


    }

    void setUpUIReferrence()
    {
        editTextsArray = new EditText[9];
        etName = (EditText) findViewById(R.id.et_Name_AddCustomerDetailsActivity);
        editTextsArray[0] = etName;

        etMobile = (EditText) findViewById(R.id.et_Mobile_AddCustomerDetailsActivity);
        editTextsArray[1] = etMobile;

        etEmail =(EditText) findViewById(R.id.et_Email_AddCustomerDetailsActivity);
        editTextsArray[2] = etEmail;

        etCity = (EditText) findViewById(R.id.et_City_AddCustomerDetailsActivity);
        editTextsArray[3] = etCity;

        etState =(EditText) findViewById(R.id.et_State_AddCustomerDetailsActivity);
        editTextsArray[4] = etState;

        etPincode = (EditText) findViewById(R.id.et_Pincode_AddCustomerDetailsActivity);
        editTextsArray[5] = etPincode;

        etComments = (EditText) findViewById(R.id.et_Comments_AddCustomerDetailsActivity);
        editTextsArray[6] = etComments;

        etBirthDate = (EditText) findViewById(R.id.et_BirthDate_AddCustomerDetailsActivity);
        editTextsArray[7] = etBirthDate;

        etAnniversaryDate =(EditText) findViewById(R.id.et_AnniversayDate_AddCustomerDetailsActivity);
        editTextsArray[8] = etAnniversaryDate;


        etBirthDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Calendar calender = Calendar.getInstance();
                int year = calender.get(Calendar.YEAR);
                int month = calender.get(Calendar.MONTH);
                int day = calender.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddNewCustomerDetails.this,android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth,mDateSetLisenerBirthDate
                ,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        mDateSetLisenerBirthDate = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month ,int day) {
                month = month + 1;
                String Date = day +"/"+month+"/"+year;
                etBirthDate.setError(null);
                etBirthDate.setText(Date);
            }
        };

        etAnniversaryDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Calendar calender = Calendar.getInstance();
                int year = calender.get(Calendar.YEAR);
                int month = calender.get(Calendar.MONTH);
                int day = calender.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddNewCustomerDetails.this,android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth,mDateSetLisenerAnniversaryDate
                        ,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetLisenerAnniversaryDate = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month ,int day) {
                month = month + 1;
                String Date = day +"/"+month+"/"+year;
                etAnniversaryDate.setError(null);
                etAnniversaryDate.setText(Date);

            }
        };




    }




    void setData()
    {
        customerDeatils.setName(etName.getText().toString());
        customerDeatils.setMobilenumber(etMobile.getText().toString());
        customerDeatils.setEmail(etEmail.getText().toString());
        customerDeatils.setState(etState.getText().toString());
        customerDeatils.setCity(etCity.getText().toString());
        customerDeatils.setPincode(etPincode.getText().toString());
        customerDeatils.setComments(etComments.getText().toString());


    }


    boolean validateInput()
    {
        Boolean vaildationState = true;
        String data ="";
        for (EditText editText: editTextsArray
             ) {
            if((data = editText.getText().toString().trim()).isEmpty())
            {

                Log.i("CHECK DATA",data);
                editText.setError("Please fill this details");
                vaildationState = false;
            }
            if(editText.getId() ==  R.id.et_Mobile_AddCustomerDetailsActivity)
            {
                if((data = editText.getText().toString().trim()).length() < 10)
                {
                    editText.setError("Please enter a valid mobile number");
                    vaildationState = false;
                }

            }
            if(editText.getId() ==  R.id.et_Email_AddCustomerDetailsActivity)
            {
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(editText.getText().toString().trim()).matches())
                {
                    editText.setError("Please enter a valid email id");
                    vaildationState = false;
                }

            }

        }

        return vaildationState;
    }



    public void onSave(View view) {
        setData();
        if(validateInput())
        {
            Utility.toastMessage(this,"Success your data is saved");
        }
    }


    public void setGender(View view) {
        switch (view.getId())
        {
            case R.id.rb_Male_AddCustomerDetailsActivity :
                customerDeatils.setGender("Male");
                break;
            case R.id.rb_FeMale_AddCustomerDetailsActivity:
                customerDeatils.setGender("Female");
                break;
            default:
                customerDeatils.setGender("Male");

        }

    }
}
