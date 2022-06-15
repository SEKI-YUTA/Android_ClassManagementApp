package com.example.classmanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;
import androidx.viewpager2.widget.ViewPager2;

import com.example.classmanagementapp.Database.RoomDB;
import com.example.classmanagementapp.Models.CClass;
import com.example.classmanagementapp.Transformer.ZoomOutPageTransformer;
import com.example.classmanagementapp.Utils.EnumConstantValues;
import com.example.classmanagementapp.Utils.EnumWeekDays;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private RoomDB database;
    private ArrayList<CClass> dataAll;
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;
    private int currentPageNum;
    private int defaultPageNum;
    private Integer viewPagerOffset;
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

        boolean isBacked = getIntent().getBooleanExtra(EnumConstantValues.IS_BACKED.getConstantString(), false);
        if(isBacked) {
            int of = getIntent().getIntExtra(EnumConstantValues.VIEWPAGER_OFFSET.getConstantString(), 0);
            viewPagerOffset = Integer.valueOf(of);
        }

        // viewpagerの起動時に表示されるページが今の曜日になるようにページを設定している
        now = new Date();
        // 端末の設定言語によって曜日の表示が異なるため英語で固定する
        DateFormatSymbols dfs = DateFormatSymbols.getInstance(Locale.ENGLISH);
        String nowDay = new SimpleDateFormat("E",dfs).format(now);
        defaultPageNum = Arrays.asList(weekDaysEn).indexOf(nowDay);
        currentPageNum = defaultPageNum;
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.post(new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(
                        viewPagerOffset != null ? viewPagerOffset.intValue() : defaultPageNum,
                        false);
            }
        });
        // ここまで

        // ページ移動時のアニメーション
        viewPager.setPageTransformer(new ZoomOutPageTransformer());

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
                startActivity(intent);
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
            // MainActivity自体を再起動させる事でデータ更新が反映されない問題を解決
            currentPageNum = holder.getAdapterPosition();
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Bundle args = new Bundle();
            DayClassPageFragment fragment = new DayClassPageFragment();
            // 変数名的にややこしいが同じ処理で出る数字が欲しいため使う
            int dateOffset = defaultPageNum;
            switch (position) {
                case 0:
                    args.putString(EnumConstantValues.WEEKDAY_KEY.getConstantString(), EnumWeekDays.Monday.getWeekDay()); // フラグメント側でフィルタをかけるのに必要
                    args.putString(EnumConstantValues.DATE_KEY.getConstantString(), dateFormat.format(new Date(now.getTime() + 60 * 60 * 24 * 1000 * (position - dateOffset))) + " " + EnumWeekDays.Monday.getWeekDay());
                    args.putSerializable(EnumConstantValues.ALL_CCLASS_KEY.getConstantString(), dataAll);
                    fragment.setArguments(args);
                    return fragment;
                case 1:
                    args.putString(EnumConstantValues.WEEKDAY_KEY.getConstantString(), EnumWeekDays.Tuesday.getWeekDay());
                    args.putString(EnumConstantValues.DATE_KEY.getConstantString(), dateFormat.format(new Date(now.getTime() + 60 * 60 * 24 * 1000 * (position - dateOffset))) + " " + EnumWeekDays.Tuesday.getWeekDay());
                    args.putSerializable(EnumConstantValues.ALL_CCLASS_KEY.getConstantString(), dataAll);
                    fragment.setArguments(args);
                    return fragment;
                case 2:
                    args.putString(EnumConstantValues.WEEKDAY_KEY.getConstantString(), EnumWeekDays.Wednesday.getWeekDay());
                    args.putString(EnumConstantValues.DATE_KEY.getConstantString(), dateFormat.format(new Date(now.getTime() + 60 * 60 * 24 * 1000 * (position - dateOffset))) + " " + EnumWeekDays.Wednesday.getWeekDay());
                    args.putSerializable(EnumConstantValues.ALL_CCLASS_KEY.getConstantString(), dataAll);
                    fragment.setArguments(args);
                    return fragment;
                case 3:
                    args.putString(EnumConstantValues.WEEKDAY_KEY.getConstantString(), EnumWeekDays.Thursday.getWeekDay());
                    args.putString(EnumConstantValues.DATE_KEY.getConstantString(), dateFormat.format(new Date(now.getTime() + 60 * 60 * 24 * 1000 * (position - dateOffset))) + " " + EnumWeekDays.Thursday.getWeekDay());
                    args.putSerializable(EnumConstantValues.ALL_CCLASS_KEY.getConstantString(), dataAll);
                    fragment.setArguments(args);
                    return fragment;
                case 4:
                    args.putString(EnumConstantValues.WEEKDAY_KEY.getConstantString(), EnumWeekDays.Friday.getWeekDay());
                    args.putString(EnumConstantValues.DATE_KEY.getConstantString(), dateFormat.format(new Date(now.getTime() + 60 * 60 * 24 * 1000 * (position - dateOffset))) + " " + EnumWeekDays.Friday.getWeekDay());
                    args.putSerializable(EnumConstantValues.ALL_CCLASS_KEY.getConstantString(), dataAll);
                    fragment.setArguments(args);
                    return fragment;
                case 5:
                    args.putString(EnumConstantValues.WEEKDAY_KEY.getConstantString(), EnumWeekDays.Saturday.getWeekDay());
                    args.putString(EnumConstantValues.DATE_KEY.getConstantString(), dateFormat.format(new Date(now.getTime() + 60 * 60 * 24 * 1000 * (position - dateOffset))) + " " + EnumWeekDays.Saturday.getWeekDay());
                    args.putSerializable(EnumConstantValues.ALL_CCLASS_KEY.getConstantString(), dataAll);
                    fragment.setArguments(args);
                    return fragment;
                case 6:
                    args.putString(EnumConstantValues.WEEKDAY_KEY.getConstantString(), EnumWeekDays.Sunday.getWeekDay());
                    args.putString(EnumConstantValues.DATE_KEY.getConstantString(), dateFormat.format(new Date(now.getTime() + 60 * 60 * 24 * 1000 * (position - dateOffset))) + " " + EnumWeekDays.Sunday.getWeekDay());
                    args.putSerializable(EnumConstantValues.ALL_CCLASS_KEY.getConstantString(), dataAll);
                    fragment.setArguments(args);
                    return fragment;
                default:
                    return fragment;
            }
        }

        @Override
        public int getItemCount() {
            return EnumConstantValues.NUM_PAGES.getConstantInt();
        }
    }
}