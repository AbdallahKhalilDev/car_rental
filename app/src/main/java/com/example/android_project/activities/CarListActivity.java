package com.example.android_project.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_project.R;
import com.example.android_project.adapters.CarAdapter;
import com.example.android_project.data.repository.CarRepository;
import com.example.android_project.fragments.ListingFragment;
import com.example.android_project.fragments.ListingFragment2;
import com.example.android_project.fragments.ListingFragment3;
import com.example.android_project.fragments.ListingFragment4;
import com.example.android_project.model.Car;

public class CarListActivity extends AppCompatActivity implements CarAdapter.OnCarClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_car_list);

        RecyclerView carList = findViewById(R.id.car_list);
        carList.setLayoutManager(new LinearLayoutManager(this));

        CarAdapter adapter = new CarAdapter(this, this);
        carList.setAdapter(adapter);

        new CarRepository(this).getAllCars(adapter::setCars);
    }

    @Override
    public void onCarClick(Car car) {
        Fragment fragment;
        switch (car.getId()) {
            case 1:
                fragment = new ListingFragment();
                break;
            case 2:
                fragment = new ListingFragment2();
                break;
            case 3:
                fragment = new ListingFragment3();
                break;
            case 4:
                fragment = new ListingFragment4();
                break;
            default:
                return;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fm, fragment, "FULLSCREEN_FRAGMENT")
                .addToBackStack(null)
                .commit();
    }
}
