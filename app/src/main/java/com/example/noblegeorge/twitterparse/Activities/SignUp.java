package com.example.noblegeorge.twitterparse.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.noblegeorge.twitterparse.R;
import com.example.noblegeorge.twitterparse.Usercredentials.UserList;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;

public class SignUp extends AppCompatActivity  implements View.OnClickListener{
    UserList userList =new UserList();
    EditText userNameEdittext,passwordEdittext,emailIdEdittext;
    Button signUpButton;
    String userName,password,emailId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Sign Up");

        userNameEdittext= (EditText)findViewById(R.id.usernameEditText1);
        passwordEdittext=(EditText)findViewById(R.id.passwordEditText1);
        emailIdEdittext=(EditText)findViewById(R.id.emailEditText);
        signUpButton =(Button)findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
       if (v.getId()==R.id.signUpButton) {

           userName = userNameEdittext.getText().toString();
           password = passwordEdittext.getText().toString();
           emailId = emailIdEdittext.getText().toString();

           if (userName.matches("") || password.matches("") || emailId.matches("")) {
               Toast.makeText(SignUp.this, "User name,Password and Email id are required", Toast.LENGTH_SHORT).show();
           } else

           {


               ParseUser user = new ParseUser();
               user.setUsername(userName);
               user.setPassword(password);
               user.setEmail(emailId);
               user.signUpInBackground(new SignUpCallback() {
                   @Override
                   public void done(ParseException e) {
                       if (e == null) {
                           Toast.makeText(SignUp.this, "Successfully registered", Toast.LENGTH_SHORT).show();


                       } else {
                           Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                       }
                   }
               });


           }
           Intent intent = new Intent(SignUp.this, MainActivity.class);
           startActivity(intent);
       }

    }
}
