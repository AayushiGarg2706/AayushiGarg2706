package com.logischtech.mytalentapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.logischtech.mytalentapp.Helpers.InternalStorage;
import com.logischtech.mytalentapp.Models.LoginResult;

import java.io.IOException;
import java.util.Locale;

public class Choose_language extends AppCompatActivity  implements View.OnClickListener{

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Choose_language.this.setVisible(false);


        setContentView(R.layout.activity_choose_language);
        Button btneng;
        Button btnothr;

        btneng = (Button) findViewById(R.id.btneng);
        btneng.setOnClickListener(this);
        btnothr=(Button)findViewById(R.id.btnothr);
        btnothr.setOnClickListener(this);
        Object fromStorage=null;


        try {
            fromStorage= InternalStorage.readObject(getApplicationContext(), "Login_Result");
            LoginResult result = (LoginResult) fromStorage;

            if(fromStorage==null && result.getOTPStatus().equals("False")){



                Choose_language.this.setVisible(true);
                startActivity(new Intent(this, Otpscreen.class));



            }
            else if(fromStorage!=null && result.getOTPStatus().equals("False")){
                Choose_language.this.setVisible(false);


                Intent i = new Intent(Choose_language.this,Otpscreen.class);
                i.putExtra("phone",result.Phone);
                startActivity(i);

            }
            else if (fromStorage!=null && result.getOTPStatus().equals("True")){
                startActivity(new Intent(this, MainActivity.class));

            }

            else{
                try {
                    InternalStorage.readObject(getApplicationContext(), "Chosen_Language");
                    Choose_language.this.setVisible(false);


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(this, MainActivity.class));



                Choose_language.this.setVisible(false);


            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btneng:
                startActivity(new Intent(this, MainActivity.class));
                String languageToLoad  = "en"; // your language
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                try {
                    InternalStorage.writeObject(getApplicationContext(), "Chosen_Language", languageToLoad);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnothr:
                startActivity(new Intent(this, MainActivity.class));
                String languageToLoad1  = "ar"; // your language
                Locale locale1 = new Locale(languageToLoad1);
                Locale.setDefault(locale1);
                Configuration config1 = new Configuration();
                config1.locale = locale1;
                getBaseContext().getResources().updateConfiguration(config1,
                        getBaseContext().getResources().getDisplayMetrics());

                try {
                    InternalStorage.writeObject(getApplicationContext(), "Chosen_Language", languageToLoad1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

        }
    }
}


