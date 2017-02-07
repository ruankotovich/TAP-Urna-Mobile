package exlege.ru.urn.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import exlege.ru.scpurn.R;
import ru.exlege.bean.Candidato;
import ru.exlege.bean.Eleitor;
import ru.exlege.dao.CandidatoDao;
import ru.exlege.dao.VotoDao;
import ru.exlege.pojo.CandidatoAdapter;

public class VoteCandidateScreen extends AppCompatActivity {

    private TextView electorInformations;
    private ListView candidateList;
    private Button votar;
    private Eleitor ele;

    Candidato can = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_candidate_screen);

        electorInformations = (TextView) findViewById(R.id.vcsElector);
        candidateList = (ListView) findViewById(R.id.list);
        votar = (Button) findViewById(R.id.vcsVotar);
        votar.setVisibility(View.INVISIBLE);

        ele = (Eleitor) getIntent().getSerializableExtra("eleitor");
        electorInformations.setText("Eleitor : "+ele.getNome()+" - "+ele.getTitulo());

        final ArrayList<Candidato> candidatos = new CandidatoDao(this).candidatos();

        Candidato nulo = new Candidato(0);
        nulo.setNome("Voto Nulo");
        nulo.setPartido("<>");
        candidatos.add(nulo);

        Candidato branco = new CandidatoDao(this).getWhite();

        if(branco != null){
            branco.setPartido("<>");
            branco.setNome("Voto Branco");
            candidatos.add(branco);
        }

        candidateList.setAdapter(new CandidatoAdapter(this, candidatos));

        candidateList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                votar.setVisibility(View.VISIBLE);
                VoteCandidateScreen.this.can = (Candidato) candidateList.getItemAtPosition(position);
                Toast.makeText(VoteCandidateScreen.this, VoteCandidateScreen.this.can.getNome(), Toast.LENGTH_SHORT).show();
            }
        });

        votar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Candidato c = VoteCandidateScreen.this.can;
                if(c != null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(VoteCandidateScreen.this);
                    builder.setMessage("Deseja realmente votar no candidato '"+c.getNome()+"' ?");

                    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            new VotoDao(VoteCandidateScreen.this).commitVote(ele.getTitulo(), can.getPid(), can.getPartido().equals("BRANCO"));
                            Toast.makeText(VoteCandidateScreen.this, "Voto consumado!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });

                    builder.setNeutralButton("Não", new DialogInterface.OnClickListener()     {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }else{
                    Toast.makeText(VoteCandidateScreen.this, "Selecione um candidato!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
