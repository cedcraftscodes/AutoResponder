package xyz.itcstutorial.autoresponder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Icorrelate on 9/30/2017.
 */

public class IncomingSms extends BroadcastReceiver {
    final SmsManager sms = SmsManager.getDefault();
    @Override
    public void onReceive(Context context, Intent intent) {


        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();
                    Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context,
                            "Sender: "+ senderNum + ", Message: " + message, duration);
                    toast.show();

                    if(message.trim().toLowerCase().equals("hi")){
                        sms.sendTextMessage(senderNum, null, "Hello. This text is automated :)", null, null);
                    }

                    //Adding Comment, this is a test committ, comment. 
                }
            }

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);
        }
    }
}
