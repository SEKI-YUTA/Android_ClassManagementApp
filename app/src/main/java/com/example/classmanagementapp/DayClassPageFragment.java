package com.example.classmanagementapp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.classmanagementapp.Adapters.CClassAdapter;
import com.example.classmanagementapp.Database.RoomDB;
import com.example.classmanagementapp.Models.CClass;
import com.example.classmanagementapp.Utils.EnumWeekDays;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DayClassPageFragment extends Fragment {
    TextView tv_class_page_date;
    RecyclerView recycler_class_list;
    RoomDB database;
    List<CClass> dataAll = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_class_page, container, false);
        tv_class_page_date = view.findViewById(R.id.tv_class_page_date);
        recycler_class_list = view.findViewById(R.id.recycler_class_list);
        database = RoomDB.getInstance(getContext());
        dataAll = database.mainDAO().getAll();

        Bundle args = getArguments();
        String weekOfDay = args.getString("曜日");
        String dayName = args.getString("日付");
        tv_class_page_date.setText(dayName);

//        CClass demoItem1 = new CClass("グローバルビジネス論", "古谷　賢一", "0302",
//                EnumWeekDays.Monday.getWeekDay(), "https://meet.google.com/wde-oyvb-avt?pli=1&authuser=2",
//                "", "12:50", "14:20");
//        CClass demoItem2 = new CClass("流通システム論", "藤岡芳郎", "5302",
//                EnumWeekDays.Thursday.getWeekDay(), "https://meet.google.com/wde-oyvb-avt?pli=1&authuser=2",
//                "", "10:40", "14:20");
//        List<CClass> demoDataAll = new ArrayList<>();
        List<CClass> demoData = new ArrayList<>();
//        demoDataAll.add(demoItem1);
//        demoDataAll.add(demoItem2);
        for(int i = 0; i < dataAll.size(); i++) {
            if(dataAll.get(i).getWeekOfDay().equals(weekOfDay)) {
                demoData.add(dataAll.get(i));
            }
        }
        CClassAdapter adapter = new CClassAdapter(getContext(), demoData);

        recycler_class_list.setHasFixedSize(true);
        recycler_class_list.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recycler_class_list.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
