package com.tbvanderleystudios.stormy.ui;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tbvanderleystudios.stormy.R;
import com.tbvanderleystudios.stormy.adapters.HourlyWeatherAdapter;
import com.tbvanderleystudios.stormy.weather.HourlyWeather;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HourlyForecastActivity extends AppCompatActivity {

    private HourlyWeather[] mHourlyWeathers;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_forecast);
        ButterKnife.bind(this);


        Intent intent = getIntent();

        // Get the parcelable objects from the intent, and store them in a Parcelable array called
        // parcelables
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.HOURLY_FORECAST);
        // Copy the array of Parcelable objects into an array of HourlyWeather objects.
        mHourlyWeathers = Arrays.copyOf(parcelables, parcelables.length, HourlyWeather[].class);

        // Set the Adapter for the RecyclerView
        HourlyWeatherAdapter adapter = new HourlyWeatherAdapter(mHourlyWeathers);
        mRecyclerView.setAdapter(adapter);

        // Set the LayoutManger for the RecyclerView.
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);
    }
}
