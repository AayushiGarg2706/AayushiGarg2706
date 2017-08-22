package com.logischtech.mytalentapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.logischtech.mytalentapp.Helpers.InternalStorage;
import com.logischtech.mytalentapp.Models.APIResponse;
import com.logischtech.mytalentapp.Models.LoginResult;

import java.io.FileOutputStream;
import java.io.IOException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import static com.logischtech.mytalentapp.R.id.container;


public class Login extends Fragment  {
    public Login() {

    }

    View rootView;
     ViewPager viewPager;
    private LayoutInflater mInflater;
    private ViewGroup mContainer;
    TextView tvforgot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.activity_login, container, false);
        viewPager = (ViewPager) getActivity().findViewById(R.id.container);


        mInflater = inflater;
        mContainer = container;


        loadlogindata();

        return rootView;
    }


     @Override
     public void onDestroy(){
//          ProgressDialog progress=new ProgressDialog(getActivity());
  //       progress.dismiss();

         super.onDestroy();
         //if (this.progress.isShowing()) {
         //    this.progress.dismiss();
         //}

         //getActivity().getApplicationContext().unbindService(getActivity());

}

    private void loadlogindata() {
        Object fromStorage= null;


        try {
            fromStorage= InternalStorage.readObject(getActivity(), "Login_Result");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
            if (fromStorage == null) {

                rootView = mInflater.inflate(R.layout.activity_login, mContainer, false);

                TextView register= (TextView)rootView.findViewById(R.id.registernow);
                Button signin=(Button)rootView.findViewById(R.id.btnsignin);

                tvforgot = (TextView)rootView.findViewById(R.id.forgot);
                tvforgot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), Emailvalidation.class);

                        startActivity(i);
                    }
                });

                register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Register newFragment = new Register();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        FrameLayout fl = (FrameLayout) rootView.findViewById(R.id.fragment_childLayout);
                        fl.removeAllViews();
                        transaction.add(rootView.getId(), newFragment);
                        transaction.commit();

                    }
                });

                signin.setOnClickListener(new View.OnClickListener(){


                    @Override
                    public void onClick(View v) {
                        String username = ((EditText)rootView.findViewById(R.id.etusername)).getText().toString();
                        String password = ((EditText) rootView.findViewById(R.id.etpassword)).getText().toString();
                        if( username == null || username.isEmpty() || password == null || password.isEmpty() )
                        {
                            new AlertDialog.Builder(getActivity())

                                    .setTitle(R.string.error)
                                    .setMessage(R.string.ALert_login_submit)
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
                            ConnectivityManager cmanager=(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                            if(!cmanager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected() &&
                                    !cmanager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()
                                    ){
                                new AlertDialog.Builder(getActivity())

                                        .setTitle(R.string.error)
                                        .setMessage(R.string.al_no_internet)
                                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                getActivity().finish();                        }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();


                            }
                            else {
                                new HttpRequestTask().execute(username, password);
                            }

                        }
                    }
                });
            }
            else{
                Profile profile = new Profile();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                FrameLayout fl = (FrameLayout) rootView.findViewById(R.id.fragment_childLayout);
                fl.removeAllViews();
                transaction.add(rootView.getId(), profile);
                transaction.commit();

            }
    }
    private boolean isViewShown = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
       if (isVisibleToUser && isResumed()) { // fragment is visible and have created
             loadlogindata();
           isViewShown=false;


       }
    }



    private class HttpRequestTask extends AsyncTask<String,String,LoginResult> {
        private ProgressDialog progress=new ProgressDialog(getActivity());
        @Override
        protected LoginResult doInBackground(String... params) {
            String fileName = "MyFile";
            String content = "hello world";
            FileOutputStream outputStream;


            try {
                String username = params[0];//URLEncoder.encode(params[0],"utf-8") ;
                String password = params[1];//URLEncoder.encode(params[1],"utf-8") ;

                //String url = "http://www.midwestit.in/mytalentservices/RestService.svc/Login/"+username+ "/" + password;
                String url = "http://www.midwestit.in/mytalentservices/RestService.svc/Login/"+username+ "/"+ password;

                //http://www.midwestit.in/mytalentservices/RestService.svc/Login/gupta.sumit.20@gmail.com/pass1234
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

                    LoginResult result =  apiResponse.getData().get(0);

                    InternalStorage.writeObject(getActivity().getApplicationContext(), "Login_Result", result);
                    return result;
                }

            } catch (Exception e)
            {

                //return null;
                //this.progress.setMessage("Invalid Credentials");

            }

            return null;
        }
        @Override
        protected void onPreExecute() {
            this.progress.setMessage(getActivity().getResources().getString(R.string.al_please_wait));
            progress.setCanceledOnTouchOutside(false);

            this.progress.show();
        }

        ViewPager viewPager;
        @Override
        protected void onPostExecute(LoginResult loginResult) {
            super.onPostExecute(loginResult);
            //   if (    true    )
            if (progress.isShowing()) {
                progress.dismiss();
            }


            if(loginResult==null){
                new AlertDialog.Builder(getActivity())

                        .setTitle(R.string.error)
                        .setMessage(R.string.al_invalid_login)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();




            }
          else  if(loginResult.getOTPStatus().equals("False")){
                Intent i = new Intent(getActivity(),Otpscreen.class);
                i.putExtra("phone",loginResult.Phone);

                startActivity(i);

            }

            else {

                    viewPager = (ViewPager) getActivity().findViewById(container);

                    viewPager.setCurrentItem(1, true);


            }



        }


    }

    public class CustomResponseErrorHandler implements ResponseErrorHandler {

        private ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();

        public boolean hasError(ClientHttpResponse response) throws IOException {
            return errorHandler.hasError(response);
        }

        public void handleError(ClientHttpResponse response) throws IOException
        {

        }
    }

}



