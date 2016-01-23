package com.tbvanderleystudios.stormy.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.tbvanderleystudios.stormy.R;
import com.tbvanderleystudios.stormy.adapters.DailyWeatherAdapter;
import com.tbvanderleystudios.stormy.weather.DailyWeather;

import java.util.Arrays;

public class DailyForecastActivity extends ListActivity {

    private DailyWeather[] mDailyWeathers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);

        Intent intent = getIntent();
        // Get the parcelable objects from the intent, and store them in a Parcelable array called
        // parcelables
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.DAILY_FORECAST);
        // Copy the array of Parcelable objects into an array of DailyWeather objecs.
        mDailyWeathers = Arrays.copyOf(parcelables, parcelables.length, DailyWeather[].class);

        // Instantiate a DailyWeatherAdapter here.
        DailyWeatherAdapter adapter = new DailyWeatherAdapter(this, mDailyWeathers);

        // Set the listAdapter to the custom adapter that we created.
        setListAdapter(adapter);
    }
}
