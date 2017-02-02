package ru.exlege.pojo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import exlege.ru.scpurn.R;
import ru.exlege.bean.Candidato;

/**
 * Created by dmitry on 2/1/17.
 */

public class CandidatoAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Candidato> mDataSource;

    public CandidatoAdapter(Context context, ArrayList<Candidato> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //1
    @Override
    public int getCount() {
        return mDataSource.size();
    }

    //2
    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    //3
    @Override
    public long getItemId(int position) {
        return position;
    }

    //4
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get view for row item
        View rowView = mInflater.inflate(R.layout.linear_candidate_adapted, parent, false);
        TextView tvPartido = (TextView) rowView.findViewById(R.id.lcaPartido);
        TextView tvNome = (TextView) rowView.findViewById(R.id.lcaNome);

        Candidato can = (Candidato) getItem(position);
        tvPartido.setText(can.getPartido());
        tvNome.setText(can.getNome()+" - "+can.getPid());

        return rowView;
    }
}
