package com.andr0day.thread;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class Service1 extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        StringBuilder sb = new StringBuilder();
        sb.append("current Pid:" + android.os.Process.myPid()+"\n");
        sb.append("current Tid:" + android.os.Process.myTid()+"\n");
        sb.append("current Uid:" + android.os.Process.myUid()+"\n");
        sb.append("current Thread Name:" + Thread.currentThread().getName() + "\n");
        sb.append("isMainThread:" + ThreadUtil.isMainThread() + "\n");
        Toast.makeText(this,sb.toString(),Toast.LENGTH_SHORT).show();
        return 1;
    }
}
