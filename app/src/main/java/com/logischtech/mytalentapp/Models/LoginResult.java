package com.logischtech.mytalentapp.Models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class LoginResult implements Serializable {
    @SerializedName("SessionId")
    public int SessionId;
    @SerializedName("Name")
    public String Name;

    @SerializedName("Gender")
    public String Gender;

    @SerializedName("DOB")
    public String DOB;

    @SerializedName("EmailID")
    public String EmailID;

    @SerializedName("Phone")
    public String Phone;

    @SerializedName("Address")
    public String Address;

    @SerializedName("OTPStatus")
    public String OTPStatus;


    public int getSessionID() {
        return this.SessionId;
    }
    public String getname(){
        return this.Name;

    }
    public String getGender(){
        return this.Gender;
    }
    public String getDateOfBirth(){
        return this.DOB;
    }
    public String getemail(){
        return this.EmailID;
    }
    public String getphone(){
        return this.Phone;
    }
    public String getOTPStatus(){
        return this.OTPStatus;
    }

    public String getAddress(){
        return this.Address;

    }

}
