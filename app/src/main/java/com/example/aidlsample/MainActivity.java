package com.example.aidlsample;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aidlsample.bean.Person;
import com.example.aidlsample.service.MyAidlService;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private IMyAidl mAidl;
    private TextView textView;
    private boolean isConnected=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
    }
    private void initView() {
        textView=findViewById(R.id.tv_content);
    }

    private void setListener() {
        findViewById(R.id.btn_bind_service).setOnClickListener(view-> bindService());
        findViewById(R.id.btn_add_person).setOnClickListener(view-> addPerson());
        findViewById(R.id.btn_unbind_service).setOnClickListener(view->unbindService());
    }

    private void unbindService() {
        if(!isConnected){
            Toast.makeText(MainActivity.this,"您还未绑定服务，请先绑定！",Toast.LENGTH_SHORT).show();
            return;
        }
        isConnected=false;
        Toast.makeText(MainActivity.this,"解绑成功！",Toast.LENGTH_SHORT).show();
        unbindService(mConnection);
    }

    private void addPerson() {
        if(!isConnected){
            Toast.makeText(MainActivity.this,"请先绑定服务！",Toast.LENGTH_SHORT).show();
            return;
        }
        Random random = new Random();
        Person person = new Person("zhang_san——" + random.nextInt(10000),new Random().nextInt(100));

        try {
            mAidl.addPerson(person);
            List<Person> personList = mAidl.getPersonList();
            textView.setText(personList.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void bindService() {
        if(isConnected){
            Toast.makeText(MainActivity.this,"已经绑定，请勿重新操作！",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(getApplicationContext(), MyAidlService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //连接后拿到 Binder，转换成 AIDL，在不同进程会返回个代理
            isConnected=true;
            Toast.makeText(MainActivity.this,"绑定成功！",Toast.LENGTH_SHORT).show();
            mAidl = IMyAidl.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(MainActivity.this,"断开绑定！",Toast.LENGTH_SHORT).show();
            mAidl = null;
        }
    };
}
