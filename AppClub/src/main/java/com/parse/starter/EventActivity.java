package com.parse.starter;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity {

    ListView myListView;
    ArrayList<String> clubEventsDescriptions;
    ArrayList<String> clubEventsLocations;
    ArrayList<Integer> clubEventsStartTimesHours;
    ArrayList<Integer> clubEventsStartTimesMinutes;
    ArrayList<Integer> clubEventsEndTimesHours;
    ArrayList<Integer> clubEventsEndTimesMinutes;
    ArrayList<Integer> clubEventsDays;
    ArrayList<Integer> clubEventsMonths;
    ArrayList<Integer> clubEventsYears;
    ArrayList<Boolean> clubEventsIsRecurring;
    List<ParseObject> ob;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        new RemoteDataTask().execute();

        myListView = (ListView) findViewById(R.id.listView2);

        clubEventsDescriptions = new ArrayList<String>();
        clubEventsLocations = new ArrayList<String>();
        clubEventsStartTimesHours = new ArrayList<Integer>();
        clubEventsStartTimesMinutes = new ArrayList<Integer>();
        clubEventsEndTimesHours = new ArrayList<Integer>();
        clubEventsEndTimesMinutes = new ArrayList<Integer>();
        clubEventsDays = new ArrayList<Integer>();
        clubEventsMonths = new ArrayList<Integer>();
        clubEventsYears = new ArrayList<Integer>();
        clubEventsIsRecurring = new ArrayList<Boolean>();
    }

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ClubEvents");
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
            myListView = (ListView) findViewById(R.id.listView2);
            // Pass the results into an ArrayAdapter
            adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.listview_item);
            for (ParseObject event : ob) {
                adapter.add(String.valueOf(event.get("EventName")));
                clubEventsDescriptions.add(String.valueOf(event.get("EventDescription")));
                clubEventsLocations.add(String.valueOf(event.get("EventLocation")));
                clubEventsStartTimesHours.add((Integer)(event.get("StartTimeHour")));
                clubEventsStartTimesMinutes.add((Integer)(event.get("StartTimeMinute")));
                clubEventsEndTimesHours.add((Integer)(event.get("EndTimeHour")));
                clubEventsEndTimesMinutes.add((Integer)(event.get("EndTimeMinute")));
                clubEventsDays.add((Integer)(event.get("EventDay")));
                clubEventsMonths.add((Integer)(event.get("EventMonth")));
                clubEventsYears.add((Integer)(event.get("EventYear")));
                clubEventsIsRecurring.add((Boolean)(event.get("IsRecurring")));
            }
            // Binds the Adapter to the ListView
            myListView.setAdapter(adapter);
            // Capture button clicks on ListView items
            myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(EventActivity.this, SingleItemViewEvent.class);
                    i.putExtra("name", parent.getItemAtPosition(position).toString());
                    i.putExtra("description", clubEventsDescriptions.get(position));
                    i.putExtra("location", clubEventsLocations.get(position));
                    i.putExtra("startHour", clubEventsStartTimesHours.get(position));
                    i.putExtra("startMinute", clubEventsStartTimesMinutes.get(position));
                    i.putExtra("endHour", clubEventsEndTimesHours.get(position));
                    i.putExtra("endMinute", clubEventsEndTimesMinutes.get(position));
                    i.putExtra("day", clubEventsDays.get(position));
                    i.putExtra("month", clubEventsMonths.get(position));
                    i.putExtra("year", clubEventsYears.get(position));
                    i.putExtra("isR", clubEventsIsRecurring.get(position));
                    startActivity(i);
                }
            });
        }
    }
}
