package exlege.ru.urn.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import exlege.ru.scpurn.R;
import ru.exlege.bean.Eleitor;
import ru.exlege.dao.EleitorDao;

public class CadEleitor extends AppCompatActivity {

    private Button bCad;
    private EditText etNome, etTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_eleitor);
        bCad = (Button) findViewById(R.id.ceCadastrar);
        etNome = (EditText) findViewById(R.id.ceNome);
        etTitulo = (EditText) findViewById(R.id.ceTitulo);

        bCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Eleitor ele = new Eleitor(Long.parseLong(etTitulo.getText().toString()), etNome.getText().toString());

                    boolean done = new EleitorDao(CadEleitor.this).cadastrar(ele);

                    if (done) {
                        Toast.makeText(CadEleitor.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(CadEleitor.this, "Erro no cadastro!", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception ex){
                    Toast.makeText(CadEleitor.this, "Erro no cadastro!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
