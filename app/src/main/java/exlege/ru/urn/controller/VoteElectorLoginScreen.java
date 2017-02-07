package exlege.ru.urn.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import exlege.ru.scpurn.R;
import ru.exlege.bean.Eleitor;
import ru.exlege.dao.EleitorDao;
import ru.exlege.dao.VotoDao;

public class VoteElectorLoginScreen extends AppCompatActivity {
    private TextView etTitulo;
    private Button bVotar;

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

                }catch(Exception ex){
                    Toast.makeText(VoteElectorLoginScreen.this, "Eleitor não encontrado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
