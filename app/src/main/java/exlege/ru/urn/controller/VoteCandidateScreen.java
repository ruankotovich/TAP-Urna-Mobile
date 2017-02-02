package exlege.ru.urn.controller;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import exlege.ru.scpurn.R;
import ru.exlege.bean.Candidato;
import ru.exlege.bean.Eleitor;
import ru.exlege.dao.CandidatoDao;
import ru.exlege.pojo.CandidatoAdapter;

public class VoteCandidateScreen extends AppCompatActivity {

    private TextView electorInformations;
    private ListView candidateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_candidate_screen);

        electorInformations = (TextView) findViewById(R.id.vcsElector);
        candidateList = (ListView) findViewById(R.id.list);

        Eleitor ele = (Eleitor) getIntent().getSerializableExtra("eleitor");
        electorInformations.setText("Eleitor : "+ele.getNome()+" - "+ele.getTitulo());

        final ArrayList<Candidato> candidatos = new CandidatoDao(this).candidatos();
        Candidato nulo = new Candidato(0);
        nulo.setNome("Voto Nulo");
        nulo.setPartido("NULO");
        candidatos.add(nulo);

        candidateList.setAdapter(new CandidatoAdapter(this, candidatos));

    }
}
