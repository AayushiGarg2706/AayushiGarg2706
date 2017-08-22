package com.logischtech.mytalentapp;

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

public class Exam1 extends Fragment {

    public Exam1() {

    }
    View rootView;
    ViewPager viewPager;
    TextView loggedin; private LayoutInflater mInflater;
    private ViewGroup mContainer;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.activity_exam1, container, false);
        viewPager = (ViewPager) getActivity().findViewById(R.id.container);
             loggedin=(TextView)rootView.findViewById(R.id.login1);
        loggedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
            }
        });
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


         //   rootView = mInflater.inflate(R.layout.activity_exam1, mContainer, false);
          //  rootView.findViewById(R.id.activity_exam1);


        }
        else if(fromStorage != null &&result1.getOTPStatus().equals("False") ){
            ExamOtp exotp = new ExamOtp();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            FrameLayout fl = (FrameLayout) rootView.findViewById(R.id.activity_exam_otp);
            fl.removeAllViews();
            transaction.add(rootView.getId(), exotp);
            transaction.commit();

        }
        else {
            Examscreen exam = new Examscreen();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            FrameLayout fl = (FrameLayout) rootView.findViewById(R.id.activity_exam1);
            fl.removeAllViews();
            transaction.add(rootView.getId(), exam);
            transaction.commit();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) { // fragment is visible and have created
            load();
        }
        else{

        }
    }

}