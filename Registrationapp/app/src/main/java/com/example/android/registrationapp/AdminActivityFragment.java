package com.example.android.registrationapp;

import android.content.Intent;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

/**
 * A placeholder fragment containing a simple view.
 */
public class AdminActivityFragment extends Fragment {
    private DatabaseReference mDatabase;
   private String uid;
    private User user = new User();
    private Button button;
    private FirebaseAuth firebaseAuth;
    Organizer organizer;
    Button logout;
    public AdminActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.fragment_admin, container, false);
        Spinner dropdown = (Spinner)rootView.findViewById(R.id.spinner1);
        String[] items = new String[]{"Add student", "Add course","Delete course"};
        firebaseAuth = FirebaseAuth.getInstance();
        logout= (Button) rootView.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               firebaseAuth.signOut();
                startActivity(new Intent(getActivity(),MainActivity.class));
                getActivity().finish();
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                switch (position) {
                    case 0:
                        // Whatever you want to happen when the first item gets selected
                        //Toast.makeText(getActivity(), "add student", Toast.LENGTH_LONG).show();
                        organizer.setSecondFragment(0);
                        break;
                    case 1:
                        // Whatever you want to happen when the second item gets selected
                       // Toast.makeText(getActivity(), "add course", Toast.LENGTH_LONG).show();
                        organizer.setSecondFragment(1);
                        break;
                    case 2:
                        organizer.setSecondFragment(2);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });



        return rootView;
    }
    void setOrganizer (Organizer org){organizer = org;}

}
