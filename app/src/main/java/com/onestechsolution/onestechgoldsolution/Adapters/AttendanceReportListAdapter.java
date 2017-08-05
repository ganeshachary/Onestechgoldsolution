package com.onestechsolution.onestechgoldsolution.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.onestechsolution.onestechgoldsolution.Model.Attendance;
import com.onestechsolution.onestechgoldsolution.Model.WorkerGold;
import com.onestechsolution.onestechgoldsolution.R;

import java.util.ArrayList;

/**
 * Created by OnesTech on 04/08/2017.
 */

public class AttendanceReportListAdapter extends BaseAdapter implements View.OnClickListener{
    private static LayoutInflater inflater = null;
    private static String TAG = "AttendanRepListAdapter";
    //private NewLoan newLoan;
    private ArrayList<Attendance> attendanceArrayList;
    //private ArrayList<NewLoan> workerArrayList;
    private Context context;

    public AttendanceReportListAdapter(Context context, ArrayList<Attendance> attendanceArrayList) {
        this.context = context;
        this.attendanceArrayList = attendanceArrayList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (attendanceArrayList.size() <= 0)
            return 1;
        return attendanceArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null) {
            vi = inflater.inflate(R.layout.attendancelistview, null);
        }
        TextView tvDate = (TextView) vi.findViewById(R.id.tv_Date_AttendanceReportList);
        TextView tvStatus = (TextView) vi.findViewById(R.id.tv_Status_AttendanceReportList);
        TextView tvTimeIn = (TextView) vi.findViewById(R.id.tv_TimeIn_AttendanceReportList);
        TextView tvTimeOut = (TextView) vi.findViewById(R.id.tv_TimeOut_AttendanceReportList);
        TextView tvReason = (TextView) vi.findViewById(R.id.tv_Reason_AttendanceReportList);

        Attendance dataitem = attendanceArrayList.get(position);
        Log.i(TAG, "getView: timeIn "+dataitem.getTimeIn());
        if(dataitem!=null) {
            tvDate.setText(dataitem.getDate());
            tvStatus.setText(dataitem.getStatus());
            tvTimeIn.setText(dataitem.getTimeIn());
            tvTimeOut.setText(dataitem.getTimeOut());
            tvReason.setText(dataitem.getReason());

            Log.i(TAG, "getView: Reason: "+dataitem.getReason());
        } else {
            Log.i(TAG, "getView: dataitem is null");
        }
        // Setting all values in listview



        return vi;
    }



    @Override
    public void onClick(View v) {
    }

}
