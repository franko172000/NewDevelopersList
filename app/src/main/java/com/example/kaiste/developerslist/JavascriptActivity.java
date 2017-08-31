package com.example.kaiste.developerslist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class JavascriptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_javascript);
        //load the fragment to display the list of javascript developers
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,new JavaScriptFragment());
    }
}
