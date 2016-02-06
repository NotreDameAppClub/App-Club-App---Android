package com.parse.starter;

import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.GregorianCalendar;

public class SingleItemViewEvent extends AppCompatActivity {

    TextView event_name, event_location, event_description, event_time, event_date, event_recur;
    String name, location, description;
    int day, month, year, startHour, startMinute, endHour, endMinute;
    Boolean isR, changeBackStart = false, changeBackEnd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item_view_event);

        Intent i = getIntent();
        name = i.getStringExtra("name");
        location = i.getStringExtra("location");
        description = i.getStringExtra("description");
        startHour = i.getIntExtra("startHour", 1);
        startMinute = i.getIntExtra("startMinute", 1);
        endHour = i.getIntExtra("endHour", 1);
        endMinute = i.getIntExtra("endMinute", 1);
        day = i.getIntExtra("day", 1);
        month = i.getIntExtra("month", 1);
        year = i.getIntExtra("year", 1);
        isR = i.getBooleanExtra("isR", false);

        event_name = (TextView) findViewById(R.id.nameTextView);
        event_location = (TextView) findViewById(R.id.locationTextView);
        event_description = (TextView) findViewById(R.id.descriptionTextView);
        event_time = (TextView) findViewById(R.id.timeTextView);
        event_date = (TextView) findViewById(R.id.dateTextView);
        event_recur = (TextView) findViewById(R.id.isRecurringTextView);

        event_name.setText(name);
        event_location.setText(location);
        event_description.setText(description);
        if(startHour > 12){
            startHour = startHour-12;
            changeBackStart = true;
        }
        if (endHour > 12){
            endHour = endHour - 12;
            changeBackEnd = true;
        }
        if(startMinute < 10){
            event_time.setText(startHour + ":0" + startMinute + " to " + endHour + ":0" + endMinute);
        }else {
            event_time.setText(startHour + ":" + startMinute + " to " + endHour + ":" + endMinute);
        }
        event_date.setText((month+1)+"/"+day+"/"+year);

        if (isR){
            event_recur.setText("This is a weekly event");
        }
        else{
            event_recur.setText("This is not a recurring event");
        }

        Button calButton = (Button) findViewById(R.id.addToCalButton);
        calButton.setOnClickListener(clickHandler);
    }

    View.OnClickListener clickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            Intent calIntent = new Intent(Intent.ACTION_INSERT);
            calIntent.setType("vnd.android.cursor.item/event");
            calIntent.putExtra(CalendarContract.Events.TITLE, name);
            calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, location);
            calIntent.putExtra(CalendarContract.Events.DESCRIPTION, description);

            if (changeBackStart == true){
                startHour += 12;
            }
            if (changeBackEnd == true){
                endHour += 12;
            }
            GregorianCalendar calDateStart = new GregorianCalendar(year, month, day, startHour, startMinute);
            GregorianCalendar calDateEnd = new GregorianCalendar(year, month, day, endHour, endMinute);
            calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calDateStart.getTimeInMillis());
            calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, calDateEnd.getTimeInMillis());

            startActivity(calIntent);
        }
    };
}
