package com.onestechsolution.onestechgoldsolution.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.onestechsolution.onestechgoldsolution.Activity.AttendanceEntryReportActivity;

import java.util.ArrayList;

/**
 * Created by OnesTech on 04/08/2017.
 */

public class AttendanceReport implements Parcelable {
    String totalPresent, totalAbsent;

    ArrayList<Attendance> attendanceArrayList;

    public AttendanceReport() {

    }

    protected AttendanceReport(Parcel in) {
        totalPresent = in.readString();
        totalAbsent = in.readString();
    }

    public static final Creator<AttendanceReport> CREATOR = new Creator<AttendanceReport>() {
        @Override
        public AttendanceReport createFromParcel(Parcel in) {
            return new AttendanceReport(in);
        }

        @Override
        public AttendanceReport[] newArray(int size) {
            return new AttendanceReport[size];
        }
    };

    public String getTotalPresent() {
        return totalPresent;
    }

    public void setTotalPresent(String totalPresent) {
        this.totalPresent = totalPresent;
    }

    public String getTotalAbsent() {
        return totalAbsent;
    }

    public void setTotalAbsent(String totalAbsent) {
        this.totalAbsent = totalAbsent;
    }

    public ArrayList<Attendance> getAttendanceArrayList() {
        return attendanceArrayList;
    }

    public void setAttendanceArrayList(ArrayList<Attendance> attendanceArrayList) {
        this.attendanceArrayList = attendanceArrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(totalPresent);
        dest.writeString(totalAbsent);
    }
}
