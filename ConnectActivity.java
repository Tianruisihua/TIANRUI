package com.example.administrator.tianrui6;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.net.Socket;

import java.util.List;

public class ConnectActivity extends AppCompatActivity {
    WifiUser wifiUser;
    EditText editText;

    Socket socket = null;
    String buffer = "";

    WifiSocket wifiSocket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        wifiSocket = new WifiSocket(ConnectActivity.this);
        editText = findViewById(R.id.eidt);
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


        Intent intent = this.getIntent();
         wifiUser = (WifiUser) intent.getSerializableExtra("Wifiuser");
        Log.e("tag",wifiUser.getName());
        boolean ji = wifiConnection(ConnectActivity.this, wifiUser.getName(),"stc89c51");
        if(ji==true){
            Toast.makeText(ConnectActivity.this,"连接成功，开始传输数据", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(ConnectActivity.this,"连接失败，返回主界面", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    public void onClick(View view){
        String da = editText.getText().toString();
        String data = "password: "+da+"\r\n";
        Log.e("TAG",da+data);
        if(!" ".equals(da)){
            wifiSocket.Wifi_Client(data);
            String ser_data  = wifiSocket.getSocketData();
            Log.e("TAG","已输入数字");
           if(!" ".equals(ser_data)){
           //     Toast.makeText(ConnectActivity.this,"密码正确", Toast.LENGTH_SHORT).show();
               if("OK".equals(ser_data)){
                   Log.e("TAGS",ser_data);
                     Toast.makeText(ConnectActivity.this,"密码正确", Toast.LENGTH_SHORT).show();

                }
                if("NO".equals(ser_data)){
                    Log.e("TAGSS",ser_data);
                    Toast.makeText(ConnectActivity.this,"密码错误", Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(ConnectActivity.this,"从机没反应", Toast.LENGTH_SHORT).show();
            }

        }

    }


    public boolean wifiConnection(Context context, String wifiSSID, String password) {
        @SuppressLint("WifiManagerLeak") WifiManager wifi=(WifiManager)getSystemService(Context.WIFI_SERVICE);
        String strQuotationSSID = "\"" + wifiSSID + "\"";
        WifiInfo wifiInfo = wifi.getConnectionInfo();
        if (wifiInfo != null && (wifiSSID.equals(wifiInfo.getSSID()) || strQuotationSSID.equals(wifiInfo.getSSID()))) {
            return true;
        }
        List<ScanResult> scanResults = wifi.getScanResults();
        if (scanResults == null || scanResults.size() == 0) {
            return false;
        }
        for (int nAllIndex = scanResults.size() - 1; nAllIndex >= 0; nAllIndex--) {
            String strScanSSID = ((ScanResult) scanResults.get(nAllIndex)).SSID;
            if (wifiSSID.equals(strScanSSID) || strQuotationSSID.equals(strScanSSID)) {
                WifiConfiguration config = new WifiConfiguration();
                config.SSID = strQuotationSSID;
                config.preSharedKey = "\"" + password + "\"";
                config.status = 2;
                return wifi.enableNetwork(wifi.addNetwork(config), false);
            }
        }
        return false;
    }


}
