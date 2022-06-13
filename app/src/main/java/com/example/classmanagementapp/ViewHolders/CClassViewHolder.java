package com.example.classmanagementapp.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.classmanagementapp.R;

public class CClassViewHolder extends RecyclerView.ViewHolder{
    public TextView tv_startAndEndTime, tv_subjectName, tv_teacherName, tv_roomName;
    public CClassViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_startAndEndTime = itemView.findViewById(R.id.tv_startAndEndTime);
        tv_subjectName = itemView.findViewById(R.id.tv_subjectName);
        tv_teacherName = itemView.findViewById(R.id.tv_teacherName);
        tv_roomName = itemView.findViewById(R.id.tv_roomName);
    }
}
