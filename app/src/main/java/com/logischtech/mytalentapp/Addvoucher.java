package com.logischtech.mytalentapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;


import android.widget.TextView;
import android.widget.Toast;

import com.logischtech.mytalentapp.Helpers.InternalStorage;
import com.logischtech.mytalentapp.Models.ExamAPIResponse;
import com.logischtech.mytalentapp.Models.ExamResult;
import com.logischtech.mytalentapp.Models.LoginResult;
import com.logischtech.mytalentapp.Models.VoucherAPIResponse;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static android.R.attr.content;
import static android.R.attr.data;
import static android.R.attr.theme;
import static com.logischtech.mytalentapp.R.id.container;
import static com.logischtech.mytalentapp.R.id.voucher;

public class Addvoucher extends AppCompatActivity {
    private TextView txtadd; EditText edvoucher;

        Button submitvoucher;
    Object fromStorage = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addvoucher);
        edvoucher=(EditText)findViewById(R.id.voucher);
        txtadd = (TextView) findViewById(R.id.addforwhat);
        submitvoucher = (Button) findViewById(R.id.vochersubmit);
        final String str = getIntent().getStringExtra("TraitName");
       final String traitType = getIntent().getStringExtra("TraitType");
        Object fromStorage = null;
   txtadd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
       @Override
       public void onFocusChange(View v, boolean hasFocus) {
        if(txtadd.getText().length()==0){
            txtadd.setError("Voucher code is required");
            submitvoucher.setEnabled(false);


}
        else{
            submitvoucher.setEnabled(true);
        }
       }

   });

        try {
            fromStorage = InternalStorage.readObject(getApplicationContext(), "Login_Result");
                  } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        LoginResult result = (LoginResult) fromStorage;
      final   Integer sessionid = result.SessionId ;



        txtadd.setText(str);
        submitvoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String voucherid = ((EditText) findViewById(R.id.voucher)).getText().toString();

                ConnectivityManager cmanager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                if(!cmanager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected() &&
                        !cmanager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()
                        ){
                    new AlertDialog.Builder(Addvoucher.this)

                            .setTitle(R.string.error)
                            .setMessage(R.string.al_no_internet)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();                        }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();


                }


                else  if(voucherid == null || voucherid.trim().equals("")){
                    edvoucher.setError("Voucher code is required");
                }


                else {
                    new HttpRequestTask().execute(sessionid, traitType, voucherid);

                    }




            }
        });


    }
   
        private class HttpRequestTask extends AsyncTask<Object, String, VoucherAPIResponse> {
            private ProgressDialog progress = new ProgressDialog(Addvoucher.this);


        @Override
        protected VoucherAPIResponse doInBackground(Object... params) {
            Object fromStorage = null;


            try {
                fromStorage = InternalStorage.readObject(getApplicationContext(), "Login_Result");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            LoginResult result = (LoginResult) fromStorage;


            try {
                Integer sessionid = result.SessionId ;
                Integer session=sessionid;
                String traitType = getIntent().getStringExtra("TraitType");

                String voucherid=(String)params[2]; ;
                //URLEncoder.encode(params[0],"utf-8") ;
                // String url = "http://www.Midwestit.in/mytalentservices/RestService.svc/TestList/" + Sessionid;
                String url = "http://www.Midwestit.in/mytalentservices/RestService.svc/AuthenticateVoucher/" + sessionid + "/" + traitType + "/" + voucherid;

                //http://www.midwestit.in/mytalentservices/RestService.svc/Login/gupta.sumit.20@gmail.com/pass1234
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.setErrorHandler(new HttpRequestTask.CustomResponseErrorHandler());
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                VoucherAPIResponse voucherAPIResponse = restTemplate.getForObject(url, VoucherAPIResponse.class);




                return voucherAPIResponse;


            } catch (Exception e) {


                this.progress.setMessage("");


            }


            return null;
        }
        @Override
        protected void onPreExecute() {
            this.progress.setMessage("");
            progress.setCanceledOnTouchOutside(false);

            this.progress.show();


        }


        @Override
        protected void onPostExecute(VoucherAPIResponse voucherAPIResponse) {
            super.onPostExecute(voucherAPIResponse);
            if (progress.isShowing()) {
                progress.dismiss();
            }

            if (voucherAPIResponse.getMessage().toLowerCase().equals("false")) {
                new AlertDialog.Builder(Addvoucher.this)

                        .setTitle(R.string.error)
                        .setMessage("")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


               }
            else if (voucherAPIResponse.getMessage().toLowerCase().equals("true")) {
                Intent i = new Intent(Addvoucher.this,MainActivity.class);
                startActivity(i);

            } else {
                new AlertDialog.Builder(Addvoucher.this)

                        .setTitle(R.string.error)
                        .setMessage("Invalid Voucher")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();



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

    }

