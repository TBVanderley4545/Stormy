package com.tbvanderleystudios.stormy.weather;

public class DailyWeather {
    private long mTime;
    private String mSummary;
    private double mTemperatureMax;
    private double mTemperatureMin;
    private String mIcon;
    private String mTimeZone;

    public DailyWeather (long time, String summary, double temperatureMax, double temperatureMin,
                         String icon, String timeZone) {
        mTime = time;
        mSummary = summary;
        mTemperatureMax = temperatureMax;
        mTemperatureMin = temperatureMin;
        mIcon = icon;
        mTimeZone = timeZone;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public double getTemperatureMax() {
        return mTemperatureMax;
    }

    public void setTemperatureMax(double temperatureMax) {
        mTemperatureMax = temperatureMax;
    }

    public double getTemperatureMin() {
        return mTemperatureMin;
    }

    public void setTemperatureMin(double temperatureMin) {
        mTemperatureMin = temperatureMin;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }
}
