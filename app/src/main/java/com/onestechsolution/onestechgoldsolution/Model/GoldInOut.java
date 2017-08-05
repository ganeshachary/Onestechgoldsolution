package com.onestechsolution.onestechgoldsolution.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Admin on 6/7/2017.
 */

public class GoldInOut implements Parcelable {
    public static final Creator<GoldInOut> CREATOR = new Creator<GoldInOut>() {
        @Override
        public GoldInOut createFromParcel(Parcel in) {
            return new GoldInOut(in);
        }

        @Override
        public GoldInOut[] newArray(int size) {
            return new GoldInOut[size];
        }
    };
    String billNo, billBook, date, deliveryDate, workerLoginId, goldOut, description, goldIn, goldInDate, goldInTime;

    public GoldInOut() {

    }

    public GoldInOut(String billNo, String billBook, String billDate, String deliveryDate, String goldIn, String goldOut, String goldInDate, String goldInTime, String workerLoginId, String description) {
        this.billNo = billNo;
        this.billBook = billBook;
        this.date = billDate;
        this.deliveryDate = deliveryDate;
        this.goldIn = goldIn;
        this.goldOut = goldOut;
        this.goldInDate = goldInDate;
        this.goldInTime = goldInTime;
        this.workerLoginId = workerLoginId;
        this.description = description;

    }

    protected GoldInOut(Parcel in) {
        billNo = in.readString();
        billBook = in.readString();
        date = in.readString();
        deliveryDate = in.readString();
        workerLoginId = in.readString();
        goldOut = in.readString();
        description = in.readString();
        goldIn = in.readString();
        goldInDate = in.readString();
        goldInTime = in.readString();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGoldIn() {
        return goldIn;
    }

    public void setGoldIn(String goldIn) {
        this.goldIn = goldIn;
    }

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getWorkerLoginId() {
        return workerLoginId;
    }

    public void setWorkerLoginId(String workerLoginId) {
        this.workerLoginId = workerLoginId;
    }

    public String getGoldOut() {
        return goldOut;
    }

    public void setGoldOut(String goldOut) {
        this.goldOut = goldOut;
    }

    public String getJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("bill_no", billNo);
        jsonObject.accumulate("bill_book", billBook);
        jsonObject.accumulate("bill_date", date);
        jsonObject.accumulate("delivery_date", deliveryDate);
        jsonObject.accumulate("gold_out", goldOut);
        jsonObject.accumulate("worker_login_id", workerLoginId);
        jsonObject.accumulate("description", description);

        JSONObject rootObject = new JSONObject();
        rootObject.accumulate("goldInOut", jsonObject);
        return rootObject.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(billNo);
        dest.writeString(billBook);
        dest.writeString(date);
        dest.writeString(deliveryDate);
        dest.writeString(workerLoginId);
        dest.writeString(goldOut);
        dest.writeString(description);
        dest.writeString(goldIn);
        dest.writeString(goldInTime);
        dest.writeString(goldInDate);
    }
}
