package com.example.classmanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;
import androidx.viewpager2.widget.ViewPager2;

import com.example.classmanagementapp.Database.RoomDB;
import com.example.classmanagementapp.Models.CClass;
import com.example.classmanagementapp.Utils.EnumWeekDays;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int NUM_PAGES = 7;
    public static final String DAY_OF_WEEK = "曜日";
    public static final String DAY_NAME = "日付";
    private RoomDB database;
    private ArrayList<CClass> dataAll;
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;
    private int defaultPageNum;
    public SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日");
    public String[] weekDaysEn;
    public Date now;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = RoomDB.getInstance(this);
        dataAll = (ArrayList<CClass>) database.mainDAO().getAll();


        viewPager = findViewById(R.id.pager);
        weekDaysEn = getResources().getStringArray(R.array.weekdaysEn);

        now = new Date();
        String nowDay = new SimpleDateFormat("E").format(now);
        Log.d("MyLog", nowDay);
        defaultPageNum = Arrays.asList(weekDaysEn).indexOf(nowDay);
        Log.d("MyLog", "default page index should be " + defaultPageNum);
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.post(new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(defaultPageNum, false);
            }
        });
        viewPager.setPageTransformer(new DepthPageTransformer());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, "onActivityResult on MainActivity", Toast.LENGTH_SHORT).show();
        Log.d("MyLog", "onActivityResult on MainActivity");
        dataAll = (ArrayList<CClass>) database.mainDAO().getAll();
        pagerAdapter.notifyDataSetChanged();
        Log.d("MyLog", String.valueOf(dataAll.size()));
        Intent selfIntent = new Intent(this, MainActivity.class);
        selfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(selfIntent);
//        int currentPage = viewPager.getCurrentItem();
//        if(currentPage==0) {
//            viewPager.setCurrentItem(NUM_PAGES - 1);
//            viewPager.setCurrentItem(0);
//        } else if(currentPage == NUM_PAGES - 1) {
//            viewPager.setCurrentItem(1);
//            viewPager.setCurrentItem(NUM_PAGES - 1);
//        } else {
//            viewPager.setCurrentItem(currentPage + 1);
//            viewPager.setCurrentItem(currentPage);
//        }

    }


    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
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
        switch (itemId) {
            case R.id.nav_option_add:
                Intent intent = new Intent(this, AddClassActivity.class);
                startActivityForResult(intent, 101);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public void onBindViewHolder(@NonNull FragmentViewHolder holder, int position, @NonNull List<Object> payloads) {
            super.onBindViewHolder(holder, position, payloads);
            // ここに現在のフラグメントのIdを書き換える処理を書く
//            DayClassPageFragment fragment = getFragmentManager().findFragmentByTag("f" + position);
            // MainActivity自体を再起動させる事でデータ更新が反映されない問題を解決
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Bundle args = new Bundle();
            DayClassPageFragment fragment = new DayClassPageFragment();

            switch (position) {
                case 0:
                    args.putString(DAY_OF_WEEK, EnumWeekDays.Monday.getWeekDay()); // フラグメント側でフィルタをかけるのに必要
                    args.putString(DAY_NAME, dateFormat.format(new Date(now.getTime() + 60 * 60 * 24 * 1000 * position)) + " " + EnumWeekDays.Monday.getWeekDay());
                    args.putSerializable("dataAll", dataAll);
                    fragment.setArguments(args);
                    return fragment;
                case 1:
                    args.putString(DAY_OF_WEEK, EnumWeekDays.Tuesday.getWeekDay());
                    args.putString(DAY_NAME, dateFormat.format(new Date(now.getTime() + 60 * 60 * 24 * 1000 * position)) + " " + EnumWeekDays.Tuesday.getWeekDay());
                    args.putSerializable("dataAll", dataAll);
                    fragment.setArguments(args);
                    return fragment;
                case 2:
                    args.putString(DAY_OF_WEEK, EnumWeekDays.Wednesday.getWeekDay());
                    args.putString(DAY_NAME, dateFormat.format(new Date(now.getTime() + 60 * 60 * 24 * 1000 * position)) + " " + EnumWeekDays.Wednesday.getWeekDay());
                    args.putSerializable("dataAll", dataAll);
                    fragment.setArguments(args);
                    return fragment;
                case 3:
                    args.putString(DAY_OF_WEEK, EnumWeekDays.Thursday.getWeekDay());
                    args.putString(DAY_NAME, dateFormat.format(new Date(now.getTime() + 60 * 60 * 24 * 1000 * position)) + " " + EnumWeekDays.Thursday.getWeekDay());
                    args.putSerializable("dataAll", dataAll);
                    fragment.setArguments(args);
                    return fragment;
                case 4:
                    args.putString(DAY_OF_WEEK, EnumWeekDays.Friday.getWeekDay());
                    args.putString(DAY_NAME, dateFormat.format(new Date(now.getTime() + 60 * 60 * 24 * 1000 * position)) + " " + EnumWeekDays.Friday.getWeekDay());
                    args.putSerializable("dataAll", dataAll);
                    fragment.setArguments(args);
                    return fragment;
                case 5:
                    args.putString(DAY_OF_WEEK, EnumWeekDays.Saturday.getWeekDay());
                    args.putString(DAY_NAME, dateFormat.format(new Date(now.getTime() + 60 * 60 * 24 * 1000 * position)) + " " + EnumWeekDays.Saturday.getWeekDay());
                    args.putSerializable("dataAll", dataAll);
                    fragment.setArguments(args);
                    return fragment;
                case 6:
                    args.putString(DAY_OF_WEEK, EnumWeekDays.Sunday.getWeekDay());
                    args.putString(DAY_NAME, dateFormat.format(new Date(now.getTime() + 60 * 60 * 24 * 1000 * position)) + " " + EnumWeekDays.Sunday.getWeekDay());
                    args.putSerializable("dataAll", dataAll);
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