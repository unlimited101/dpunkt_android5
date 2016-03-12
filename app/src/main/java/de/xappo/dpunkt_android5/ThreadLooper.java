package de.xappo.dpunkt_android5;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * Created by knoppik on 06.03.16.
 */
public class ThreadLooper extends Thread {
    private Handler mHandler;

    @Override
    public void run() {
        Looper.prepare();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Log.v(getClass().getName(), "Endless looper");
            }
        };

        Looper.loop();
    }

    public void startWork() {
        mHandler.sendEmptyMessage(0);
    }
}
