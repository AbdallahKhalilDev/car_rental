package com.example.android_project.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android_project.R;
import com.example.android_project.helpers.BookingDialog;
import com.example.android_project.helpers.CurrencyFormatter;
import com.example.android_project.helpers.CurrencyManager;
import com.example.android_project.model.Car;
import com.example.android_project.viewmodel.CurrencyViewModel;

public class CarDetailFragment extends Fragment {

    private double rate = 0;

    public CarDetailFragment() {
    }

    public static CarDetailFragment newInstance(Car car) {
        CarDetailFragment fragment = new CarDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("car", car);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_car_detail, container, false);
        root.setClickable(true);
        root.setFocusable(true);
        root.setFocusableInTouchMode(true);

        Bundle args = getArguments();
        Car car = args != null ? (Car) args.getSerializable("car") : null;

        String currency = new CurrencyManager(requireContext()).getCurrency();
        TextView priceValue = root.findViewById(R.id.price_value);

        if (car != null) {
            ((TextView) root.findViewById(R.id.txt_listing)).setText(car.getName());
            ((ImageView) root.findViewById(R.id.car_image)).setImageResource(car.getImageResId());
            ((TextView) root.findViewById(R.id.engine_value)).setText(car.getEngine());
            ((TextView) root.findViewById(R.id.horsepower_value)).setText(car.getHorsepower());
            ((TextView) root.findViewById(R.id.transmission_value)).setText(car.getTransmission());
            ((TextView) root.findViewById(R.id.seats_value)).setText(String.valueOf(car.getSeats()));
            ((TextView) root.findViewById(R.id.features_value)).setText(car.getFeatures());
            priceValue.setText(CurrencyFormatter.format(requireContext(), car.getPricePerDay(), currency, rate));
        }

        // the detail price shares the list's rate, so it reads the same activity-scoped
        // CurrencyViewModel; when the live rate lands it re-renders here as well
        CurrencyViewModel currencyViewModel =
                new ViewModelProvider(requireActivity()).get(CurrencyViewModel.class);
        currencyViewModel.getRate().observe(getViewLifecycleOwner(), r -> {
            rate = r != null ? r : 0;
            if (car != null) {
                priceValue.setText(CurrencyFormatter.format(requireContext(), car.getPricePerDay(), currency, rate));
            }
        });

        Button book_now = root.findViewById(R.id.book_now);
        book_now.setOnClickListener(v -> {
            if (car != null) {
                BookingDialog.show(requireContext(), car, currency, rate);
            }
        });
        return root;
    }
}
