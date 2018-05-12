package com.example.administrator.tianrui6;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<ScanResult> mWifilist;
    private TextView mTextMessage;
    WifiAdmin wifiAdmin;
    private ListView listView;
    List<String> data;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("tag", String.valueOf(i));

                if(data.get(i).equals("Tianrui_123"))
                {
                    Log.e("TAG", "onItemClick: "+adapterView);
                    WifiUser wifiUser=new WifiUser(data.get(i), 7);//实例化对象
                    Intent intent=new Intent();
                    intent.setClass(MainActivity.this, ConnectActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("Wifiuser", wifiUser);//序列化
                    intent.putExtras(bundle);//发送数据
                    startActivity(intent);//启动intent

                }
                if(data.get(i).equals("ESP_13970B")){
                    WifiUser wifiUser=new WifiUser(data.get(i), 7);//实例化对象
                    Intent intent=new Intent();
                    intent.setClass(MainActivity.this, ConnectActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("Wifiuser", wifiUser);//序列化
                    intent.putExtras(bundle);//发送数据
                    startActivity(intent);//启动intent
                }
            }
        });

        wifiAdmin = new WifiAdmin(MainActivity.this);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    public void nearwifi(View view){
       int i = wifiAdmin.checkState(MainActivity.this);
       if(i==3||i==2){

           wifiAdmin.startScan(MainActivity.this);
           mWifilist = wifiAdmin.getWifiList();
           if((mWifilist.size() ==0)){
               Log.e("NEARWIFI","size==0");
               mWifilist = wifiAdmin.getwifimanager().getScanResults();
               if(mWifilist.size()==0){
                   Log.e("NEARWIFI","msize==0");
               }
           }
           data = new ArrayList<String>();
           for (ScanResult sr :mWifilist) {
               Log.e("SSID",sr.SSID);
               data.add(sr.SSID);
           }

           ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>
                   (this,android.R.layout.simple_list_item_1,data);
           listView.setAdapter(myArrayAdapter);

       }


    }




}
