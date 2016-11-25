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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DeleteCourseFragment extends Fragment {

    private DatabaseReference mDatabase;
    private Button delete;
    Course course = new Course();
   private Spinner spinner;
    ArrayList<Course> courses = new ArrayList<Course>() ;
    ArrayList<String> names = new ArrayList<String>() ;
    private int pos;
    public DeleteCourseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      final   View rootView =inflater.inflate(R.layout.fragment_delete_course, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // Toast.makeText(getActivity(), "inside deleeteeeeee", Toast.LENGTH_LONG).show();
        delete=(Button)rootView.findViewById(R.id.delete);
        spinner = (Spinner)rootView.findViewById(R.id.spinner_name);



        final Firebase ref = new Firebase("https://registration-app-f44af.firebaseio.com/courses/");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                names.clear();
                courses.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //Getting the data from snapshot

                    course = postSnapshot.getValue(Course.class);
                    course.id =postSnapshot.getKey();
                    names.add(course.name);
                    courses.add(course);
                    String string = "Name: "+course.name+"\nDepartment: "+course.department+ "\nid = "+ course.id+"\n\n";
                    Log.d("retrieve data",string);
                }
                if (getActivity() != null) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, names);
                    spinner.setAdapter(adapter);
                }

            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("FireBase Error","The read failed: " + firebaseError.getMessage());            }





        });

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
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("courses").child(courses.get(pos).id).removeValue();
                Toast.makeText(getActivity(), "Done successfully", Toast.LENGTH_LONG).show();
                Log.d("id",courses.get(pos).id);

            }
        });

        return rootView;
    }


}
