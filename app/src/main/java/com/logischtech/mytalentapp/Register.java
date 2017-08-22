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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.logischtech.mytalentapp.Helpers.InternalStorage;
import com.logischtech.mytalentapp.Models.APIResponse;
import com.logischtech.mytalentapp.Models.LoginResult;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_spinner_dropdown_item;

public class Register extends Fragment implements AdapterView.OnItemSelectedListener {
    public Register() {

    }

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.activity_register, container, false);
        Button register = (Button) rootView.findViewById(R.id.btnregister);
        TextView signin = (TextView) rootView.findViewById(R.id.signinlink);
        Spinner spinner = (Spinner) rootView.findViewById(R.id.spusername);
        spinner.setOnItemSelectedListener(this);
        List<String> items = new ArrayList<String>() {{
            add(getString(R.string.gender_male));
            add(getString(R.string.gender_female));

        }};


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), simple_spinner_dropdown_item, items);
        dataAdapter.setDropDownViewResource(simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = ((EditText) rootView.findViewById(R.id.firstname)).getText().toString();
                String lastname = ((EditText) rootView.findViewById(R.id.etlastname)).getText().toString();
                //   String gender=((EditText)findViewById(R.id.etusername)).getText().toString();
                String phone = ((EditText) rootView.findViewById(R.id.moblie)).getText().toString();
                String address = "a";
                String middlename = "m";
                String gender = ((Spinner) rootView.findViewById(R.id.spusername)).getSelectedItem().toString();
                ;

                String email = ((EditText) rootView.findViewById(R.id.etemail)).getText().toString();
                if (firstname == null || firstname.isEmpty() || lastname == null || lastname.isEmpty() || phone == null || phone.isEmpty() || address == null || address.isEmpty() || email == null || email.isEmpty()) {
                    new AlertDialog.Builder(getActivity())

                            .setTitle(R.string.error)
                            .setMessage(R.string.al_alert_btnregister)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    ;

                } else {
                    ConnectivityManager cmanager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (!cmanager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected() &&
                            !cmanager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()
                            ) {
                        new AlertDialog.Builder(getActivity())

                                .setTitle(R.string.error)
                                .setMessage(R.string.al_no_internet)
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        getActivity().finish();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();


                    } else {
                        new HttpRegisterRequestTask().execute(firstname, lastname, phone, address, email, middlename, gender);

                    }


                }
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login newFragmentC1 = new Login();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                FrameLayout fl = (FrameLayout) rootView.findViewById(R.id.fragment_childLayoutc2);
                fl.removeAllViews();
                transaction.add(rootView.getId(), newFragmentC1);
                transaction.commit();


            }
        });


        return rootView;
    }





    @Override
    public void onDestroy(){
        super.onDestroy();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        //   Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private class HttpRegisterRequestTask extends AsyncTask<String, String, LoginResult> {
        private ProgressDialog progress = new ProgressDialog(getActivity());

        @Override
        protected LoginResult doInBackground(String... params) {
            String filename = "myfile";
            String string = "Hello World";
            // APIResponse apiResponse=result.getdata();
            FileOutputStream outputStream;
            try {

                String firstname = params[0];//URLEncoder.encode(params[0],"utf-8") ;
                String lastname = params[1];//URLEncoder.encode(params[1],"utf-8") ;

                //  String gender=params[2];
                String email = params[4];
                String phone = params[2];
                String address = params[3];
                String middlename = params[5];
                ;
                String gender = params[6];
                ;
                String url = "http://www.midwestit.in/mytalentservices/RestService.svc/Registration/" + firstname + "/" + middlename + "/" + lastname + "/" + gender + "/" + email + "/" + phone + "/" + address;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.setErrorHandler(new CustomResponseErrorHandler());
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                APIResponse apiResponse = restTemplate.getForObject(url, APIResponse.class);

                if (apiResponse.getMessage().toLowerCase() == "false") {
                    return null;
                } else if (apiResponse.getData() == null) {
                    return null;

                } else {

                    LoginResult result = apiResponse.getData().get(0);

                    InternalStorage.writeObject(getActivity().getApplicationContext(), "Login_Result", result);

                    return result;
                }

            } catch (Exception e) {
                this.progress.setMessage(getActivity().getResources().getString(R.string.al_please_wait));
//
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
            if (progress.isShowing()) {
                progress.dismiss();
            }

            if (loginResult == null) {
                new AlertDialog.Builder(getActivity())

                        .setTitle(R.string.error)
                        .setMessage("Email already registered")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


            } else {
                Intent i = new Intent(getActivity(), Otpscreen.class);
                i.putExtra("phone", loginResult.Phone);
                startActivity(i);
            }
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



//return PlaceholderFragment.newInstance(position + 1); PlaceholderFragment.newInstance(position + 1);