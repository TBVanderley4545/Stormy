package com.tbvanderleystudios.stormy.ui;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.tbvanderleystudios.stormy.R;

public class DailyForecastActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);

        String[] daysOfTheWeek = { "Sunday",
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday" };

        // This is an ArrayAdapter of String objects as identified in between the angle brackets.
        ArrayAdapter<String> adapter;
        // Instantiate the ArrayAdapter that we created on the line above.
        // 1) We give the context of this.
        // 2) We want to use the pre generated layout of android.R.layout.simple_list_item_1
        // 3) We use the String array of daysOfTheWeek that we created above as the data to adapt.
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                daysOfTheWeek);
        // set the list adapter with our adapter object.
        setListAdapter(adapter);
    }
}
