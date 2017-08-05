package com.onestechsolution.onestechgoldsolution.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.onestechsolution.onestechgoldsolution.R;
import com.onestechsolution.onestechgoldsolution.Utilities.Utility;

public class LoanHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);
    }

    public void openNewLoanActivity(View view) {
        if (Utility.isNetworkAvailable(this)) {
            Intent intent = new Intent(this, NewLoanActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Network issue. Please switch on the internet.", Toast.LENGTH_SHORT).show();
        }

    }

    public void openExistingLoanActivity(View view) {
        if (Utility.isNetworkAvailable(this)) {
            Intent intent = new Intent(this, LoanListActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Network issue. Please switch on the internet.", Toast.LENGTH_SHORT).show();
        }
    }
}
