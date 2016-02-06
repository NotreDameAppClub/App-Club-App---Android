package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.SaveCallback;

public class AddNewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);
    }

    public void buttonClick (View view){
        final EditText subjectText = (EditText) findViewById(R.id.subjectTextField);
        EditText contentText = (EditText) findViewById(R.id.contentTextField);

        ParseObject newNewsObject = new ParseObject("ClubNews");  // add new member
        newNewsObject.put("Subject", subjectText.getText().toString());
        newNewsObject.put("Content", contentText.getText().toString());
        newNewsObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), "News Story Successfully Added", Toast.LENGTH_LONG).show();
                    ParsePush push = new ParsePush();
                    push.setChannel("AppClub");
                    push.setMessage("News: " + subjectText.getText().toString());
                    push.sendInBackground();
                    Intent intent = new Intent(getApplicationContext(), ExecutiveActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "News Story Not Successfully Added! Try Again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
