package com.tbvanderleystudios.stormy;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    // Declare the CurrentWeather data model
    private CurrentWeather mCurrentWeather;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Below is how we attach ButterKnife
        ButterKnife.bind(this);

        // Establish latitude and longitude
        final double latitude = 36.209538;
        final double longitude = -81.703724;

        // Create an onClickListener for out mRefreshImageView
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

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    // Receive a Response from the Call by using the execute method.
                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);

                        if (response.isSuccessful()) {
                            mCurrentWeather = getCurrentDetails(jsonData);
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
                    } catch(JSONException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                }
            });
        } else {
            alertUserAboutNetworkError();
        }
    }

    private void updateDisplay() {
        mTemperatureLabel.setText(Integer.toString(mCurrentWeather.getTemperature()));

        // Create a formatted String object
        String timeSetter = getResources().getString(R.string.time_label_setter);
        timeSetter = String.format(timeSetter, mCurrentWeather.getFormattedTime());
        // Set the time wth the formatted String resource.
        mTimeLabel.setText(timeSetter);

        mHumidityValue.setText(Double.toString(mCurrentWeather.getHumidity()));
        mPrecipValue.setText(mCurrentWeather.getPrecipChance() + "%");
        mSummaryLabel.setText(mCurrentWeather.getSummary());

        Drawable drawable = getResources().getDrawable(mCurrentWeather.getIconId());
        mIconImageView.setImageDrawable(drawable);
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
}
