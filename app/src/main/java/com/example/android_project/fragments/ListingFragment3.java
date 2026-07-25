package com.example.android_project.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android_project.R;
import com.example.android_project.helpers.BookingDialog;
import com.example.android_project.helpers.CurrencyFormatter;
import com.example.android_project.helpers.CurrencyManager;
import com.example.android_project.model.Car;
import com.example.android_project.viewmodel.CurrencyViewModel;

public class ListingFragment3 extends Fragment {

    private double rate = 0;

    public ListingFragment3() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_listing3, container, false);
        root.setClickable(true);
        root.setFocusable(true);
        root.setFocusableInTouchMode(true);

        Bundle args = getArguments();
        Car car = args != null ? (Car) args.getSerializable("car") : null;

        TextView priceValue = root.findViewById(R.id.price_value);
        String currency = new CurrencyManager(requireContext()).getCurrency();
        if (car != null) {
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
