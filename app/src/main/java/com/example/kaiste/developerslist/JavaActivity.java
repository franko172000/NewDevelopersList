package com.example.kaiste.developerslist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class JavaActivity extends AppCompatActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);
        //load the fragment to display the list of java developers
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,new JavaFragment());
    }
}
