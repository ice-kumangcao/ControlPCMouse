package com.example.ice.controlmouse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ice.controlmouse.util.MyView;

public class MainActivity extends AppCompatActivity {

    private MyView myView;
    private String ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ip="10.92.5.29";

        myView=(MyView)findViewById(R.id.main_myview);
        myView.setIp(ip);
    }
}
