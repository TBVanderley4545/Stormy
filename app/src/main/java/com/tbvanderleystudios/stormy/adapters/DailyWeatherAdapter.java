package com.tbvanderleystudios.stormy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tbvanderleystudios.stormy.R;
import com.tbvanderleystudios.stormy.weather.DailyWeather;

public class DailyWeatherAdapter extends BaseAdapter {

    // Add a field for the context.
    private Context mContext;
    private DailyWeather[] mDailyWeathers;

    public DailyWeatherAdapter(Context context, DailyWeather[] dailyWeathers) {
        mContext = context;
        mDailyWeathers = dailyWeathers;
    }

    @Override
    public int getCount() {
        return mDailyWeathers.length;
    }

    @Override
    public Object getItem(int position) {
        return mDailyWeathers[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;  // We aren't going to use this. Tag items for easy reference
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Declare a ViewHolder object called holder
        ViewHolder holder;

        if(convertView == null) {
            // This means that the View is brand new, and that it needs to be created.

            // Set the View called convertView to R.layout.daily_list_item. (Just requires a special
            // technique to do so).
            convertView = LayoutInflater.from(mContext).inflate(R.layout.daily_list_item, null);

            // Instantiate the ViewHolder object called holder to a new ViewHolder(), and then
            // set the ViewHolder's Views to the various Views used in the layout file
            // R.layout.daily_list_item.
            holder = new ViewHolder();
            holder.iconImageView = (ImageView) convertView.findViewById(R.id.iconImageView);
            holder.temperatureMinLabel = (TextView) convertView.findViewById(R.id.temperatureMinLabel);
            holder.temperatureMaxLabel = (TextView) convertView.findViewById(R.id.temperatureMaxLabel);
            holder.dayLabel = (TextView) convertView.findViewById(R.id.dayNameLabel);

            convertView.setTag(holder);
        } else {
            // This means that the view has already been used.
            holder = (ViewHolder) convertView.getTag();
        }

        // Create a new DailyWeather object
        DailyWeather dailyWeather = mDailyWeathers[position];
        holder.iconImageView.setImageResource(dailyWeather.getIconId());
        holder.temperatureMinLabel.setText(Integer.toString(dailyWeather.getTemperatureMin()));
        holder.temperatureMaxLabel.setText(Integer.toString(dailyWeather.getTemperatureMax()));

        // This checks to see if the position in the holder is the first position.
        // If it is, that means that it is the current day and that it should be called Today
        // instead of by its name of the day of the week.
        if (position == 0) {
            holder.dayLabel.setText("Today");
        } else {
            holder.dayLabel.setText(dailyWeather.getDayOfTheWeek());
        }

        return convertView;
    }

    private static class ViewHolder {
        ImageView iconImageView;
        TextView temperatureMinLabel;
        TextView temperatureMaxLabel;
        TextView dayLabel;
    }
}
