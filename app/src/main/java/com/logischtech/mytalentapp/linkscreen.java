package com.logischtech.mytalentapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.logischtech.mytalentapp.Helpers.InternalStorage;
import com.logischtech.mytalentapp.Models.LoginResult;
import com.logischtech.mytalentapp.Models.PaymentRequestResponse;
import com.logischtech.mytalentapp.Models.PaymentResponse;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class linkscreen extends AppCompatActivity {
    private WebView mWebView;
    private static final String VALID = "Try Again";
    private static final String INVALID = "Later";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkscreen);
        mWebView = (WebView) findViewById(R.id.webview1);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new WebAppInterface(this), "android");
        mWebView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(String.valueOf(request.getUrl()));
                return  true;
            }
        });
        try {

            Object fromStorage = null;
            fromStorage = InternalStorage.readObject(linkscreen.this, "Login_Result");
            LoginResult result = (LoginResult) fromStorage;
            Integer Sessionid = result.SessionId;
            String exams = "1";
            final String traitType = getIntent().getStringExtra("TraitType");

            new HttpResponseTask().execute(Sessionid, exams, traitType);


        } catch (Exception e) {

        }


        //mWebView.loadUrl("http://192.168.0.84/javascriptbtn.html");
    }



    public class WebAppInterface {

        Context mContext;


        /**
         * Instantiate the interface and set the context
         */
        WebAppInterface(Context c) {
            mContext = c;
        }


        @JavascriptInterface
        public void PaymentStatusUpdate(String returnId, String code, String description) {
            Object fromStorage = null;
            try {
                fromStorage = InternalStorage.readObject(linkscreen.this, "Login_Result");
                LoginResult result = (LoginResult) fromStorage;
                new HttpPaymentResponse().execute(result.getSessionID(), returnId, code, description);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }


    }


    private class HttpResponseTask extends AsyncTask<Object, String, PaymentResponse> {
        private ProgressDialog progress = new ProgressDialog(linkscreen.this);

        @Override
        protected PaymentResponse doInBackground(Object... params) {

            try {
                Integer Sessionid = (Integer) params[0];
                String exams = (String) params[1];
                String traitType = getIntent().getStringExtra("TraitType");
                String url = "http://www.midwestit.in/mytalentservices/RestService.svc/GetMerchantID/" + Sessionid + "/" + exams + "/" + traitType;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.setErrorHandler(new CustomResponseErrorHandler());
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                PaymentResponse paymentResponse = restTemplate.getForObject(url, PaymentResponse.class);

                return paymentResponse;


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
        protected void onPostExecute(PaymentResponse paymentResponse) {
            super.onPostExecute(paymentResponse);
            if (progress.isShowing()) {
                progress.dismiss();

            }


            if (paymentResponse.getMessage().equals("True")) {

                try {
                    String ResultID = paymentResponse.getReturnID();
                    //mWebView.loadUrl("http://192.168.11.126/mytalent/home/payment?returnId=" + ResultID);
                    //mWebView.loadUrl("http://52.187.19.230/mytalent/Home/PaymentComplete?returnId=69&checkoutId=3338C2D8C8A04FCC403312AD0C74B047.sbg-vm-tx01&id=3338C2D8C8A04FCC403312AD0C74B047.sbg-vm-tx01&resourcePath=%2Fv1%2Fcheckouts%2F3338C2D8C8A04FCC403312AD0C74B047.sbg-vm-tx01%2Fpayment");
                   mWebView.loadUrl("http://52.187.186.166/mytalent/home/payment?returnId=" + ResultID);

                } catch (Exception e) {


                }


            } else {

                new android.app.AlertDialog.Builder(linkscreen.this)
                        .setTitle("Info")
                        .setMessage("Error occured while processing.Please try again.")
                        .setNegativeButton(VALID, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                try {
                                    Object fromStorage = null;
                                    fromStorage = InternalStorage.readObject(linkscreen.this, "Login_Result");
                                    LoginResult result = (LoginResult) fromStorage;
                                    Integer Sessionid = result.SessionId;
                                    String exams = "1";
                                    final String traitType = getIntent().getStringExtra("TraitType");

                                    new HttpResponseTask().execute(Sessionid, exams, traitType);


                                } catch (Exception e) {


                                }


                            }
                        })
                        .setPositiveButton(INVALID, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // do something
                               Examscreen plf = new Examscreen();
                               getSupportFragmentManager().beginTransaction().replace(R.id.activity_linkscreen, plf, "tag").addToBackStack(null).commit();
                                RelativeLayout B1 = (RelativeLayout) findViewById(R.id.activity_linkscreen);
                                B1.removeAllViews();



                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                ;


            }


        }

        public class CustomResponseErrorHandler implements ResponseErrorHandler {

            private ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();

            public boolean hasError(ClientHttpResponse response) throws IOException {
                return errorHandler.hasError(response);
            }

            public void handleError(ClientHttpResponse response) throws IOException {

            }
        }

    }

    private class HttpPaymentResponse extends AsyncTask<Object, String, PaymentRequestResponse> {
        private ProgressDialog progress = new ProgressDialog(linkscreen.this);

        @Override
        protected PaymentRequestResponse doInBackground(Object... params) {

            try {
                Integer Sessionid = (Integer) params[0];
                String ResultID = (String) params[1];
                String Status = (String) params[2];
                String FinalStatus ="Success";

                String url = "http://www.midwestit.in/mytalentservices/RestService.svc/PaymentStatusUpdate/" + Sessionid + "/" + ResultID + "/" + Status + "/" + FinalStatus;

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.setErrorHandler(new CustomResponseErrorHandler());
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                PaymentRequestResponse paymentRequestResponse = restTemplate.getForObject(url, PaymentRequestResponse.class);

                return paymentRequestResponse;


            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            this.progress.setMessage("");
            progress.setCanceledOnTouchOutside(false);

        }

        @Override
        protected void onPostExecute(PaymentRequestResponse paymentRequestResponse) {
            super.onPostExecute(paymentRequestResponse);
            if (progress.isShowing()) {
                progress.dismiss();

            }

            if (paymentRequestResponse.getMessage().equals("True")) {

              // Examscreen plf = new Examscreen();
                //getSupportFragmentManager().beginTransaction().replace(R.id.activity_linkscreen, plf, "tag").addToBackStack(null).commit();
               // RelativeLayout B1 = (RelativeLayout) findViewById(R.id.activity_linkscreen);
               // B1.removeAllViews();
                startActivity(new Intent(linkscreen.this, MainActivity.class));


            } else {


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

