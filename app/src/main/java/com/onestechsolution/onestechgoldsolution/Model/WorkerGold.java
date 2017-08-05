package com.onestechsolution.onestechgoldsolution.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by OnesTech on 03/08/2017.
 */

public class WorkerGold implements Parcelable{
    String billNo;
    String billBook;
    String billDate;
    String deliveryDate;
    String goldInDate;
    String goldInTime;
    String goldIn;
    String goldOut;
    String workerLoginId;
    String description;

    public WorkerGold() {
    }

    protected WorkerGold(Parcel in) {
        billNo = in.readString();
        billBook = in.readString();
        billDate = in.readString();
        deliveryDate = in.readString();
        goldInDate = in.readString();
        goldInTime = in.readString();
        goldIn = in.readString();
        goldOut = in.readString();
        workerLoginId = in.readString();
        description = in.readString();
        balance = in.readString();
    }

    public static final Creator<WorkerGold> CREATOR = new Creator<WorkerGold>() {
        @Override
        public WorkerGold createFromParcel(Parcel in) {
            return new WorkerGold(in);
        }

        @Override
        public WorkerGold[] newArray(int size) {
            return new WorkerGold[size];
        }
    };

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    String balance;

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getBillBook() {
        return billBook;
    }

    public void setBillBook(String billBook) {
        this.billBook = billBook;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getGoldInDate() {
        return goldInDate;
    }

    public void setGoldInDate(String goldInDate) {
        this.goldInDate = goldInDate;
    }

    public String getGoldInTime() {
        return goldInTime;
    }

    public void setGoldInTime(String goldInTime) {
        this.goldInTime = goldInTime;
    }

    public String getGoldIn() {
        return goldIn;
    }

    public void setGoldIn(String goldIn) {
        this.goldIn = goldIn;
    }

    public String getGoldOut() {
        return goldOut;
    }

    public void setGoldOut(String goldOut) {
        this.goldOut = goldOut;
    }

    public String getWorkerLoginId() {
        return workerLoginId;
    }

    public void setWorkerLoginId(String workerLoginId) {
        this.workerLoginId = workerLoginId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(billNo);
        dest.writeString(billBook);
        dest.writeString(billDate);
        dest.writeString(deliveryDate);
        dest.writeString(goldInDate);
        dest.writeString(goldInTime);
        dest.writeString(goldIn);
        dest.writeString(goldOut);
        dest.writeString(workerLoginId);
        dest.writeString(description);
        dest.writeString(balance);
    }
}
