package exlege.ru.urn.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import exlege.ru.scpurn.R;
import ru.exlege.bean.Candidato;
import ru.exlege.bean.Eleitor;
import ru.exlege.dao.CandidatoDao;
import ru.exlege.dao.EleitorDao;

public class ConEleitor extends AppCompatActivity {

    private EditText eleTitulo, eleNome;
    private Button src, alt, del;

    private Eleitor eleitor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_con_eleitor);

        eleTitulo = (EditText) findViewById(R.id.coeTitulo);
        eleNome = (EditText) findViewById(R.id.coeNome);
        alt = (Button) findViewById(R.id.coeAlterar);
        del = (Button) findViewById(R.id.coeDeletar);
        src = (Button) findViewById(R.id.coeSearch);

        alt.setEnabled(false);
        del.setEnabled(false);


        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConEleitor.this.eleitor != null) {
                    long pid = ConEleitor.this.eleitor.getTitulo();
                    boolean done = new EleitorDao(ConEleitor.this).deletar(pid);

                    if (done) {
                        Toast.makeText(ConEleitor.this, "Eleitor Deletado com Sucesso!", Toast.LENGTH_SHORT).show();
                        ConEleitor.this.eleitor = null;
                        alt.setEnabled(false);
                        del.setEnabled(false);
                        eleNome.setText("");
                        eleTitulo.setText("");
                    } else {
                        Toast.makeText(ConEleitor.this, "Erro ao Deletar Eleitor!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        alt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConEleitor.this.eleitor != null) {
                    Eleitor c = ConEleitor.this.eleitor;
                    long pid = c.getTitulo();

                    c.setTitulo(Long.valueOf(ConEleitor.this.eleTitulo.getText().toString()));
                    c.setNome(ConEleitor.this.eleNome.getText().toString());

                    boolean alterar = new EleitorDao(ConEleitor.this).alterar(pid, c);

                    if (alterar) {
                        Toast.makeText(ConEleitor.this, "Alteração realizada com sucesso!", Toast.LENGTH_SHORT).show();

                        ConEleitor.this.eleitor = null;
                        alt.setEnabled(false);
                        del.setEnabled(false);
                        eleNome.setText("");
                        eleTitulo.setText("");

                    } else {
                        Toast.makeText(ConEleitor.this, "Alteração não completada!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        src.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Eleitor can = new EleitorDao(ConEleitor.this).consultar(Long.parseLong(eleTitulo.getText().toString()));

                    if (can != null) {
                        ConEleitor.this.eleitor = can;
                        alt.setEnabled(true);
                        del.setEnabled(true);
                        eleNome.setText(can.getNome());

                    } else {
                        ConEleitor.this.eleitor = null;
                        alt.setEnabled(false);
                        del.setEnabled(false);
                        eleNome.setText("");

                        Toast.makeText(ConEleitor.this, "Eleitor não encontrado!", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception ex) {
                    Toast.makeText(ConEleitor.this, "Eleitor não encontrado!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
