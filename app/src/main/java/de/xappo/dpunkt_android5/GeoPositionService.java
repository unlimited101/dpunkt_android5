package de.xappo.dpunkt_android5;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.util.StringBuilderPrinter;

public class GeoPositionService extends Service {

    private String mString;
    private Handler mHandler;
    private MainActivity.MyRunnable mRunnable;
    private final IBinder mGpsBinder = new GeoPositionServiceBinder();

    public GeoPositionService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mGpsBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mHandler = null;
        return super.onUnbind(intent);
    }

    public class GeoPositionServiceBinder extends Binder {
        public String getString() {
            return mString;
        }

        public void setString(String string) {
            mString = string;
        }

        public void logString() {
            Log.i(getClass().getName(), "mString: " + mString);
        }

        public void setActivityCallbackHandler(final Handler callback) {
            mHandler = callback;
        }

        public void calcResult() {
            new Thread() {
                public void run() {
                    mRunnable.result = calcRes();
                    mHandler.post(mRunnable);
                }
            }.start();
        }


        public void setRunnable(final MainActivity.MyRunnable runnable) {
            mRunnable = runnable;
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
