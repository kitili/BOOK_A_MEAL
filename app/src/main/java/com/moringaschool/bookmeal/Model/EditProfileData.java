
package com.moringaschool.bookmeal.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class EditProfileData {

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
    private String firstName;
    @SerializedName("other_name")
    @Expose
    private String otherName;

    /**
     * No args constructor for use in serialization
     * 
     */
    public EditProfileData() {
    }

    /**
     * 
     * @param firstName
     * @param userImage
     * @param dateJoined
     * @param otherName
     * @param id
     * @param email
     * @param username
     */
    public EditProfileData(String id, String email, String username, String dateJoined, String userImage, String firstName, String otherName) {
        super();
        this.id = id;
        this.email = email;
        this.username = username;
        this.dateJoined = dateJoined;
        this.userImage = userImage;
        this.firstName = firstName;
        this.otherName = otherName;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

}
