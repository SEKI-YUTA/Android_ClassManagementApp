package com.example.classmanagementapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.classmanagementapp.Models.CClass;
import com.example.classmanagementapp.R;
import com.example.classmanagementapp.ViewHolders.CClassViewHolder;

import java.util.List;

public class CClassAdapter extends RecyclerView.Adapter<CClassViewHolder> {
    private Context context;
    private List<CClass> classList;

    public CClassAdapter(Context context, List<CClass> classList) {
        this.context = context;
        this.classList = classList;
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
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }
}
