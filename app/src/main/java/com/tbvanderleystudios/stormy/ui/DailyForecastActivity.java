package com.tbvanderleystudios.stormy.ui;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tbvanderleystudios.stormy.R;
import com.tbvanderleystudios.stormy.adapters.DailyWeatherAdapter;
import com.tbvanderleystudios.stormy.weather.DailyWeather;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DailyForecastActivity extends Activity {

    private DailyWeather[] mDailyWeathers;

    @Bind(android.R.id.list)
    ListView mListView;
    @Bind(android.R.id.empty)
    TextView mEmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        // Get the parcelable objects from the intent, and store them in a Parcelable array called
        // parcelables
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.DAILY_FORECAST);
        // Copy the array of Parcelable objects into an array of DailyWeather objects.
        mDailyWeathers = Arrays.copyOf(parcelables, parcelables.length, DailyWeather[].class);

        // Instantiate a DailyWeatherAdapter here.
        DailyWeatherAdapter adapter = new DailyWeatherAdapter(this, mDailyWeathers);

        // Set the listAdapter to the custom adapter that we created.
        mListView.setAdapter(adapter);

        // Set the TextView if empty.
        mListView.setEmptyView(mEmptyTextView);

        // Set onItemClickListener.
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dayOfTheWeek = mDailyWeathers[position].getDayOfTheWeek();
                String conditions = mDailyWeathers[position].getSummary();
                String lowTemp = Integer.toString(mDailyWeathers[position].getTemperatureMin());
                String highTemp = Integer.toString(mDailyWeathers[position].getTemperatureMax());
                String message = String.format("On %s the low will be %s and the high will be %s." +
                        " It will be %s", dayOfTheWeek, lowTemp, highTemp, conditions);
                Toast.makeText(DailyForecastActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
