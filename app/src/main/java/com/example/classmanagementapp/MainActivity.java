package com.example.classmanagementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.classmanagementapp.Models.CClass;
import com.example.classmanagementapp.Utils.EnumWeekOfDay;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int NUM_PAGES = 7;
    public static final String DAY_OF_WEEK = "曜日";
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
//        viewPager.setPageTransformer(new );
    }

    @Override
    public void onBackPressed() {
        if(viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId) {
            case R.id.nav_option_add:
                Intent intent = new Intent(this, AddClassActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {super(fa);}

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Bundle args = new Bundle();
            DayClassPageFragment fragment = new DayClassPageFragment();

            switch (position) {
                case 0:
                    args.putString(DAY_OF_WEEK, EnumWeekOfDay.Monday.getWeekOfDay());
                    fragment.setArguments(args);
                    return fragment;
                case 1:
                    args.putString(DAY_OF_WEEK, EnumWeekOfDay.Tuesday.getWeekOfDay());
                    fragment.setArguments(args);
                    return fragment;
                case 2:
                    args.putString(DAY_OF_WEEK, EnumWeekOfDay.Wednesday.getWeekOfDay());
                    fragment.setArguments(args);
                    return fragment;
                case 3:
                    args.putString(DAY_OF_WEEK, EnumWeekOfDay.Thursday.getWeekOfDay());
                    fragment.setArguments(args);
                    return fragment;
                case 4:
                    args.putString(DAY_OF_WEEK, EnumWeekOfDay.Friday.getWeekOfDay());
                    fragment.setArguments(args);
                    return fragment;
                case 5:
                    args.putString(DAY_OF_WEEK, EnumWeekOfDay.Saturday.getWeekOfDay());
                    fragment.setArguments(args);
                    return fragment;
                case 6:
                    args.putString(DAY_OF_WEEK, EnumWeekOfDay.Sunday.getWeekOfDay());
                    fragment.setArguments(args);
                    return fragment;
                default:
                    return fragment;
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}