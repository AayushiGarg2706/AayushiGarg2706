package com.logischtech.mytalentapp.Models;

/**
 * Created by Aayushi.Garg on 17-04-2017.
 */

public class Question {
    public String Question;
    public String Answer;
    public String QuestionaryID;
    public  String StudentAction;
    public String TestID;
    public String TraitID;

    public String getQuestionText(){
        return this.Question;
    }
    public String getAnswer(){
        return this.Answer;
    }
    public String getQuestionaryID(){
        return this.QuestionaryID;
    }

    public String getTestID(){
        return this.TestID;
    }
    public String getTraitID(){
        return this.TraitID;
    }


    public String getStudentAction(){
        return this.StudentAction;
    }
}