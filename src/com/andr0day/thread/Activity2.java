package com.andr0day.thread;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.*;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Activity2 extends Activity {
    private static final int MSG_RMT = 1;

    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            Object obj = msg.obj;
            switch (msg.what) {
                case MSG_RMT:
                    Toast.makeText(Activity2.this, obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
            }


        }
    };


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
                intent.setClass(Activity2.this, Service1.class);
                startService(intent);
            }
        });

        Button btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Activity2.this, RService1.class);
                bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
            }
        });


    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            RService rService = RService.Stub.asInterface(iBinder);
            try {
                String str = rService.invoke();
                Message msg = handler.obtainMessage(MSG_RMT);
                msg.obj = str;
                handler.sendMessage(msg);
                unbindService(this);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
}