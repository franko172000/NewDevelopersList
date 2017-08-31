package com.example.kaiste.developerslist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PhpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_php);
        //load the fragment to display the list of php developers
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,new PhpFragment());
    }
}
