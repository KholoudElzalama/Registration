package com.example.android.registrationapp;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddStudentFragment extends Fragment {

    private Button add;
    private EditText name_edittext;
    private DatabaseReference mDatabase;
    private  User student = new User();
    private FirebaseAuth mAuth;
    private  EditText email_edittext;
    private  EditText password_edittext;
    String TAG = "signup";
    private Spinner department;
    private Spinner level;
    private String uid;
    public AddStudentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =inflater.inflate(R.layout.fragment_add_student, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        add= (Button) rootView.findViewById(R.id.add_student);
        name_edittext = (EditText)rootView.findViewById(R.id.editText_name);
        department =(Spinner) rootView.findViewById(R.id.spinner_department);
        level =(Spinner) rootView.findViewById(R.id.spinner_level);
        email_edittext =(EditText)rootView.findViewById(R.id.editText_studentemail);
        password_edittext =(EditText)rootView.findViewById(R.id.editText_studentpassword);
       ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Departments, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        department.setAdapter(adapter);
         adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Levels, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        level.setAdapter(adapter);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              if(email_edittext.getText().toString().isEmpty()||password_edittext.getText().toString().isEmpty()||name_edittext.getText().toString().isEmpty()){
                  Toast.makeText(getActivity(),"Fill all fields",Toast.LENGTH_LONG).show();
              }
                else{
//                  Firebase ref = new Firebase("https://registration-app-f44af.firebaseio.com/");
//
//                  ref.createUser(email_edittext.getText().toString(), password_edittext.getText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {
//                      @Override
//                      public void onSuccess(Map<String, Object> result) {
//                          System.out.println("Successfully created user account with uid: " + result.get("uid"));
//
//                          Log.d("userid", result.get("uid").toString());
//                          student.name = name_edittext.getText().toString();
//                          student.depratment = department.getSelectedItem().toString();
//                          student.role="student";
//                          Toast.makeText(getActivity(), "Done successfully", Toast.LENGTH_LONG).show();
//                          mDatabase.child("users").child(result.get("uid").toString()).setValue(student);
//                          name_edittext.setText("");
//                          email_edittext.setText("");
//                          password_edittext.setText("");
//                      }
//                      @Override
//                      public void onError(FirebaseError firebaseError) {
//                          Log.d("signin","errooooor");
//                          Toast.makeText(getActivity(), firebaseError.getMessage(), Toast.LENGTH_LONG).show();
//                          // there was an error
//                      }
//                  });







                   Task<AuthResult> authResultTask = mAuth.createUserWithEmailAndPassword(email_edittext.getText().toString(), password_edittext.getText().toString())
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (!task.isSuccessful()) {
                                    try {
                                        throw task.getException();
                                    } catch(FirebaseAuthWeakPasswordException e) {
                                        Toast.makeText(getActivity(), "Weak Password!!", Toast.LENGTH_LONG).show();
                                    } catch(FirebaseAuthInvalidCredentialsException e) {
                                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                                    } catch(FirebaseAuthUserCollisionException e) {
                                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                                    } catch(Exception e) {
                                        Log.e(TAG, e.getMessage());
                                    }
                                }
                                else {
                                    Log.d("signup", "createUserWithEmail:onComplete:" + task.isSuccessful());
                                    FirebaseUser user = task.getResult().getUser();
                                    Log.d("userid", user.getUid());
                                    student.name = name_edittext.getText().toString();
                                    student.depratment = department.getSelectedItem().toString();
                                    student.role="student";
                                    Toast.makeText(getActivity(), "Done successfully", Toast.LENGTH_LONG).show();
                                    mDatabase.child("users").child(user.getUid()).setValue(student);
                                    name_edittext.setText("");
                                    email_edittext.setText("");
                                    password_edittext.setText("");
                                }
                            }


                        }
                        );



            }
            }
        });
        return rootView;
    }



}
