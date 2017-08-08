package com.onestechsolution.onestechgoldsolution.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.onestechsolution.onestechgoldsolution.Adapters.AttendanceReportListAdapter;
import com.onestechsolution.onestechgoldsolution.Adapters.LoanListViewAdapter;
import com.onestechsolution.onestechgoldsolution.Asynctask.FetchAttendanceReportDetails;
import com.onestechsolution.onestechgoldsolution.Asynctask.FetchGoldInOutReportDetails;
import com.onestechsolution.onestechgoldsolution.Model.Attendance;
import com.onestechsolution.onestechgoldsolution.Model.AttendanceReport;
import com.onestechsolution.onestechgoldsolution.Model.GoldInOutReport;
import com.onestechsolution.onestechgoldsolution.Model.WorkerGold;
import com.onestechsolution.onestechgoldsolution.R;
import com.onestechsolution.onestechgoldsolution.Utilities.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AttendanceReportActivity extends AppCompatActivity implements FetchAttendanceReportDetails.AttendanceReportInterface {
    private static final String TAG = "AttendanceReport";
    ArrayList<Attendance> attendanceArrayList;
    AttendanceReportListAdapter attendanceReportListAdapter;
    ArrayList<String> workerLoginIds;
    AttendanceReport attendanceReport;
    Attendance attendance;
    ListView listView;
    TextView tvFromDate, tvToDate, tvEmptyList, tvPresent, tvAbsent;
    Spinner spinner;
    String username, password;
    Calendar calendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener fromDatePicker = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            updateDateTextView(findViewById(R.id.btn_FromDate_AttendanceReportActivity));
        }
    };

    DatePickerDialog.OnDateSetListener toDatePicker = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            updateDateTextView(findViewById(R.id.btn_ToDate_AttendanceReportActivity));
        }
    };
    int spnrWorkerLoginIds = R.id.spnr_WorkerLoginIds_AttendanceReportActivity;
    int btnFromDate = R.id.btn_FromDate_AttendanceReportActivity;
    int btnToDate = R.id.btn_ToDate_AttendanceReportActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_report);
        username = Utility.getPreferences(this, "username");
        password = Utility.getPreferences(this, "password");
        Log.i(TAG, "onCreate: Username : "+username+" Password: "+password);

        listView = (ListView) findViewById(R.id.lv_AttendanceReportList_AttendanceReportListActivity);

        workerLoginIds = getIntent().getStringArrayListExtra("workerLoginIds");
        Log.i(TAG, "onCreate: workerLoginIds: " + workerLoginIds.toString());

        attendanceArrayList = new ArrayList<>();

        tvFromDate = (TextView) findViewById(R.id.tv_FromDate_AttendanceReportActivity);
        tvToDate = (TextView) findViewById(R.id.tv_ToDate_AttendanceReportActivity);
        tvEmptyList = (TextView) findViewById(R.id.tv_EmptyList_AttendanceReportListActivity);
        tvPresent = (TextView) findViewById(R.id.tv_Present_AttendanceReportActivity);
        tvAbsent = (TextView) findViewById(R.id.tv_Absent_AttendanceReportActivity);
        setSpinnerData();
        ///
    }

    private void setSpinnerData() {
        setSpinnerAdapter(spnrWorkerLoginIds, workerLoginIds);
    }

    private void setSpinnerAdapter(int spinnerId, ArrayList<String> list) {
        spinner = (Spinner) findViewById(spinnerId);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        spinner.setAdapter(adapter);
    }

    private void updateDateTextView(View view) {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.getDefault());
        if(view==findViewById(btnFromDate)){
            tvFromDate.setText(simpleDateFormat.format(calendar.getTime()));
        } else if(view==findViewById(btnToDate)){
            tvToDate.setText(simpleDateFormat.format(calendar.getTime()));
        } else {
            Log.i(TAG, "updateDateTextView: No such button");
        }

    }


    public void openAttendanceEntryActivity(View view) {

        if(Utility.isNetworkAvailable(this)) {
            Intent intent = new Intent(this, AttendanceEntryReportActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Network issue. Please switch on the internet", Toast.LENGTH_SHORT).show();
        }

    }


    public void setDate(View view) {
        if (view == findViewById(R.id.btn_FromDate_AttendanceReportActivity)) {
            new DatePickerDialog(this, fromDatePicker, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        } else if (view == findViewById(R.id.btn_ToDate_AttendanceReportActivity)) {
            new DatePickerDialog(this, toDatePicker, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    }

    public void getReport(View view) {
        Date fromDate = null, toDate = null;
        String workerLoginId = ((Spinner) findViewById(spnrWorkerLoginIds)).getSelectedItem().toString();

        if(!tvFromDate.getText().toString().isEmpty() && !tvFromDate.getText().toString().equalsIgnoreCase("date") && !tvToDate.getText().toString().isEmpty() && !tvToDate.getText().toString().equalsIgnoreCase("date")) {
            Log.i(TAG, "onUpload: FromDate: " + tvFromDate.getText().toString() + " ToDate: " + tvToDate.getText().toString());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            try {
                fromDate = sdf.parse(tvFromDate.getText().toString());
                toDate = sdf.parse(tvToDate.getText().toString());
                Log.i(TAG, "onUpload: FromDate: " + sdf.format(fromDate) + " ToDate: " + sdf.format(toDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if(tvFromDate.getText().toString().isEmpty() || tvFromDate.getText().toString().equals(null) || tvFromDate.getText().toString().equalsIgnoreCase("date")) {
            Toast.makeText(this, "Please select From date field", Toast.LENGTH_SHORT).show();
        } else if(tvToDate.getText().toString().isEmpty() || tvToDate.getText().toString().equals(null) || tvToDate.getText().toString().equalsIgnoreCase("date")) {
            Toast.makeText(this, "Please select To date field", Toast.LENGTH_SHORT).show();
        } else if(fromDate.compareTo(toDate)>0) {
            Toast.makeText(this, "To date cannot be greater than From date", Toast.LENGTH_SHORT).show();
        } else {
            if(Utility.isNetworkAvailable(this)) {
                FetchAttendanceReportDetails asyncTask = new FetchAttendanceReportDetails(this, username, password);
                asyncTask.attendanceInterface = this;
                asyncTask.execute(workerLoginId, tvFromDate.getText().toString(), tvToDate.getText().toString());
            } else {
                Toast.makeText(this, "Network issue. Please switch on the internet", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void parseAttendanceReportJSON(String myJSON) {
        attendanceArrayList.clear();
        try {
            Log.i(TAG, "parseAttendanceReportJSON: myJSON: "+myJSON);
            if(myJSON !=null && !myJSON.isEmpty()) {
                JSONObject jsonRoot = new JSONObject(myJSON);
                if(!jsonRoot.getBoolean("status")) {
                    JSONArray jsonArray = jsonRoot.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        attendanceReport = new AttendanceReport();
                        String totalPresent = jsonObject.get("total_present").toString();
                        Log.i(TAG, "parseGoldInOutJSON: TotalPresent: "+totalPresent);
                        attendanceReport.setTotalPresent(jsonObject.get("total_present").toString());
                        attendanceReport.setTotalAbsent(jsonObject.get("total_absent").toString());

                        JSONArray datewiseReportArray = jsonObject.getJSONArray("datewise_attendance");
                        for (int j = 0; j < datewiseReportArray.length(); j++) {
                            JSONObject datewiseAttendanceObject = datewiseReportArray.getJSONObject(j);
                            attendance = new Attendance();
                            attendance.setTimeIn(datewiseAttendanceObject.getString("time_in"));
                            Log.i(TAG, "parseGoldInOutJSON: Time In : "+datewiseAttendanceObject.getString("time_in"));

                            attendance.setTimeOut(datewiseAttendanceObject.getString("time_out"));
                            attendance.setDate(datewiseAttendanceObject.getString("date"));
                            attendance.setStatus(datewiseAttendanceObject.getString("status"));
                            attendance.setReason(datewiseAttendanceObject.getString("reason"));
                            attendanceArrayList.add(attendance);
                            Log.i(TAG, "parseAttendanceReportJSON: attendance.getTimeIn() "+attendance.getTimeIn());
                        }

                        attendanceReport.setAttendanceArrayList(attendanceArrayList);

                    }
                    if(attendanceReport.getTotalPresent()!=null && !attendanceReport.getTotalPresent().equalsIgnoreCase("")) {
                        tvPresent.setText(attendanceReport.getTotalPresent());
                    }
                    if(attendanceReport.getTotalAbsent()!=null && !attendanceReport.getTotalAbsent().equalsIgnoreCase("")) {
                        tvAbsent.setText(attendanceReport.getTotalAbsent());
                    }
                    Log.i(TAG, "parseGoldInOutJSON: message: "+jsonRoot.get("message"));
                } else {
                    Toast.makeText(this, jsonRoot.getString("message"), Toast.LENGTH_SHORT).show();
                }

                if (attendanceArrayList != null && attendanceArrayList.size() > 0) {
                    attendanceReportListAdapter = new AttendanceReportListAdapter(this, attendanceArrayList);
                    tvEmptyList.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    listView.setAdapter(attendanceReportListAdapter);
                } else {
                    /*loanListItems.clear();
                    loanListViewAdapter.notifyDataSetChanged();*/
                    listView.setAdapter(null);
                    listView.setVisibility(View.GONE);
                    tvEmptyList.setVisibility(View.VISIBLE);
                    tvEmptyList.setText("No records found for the above mentioned dates. Please select another dates.");
                    //tvEmptyList.setHighlightColor(Color.RED);
                    Log.i(TAG, "showList: No such record found");
                    //Toast.makeText(this, "No such record found", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "JSON response is empty", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void attendanceProcessFinish(String output) {
        parseAttendanceReportJSON(output);

    }


}
