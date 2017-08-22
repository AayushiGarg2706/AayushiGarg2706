

package com.logischtech.mytalentapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.tool.reflection.ModelClass;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.logischtech.mytalentapp.Helpers.InternalStorage;
import com.logischtech.mytalentapp.Models.APIResponse;
import com.logischtech.mytalentapp.Models.ExamAPIResponse;
import com.logischtech.mytalentapp.Models.ExamResult;
import com.logischtech.mytalentapp.Models.LoginResult;
import com.logischtech.mytalentapp.Models.Question;
import com.logischtech.mytalentapp.Models.ReportModel;

import org.apache.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static com.logischtech.mytalentapp.R.id.btnbuy;
import static com.logischtech.mytalentapp.R.id.btnreport;
import static com.logischtech.mytalentapp.R.id.container;
import static com.logischtech.mytalentapp.R.id.traits;


public class Examscreen extends Fragment {
    public Examscreen() {

    }

    public View rootView;
    ViewPager viewPager;
    private LayoutInflater mInflater;
    private ViewGroup mContainer;
    private AppCompatActivity add;
    TextView Mymajor;
    TextView voucherno,voucherno1,voucherno2,voucherno4,vouchertext,vouchertext2,vouchertext3,vouchertext4;
    TextView voucherhidden,mymajorvoucherhidden;
    public static final String TAG = "NetworkHelper";
    public static final String CHECK_INTERNET = "";
    AlertDialog mAlertDialog = null;
    private Activity mActivity;



    Button btntakeexam;
    Button btnbuy1,crtake,crreport,crbuy,cradd;
    Button btnaddvochr,btnreport1,btntake1,btnaddvochr2,btnreport2,btntake2,mymatebuy,mymatetake,mymatereport,mymateadd,WMIadd,WMIreport,WMIbuy,WMItake;
    String LOCALE_HINDI = "hi";
    String LOCALE_ENGLISH = "en";
    Locale mLocale;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.activity_examscreen, container, false);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
           // rootView.setVisibility(View.INVISIBLE);



