package com.example.classmanagementapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Date;

public class AddClassActivity extends AppCompatActivity {
    ActionBar actionBar;
    EditText edit_subjectName, edit_teacherName, edit_roomName,
            edit_startTime, edit_endTime, edit_onlineLink, edit_remark;
    Button btn_choiceStartTime, btn_choiceEndTime;
    Spinner spinner_weekDay;
    Time startTime, endTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);


        // アクションバーに戻るボタンを追加
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.custom_appbar1);
        // ここまで

        edit_subjectName = findViewById(R.id.edit_subjectName);
        edit_teacherName = findViewById(R.id.edit_teacherName);
        edit_roomName = findViewById(R.id.edit_roomName);
        edit_startTime = findViewById(R.id.edit_startTime);
        edit_endTime = findViewById(R.id.edit_endTime);
        edit_onlineLink = findViewById(R.id.edit_onlineLink);
        edit_remark = findViewById(R.id.edit_remark);
        btn_choiceStartTime = findViewById(R.id.btn_choiceStartTime);
        btn_choiceEndTime = findViewById(R.id.btn_choiceEndTime);
        spinner_weekDay = findViewById(R.id.spinner_weekDay);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.weekdays,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner_weekDay.setAdapter(adapter);

        spinner_weekDay.setOnItemSelectedListener(spinnerListener);

        // アクションバーの戻るボタンを押したときの処理
        findViewById(R.id.customAppbar_goBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // ここまで
    }

    // 曜日選択のスピナーを選択したときの処理
    private final AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String[] weekDays = getResources().getStringArray(R.array.weekdays);
            Log.d("MyLog", weekDays[i]);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    // ここまで
}