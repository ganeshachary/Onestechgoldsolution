package com.onestechsolution.onestechgoldsolution.Model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by psganesh on 01/10/17.
 */

public class CustomerDeatils {
    String name, mobilenumber , email , state, city , pincode ,address, comments, gender , birthdate , anniversarydate;


    public CustomerDeatils()
    {
        gender = "Male";
        anniversarydate = "0000-00-00";
        birthdate = "0000-00-00";


    }

    public String getJSON() throws JSONException {
       JSONObject jsonObject = new JSONObject();
       jsonObject.accumulate("name", name);
       jsonObject.accumulate("mobilenumber", mobilenumber);
       jsonObject.accumulate("email", email);
       jsonObject.accumulate("state", state);
       jsonObject.accumulate("city", city);
       jsonObject.accumulate("pincode", pincode);
       jsonObject.accumulate("gender", gender);
       jsonObject.accumulate("birthdate", birthdate);
       jsonObject.accumulate("anniversarydate", anniversarydate);
       jsonObject.accumulate("comments", comments);
       JSONObject rootObject = new JSONObject();
       rootObject.accumulate("customer", jsonObject);
       return rootObject.toString();
   }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getAnniversaydate() {
        return anniversarydate;
    }

    public void setAnniversaydate(String anniversaydate) {
        this.anniversarydate = anniversaydate;
    }
}
