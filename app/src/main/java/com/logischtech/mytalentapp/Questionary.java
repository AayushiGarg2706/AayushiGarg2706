package com.logischtech.mytalentapp;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.logischtech.mytalentapp.Helpers.InternalStorage;
import com.logischtech.mytalentapp.Models.Answer;
import com.logischtech.mytalentapp.Models.MessageAPIResponse;
import com.logischtech.mytalentapp.Models.Question;
import com.logischtech.mytalentapp.Models.QuestionAPIResponse;
import com.logischtech.mytalentapp.Models.ReportModel;
//import com.logischtech.mytalentapp.Models.Questions;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.logischtech.mytalentapp.Examscreen.TAG;

public class Questionary extends Fragment  {
    protected static final int Timer_Runtime=10000;
   public TextView tvcounterdown,ans;
    public RadioButton radio1,radio2,radio3,radio4,radio5;
    public  List<Question> questionList=new ArrayList<Question>();
    public  List<Answer> answerList =new ArrayList<Answer>();
public String TraitTypeID = "0";
    protected boolean mbActive;
    protected ProgressBar mProgressBar;
    View rootView;
    ViewPager viewPager;
    TextView traits;
    public String traitreport;
    Object fromStorage=null;
    int num=0;
    int i=0;
    boolean alertIsBeingShown=false;
    private MainActivity.SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private Button btnsubmit , btnstart;
    private RadioGroup questions;
    private RadioButton radioques;
    private String title;
    private int page;
    private boolean isViewShown = false;
    ImageView moveright;
    CounterClass timer ;

