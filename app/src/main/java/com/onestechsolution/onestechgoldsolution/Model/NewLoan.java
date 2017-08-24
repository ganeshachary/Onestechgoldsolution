package com.onestechsolution.onestechgoldsolution.Model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by OnesTech on 21/05/2017.
 */

public class NewLoan implements Parcelable{

    public static final Creator<NewLoan> CREATOR = new Creator<NewLoan>() {
        @Override
        public NewLoan createFromParcel(Parcel in) {
            return new NewLoan(in);
        }

        @Override
        public NewLoan[] newArray(int size) {
            return new NewLoan[size];
        }
    };
    private static String TAG = "NewLoan";
    public String custPhotoUri;
    private String loanid, customerName, phone, amount, percentage, description, typesOfItems, address;
    private Uri imageUris[] = new Uri[9];
    private String imageNames[] = new String[9];
    private String itemType[] = new String[8];
    private String itemCount[] = new String[8];
    private String itemWeight[] = new String[8];
    private String itemImage;
    private String loanDate, loanTime;
    private String currentGoldRate;
    private String grossWeight, netWeight;
    private String partyCode, partyReferenceNumber;
    private String penaltyPayable, penaltyPercentage, penaltyAmount;
    private String emiPayable, emiNumberOfMonthsPending, emiAmount,emiNumberOfMonthsPaid;
    private String paymentDate;
    private String principlePayable, principleAmount;
    private String status, profitLossStatus;
    private String lastEmiPaidDate;
    private String renewalDate;
    private String pan, aadhar, loanRate, loanValue;

    public NewLoan() {

    }


    public NewLoan(String custName, String phone, String custPhotoUri, String date,
                   String time, String amount, String loanUniqueId, String interest,
                   String grossWeight, String noOfItems, String netWeight, JSONArray items, String description,
                   String status, String profitLossStatus, String lastEmiPaidDate, String currentGoldRate,
                   String partyCode, String renewalDate, String party_ref_number, String penalty_payable,
                   String penalty_percentage, String emi_payable, String emi_months_pending, String principle_payable) throws JSONException {
        this.customerName = custName;
        this.phone = phone;
        this.custPhotoUri = custPhotoUri;
        this.loanDate = date;
        this.loanTime = time;
        this.amount = amount;
        this.loanid = loanUniqueId;
        this.percentage = interest;
        this.typesOfItems = noOfItems;
        this.grossWeight = grossWeight;
        this.netWeight = netWeight;
        this.status = status;
        this.currentGoldRate = currentGoldRate;
        this.partyCode = partyCode;
        this.profitLossStatus = profitLossStatus;
        this.lastEmiPaidDate = lastEmiPaidDate;
        this.renewalDate = renewalDate;
        this.partyReferenceNumber = party_ref_number;
        this.penaltyPayable = penalty_payable;
        this.penaltyPercentage = penalty_percentage;
        this.emiPayable = emi_payable;
        this.emiNumberOfMonthsPending = emi_months_pending;
        this.principlePayable = principle_payable;

        //"items":[{"type":"Necklace","count":"2","weight":"54","item_photo":"http:181048.jpg"},

        for (int i = 0; i < items.length(); i++) {
            JSONObject jsonObject = items.getJSONObject(i);
            this.itemType[i] = jsonObject.getString("type");
            Log.i(TAG, "NewLoan: itemType["+i+"]"+this.itemType[i]);
            this.itemCount[i] = jsonObject.getString("count");
            Log.i(TAG, "NewLoan: itemCount["+i+"]"+this.itemCount[i]);
            this.itemWeight[i] = jsonObject.getString("weight");
            Log.i(TAG, "NewLoan: itemWeight["+i+"]"+itemWeight[i]);
            this.imageNames[i] = jsonObject.getString("item_photo");
        }

        this.description = description;
    }

    protected NewLoan(Parcel in) {
        custPhotoUri = in.readString();
        loanid = in.readString();
        customerName = in.readString();
        phone = in.readString();
        amount = in.readString();
        percentage = in.readString();
        description = in.readString();
        typesOfItems = in.readString();
        imageUris = in.createTypedArray(Uri.CREATOR);
        imageNames = in.createStringArray();
        itemType = in.createStringArray();
        itemCount = in.createStringArray();
        itemWeight = in.createStringArray();
        itemImage = in.readString();
        loanDate = in.readString();
        loanTime = in.readString();
        currentGoldRate = in.readString();
        grossWeight = in.readString();
        netWeight = in.readString();
        partyCode = in.readString();
        partyReferenceNumber = in.readString();
        penaltyPayable = in.readString();
        penaltyPercentage = in.readString();
        penaltyAmount = in.readString();
        emiPayable = in.readString();
        emiNumberOfMonthsPending = in.readString();
        emiAmount = in.readString();
        principlePayable = in.readString();
        principleAmount = in.readString();
        status = in.readString();
        profitLossStatus = in.readString();
        lastEmiPaidDate = in.readString();
        renewalDate = in.readString();

        penaltyPayable = in.readString();
        penaltyPercentage = in.readString();
        emiPayable = in.readString();
        emiNumberOfMonthsPending = in.readString();
        principlePayable = in.readString();
    }

