package com.example.android.registrationapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterCourseFragment extends Fragment {
    private DatabaseReference mDatabase;
    private Button register;
    private FirebaseAuth mAuth;

    Course course = new Course();
    private Spinner spinner;
    ArrayList<Course> courses = new ArrayList<Course>() ;
    ArrayList<String> names = new ArrayList<String>() ;
    ArrayList<String> usercourses = new ArrayList<String>();
    private int pos;
    boolean b =false;
    String courseuid;


    public RegisterCourseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_register_course, container, false);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // Toast.makeText(getActivity(), "inside deleeteeeeee", Toast.LENGTH_LONG).show();
        register=(Button)rootView.findViewById(R.id.register);
        spinner = (Spinner)rootView.findViewById(R.id.spinner_name);
        final String id = mAuth.getCurrentUser().getUid();
        final Firebase ref = new Firebase("https://registration-app-f44af.firebaseio.com/courses/");
        final Firebase ref2 = new Firebase("https://registration-app-f44af.firebaseio.com/users/"+ id+"/coursesID");
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usercourses.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //Getting the data from snapshot
                    courseuid = postSnapshot.getValue().toString();
                    usercourses.add(courseuid);
                }
                ref.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        courses.clear();
                        names.clear();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                        {
                        //Getting the data from snapshot

                        course = postSnapshot.getValue(Course.class);
                        course.id = postSnapshot.getKey();
                        courses.add(course);
                        String string = "Name: " + course.name + "\nDepartment: " + course.department + "\nid = " + course.id + "\n\n";
                        Log.d("retrieve data", string);

                        }
                        for(int i =0;i<courses.size();i++) {
                            for (int j=0; j < usercourses.size(); j++) {

                                    if (courses.get(i).id.equals(usercourses.get(j))) {
                                        b =true;
                                    }
                                }
                            if(!b){
                                names.add(courses.get(i).name);
                                b= false;
                            }

                            }

                            Log.d("sizeofnames",Integer.toString(names.size()));
                        if (getActivity() != null){
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, names);
                            spinner.setAdapter(adapter);
                        }

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                names.clear();
//                courses.clear();
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    //Getting the data from snapshot
//
//                    course = postSnapshot.getValue(Course.class);
//                    course.id = postSnapshot.getKey();
//                    courses.add(course);
//                    String string = "Name: " + course.name + "\nDepartment: " + course.department + "\nid = " + course.id + "\n\n";
//                    Log.d("retrieve data", string);
//                }
//                    ref2.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                           usercourses.clear();
//                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                                //Getting the data from snapshot
//
//                                courseuid = postSnapshot.getValue().toString();
//                                usercourses.add(courseuid);
//                            }
//                            for(int i =0;i<courses.size();i++) {
//                                if (i < usercourses.size()) {
//                                    if (courses.get(i).id.equals(usercourses.get(i))) {
//                                    } else {
//                                        names.add(courses.get(i).name);
//                                    }
//                                }
//                                else{
//                                    names.add(courses.get(i).name);
//                                }
//                            }
//
//                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, names);
//                            spinner.setAdapter(adapter);
//                        }
//                         @Override
//                        public void onCancelled(FirebaseError firebaseError) {
//                             Log.d("FireBase Error","The read failed: " + firebaseError.getMessage());                        }
//                     });
//            }
//
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//                Log.d("FireBase Error","The read failed: " + firebaseError.getMessage());
//            }
//        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                pos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            mDatabase.child("users").child(id).child("coursesID").push().setValue(courses.get(pos).id);
                Toast.makeText(getActivity(), "Done successfully", Toast.LENGTH_LONG).show();

            }
        });
        return rootView;
    }

}
