package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.parse.ParseInstallation;
import com.parse.ParsePush;

public class ExecutiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executive);

        Button viewNews = (Button) findViewById(R.id.viewFeedButton);
        Button makeNews = (Button) findViewById(R.id.addToFeedButton);
        Button viewEvents = (Button) findViewById(R.id.eventViewButton);
        Button addEvent = (Button) findViewById(R.id.eventAddButton);

        viewNews.setOnClickListener(clickHandler);
        makeNews.setOnClickListener(clickHandler);
        viewEvents.setOnClickListener(clickHandler);
        addEvent.setOnClickListener(clickHandler);

        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParsePush.subscribeInBackground("AppClub");
    }

    View.OnClickListener clickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.viewFeedButton:
                    Intent intent = new Intent(getApplicationContext(), NewsActivity.class);
                    startActivity(intent);
                    break;
                case R.id.addToFeedButton:
                    Intent intent2 = new Intent(getApplicationContext(), AddNewsActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.eventViewButton:
                    Intent intent3 = new Intent(getApplicationContext(), EventActivity.class);
                    startActivity(intent3);
                    break;
                case R.id.eventAddButton:
                    Intent intent4 = new Intent(getApplicationContext(), AddEventActivity.class);
                    startActivity(intent4);
                    break;
            }
        }
    };
}
