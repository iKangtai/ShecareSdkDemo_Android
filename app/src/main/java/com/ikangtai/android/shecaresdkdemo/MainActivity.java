package com.ikangtai.android.shecaresdkdemo;

import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ikangtai.android.shecaresdk.BindActivity;
import com.ikangtai.android.shecaresdk.OnDataListener;
import com.ikangtai.android.shecaresdk.ShecareSdk;
import com.ikangtai.android.shecaresdk.ble.OnBindListener;
import com.ikangtai.android.shecaresdk.databean.ReceiveData;
import com.ikangtai.android.shecaresdk.databean.net.req.UploadData;
import com.ikangtai.android.shecaresdk.databean.net.resp.AnalysisResp;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ShecareSdk.setUserId("11111");

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

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShecareSdk.initUploadData(new OnCallUpload() {
                    @Override
                    public UploadData upload() {
                        //初次上传所有 信息
                        UploadData uploadData = new UploadData();
                        uploadData.setMacAddress("ZZ-ZZ-ZZ-ZZ-ZZ-ZZ");
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
                        periodsBean1.setStatus(1); //1 表示有效经期，0表示无效经期
                        periodsBeans.add(periodsBean1);
                        UploadData.PeriodsBean periodsBean2 = new UploadData.PeriodsBean();
                        periodsBean2.setPeriod(2);
                        periodsBean2.setTime("2018-03-01");
                        periodsBean2.setStatus(1);
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
                temperature.setTemp(34.6);
                temperature.setDate("2018-02-22 12:12:12");
                list.add(temperature);
//                temperature.setTemp();
                ShecareSdk.uploadTemps("ZZ-ZZ-ZZ-ZZ-ZZ-ZZ", list, new OnCallBack<UploadDataResp>() {
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
                periodsBean1.setTime("2018-03-01");
                periodsBean1.setStatus(1); //1 表示有效经期，0表示无效经期
                periodsBeans.add(periodsBean1);
                Period periodsBean2 = new Period();
                periodsBean2.setPeriod(2);
                periodsBean2.setTime("2018-03-01");
                periodsBean2.setStatus(1);
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
                startActivity(new Intent(MainActivity.this, BindActivity.class));
            }
        });


        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShecareSdk.unBindDevice(macAddress, new OnBindListener() {
                    @Override
                    public void unBindSuccess() {
                        Toast.makeText(ShecareSdk.getContext(), "解绑成功", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void unBindFailure(final String errorMsg) {
                        Toast.makeText(ShecareSdk.getContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ShecareSdk.setOnDeviceDataListener(new OnDataListener() {
                    @Override
                    public void onBindState(int state, String msg, String macAddress, String version) {

                    }

                    @Override
                    public void onDeviceData(final ReceiveData deviceData) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                List<Temperature> temperatures = deviceData.getTemperatures();
                                StringBuffer sb = new StringBuffer();
                                for (Temperature temperature : temperatures) {
                                    if (temperature != null) {
                                        sb.append(temperature.getDate()).append("  ").append(temperature.getTemp());
                                    }
                                }
                                textView.setText(sb);
                            }
                        });
                    }
                });
            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShecareSdk.getAnalysis(new OnCallBack<AnalysisResp>() {
                    @Override
                    public void onSuccess(AnalysisResp analysisResp) {
                        Toast.makeText(MainActivity.this, analysisResp.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
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
            Toast.makeText(ShecareSdk.getContext(), macAddress + "  " + version, Toast.LENGTH_SHORT).show();
        }
    }
}
