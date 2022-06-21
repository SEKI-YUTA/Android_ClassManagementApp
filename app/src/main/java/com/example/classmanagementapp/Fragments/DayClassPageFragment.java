package com.example.classmanagementapp.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.classmanagementapp.Adapters.CClassAdapter;
import com.example.classmanagementapp.CClassDetailActivity;
import com.example.classmanagementapp.Database.RoomDB;
import com.example.classmanagementapp.EditClassActivity;
import com.example.classmanagementapp.Listeners.OnClassSelectedListener;
import com.example.classmanagementapp.MainActivity;
import com.example.classmanagementapp.Models.CClass;
import com.example.classmanagementapp.R;
import com.example.classmanagementapp.Utils.EnumConstantValues;
import com.example.classmanagementapp.Utils.TimeUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DayClassPageFragment extends Fragment {
    private TextView tv_class_page_date;
    private RecyclerView recycler_class_list;
    private List<CClass> dataAll;
    private List<CClass> filteredData;
    private CClass selectedCClass;
    private CClassAdapter adapter;

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

        filteredData = new ArrayList<>();
        for(int i = 0; i < dataAll.size(); i++) {
            if(dataAll.get(i).getWeekOfDay().equals(weekOfDay)) {
                filteredData.add(dataAll.get(i));
            }
        }

        // 開始時間が早い順に並び変える処理
        Log.d("MyLog" , "Sort");
        for(int i = 0; i < filteredData.size() - 1; i++) {
            Log.d("MyLog", "sort processing");
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            Date date1 = TimeUtil.convertDateFromHM(filteredData.get(i).getStartTime());
            Date date2 = TimeUtil.convertDateFromHM(filteredData.get(i + 1).getStartTime());
            Log.d("Fragment", format.format(date1));
            Log.d("Fragment", format.format(date2));
            if(date1.after(date2)) {
                CClass tmp = filteredData.get(i);

                filteredData.set(i, filteredData.get(i + 1));
                filteredData.set(i + 1, tmp);
            }
        }
        // ここまで

        adapter = new CClassAdapter(getContext(), filteredData, listener);

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
            startActivity(intent);
        }

        @Override
        public void onClassLongPressed(CardView cardView, CClass cClass) {
            selectedCClass = cClass;
            PopupMenu popupMenu = new PopupMenu(getContext(), cardView);
            popupMenu.getMenuInflater().inflate(R.menu.class_context_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.context_edit:
                            try {
                                Intent intent = new Intent(getContext(), EditClassActivity.class);
                                intent.putExtra("currentCClass", cClass);
                                startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getContext(), "some error", Toast.LENGTH_SHORT).show();
                            }
                            return true;
                        case R.id.context_delete:
                            int of = TimeUtil.getWeekDayIndexJa(cClass.getWeekOfDay(), getContext());
                            RoomDB database = RoomDB.getInstance(getContext());
                            database.mainDAO().delete(cClass);
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            intent.putExtra(EnumConstantValues.VIEWPAGER_OFFSET.getConstantString(), of);
                            intent.putExtra(EnumConstantValues.IS_BACKED.getConstantString(), true);
                            startActivity(intent);
                            return true;
                        default:
                            return false;
                    }
                }
            });
            popupMenu.show();
        }
    };


}
