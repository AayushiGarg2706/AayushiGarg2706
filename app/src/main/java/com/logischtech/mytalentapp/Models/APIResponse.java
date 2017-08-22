package com.logischtech.mytalentapp.Models;

import java.util.List;

/**
 * Created by Aayushi.Garg on 04-04-2017.
 */

public class APIResponse {
    public String Message;

    public List<LoginResult> Data;

    public String getMessage(){
        return this.Message;
    }
    public List<LoginResult> getData(){
        return this.Data;
    }
}
