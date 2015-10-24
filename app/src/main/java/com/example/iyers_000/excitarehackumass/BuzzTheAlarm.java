package com.example.iyers_000.excitarehackumass;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;

public class BuzzTheAlarm extends AppCompatActivity {

    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buzz_the_alarm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Log.v("BuzzTheAlarm", "running buzz the alarm");

        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
        }
        if (!mBluetoothAdapter.isEnabled()) {
            //start bluetooth
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        String address = "64:89:9A:EA:70:35";
        BluetoothDevice device = null;// = mBluetoothAdapter.getRemoteDevice(address);

        Set<BluetoothDevice> mPairedDevices = mBluetoothAdapter.getBondedDevices();
        if (mPairedDevices.size() > 0) {
            for (BluetoothDevice mDevice : mPairedDevices) {
                device = mBluetoothAdapter.getRemoteDevice(mDevice.getAddress());

                if (device.getName().equalsIgnoreCase("g2") )
                {
                    AcceptThread acceptThread = new AcceptThread();
                    acceptThread.run();
                    return;

                }

                else if(device.getName().equalsIgnoreCase("xt1033"))
                {
                    break;
                }
            }
        }
        Log.v("BuzzTheAlarm", device.getName());
        if (device == null)
            Log.v("BuzzTheAlarm", "empty device");
        BluetoothSocket tmp = null;
        BluetoothSocket socket = null;

        // Get a BluetoothSocket for a connection with the
        // given BluetoothDevice
        try {
            socket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
        } catch (Exception e) {
            Log.e("", "Error creating socket");
        }

        try {
            socket.connect();
            Log.v("", "Connected");
        } catch (IOException we) {
            Log.e("", we.getMessage());
            try {
                Log.e("", "trying fallback...");

                socket = (BluetoothSocket) device.getClass().getMethod("createRfcommSocket", new Class[]{int.class}).invoke(device, 1);
                socket.connect();

                Log.e("", "Connected");


            } catch (IOException e) {
                Log.e(String.valueOf(this), "create() failed", e);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            socket = tmp;
            try {
                ConnectedThread connectedThread = new ConnectedThread(socket);
                connectedThread.start();
              //  connectedThread.sendData();
            } catch (IOException qe) {
                qe.printStackTrace();
            }

        }

    }}
class ConnectedThread extends Thread {
    public  BluetoothSocket mmSocket=null;
    public  InputStream mmInStream=null;
    public  OutputStream mmOutStream=null;

    public ConnectedThread(BluetoothSocket socket) throws IOException {
        mmSocket = socket;

        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException ee) {
            Log.e("CT","io");
            ee.printStackTrace();
        }
        mmInStream = tmpIn;
        mmOutStream = tmpOut;
        Log.v("BTA", "initialize");


    }

    public void run() {
        byte[] buffer = new byte[1024];  // buffer store for the stream
        int bytes; // bytes returned from read()
        Log.v("BTA","running");
        // Keep listening to the InputStream until an exception occurs
        while (true) {
            try {
                // Read from the InputStream
                bytes = mmInStream.read(buffer);
                Log.v("BTA", new String(String.valueOf(bytes)));
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }


    /* Call this from the main activity to send data to the remote device */
    public void write(byte[] bytes) {
        try {
            mmOutStream.write(bytes);
            Log.v("CT", "bytes written");
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void sendData() throws IOException {
        write("Hello".getBytes());
    }
}

 class AcceptThread extends Thread {
    private final BluetoothServerSocket mmServerSocket;
     BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    public AcceptThread() {
        // Use a temporary object that is later assigned to mmServerSocket,
        // because mmServerSocket is final
        BluetoothServerSocket tmp = null;
        try {
            // MY_UUID is the app's UUID string, also used by the client code
            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("Yo", UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
        } catch (IOException e) {e.printStackTrace(); }
        mmServerSocket = tmp;
    }

    public void run() {
        BluetoothSocket socket = null;
        // Keep listening until exception occurs or a socket is returned
        while (true) {
            try {
                socket = mmServerSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();break;
            }
            // If a connection was accepted
            if (socket != null) {
                // Do work to manage the connection (in a separate thread)
                try {
                    socket.getOutputStream().write("Hello boy".getBytes());
                    Log.e("Acc","written bbytes");
               // mmServerSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //break;
            }
        }
    }

    /** Will cancel the listening socket, and cause the thread to finish */
    public void cancel() {
        try {
            mmServerSocket.close();
        } catch (IOException e) { }
    }
}