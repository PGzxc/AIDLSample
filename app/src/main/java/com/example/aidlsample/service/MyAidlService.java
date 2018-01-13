package com.example.aidlsample.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import com.example.aidlsample.IMyAidl;
import com.example.aidlsample.bean.Person;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/1/13.
 */

public class MyAidlService extends Service {
    private final String TAG=this.getClass().getSimpleName();
    private List<Person> mPersons;
  /** 客户端与服务端绑定时的回调，返回 mIBinder 后客户端就可以通过它远程调用服务端的方法，即实现了通讯*/
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mPersons = new ArrayList<>();
        Log.d(TAG, "MyAidlService onBind");
        return mIBinder;
    }
    private IBinder mIBinder=new IMyAidl.Stub() {
        @Override
        public void addPerson(Person person) throws RemoteException {
            mPersons.add(person);
        }

        @Override
        public List<Person> getPersonList() throws RemoteException {
            return mPersons;
        }
    };
}
