package com.onestechsolution.onestechgoldsolution.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by OnesTech on 03/08/2017.
 */

public class GoldInOutReport implements Parcelable{

    String totalWorkers, totalGoldOut, totalGoldIn, totalBalance;
    ArrayList<WorkerGold> workerGolds;

    public GoldInOutReport() {
    }

    protected GoldInOutReport(Parcel in) {
        totalWorkers = in.readString();
        totalGoldOut = in.readString();
        totalGoldIn = in.readString();
        totalBalance = in.readString();
        workerGolds = in.createTypedArrayList(WorkerGold.CREATOR);
    }

    public static final Creator<GoldInOutReport> CREATOR = new Creator<GoldInOutReport>() {
        @Override
        public GoldInOutReport createFromParcel(Parcel in) {
            return new GoldInOutReport(in);
        }

        @Override
        public GoldInOutReport[] newArray(int size) {
            return new GoldInOutReport[size];
        }
    };

    public String getTotalWorkers() {
        return totalWorkers;
    }

    public void setTotalWorkers(String totalWorkers) {
        this.totalWorkers = totalWorkers;
    }

    public String getTotalGoldOut() {
        return totalGoldOut;
    }

    public void setTotalGoldOut(String totalGoldOut) {
        this.totalGoldOut = totalGoldOut;
    }

    public String getTotalGoldIn() {
        return totalGoldIn;
    }

    public void setTotalGoldIn(String totalGoldIn) {
        this.totalGoldIn = totalGoldIn;
    }

    public String getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(String totalBalance) {
        this.totalBalance = totalBalance;
    }

    public ArrayList<WorkerGold> getWorkerGolds() {
        return workerGolds;
    }

    public void setWorkerGolds(ArrayList<WorkerGold> workerGolds) {
        this.workerGolds = workerGolds;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(totalWorkers);
        dest.writeString(totalGoldOut);
        dest.writeString(totalGoldIn);
        dest.writeString(totalBalance);
        dest.writeTypedList(workerGolds);
    }
}
