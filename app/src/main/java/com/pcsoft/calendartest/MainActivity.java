package com.pcsoft.calendartest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.pcsoft.calendartest.model.SimpleCalendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SimpleCalendar simpleCalendar = findViewById(R.id.square_day);
        simpleCalendar.setCallBack(view -> {});
    }
}
