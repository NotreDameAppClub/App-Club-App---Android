package com.parse.starter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.SaveCallback;

import java.util.Calendar;

public class AddEventActivity extends AppCompatActivity {

    private int year, month, day, startHour, startMinute, endHour, endMinute;
    private Button dateButton, timeButton, timeEndButton;
    static final int DATE_ID = 999, TIME_ID = 998, TIME_END_ID = 997;
    public boolean startHBC = false, endHBC = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        startHour = endHour = c.get(Calendar.HOUR_OF_DAY);
        startMinute = endMinute = c.get(Calendar.MINUTE);

        addListener();
    }

    public void addListener() {

        dateButton = (Button) findViewById(R.id.dateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_ID);
            }
        });
        timeButton = (Button) findViewById(R.id.timeButton);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_ID);
            }
        });
        timeEndButton = (Button) findViewById(R.id.endTimeButton);
        timeEndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_END_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener, year, month, day);
            case TIME_ID:
                return new TimePickerDialog(this, timePickerListener, startHour, startMinute, false);
            case TIME_END_ID:
                return new TimePickerDialog(this, timePickerListener2, endHour, endMinute, false);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            dateButton.setText(month+1 + "/" + day + "/" + year);
        }
    };

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
            startHour = selectedHour;
            startMinute = selectedMinute;

            if (startHour > 12) {
                startHour = startHour - 12;
                startHBC = true;
            }
            if (startMinute < 10) {
                timeButton.setText(startHour + ":0" + startMinute);
            } else {
                timeButton.setText(startHour + ":" + startMinute);
            }
            timeButton.setText(startHour + ":" + startMinute);
        }
    };

    private TimePickerDialog.OnTimeSetListener timePickerListener2 = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
            endHour = selectedHour;
            endMinute = selectedMinute;

            if (endHour > 12) {
                endHour = endHour - 12;
                endHBC = true;
            }
            if (endMinute < 10) {
                timeEndButton.setText(endHour + ":0" + endMinute);
            } else {
                timeEndButton.setText(endHour + ":" + endMinute);
            }
            timeEndButton.setText(endHour + ":" + endMinute);
        }
    };

    public void buttonClick (View view){
        final EditText nameText = (EditText) findViewById(R.id.editText2);
        EditText locationText = (EditText) findViewById(R.id.editText3);
        EditText descriptionText = (EditText) findViewById(R.id.eventDescriptionText);
        CheckBox cb = (CheckBox) findViewById(R.id.checkBox);

        if (startHBC == true) startHour += 12;
        if (endHBC == true) endHour += 12;

        ParseObject newEventObject = new ParseObject("ClubEvents");  // add new member
        newEventObject.put("EventName", nameText.getText().toString());
        newEventObject.put("EventDescription", descriptionText.getText().toString());
        newEventObject.put("EventLocation", locationText.getText().toString());
        newEventObject.put("EventDay", day);
        newEventObject.put("EventMonth", month);
        newEventObject.put("EventYear", year);
        newEventObject.put("StartTimeHour", startHour);
        newEventObject.put("StartTimeMinute", startMinute);
        newEventObject.put("EndTimeHour", endHour);
        newEventObject.put("EndTimeMinute", endMinute);
        newEventObject.put("isRecurring", cb.isChecked());

        newEventObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), "Event Successfully Added", Toast.LENGTH_LONG).show();
                    ParsePush push = new ParsePush();
                    push.setChannel("AppClub");
                    push.setMessage("Event: " + nameText.getText().toString());
                    push.sendInBackground();
                    Intent intent = new Intent(getApplicationContext(), ExecutiveActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Event Not Successfully Added! Try Again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
