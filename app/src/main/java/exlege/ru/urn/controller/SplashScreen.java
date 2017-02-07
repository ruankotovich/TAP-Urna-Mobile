package exlege.ru.urn.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import exlege.ru.scpurn.R;
import ru.exlege.dao.PrivilegesDao;

public class SplashScreen extends AppCompatActivity {

    Button loginButton;
    EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        loginButton = (Button) findViewById(R.id.loginButton);
        passwordField = (EditText) findViewById(R.id.adminPassword);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean login = new PrivilegesDao(SplashScreen.this).verifyPrivileges(passwordField.getText().toString());

                if(login){
                    passwordField.setText("");
                    startActivity(new Intent(SplashScreen.this, MainWindow.class));
                }else{
                    Toast.makeText(SplashScreen.this, "Token Incorreto.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
