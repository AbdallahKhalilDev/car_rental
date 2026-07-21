package com.example.android_project.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_project.R;
import com.example.android_project.model.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    public interface OnCarClickListener {
        void onCarClick(Car car);
    }

    private final Context context;
    private final OnCarClickListener listener;
    private final List<Car> cars = new ArrayList<>();

    public CarAdapter(Context context, OnCarClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setCars(List<Car> newCars) {
        cars.clear();
        if (newCars != null) {
            cars.addAll(newCars);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.viewholder_car, parent, false);
        return new CarViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = cars.get(position);

        holder.name.setText(car.getName());
        holder.price.setText(context.getString(R.string.price_usd,
                String.format(Locale.US, "%.0f", car.getPricePerDay())));
        holder.image.setImageResource(car.getImageResId());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCarClick(car);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    static class CarViewHolder extends RecyclerView.ViewHolder {

        final TextView name;
        final TextView price;
        final ImageView image;

        CarViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            price = itemView.findViewById(R.id.item_price);
            image = itemView.findViewById(R.id.item_image);
        }
    }
}
