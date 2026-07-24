package com.example.android_project.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android_project.R;
import com.example.android_project.helpers.BookingDialog;
import com.example.android_project.model.Car;

public class ListingFragment2 extends Fragment {

    public ListingFragment2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_listing2, container, false);
        root.setClickable(true);
        root.setFocusable(true);
        root.setFocusableInTouchMode(true);

        Bundle args = getArguments();
        Car car = args != null ? (Car) args.getSerializable("car") : null;

        Button book_now = root.findViewById(R.id.book_now);
        book_now.setOnClickListener(v -> {
            if (car != null) {
                BookingDialog.show(requireContext(), car);
            }
        });
        return root;
    }
}
