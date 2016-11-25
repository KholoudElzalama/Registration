package com.example.android.registrationapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class StudentActivity extends AppCompatActivity implements Organizer{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(null==savedInstanceState){
            StudentActivityFragment fragment = new StudentActivityFragment();
            fragment.setOrganizer(this);
            getSupportFragmentManager().beginTransaction().add(R.id.fragment,fragment).commit();
        }

    }
    @Override
    public void setSecondFragment(int position) {
        if(position == 0 ) {
            RegisterCourseFragment fragment = new RegisterCourseFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.secondFragment, fragment).commit();
        }

        else{
            ShowRegisteredCoursesFragment fragment = new ShowRegisteredCoursesFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.secondFragment, fragment).commit();
        }
    }

}
