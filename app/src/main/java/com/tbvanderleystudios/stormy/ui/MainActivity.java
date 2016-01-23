package com.tbvanderleystudios.stormy.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tbvanderleystudios.stormy.R;
import com.tbvanderleystudios.stormy.weather.CurrentWeather;
import com.tbvanderleystudios.stormy.weather.DailyWeather;
import com.tbvanderleystudios.stormy.weather.Forecast;
import com.tbvanderleystudios.stormy.weather.HourlyWeather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    // Declare the Forecast data model
    private Forecast mForecast;

    // Instead of declaring private field variables, we used a third party library called
    // ButterKnife. The line of code immediately below this comment is the same as:
    // private TextView mTimeLabel = (TextView) findViewById(R.id.timeLabel);
    @Bind(R.id.timeLabel) TextView mTimeLabel;
    @Bind(R.id.temperatureLabel) TextView mTemperatureLabel;
    @Bind(R.id.locationLabel) TextView mLocationLabel;
    @Bind(R.id.humidityValue) TextView mHumidityValue;
    @Bind(R.id.precipValue) TextView mPrecipValue;
    @Bind(R.id.summaryLabel) TextView mSummaryLabel;
    @Bind(R.id.iconImageView) ImageView mIconImageView;
    @Bind(R.id.refreshImageView) ImageView mRefreshImageView;
    @Bind(R.id.progressBar) ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Below is how we attach ButterKnife
        ButterKnife.bind(this);

        mProgressBar.setVisibility(View.INVISIBLE);

        // Establish latitude and longitude
        final double latitude = 36.209538;
        final double longitude = -81.703724;

        // Create an onClickListener for our mRefreshImageView
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getForecast(latitude, longitude);
            }
        };
        mRefreshImageView.setOnClickListener(listener);

        getForecast(latitude, longitude);
    }

    private void getForecast(double latitude, double longitude) {
        String apiKey = "eab2de607489e1190b98c8107fc269af";

        String forecastURL = "https://api.forecast.io/forecast/" + apiKey + "/" + latitude
                + "," + longitude;

        // Check if network is available first to prevent an exception from being thrown
        if(isNetworkAvailable()) {
            // Set the ProgressBar as Visible and Refresh as Invisible
            toggleRefresh();

            // First create a new OkHTTPClient
            OkHttpClient client = new OkHttpClient();

            // Now create a request that the user will send to the server by chaining methods.
            Request request = new Request.Builder()
                    .url(forecastURL)
                    .build();

            // Wrap the Request in a Call
            Call call = client.newCall(request);

            // Queue up the call
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });

                    alertUserAboutError();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });

                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);

                        if (response.isSuccessful()) {
                            mForecast = parseForecastDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });
                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                }
            });
        } else {
            alertUserAboutNetworkError();
        }
    }

    private void toggleRefresh() {
        if (mProgressBar.getVisibility() == View.INVISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
            mRefreshImageView.setVisibility(View.INVISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mRefreshImageView.setVisibility(View.VISIBLE);
        }
    }

    private void updateDisplay() {
        CurrentWeather currentWeather = mForecast.getCurrentWeather();

        mTemperatureLabel.setText(Integer.toString(currentWeather.getTemperature()));

        // Create a formatted String object
        String timeSetter = getResources().getString(R.string.time_label_setter);
        timeSetter = String.format(timeSetter, currentWeather.getFormattedTime());
        // Set the time wth the formatted String resource.
        mTimeLabel.setText(timeSetter);

        mHumidityValue.setText(Double.toString(currentWeather.getHumidity()));
        mPrecipValue.setText(currentWeather.getPrecipChance() + "%");
        mSummaryLabel.setText(currentWeather.getSummary());

        Drawable drawable = getResources().getDrawable(currentWeather.getIconId());
        mIconImageView.setImageDrawable(drawable);
    }

    private Forecast parseForecastDetails(String jsonData) throws JSONException{
        // Create a Forecast object called forecast.
        Forecast forecast = new Forecast();

        // Set the current weather of the newly created Forecast object using our
        // getCurrentDetails() method.
        forecast.setCurrentWeather(getCurrentDetails(jsonData));

        // Set the hourly forecasts.
        forecast.setHourlyWeathers(getHourlyForecast(jsonData));

        // Set the daily forecasts.
        forecast.setDailyWeathers(getDailyForecast(jsonData));

        return forecast;
    }

    private DailyWeather[] getDailyForecast(String jsonData) throws JSONException {
        // This takes in all of the JSONData that is on the website.
        // It was passed in from parseForecastDetails, which has it passed in from
        // getForecast() when the JSONData was first obtained.
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");

        // Create a new JSONObject to hold the hourly data from the Forecast object above.
        JSONObject daily = forecast.getJSONObject("daily");

        // Create a new JSONArray to hold all of the individual daily objects.
        JSONArray data = daily.getJSONArray("data");

        // Create an array of DailyWeather objects to be updated in a for loop.
        DailyWeather[] dailyForecasts = new DailyWeather[data.length()];

        // Use this for loop to update each JSONObject in the JSONArray
        for(int i=0; i < data.length(); i++) {
            JSONObject jsonDay = data.getJSONObject(i);

            DailyWeather day = new DailyWeather(
                    jsonDay.getLong("time"),
                    jsonDay.getString("summary"),
                    jsonDay.getDouble("temperatureMax"),
                    jsonDay.getDouble("temperatureMin"),
                    jsonDay.getString("icon"),
                    timezone
            );

            dailyForecasts[i] = day;
        }

        return dailyForecasts;
    }

    private HourlyWeather[] getHourlyForecast(String jsonData) throws JSONException{
        // This takes in all of the JSONData that is on the website.
        // It was passed in from parseForecastDetails, which has it passed in from
        // getForecast() when the JSONData was first obtained.
        JSONObject forecast = new JSONObject(jsonData);
        String timezone= forecast.getString("timezone");

        // Create a new JSONObject to hold the hourly data from the Forecast object above.
        JSONObject hourly = forecast.getJSONObject("hourly");

        // Create a JSONArray to hold all of the individual hourly objects
        JSONArray data = hourly.getJSONArray("data");

        // Create an array of HourlyWeather objects to be updated in a for loop
        HourlyWeather[] hourlyForecasts = new HourlyWeather[data.length()];

        // Use this for loop to update each JSONObject in the JSONArray
        for (int i=0; i < data.length(); i++) {
            JSONObject jsonHour = data.getJSONObject(i);

            HourlyWeather hour = new HourlyWeather(
                    jsonHour.getLong("time"),
                    jsonHour.getString("summary"),
                    jsonHour.getDouble("temperature"),
                    jsonHour.getString("icon"),
                    timezone);

            hourlyForecasts[i] = hour;
        }

        return hourlyForecasts;
    }

    private CurrentWeather getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        Log.i(TAG, "From JSON: " + timezone);

        // Use the forecast JSONObject that we created earlier to create another JSONObject
        // that used the currently data.
        JSONObject currently = forecast.getJSONObject("currently");
        // Use the custom constructor to create a new CurrentWeather object
        CurrentWeather currentWeather = new CurrentWeather(
                currently.getString("icon"),
                currently.getLong("time"),
                currently.getDouble("temperature"),
                currently.getDouble("humidity"),
                currently.getDouble("precipProbability"),
                currently.getString("summary"),
                timezone
        );

        Log.d(TAG, currentWeather.getFormattedTime());

        return currentWeather;
    }


    private boolean isNetworkAvailable() {
        // Create a new ConnectivityManager
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Instantiate a NetworkInfo object
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;

        // Check if network is present and connected
        if(networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), getString(R.string.error_dialog_tag));
    }

    private void alertUserAboutNetworkError() {
        NoNetworkDialogFragment dialog = new NoNetworkDialogFragment();
        dialog.show(getFragmentManager(), getString(R.string.error_dialog_tag));
    }

    // This uses ButterKnife to add a click event to the dailyButton
    @OnClick (R.id.dailyButton)
    public void startDailyActivity(View view) {
        Intent intent = new Intent(this, DailyForecastActivity.class);
        startActivity(intent);
    }
}
