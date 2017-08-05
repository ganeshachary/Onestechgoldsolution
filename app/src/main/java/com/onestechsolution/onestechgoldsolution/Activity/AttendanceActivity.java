package com.onestechsolution.onestechgoldsolution.Activity;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.onestechsolution.onestechgoldsolution.Asynctask.SendWorkerAttendanceData;
import com.onestechsolution.onestechgoldsolution.Model.Attendance;
import com.onestechsolution.onestechgoldsolution.R;
import com.onestechsolution.onestechgoldsolution.Utilities.Utility;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

public class AttendanceActivity extends AppCompatActivity {
    private static final String TAG = "AttendanceActivity";
    Button btnTimeAttendanceActivity;
    Spinner spinner;
    Attendance attendance;
    ArrayList<String> workerLoginIds;
    TextView tvTimeAttendanceActivity;
    String username, password;
    Calendar calendar = Calendar.getInstance();
    int spnrAttendance = R.id.spnr_AttendanceStatus_AttendanceActivity;
    int spnrWorkerLoginIds = R.id.spnr_WorkerLoginIds_AttendanceActivity;


    TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            String AM_PM;
            if (hourOfDay < 12) {
                AM_PM = "AM";
            } else {
                AM_PM = "PM";
            }

            tvTimeAttendanceActivity.setText(hourOfDay + ":" + minute + " " + AM_PM);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        username = Utility.getPreferences(this, "username");
        password = Utility.getPreferences(this, "password");
        workerLoginIds = getIntent().getStringArrayListExtra("workerLoginIds");
        Log.i(TAG, "onCreate: workerLoginIds: " + workerLoginIds.toString());
        btnTimeAttendanceActivity = (Button) findViewById(R.id.btn_Time_AttendanceActivity);
        tvTimeAttendanceActivity = (TextView) findViewById(R.id.tv_Time_AttendanceActivity);
        setSpinnerData();
        setDate();
    }

    private void setSpinnerData() {
        setSpinnerAdapter(spnrAttendance, R.array.attendance_status);
        setSpinnerAdapter(spnrWorkerLoginIds, workerLoginIds);
    }

    private void setSpinnerAdapter(int spinnerid, int arrayid) {

        spinner = (Spinner) findViewById(spinnerid);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                arrayid, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    public void setSpinnerAdapter(int spinnerid, ArrayList<String> list) {
        spinner = (Spinner) findViewById(spinnerid);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        spinner.setAdapter(adapter);
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

    private void setDate() {
        ((TextView) findViewById(R.id.tv_Date_AttendanceActivity)).setText(Utility.getDate());
    }

    public void showTimePickerDialog(View view) {
        new TimePickerDialog(this, time, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
    }


    public void onUpdate(View view) throws ParseException {
        String workerLoginId = ((Spinner) findViewById(R.id.spnr_WorkerLoginIds_AttendanceActivity)).getSelectedItem().toString();
        String date = ((TextView) findViewById(R.id.tv_Date_AttendanceActivity)).getText().toString();
        String timeIn = ((TextView) findViewById(R.id.tv_Time_AttendanceActivity)).getText().toString();
        String reason = ((EditText) findViewById(R.id.et_Reason_AttendanceActivity)).getText().toString();
        String status = ((Spinner) findViewById(R.id.spnr_AttendanceStatus_AttendanceActivity)).getSelectedItem().toString();
        Log.i(TAG, "onUpdate: workerLoginId: " + workerLoginId + " date: " + date + " time: " + timeIn + " reason: " + reason + " status: " + status);

        if (workerLoginId.isEmpty() || workerLoginId.equals(null)) {
            Toast.makeText(this, "Workerlogin Id cannot be empty. Please contact support.", Toast.LENGTH_SHORT).show();
        } else if (date.isEmpty() || date.equals(null)) {
            Toast.makeText(this, "Date field cannot be empty. Please contact support.", Toast.LENGTH_SHORT).show();
        }else if (timeIn.isEmpty() || timeIn.equals(null)) {
            Toast.makeText(this, "Please select the In time", Toast.LENGTH_SHORT).show();
        } else {


            attendance = new Attendance();
            attendance.setWorkerLoginId(workerLoginId);
            attendance.setDate(date);
            attendance.setTimeIn(timeIn);
            attendance.setReason(reason);
            attendance.setStatus(status);


            if (Utility.isNetworkAvailable(this)) {
                new AlertDialog.Builder(this)
                        .setTitle("Are you sure?")
                        .setMessage("All the details will be uploaded, please once check all the values before uploading")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new SendWorkerAttendanceData(AttendanceActivity.this, username, password).execute(attendance);

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
