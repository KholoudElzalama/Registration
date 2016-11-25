package com.example.android.registrationapp;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by win on 25/11/2016.
 */
public class Course {
  @JsonIgnore
  public String id ;
  public   String name;
  public   String department;

    public Course() {
    }
}
