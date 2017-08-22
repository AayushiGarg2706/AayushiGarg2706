package com.logischtech.mytalentapp;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Know extends Fragment {
    public Know() {

    }
   View rootView;
    ViewPager viewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         rootView = inflater.inflate(R.layout.activity_knowscreen, container, false);
        Button btngetstrt=(Button)rootView.findViewById(R.id.btngetstarted);
        viewPager = (ViewPager) getActivity().findViewById(R.id.container);

        btngetstrt.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                viewPager.setCurrentItem(2);


            }
        });
        return rootView;
    }

}