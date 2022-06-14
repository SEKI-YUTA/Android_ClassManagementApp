package com.example.classmanagementapp;


import android.content.Intent;
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
import com.example.classmanagementapp.Listeners.OnClassSelectedListener;
import com.example.classmanagementapp.Models.CClass;
import com.example.classmanagementapp.Utils.EnumConstantValues;

import java.util.ArrayList;
import java.util.List;


public class DayClassPageFragment extends Fragment {
    TextView tv_class_page_date;
    RecyclerView recycler_class_list;
    List<CClass> dataAll;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

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

        List<CClass> demoData = new ArrayList<>();
        for(int i = 0; i < dataAll.size(); i++) {
            if(dataAll.get(i).getWeekOfDay().equals(weekOfDay)) {
                demoData.add(dataAll.get(i));
            }
        }
        CClassAdapter adapter = new CClassAdapter(getContext(), demoData, listener);

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
