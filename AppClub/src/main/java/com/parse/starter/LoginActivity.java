package com.parse.starter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.SaveCallback;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    public void loginButtonClick(View view) {
        final EditText textField = (EditText) findViewById(R.id.editText);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("AppClubMember");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                boolean isFound = false;
                boolean isExec = false;
                if (e == null) {
                    for (ParseObject object : objects) {
                        if (String.valueOf(object.get("netid")).equals(textField.getText().toString())) {
                            isFound = true;
                            isExec = (Boolean)object.get("isExecutive");
                            break;
                        }
                    }
                    if (isFound == true){
                        if (isExec == false){
                            Intent intent = new Intent(getApplicationContext(), GenMemberActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Intent intent = new Intent(getApplicationContext(), ExecutiveActivity.class);
                            startActivity(intent);
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "You are not in the App Club. Sign up!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public void signupButtonClick(View view){
        final EditText textField = (EditText) findViewById(R.id.editText);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("AppClubMember");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                boolean isFound = false;
                if (e == null) {
                    for (ParseObject object : objects) {
                        if (String.valueOf(object.get("netid")).equals(textField.getText().toString())) {
                            isFound = true;
                            Toast.makeText(getApplicationContext(), "You are already in the App Club!", Toast.LENGTH_LONG).show();
                            break;
                        }
                    }
                    if (isFound == false){
                        ParseObject newMember = new ParseObject("AppClubMember");
                        newMember.put("netid", textField.getText().toString());
                        newMember.put("isExecutive", false);
                        newMember.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Toast.makeText(getApplicationContext(), "You have been successfully added to the App Club!", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), NewsActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Sign up failure occurred! Try Again", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                }
            }
        });

    }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    ParseAnalytics.trackAppOpenedInBackground(getIntent());

    /*ParseObject testObject = new ParseObject("AppClubMember");  // add new member
    testObject.put("netid", "npelleg1");
    testObject.saveInBackground(); */
  }
}
