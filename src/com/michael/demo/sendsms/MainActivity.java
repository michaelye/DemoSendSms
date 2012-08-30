package com.michael.demo.sendsms;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This demo shows how to send message in self application.
 * 
 * ���demoչʾ��������Լ��ĳ����з��Ͷ���
 * 
 *	�ο���http://stackoverflow.com/questions/8447735/android-sms-type-constants
 *	MESSAGE_TYPE_ALL    = 0;//����(��2һ��Ч��)
 *	MESSAGE_TYPE_INBOX  = 1;//����
 *	MESSAGE_TYPE_SENT   = 2;//����
 *	MESSAGE_TYPE_DRAFT  = 3;//���ڲݸ�����
 *	MESSAGE_TYPE_OUTBOX = 4;//�����䣨�ͷ�����һ��Ч����
 *	MESSAGE_TYPE_FAILED = 5; // for failed outgoing messages����ʧ��
 *	MESSAGE_TYPE_QUEUED = 6; // for messages to send later//������
 * 
 *  ContentValues values = new ContentValues();
 *  values.put("address", "13023895555");
 *  values.put("body", "short message content");
 *  values.put("date", "1322039220502");
 *  values.put("type", "1");
 *  values.put("status", "-1");
 *  values.put("read", "1");
 *  values.put("protocol", "0");
 *  getContentResolver().insert(Uri.parse("content://sms"), values);
 * 
 * 
 * @author MichaelYe
 * @since 2012-8-30
 * 
 * */
public class MainActivity extends Activity 
{

	private EditText etNumber;
	private EditText etSmsContent;
	private Button btnSend;
	private Button btnCancel;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etNumber = (EditText)findViewById(R.id.et_number);
        etSmsContent = (EditText)findViewById(R.id.et_sms_content);
        btnSend = (Button)findViewById(R.id.btn_send);
        btnCancel = (Button)findViewById(R.id.btn_cancel);
        
        btnSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				String phoneNumber = etNumber.getText().toString().trim();
				if(phoneNumber.equals(""))
				{
					Toast.makeText(MainActivity.this, "Number can not be empty!", Toast.LENGTH_SHORT).show();
					return;
				}
				else
				{
					String smsContent = etSmsContent.getText().toString().trim();
					sendSms(phoneNumber, smsContent);
					writeToDataBase(phoneNumber, smsContent);
				}
			}
		});
        btnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				finish();
			}
		});
    }

    /**
     * send sms
     * 
     * ���Ͷ���
     * 
     * */
    private void sendSms(String phoneNumber, String smsContent)
    {
    	SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, smsContent, null, null);
        Toast.makeText(this, "Send Success", Toast.LENGTH_LONG).show();
    }
    
    /**
     * write to database
     * 
     * д�����ݿ�
     * 
     * */
    private void writeToDataBase(String phoneNumber, String smsContent)
    {
    	ContentValues values = new ContentValues();
        values.put("address", phoneNumber);//�����ֶ���������
        values.put("body", smsContent);
        values.put("type", "2");
        values.put("read", "1");//1��ʾ�Ѷ�
        getContentResolver().insert(Uri.parse("content://sms/inbox"), values);
    }

}









