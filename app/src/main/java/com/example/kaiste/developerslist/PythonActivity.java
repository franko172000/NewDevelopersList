package com.example.kaiste.developerslist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PythonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_python);
        //load the fragment to display the list of python developers
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,new PythonFragment());
    }
}
