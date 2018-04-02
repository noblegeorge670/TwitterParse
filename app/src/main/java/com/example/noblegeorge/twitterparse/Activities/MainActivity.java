package com.example.noblegeorge.twitterparse.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noblegeorge.twitterparse.R;
import com.example.noblegeorge.twitterparse.Usercredentials.UserList;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import static com.parse.ParseAnalytics.trackAppOpenedInBackground;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
     TextView userNametextview,passwordTextview;
     String userName,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Twitter Clone");
        TextView signupTextView =(TextView)findViewById(R.id.signup);
        signupTextView.setOnClickListener(this);
        userNametextview =(EditText)findViewById(R.id.usernameEditText);
        passwordTextview=(EditText)findViewById(R.id.passwordEditText);
        Button signInButton =(Button)findViewById(R.id.signInButton);
        signInButton.setOnClickListener(this);

          if (ParseUser.getCurrentUser()!=null)
          {
              Intent intent =new Intent(MainActivity.this, UserList.class);
              startActivity(intent);
          }

        /*ParseObject sampleObject =new ParseObject("Twitter");

        sampleObject.put("user","noble");

        sampleObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if (e==null)
                {
                    Log.i("result","success");
                }
                else
                {
                    Log.i("result","failed");
                }


            }
        });*/


        trackAppOpenedInBackground(getIntent());


    }

    @Override
    public void onClick(View v) {
        userName=userNametextview.getText().toString();
        password=passwordTextview.getText().toString();
        if (v.getId()==R.id.signup)
        {
            Intent intent = new Intent(MainActivity.this,SignUp.class);
            startActivity(intent);
        }
        else if (v.getId()==R.id.signInButton)
        {
            ParseUser.logInInBackground(userName, password, new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (e==null)
                    {
                        Toast.makeText(MainActivity.this,"Login success",Toast.LENGTH_SHORT).show();
                        Intent intent =new Intent(MainActivity.this, UserList.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}
