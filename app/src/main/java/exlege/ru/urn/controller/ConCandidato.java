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

public class ConCandidato extends AppCompatActivity {

    private EditText canPid, canNome, canPartido;
    private Button src ,alt, del;

    private Candidato candidate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_con_candidato);

        canPid = (EditText) findViewById(R.id.ccCandidactPid);
        canNome = (EditText) findViewById(R.id.ccCandidactNome);
        canPartido = (EditText) findViewById(R.id.ccCandidactPartido);
        alt = (Button) findViewById(R.id.ccAlterar);
        del = (Button) findViewById(R.id.ccDeletar);
        src = (Button) findViewById(R.id.ccSearch);

        alt.setEnabled(false);
        del.setEnabled(false);

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConCandidato.this.candidate != null){
                    int pid = ConCandidato.this.candidate.getPid();
                    boolean done = new CandidatoDao(ConCandidato.this).deletar(pid);

                    if(done){
                        Toast.makeText(ConCandidato.this, "Candidato Deletado com Sucesso!", Toast.LENGTH_SHORT).show();
                        ConCandidato.this.candidate = null;
                        alt.setEnabled(false);
                        del.setEnabled(false);
                        canNome.setText("");
                        canPartido.setText("");
                        canPid.setText("");
                    }else{
                        Toast.makeText(ConCandidato.this, "Erro ao Deletar Candidato!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        alt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConCandidato.this.candidate != null){
                    Candidato c = ConCandidato.this.candidate;
                    int pid = c.getPid();
                    c.setPartido(ConCandidato.this.canPartido.getText().toString());
                    c.setPid(Integer.valueOf(ConCandidato.this.canPid.getText().toString()));
                    c.setNome(ConCandidato.this.canNome.getText().toString());

                    boolean alterar = new CandidatoDao(ConCandidato.this).alterar(pid,c);

                    if(alterar){
                        Toast.makeText(ConCandidato.this, "Alteração realizada com sucesso!", Toast.LENGTH_SHORT).show();

                        ConCandidato.this.candidate = null;
                        alt.setEnabled(false);
                        del.setEnabled(false);
                        canNome.setText("");
                        canPartido.setText("");

                    }else{
                        Toast.makeText(ConCandidato.this, "Alteração não completada!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        src.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Candidato can = new CandidatoDao(ConCandidato.this).consultar(Integer.parseInt(canPid.getText().toString()));

                    if (can != null) {
                        ConCandidato.this.candidate = can;
                        alt.setEnabled(true);
                        del.setEnabled(true);
                        canNome.setText(can.getNome());
                        canPartido.setText(can.getPartido());

                    } else {
                        ConCandidato.this.candidate = null;
                        alt.setEnabled(false);
                        del.setEnabled(false);
                        canNome.setText("");
                        canPartido.setText("");

                        Toast.makeText(ConCandidato.this, "Candidato não encontrado!", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception ex){
                    Toast.makeText(ConCandidato.this, "Candidato não encontrado!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
