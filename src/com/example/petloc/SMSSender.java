package com.example.petloc;

import java.util.List;

import android.telephony.SmsManager;
import android.util.Log;

public class SMSSender {

	public static boolean send(String content) {

		/*
		 * ��ָ�����뷢�Ͷ���
		 */
		try {
			List<String> phone_numberList;
			GlobalResource phone_Resource = GlobalResource.instance();
			phone_numberList = phone_Resource.phoneNumberList;
			// �����������к��붼�����ˣ������Ժ���չʱ���ø�
			for (String phone_number : phone_numberList) {
				SmsManager smsManager = SmsManager.getDefault();
				//List<String> divideContents = smsManager.divideMessage("[petloc]:" + content); 
				List<String> divideContents = smsManager.divideMessage(content);
				for (String text : divideContents) {
					smsManager.sendTextMessage(phone_number, null, text, null, null);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.d(GlobalResource.TAG, e.toString());
			return false;
		}
		return true;
		
	}
}
