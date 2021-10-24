
package com.moringaschool.bookmeal.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ProfileResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ProfileResponse() {
    }

    /**
     * 
     * @param data
     * @param status
     */
    public ProfileResponse(String status, List<Datum> data) {
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

    public List<Datum>  getData() {
        return data;
    }

    public void setData(List<Datum>  data) {
        this.data = data;
    }

}
