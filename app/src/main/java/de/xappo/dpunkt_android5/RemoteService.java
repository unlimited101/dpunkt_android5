package de.xappo.dpunkt_android5;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;

/**
 * Created by knoppik on 12.03.16.
 */
public class RemoteService extends Service {

    private String mString;
    private Handler mHandler;
    private MainActivity.MyRunnable mRunnable;

    public RemoteService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mRemoteBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mHandler = null;
        return super.onUnbind(intent);
    }


    private final IRemoteService.Stub mRemoteBinder = new IRemoteService.Stub() {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public long calcResult() throws RemoteException {
            return calcRes();
        }

        @Override
        public int getTripleSum(Triple triple) throws RemoteException {
            return triple.getX() + triple.getY() + triple.getZ();
        }
    };


    private long calcRes() {
        long res = 0;
        for (int i = 0; i < 100000; i++) {
            if (i % 17777 == 0 || i % 11333 > 11331) {
                res += i % 14143;
            }
        }
        return res;
    }
}