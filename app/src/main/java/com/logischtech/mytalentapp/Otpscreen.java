package com.logischtech.mytalentapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.logischtech.mytalentapp.Helpers.InternalStorage;
import com.logischtech.mytalentapp.Models.APIResponse;
import com.logischtech.mytalentapp.Models.LoginResult;
import com.logischtech.mytalentapp.Models.OtpResponse;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static com.logischtech.mytalentapp.R.id.container;
import static com.logischtech.mytalentapp.R.id.email;

public class Otpscreen extends AppCompatActivity {

      EditText mobile;
    EditText OtpNumber;
    Object fromStorage = null;
    public TextView emailid;
    TextView newregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpscreen);
        Button btn = (Button) findViewById(R.id.btnverify);
        TextView resndotp = (TextView) findViewById(R.id.tvresendotp);
       // viewPager = (ViewPager) findViewById(R.id.container);
       // Bundle args = getIntent().getExtras();
       // String name = args.getString("phone", "");
        mobile = (EditText)findViewById(R.id.etmoblie);
        final String phone = getIntent().getStringExtra("phone");
        OtpNumber =(EditText)findViewById(R.id.etetotp);


        mobile.setText(phone);
        mobile.setClickable(false);
        mobile.setFocusable(false);


        try {
            fromStorage = InternalStorage.readObject(Otpscreen.this, "Login_Result");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        LoginResult result = (LoginResult) fromStorage;

        final Integer Sessionid = result.SessionId;
        final String Emailid = result.EmailID;

        resndotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HttpRequestResendOtp().execute(Emailid);




            }
        });

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String OtpNumber = ((EditText) findViewById(R.id.etetotp)).getText().toString();
                if( OtpNumber == null || OtpNumber.isEmpty() || mobile == null )
                {
                    new AlertDialog.Builder(Otpscreen.this)

                            .setTitle(R.string.error)
                            .setMessage("Please enter Otpnumber")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();;
                }
else {

                    new HttpRequestTask().execute(Sessionid, OtpNumber);
                    //  Intent i = new Intent(Otpscreen.this,Examscreen.class);
                    // startActivity(i);

                }

            }
        });

      //  return rootView;
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        //getActivity().getApplicationContext().unbindService(getActivity());



    }



    private class HttpRequestTask extends AsyncTask<Object, String, OtpResponse> {
        private ProgressDialog progress = new ProgressDialog(Otpscreen.this
        );

        @Override
        protected OtpResponse doInBackground(Object... params) {
            try {
                fromStorage = InternalStorage.readObject(Otpscreen.this, "Login_Result");
                LoginResult result = (LoginResult) fromStorage;

                Integer Sessionid = result.SessionId;
                String OtpNumber = (String) params[1];
                String url = "http://www.Midwestit.in/mytalentservices/RestService.svc/AuthenticateOTP/" + Sessionid + "/" + OtpNumber;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.setErrorHandler(new CustomResponseErrorHandler());
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                OtpResponse otpresponse = restTemplate.getForObject(url, OtpResponse.class);

                return otpresponse;


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
        protected void onPostExecute(OtpResponse otpresponse) {
            super.onPostExecute(otpresponse);
            if (progress.isShowing()) {
                progress.dismiss();
            }
            if (otpresponse.getMessage().toLowerCase().equals("false")) {
                new AlertDialog.Builder(Otpscreen.this)

                        .setTitle(R.string.error)
                        .setMessage("Invalid OtpNumber")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


            } else {
                LoginResult result = (LoginResult) fromStorage;

                final Integer Sessionid = result.SessionId;
                new HttpRequestOtp().execute(Sessionid);
                startActivity(new Intent(Otpscreen.this, MainActivity.class));



            }


        }

        private class CustomResponseErrorHandler implements ResponseErrorHandler {
            private ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();

            public boolean hasError(ClientHttpResponse response) throws IOException {
                return errorHandler.hasError(response);
            }

            public void handleError(ClientHttpResponse response) throws IOException {

            }

        }

    }

    private class HttpRequestResendOtp extends AsyncTask<String, String, OtpResponse> {
        private ProgressDialog progress = new ProgressDialog(Otpscreen.this);

        @Override
        protected OtpResponse doInBackground(String... params) {
            try {
                fromStorage = InternalStorage.readObject(getApplicationContext(), "Login_Result");
                LoginResult result = (LoginResult) fromStorage;
                String Emailid = result.EmailID;
                String url = "http://www.Midwestit.in/mytalentservices/RestService.svc/ResendOTP/" + Emailid;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.setErrorHandler(new CustomResponseErrorHandler());
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                OtpResponse otpresponse = restTemplate.getForObject(url, OtpResponse.class);

                return otpresponse;


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
        protected void onPostExecute(OtpResponse otpresponse) {
            super.onPostExecute(otpresponse);
            if (progress.isShowing()) {
                progress.dismiss();
            }

        }

        private class CustomResponseErrorHandler implements ResponseErrorHandler {
            private ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();

            public boolean hasError(ClientHttpResponse response) throws IOException {
                return errorHandler.hasError(response);
            }

            public void handleError(ClientHttpResponse response) throws IOException {

            }

        }

    }

    private class HttpRequestOtp extends AsyncTask<Object,String,LoginResult> {
        private ProgressDialog progress=new ProgressDialog(Otpscreen.this);

        @Override
        protected LoginResult doInBackground(Object... params) {

            try{

                fromStorage = InternalStorage.readObject(getApplicationContext(), "Login_Result");
                LoginResult result = (LoginResult) fromStorage;

                Integer Sessionid = result.SessionId;


                String url="http://www.Midwestit.in/mytalentservices/RestService.svc/StudentDetails/"+ Sessionid;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.setErrorHandler(new CustomResponseErrorHandler());
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                APIResponse apiResponse = restTemplate.getForObject(url, APIResponse.class);
                if (  apiResponse.getMessage().toLowerCase() == "false"  )
                {
                    return  null;
                }
                else if(apiResponse.getData()==null )
                {
                    return null;

                }
                else
                {
                    LoginResult resultdata =  apiResponse.getData().get(0);

                    InternalStorage.removeObject(getApplicationContext(), "Login_Result");
                    InternalStorage.writeObject(getApplicationContext(), "Login_Result", resultdata);
                    return resultdata;
                }




            }
            catch (Exception e){
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
        protected void onPostExecute(LoginResult loginResult){
            super.onPostExecute(loginResult);
            if (progress.isShowing()) {
                progress.dismiss();
            }

      if(loginResult.getOTPStatus().equals("True")){

          startActivity(new Intent(Otpscreen.this, MainActivity.class));

      }
            else{

          new AlertDialog.Builder(Otpscreen.this)

                  .setTitle(R.string.error)
                  .setMessage("Invalid OtpNumber")
                  .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int which) {
                          // continue with delete
   dialog.dismiss();                      }
                  })
                  .setIcon(android.R.drawable.ic_dialog_alert)
                  .show();

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




