package exlege.ru.urn.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import exlege.ru.scpurn.R;
import ru.exlege.bean.Candidato;
import ru.exlege.dao.CandidatoDao;

public class CadCandidato extends AppCompatActivity {

    private EditText canPid, canNome, canPartido;
    private Button cad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_candidato);

        canPid = (EditText) findViewById(R.id.mcCandidactPid);
        canNome = (EditText) findViewById(R.id.mcCandidactNome);
        canPartido = (EditText) findViewById(R.id.mcCandidactPartido);
        cad = (Button) findViewById(R.id.mcCadastrar);

        cad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Candidato can = new Candidato(Integer.parseInt(canPid.getText().toString()));
                can.setNome(canNome.getText().toString());
                can.setPartido(canPartido.getText().toString());

                boolean cadastro = new CandidatoDao(CadCandidato.this).cadastrar(can);

                if(cadastro){
                    Toast.makeText(CadCandidato.this, "Cadastrado com Sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(CadCandidato.this, "Erro no cadastro!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
