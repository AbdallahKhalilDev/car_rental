package com.example.android_project.helpers;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class AppExecutors {

    private static volatile AppExecutors instance;

    private final Executor diskIO;
    private final Executor mainThread;

    public static AppExecutors getInstance() {
        if (instance == null) {
            synchronized (AppExecutors.class) {
                if (instance == null) {
                    instance = new AppExecutors();
                }
            }
        }
        return instance;
    }

    private AppExecutors() {
        // one thread, so two database operations can never contend for the same lock
        diskIO = Executors.newSingleThreadExecutor(r -> new Thread(r, "disk-io"));
        Handler handler = new Handler(Looper.getMainLooper());
        mainThread = handler::post;
    }

    public Executor diskIO() {
        return diskIO;
    }

    public Executor mainThread() {
        return mainThread;
    }
}
