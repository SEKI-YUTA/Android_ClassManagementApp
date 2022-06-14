package com.example.classmanagementapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.classmanagementapp.Database.RoomDB;
import com.example.classmanagementapp.Models.CClass;
import com.example.classmanagementapp.Utils.EnumConstantValues;

public class ClassDetailActivity extends AppCompatActivity {
    TextView tv_classTime,tv_subjectName, tv_teacherName, tv_roomName, tv_onlineLink,tv_remarkText;
    ImageButton imgBtn_delete;
    AlertDialog.Builder alertDialogBuilder;
    ActionBar actionbar;
    ImageButton customAppbar_goBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);

        // アクションバーをカスタム
        actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(false);
        actionbar.setDisplayShowCustomEnabled(true);
        actionbar.setCustomView(R.layout.custom_appbar1);
        customAppbar_goBack = findViewById(R.id.customAppbar_goBack);
        customAppbar_goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // ここまで

        tv_classTime = findViewById(R.id.tv_classTime);
        tv_subjectName = findViewById(R.id.tv_subjectName);
        tv_teacherName = findViewById(R.id.tv_teacherName);
        tv_roomName = findViewById(R.id.tv_roomName);
        tv_onlineLink = findViewById(R.id.tv_onlineLink);
        tv_remarkText = findViewById(R.id.tv_remarkText);
        imgBtn_delete = findViewById(R.id.imgBtn_delete);

        CClass ccLass = (CClass) getIntent().getSerializableExtra(EnumConstantValues.ONE_CCLASS_KEY.getConstantString());

        tv_classTime.setText(ccLass.getWeekOfDay() +"  " +  ccLass.getStartTime() + "~" + ccLass.getEndTime());
        tv_subjectName.setText(ccLass.getSubjectName());
        tv_teacherName.setText(ccLass.getTeacherName());
        tv_roomName.setText(ccLass.getRoomName());
        tv_onlineLink.setText(ccLass.getOnlineLink());
        tv_remarkText.setText(ccLass.getRemarkText());

        imgBtn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogBuilder = new AlertDialog.Builder(ClassDetailActivity.this);
                alertDialogBuilder.setMessage((ccLass.getWeekOfDay() +"  " +  ccLass.getStartTime() + "~" + ccLass.getEndTime() + "の授業を本当に削除しますか？"));

                alertDialogBuilder.setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RoomDB database = RoomDB.getInstance(ClassDetailActivity.this);
                database.mainDAO().delete(ccLass);
                        Toast.makeText(ClassDetailActivity.this, "削除しました。", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }
                });
                alertDialogBuilder.setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(ClassDetailActivity.this, "削除をキャンセルしました。", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialogBuilder.create();
                alertDialogBuilder.show();


            }
        });


    }
}