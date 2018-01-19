package com.example.shijun.refreshandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private WaveAnimationView waveAnimationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        waveAnimationView = (WaveAnimationView) findViewById(R.id.pull_to_refresh_wave);
        waveAnimationView.startAnimation();

        Button myBtn = (Button)findViewById(R.id.myBtn);
        myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MyListActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        waveAnimationView.stopAnimation();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        waveAnimationView.startAnimation();
    }
}
