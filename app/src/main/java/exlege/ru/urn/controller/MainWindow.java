package exlege.ru.urn.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import exlege.ru.scpurn.R;


public class MainWindow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);
        Toast.makeText(this, "Bem-Vindo!", Toast.LENGTH_SHORT).show();
    }
}
