package com.andr0day.thread;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Activity1 extends Activity {
    private static final int MSG_SYNC = 1;

    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            Object obj = msg.obj;
            switch (msg.what) {
                case MSG_SYNC:
                    Toast.makeText(Activity1.this, obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
            }


        }
    };

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextView textView = (TextView) findViewById(R.id.txt);
        StringBuilder sb = new StringBuilder();
        sb.append("current Pid:" + android.os.Process.myPid() + "\n");
        sb.append("current Tid:" + android.os.Process.myTid() + "\n");
        sb.append("current Uid:" + android.os.Process.myUid() + "\n");
        sb.append("current Thread Name:" + Thread.currentThread().getName() + "\n");
        sb.append("isMainThread:" + ThreadUtil.isMainThread() + "\n");
        textView.setText(sb.toString());

        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Activity1.this, Activity2.class);
                startActivity(intent);
            }
        });

        Button btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... voids) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("current Pid:" + android.os.Process.myPid() + "\n");
                        sb.append("current Tid:" + android.os.Process.myTid() + "\n");
                        sb.append("current Uid:" + android.os.Process.myUid() + "\n");
                        sb.append("current Thread Name:" + Thread.currentThread().getName() + "\n");
                        sb.append("isMainThread:" + ThreadUtil.isMainThread() + "\n");
                        Message msg = handler.obtainMessage(MSG_SYNC);
                        msg.obj = sb.toString();
                        handler.sendMessage(msg);
                        return null;
                    }
                }.execute();
            }
        });



    }
}
