package com.example.classmanagementapp;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.classmanagementapp.Listeners.OnClassSelectedListener;
import com.example.classmanagementapp.Models.CClass;
import com.example.classmanagementapp.Utils.EnumConstantValues;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DayClassPageFragment extends Fragment {
    private TextView tv_class_page_date;
    private RecyclerView recycler_class_list;
    private List<CClass> dataAll;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_class_page, container, false);
        tv_class_page_date = view.findViewById(R.id.tv_class_page_date);
        recycler_class_list = view.findViewById(R.id.recycler_class_list);


        Bundle args = getArguments();
        String weekOfDay = args.getString(EnumConstantValues.WEEKDAY_KEY.getConstantString());
        String dayName = args.getString(EnumConstantValues.DATE_KEY.getConstantString());
        dataAll = (List<CClass>) args.getSerializable(EnumConstantValues.ALL_CCLASS_KEY.getConstantString());
        tv_class_page_date.setText(dayName);

        List<CClass> filteredData = new ArrayList<>();
        for(int i = 0; i < dataAll.size(); i++) {
            if(dataAll.get(i).getWeekOfDay().equals(weekOfDay)) {
                filteredData.add(dataAll.get(i));
                Log.d("MyLog", weekOfDay);
            }
        }

        // 開始時間が早い順に並び変える処理
        for(int i = 0; i < filteredData.size() - 1; i++) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            Date date1 = new Date();
            Date date2 = new Date();
            date1.setHours(Integer.parseInt(filteredData.get(i).getStartTime().split(":")[0]));
            date1.setMinutes(Integer.parseInt(filteredData.get(i).getStartTime().split(":")[1]));
            date2.setHours(Integer.parseInt(filteredData.get(i + 1).getStartTime().split(":")[0]));
            date2.setMinutes(Integer.parseInt(filteredData.get(i + 1).getStartTime().split(":")[1]));
            if(date1.after(date2)) {
                CClass tmp = filteredData.get(i);
                Log.d("MyLog", format.format(date1));
                Log.d("MyLog", format.format(date2));
                filteredData.set(i, filteredData.get(i + 1));
                filteredData.set(i + 1, tmp);
            }
        }
        // ここまで



        CClassAdapter adapter = new CClassAdapter(getContext(), filteredData, listener);

        recycler_class_list.setHasFixedSize(true);
        recycler_class_list.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recycler_class_list.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private final OnClassSelectedListener listener = new OnClassSelectedListener() {
        @Override
        public void onClassSelected(CClass cClass) {
            Intent intent = new Intent(getContext(), CClassDetailActivity.class);
            intent.putExtra(EnumConstantValues.ONE_CCLASS_KEY.getConstantString(), cClass);
            startActivityForResult(intent, 102);
        }
    };
}
