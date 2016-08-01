package com.fongwama.mosungi.functions;

import android.support.design.widget.TextInputEditText;

/**
 * Created by Karl on 05/07/2016.
 */
public class MyFunctions {

    //Fonctio qui vérifie si ub=n EditText est vide ou pas
    public Boolean checkTextInputEditText(TextInputEditText textInputEditText)
    {
        Boolean result = true;
        if (textInputEditText.getText().toString().isEmpty())
        {
            result = false;
        }

        return result;
    }

    public Boolean checkPhoneNumberFormat(TextInputEditText phoneNumberEditText)
    {
        Boolean result = true;
        if (phoneNumberEditText.getText().toString().length() == 9 )
        {
            //Vérifie si le numéro a bel et bien 9 chiffres

        }
        else
        {
            result = false;
        }

        return result;
    }
}
