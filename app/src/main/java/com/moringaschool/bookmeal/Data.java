
package com.moringaschool.bookmeal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Data implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("date_joined")
    @Expose
    private String dateJoined;
    @SerializedName("user_image")
    @Expose
    private String userImage;
    @SerializedName("first_name")
    @Expose
    private Object firstName;
    @SerializedName("other_name")
    @Expose
    private Object otherName;
    @SerializedName("tokens")
    @Expose
    private Tokens tokens;
    @SerializedName("is_staff")
    @Expose
    private Boolean isStaff;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param firstName
     * @param userImage
     * @param isStaff
     * @param dateJoined
     * @param otherName
     * @param tokens
     * @param id
     * @param email
     * @param username
     */
    public Data(String id, String email, String username, String dateJoined, String userImage, Object firstName, Object otherName, Tokens tokens, Boolean isStaff) {
        super();
        this.id = id;
        this.email = email;
        this.username = username;
        this.dateJoined = dateJoined;
        this.userImage = userImage;
        this.firstName = firstName;
        this.otherName = otherName;
        this.tokens = tokens;
        this.isStaff = isStaff;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(String dateJoined) {
        this.dateJoined = dateJoined;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public Object getFirstName() {
        return firstName;
    }

    public void setFirstName(Object firstName) {
        this.firstName = firstName;
    }

    public Object getOtherName() {
        return otherName;
    }

    public void setOtherName(Object otherName) {
        this.otherName = otherName;
    }

    public Tokens getTokens() {
        return tokens;
    }

    public void setTokens(Tokens tokens) {
        this.tokens = tokens;
    }

    public Boolean getIsStaff() {
        return isStaff;
    }

    public void setIsStaff(Boolean isStaff) {
        this.isStaff = isStaff;
    }

}
