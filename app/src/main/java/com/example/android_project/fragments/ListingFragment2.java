package com.example.android_project.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.android_project.R;

public class ListingFragment2 extends Fragment {

    public ListingFragment2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_listing2, container, false);
        root.setClickable(true);
        root.setFocusable(true);
        root.setFocusableInTouchMode(true);

        Button book_now = root.findViewById(R.id.book_now);
        book_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), R.string.booked_tst, Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }
}
