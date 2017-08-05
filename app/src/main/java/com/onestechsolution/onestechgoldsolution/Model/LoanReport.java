package com.onestechsolution.onestechgoldsolution.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by OnesTech on 29/07/2017.
 */

public class LoanReport implements Parcelable{

    private String totalCustomer, totalLoanAmount, totalPrinciplePaid, totalPrinciplePending, totalGrossWeight, totalNetWeight, totalRings,
    totalChains, totalStuds, totalNecklaces, totalEarrings, totalBracelets, totalKalamanis, totalEarchains, totalDollars, totalHaars, totalBangles,
    totalOthers, totalPenaltyAmountPaid, totalDiscount, totalEMIPaid, totalEMIAmountPending, totalEMIMonthsPending3, totalEMIMonthsPending6, totalNewLoans, totalClosedLoan;

    public LoanReport() {

    }

    protected LoanReport(Parcel in) {
        totalCustomer = in.readString();
        totalLoanAmount = in.readString();
        totalPrinciplePaid = in.readString();
        totalPrinciplePending = in.readString();
        totalGrossWeight = in.readString();
        totalNetWeight = in.readString();
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
        totalPenaltyAmountPaid = in.readString();
        totalDiscount = in.readString();
        totalEMIPaid = in.readString();
        totalEMIAmountPending = in.readString();
        totalEMIMonthsPending3 = in.readString();
        totalEMIMonthsPending6 = in.readString();
        totalNewLoans = in.readString();
        totalClosedLoan = in.readString();
    }

    public static final Creator<LoanReport> CREATOR = new Creator<LoanReport>() {
        @Override
        public LoanReport createFromParcel(Parcel in) {
            return new LoanReport(in);
        }

        @Override
        public LoanReport[] newArray(int size) {
            return new LoanReport[size];
        }
    };

    public String getTotalCustomer() {
        return totalCustomer;
    }

    public void setTotalCustomer(String totalCustomer) {
        this.totalCustomer = totalCustomer;
    }

    public String getTotalLoanAmount() {
        return totalLoanAmount;
    }

    public void setTotalLoanAmount(String totalLoanAmount) {
        this.totalLoanAmount = totalLoanAmount;
    }

    public String getTotalPrinciplePaid() {
        return totalPrinciplePaid;
    }

    public void setTotalPrinciplePaid(String totalPrinciplePaid) {
        this.totalPrinciplePaid = totalPrinciplePaid;
    }

    public String getTotalPrinciplePending() {
        return totalPrinciplePending;
    }

    public void setTotalPrinciplePending(String totalPrinciplePending) {
        this.totalPrinciplePending = totalPrinciplePending;
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

    public String getTotalPenaltyAmountPaid() {
        return totalPenaltyAmountPaid;
    }

    public void setTotalPenaltyAmountPaid(String totalPenaltyAmountPaid) {
        this.totalPenaltyAmountPaid = totalPenaltyAmountPaid;
    }

    public String getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(String totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public String getTotalEMIPaid() {
        return totalEMIPaid;
    }

    public void setTotalEMIPaid(String totalEMIPaid) {
        this.totalEMIPaid = totalEMIPaid;
    }

    public String getTotalEMIAmountPending() {
        return totalEMIAmountPending;
    }

    public void setTotalEMIAmountPending(String totalEMIAmountPending) {
        this.totalEMIAmountPending = totalEMIAmountPending;
    }

    public String getTotalEMIMonthsPending3() {
        return totalEMIMonthsPending3;
    }

    public void setTotalEMIMonthsPending3(String totalEMIMonthsPending3) {
        this.totalEMIMonthsPending3 = totalEMIMonthsPending3;
    }

    public String getTotalEMIMonthsPending6() {
        return totalEMIMonthsPending6;
    }

    public void setTotalEMIMonthsPending6(String totalEMIMonthsPending6) {
        this.totalEMIMonthsPending6 = totalEMIMonthsPending6;
    }

    public String getTotalNewLoans() {
        return totalNewLoans;
    }

    public void setTotalNewLoans(String totalNewLoans) {
        this.totalNewLoans = totalNewLoans;
    }

    public String getTotalClosedLoan() {
        return totalClosedLoan;
    }

    public void setTotalClosedLoan(String totalClosedLoan) {
        this.totalClosedLoan = totalClosedLoan;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(totalCustomer);
        dest.writeString(totalLoanAmount);
        dest.writeString(totalPrinciplePaid);
        dest.writeString(totalPrinciplePending);
        dest.writeString(totalGrossWeight);
        dest.writeString(totalNetWeight);
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
        dest.writeString(totalPenaltyAmountPaid);
        dest.writeString(totalDiscount);
        dest.writeString(totalEMIPaid);
        dest.writeString(totalEMIAmountPending);
        dest.writeString(totalEMIMonthsPending3);
        dest.writeString(totalEMIMonthsPending6);
        dest.writeString(totalNewLoans);
        dest.writeString(totalClosedLoan);
    }
}
