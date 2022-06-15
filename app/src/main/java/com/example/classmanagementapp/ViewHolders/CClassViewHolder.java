package com.example.classmanagementapp.ViewHolders;

import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.classmanagementapp.R;

public class CClassViewHolder extends RecyclerView.ViewHolder{
    public TextView tv_startAndEndTime, tv_subjectName, tv_teacherName, tv_roomName;
    public CardView classCard;
    public Switch toggleAlarm;
    public ImageButton imgBtn_linkCopy;
    public CClassViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_startAndEndTime = itemView.findViewById(R.id.tv_startAndEndTime);
        tv_subjectName = itemView.findViewById(R.id.tv_subjectName);
        tv_teacherName = itemView.findViewById(R.id.tv_teacherName);
        tv_roomName = itemView.findViewById(R.id.tv_roomName);
        classCard = itemView.findViewById(R.id.classCard);
        toggleAlarm = itemView.findViewById(R.id.toggleAlarm);
    }
}
