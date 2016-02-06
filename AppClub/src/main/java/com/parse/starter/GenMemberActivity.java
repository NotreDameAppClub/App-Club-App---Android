package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.parse.ParseInstallation;
import com.parse.ParsePush;

public class GenMemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gen_member);

        Button viewNews = (Button) findViewById(R.id.viewNewsButton);
        Button viewEvents = (Button) findViewById(R.id.eventFeedButton);

        viewNews.setOnClickListener(clickHandler);
        viewEvents.setOnClickListener(clickHandler);

        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParsePush.subscribeInBackground("AppClub");
    }

    View.OnClickListener clickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.viewNewsButton:
                    Intent intent = new Intent(getApplicationContext(), NewsActivity.class);
                    startActivity(intent);
                    break;
                case R.id.eventFeedButton:
                    Intent intent2 = new Intent(getApplicationContext(), EventActivity.class);
                    startActivity(intent2);
                    break;
            }
        }
    };
}
