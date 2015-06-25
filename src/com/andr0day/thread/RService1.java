package com.andr0day.thread;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

public class RService1 extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


    private Binder binder = new RService.Stub(){

        @Override
        public String invoke() throws RemoteException {
            StringBuilder sb = new StringBuilder();
            sb.append("current Pid:" + android.os.Process.myPid() + "\n");
            sb.append("current Tid:" + android.os.Process.myTid() + "\n");
            sb.append("current Uid:" + android.os.Process.myUid() + "\n");
            sb.append("current Thread Name:" + Thread.currentThread().getName() + "\n");
            sb.append("isMainThread:" + ThreadUtil.isMainThread() + "\n");
            return sb.toString();
        }
    };
}
