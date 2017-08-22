package com.logischtech.mytalentapp.Models;

import java.util.List;

/**
 * Created by Aayushi.Garg on 08-05-2017.
 */

public class QuestionAPIResponse {
    public String Message;

    public List<Question> Data;

    public String getMessage(){
        return this.Message;
    }
    public List<Question> getData(){
        return this.Data;
    }

}
