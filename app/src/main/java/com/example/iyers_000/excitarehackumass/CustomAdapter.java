package com.example.iyers_000.excitarehackumass;

/**
 * Created by Jay Tarun Chheda on 10/25/2015.
 */

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

public class CustomAdapter extends BaseAdapter{
    ArrayList<AlarmStatus> alarmStatuses = new ArrayList<AlarmStatus>();
    Context context;
    private static LayoutInflater inflater=null;

    public CustomAdapter(MainActivity mainActivity, ArrayList<AlarmStatus> alarmStatuses) {
        // TODO Auto-generated constructor stub
        for(AlarmStatus alarmStatus : this.alarmStatuses)alarmStatuses.add(alarmStatus);
        context=mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addElement(AlarmStatus alarmStatus){
        alarmStatuses.add(alarmStatus);
        notifyDataSetChanged();
        Log.d("CustAdap","Adding alarm");

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return alarmStatuses.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
   //     TextView tv;
        TextView timeText;
        Switch button;
        CheckBox chk1;
        CheckBox chk2;
        CheckBox chk3;
        CheckBox chk4;
        CheckBox chk5;
        CheckBox chk6;
        CheckBox chk7;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.program_list, null);
        final AlarmStatus alarmStatus = alarmStatuses.get(position);
        holder.timeText=(TextView) rowView.findViewById(R.id.timeText1);
        holder.timeText.setText(alarmStatuses.get(position).hour+":"+alarmStatuses.get(position).min);
        holder.button=(Switch) rowView.findViewById(R.id.switch1);
        holder.button.setChecked(alarmStatus.ON);
        holder.chk1 = (CheckBox) rowView.findViewById(R.id.checkBox1);
        holder.chk2 = (CheckBox) rowView.findViewById(R.id.checkBox2);
        holder.chk3 = (CheckBox) rowView.findViewById(R.id.checkBox3);
        holder.chk4 = (CheckBox) rowView.findViewById(R.id.checkBox4);
        holder.chk5 = (CheckBox) rowView.findViewById(R.id.checkBox5);
        holder.chk6 = (CheckBox) rowView.findViewById(R.id.checkBox6);
        holder.chk7 = (CheckBox) rowView.findViewById(R.id.checkBox7);
        holder.chk1.setChecked(alarmStatus.days[0]);
        holder.chk2.setChecked(alarmStatus.days[1]);
        holder.chk3.setChecked(alarmStatus.days[2]);
        holder.chk4.setChecked(alarmStatus.days[3]);
        holder.chk5.setChecked(alarmStatus.days[4]);
        holder.chk6.setChecked(alarmStatus.days[5]);
        holder.chk7.setChecked(alarmStatus.days[6]);

        holder.button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                    alarmStatus.ON = isChecked;
                    Intent msgIntent = new Intent(context, BuzzTheAlarm.class);
                    Gson gson = new Gson();

                    // convert java object to JSON format,
                    // and returned as JSON formatted string
                    String jsonAlarmStatus = gson.toJson(alarmStatus);
                    msgIntent.putExtra("alarmStatus", jsonAlarmStatus);
                    Log.e("cust Adapter", jsonAlarmStatus);
                    context.startService(msgIntent);
                  //  Toast.makeText(this, "Vola! Time to go to bed", Toast.LENGTH_SHORT).show();

                if (isChecked) {

                    Toast.makeText(context, "Alarm turned ON", Toast.LENGTH_SHORT).show();
                } else {
                    //alarmStatus.ON = false;
                    Toast.makeText(context, "Alarm turned OFF", Toast.LENGTH_SHORT).show();
                }

            }
        });

        holder.timeText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showTimeDialog(v, position);
                //Toast.makeText(context, "You Clicked " + result.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        holder.chk1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                alarmStatus.days[0] = isChecked;
            }
        });
        holder.chk2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                alarmStatus.days[1] = isChecked;
            }
        });
        holder.chk3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                alarmStatus.days[2] = isChecked;
            }
        });
        holder.chk4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                alarmStatus.days[3] = isChecked;
            }
        });
        holder.chk5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                alarmStatus.days[4] = isChecked;
            }
        });
        holder.chk6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                alarmStatus.days[5] = isChecked;
            }
        });
        holder.chk7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                alarmStatus.days[6] = isChecked;
            }
        });

        return rowView;
    }

    public void showTimeDialog(View view, final int position)
    {

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {

            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                Toast.makeText(context, ""+selectedHour, Toast.LENGTH_SHORT).show();
                AlarmStatus alarmStatus = alarmStatuses.get(position);
                alarmStatus.hour = selectedHour;
                alarmStatus.min = selectedMinute;
                notifyDataSetChanged();
            }
        }, hour, minute,false);
        mTimePicker.setTitle("Start Time");
        mTimePicker.show();


    }

}


