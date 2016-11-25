package com.example.android.registrationapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddCourseFragment extends Fragment {

    private Spinner department;
    private EditText name;
    private Button add;
    private Course course = new Course();
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    public AddCourseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView =inflater.inflate(R.layout.fragment_add_course, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        department =(Spinner) rootView.findViewById(R.id.spinner_department);
        name =(EditText)rootView.findViewById(R.id.editText_name);
        add =(Button)rootView.findViewById(R.id.add);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Departments, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        department.setAdapter(adapter);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                course.name = name.getText().toString();
                course.department = department.getSelectedItem().toString();
                Toast.makeText(getActivity(), "Done successfully", Toast.LENGTH_LONG).show();
                mDatabase.child("courses").push().setValue(course);
                name.setText("");
            }
        });
        return rootView;
    }

}
