package de.xappo.dpunkt_android5;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

public class MessageService extends Service {

    private Handler mHandler;
    private final IBinder mBinder = new MessageServiceBinder();

    public MessageService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mHandler = null;
        return super.onUnbind(intent);
    }

    public class MessageServiceBinder extends Binder {

        public void setActivityCallbackHandler(final Handler callback) {
            mHandler = callback;
        }

        public void calcResult() {
            new Thread() {
                public void run() {
                    long res = calcRes();
                    final Message msg = new Message();
                    final Bundle bundle = new Bundle();
                    bundle.putLong("res", res);
                    msg.setData(bundle);
                    mHandler.sendMessage(msg);
                }
            }.start();
        }
    }




    private long calcRes() {
        long res = 0;
        for (int i = 0; i < 100000; i++) {
            if (i % 17777 == 0 || i % 11333 > 11331) {
                res += i % 14143;
            }
            Log.v(getClass().getName(), "i: " + i + " -- res: " + res);
        }
        return res;
    }
}
