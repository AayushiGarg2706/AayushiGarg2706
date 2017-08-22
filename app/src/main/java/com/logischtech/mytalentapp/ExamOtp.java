package com.logischtech.mytalentapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.logischtech.mytalentapp.Helpers.InternalStorage;
import com.logischtech.mytalentapp.Models.LoginResult;

import java.io.IOException;

public class ExamOtp extends Fragment {

    public ExamOtp() {

    }
    View rootView;
    ViewPager viewPager;
    TextView loggedin;
    private LayoutInflater mInflater;
    private ViewGroup mContainer;
    Button otpscreen;
    Object fromStorage = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      //  setUserVisibleHint(true);
       // rootView = inflater.inflate(R.layout.activity_exam1, container, false);
        final View rootView = inflater.inflate(R.layout.activity_exam_otp, container, false);
        viewPager = (ViewPager) getActivity().findViewById(R.id.container);
        otpscreen = (Button)rootView.findViewById(R.id.gotootp);
        try {
            fromStorage= InternalStorage.readObject(getActivity(), "Login_Result");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        final LoginResult result = (LoginResult) fromStorage;


        otpscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Otpscreen.class);
                i.putExtra("phone", result.Phone);

                startActivity(i);

            }
        });

       // viewPager = (ViewPager) getActivity().findViewById(R.id.container);
        load();

        return rootView;
    }

    private void load() {
        Object fromStorage = null;


        try {
            fromStorage = InternalStorage.readObject(getActivity(), "Login_Result");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        LoginResult result1 = (LoginResult) fromStorage;


        if (fromStorage == null) {
           // Exam1 exam = new Exam1();
          //  FragmentTransaction transaction = getFragmentManager().beginTransaction();
         //   FrameLayout fl = (FrameLayout) rootView.findViewById(R.id.activity_exam_otp);
          //  fl.removeAllViews();
          //  transaction.add(rootView.getId(), exam);
          //  transaction.commit();


        }
        else if(fromStorage != null &&result1.getOTPStatus().equals("False") ){

          //  rootView = mInflater.inflate(R.layout.activity_exam_otp, mContainer, false);
          //  rootView.findViewById(R.id.activity_exam_otp);


        }
        else if(fromStorage != null &&result1.getOTPStatus().equals("True"))  {
           // Examscreen exam = new Examscreen();
          //  FragmentTransaction transaction = getFragmentManager().beginTransaction();
          //  FrameLayout fl = (FrameLayout) rootView.findViewById(R.id.activity_exam_otp);
         //   fl.removeAllViews();
         //   transaction.add(rootView.getId(), exam);
          //  transaction.commit();
        }

    }

   // @Override
  //  public void setUserVisibleHint(boolean isVisibleToUser) {
   //     super.setUserVisibleHint(isVisibleToUser);
   //     if (isVisibleToUser && isResumed()) { // fragment is visible and have created
           // load();
   //     }
   //     else{

    //    }
   // }

}