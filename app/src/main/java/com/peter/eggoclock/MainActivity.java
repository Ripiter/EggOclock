package com.peter.eggoclock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<ImageButton> topButtons = new ArrayList<>();
    boolean stopTimer = false;
    Integer timer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startBtn = findViewById(R.id.start_btn);
        startBtn.setEnabled(false);

        startBtn.setOnClickListener(this::startTimer);

        setTopButtonClick();
    }

    public void onButtonEggSelectedClicked(View view) {

        ImageButton pressed = (ImageButton)view;
        TextView timerText = findViewById(R.id.timer_text);

        switch (pressed.getId())
        {
            case R.id.hard_egg_btn:
                timer = 100;
                break;
            case R.id.smile_egg_btn:
                timer = 100;
                break;
            case R.id.normal_egg_btn:
                timer = 100;
                break;
        }

        timerText.setText(timeToString());

        Button startBtn = findViewById(R.id.start_btn);
        startBtn.setEnabled(true);
    }

    public void startTimer(View view){
        Button startBtn = (Button) view;

        Handler handler = new Handler();
        TextView timerText = findViewById(R.id.timer_text);

        if(startBtn.getText() == getResources().getString(R.string.start_btn_name)){

            startBtn.setText(R.string.end_btn_name);
            setTopButtons(false);
            stopTimer = false;

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    timer--;

                    if(timer <= 0) {
                        setTopButtons(true);
                        startBtn.setEnabled(false);
                    }
                    else
                    {
                        timerText.setText(timeToString());

                        if(stopTimer == false)
                            handler.postDelayed(this, 1000);
                    }
                }
            },1000);

        }
        else
        {
            // stop  the time
            stopTimer = true;
            startBtn.setText(R.string.start_btn_name);
            setTopButtons(true);
        }
    }
    public String timeToString(){
        Integer minutes = timer / 60;
        Integer seconds = timer - (minutes * 60);

        String secondsTime;

        boolean isTwoCharacters = seconds.toString().length() == 2 ? true : false;

        // change the seconds display from fx 1:2 to 1:02
        if(isTwoCharacters == false){
            secondsTime = "0" + seconds;
        }else{
            secondsTime = seconds.toString();
        }

        return minutes + ":" + secondsTime;
    }

    void setTopButtons(boolean set){
        for(Integer i = 0; i < topButtons.size(); i++){
            topButtons.get(i).setEnabled(set);
        }
    }

    void setTopButtonClick(){

        ImageButton blod = (ImageButton) findViewById(R.id.normal_egg_btn);
        ImageButton smile = (ImageButton) findViewById(R.id.smile_egg_btn);
        ImageButton hard = (ImageButton) findViewById(R.id.hard_egg_btn);

        topButtons.add(hard);
        topButtons.add(smile);
        topButtons.add(blod);

        for(Integer i = 0; i < topButtons.size(); i++){
            topButtons.get(i).setOnClickListener(v -> onButtonEggSelectedClicked(v));
        }
    }
}