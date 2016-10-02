package com.fongwama.mosungi.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fongwama.mosungi.R;
import com.fongwama.mosungi.functions.ColoredSnackbar;
import com.fongwama.mosungi.functions.MyFunctions;

public class ConnexionActivity extends AppCompatActivity {
    private Button validate;
    private TextInputEditText pseudo;
    private TextInputEditText password;
    private CoordinatorLayout coordinatorLayout;

    MyFunctions myFunctions = new MyFunctions();
    ColoredSnackbar coloredSnackbar = new ColoredSnackbar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.cordinatorLayout);

        pseudo      = (TextInputEditText) findViewById(R.id.pseudo);
        password    = (TextInputEditText) findViewById(R.id.password);

        validate = (Button)findViewById(R.id.validate);
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (myFunctions.checkTextInputEditText(pseudo) && myFunctions.checkTextInputEditText(password))
                {
                    String pseudoText   = pseudo.getText().toString();
                    String passwordText = password.getText().toString();

                    startActivity(new Intent(ConnexionActivity.this, HomeActivity.class));
                }
                else
                {
                    Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.alert_empty, Snackbar.LENGTH_SHORT);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.WHITE);
                    snackbar.show();
                    //coloredSnackbar.alert(snackbar, getApplicationContext()).show();
                    //Snackbar.make(v, R.string.alert_empty, Snackbar.LENGTH_LONG).show();
                }

            }
        });
        findViewById(R.id.sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConnexionActivity.this, InscriptionActivity.class));
            }
        });


    }

}
