package com.logischtech.mytalentapp.Models;

/**
 * Created by Aayushi.Garg on 17-05-2017.
 */

public class Answer {
    public String Answer;
    public String QuestionID;
    public  String StudentAction;
    public String TestID;
    public String TraitID;

    public String getAnswer(){
        return this.Answer;
    }
    public String getQuestionID(){
        return this.QuestionID;
    }

    public String gettestID(){
        return this.TestID;
    }


    public String getStudentAction(){
        return this.StudentAction;
    }

    public String getTraitID(){
        return this.TraitID;
    }
}

