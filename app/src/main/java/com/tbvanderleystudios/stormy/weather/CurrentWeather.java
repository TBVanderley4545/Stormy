package com.tbvanderleystudios.stormy.weather;

import com.tbvanderleystudios.stormy.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CurrentWeather {
    private String mIcon;
    private long mTime;
    private double mTemperature;
    private double mHumidity;
    private double mPrecipChance;
    private String mSummary;
    private String mTimeZone;

    //Creating a constructor for the CurrentWeather class
    public CurrentWeather(String icon, long time, double temperature, double humidity,
                          double precipChance, String summary, String timeZone) {
        mIcon = icon;
        mTime = time;
        mTemperature = temperature;
        mHumidity = humidity;
        mPrecipChance = precipChance;
        mSummary = summary;
        mTimeZone = timeZone;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public int getTemperature() {
        return (int) Math.round(mTemperature);
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public void setHumidity(double humidity) {
        mHumidity = humidity;
    }

    public int getPrecipChance() {
        double precipPercentage = mPrecipChance * 100;
        return (int) Math.round(precipPercentage);
    }

    public void setPrecipChance(double precipChance) {
        mPrecipChance = precipChance;
    }

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    public int getIconId() {
        return Forecast.getIconId(mIcon);
    }

    public String getFormattedTime() {
        // This will format our dates to hours and minutes with an A.M./P.M. mark
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        // We had to set the timezone for the SimpleDateFormat object that we called formatter
        formatter.setTimeZone(TimeZone.getTimeZone(mTimeZone));
        // We need to convert the Unix time in milliseconds into seconds for
        // for the formatter object's required Date argument
        Date dateTime = new Date(mTime * 1000);
        // Create a String from the SimpleDateFormat object.
        String timeString = formatter.format(dateTime);

        return  timeString;
    }
}
