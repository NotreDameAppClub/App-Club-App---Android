package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SingleItemView extends AppCompatActivity {

    TextView txtname, subjectline;
    String content, subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singleitemview);
        Intent i = getIntent();
        content = i.getStringExtra("content");
        subject = i.getStringExtra("subject");
        txtname = (TextView) findViewById(R.id.newsText);
        subjectline = (TextView) findViewById(R.id.subjectTextView);
        txtname.setText(content);
        subjectline.setText(subject);
    }
}
