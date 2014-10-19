package com.artemzin.android.images_please.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.artemzin.android.images_please.R;
import com.artemzin.android.images_please.ui.fragment.ListOfImagesFragment;


public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, ListOfImagesFragment.newInstance())
                    .commit();
        }
    }
}
