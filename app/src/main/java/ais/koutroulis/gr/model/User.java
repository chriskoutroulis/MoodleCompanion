package ais.koutroulis.gr.model;

/**
 * Created by c0nfr0ntier on 11/7/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("department")
    @Expose
    private String department;
    @SerializedName("firstaccess")
    @Expose
    private int firstaccess;
    @SerializedName("lastaccess")
    @Expose
    private int lastaccess;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("descriptionformat")
    @Expose
    private int descriptionformat;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("profileimageurlsmall")
    @Expose
    private String profileimageurlsmall;
    @SerializedName("profileimageurl")
    @Expose
    private String profileimageurl;

    /**
     * @return The id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return The fullname
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * @param fullname The fullname
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @param department The department
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * @return The firstaccess
     */
    public int getFirstaccess() {
        return firstaccess;
    }

    /**
     * @param firstaccess The firstaccess
     */
    public void setFirstaccess(int firstaccess) {
        this.firstaccess = firstaccess;
    }

    /**
     * @return The lastaccess
     */
    public int getLastaccess() {
        return lastaccess;
    }

    /**
     * @param lastaccess The lastaccess
     */
    public void setLastaccess(int lastaccess) {
        this.lastaccess = lastaccess;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The descriptionformat
     */
    public int getDescriptionformat() {
        return descriptionformat;
    }

    /**
     * @param descriptionformat The descriptionformat
     */
    public void setDescriptionformat(int descriptionformat) {
        this.descriptionformat = descriptionformat;
    }

    /**
     * @return The city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return The profileimageurlsmall
     */
    public String getProfileimageurlsmall() {
        return profileimageurlsmall;
    }

    /**
     * @param profileimageurlsmall The profileimageurlsmall
     */
    public void setProfileimageurlsmall(String profileimageurlsmall) {
        this.profileimageurlsmall = profileimageurlsmall;
    }

    /**
     * @return The profileimageurl
     */
    public String getProfileimageurl() {
        return profileimageurl;
    }

    /**
     * @param profileimageurl The profileimageurl
     */
    public void setProfileimageurl(String profileimageurl) {
        this.profileimageurl = profileimageurl;
    }
}
