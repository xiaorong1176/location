package com.example.petloc;

import com.baidu.mapapi.SDKInitializer;

import android.app.Application;

public class PetLocApplication extends Application {
	public void onCreate(){
		super.onCreate();
		/*
		 * ע�⣺��SDK���������ʹ��֮ǰ����Ҫ����
         *  SDKInitializer.initialize(getApplicationContext());
         * ��˽��÷�������Application�ĳ�ʼ��������
		 */
		SDKInitializer.initialize(this);
	}
}
