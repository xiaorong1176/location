package com.example.petloc;

import com.example.petloc.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Utils {
	
	public static void showPhoneSettingDlg(final Activity activity)
	{
		LayoutInflater factory = LayoutInflater.from(activity);
		//�õ��Զ���Ի���
        final View dialog_view = factory.inflate(R.layout.activity_phone_setting, null);
        ((EditText)dialog_view.findViewById(R.id.phoneNumber)).setText(GlobalResource.instance().phoneNumberList.get(0));
        //�����Ի���
        AlertDialog dlg = new AlertDialog.Builder(activity)
        .setTitle("����")
        .setView(dialog_view)//�����Զ���Ի������ʽ
        .setPositiveButton("ȷ��", //����"ȷ��"��ť
        new DialogInterface.OnClickListener() //�����¼�����
        {
            public void onClick(DialogInterface dialog, int whichButton) 
            {
            	EditText phoneNumberEditText = (EditText)dialog_view.findViewById(R.id.phoneNumber);
            	GlobalResource.instance().phoneNumberList.set(0, phoneNumberEditText.getText().toString());
            	GlobalResource.instance().saveConfig();
            }
        })
        .setNegativeButton("ȡ��", //���á�ȡ������ť
        new DialogInterface.OnClickListener() 
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
            	//���"ȡ��"��ť֮��
            }
        })
        .create();//����
        dlg.show();//��ʾ
	}
	
	/*
	 * ���Ͷ������ü��ƶ�Э�飺���ȷ��������Զ����������ȡ��������
	 */
	public static void showTimeSettingDlg(final Activity activity){
		LayoutInflater factory = LayoutInflater.from(activity);
		final View dialog_view = factory.inflate(R.layout.activity_time_setting, null);
		
		switch (GlobalResource.instance().refreshTime) {
		case 10:
			((RadioButton)dialog_view.findViewById(R.id.ten_seconds)).setChecked(true);
			break;

		case 30:
			((RadioButton)dialog_view.findViewById(R.id.thirty_seconds)).setChecked(true);
			break;

		case 60:
			((RadioButton)dialog_view.findViewById(R.id.one_minute)).setChecked(true);
			break;

		default:
			((RadioButton)dialog_view.findViewById(R.id.one_time)).setChecked(true);
			break;
		}
		
		//�����Ի���
		AlertDialog dlg = new AlertDialog.Builder(activity)
		.setTitle("ѡ��ʱ��")
		.setView(dialog_view)//�����Զ���Ի������ʽ
		.setPositiveButton("ȷ��", //����"ȷ��"��ť
		new DialogInterface.OnClickListener(){ //�����¼�����
			public void onClick(DialogInterface dialog, int whichButton)
			{
				//�жϷ��Ͷ�λ�����ʱ�䣬���ȷ����ť���Ͷ���
				RadioGroup timeGroup = (RadioGroup)dialog_view.findViewById(R.id.setting_time);
				int selectId = timeGroup.getCheckedRadioButtonId();
				int refreshTime = 0;
				switch (selectId) {
				case R.id.ten_seconds:
					refreshTime = 10;
					break;
				case R.id.thirty_seconds:
					refreshTime = 30;
					break;
				case R.id.one_minute:
					refreshTime = 60;
					break;
				case R.id.one_time:
					refreshTime = 0;
					break;
				}
				
				// Э���ȶ��ɼ򵥵ط������֣����������������ַ���ƥ�䣬�鷳��
				// ������ֱ�ӱ�ʾ���뷢һ�Σ�
				// �����0�����ʾֻ��һ�κ��ٷ��ͣ�
				// �����-1�����ʾ���޸ĵ�ǰˢ��ʱ�䣬������Ҫ���ϸ��ҷ�һ�Ρ�
				Log.d(GlobalResource.TAG, "refreshTime = " + refreshTime);
				GlobalResource.instance().refreshTime = refreshTime;
				GlobalResource.instance().saveConfig();
				SMSSender.send("" + refreshTime);
			}
			})
		.setNegativeButton("ȡ��", //���á�ȡ������ť
        new DialogInterface.OnClickListener() 
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
            	//���"ȡ��"��ť֮�󲻽��ж�λ
            	
            }
        })
			.create();//����
		dlg.show();//��ʾ
		}
	
}
