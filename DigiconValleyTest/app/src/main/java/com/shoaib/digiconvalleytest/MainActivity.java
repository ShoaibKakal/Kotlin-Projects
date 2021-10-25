package com.shoaib.digiconvalleytest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;
import com.shoaib.digiconvalleytest.fragment.OrdersFragment;
import com.shoaib.digiconvalleytest.fragment.PastDueFragment;
import com.shoaib.digiconvalleytest.fragment.UpcomingFragment;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}