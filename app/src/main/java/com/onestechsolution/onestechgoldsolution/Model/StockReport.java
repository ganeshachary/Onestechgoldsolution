package com.onestechsolution.onestechgoldsolution.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by OnesTech on 28/07/2017.
 */

public class StockReport implements Parcelable{

    String inStockItems, soldItems, totalGrossWeight, totalNetWeight, totalStoneWeight, totalNoOfStones, totalRings, totalChains, totalStuds,
            totalNecklaces, totalEarrings, totalBracelets, totalKalamanis, totalEarchains, totalDollars, totalHaars, totalBangles, totalOthers;

    public StockReport() {
    }

    protected StockReport(Parcel in) {
        inStockItems = in.readString();
        soldItems = in.readString();
        totalGrossWeight = in.readString();
        totalNetWeight = in.readString();
        totalStoneWeight = in.readString();
        totalNoOfStones = in.readString();
        totalRings = in.readString();
        totalChains = in.readString();
        totalStuds = in.readString();
        totalNecklaces = in.readString();
        totalEarrings = in.readString();
        totalBracelets = in.readString();
        totalKalamanis = in.readString();
        totalEarchains = in.readString();
        totalDollars = in.readString();
        totalHaars = in.readString();
        totalBangles = in.readString();
        totalOthers = in.readString();
    }

    public static final Creator<StockReport> CREATOR = new Creator<StockReport>() {
        @Override
        public StockReport createFromParcel(Parcel in) {
            return new StockReport(in);
        }

        @Override
        public StockReport[] newArray(int size) {
            return new StockReport[size];
        }
    };

    public String getInStockItems() {
        return inStockItems;
    }

    public void setInStockItems(String inStockItems) {
        this.inStockItems = inStockItems;
    }

    public String getSoldItems() {
        return soldItems;
    }

    public void setSoldItems(String soldItems) {
        this.soldItems = soldItems;
    }

    public String getTotalGrossWeight() {
        return totalGrossWeight;
    }

    public void setTotalGrossWeight(String totalGrossWeight) {
        this.totalGrossWeight = totalGrossWeight;
    }

    public String getTotalNetWeight() {
        return totalNetWeight;
    }

    public void setTotalNetWeight(String totalNetWeight) {
        this.totalNetWeight = totalNetWeight;
    }

    public String getTotalStoneWeight() {
        return totalStoneWeight;
    }

    public void setTotalStoneWeight(String totalStoneWeight) {
        this.totalStoneWeight = totalStoneWeight;
    }

    public String getTotalNoOfStones() {
        return totalNoOfStones;
    }

    public void setTotalNoOfStones(String totalNoOfStones) {
        this.totalNoOfStones = totalNoOfStones;
    }

    public String getTotalRings() {
        return totalRings;
    }

    public void setTotalRings(String totalRings) {
        this.totalRings = totalRings;
    }

    public String getTotalChains() {
        return totalChains;
    }

    public void setTotalChains(String totalChains) {
        this.totalChains = totalChains;
    }

    public String getTotalStuds() {
        return totalStuds;
    }

    public void setTotalStuds(String totalStuds) {
        this.totalStuds = totalStuds;
    }

    public String getTotalNecklaces() {
        return totalNecklaces;
    }

    public void setTotalNecklaces(String totalNecklaces) {
        this.totalNecklaces = totalNecklaces;
    }

    public String getTotalEarrings() {
        return totalEarrings;
    }

    public void setTotalEarrings(String totalEarrings) {
        this.totalEarrings = totalEarrings;
    }

    public String getTotalBracelets() {
        return totalBracelets;
    }

    public void setTotalBracelets(String totalBracelets) {
        this.totalBracelets = totalBracelets;
    }

    public String getTotalKalamanis() {
        return totalKalamanis;
    }

    public void setTotalKalamanis(String totalKalamanis) {
        this.totalKalamanis = totalKalamanis;
    }

    public String getTotalEarchains() {
        return totalEarchains;
    }

    public void setTotalEarchains(String totalEarchains) {
        this.totalEarchains = totalEarchains;
    }

    public String getTotalDollars() {
        return totalDollars;
    }

    public void setTotalDollars(String totalDollars) {
        this.totalDollars = totalDollars;
    }

    public String getTotalHaars() {
        return totalHaars;
    }

    public void setTotalHaars(String totalHaars) {
        this.totalHaars = totalHaars;
    }

    public String getTotalBangles() {
        return totalBangles;
    }

    public void setTotalBangles(String totalBangles) {
        this.totalBangles = totalBangles;
    }

    public String getTotalOthers() {
        return totalOthers;
    }

    public void setTotalOthers(String totalOthers) {
        this.totalOthers = totalOthers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(inStockItems);
        dest.writeString(soldItems);
        dest.writeString(totalGrossWeight);
        dest.writeString(totalNetWeight);
        dest.writeString(totalStoneWeight);
        dest.writeString(totalNoOfStones);
        dest.writeString(totalRings);
        dest.writeString(totalChains);
        dest.writeString(totalStuds);
        dest.writeString(totalNecklaces);
        dest.writeString(totalEarrings);
        dest.writeString(totalBracelets);
        dest.writeString(totalKalamanis);
        dest.writeString(totalEarchains);
        dest.writeString(totalDollars);
        dest.writeString(totalHaars);
        dest.writeString(totalBangles);
        dest.writeString(totalOthers);
    }
}
