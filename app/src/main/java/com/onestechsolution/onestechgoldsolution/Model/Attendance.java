package com.onestechsolution.onestechgoldsolution.Model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Admin on 6/7/2017.
 */

public class Attendance {
    String workerLoginId, timeIn, timeOut, date, status, reason;

    public String getJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("worker_login_id", workerLoginId);
        jsonObject.accumulate("time_in", timeIn);
        jsonObject.accumulate("date", date);
        jsonObject.accumulate("status", status);
        jsonObject.accumulate("reason", reason);

        JSONObject rootObject = new JSONObject();
        rootObject.accumulate("attendance", jsonObject);
        return rootObject.toString();
    }

    public String getWorkerLoginId() {
        return workerLoginId;
    }

    public void setWorkerLoginId(String workerLoginId) {
        this.workerLoginId = workerLoginId;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
