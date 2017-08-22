package com.logischtech.mytalentapp.Models;

import java.util.List;

/**
 * Created by Aayushi.Garg on 17-05-2017.
 */

public class AnswerResult {
    public String Message;

    public List<Answer> Data;

    public String getMessage(){
        return this.Message;
    }
    public List<Answer> getData(){
        return this.Data;
    }
}
