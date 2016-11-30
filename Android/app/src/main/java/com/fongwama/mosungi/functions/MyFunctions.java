package com.fongwama.mosungi.functions;

import android.support.design.widget.TextInputEditText;

import java.text.SimpleDateFormat;

/**
 * Created by Karl on 05/07/2016.
 */
public class MyFunctions {

    //make unix server timestamp readable(by humans) as normal date
    public static String getReadableDate(long timeStamp){
        SimpleDateFormat shortenedDate = new SimpleDateFormat("EEE\ndd\nMMM");
        return shortenedDate.format(timeStamp);
    }

    public static String get24Hour(long timeStamp){

        SimpleDateFormat hour12to24 = new SimpleDateFormat("HH");
        return hour12to24.format(timeStamp);
    }

    //Fonctio qui vérifie si ub=n EditText est vide ou pas
    public Boolean checkTextInputEditText(TextInputEditText textInputEditText)
    {
        return textInputEditText.getText().toString().isEmpty() ? false: true;
    }

    public Boolean checkPhoneNumberFormat(TextInputEditText phoneNumberEditText)
    {
        //Vérifie si le numéro a bel et bien 9 chiffres
        return phoneNumberEditText.getText().toString().length() == 9 ? true : false;
    }
}
