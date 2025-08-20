package com.example.android_project.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
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

        FrameLayout fm = findViewById(R.id.fm);

        int[] images = new int[] {
                R.drawable.e450,
                R.drawable.q5,
                R.drawable.turbo_s
        };

        String[] names = new String[] {
                getString(R.string.merc),
                getString(R.string.audi),
                getString(R.string.porsche)
        };

        String[] prices = new String[] {
                getString(R.string.merc_price),
                getString(R.string.audi_price),
                getString(R.string.porsche_price)
        };

        ListView listView = findViewById(R.id.listView);
        MyAdapter adapter = new MyAdapter(this, images, names, prices);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                androidx.fragment.app.Fragment frag = null;
                switch (position) {
                    case 0:
                        frag = new ListingFragment();
                        break;
                    case 1:
                        frag = new ListingFragment2();
                        break;
                    case 2:
                        frag = new ListingFragment3();
                        break;
                    default:
                        break;
                }
                if (frag != null) openFragment(frag);
            }

            private void openFragment(androidx.fragment.app.Fragment frag) {
                FragmentManager fmgr = getSupportFragmentManager();
                FragmentTransaction ft = fmgr.beginTransaction();
                ft.replace(R.id.fm, frag, "FULLSCREEN_FRAGMENT");
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }
}