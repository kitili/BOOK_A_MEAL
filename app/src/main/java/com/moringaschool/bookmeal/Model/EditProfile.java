
package com.moringaschool.bookmeal.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class EditProfile {


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private Data data;

    /**
     * No args constructor for use in serialization
     * 
     */
    public EditProfile() {
    }

    /**
     * 
     * @param data
     * @param status
     */
    public EditProfile(String status, Data data) {
        super();
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
