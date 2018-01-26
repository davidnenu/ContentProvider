package com.example.dm2.contentprovider;

import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //String[] TIPO_LLAMADA = {"", "entrante", "saliente", "perdida"};
        TextView salida = (TextView) findViewById(R.id.salida);
        //Uri llamadas = Uri.parse("content://contacts/people"); MAL
        Cursor c = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        //int i = c.getColumnCount()-1;
        //for(int j=0;j<i;j++){
        //    salida.append("\n" + c.getColumnName(j));
        //}
        while (c.moveToNext()) {
            salida.append("\n" +  "----"+c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY))+"----");
            String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

            if(Integer.parseInt(hasPhone)==1) {
                // You know it has a number so now query it like this
                salida.append("\n Tfns: ");
                Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + c.getString(c.getColumnIndex(ContactsContract.Contacts._ID)), null, null);
                while (phones.moveToNext()) {
                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    salida.append("\n" + phoneNumber);
                }
                phones.close();
            }

        }
        c.close();


    }
}
