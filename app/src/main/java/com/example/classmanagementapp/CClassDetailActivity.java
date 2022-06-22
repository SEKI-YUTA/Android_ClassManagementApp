package com.example.classmanagementapp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.classmanagementapp.Database.RoomDB;
import com.example.classmanagementapp.Models.CClass;
import com.example.classmanagementapp.Utils.AppBarSetUP;
import com.example.classmanagementapp.Utils.EnumConstantValues;
import com.example.classmanagementapp.Utils.TimeUtil;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CClassDetailActivity extends AppCompatActivity {
    private TextView tv_classTime,tv_subjectName, tv_teacherName, tv_roomName, tv_onlineLink,tv_remarkText;
    private ImageButton imgBtn_delete;
    private AlertDialog.Builder alertDialogBuilder;
    private ActionBar actionbar;
    private ImageButton customAppbar_goBack, imgBtn_linkCopy;
    private CClass ccLass;
    private int viewPagerOffset;
    private boolean isURLValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cclass_detail);

        tv_classTime = findViewById(R.id.tv_classTime);
        tv_subjectName = findViewById(R.id.tv_subjectName);
        tv_teacherName = findViewById(R.id.tv_teacherName);
        tv_roomName = findViewById(R.id.tv_roomName);
        tv_onlineLink = findViewById(R.id.tv_onlineLink);
        tv_remarkText = findViewById(R.id.tv_remarkText);
        imgBtn_delete = findViewById(R.id.imgBtn_delete);
        imgBtn_linkCopy = findViewById(R.id.imgBtn_linkCopy);

        tv_classTime.setSelected(true);

        ccLass = (CClass) getIntent().getSerializableExtra(EnumConstantValues.ONE_CCLASS_KEY.getConstantString());

        // URLが正式な形かをチェック
        String url = ccLass.getOnlineLink();
        Log.d("OnlineLink", url);
        url = url.replace(" ", "");
        url = url.replace("　", "");
        Pattern pattern = Pattern.compile("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
        Matcher m = pattern.matcher(url);
        isURLValid = m.find();
        Log.d("MyLog", String.valueOf(isURLValid));
        if(isURLValid) {
            tv_onlineLink.setTextColor(getResources().getColor(R.color.sky_blue));
        }
        // ここまで

        viewPagerOffset = TimeUtil.getWeekDayIndexJa(ccLass.getWeekOfDay(), this);

        // アクションバーをカスタム
        actionbar = getSupportActionBar();
        AppBarSetUP.hideTitle(actionbar);
        AppBarSetUP.applyCustomLayout(actionbar, R.layout.custom_appbar1);
        customAppbar_goBack = findViewById(R.id.customAppbar_goBack);
        customAppbar_goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               backToMain();
            }
        });
        // ここまで

        tv_classTime.setText(ccLass.getWeekOfDay() +"  " +  ccLass.getStartTime() + "~" + ccLass.getEndTime());
        tv_subjectName.setText(ccLass.getSubjectName());
        tv_teacherName.setText(ccLass.getTeacherName());
        tv_roomName.setText(ccLass.getRoomName());
        tv_onlineLink.setText(ccLass.getOnlineLink());
        tv_remarkText.setText(ccLass.getRemarkText());

        imgBtn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogBuilder = new AlertDialog.Builder(CClassDetailActivity.this);
                alertDialogBuilder.setMessage((ccLass.getWeekOfDay() +"  " +  ccLass.getStartTime() + "~" + ccLass.getEndTime() + "の授業を本当に削除しますか？"));

                alertDialogBuilder.setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(CClassDetailActivity.this, "削除をキャンセルしました。", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialogBuilder.setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RoomDB database = RoomDB.getInstance(CClassDetailActivity.this);
                        database.mainDAO().delete(ccLass);
                        Toast.makeText(CClassDetailActivity.this, "削除しました。", Toast.LENGTH_SHORT).show();
                        backToMain();
                    }
                });
                alertDialogBuilder.create();
                alertDialogBuilder.show();


            }
        });

        tv_onlineLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isURLValid) {
                    Log.d("MyLog", "valid url");
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ccLass.getOnlineLink()));
                    startActivity(browserIntent);
                }
            }
        });

        imgBtn_linkCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ccLass.getOnlineLink() == "") return;
                // 授業リンクをコピーする処理
                ClipboardManager clipboardManager = (ClipboardManager) CClassDetailActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                if(clipboardManager == null) {
                    return;
                }
                clipboardManager.setPrimaryClip(ClipData.newPlainText("", ccLass.getOnlineLink()));
                Toast.makeText(CClassDetailActivity.this, "授業リンクをコピーしました。", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        backToMain();
    }

    private void backToMain() {
        Intent intent = new Intent(CClassDetailActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(EnumConstantValues.VIEWPAGER_OFFSET.getConstantString(), viewPagerOffset);
        intent.putExtra(EnumConstantValues.IS_BACKED.getConstantString(), true);
        startActivity(intent);
        finish();
    }
}