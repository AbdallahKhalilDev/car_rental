package com.example.android_project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.android_project.R;

import fragments.ListingFragment;
import fragments.ListingFragment2;
import fragments.ListingFragment3;

public class MainActivity5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main5);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        FrameLayout fm= findViewById(R.id.fm);
        LinearLayout listing1=findViewById(R.id.listing1);
        LinearLayout listing2=findViewById(R.id.listing2);
        LinearLayout listing3=findViewById(R.id.listing3);



        listing1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListingFragment listingFragment= new ListingFragment();
                FragmentManager myFragmentManager= getSupportFragmentManager();
                FragmentTransaction myFragmentTransaction = myFragmentManager.beginTransaction();
                myFragmentTransaction.replace(R.id.fm, listingFragment, "FULLSCREEN_FRAGMENT");
                myFragmentTransaction.addToBackStack(null); // add to back stack so user can press Back to remove
                myFragmentTransaction.commit();
            }
        });

        listing2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListingFragment2 listingFragment2= new ListingFragment2();
                FragmentManager myFragmentManager= getSupportFragmentManager();
                FragmentTransaction myFragmentTransaction = myFragmentManager.beginTransaction();
                myFragmentTransaction.replace(R.id.fm, listingFragment2, "FULLSCREEN_FRAGMENT");
                myFragmentTransaction.addToBackStack(null); // add to back stack so user can press Back to remove
                myFragmentTransaction.commit();            }
        });
        listing3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListingFragment3 listingFragment3= new ListingFragment3();
                FragmentManager myFragmentManager= getSupportFragmentManager();
                FragmentTransaction myFragmentTransaction = myFragmentManager.beginTransaction();
                myFragmentTransaction.replace(R.id.fm, listingFragment3, "FULLSCREEN_FRAGMENT");
                myFragmentTransaction.addToBackStack(null); // add to back stack so user can press Back to remove
                myFragmentTransaction.commit();            }
        });

    }
}