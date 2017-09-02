package com.onestechsolution.onestechgoldsolution.Activity;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.onestechsolution.onestechgoldsolution.Asynctask.LoginCheck;
import com.onestechsolution.onestechgoldsolution.R;
import com.onestechsolution.onestechgoldsolution.Utilities.Utility;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = (EditText) findViewById(R.id.et_username_LoginActivity);
        etPassword = (EditText) findViewById(R.id.et_password_LoginActivity);
        etUsername.setText(Utility.getPreferences(this, "username"));
        etPassword.setText(Utility.getPreferences(this, "password"));
        Utility.setPreferences(this, "environment","PROD");
        Log.i(TAG, "onCreate: username: " + Utility.getPreferences(this, "username") + " password: " + Utility.getPreferences(this, "password") + " role: " + Utility.getPreferences(this, "role"));
    }

    public void validateValues() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        if (!username.equals(null) && !username.isEmpty() && !password.equals(null) && !password.isEmpty()) {
            new LoginCheck(this).execute(username, password);
        } else {
            Toast.makeText(this, "Username & password cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to close this activity?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void openHomeActivity(View view) {
        if (Utility.isNetworkAvailable(this)) {
            validateValues();
        } else {
            Toast.makeText(this, "Network issue. Please switch on the internet", Toast.LENGTH_SHORT).show();
        }

    }


}
