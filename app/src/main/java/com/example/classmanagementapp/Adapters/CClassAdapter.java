package com.example.classmanagementapp.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.classmanagementapp.Listeners.OnClassSelectedListener;
import com.example.classmanagementapp.Models.CClass;
import com.example.classmanagementapp.R;
import com.example.classmanagementapp.ViewHolders.CClassViewHolder;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CClassAdapter extends RecyclerView.Adapter<CClassViewHolder> {
    private Context context;
    private List<CClass> classList;
    OnClassSelectedListener listener;

    public CClassAdapter(Context context, List<CClass> classList, OnClassSelectedListener listener) {
        this.context = context;
        this.classList = classList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CClassViewHolder(LayoutInflater.from(context).inflate(R.layout.class_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CClassViewHolder holder, int position) {
        CClass cClass = classList.get(holder.getAdapterPosition());
        holder.tv_subjectName.setText(cClass.getSubjectName());
        holder.tv_teacherName.setText(cClass.getTeacherName());
        holder.tv_roomName.setText(cClass.getRoomName());
        holder.tv_startAndEndTime.setText(cClass.getStartTime() + "~" + cClass.getEndTime() + " " + cClass.getWeekOfDay());
        holder.classCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClassSelected(cClass);
            }
        });
        holder.toggleAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                CClass cClass = classList.get(holder.getAdapterPosition());
                String[] weekDays = context.getResources().getStringArray(R.array.weekdays);
                List<DayOfWeek> oneWeek = new ArrayList<>();
                oneWeek.addAll(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY));
                int dayIndex = Arrays.asList(weekDays).indexOf(cClass.getWeekOfDay());
                Date now = new Date();
                Date nextDate;
//                Log.d("MyLog", String.valueOf(now.getYear()));
//                Log.d("MyLog", String.valueOf(now.getMonth()));
//                Log.d("MyLog", String.valueOf(now.getDate()));
                LocalDate d = LocalDate.of(1900 + now.getYear(), now.getMonth() + 1, now.getDate()).with(TemporalAdjusters.next(oneWeek.get(dayIndex)));
                Log.d("MyLog", d.toString());
                nextDate =Date.from(d.atStartOfDay(ZoneId.systemDefault()).toInstant());
                if(b) {
                    // アラームをオンにする処理
                } else {
                    // アラームをオフにする処理
                }
                String message = String.format("%S %S%S%S",  cClass.getWeekOfDay(),holder.tv_startAndEndTime.getText().toString(), "の授業アラームを", b ? "ONにしました" : "OFFにしました");
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
        
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }
}
