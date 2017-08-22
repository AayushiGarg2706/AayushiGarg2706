package com.logischtech.mytalentapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.logischtech.mytalentapp.Helpers.InternalStorage;
import com.logischtech.mytalentapp.Models.ForgotPasswordResponse;
import com.logischtech.mytalentapp.Models.LoginResult;
import com.logischtech.mytalentapp.Models.OtpResponse;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class Emailvalidation extends AppCompatActivity {
    View rootView;
    Button btnsend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emailvalidation);
        btnsend = (Button) findViewById(R.id.btnsend);
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailid = ((EditText) findViewById(R.id.etemail)).getText().toString();
                if (emailid == null || emailid.isEmpty()) {
                    new AlertDialog.Builder(Emailvalidation.this)

                            .setTitle(R.string.error)
                            .setMessage(R.string.alert)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    ;
                }






                else {

                    new HttpForgotPasswordTask().execute(emailid);

                }

            }
        });


    }


    private class HttpForgotPasswordTask extends AsyncTask<String,String ,ForgotPasswordResponse> {
        private ProgressDialog progress = new ProgressDialog(Emailvalidation.this);

        @Override
        protected ForgotPasswordResponse doInBackground(String... params) {
try {
    String emailid = (String) params[0];
    String url = "http://www.midwestit.in/mytalentservices/RestService.svc/forgotpassword/" + emailid;
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setErrorHandler(new CustomResponseErrorHandler());
    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    ForgotPasswordResponse forgotPasswordResponse = restTemplate.getForObject(url, ForgotPasswordResponse.class);

    return forgotPasswordResponse;


        } catch (Exception e) {
            this.progress.setMessage("");


        }


        return null;

        }
        @Override
        protected void onPreExecute() {
            this.progress.setMessage("");
            this.progress.show();

        }

        @Override
        protected void onPostExecute(ForgotPasswordResponse forgotPasswordResponse) {
            super.onPostExecute(forgotPasswordResponse);
            if (progress.isShowing()) {
                progress.dismiss();
            }

        }




    }
    private class CustomResponseErrorHandler implements ResponseErrorHandler {
        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            return false;
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {

        }
    }


}