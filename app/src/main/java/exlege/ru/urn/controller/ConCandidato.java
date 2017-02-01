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

        src.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Candidato can = new CandidatoDao(ConCandidato.this).consultar(Integer.parseInt(canPid.getText().toString()));

                if(can != null){
                    alt.setEnabled(true);
                    del.setEnabled(true);
                    canNome.setText(can.getNome());
                    canPartido.setText(can.getPartido());

                }else{
                    alt.setEnabled(false);
                    del.setEnabled(false);
                    canNome.setText("");
                    canPartido.setText("");

                    Toast.makeText(ConCandidato.this, "Candidato não encontrado!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
