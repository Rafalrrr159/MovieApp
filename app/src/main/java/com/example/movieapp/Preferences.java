package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
//import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import com.example.movieapp.databinding.ActivityPreferencesBinding;

import java.util.Objects;

public class Preferences extends AppCompatActivity {
    ActivityPreferencesBinding activityPreferencesBinding;
    private Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        activityPreferencesBinding = DataBindingUtil.setContentView(this,R.layout.activity_preferences);
        setContentView(activityPreferencesBinding.getRoot());
        toolBar = (Toolbar) activityPreferencesBinding.toolBar;
        setSupportActionBar(toolBar);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Preferences.this,MainActivity.class));
                finish();
            }
        });
    }
}
