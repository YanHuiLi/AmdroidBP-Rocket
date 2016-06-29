package com.example.archer.rocket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.StartRocket)
    Button StartRocket;
    @Bind(R.id.StopRocket)
    Button StopRocket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.StartRocket, R.id.StopRocket})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.StartRocket:

                startService(new Intent(this,RocketService.class));

                finish();
                break;
            case R.id.StopRocket:

                stopService(new Intent(this,RocketService.class));
               finish();
                break;
        }
    }
}
