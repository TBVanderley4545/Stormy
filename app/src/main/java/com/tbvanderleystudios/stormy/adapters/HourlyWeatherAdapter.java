package com.tbvanderleystudios.stormy.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tbvanderleystudios.stormy.R;
import com.tbvanderleystudios.stormy.weather.HourlyWeather;

public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherAdapter.HourlyWeatherViewHolder> {

    private HourlyWeather[] mHourlyWeathers;

    public HourlyWeatherAdapter(HourlyWeather[] hourlyWeathers) {
        mHourlyWeathers = hourlyWeathers;
    }

    public class HourlyWeatherViewHolder extends RecyclerView.ViewHolder {

        public TextView mTimeLabel;
        public TextView mSummaryLabel;
        public TextView mTemperatureLabel;
        public ImageView mIconImageView;

        public HourlyWeatherViewHolder(View itemView) {
            super(itemView);

            mTimeLabel = (TextView) itemView.findViewById(R.id.timeLabel);
            mSummaryLabel = (TextView) itemView.findViewById(R.id.summaryLabel);
            mTemperatureLabel = (TextView) itemView.findViewById(R.id.temperatureLabel);
            mIconImageView = (ImageView) itemView.findViewById(R.id.iconImageView);
        }

        public void bindHourlyWeather(HourlyWeather hourlyWeather) {
            mTimeLabel.setText(hourlyWeather.getHourOfTheDay());
            mSummaryLabel.setText(hourlyWeather.getSummary());
            mTemperatureLabel.setText(Integer.toString(hourlyWeather.getTemperature()));
            mIconImageView.setImageResource(hourlyWeather.getIconId());
        }
    }

    @Override
    public HourlyWeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hourly_list_item, parent, false);

        HourlyWeatherViewHolder viewHolder = new HourlyWeatherViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HourlyWeatherViewHolder holder, int position) {
        holder.bindHourlyWeather(mHourlyWeathers[position]);
    }

    @Override
    public int getItemCount() {
        return mHourlyWeathers.length;
    }


}
