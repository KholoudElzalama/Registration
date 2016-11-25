package com.example.android.registrationapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
public class ShowRegisteredCoursesFragment extends Fragment {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private ListView listView;
    private int pos;
    String courseuid;
    boolean b = false;
    Course course = new Course();
    ArrayList<Course> courses = new ArrayList<Course>() ;
    ArrayList<String> names = new ArrayList<String>() ;
    ArrayList<String> usercourses = new ArrayList<String>();


    public ShowRegisteredCoursesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =inflater.inflate(R.layout.fragment_show_registered_courses, container, false);
        listView = (ListView)rootView.findViewById(R.id.courses);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
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
                                  b = true;
                                }
                            }
                            if(b){
                                names.add(courses.get(i).name);
                                b=false;
                            }
                        }

                        Log.d("sizeofnames",Integer.toString(names.size()));
                        if(names.size()==0){
                            Toast.makeText(getActivity(),"there is no registered courses",Toast.LENGTH_LONG);
                        }
                        if(getActivity() != null) {
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, names);
                            listView.setAdapter(adapter);
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
        return rootView;
    }

}