Mymajor=(TextView)rootView.findViewById(R.id.textView3);
        voucherno=(TextView)rootView.findViewById(R.id.voucherno);
        voucherno1=(TextView)rootView.findViewById(R.id.voucherno1);
        voucherno2=(TextView)rootView.findViewById(R.id.voucherno2);
        voucherno4=(TextView)rootView.findViewById(R.id.voucherno4);
        voucherhidden=(TextView)rootView.findViewById(R.id.voucherhidden);
        voucherhidden.setVisibility(View.GONE);
        vouchertext=(TextView)rootView.findViewById(R.id.vouchertext1);
        vouchertext2=(TextView)rootView.findViewById(R.id.vouchertext2);
        vouchertext3=(TextView)rootView.findViewById(R.id.vouchertext3);
        vouchertext4=(TextView)rootView.findViewById(R.id.vouchertext4);
        vouchertext.setVisibility(View.GONE);
        vouchertext2.setVisibility(View.GONE);
        vouchertext3.setVisibility(View.GONE);
        vouchertext4.setVisibility(View.GONE);

        mymajorvoucherhidden=(TextView)rootView.findViewById(R.id.voucherhiddenmajor);
        mymajorvoucherhidden.setVisibility(View.GONE);



        btntakeexam = (Button) rootView.findViewById(R.id.btntakeexam);
        btnbuy1=(Button)rootView.findViewById(R.id.btnbuy) ;
        btnaddvochr=(Button)rootView.findViewById(R.id.addvouchr) ;
        btnreport1=(Button)rootView.findViewById(R.id.btnreport1) ;
        btntake1=(Button)rootView.findViewById(R.id.btntake1) ;

        mymatebuy=(Button)rootView.findViewById(R.id.btnbuy2);
        mymateadd=(Button)rootView.findViewById(R.id.addvouchr2) ;
        mymatereport=(Button)rootView.findViewById(R.id.btnreport) ;
        mymatetake=(Button)rootView.findViewById(R.id.btntakeexam2) ;

        WMIbuy=(Button)rootView.findViewById(R.id.btnbuy1) ;
        WMIadd=(Button)rootView.findViewById(R.id.addvouchr3);
        WMItake=(Button)rootView.findViewById(R.id.btntakeexam3);
        WMIreport=(Button)rootView.findViewById(R.id.btnreport3);
        cradd=(Button)rootView.findViewById(R.id.cradd);

        crbuy=(Button)rootView.findViewById(R.id.crbuy);
        crreport=(Button)rootView.findViewById(R.id.crreport);
        crtake=(Button)rootView.findViewById(R.id.btntakeexam);
      //  Dialog dialog=new Dialog(getActivity(),android.R.style.Theme_Black_NoTitleBar_Fullscreen);
      //  dialog.setContentView(R.layout.frame_help);
      //  dialog.show();
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        btnbuy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),linkscreen.class);
                i.putExtra("TraitType","MYMAJOR");
                startActivity(i);

            }
        });
        WMIbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(),linkscreen.class);
                i.putExtra("TraitType","WHOAMI");
                startActivity(i);

            }
        });
        mymatebuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),linkscreen.class);
                i.putExtra("TraitType","MYMATE");
                startActivity(i);

            }
        });
        crbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),linkscreen.class);
                i.putExtra("TraitType","CAREERCHANGE");
                startActivity(i);


            }
        });

        WMIreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String reportModel = (String) InternalStorage.readObject(getActivity().getApplicationContext(),"WHOAMIReport");
                  // reportModel.ReportLink
                    Intent i = new Intent(getActivity(), Reportlink.class);
                    i.putExtra("reportlink", reportModel);
                    startActivity(i);
                   } catch (IOException e) {
                    new AlertDialog.Builder(getActivity())

                            .setTitle(R.string.error)
                            .setMessage(R.string.al_WMIreport)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();;
                } catch (ClassNotFoundException e) {
                    new AlertDialog.Builder(getActivity())

                            .setTitle(R.string.error)
                            .setMessage(R.string.al_WMIreport)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();;
                }


            }
        });
        mymatereport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String reportModel = (String) InternalStorage.readObject(getActivity().getApplicationContext(),"MYMATEReport");
                   // webview1.setText(reportModel.ReportLink);

                    Intent i = new Intent(getActivity(), Reportlink.class);
                    i.putExtra("reportlink", reportModel);
                    startActivity(i);
                }
 catch(Exception ex) {
     new AlertDialog.Builder(getActivity())

             .setTitle(R.string.error)
             .setMessage(R.string.al_WMIreport)
             .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int which) {
                     dialog.dismiss();
                 }
             })
             .setIcon(android.R.drawable.ic_dialog_alert)
             .show();
     ;
 }

            }
        });
        crreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    String reportModel = (String) InternalStorage.readObject(getActivity().getApplicationContext(),"CAREERCHANGEReport");

                    Intent i = new Intent(getActivity(), Reportlink.class);
                    i.putExtra("reportlink", reportModel);
                    startActivity(i);
                }
                catch (Exception ex) {

                    new AlertDialog.Builder(getActivity())

                            .setTitle(R.string.error)
                            .setMessage(R.string.al_WMIreport)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    ;
                }
            }
        });
        btnreport1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String reportModel = (String) InternalStorage.readObject(getActivity().getApplicationContext(),"MYMAJORReport");
                    Intent i = new Intent(getActivity(), Reportlink.class);
                    i.putExtra("reportlink", reportModel);
                    startActivity(i);

                }
                catch (Exception ex) {

                    new AlertDialog.Builder(getActivity())

                            .setTitle(R.string.error)
                            .setMessage(R.string.al_WMIreport)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    ;
                }

            }
        });





        mymateadd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(getActivity(),Addvoucher.class);
               startActivity(i);
              i.putExtra("My MAjor",Mymajor.getText().toString());


           }
       });
        cradd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),Addvoucher.class);
                startActivity(i);


            }
        });
        WMIadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),Addvoucher.class);
                startActivity(i);


            }
        });
        btnaddvochr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),Addvoucher.class);
                i.putExtra("TraitName","My MAJOR");
                i.putExtra("TraitType","MYMAJOR");
                startActivity(i);



            }
        });
        WMIadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),Addvoucher.class);
                i.putExtra("TraitName","WHO AM I");
                i.putExtra("TraitType","WHOAMI");
                startActivity(i);

            }
        });
        mymateadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),Addvoucher.class);
                i.putExtra("TraitName","MY MATE");
                i.putExtra("TraitType","MYMATE");
                startActivity(i);

            }
        });
        cradd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),Addvoucher.class);
                i.putExtra("TraitName","Career Change");
                i.putExtra("TraitType","CAREERCHANGE");
                startActivity(i);

            }
        });

        WMItake.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Questionary newFragmentB1 = new Questionary();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                FrameLayout B1 = (FrameLayout) rootView.findViewById(R.id.fragment_childLayoutb1);
                B1.removeAllViews();
                transaction.add(rootView.getId(), newFragmentB1);
                Bundle args=new Bundle();
                args.putString("TraitName","WHO AM I");
                args.putString("TraitType","WHOAMI");
                args.putString("TraitTypeID","2");
                args.putString("voucherhidden",voucherhidden.getText().toString());

                newFragmentB1.setArguments(args);

                transaction.commit();


            }
        });

        mymatetake.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Questionary newFragmentB1 = new Questionary();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                FrameLayout B1 = (FrameLayout) rootView.findViewById(R.id.fragment_childLayoutb1);
                B1.removeAllViews();
                transaction.add(rootView.getId(), newFragmentB1);
                Bundle args=new Bundle();
                args.putString("TraitName","MY MATE");
                args.putString("TraitType","MYMATE");
                args.putString("TraitTypeID","3");
                args.putString("voucherhidden",voucherhidden.getText().toString());
                newFragmentB1.setArguments(args);
                transaction.commit();
            }
        });

        btntake1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Questionary newFragmentB1 = new Questionary();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                FrameLayout B1 = (FrameLayout) rootView.findViewById(R.id.fragment_childLayoutb1);
                B1.removeAllViews();
                transaction.add(rootView.getId(), newFragmentB1);
                Bundle args=new Bundle();

                args.putString("TraitName","MY MAJOR");
                args.putString("TraitType","MYMAJOR");
                args.putString("TraitTypeID","1");
                args.putString("voucherhidden",mymajorvoucherhidden.getText().toString());

                newFragmentB1.setArguments(args);

                transaction.commit();


            }
        });





        btntakeexam.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Questionary newFragmentB1 = new Questionary();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                FrameLayout B1 = (FrameLayout) rootView.findViewById(R.id.fragment_childLayoutb1);
                B1.removeAllViews();
                transaction.add(rootView.getId(), newFragmentB1);
                Bundle args=new Bundle();
                args.putString("TraitName","CARREER CHANGE");
                args.putString("TraitType","CAREERCHANGE");
                args.putString("TraitTypeID","4");
                args.putString("voucherhidden",voucherhidden.getText().toString());
                newFragmentB1.setArguments(args);


                transaction.commit();


            }
        });


        mInflater = inflater;
        mContainer = container;
        viewPager = (ViewPager) getActivity().findViewById(R.id.container);
        load();

        return rootView;

    }



    private void load() {
        Object fromStorage = null;


        try {
            fromStorage = InternalStorage.readObject(getActivity().getApplicationContext(), "Login_Result");
           // LoginResult result = (LoginResult) fromStorage;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        LoginResult result1 = (LoginResult) fromStorage;

        if (fromStorage == null  ) {
            Exam1 exam = new Exam1();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            FrameLayout fl = (FrameLayout) rootView.findViewById(R.id.fragment_childLayoutb1);
            fl.removeAllViews();
            transaction.add(rootView.getId(), exam);
            transaction.commit();


        }
        else if(fromStorage != null && result1.getOTPStatus().equals("False")  )  {

               ExamOtp exotp = new ExamOtp();
          //  FrameLayout fl = (FrameLayout) rootView.findViewById(R.id.activity_exam1);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            FrameLayout fl = (FrameLayout) rootView.findViewById(R.id.fragment_childLayoutb1);
            fl.removeAllViews();
            transaction.add(rootView.getId(), exotp);
            transaction.commit();




        }
        else {

            LoginResult result = (LoginResult) fromStorage;
            Examscreen exam = new Examscreen();


                FrameLayout fl = (FrameLayout) rootView.findViewById(R.id.activity_exam1);
            if (fl != null) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                fl.removeAllViews();
                transaction.add(rootView.getId(), exam);
                transaction.commit();///

            }
            crtake.setVisibility(View.GONE);
            crreport.setVisibility(View.GONE);
            mymatereport.setVisibility(View.GONE);
            mymatetake.setVisibility(View.GONE);

            WMItake.setVisibility(View.GONE);
            WMIreport.setVisibility(View.GONE);
            btnreport1.setVisibility(View.GONE);
            btntake1.setVisibility(View.GONE);


            ConnectivityManager cmanager=(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            if(!cmanager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected() &&
                    !cmanager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()
                    ){
                new AlertDialog.Builder(getActivity())

                        .setTitle(R.string.error)
                        .setMessage(R.string.al_no_internet)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();                       }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


            }
            else {
                new HttpRequestTask().execute(result.SessionId);
            }

        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) { // fragment is visible and have created
            load();
        } else {

        }
    }




    private class HttpRequestTask extends AsyncTask<Integer, String, ExamAPIResponse> {
        private ProgressDialog progress = new ProgressDialog(getActivity());


        @Override
        protected ExamAPIResponse doInBackground(Integer... params) {
            try {
                Integer Sessionid = params[0];//URLEncoder.encode(params[0],"utf-8") ;
                String url = "http://www.Midwestit.in/mytalentservices/RestService.svc/TestList/" + Sessionid;

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.setErrorHandler(new CustomResponseErrorHandler());
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ExamAPIResponse examAPIResponse = restTemplate.getForObject(url, ExamAPIResponse.class);


                if (examAPIResponse.getMessage().toLowerCase() == "false") {
                    return null;
                } else if (examAPIResponse.getData() == null) {
                    return null;

                } else {

                    InternalStorage.writeObject(getActivity().getApplicationContext(), "Exams_Result", examAPIResponse);


                    return examAPIResponse;
                }
            } catch (Exception e)
            {

                this.progress.setMessage("");
                //this.progress.hide();
            }


            return null;
        }
        @Override
        protected void onPreExecute() {
          //   getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            this.progress.setMessage(getActivity().getResources().getString(R.string.al_please_wait));
            progress.setCanceledOnTouchOutside(false);
//            progress.setCancelable(true);
//            progress.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                @Override
//                public void onCancel(DialogInterface dlg) {
//                    HttpRequestTask.this.cancel(true);
//                }
//            });


            this.progress.show();
        }
        ViewPager viewPager;
        @Override
        protected void onPostExecute(ExamAPIResponse exam) {

        rootView.setVisibility(View.VISIBLE);
            super.onPostExecute(exam);

            if (progress.isShowing()) {
                progress.dismiss();
            }



            if(exam !=null){

                try{


                    exam.getData();
                  for(ExamResult e: exam.getData()){

                      ExamResult result =  e;

                      if(result.getTraitTypeId()==1) {
                          voucherno1.setText(result.getVoucherNo());
                          String vId = String.valueOf(result.getVoucherId());
                          mymajorvoucherhidden.setText(vId);

                          if ( result.getTestStatus().toLowerCase().equals("incomplete")) {
                              btnreport1.setVisibility(View.GONE);
                              btnaddvochr.setVisibility(View.GONE);
                              btnbuy1.setVisibility(View.GONE);
                              btntake1.setVisibility(View.VISIBLE);
                              vouchertext.setVisibility(View.VISIBLE);

                          } else if ( result.getTestStatus().toLowerCase().equals("completed")) {
                              btnbuy1.setVisibility(View.GONE);
                              btntake1.setVisibility(View.GONE);
                              btnaddvochr.setVisibility(View.GONE);
                              btnreport1.setVisibility(View.VISIBLE);
                              vouchertext.setVisibility(View.VISIBLE);



                          } else {

                              btntake1.setVisibility(View.GONE);
                              btnreport1.setVisibility(View.GONE);
                              btnaddvochr.setVisibility(View.VISIBLE);
                              btnbuy1.setVisibility(View.VISIBLE);

                          }
                      }
                      else
                      if(result.getTraitTypeId()==2){
                          voucherno.setText(result.getVoucherNo());
                          String vId = String.valueOf(result.getVoucherId());
                         voucherhidden.setText(vId);

                          if (result.getTestStatus().toLowerCase().equals("incomplete")) {

                             WMIadd.setVisibility(View.GONE);
                              WMIreport.setVisibility(View.GONE);
                              WMIbuy.setVisibility(View.GONE);
                              WMItake.setVisibility(View.VISIBLE);
                              vouchertext2.setVisibility(View.VISIBLE);




                          } else if (result.getTestStatus().toLowerCase().equals("completed")) {
                              WMIadd.setVisibility(View.GONE);
                              WMIbuy.setVisibility(View.GONE);
                              WMItake.setVisibility(View.GONE);
                              WMIreport.setVisibility(View.VISIBLE);
                              vouchertext2.setVisibility(View.VISIBLE);


                          } else {

                              WMItake.setVisibility(View.GONE);
                              WMIreport.setVisibility(View.GONE);
                              WMIadd.setVisibility(View.VISIBLE);
                              WMIbuy.setVisibility(View.VISIBLE);

                          }



                      }
                      else if(result.getTraitTypeId()==3){
                          if ( result.getTestStatus().toLowerCase().equals("incomplete")) {
                              voucherno4.setText(result.getVoucherNo());

                              mymatereport.setVisibility(View.GONE);
                              mymateadd.setVisibility(View.GONE);
                              mymatebuy.setVisibility(View.GONE);
                              mymatetake.setVisibility(View.VISIBLE);
                              vouchertext3.setVisibility(View.VISIBLE);



                          } else if ( result.getTestStatus().toLowerCase().equals("completed")) {
                              mymatebuy.setVisibility(View.GONE);
                              mymatetake.setVisibility(View.GONE);
                              mymatereport.setVisibility(View.VISIBLE);
                              mymateadd.setVisibility(View.GONE);
                              vouchertext3.setVisibility(View.VISIBLE);


                          } else {

                              mymatetake.setVisibility(View.GONE);
                              mymatereport.setVisibility(View.GONE);
                              mymateadd.setVisibility(View.VISIBLE)
                              ;mymatebuy.setVisibility(View.VISIBLE);

                          }

                      }
                      else{
                          if ( result.getTestStatus().toLowerCase().equals("incomplete")) {
                              voucherno2.setText(result.getVoucherNo());

                              crreport.setVisibility(View.GONE);
                              crbuy.setVisibility(View.GONE);
                              cradd.setVisibility(View.GONE);
                              crtake.setVisibility(View.VISIBLE);
                              vouchertext4.setVisibility(View.VISIBLE);


                          } else if ( result.getTestStatus().toLowerCase().equals("completed")) {
                              voucherno.setText(result.getVoucherNo());

                              crbuy.setVisibility(View.GONE);
                              cradd.setVisibility(View.GONE);
                              crtake.setVisibility(View.GONE);
                              crreport.setVisibility(View.VISIBLE);
                              vouchertext4.setVisibility(View.VISIBLE);


                          } else {
                              crtake.setVisibility(View.GONE);
                              cradd.setVisibility(View.VISIBLE);
                              crbuy.setVisibility(View.VISIBLE);
                              crreport.setVisibility(View.GONE);

                          }

                      }

                  }

                }
                catch (Exception e)
                {

                    this.progress.setMessage("");
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
}



