package exlege.ru.urn.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import exlege.ru.scpurn.R;
import ru.exlege.bean.Eleitor;
import ru.exlege.dao.EleitorDao;
import ru.exlege.dao.PrivilegesDao;
import ru.exlege.dao.VotoDao;

public class VoteElectorLoginScreen extends AppCompatActivity {
    private TextView etTitulo;
    private Button bVotar;


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(VoteElectorLoginScreen.this);
            alertDialog.setTitle("Encerrar Votação?");
            alertDialog.setMessage("Deseja Encerrar a Votação?");

            final EditText input = new EditText(VoteElectorLoginScreen.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            alertDialog.setView(input);
            input.setInputType(InputType.TYPE_CLASS_TEXT |
                    InputType.TYPE_TEXT_VARIATION_PASSWORD);

            alertDialog.setPositiveButton("Encerrar",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            String password = input.getText().toString();
                            if (password.compareTo("") != 0) {
                                if (new PrivilegesDao(VoteElectorLoginScreen.this).verifyPrivileges(password)) {
                                    Toast.makeText(getApplicationContext(),
                                            "Votação Encerrada", Toast.LENGTH_SHORT).show();
                                    new PrivilegesDao(VoteElectorLoginScreen.this).doDeadlock(password);
                                    VoteElectorLoginScreen.this.finish();
                                } else {
                                    Toast.makeText(getApplicationContext(),
                                            "Senha Incorreta", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });

            alertDialog.setNegativeButton("Voltar sem Encerrar",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            String password = input.getText().toString();
                            if (password.compareTo("") != 0) {
                                if (new PrivilegesDao(VoteElectorLoginScreen.this).verifyPrivileges(password)) {
                                    VoteElectorLoginScreen.this.finish();
                                } else {
                                    Toast.makeText(getApplicationContext(),
                                            "Senha Incorreta", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });

            alertDialog.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            alertDialog.show();
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_elector_login_screen);

        etTitulo = (TextView) findViewById(R.id.velsTitulo);
        bVotar = (Button) findViewById(R.id.velsVotar);

        bVotar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    long titulo = Long.parseLong(etTitulo.getText().toString());
                    boolean able = new VotoDao(VoteElectorLoginScreen.this).verifyAble(titulo);

                    if (able) {
                        Eleitor ele = new EleitorDao(VoteElectorLoginScreen.this).consultar(titulo);
                        if (ele != null) {
                            etTitulo.setText("");
                            Intent it = new Intent(VoteElectorLoginScreen.this, VoteCandidateScreen.class);
                            it.putExtra("eleitor", ele);
                            startActivity(it);
                        } else {
                            Toast.makeText(VoteElectorLoginScreen.this, "Eleitor não encontrado", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(VoteElectorLoginScreen.this, "Ops, parece que você já votou.", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception ex) {
                    Toast.makeText(VoteElectorLoginScreen.this, "Eleitor não encontrado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
