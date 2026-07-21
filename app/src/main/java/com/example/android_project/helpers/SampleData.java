package com.example.android_project.helpers;

import android.content.Context;

import com.example.android_project.R;
import com.example.android_project.model.Car;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Placeholder until the SQLite-backed CarRepository replaces it.
public final class SampleData {

    private SampleData() {
    }

    public static List<Car> getCars(Context context) {
        return new ArrayList<>(Arrays.asList(
                new Car(1,
                        context.getString(R.string.merc),
                        300,
                        R.drawable.e450,
                        context.getString(R.string.engine_merc),
                        context.getString(R.string.merc_hp),
                        context.getString(R.string.merc_transmission),
                        5,
                        context.getString(R.string.merc_features)),
                new Car(2,
                        context.getString(R.string.audi),
                        230,
                        R.drawable.q5,
                        context.getString(R.string.engine_audi),
                        context.getString(R.string.audi_hp),
                        context.getString(R.string.audi_transmission),
                        5,
                        context.getString(R.string.audi_features)),
                new Car(3,
                        context.getString(R.string.porsche),
                        550,
                        R.drawable.turbo_s,
                        context.getString(R.string.engine_porsche),
                        context.getString(R.string.porsche_hp),
                        context.getString(R.string.porsche_transmission),
                        4,
                        context.getString(R.string.porsche_features))));
    }
}
