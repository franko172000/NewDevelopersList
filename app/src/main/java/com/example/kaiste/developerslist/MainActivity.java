package com.example.kaiste.developerslist;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    /**
     * Create a log name for debugging purpose
     */
    private final static String LOG_TAG  = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getSupportActionBar().setElevation(0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** Get the view page **/
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        //set the viewpager to load the fragments
        DevelopersPagerAdapter adapter = new DevelopersPagerAdapter(getSupportFragmentManager());
        //
        viewPager.setAdapter(adapter);
        //get the tab view
        TabLayout langTab = (TabLayout) findViewById(R.id.tab);
        //set up the tab to work with the viewpager
        langTab.setupWithViewPager(viewPager);
    }
}
