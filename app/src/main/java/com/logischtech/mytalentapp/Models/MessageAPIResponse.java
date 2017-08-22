package com.logischtech.mytalentapp.Models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Aayushi.Garg on 03-08-2017.
 */

public class MessageAPIResponse implements Serializable {
    public String Message;

    //public List<ExamResult> Data;

    public String getMessage()
    {
        return this.Message;
    }
    //public List<ExamResult> getData()
    //{
     //   return this.Data;
    //}
}