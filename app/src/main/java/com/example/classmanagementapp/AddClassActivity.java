package com.example.classmanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.classmanagementapp.Database.RoomDB;
import com.example.classmanagementapp.Models.CClass;
import com.example.classmanagementapp.Utils.AppBarSetUP;
import com.example.classmanagementapp.Utils.EnumConstantValues;

import java.util.Arrays;

public class AddClassActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private EditText edit_subjectName, edit_teacherName, edit_roomName,
            edit_startTime, edit_endTime, edit_onlineLink, edit_remark;
    private Button btn_choiceStartTime, btn_choiceEndTime, btn_classAdd;
    private Spinner spinner_weekDay;
    private RoomDB database;
    private int viewPagerPageNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        try {
            viewPagerPageNum = getIntent().getIntExtra("viewPagerPageNum", 0);
        } catch(Exception e) {
            e.printStackTrace();
        }
        // アクションバーに戻るボタンを追加
        actionBar = getSupportActionBar();
        AppBarSetUP.hideTitle(actionBar);
        AppBarSetUP.applyCustomLayout(actionBar, R.layout.custom_appbar1);
        // ここまで

        edit_subjectName = findViewById(R.id.edit_subjectName);
        edit_teacherName = findViewById(R.id.edit_teacherName);
        edit_roomName = findViewById(R.id.edit_roomName);
        edit_startTime = findViewById(R.id.edit_startTime);
        edit_endTime = findViewById(R.id.edit_endTime);
        edit_onlineLink = findViewById(R.id.edit_onlineLink);
        edit_remark = findViewById(R.id.edit_remarkText);
        btn_choiceStartTime = findViewById(R.id.btn_choiceStartTime);
        btn_choiceEndTime = findViewById(R.id.btn_choiceEndTime);
        spinner_weekDay = findViewById(R.id.spinner_weekOfDay);
        btn_classAdd = findViewById(R.id.btn_classAdd);

        database = RoomDB.getInstance(AddClassActivity.this);

        edit_startTime.setFocusable(false);
        edit_endTime.setFocusable(false);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.weekdays,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner_weekDay.setAdapter(adapter);

        btn_choiceStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ここにタイムピッカーから取得した値をフォーマットしてから
                // edit_startTimeに表示させる処理を書く
                DialogFragment newFragment = new TimePick(edit_startTime);
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        btn_choiceEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ここにタイムピッカーから取得した値をフォーマットしてから
                // edit_endTimeに表示させる処理を書く
                DialogFragment newFragment = new TimePick(edit_endTime);
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        spinner_weekDay.setOnItemSelectedListener(spinnerListener);

        // アクションバーの戻るボタンを押したときの処理
        findViewById(R.id.customAppbar_goBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMain(viewPagerPageNum);
            }
        });
        // ここまで

        btn_classAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // データベースに追加する処理
                String subjectName = edit_subjectName.getText().toString();
                String teacherName = edit_teacherName.getText().toString();
                String roomName = edit_roomName.getText().toString();
                String weekOfDay = spinner_weekDay.getSelectedItem().toString();
                String onlineLink = edit_onlineLink.getText().toString();
                String remarkText = edit_remark.getText().toString();
                String startTime = edit_startTime.getText().toString();
                String endTime = edit_endTime.getText().toString();

                if (subjectName.equals("") || teacherName.equals("") || roomName.equals("") || weekOfDay.equals("")
                        || startTime.equals("") || endTime.equals("")) {
                    Toast.makeText(AddClassActivity.this, "備考と授業リンク以外の項目すべてに入力してください", Toast.LENGTH_SHORT).show();
                    return;
                }
                CClass newClass = new CClass(subjectName, teacherName, roomName, weekOfDay, onlineLink,
                        remarkText, startTime, endTime);
                database.mainDAO().insert(newClass);
                String[] weekDays = getResources().getStringArray(R.array.weekdays);
                int of = Arrays.asList(weekDays).indexOf(newClass.getWeekOfDay());
                backToMain(of);
            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        backToMain(viewPagerPageNum);
    }

    // 曜日選択のスピナーを選択したときの処理
    private final AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    // ここまで

    private void backToMain(int of) {
        Intent intent = new Intent(AddClassActivity.this, MainActivity.class);
        intent.putExtra(EnumConstantValues.VIEWPAGER_OFFSET.getConstantString(), of);
        intent.putExtra(EnumConstantValues.IS_BACKED.getConstantString(), true);
        startActivity(intent);
        finish();
    }
}