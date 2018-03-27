package com.ikangtai.android.shecaresdkdemo;

import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ikangtai.android.shecaresdk.ShecareSdk;
import com.ikangtai.android.shecaresdk.ble.bind.OnUnBindListener;
import com.ikangtai.android.shecaresdk.ble.connect.OnReceiveDataListener;
import com.ikangtai.android.shecaresdk.databean.net.req.UploadData;
import com.ikangtai.android.shecaresdk.databean.net.resp.UploadDataResp;
import com.ikangtai.android.shecaresdk.databean.user.Period;
import com.ikangtai.android.shecaresdk.databean.user.Temperature;
import com.ikangtai.android.shecaresdk.databean.user.UserInfo;
import com.ikangtai.android.shecaresdk.net.OnCallBack;
import com.ikangtai.android.shecaresdk.net.OnCallUpload;

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

        ShecareSdk.setUserId("111327");

        Button btn1 = (Button) findViewById(R.id.btn_1);
        Button btn2 = (Button) findViewById(R.id.btn_2);

        Button btn3 = (Button) findViewById(R.id.btn_3);
        Button btn4 = (Button) findViewById(R.id.btn_4);
        Button btn5 = (Button) findViewById(R.id.btn_5);
        Button btn6 = (Button) findViewById(R.id.btn_6);
        Button btn7 = (Button) findViewById(R.id.btn_7);
        Button btn8 = (Button) findViewById(R.id.btn_8);
        Button btn9 = (Button) findViewById(R.id.btn_9);
        final Button btn10 = (Button) findViewById(R.id.btn_10);
        final TextView textView = (TextView) findViewById(R.id.tv_1);
//
        // 获取已经绑定过的设备的地址
        sp = getSharedPreferences("yinglinyihao", Context.MODE_PRIVATE);
        macAddress = sp.getString("address", null);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShecareSdk.initUploadData(new OnCallUpload() {
                    @Override
                    public UploadData upload() {
                        //初次上传所有 信息
                        UploadData uploadData = new UploadData();
                        uploadData.setMacAddress("AA:AA:AA:AA:AA");
                        uploadData.setUserId(ShecareSdk.sdkUserId);
                        // 添加体温
                        List<UploadData.TempsBean> temperatures = new ArrayList<>();
                        UploadData.TempsBean tempsBean = new UploadData.TempsBean();
                        tempsBean.setTempTime("2018-03-01 12:12:12");
                        tempsBean.setTempValue("36.60");
                        uploadData.setTemps(temperatures);
                        // 添加经期
                        List<UploadData.PeriodsBean> periodsBeans = new ArrayList<>();
                        UploadData.PeriodsBean periodsBean1 = new UploadData.PeriodsBean();
                        periodsBean1.setPeriod(1); // 1表示经期开始，2表示经期结束
                        periodsBean1.setTime("2018-03-01");
                        periodsBean1.setDeleted(0); //1表示删除，0表示未删除
                        periodsBeans.add(periodsBean1);
                        UploadData.PeriodsBean periodsBean2 = new UploadData.PeriodsBean();
                        periodsBean2.setPeriod(2);
                        periodsBean2.setTime("2018-03-06");
                        periodsBean2.setDeleted(1);
                        periodsBeans.add(periodsBean2);
                        uploadData.setPeriods(periodsBeans);
                        //添加生理信息
                        UploadData.UserInfoBean userInfoBean = new UploadData.UserInfoBean();
                        userInfoBean.setCycleLength(29);//周期长度
                        userInfoBean.setMensLength(4);//经期长度
                        uploadData.setUserInfo(userInfoBean);


                        return uploadData;
                    }

                    @Override
                    public void onSuccess() {
                        Toast.makeText(MainActivity.this, "上传成功", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(String msg) {
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WebviewActivity.class));
            }
        });


        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Temperature> list = new ArrayList<>();
                Temperature temperature = new Temperature();
                temperature.setTemp(36.66);
                temperature.setDate("2018-02-22 12:12:12");
                temperature.setDeleted(0);

                Temperature temperature1 = new Temperature();
                temperature1.setTemp(36.77);
                temperature1.setDeleted(0);
                temperature1.setDate("2018-02-23 12:12:12");

                list.add(temperature);
                list.add(temperature1);
//                temperature.setTemp();
                ShecareSdk.uploadTemps("AA:AA:AA:AA:AA:AA", list, new OnCallBack<UploadDataResp>
                        () {
                    @Override
                    public void onSuccess(UploadDataResp uploadDataResp) {
                        Toast.makeText(MainActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Period> periodsBeans = new ArrayList<>();
                Period periodsBean1 = new Period();
                periodsBean1.setPeriod(1); // 1表示经期开始，2表示经期结束
                periodsBean1.setTime("2018-02-11");
                periodsBean1.setDeleted(0); //1表示删除，0表示未删除
                periodsBeans.add(periodsBean1);
                Period periodsBean2 = new Period();
                periodsBean2.setPeriod(2);
                periodsBean2.setTime("2018-02-15");
                periodsBean2.setDeleted(0);
                periodsBeans.add(periodsBean2);

                ShecareSdk.uploadPeriods(periodsBeans, new OnCallBack<UploadDataResp>() {
                    @Override
                    public void onSuccess(UploadDataResp uploadDataResp) {
                        Toast.makeText(MainActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserInfo userInfo = new UserInfo();
                userInfo.setCycleLength(29);//周期长度
                userInfo.setMensLength(4);//经期长度

                ShecareSdk.uploadUserInfo(userInfo, new OnCallBack<UploadDataResp>() {
                    @Override
                    public void onSuccess(UploadDataResp uploadDataResp) {
                        Toast.makeText(MainActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, BindActivity.class));
                ShecareSdk.bindDevice(MainActivity.this);
            }
        });


        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShecareSdk.unBindDevice(macAddress, new OnUnBindListener() {
                    @Override
                    public void unBindSuccess() {
                        //解绑成功后，清楚sp存储的已经绑定的蓝牙地址
                        sp.edit().putString("address", null);
                        Toast.makeText(ShecareSdk.getContext(), "解绑成功", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void unBindFailure(final String errorMsg) {
                        Toast.makeText(ShecareSdk.getContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        temperatureList = new ArrayList<>();
        btn7.setOnClickListener(new View.OnClickListener() {
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

                    }
                });
            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShecareSdk.getAnalysis(new OnCallBack<String>() {
                    @Override
                    public void onSuccess(String analysisResp) {
                        Toast.makeText(MainActivity.this, analysisResp, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        Toast.makeText(MainActivity.this, code + msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int connectStatus = ShecareSdk.getConnectStatus();
                if (connectStatus == BluetoothProfile.STATE_CONNECTED) {
                    btn10.setText("连接状态 = 已连接");
                } else {
                    btn10.setText("连接状态 = 未连接");

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
                Toast.makeText(ShecareSdk.getContext(), macAddress + "；  " + version, Toast
                        .LENGTH_SHORT)
                        .show();

                //存储已经绑定的设备地址，这里存储在sp中，具体保存方式可根据你们的业务逻辑来处理
                sp.edit().putString("address", macAddress).commit();
            }
        }

    }
}
