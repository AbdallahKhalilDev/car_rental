package com.example.android_project.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
    private Context context;
    private int[] images;
    private String[] names;
    private String[] prices;

    public MyAdapter(Context context, int[] images, String[] names, String[] prices) {
        this.context = context;
        this.images = images;
        this.names = names;
        this.prices = prices;
    }

    @Override
    public int getCount() {
        return names == null ? 0 : names.length;
    }

    @Override
    public Object getItem(int position) {
        return names[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Always inflate a new row
        View row = LayoutInflater.from(context).inflate(
                com.example.android_project.R.layout.list_item, parent, false);

        // Find views directly
        TextView name = row.findViewById(com.example.android_project.R.id.item_name);
        TextView price = row.findViewById(com.example.android_project.R.id.item_price);
        ImageView image = row.findViewById(com.example.android_project.R.id.item_image);

        // Set the data
        name.setText(names[position]);
        price.setText(prices[position]);
        image.setImageResource(images[position]);

        return row;
    }
}
