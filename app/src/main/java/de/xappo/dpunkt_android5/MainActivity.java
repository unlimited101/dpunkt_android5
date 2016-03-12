package de.xappo.dpunkt_android5;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.button_dial)
    protected Button mButtonDial;
    @Bind(R.id.button_thread_endless)
    protected Button mButtonThreadEndless;
    @Bind(R.id.button_finish)
    protected Button mButtonFinish;
    @Bind(R.id.textview)
    protected TextView mTextView;

    private GeoPositionService.GeoPositionServiceBinder mGeoPositionServiceBinder;
    private MessageService.MessageServiceBinder mMessageServiceBinder;
    private IRemoteService mRemoteServiceBinder;

    private final Handler mGeoHandler = new Handler();
    private final Handler mMsgHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            final Bundle bundle = msg.getData();
            final long res = bundle.getLong("res");
            mTextView.setText("" + res);
            super.handleMessage(msg);
        }
    };



    class MyRunnable implements Runnable {

        public long result;

        @Override
        public void run() {
            mTextView.setText("" + result);
        }
    }

    private ServiceConnection mGeoPositionServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mGeoPositionServiceBinder = (GeoPositionService.GeoPositionServiceBinder) binder;
            mGeoPositionServiceBinder.setActivityCallbackHandler(mGeoHandler);
            mGeoPositionServiceBinder.setRunnable(new MyRunnable());
            mGeoPositionServiceBinder.calcResult();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private ServiceConnection mMessageServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mMessageServiceBinder = (MessageService.MessageServiceBinder) binder;
            mMessageServiceBinder.setActivityCallbackHandler(mMsgHandler);
            mMessageServiceBinder.calcResult();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private ServiceConnection mRemoteServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mRemoteServiceBinder = IRemoteService.Stub.asInterface(binder);
            long res = 0;
            try {
                res = mRemoteServiceBinder.calcResult();
            } catch (RemoteException e) {
                e.printStackTrace();
            } finally {
                if (res != 0) {
                    mTextView.setText("" + res);
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_dial)
    public void buttonDialClick() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:(05191)18381"));
        startActivity(intent);
    }

    @OnClick(R.id.button_thread_endless)
    public void setButtonThreadEndlessClick() {
        Runnable endlessRunnable = new Runnable() {

            @Override
            public void run() {
                while (true) {
                    Log.v(getClass().getName(), "Endless Thred");
                }
            }
        };
        Thread thread = new Thread(endlessRunnable);
        thread.start();
    }

    @OnClick(R.id.button_thread_looper)
    public void setButtonThreadLooperClick() {
        ThreadLooper threadLooper = new ThreadLooper();
        threadLooper.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadLooper.startWork();
    }

    @OnClick(R.id.button_connect_geo)
    public void buttonConnectGeoClick() {
        final Intent geoIntent = new Intent(this, GeoPositionService.class);
        bindService(geoIntent, mGeoPositionServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @OnClick(R.id.button_disconnect_geo)
    public void buttonDisconnectGeoClick() {
        try {
            mGeoHandler.removeCallbacksAndMessages(null);
            unbindService(mGeoPositionServiceConnection);
            stopService(new Intent(this, GeoPositionService.class));

        } catch (Exception e) {}
    }

    @OnClick(R.id.button_connect_msg)
    public void buttonConnectMsgClick() {
        final Intent msgIntent = new Intent(this, MessageService.class);
        bindService(msgIntent, mMessageServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @OnClick(R.id.button_disconnect_msg)
    public void buttonDisconnectMsgClick() {
        try {
            mMsgHandler.removeCallbacksAndMessages(null);
            unbindService(mMessageServiceConnection);
            stopService(new Intent(this, MessageService.class));
        } catch (Exception e) {}
    }

    @OnClick(R.id.button_connect_remote)
    public void buttonConnectRemoteClick() {
        final Intent remoteIntent = new Intent(this, RemoteService.class);
        bindService(remoteIntent, mRemoteServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @OnClick(R.id.button_disconnect_remote)
    public void buttonDisconnectRemoteClick() {
        try {
            unbindService(mRemoteServiceConnection);
            stopService(new Intent(this, RemoteService.class));
        } catch (Exception e) {}
    }


    @OnClick(R.id.button_calc_triple)
    public void buttonCalcTripleClick() {
        try {
            mTextView.setText(mTextView.getText() + " " + mRemoteServiceBinder.getTripleSum(new Triple(1, 2, 3)));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.button_reset)
    public void buttonResetClick() {
        mTextView.setText("Text");
    }


    @OnClick(R.id.button_finish)
    public void buttonFinishClick() {
        finish();
    }


}
