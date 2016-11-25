package com.example.android.registrationapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private Firebase firebaseRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private  Button signin;
    private  EditText email;
    private EditText password;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View RootView =inflater.inflate(R.layout.fragment_main, container, false);
//        firebaseRef = new Firebase("https://registration-app-f44af.firebaseio.com/");
        mAuth = FirebaseAuth.getInstance();
        final SharedPreferences pref = getActivity().getPreferences(0);
        final SharedPreferences.Editor edt = pref.edit();

        signin = (Button) RootView.findViewById(R.id.signin_button);
        email=(EditText)  RootView.findViewById(R.id.email_edittext);
        password = (EditText)  RootView.findViewById(R.id.password_edittext);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("signin", "onAuthStateChanged:signed_in:" + user.getUid());
                    if(user.getUid().equals(getString(R.string.admin_auth))){
                        Intent intent = new Intent(getActivity(),AdminActivity.class);
                        intent.putExtra("uid",user.getUid());
                        startActivity(intent);
                        getActivity().finish();
                        Log.d("signin", "adminnnnnnnnn");
                    }
                    else{
                        Intent intent = new Intent(getActivity(),StudentActivity.class);
                        startActivity(intent);
                        getActivity().finish();

                    }
                } else {

                    // User is signed out
                    Log.d("signin", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               logIn();
            }
        });

         return RootView;
    }

   private void logIn() {
       String Email = email.getText().toString();
       String Password = password.getText().toString();
       if (Email.isEmpty() || Password.isEmpty()) {
           Toast.makeText(getActivity(),"Fields are empty",Toast.LENGTH_LONG).show();
       } else {
           mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           Log.d("LOGIN", "signInWithEmail:onComplete:" + task.isSuccessful());

                           // If sign in fails, display a message to the user. If sign in succeeds
                           // the auth state listener will be notified and logic to handle the
                           // signed in user can be handled in the listener.
                           if (!task.isSuccessful()) {
                               Log.w("LOGIN", "signInWithEmail:failed", task.getException());
                               Toast.makeText(getActivity(),"Sign in problem",Toast.LENGTH_LONG).show();

                           }

                       }
                   });
       }
   }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
