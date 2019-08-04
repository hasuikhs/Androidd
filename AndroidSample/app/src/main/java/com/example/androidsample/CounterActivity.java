package com.example.androidsample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

class MyCounter1 implements Runnable{
    private Handler handler;

    MyCounter1(Handler handler){
        this.handler = handler;
    }

    @Override
    public void run() {
        for(int i = 0; i < 10 ; i++) {
            try{
                Thread.sleep(1000);
                Bundle bundle = new Bundle();
                bundle.putString("COUNT",i + "");
                Message msg = new Message();
                msg.setData(bundle);
                handler.sendMessage(msg);
                // UI Thread에 handler를 이용해서 message를 전달
            }catch (Exception e){

            }
        }
    }
}

public class CounterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        final TextView tv1 = (TextView)findViewById(R.id.counterTv1);
        Button startBtn1 = (Button)findViewById(R.id.startBtn1);

        final Handler handler = new Handler() {
            // message를 받는 순간 아래 method가 호출
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Bundle b = msg.getData();
                tv1.setText(b.getString("COUNT"));

            }
        };
        startBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Thread를 하나 생성해서 1초마다 TextView에 카운트를 출력
                MyCounter1 counter = new MyCounter1(handler);
                Thread t = new Thread(counter);
                t.start();
            }
        });
    }
}
