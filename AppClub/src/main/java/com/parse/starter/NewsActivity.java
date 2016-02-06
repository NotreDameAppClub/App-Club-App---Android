package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.os.AsyncTask;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

    ListView myListView;
    ArrayList<String> clubNewsContents;
    List<ParseObject> ob;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed);
        new RemoteDataTask().execute();

        myListView = (ListView) findViewById(R.id.listView);

        clubNewsContents = new ArrayList<String>();

    //    ParseInstallation.getCurrentInstallation().saveInBackground();
    //    ParsePush.subscribeInBackground("AppClub");
    }

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ClubNews");
            try {
                ob = query.find();
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            myListView = (ListView) findViewById(R.id.listView);
            // Pass the results into an ArrayAdapter
            adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.listview_item);
            for (ParseObject news : ob) {
                adapter.add(String.valueOf(news.get("Subject")));
                clubNewsContents.add(String.valueOf(news.get("Content")));
            }
            // Binds the Adapter to the ListView
            myListView.setAdapter(adapter);
            // Capture button clicks on ListView items
            myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(NewsActivity.this, SingleItemView.class);
                    i.putExtra("subject", parent.getItemAtPosition(position).toString());
                    i.putExtra("content", clubNewsContents.get(position));
                    startActivity(i);
                }
            });
        }
    }
}
