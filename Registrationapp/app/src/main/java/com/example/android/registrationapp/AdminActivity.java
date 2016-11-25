package com.example.android.registrationapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class AdminActivity extends AppCompatActivity implements Organizer {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(null==savedInstanceState){
            AdminActivityFragment fragment = new AdminActivityFragment();
            fragment.setOrganizer(this);
            getSupportFragmentManager().beginTransaction().add(R.id.fragment,fragment).commit();
        }

    }

    @Override
    public void setSecondFragment(int position) {
        if(position == 0 ) {
            AddStudentFragment fragment = new AddStudentFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.secondFragment, fragment).commit();
        }
        else if(position == 1){
           AddCourseFragment fragment = new AddCourseFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.secondFragment, fragment).commit();

        }
        else{
           DeleteCourseFragment fragment = new DeleteCourseFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.secondFragment, fragment).commit();
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }
}
