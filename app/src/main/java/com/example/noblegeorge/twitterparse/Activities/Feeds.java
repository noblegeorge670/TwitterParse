package com.example.noblegeorge.twitterparse.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.noblegeorge.twitterparse.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Feeds extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);
        setTitle("Your feed");

         final ListView feedsListView=   findViewById(R.id.feedsListView);
        final List<Map<String,String>> feeds =new ArrayList<>();


        ParseQuery<ParseObject> query =new ParseQuery<ParseObject>("Tweet");
        //query.whereEqualTo("username","jefin");
        query.whereContainedIn("username",ParseUser.getCurrentUser().getList("followers"));
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {


                if (e==null)
                {
                    if (objects.size()>0)
                    {


                        for (ParseObject tweet:objects)
                        {
                            Map<String,String> tweetData=new HashMap<>();
                            tweetData.put("data",tweet.getString("tweet"));
                            tweetData.put("username",tweet.getString("username"));
                            feeds.add(tweetData);

                        }

                    }

                    SimpleAdapter adapter = new SimpleAdapter(Feeds.this,feeds,android.R.layout.simple_list_item_2,
                            new String[]{"data","username"},new int[]{android.R.id.text1,android.R.id.text2});
                    feedsListView.setAdapter(adapter);
                }
            }
        });
    }

}
