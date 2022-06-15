package com.example.classmanagementapp.Adapters;

import android.content.Context;
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
        holder.tv_startAndEndTime.setText(cClass.getStartTime() + "~" + cClass.getEndTime());
        holder.classCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClassSelected(cClass);
            }
        });
        holder.toggleAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
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
