package exlege.ru.urn.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import exlege.ru.scpurn.R;
import ru.exlege.bean.AveragePair;
import ru.exlege.dao.VotoDao;
import ru.exlege.pojo.Analytics;

public class AnalyticsWindow extends AppCompatActivity {

    LinearLayout firstP, secondP, thirdP;
    TextView firstName, secondName, thirdName;
    TextView firstPt, secondPt, thirdPt;
    ProgressBar firstProgress, secondProgress, thirdProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics_window);

        firstP = (LinearLayout) findViewById(R.id.aawfirstPanel);
        firstName = (TextView) findViewById(R.id.aawFirstName);
        firstPt = (TextView) findViewById(R.id.aawfirstPercentage);
        firstProgress = (ProgressBar) findViewById(R.id.aawfirstBar);

        secondP = (LinearLayout) findViewById(R.id.aawsecondPanel);
        secondName = (TextView) findViewById(R.id.aawsecondName);
        secondPt = (TextView) findViewById(R.id.aawsecondPercentage);
        secondProgress = (ProgressBar) findViewById(R.id.aawsecondBar);

        thirdP = (LinearLayout) findViewById(R.id.aawthirthPanel);
        thirdName = (TextView) findViewById(R.id.aawthirthName);
        thirdPt = (TextView) findViewById(R.id.aawthirthPercentage);
        thirdProgress = (ProgressBar) findViewById(R.id.aawthirthBar);

        Analytics analytics = new VotoDao(this).getAnalytcs();
        if(analytics.getTotal()>0) {
            Log.e("a", analytics.toString());
            int count = 0;
            float seizure;

            for (AveragePair pairs : analytics.getPair()) {
                count++;
                seizure = ((float) pairs.getVotes() / (float) analytics.getTotal());
                float progress = ((float) pairs.getVotes() / (float) analytics.getTotal()) * 100;
                switch (count) {
                    case 1:
                        firstName.setText(pairs.getName());
                        firstProgress.setProgress((int) Math.floor(progress));
                        firstPt.setText(String.format("%.02f", progress) + "%");
                    case 2:
                        secondName.setText(pairs.getName());
                        secondProgress.setProgress((int) Math.floor(progress));
                        secondPt.setText(String.format("%.02f", progress) + "%");
                    case 3:
                        thirdName.setText(pairs.getName());
                        thirdProgress.setProgress((int) Math.floor(progress));
                        thirdPt.setText(String.format("%.02f", progress) + "%");
                    default:
                        break;
                }
            }

            switch (analytics.getPair().size()) {
                case 0:
                    firstP.setVisibility(View.GONE);
                    secondP.setVisibility(View.GONE);
                    thirdP.setVisibility(View.GONE);
                    TextView analyMsg = (TextView) findViewById(R.id.aawAnalytics);
                    analyMsg.setText("\n\nOps, nenhuma estatística disponível!");
                case 1:
                    secondP.setVisibility(View.GONE);
                    thirdP.setVisibility(View.GONE);
                case 2:
                    thirdP.setVisibility(View.GONE);
                default:
                    break;
            }
        }else{
            firstP.setVisibility(View.GONE);
            secondP.setVisibility(View.GONE);
            thirdP.setVisibility(View.GONE);
            TextView analyMsg = (TextView) findViewById(R.id.aawAnalytics);
            analyMsg.setText("\n\nOps, nenhuma estatística disponível!");
        }
    }
}
