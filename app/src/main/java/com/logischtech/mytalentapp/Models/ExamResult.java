package com.logischtech.mytalentapp.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Aayushi.Garg on 04-04-2017.
 */

public class ExamResult implements Serializable {
    @SerializedName("ExpiresOn")
    public String ExpiresOn;

    @SerializedName("ResultTraitID")
    public int ResultTraitID;

    @SerializedName("Sessionid")

    public int Sessionid;

    @SerializedName("TestId")
    public int TestID;

    @SerializedName("TestStatus")
    public String TestStatus;

    @SerializedName("TraitType")
    public String TraitType;

    @SerializedName("TraitTypeID")
    public int TraitTypeID;

    @SerializedName("VoucherID")
    public int VoucherID;
    @SerializedName("VoucherNo")
    public String VoucherNo;


    public String getExpiresOn() {
        return this.ExpiresOn;
    }
    public int getResultTraitId(){
        return this.ResultTraitID;

    }
    public int getSessionId(){
        return this.Sessionid;
    }
    public int getTestId(){
        return this.TestID;
    }
    public String getTestStatus(){
        return this.TestStatus;
    }
    public String getTraitType(){
        return this.TraitType;
    }
    public int getVoucherId(){
        return this.VoucherID;

    }
    public int getTraitTypeId(){
        return this.TraitTypeID;
    }
    public String getVoucherNo(){
        return this.VoucherNo;
    }

}