    public Questionary() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.activity_questionscreen, container, false);
        rootView.setVisibility(View.INVISIBLE);
        final TextView ques=(TextView)rootView.findViewById(R.id.question);
        tabLayout=(TabLayout)rootView.findViewById(R.id.tabs);
        final RadioButton radio1  = (RadioButton)rootView.findViewById(R.id.radio1);
        final RadioButton radio2  = (RadioButton)rootView.findViewById(R.id.radio2);
        final RadioButton radio3  = (RadioButton)rootView.findViewById(R.id.radio3);
        final RadioButton radio4 = (RadioButton)rootView.findViewById(R.id.radio4);
        final RadioButton radio5 = (RadioButton)rootView.findViewById(R.id.radio5);

           traits = (TextView)rootView.findViewById(R.id.traits);


        final TextView ques1=(TextView)rootView.findViewById(R.id.question2);
        TextView quesnum = (TextView) rootView.findViewById(R.id.numques);
        mProgressBar=(ProgressBar)rootView.findViewById(R.id.pb);
        TabLayout tablayout=(TabLayout)rootView.findViewById(R.id.tabs);

        moveright=(ImageView)rootView.findViewById(R.id.move);

        String currentLanguage = getActivity().getBaseContext().getResources().getConfiguration().locale.getLanguage();
        String language=currentLanguage;
        Bundle args=this.getArguments();
       final String traittype=args.getString("TraitType","");
        String voucherid=args.getString("voucherhidden","");
        TraitTypeID = args.getString("TraitTypeID");
         traitreport = traittype;

        traits.setText(traittype);
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
                    .show()
            ;
        }
        else {
            new  HttpRequestTask().execute(traittype,voucherid,language);
        }//

        moveright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar=(ProgressBar)rootView.findViewById(R.id.pb);
                RadioGroup group = (RadioGroup)rootView.findViewById(R.id.questions);
                 Answer answerObj =new Answer();

                answerObj.TestID =questionList.get(num).getTestID().toString();
                answerObj.QuestionID=questionList.get(num).getQuestionaryID().toString();
                answerObj.TraitID = questionList.get(num).getTraitID().toString();

                if(radio1.isChecked()==true){
                    questionList.get(num).StudentAction = "1.5";
                    answerObj.Answer=  "1.5";
                    answerObj.StudentAction="1";
                }
              else   if(radio2.isChecked()==true){
                    questionList.get(num).StudentAction = "5";
                    answerObj.Answer= "5";
                    answerObj.StudentAction="1";


                }
               else if(radio3.isChecked()==true){
                    questionList.get(num).StudentAction = "3";
                    answerObj.Answer="3";
                    answerObj.StudentAction="1";
                }
               else if(radio4.isChecked()==true){
                    questionList.get(num).StudentAction = "1";
                    answerObj.Answer="1";
                    answerObj.StudentAction="1";


                }
               else if(radio5.isChecked()==true){
                    questionList.get(num).StudentAction = "0";
                    answerObj.Answer= "0";
                    answerObj.StudentAction="1";

                }
                else{
                    answerObj.StudentAction="0";

            }

             answerList.add(answerObj);


                num += 1;
                group.clearCheck();
               final int intervalTime = 20000; // 20 sec
                Handler handler = new Handler();
                handler.postDelayed(new Runnable()  {
                    @Override
                    public void run() {

                    }
                }, intervalTime);




                if(num>=questionList.size()){
                    tvcounterdown.setText("Completed.");
                    new AlertDialog.Builder(getActivity())

                            .setTitle("Info")
                            .setMessage(R.string.al_ask_submit)
                            .setPositiveButton(android.R.string.yes,  new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // do something
                                   // Examscreen examscreen=new Examscreen();
                                  //  FragmentTransaction transaction=getFragmentManager().beginTransaction();
                                  //  FrameLayout f4=(FrameLayout)rootView.findViewById(R.id.activity_questionary);
                                 //   f4.removeAllViews();
                                  //  transaction.add(rootView.getId(),examscreen);
                                 //   transaction.commit();
                                 new HttpPostsubmit().execute();

                                }
                            })

                    .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();;

                }
                else {
                    TextView ques = (TextView) rootView.findViewById(R.id.question);
                    TextView quesnum = (TextView) rootView.findViewById(R.id.numques);

                    Question selectedQuestion = questionList.get(num);
                    ques.setText(selectedQuestion.Question);
                    quesnum.setText(num+1 +" "+ "of"+" " +questionList.size());
                 //   Integer i=((num+1)/questionList.size()) *100;
                    Float x = new Float(((num+1) * 100)/questionList.size()) ;
                    int i=(int)Math.ceil(x);

                    mProgressBar.setProgress(i);
                    timer.cancel();
                    //timer = new CounterClass(20000, 1000);
                    tvcounterdown.setText("00:20:00");
                    timer.start();
                }

            }

        });



        viewPager = (ViewPager) getActivity().findViewById(R.id.container);

       viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override public void onPageSelected(int position) {
               setUserVisibleHint(false);

         }
        });

        btnsubmit = (Button)rootView.findViewById(R.id.submit);
        //timer.

        btnsubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
             //   timer.cancel();

                if(num < questionList.size()){
                    new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.error)
                            .setMessage(R.string.al_ask_submit_before)
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing (will close dialog)
                                    dialog.dismiss();
                                }
                            })
                            .setPositiveButton(android.R.string.yes,  new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Examscreen examscreen=new Examscreen();
                                    FragmentTransaction transaction=getFragmentManager().beginTransaction();
                                    FrameLayout f4=(FrameLayout)rootView.findViewById(R.id.activity_questionary);
                                    f4.removeAllViews();
                                    transaction.add(rootView.getId(),examscreen);
                                    transaction.commit();


                                }
                            })

                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();;

                }
                else if(num==questionList.size()){
                    moveright.setVisibility(View.GONE);

                }
                else {
                    TextView ques = (TextView) rootView.findViewById(R.id.question);
                    TextView quesnum = (TextView) rootView.findViewById(R.id.numques);

                    Question selectedQuestion = questionList.get(num);
                    ques.setText(selectedQuestion.Question);
                    quesnum.setText(num+1 +" "+ "of"+" " +questionList.size());
                    //   Integer i=((num+1)/questionList.size()) *100;
                    Float x = new Float(num+1/questionList.size() * 100) ;
                    int i=(int)Math.round(x);
                    mProgressBar.setProgress(i);

                }

                new HttpPostsubmit().execute();
            }

        });

        RadioGroup group = (RadioGroup)rootView.findViewById(R.id.questions);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.radio1:
                        break;
                    case R.id.radio2:
                        break;
                    case R.id.radio3:
                        break;
                    case R.id.radio4:
                        break;
                    case R.id.radio5:
                        break;
                }
            }
        });

        return rootView;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        //getActivity().getApplicationContext().unbindService(getActivity());



    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getView() ==getActivity().findViewById(R.id.activity_questionary)) { // fragment is visible and have created

            isViewShown = true
            ;


            //  fetchData();
        }
        else{
            isViewShown=false
            ;


        }
    }

    @Override
    public void onResume(){
        super.onResume();
        FrameLayout frag=(FrameLayout)rootView.findViewById(R.id.activity_questionary);
        frag.requestFocus();
        frag.clearFocus();

    }

    public void updateProgress(final int timePassed){
        if(null !=mProgressBar){
            final int progress=mProgressBar.getMax() - timePassed/Timer_Runtime;
            mProgressBar.setProgress(progress);
        }
    }

    public void onContinue(){
        Log.d("messagemFinal","Loading!!!");
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    public class CounterClass extends CountDownTimer {
        final Thread timerThread =new Thread(){
            @Override
            public void run() {
                mbActive = true;
                try {
                    int waited = 0;
                    while (mbActive && (waited < Timer_Runtime)) {
                        sleep(200);
                        if (mbActive) {
                            waited += 200;
                            updateProgress(waited);
                        }
                    }
                } catch (InterruptedException e) {

                } finally {
                    onContinue();
                }
                timerThread.start();

            }
        };

        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;
            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(
                            TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
                            TimeUnit.MILLISECONDS.toMinutes(millis)));

            tvcounterdown.setText(hms);
        }

        @Override
        public void onFinish() {

            System.out.println("On finish called.");
            if(num>=questionList.size()){
                // moveright.setVisibility(View.INVISIBLE);
                btnsubmit.performClick();

            }
            else{
                moveright.performClick();
            }
        }
    }

    private class HttpPostsubmit extends AsyncTask<Object, Object, String> {
        private ProgressDialog progress=new ProgressDialog(getActivity());


        @Override
        protected String doInBackground(Object... params) {
            String result = "";
            try{

                JSONArray jsonArray=new JSONArray();
                for(int i=0;i < questionList.size();i++){
                    JSONObject request = new JSONObject();
                    request.put("QuestionaryID", questionList.get(i).QuestionaryID);
                    request.put("StudentAction","0");
                    request.put("Answer","1.5");
                    request.put("TestID",questionList.get(i).TestID);

                    //since trait id will be same for all the questions thus picking put first trait id of the question list.
                    request.put("TraitID",questionList.get(i).TraitID);
                    jsonArray.put(request);

                }
//               for(int i=0;i < answerList.size();i++){
//                    JSONObject request = new JSONObject();
//                    request.put("QuestionaryID", answerList.get(i).QuestionID);
//                    request.put("StudentAction",answerList.get(i).StudentAction);
//                    request.put("Answer",answerList.get(i).Answer);
//                    request.put("TestID",answerList.get(i).TestID);
//
//                    //since trait id will be same for all the questions thus picking put first trait id of the question list.
//                    request.put("TraitID",answerList.get(i).TraitID);
//                    jsonArray.put(request);
//
//
//                }

                List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
                acceptableMediaTypes.add(MediaType.APPLICATION_JSON);

            // Prepare header
                HttpHeaders headers = new HttpHeaders();
                headers.setAccept(acceptableMediaTypes);
                headers.setContentType(MediaType.APPLICATION_JSON);
                // Pass the new person and header
                HttpEntity entity = new HttpEntity(jsonArray.toString(), headers);

                System.out.println("Json Object : "+entity);
// Send the request as POST
                InputStream inputStream = null;
                //String result = "";
                try {
                    String url="http://www.midwestit.in/Mytalentservices/RestService.svc/updateanswerlist2";
                    // 1. create HttpClient
                    HttpClient httpclient = new DefaultHttpClient();
                    // 2. make POST request to the given URL
                    HttpPost httpPost = new HttpPost(url);
                    String json = "";


                    httpPost.setEntity(new StringEntity(jsonArray.toString(),"UTF-8"));
                    httpPost.setHeader("Accept", "application/json");
                    httpPost.setHeader("Content-type", "application/json");
                    HttpResponse httpResponse = httpclient.execute(httpPost);
                    inputStream = httpResponse.getEntity().getContent();
                    if (inputStream != null)
                        result = convertInputStreamToString(inputStream);
                    else
                        result = "Did not work!";

                }//end of try
                catch (Exception e) {
                    Log.d("InputStream", e.getLocalizedMessage());
                }

            }
            catch (Exception e)
            {
                Log.d(TAG, "doInBackground: ");
            }
            return result;

        }
        private String convertInputStreamToString(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while ((line = bufferedReader.readLine()) != null)
                result += line;

            inputStream.close();
            return result;
        }
        @Override
        protected void onPreExecute(){
            this.progress.setMessage(getActivity().getResources().getString(R.string.al_please_wait));
            progress.setCanceledOnTouchOutside(false);

            this.progress.show();

        }
        @Override
        protected void onPostExecute(String response){

            if (progress.isShowing()) {
                progress.dismiss();
            }

            try {

                JSONObject obj = new JSONObject(response);
                String result = obj.getString("Message");
                System.out.println("Json Object : "+result);
                try {
                  //  Bundle args=this.getArguments();

                    InternalStorage.writeObject(getActivity().getApplicationContext(),traitreport+"Report",result);
                    Examscreen examscreen=new Examscreen();
                      FragmentTransaction transaction=getFragmentManager().beginTransaction();
                      FrameLayout f4=(FrameLayout)rootView.findViewById(R.id.activity_questionary);
                       f4.removeAllViews();
                      transaction.add(rootView.getId(),examscreen);
                       transaction.commit();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                //obj["Message"]
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(response);
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

    private class HttpRequestTask extends AsyncTask<String ,String,QuestionAPIResponse> {
        private ProgressDialog progress = new ProgressDialog(getActivity());

        @Override
        protected QuestionAPIResponse doInBackground(String ... params) {

            Object fromStorage = null;
            try{
                String traittype= (String) params[0];

                String voucherid= (String ) params[1];

                String language= (String) params[2];

                String url="http://www.midwestit.in/mytalentservices/RestService.svc/QuestionaryList/" +traittype + "/" + voucherid +"/" + language;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.setErrorHandler(new HttpRequestTask.CustomResponseErrorHandler());
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                QuestionAPIResponse questionAPIResponse = restTemplate.getForObject(url, QuestionAPIResponse.class);

                    return questionAPIResponse;




        } catch (Exception e) {

            this.progress.setMessage("");

        }


        return null;
        }
        @Override
        protected void onPreExecute() {
            this.progress.setMessage(getActivity().getResources().getString(R.string.al_please_wait));
            this.progress.show();
        }
        @Override
        protected void onPostExecute(QuestionAPIResponse response){

            super.onPostExecute(response);
            if (progress.isShowing()) {
                progress.dismiss();
            }
            rootView.setVisibility(View.VISIBLE);

            TextView ques = (TextView) rootView.findViewById(R.id.question);
            TextView quesnum = (TextView) rootView.findViewById(R.id.numques);
            moveright=(ImageView)rootView.findViewById(R.id.move);

            if(response!=null){
                try{
                 questionList =
                    response.getData();
                    Question selectedQuestion = questionList.get(num);
                    ques.setText(selectedQuestion.Question);

                    quesnum.setText(num + 1 +" "+ "of"+" " +questionList.size());
                    Float x = new Float(num+1/questionList.size() * 100) ;
                    int i=(int)Math.round(x);
                    mProgressBar.setProgress(i);
                    String studentaction=selectedQuestion.getStudentAction();
                    final int intervalTime = 20000; // 20 sec

                    tvcounterdown = (TextView)rootView.findViewById(R.id.counterdown);
                    //int totalSeconds = questionList.size() * 20;


                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable()  {
                        @Override
                        public void run() {

                        }
                    }, intervalTime);

                    timer = new CounterClass(20000, 1000);
                    tvcounterdown.setText("00:20:00");
                    timer.start();
                }
                catch (Exception e){

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
}


