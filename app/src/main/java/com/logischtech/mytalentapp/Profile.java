package com.logischtech.mytalentapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.logischtech.mytalentapp.Helpers.InternalStorage;
import com.logischtech.mytalentapp.Models.LoginResult;

import java.io.IOException;

import static com.logischtech.mytalentapp.R.id.container;
import static com.logischtech.mytalentapp.R.id.transition_current_scene;

public class Profile extends Fragment {
    public Profile(){

    }

    View rootView;
    ViewPager viewPager;
    EditText Name;
    EditText Phone;
    EditText Email;
    Button logout;
    private LayoutInflater mInflater;
    private ViewGroup mContainer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View rootView = inflater.inflate(R.layout.activity_profile, container, false);
        Name=(EditText)rootView.findViewById(R.id.Name);
        Phone=(EditText)rootView.findViewById(R.id.moblie) ;
        Email=(EditText)rootView.findViewById(R.id.etemail);
        logout=(Button) rootView.findViewById(R.id.logout);
        Phone.setClickable(false);
        Phone.setFocusable(false);
        Email.setClickable(false);
        Email.setFocusable(false);
        Name.setClickable(false);
        Name.setFocusable(false);
        viewPager = (ViewPager) getActivity().findViewById(R.id.container);


        mInflater = inflater;
        mContainer = container;

        loaddata1();



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object fromStorage=null;
               try{
                   fromStorage=InternalStorage.removeObject(getActivity(),"Login_Result");
                   Login newFragmentC1 = new Login();
                   FragmentTransaction transaction = getFragmentManager().beginTransaction();
                   FrameLayout fl = (FrameLayout) rootView.findViewById(R.id.activity_profile);
                   fl.removeAllViews();
                  transaction.add(rootView.getId(), newFragmentC1);
                   transaction.commit();




               }
                catch (IOException e) {
                e.printStackTrace();
                } catch (ClassNotFoundException e) {
                   e.printStackTrace();
                }


            }
        });
        return rootView;
    }

    private void loaddata1() {
        Object fromStorage= null;
        try {
            fromStorage= InternalStorage.readObject(getActivity().getApplicationContext(), "Login_Result");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (fromStorage == null) {


            }
            else{

                rootView = mInflater.inflate(R.layout.activity_profile, mContainer, false);
                rootView.findViewById(R.id.activity_profile);

            }
        LoginResult result = (LoginResult) fromStorage;
        Name.setText(result.Name);
        Phone.setText(result.Phone);
        Email.setText(result.EmailID);










    }


}