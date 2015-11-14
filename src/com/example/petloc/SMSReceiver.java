package com.example.petloc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSReceiver extends BroadcastReceiver{
	//public final String flagString = "[petloc]:";
	public final String flagString = "$GPGLL";
	
	public void onReceive(Context context, Intent intent)
	{
		if (intent.getAction().equals(
    			"android.provider.Telephony.SMS_RECEIVED"));
		{
			StringBuilder sBuilder =  new StringBuilder();
			Bundle bundle =  intent.getExtras();
			Log.d(GlobalResource.TAG, "receive message");
			if (bundle != null)
			{
				Object[] pdus = (Object[]) bundle.get("pdus");
				SmsMessage[] messages = new SmsMessage[pdus.length];
				for (int i = 0; i < pdus.length; i++)
				{
					messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				}
				for (SmsMessage message : messages)
    			{
    				sBuilder.append(message.getDisplayMessageBody());
    			}
				String message = sBuilder.toString();
				Log.d(GlobalResource.TAG, "receive message:" + message);
				if (message.length() >= flagString.length() && message.substring(0, flagString.length()).equals(flagString))
				{
					this.abortBroadcast();
					
					GlobalResource globalResource = GlobalResource.instance();
					// 格式为(31.53893,104.713855)
					message = message.substring(flagString.length(), message.length());
					String[] posStrings = message.split(",");
					double lat = Double.valueOf(posStrings[1]);			
					double lon = Double.valueOf(posStrings[3]);
					//lat = lat/100;
					//lon = lon/100;
									
				    double latit_in = (int)(lat/100) + ((int)(lat)%100)/60.0;   //整数部分转化
				    double latit_de = (lat - (int)(lat))/60.0;                  //小数部分转化  
				    lat = latit_in + latit_de; 
				    double longi_in = (int)(lon/100) + ((int)(lon)%100)/60.0; //整数部分转化
				    double longi_de = (lon - (int)(lon))/60.0;                //小数部分转化   
				    lon = longi_in + longi_de;  
					
					globalResource.petLatitude = lat;
					globalResource.petLongitude = lon;
					globalResource.saveConfig();
					Log.d(GlobalResource.TAG, "petLatitude:" + globalResource.petLatitude + ", petLongitude:" + globalResource.petLongitude);
		
				}else{
					Log.d(GlobalResource.TAG, "Error message:" + message);
				}

			}		
		}
	}
}
