package com.onestechsolution.onestechgoldsolution.Model;

import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Admin on 6/4/2017.
 */

public class Worker {
    String name, pan, aadhar, salary, joiningDate, phone, dob, address, description, workerId;
    Uri workerPhoto;
    String imageName;

    public Worker() {

    }

    public Worker(String name, String pan, String aadhar, String salary, String joiningDate, String phone, String dob, String address, String description, String workerId, String imageName) {
        this.name = name;
        this.pan = pan;
        this.aadhar = aadhar;
        this.salary = salary;
        this.joiningDate = joiningDate;
        this.phone = phone;
        this.dob = dob;
        this.address = address;
        this.description = description;
        this.workerId = workerId;
        this.imageName = imageName;
    }

    public void getImageName() {
        if (workerPhoto == null) {
            imageName = "";
        } else {
            String fileUriName = "";
            fileUriName = workerPhoto.getPath();
            fileUriName = fileUriName.substring(fileUriName.indexOf('G'));
            fileUriName = fileUriName.substring(fileUriName.indexOf('/') + 1);
            imageName = fileUriName;
        }
    }

    public String getImageNameForList() {
        return imageName;
    }

    public String getJSON() throws JSONException {
        getImageName();
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("name", name);
        jsonObject.accumulate("pan", pan);
        jsonObject.accumulate("aadhar", aadhar);
        jsonObject.accumulate("salary", salary);
        jsonObject.accumulate("joiningDate", joiningDate);
        jsonObject.accumulate("phone", phone);
        jsonObject.accumulate("dob", dob);
        jsonObject.accumulate("address", address);
        jsonObject.accumulate("description", description);
        jsonObject.accumulate("workerId", workerId);
        jsonObject.accumulate("imageName", imageName);

        JSONObject rootObject = new JSONObject();
        rootObject.accumulate("worker", jsonObject);
        return rootObject.toString();
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Uri getWorkerPhoto() {
        return workerPhoto;
    }

    public void setWorkerPhoto(Uri workerPhoto) {
        this.workerPhoto = workerPhoto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }


    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
