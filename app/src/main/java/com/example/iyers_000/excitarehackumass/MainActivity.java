package com.example.iyers_000.excitarehackumass;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;

import android.view.View;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends Activity {

    ListView lv;
    Context context;
    public ArrayList<AlarmStatus> alarmStatuses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context=this;

        lv=(ListView) findViewById(R.id.listView);
        final CustomAdapter custAdapter = new CustomAdapter(this, alarmStatuses);
        // custAdapter.addElement(46, 64);
        lv.setAdapter(custAdapter);
        // custAdapter.addElement(46, 64);
        // lv.setAdapter(custAdapter);
        custAdapter.notifyDataSetChanged();
        //  custAdapter.notify();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.
                make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                showTimeDialog(view, custAdapter);
                //custAdapter.addElement();
                custAdapter.notifyDataSetChanged();
            }
        });

    }

    public void showTimeDialog(View view, final CustomAdapter customAdapter)
    {

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                Toast.makeText(context, ""+selectedHour, Toast.LENGTH_SHORT).show();
                AlarmStatus alarmStatus= new AlarmStatus();
                alarmStatus.hour = selectedHour;
                alarmStatus.min = selectedMinute;
                customAdapter.addElement(alarmStatus);
                customAdapter.notifyDataSetChanged();
            }
        }, hour, minute,false);
        mTimePicker.setTitle("Start Time");
        mTimePicker.show();


    }

}

class AlarmStatus
{
    int hour;
    int min;
    boolean days[]={false, false, false, false, false, false, false};
    boolean ON=false;
}