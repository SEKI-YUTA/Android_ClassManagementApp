package com.example.classmanagementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.classmanagementapp.Utils.EnumWeekDays;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final int NUM_PAGES = 7;
    public static final String DAY_OF_WEEK = "曜日";
    public static final String DAY_NAME = "日付";
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;
    public SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日");
    public String[] weekDaysEn;
    public Date now;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        weekDaysEn = getResources().getStringArray(R.array.weekdaysEn);

        now = new Date();
        String nowDay = new SimpleDateFormat("E").format(now);
        Log.d("MyLog", nowDay);
        int defaultPageNum = Arrays.asList(weekDaysEn).indexOf(nowDay);
        Log.d("MyLog", "default page index should be " + defaultPageNum);

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
                    args.putString(DAY_OF_WEEK, EnumWeekDays.Monday.getWeekDay()); // フラグメント側でフィルタをかけるのに必要
                    args.putString(DAY_NAME, dateFormat.format(new Date(now.getTime() + 60 * 60 * 24 * 1000 * position)) + " " + EnumWeekDays.Monday.getWeekDay());
                    fragment.setArguments(args);
                    return fragment;
                case 1:
                    args.putString(DAY_OF_WEEK, EnumWeekDays.Tuesday.getWeekDay());
                    args.putString(DAY_NAME, dateFormat.format(new Date(now.getTime() + 60 * 60 * 24 * 1000 * position)) + " " + EnumWeekDays.Tuesday.getWeekDay());
                    fragment.setArguments(args);
                    return fragment;
                case 2:
                    args.putString(DAY_OF_WEEK, EnumWeekDays.Wednesday.getWeekDay());
                    args.putString(DAY_NAME, dateFormat.format(new Date(now.getTime() + 60 * 60 * 24 * 1000 * position)) + " " + EnumWeekDays.Wednesday.getWeekDay());
                    fragment.setArguments(args);
                    return fragment;
                case 3:
                    args.putString(DAY_OF_WEEK, EnumWeekDays.Thursday.getWeekDay());
                    args.putString(DAY_NAME, dateFormat.format(new Date(now.getTime() + 60 * 60 * 24 * 1000 * position)) + " " + EnumWeekDays.Thursday.getWeekDay());
                    fragment.setArguments(args);
                    return fragment;
                case 4:
                    args.putString(DAY_OF_WEEK, EnumWeekDays.Friday.getWeekDay());
                    args.putString(DAY_NAME, dateFormat.format(new Date(now.getTime() + 60 * 60 * 24 * 1000 * position)) + " " + EnumWeekDays.Friday.getWeekDay());
                    fragment.setArguments(args);
                    return fragment;
                case 5:
                    args.putString(DAY_OF_WEEK, EnumWeekDays.Saturday.getWeekDay());
                    args.putString(DAY_NAME, dateFormat.format(new Date(now.getTime() + 60 * 60 * 24 * 1000 * position)) + " " + EnumWeekDays.Saturday.getWeekDay());
                    fragment.setArguments(args);
                    return fragment;
                case 6:
                    args.putString(DAY_OF_WEEK, EnumWeekDays.Sunday.getWeekDay());
                    args.putString(DAY_NAME, dateFormat.format(new Date(now.getTime() + 60 * 60 * 24 * 1000 * position)) + " " + EnumWeekDays.Sunday.getWeekDay());
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