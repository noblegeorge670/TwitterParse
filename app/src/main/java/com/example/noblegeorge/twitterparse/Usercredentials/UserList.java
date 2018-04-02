package com.example.noblegeorge.twitterparse.Usercredentials;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.example.noblegeorge.twitterparse.Activities.Feeds;
import com.example.noblegeorge.twitterparse.Activities.MainActivity;
import com.example.noblegeorge.twitterparse.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by noblegeorge on 2018-03-30.
 */

public class UserList extends AppCompatActivity{
    ListView userListView;
    ArrayAdapter adapter;
    ArrayList<String>users =new ArrayList<String>();
    List<String>followers =new ArrayList<>();


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater =new MenuInflater(this);
       inflater.inflate(R.menu.my_menu,menu);

       setTitle("Users List");


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId()==R.id.tweet) {

            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Send a tweet");
            final EditText tweetEditText =new EditText(this);
            builder.setView(tweetEditText);
            builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.i("save",tweetEditText.getText().toString());

                    ParseObject tweet =new ParseObject("Tweet");
                    tweet.put("username",ParseUser.getCurrentUser().getUsername());
                    tweet.put("tweet",tweetEditText.getText().toString());
                    tweet.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            Toast.makeText(UserList.this,"Successfully send your tweet",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();



        }
        else if (item.getItemId()==R.id.logout)
        {
            ParseUser.logOut();
            Intent intent =new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId()==R.id.feeds)
        {
            Intent intent=new Intent(UserList.this, Feeds.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userslist);


        if (ParseUser.getCurrentUser().get("followers")==null)

        {


            ParseUser.getCurrentUser().put("followers",followers);

        }

         userListView =(ListView)findViewById(R.id.usersList);
         userListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
          adapter =new ArrayAdapter(this,android.R.layout.simple_list_item_checked,users);

        userListView.setAdapter(adapter);
        diplayUsers();
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CheckedTextView checkedTextView =(CheckedTextView) view;
                if (checkedTextView.isChecked())
                {
                    Log.i("save","selected");
                    followers.add(users.get(position));

                    ParseUser.getCurrentUser().put("followers",followers);

                        ParseUser.getCurrentUser().saveInBackground();


                }
                else if (!checkedTextView.isChecked())
                {
                    Log.i("save","unselected");
                    Log.i("save",users.get(position).toString());
                    followers.remove(users.get(position));
                   ParseUser.getCurrentUser().put("followers",followers);
                    try {
                        ParseUser.getCurrentUser().save();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                adapter.notifyDataSetChanged();
            }
        });
    }







public void diplayUsers()
{
    users.clear();
    ParseQuery<ParseUser> query =ParseUser.getQuery();
    query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
    query.addAscendingOrder("username");
    query.findInBackground(new FindCallback<ParseUser>() {
        @Override
        public void done(List<ParseUser> objects, ParseException e) {
            if (e==null)
            {
                if (objects.size()>0)
                {
                    for (ParseUser user:objects)
                    {
                        users.add(user.getUsername());
                    }

                    adapter.notifyDataSetChanged();
                }

                for (String username:users)
                {
                    if (ParseUser.getCurrentUser().getList("followers").contains(username))
                    {
                        userListView.setItemChecked(users.indexOf(username),true);
                    }
                }
            }
            else
            {
                Log.i("error",e.getMessage().toString());
            }
        }
    });


}
}
