package com.logischtech.mytalentapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.logischtech.mytalentapp.Helpers.InternalStorage;
import com.logischtech.mytalentapp.Models.LoginResult;

import java.io.IOException;

import static com.logischtech.mytalentapp.R.id.container;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
   private TabLayout tabLayout;

    private int tabIcons[]={
            R.drawable.iconknow ,
            R.drawable.iconexam,
            R.drawable.iconprofile,

    };
    private int imageResources[]={
            R.drawable.iconknow ,
            R.drawable.iconexamactive,
            R.drawable.iconprofileactive

    };
    Object fromStorage=null;

    public void onBackPressed()
    {
        Object fromStorage= null;
        try {
            fromStorage = InternalStorage.readObject(getApplicationContext(), "Login_Result");
            //If logged In then do nothing user can not exit from tab controls.
        } catch (IOException e) {
            //If not logged In then allow back to happen.
            super.onBackPressed();
        } catch (ClassNotFoundException e) {
            //If not logged In then allow back to happen.
            super.onBackPressed();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }

        ConnectivityManager cmanager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(!cmanager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected() &&
                !cmanager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()
                ){
            new AlertDialog.Builder(MainActivity.this)

                    .setTitle(R.string.error)
                    .setMessage(R.string.al_no_internet)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();


        }
        else{

        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

         tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
       setupTabIcons();

        try {
            fromStorage= InternalStorage.readObject(this, "Login_Result");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(fromStorage==null){
            tabLayout.getTabAt(2).select();
            tabLayout.getTabAt(2).setIcon(R.drawable.iconprofileactive).select();
            


        }
        else{
            tabLayout.getTabAt(1).select();
            tabLayout.getTabAt(1).setIcon(R.drawable.iconexamactive).select();

        }

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.setIcon(imageResources[tab.getPosition()]);

                mViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setIcon(tabIcons[tab.getPosition()]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        //getActivity().getApplicationContext().unbindService(getActivity());



    }

    private void setupTabIcons() {
       tabLayout.getTabAt(0).setIcon(tabIcons[0]);
       tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);



  }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

         if(id==android.R.id.home)
        {
            finish();
            return true;
        }else{

         }
        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }
View rootView;
        ViewPager viewPager;

        @Override
        public View onCreateView(LayoutInflater inflater,final ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_main, container, false);

            final Fragment frag = this;

            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            Button btnShowChild = (Button) rootView.findViewById(R.id.btnShowChild);

            btnShowChild.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    enterNextFragment(container, frag);
                }
            });
            return rootView;
        }
        private void enterNextFragment(ViewGroup container, Fragment frag) {
            Know a3Fragment = new Know();
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();


            FrameLayout fl = (FrameLayout) rootView.findViewById(R.id.fragment_mainLayout);
            fl.removeAllViews();
            transaction.add(rootView.getId(), a3Fragment);

            transaction.commit();
        }
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            final Fragment result;
            switch (position) {
                case 0:
                    // First Fragment of First Tab
                    result = new Know();
                    break;
                case 1:
                    // First Fragment of Second Tab
                    result = new Examscreen();

                    break;
                case 2:
                    // First Fragment of Third Tab
                    result = new Login();
                    break;
                default:
                    result = null;
                    break;
            }

            return result;
        }


        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString( R.string.know);
                case 1:
                    return getString(R.string.exam);
                case 2:
                    return getString(R.string.profile);
            }
            return null;
        }

    }
}