    public void setLoanUrisPosition(int position, Uri uri) {
        imageUris[position] = uri;
    }

    public String getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(String netWeight) {
        this.netWeight = netWeight;
    }

    public String getGrossWeight() {
        /*double grossWeight=0;
        for (int i = 0; i < itemWeight.length; i++) {
            grossWeight+=Double.parseDouble( itemWeight[i]);
        }
        return grossWeight;*/
        return grossWeight;
    }

    public void setGrossWeight(String grossWeight) {
        this.grossWeight = grossWeight;
    }

    public void getImageNames()
    {
        String fileUriName = "";
        for (int i = 0; i < imageUris.length; i++) {

            if (imageUris[i] == null) {

                imageNames[i] = "";
            } else {
                fileUriName = imageUris[i].getPath();
                //Log.i(TAG, "getImageNames: imageUris[i].getPath(): "+fileUriName);


                //fileUriName = fileUriName.substring(fileUriName.indexOf('G'));
                //Log.i(TAG, "getImageNames: fileUriName.substring(fileUriName.indexOf('G')): "+fileUriName);


                //fileUriName = fileUriName.substring(fileUriName.indexOf('/') + 1);
                //Log.i(TAG, "getImageNames: fileUriName.substring(fileUriName.indexOf('/')+1): "+fileUriName);

                fileUriName = fileUriName.substring(fileUriName.lastIndexOf("/")+1);
                Log.i(TAG, "getImageNames: fileUriName: "+fileUriName);
                imageNames[i] = fileUriName;
            }
        }

    }

