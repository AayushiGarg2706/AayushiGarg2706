package com.logischtech.mytalentapp.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Aayushi.Garg on 04-04-2017.
 */

public class ExamAPIResponse implements Serializable {
    public String Message;

    public List<ExamResult> Data;

    public String getMessage()
    {
        return this.Message;
    }
    public List<ExamResult> getData()
    {
        return this.Data;
    }
}
