package com.example.android_project.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_project.R;
import com.example.android_project.adapters.CarAdapter;
import com.example.android_project.fragments.CarDetailFragment;
import com.example.android_project.helpers.CurrencyManager;
import com.example.android_project.model.Car;
import com.example.android_project.viewmodel.CarListViewModel;
import com.example.android_project.viewmodel.CurrencyViewModel;

public class CarListActivity extends BaseActivity implements CarAdapter.OnCarClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_car_list);

        RecyclerView carList = findViewById(R.id.car_list);
        carList.setLayoutManager(new LinearLayoutManager(this));

        CarAdapter adapter = new CarAdapter(this, this);
        carList.setAdapter(adapter);

        CarListViewModel viewModel = new ViewModelProvider(this).get(CarListViewModel.class);
        viewModel.getCars().observe(this, adapter::setCars);

        String currency = new CurrencyManager(this).getCurrency();
        CurrencyViewModel currencyViewModel = new ViewModelProvider(this).get(CurrencyViewModel.class);
        currencyViewModel.getRate().observe(this,
                rate -> adapter.setCurrency(currency, rate != null ? rate : 0));
    }

    @Override
    public void onCarClick(Car car) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fm, CarDetailFragment.newInstance(car), "FULLSCREEN_FRAGMENT")
                .addToBackStack(null)
                .commit();
    }
}