    public String getJSON() throws JSONException {
        getImageNames();
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("loanId", loanid);
        jsonObject.accumulate("customerName", customerName);
        //jsonObject.accumulate("cust_photo", custPhotoUri);
        jsonObject.accumulate("aadhaar_card",aadhar);
        jsonObject.accumulate("address", address);
        jsonObject.accumulate("pan_card", pan);
        jsonObject.accumulate("phone", phone);
        jsonObject.accumulate("amount", amount);
        jsonObject.accumulate("percentage", percentage);
        jsonObject.accumulate("description", description);
        jsonObject.accumulate("types_of_items", typesOfItems);
        jsonObject.accumulate("itemType", new JSONArray(Arrays.asList(itemType)));
        jsonObject.accumulate("itemCount", new JSONArray(Arrays.asList(itemCount)));
        jsonObject.accumulate("itemWeight", new JSONArray(Arrays.asList(itemWeight)));
        jsonObject.accumulate("grossWeight", grossWeight);
        jsonObject.accumulate("loanDate", loanDate);
        jsonObject.accumulate("loanTime", loanTime);
        jsonObject.accumulate("netWeight", netWeight);
        jsonObject.accumulate("currentGoldRate", currentGoldRate);
        jsonObject.accumulate("imageNames", new JSONArray(Arrays.asList(imageNames)));

        JSONObject rootObject = new JSONObject();
        rootObject.accumulate("loan", jsonObject);

        return rootObject.toString();

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(String loanRate) {
        this.loanRate = loanRate;
    }

    public String getLoanValue() {
        return loanValue;
    }

    public void setLoanValue(String loanValue) {
        this.loanValue = loanValue;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getEmiPayable() {
        return emiPayable;
    }

    public void setEmiPayable(String emiPayable) {
        this.emiPayable = emiPayable;
    }

    public String getEmiNumberOfMonthsPending() {
        return emiNumberOfMonthsPending;
    }

    public void setEmiNumberOfMonthsPending(String emiNumberOfMonthsPending) {
        this.emiNumberOfMonthsPending = emiNumberOfMonthsPending;
    }

    public String getEmiNumberOfMonthsPaid() {
        return emiNumberOfMonthsPaid;
    }

    public void setEmiNumberOfMonthsPaid(String emiNumberOfMonthsPaid) {
        this.emiNumberOfMonthsPaid = emiNumberOfMonthsPaid;
    }

    public String getPenaltyPayable() {
        return penaltyPayable;
    }

    public void setPenaltyPayable(String penaltyPayable) {
        this.penaltyPayable = penaltyPayable;
    }

    public String getPenalty_percentage() {
        return penaltyPercentage;
    }

    public void setPenalty_percentage(String penalty_percentage) {
        this.penaltyPercentage = penalty_percentage;
    }

    public String getEmi_payable() {
        return emiPayable;
    }

    public void setEmi_payable(String emi_payable) {
        this.emiPayable = emi_payable;
    }

    public String getEmi_months_pending() {
        return emiNumberOfMonthsPending;
    }

    public void setEmi_months_pending(String emi_months_pending) {
        this.emiNumberOfMonthsPending = emi_months_pending;
    }

    public String getPrinciple_payable() {
        return principlePayable;
    }

    public void setPrinciple_payable(String principle_payable) {
        this.principlePayable = principle_payable;
    }

    public String getPartyCode() {
        return partyCode;
    }

    public void setPartyCode(String partyCode) {
        this.partyCode = partyCode;
    }

    public String getPartyReferenceNumber() {
        return partyReferenceNumber;
    }

    public void setPartyReferenceNumber(String partyReferenceNumber) {
        this.partyReferenceNumber = partyReferenceNumber;
    }

    public String getPenalty() {
        return penaltyPayable;
    }

    public void setPenalty(String penalty) {
        this.penaltyPayable = penalty;
    }

    public String getPenaltyPercentage() {
        return penaltyPercentage;
    }

    public void setPenaltyPercentage(String penaltyPercentage) {
        this.penaltyPercentage = penaltyPercentage;
    }

    public String getCurrentGoldRate() {
        return currentGoldRate;
    }

    public void setCurrentGoldRate(String currentGoldRate) {
        this.currentGoldRate = currentGoldRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfitLossStatus() {
        return profitLossStatus;
    }

    public void setProfitLossStatus(String profitLossStatus) {
        this.profitLossStatus = profitLossStatus;
    }

    public String getLastEmiPaidDate() {
        return lastEmiPaidDate;
    }

    public void setLastEmiPaidDate(String lastEmiPaidDate) {
        this.lastEmiPaidDate = lastEmiPaidDate;
    }

    public String getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(String renewalDate) {
        this.renewalDate = renewalDate;
    }

    public String getPenaltyAmount() {
        return penaltyAmount;
    }

    public void setPenaltyAmount(String penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
    }

    public String getEmi() {
        return emiPayable;
    }

    public void setEmi(String emi) {
        this.emiPayable = emi;
    }

    public String getEmiNumberOfMonths() {
        return emiNumberOfMonthsPending;
    }

    public void setEmiNumberOfMonths(String emiNumberOfMonths) {
        this.emiNumberOfMonthsPending = emiNumberOfMonths;
    }

    public String getEmiAmount() {
        return emiAmount;
    }

    public void setEmiAmount(String emiAmount) {
        this.emiAmount = emiAmount;
    }

    public String getPrinciplePayable() {
        return principlePayable;
    }

    public void setPrinciplePayable(String principlePayable) {
        this.principlePayable = principlePayable;
    }

    public String getPrincipleAmount() {
        return principleAmount;
    }

    public void setPrincipleAmount(String principleAmount) {
        this.principleAmount = principleAmount;
    }

    public String getCustPhotoUri() {
        return custPhotoUri;
    }

    public void setCustPhotoUri(String custPhotoUri) {
        this.custPhotoUri = custPhotoUri;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public void setImageNames(String[] imageNames) {
        this.imageNames = imageNames;
    }

    public String getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(String loanTime) {
        this.loanTime = loanTime;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public Uri[] getImageUris() {
        return imageUris;
    }

    public String getLoanid() {
        return loanid;
    }

    public void setLoanid(String loanid) {
        this.loanid = loanid;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypesOfItems() {
        return typesOfItems;
    }

    public void setTypesOfItems(String typesOfItems) {
        this.typesOfItems = typesOfItems;
    }

    public String[] getItemType() {
        return itemType;
    }

    public void setItemType(String[] itemType) {
        this.itemType = itemType;
    }

    public String[] getItemCount() {
        return itemCount;
    }

    public void setItemCount(String[] itemCount) {
        this.itemCount = itemCount;
    }

    public String[] getItemWeight() {
        return itemWeight;
    }

    public void setItemWeight(String[] itemWeight) {
        this.itemWeight = itemWeight;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(custPhotoUri);
        dest.writeString(loanid);
        dest.writeString(customerName);
        dest.writeString(phone);
        dest.writeString(amount);
        dest.writeString(percentage);
        dest.writeString(description);
        dest.writeString(typesOfItems);
        dest.writeTypedArray(imageUris, flags);
        dest.writeStringArray(imageNames);
        dest.writeStringArray(itemType);
        dest.writeStringArray(itemCount);
        dest.writeStringArray(itemWeight);
        dest.writeString(itemImage);
        dest.writeString(loanDate);
        dest.writeString(loanTime);
        dest.writeString(currentGoldRate);
        dest.writeString(grossWeight);
        dest.writeString(netWeight);
        dest.writeString(partyCode);
        dest.writeString(partyReferenceNumber);
        dest.writeString(penaltyPayable);
        dest.writeString(penaltyPercentage);
        dest.writeString(penaltyAmount);
        dest.writeString(emiPayable);
        dest.writeString(emiNumberOfMonthsPending);
        dest.writeString(emiAmount);
        dest.writeString(principlePayable);
        dest.writeString(principleAmount);
        dest.writeString(status);
        dest.writeString(profitLossStatus);
        dest.writeString(lastEmiPaidDate);
        dest.writeString(renewalDate);

        dest.writeString(penaltyPayable);
        dest.writeString(penaltyPercentage);
        dest.writeString(emiPayable);
        dest.writeString(emiNumberOfMonthsPending);
        dest.writeString(principlePayable);
    }
}
