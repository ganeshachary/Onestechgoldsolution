package com.onestechsolution.onestechgoldsolution.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import com.bumptech.glide.util.Util;
import com.onestechsolution.onestechgoldsolution.Model.CustomerDeatils;
import com.onestechsolution.onestechgoldsolution.R;
import com.onestechsolution.onestechgoldsolution.Utilities.Utility;

public class AddNewCustomerDetails extends AppCompatActivity {

    EditText etName , etMobile , etEmail , etState , etCity , etPincode ,etComments;
    EditText[] editTextsArray;

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
        editTextsArray = new EditText[7];
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
