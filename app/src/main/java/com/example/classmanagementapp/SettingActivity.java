package com.example.classmanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingActivity extends AppCompatActivity {
    EditText edit_username, edit_password;
    Button btn_applyUsername, btn_applyPassword;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        preferences = this.getApplicationContext().getSharedPreferences("Setting", MODE_PRIVATE);
        String defaultUsername = preferences.getString("webClassUsername", "");
        String defaultPassword = preferences.getString("webClassPassword", "");


        edit_username = findViewById(R.id.edit_username);
        edit_password = findViewById(R.id.edit_password);
        btn_applyUsername = findViewById(R.id.btn_applyUsername);
        btn_applyPassword = findViewById(R.id.btn_applyPassword);

        edit_username.setText(defaultUsername);
        edit_password.setText(defaultPassword);

        btn_applyUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("webClassUsername", edit_username.getText().toString());
                editor.apply();
            }
        });

        btn_applyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("webClassPassword", edit_password.getText().toString());
                editor.apply();
            }
        });

    }
}