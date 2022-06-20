package com.example.classmanagementapp.Adapters;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.classmanagementapp.AlarmReceiver;
import com.example.classmanagementapp.Database.RoomDB;
import com.example.classmanagementapp.Listeners.OnClassSelectedListener;
import com.example.classmanagementapp.Models.CClass;
import com.example.classmanagementapp.R;
import com.example.classmanagementapp.Utils.TimeUtil;
import com.example.classmanagementapp.ViewHolders.CClassViewHolder;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "simpleAlarmChannel";
            String description = "This text is channel description";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("classAlarm", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
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
        holder.tv_startAndEndTime.setText(cClass.getStartTime() + "~" + cClass.getEndTime() + " " + cClass.getWeekOfDay());
        holder.toggleAlarm.setChecked(cClass.isActive());

        createNotificationChannel();

        holder.classCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClassSelected(cClass);
            }
        });
        holder.classCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onClassLongPressed(holder.classCard, classList.get(holder.getAdapterPosition()));
                return true;
            }
        });
        holder.toggleAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                long triggerTime;
                RoomDB database = RoomDB.getInstance(context);
                Context appContext = context.getApplicationContext();
                CClass cClass = classList.get(holder.getAdapterPosition());

                List<DayOfWeek> oneWeek = new ArrayList<>();
                oneWeek.addAll(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY));

                Date startTime = TimeUtil.convertDateFromHM(cClass.getStartTime());
                Date now = new Date();
                if(startTime.after(now)) {
                    Log.d("MyLog", "今日の" + cClass.getStartTime() + "にアラームをセット");
                    triggerTime = startTime.getTime();
                } else {
                    Log.d("MyLog", "来週の" + cClass.getStartTime() + "にアラームをセット");
                    triggerTime = startTime.getTime() + 60 * 60 * 24 * 7 * 1000;
                }
                if(b) {
                    // アラームをオンにする処理
                    holder.alarmManager = (AlarmManager) appContext.getSystemService(ALARM_SERVICE);
                    Intent intent = new Intent(appContext, AlarmReceiver.class);
                    intent.putExtra("classTitle", cClass.getSubjectName());
                    intent.putExtra("classTime", String.format("%S~%S", cClass.getStartTime(), cClass.getEndTime()));
                    Log.d("toggleAlarm", String.valueOf(holder.toggleAlarm.getId()));

                    if(Build.VERSION.SDK_INT >= 31) {
                        holder.pendingIntent = PendingIntent.getBroadcast(appContext, 0, intent, PendingIntent.FLAG_IMMUTABLE);
                    } else {
                        holder.pendingIntent = PendingIntent.getBroadcast(appContext, 0 , intent, 0);
                    }

                    holder.alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, triggerTime,
                            60 * 60 * 24 * 7 * 1000 ,holder.pendingIntent);
                    database.mainDAO().updateIsActiveState(cClass.getID(), true);
                    // 一週間おきにアラームを鳴らす
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                60 * 60 * 24 * 7 * 1000, pendingIntent);

                    Toast.makeText(appContext, "Alarm set successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // アラームをオフにする処理
                    Intent intent = new Intent(appContext, AlarmReceiver.class);
                    if(Build.VERSION.SDK_INT >= 31) {
                        holder.pendingIntent = PendingIntent.getBroadcast(appContext, 0, intent, PendingIntent.FLAG_IMMUTABLE);
                    } else {
                        holder.pendingIntent = PendingIntent.getBroadcast(appContext, 0 , intent, 0);
                    }

                    if(holder.alarmManager == null) {
                        holder.alarmManager = (AlarmManager) appContext.getSystemService(ALARM_SERVICE);
                    }

                    holder.alarmManager.cancel(holder.pendingIntent);
                    database.mainDAO().updateIsActiveState(cClass.getID(), false);
                    Toast.makeText(context, "The alarm canceled", Toast.LENGTH_SHORT).show();
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
