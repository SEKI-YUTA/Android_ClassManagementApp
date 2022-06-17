package com.example.classmanagementapp.Listeners;

import androidx.cardview.widget.CardView;

import com.example.classmanagementapp.Models.CClass;

public interface OnClassSelectedListener {
    void onClassSelected(CClass cClass);
    void onClassLongPressed(CardView cardView, CClass cClass);
}
