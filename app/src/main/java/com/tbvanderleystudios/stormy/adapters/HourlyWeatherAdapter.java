package com.tbvanderleystudios.stormy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tbvanderleystudios.stormy.R;
import com.tbvanderleystudios.stormy.weather.HourlyWeather;

public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherAdapter.HourlyWeatherViewHolder> {

    private HourlyWeather[] mHourlyWeathers;
    private Context mContext;

    public HourlyWeatherAdapter(Context context, HourlyWeather[] hourlyWeathers) {
        mContext = context;
        mHourlyWeathers = hourlyWeathers;
    }

    public class HourlyWeatherViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

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

            itemView.setOnClickListener(this);
        }

        public void bindHourlyWeather(HourlyWeather hourlyWeather) {
            mTimeLabel.setText(hourlyWeather.getHourOfTheDay());
            mSummaryLabel.setText(hourlyWeather.getSummary());
            mTemperatureLabel.setText(Integer.toString(hourlyWeather.getTemperature()));
            mIconImageView.setImageResource(hourlyWeather.getIconId());
        }

        @Override
        public void onClick(View v) {
            String time = mTimeLabel.getText().toString();
            String temperature = mTemperatureLabel.getText().toString();
            String summary = mSummaryLabel.getText().toString();
            String message = String.format("At %s, it will be %s and %s", time, temperature, summary);

            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
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
