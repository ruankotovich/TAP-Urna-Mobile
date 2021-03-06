package exlege.ru.urn.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import exlege.ru.scpurn.R;
import ru.exlege.dao.PrivilegesDao;
import ru.exlege.pojo.Analytics;


public class MainWindow extends AppCompatActivity {

    private Button electorButton, candidateButton, voteButton, analyticsButton, tokenButton;
    private EditText tokenPassword;
    private LinearLayout tokenView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);

        electorButton = (Button) findViewById(R.id.electorButton);
        candidateButton = (Button) findViewById(R.id.candidateButton);
        voteButton = (Button) findViewById(R.id.voteButton);
        analyticsButton = (Button) findViewById(R.id.analyticsButton);
        tokenView = (LinearLayout) findViewById(R.id.mwToken);
        tokenPassword = (EditText) findViewById(R.id.mwTokenET);
        tokenButton = (Button) findViewById(R.id.mwTokenBT);

        tokenView.setVisibility(View.INVISIBLE);

        buildListeners();

        Toast.makeText(this, "Bem-Vindo!", Toast.LENGTH_SHORT).show();
    }

    private void buildListeners() {

        voteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tokenView.setVisibility(View.VISIBLE);
                tokenPassword.requestFocus();
            }
        });

        tokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String pass = tokenPassword.getText().toString();
                boolean login = new PrivilegesDao(MainWindow.this).verifyPrivileges(pass);
                boolean isDeadlock = new PrivilegesDao(MainWindow.this).verifyDeadlock();
                if (login) {
                    if (!isDeadlock) {
                        startActivity(new Intent(MainWindow.this, VoteElectorLoginScreen.class));
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainWindow.this);
                        builder.setMessage("A votação foi encerrada, deseja reabrir a votação?");

                        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                new PrivilegesDao(MainWindow.this).revokeDeadlock();
                                startActivity(new Intent(MainWindow.this, VoteElectorLoginScreen.class));
                            }
                        });

                        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                } else {
                    Toast.makeText(MainWindow.this, "Token Incorreto.", Toast.LENGTH_SHORT).show();
                }
                tokenPassword.setText("");
                tokenView.setVisibility(View.INVISIBLE);
            }
        });

        candidateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainWindow.this);
                builder.setMessage("Qual operação deseja realizar com \'Candidato\'?");

                builder.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(MainWindow.this, CadCandidato.class));
                    }
                });

                builder.setNegativeButton("Consultar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(MainWindow.this, ConCandidato.class));
                    }
                });

                builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        electorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainWindow.this);
                builder.setMessage("Qual operação deseja realizar com \'Eleitor\'?");

                builder.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(MainWindow.this, CadEleitor.class));
                    }
                });

                builder.setNegativeButton("Consultar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(MainWindow.this, ConEleitor.class));
                    }
                });

                builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        analyticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new PrivilegesDao(MainWindow.this).verifyDeadlock()) {
                    startActivity(new Intent(MainWindow.this, AnalyticsWindow.class));
                } else {
                    Toast.makeText(MainWindow.this, "A votação ainda não foi encerrada\nEncerre a votação para ver as estatísticas", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
