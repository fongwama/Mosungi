package com.fongwama.mosungi;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.fongwama.mosungi.ui.activity.AccueilActivity;

public class MainActivity extends AppCompatActivity {

    private TextView appName;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appName = (TextView)findViewById(R.id.app_name);
        Typeface font =Typeface.createFromAsset(getAssets(), "Fibon_Sans_Regular.otf");
        appName.setTypeface(font);

        animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        appName.startAnimation(animation);

        Thread time = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    startActivity(new Intent(MainActivity.this, AccueilActivity.class));
                }
            }
        }; time.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
