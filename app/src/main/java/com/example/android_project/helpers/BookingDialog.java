package com.example.android_project.helpers;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.android_project.R;
import com.example.android_project.data.local.BookingDao;
import com.example.android_project.model.Booking;
import com.example.android_project.model.Car;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

// the "Book Now" flow: a custom dialog whose inflated layout (root) collects the two dates,
// then a fire-and-forget insert — the same write style as the profile screen, not MVVM
public final class BookingDialog {

    private static final long DAY_MS = 24L * 60 * 60 * 1000;

    private BookingDialog() {
    }

    public static void show(Context context, Car car) {
        View root = LayoutInflater.from(context).inflate(R.layout.dialog_booking, null);
        TextView startField = root.findViewById(R.id.start_date_field);
        TextView endField = root.findViewById(R.id.end_date_field);
        TextView totalValue = root.findViewById(R.id.total_value);

        // ASCII dates for storage — Locale.US keeps them yyyy-MM-dd even when the UI is Arabic
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar[] startCal = {null};
        Calendar[] endCal = {null};

        Runnable updateTotal = () -> {
            double total = 0;
            if (startCal[0] != null && endCal[0] != null && !endCal[0].before(startCal[0])) {
                total = days(startCal[0], endCal[0]) * car.getPricePerDay();
            }
            totalValue.setText(context.getString(R.string.booking_total,
                    String.format(Locale.US, "%.0f", total)));
        };
        updateTotal.run();

        startField.setOnClickListener(v -> pickDate(context, startCal, startField, fmt, updateTotal));
        endField.setOnClickListener(v -> pickDate(context, endCal, endField, fmt, updateTotal));

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(car.getName())
                .setView(root)
                .setPositiveButton(R.string.book_now, null)
                .setNegativeButton(R.string.cancel, null)
                .create();

        // wire the positive button after showing, so a validation failure keeps the dialog open
        dialog.setOnShowListener(d -> {
            Button book = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            book.setOnClickListener(v -> {
                if (startCal[0] == null || endCal[0] == null) {
                    Toast.makeText(context, R.string.err_pick_dates, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (endCal[0].before(startCal[0])) {
                    Toast.makeText(context, R.string.err_end_before_start, Toast.LENGTH_SHORT).show();
                    return;
                }
                save(context, car, startCal[0], endCal[0], fmt);
                dialog.dismiss();
            });
        });
        dialog.show();
    }

    private static void pickDate(Context context, Calendar[] holder, TextView field,
                                 SimpleDateFormat fmt, Runnable onPicked) {
        Calendar init = holder[0] != null ? holder[0] : Calendar.getInstance();
        new DatePickerDialog(context, (view, year, month, day) -> {
            Calendar picked = Calendar.getInstance();
            picked.set(year, month, day, 0, 0, 0);
            picked.set(Calendar.MILLISECOND, 0);
            holder[0] = picked;
            field.setText(fmt.format(picked.getTime()));
            onPicked.run();
        }, init.get(Calendar.YEAR), init.get(Calendar.MONTH), init.get(Calendar.DAY_OF_MONTH)).show();
    }

    private static void save(Context context, Car car, Calendar start, Calendar end,
                             SimpleDateFormat fmt) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        double total = days(start, end) * car.getPricePerDay();
        Booking booking = new Booking(user.getUid(), car.getId(),
                fmt.format(start.getTime()), fmt.format(end.getTime()), total);

        AppExecutors executors = AppExecutors.getInstance();
        BookingDao dao = new BookingDao(context);
        executors.diskIO().execute(() -> {
            dao.insert(booking);
            executors.mainThread().execute(() ->
                    Toast.makeText(context, R.string.booked_tst, Toast.LENGTH_SHORT).show());
        });
    }

    private static long days(Calendar start, Calendar end) {
        long diff = end.getTimeInMillis() - start.getTimeInMillis();
        return Math.max(1, Math.round(diff / (double) DAY_MS));
    }
}
