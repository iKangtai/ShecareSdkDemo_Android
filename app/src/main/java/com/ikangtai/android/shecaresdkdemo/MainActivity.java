package com.ikangtai.android.shecaresdkdemo;

import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ikangtai.android.shecaresdk.ShecareSdk;
import com.ikangtai.android.shecaresdk.ble.bind.OnUnBindListener;
import com.ikangtai.android.shecaresdk.ble.connect.OnReceiveDataListener;
import com.ikangtai.android.shecaresdk.databean.user.Temperature;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String macAddress;
    private String version;
    private SharedPreferences sp;
    private List<Temperature> temperatureList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ShecareSdk.setUserId("e14a522653db91dfd5a3ce2760111333");

        Button btn1 = (Button) findViewById(R.id.btn_1);
        Button btn2 = (Button) findViewById(R.id.btn_2);
        Button btn3 = (Button) findViewById(R.id.btn_3);
        final Button btn4 = (Button) findViewById(R.id.btn_4);
        final TextView textView = (TextView) findViewById(R.id.tv_1);

        // 获取已经绑定过的设备的地址
        sp = getSharedPreferences("yinglinyihao", Context.MODE_PRIVATE);
        macAddress = sp.getString("address", null);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShecareSdk.bindDevice(MainActivity.this);
            }
        });


        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShecareSdk.unBindDevice(macAddress, new OnUnBindListener() {
                    @Override
                    public void unBindSuccess() {
                        //解绑成功后，清除sp存储的已经绑定的蓝牙地址
                        sp.edit().putString("address", null);
                        Toast.makeText(ShecareSdk.getContext(), R.string.unbind_success, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void unBindFailure(int code, final String errorMsg) {
                        Log.i("ble_unbind", "code: " + code + " errorMsg:" + errorMsg);
                        Toast.makeText(ShecareSdk.getContext(), R.string.unbind_failed, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        temperatureList = new ArrayList<>();
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShecareSdk.setOnDeviceDataListener(macAddress, new OnReceiveDataListener() {
                    @Override
                    public void onReceiverData(List<Temperature> temps) {
                        //因为体温计每次最多上传10条数据，所以如果体温计中历史数据多于十条，会分多次上传，所以此处需要注意
                        temperatureList.addAll(temps);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                StringBuffer sb = new StringBuffer();
                                for (Temperature temperature : temperatureList) {
                                    if (temperature != null) {
                                        sb.append(temperature.getDate()).append("  ").append(temperature.getTemp());
                                    }
                                }
                                textView.setText(sb.toString());
                            }
                        });
                    }

                    @Override
                    public void onReceiverFailure(int code, String msg) {
                        Toast.makeText(ShecareSdk.getContext(), msg, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onConnectionStateChange(int state, String macAddress) {
                        if (state == BluetoothProfile.STATE_CONNECTED) {
                            Log.i("ble_conn", "The device is connected.");

                        } else if (state == BluetoothProfile.STATE_DISCONNECTED) {
                            Log.i("ble_conn", "The device has been disconnected.");
                        }
                    }
                });
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int connectStatus = ShecareSdk.getConnectStatus();
                if (connectStatus == BluetoothProfile.STATE_CONNECTED) {
                    btn4.setText("Connection Status = connected");
                } else {
                    btn4.setText("Connection Status = not connected");

                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            macAddress = data.getStringExtra("macAddress");
            version = data.getStringExtra("version");
            if (!TextUtils.isEmpty(macAddress)) {
                Toast.makeText(ShecareSdk.getContext(), "version = " + version + "; address = " + macAddress,
                        Toast
                                .LENGTH_SHORT)
                        .show();

                //存储已经绑定的设备地址，这里存储在sp中，具体保存方式可根据你们的业务逻辑来处理
                sp.edit().putString("address", macAddress).commit();
            }
        }

    }
}
