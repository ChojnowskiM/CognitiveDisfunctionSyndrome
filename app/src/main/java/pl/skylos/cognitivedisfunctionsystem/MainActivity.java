package pl.skylos.cognitivedisfunctionsystem;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Check saved data and returns it.
        if (savedInstanceState != null) {
            pointsGained = savedInstanceState.getInt("pointsGained");
            questionNumber = savedInstanceState.getInt("questionNumber");
            positionX = savedInstanceState.getInt("position");
            RadioGroup rg = findViewById(R.id.q1);
            if (positionX != -1 ) {
                ((RadioButton) rg.getChildAt(positionX)).setChecked(true);
            }
            String question = setQuestion(questionNumber);
            TextView questionAsk = findViewById(R.id.question);
            questionAsk.setText(question);
        }
    }
    //Saving data (radiobuttons state, questions, points, etc. state).
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("pointsGained", pointsGained);
        outState.putInt("questionNumber", questionNumber);
        if (questionNumber <= 16) {
        RadioGroup rg = findViewById(R.id.q1);
            int id = rg.getCheckedRadioButtonId();
            View radioB = rg.findViewById(id);
            positionX = rg.indexOfChild(radioB);
            outState.putInt("position", positionX);
        }
    }
    //variable holds number of points gained in quiz.
    public int pointsGained = 0;
    //variable setting correct question from array setQuestion.
    public int questionNumber = 0;
    //variable set which radiobutton is checked.
    public int positionX;


    //Method which makes button "next" working as intended.
    public void nextQuestion(View view) {
        TextView segment = findViewById(R.id.segment);
        TextView segmentName = findViewById(R.id.segment_desc);
        TextView questionNum = findViewById(R.id.question_number);
        TextView cdsPointsText = findViewById(R.id.cds_points);
        RadioGroup rg = findViewById(R.id.q1);
        ProgressBar questNum = findViewById(R.id.questions_bar);
        ProgressBar cdsPoints = findViewById(R.id.cds_bar);
        Button nextQ = findViewById(R.id.next);
        //statement check is there any button checked, if not do nothing.
        if (rg.getCheckedRadioButtonId() == -1) {
        } else {
            questionNumber += 1;
            if (questionNumber >= 16) {
                nextQ.setText(getString(R.string.result));
                points();
                rg.clearCheck();
                questionNum.setText(getString(R.string.question_number) + (questionNumber+1) + getString(R.string.of) + " 17");
                cdsPointsText.setText(getString(R.string.dog_results) + pointsGained);
                questNum.setProgress(questionNumber);
                cdsPoints.setProgress(pointsGained);
                if ( questionNumber > 16 ) {
                    setContentView(R.layout.scores);
                    TextView finalScore = findViewById(R.id.final_score);
                    ProgressBar cds2 = findViewById(R.id.cds_bar2);
                    TextView scoreDesc = findViewById(R.id.score_desc);
                    finalScore.setText(String.valueOf(pointsGained));
                    cds2.setProgress(pointsGained);
                    if ( pointsGained < 8 ) {
                        scoreDesc.setText(getString(R.string.points_normal));
                    } else if (pointsGained >= 8 && pointsGained <= 23 ) {
                        scoreDesc.setText(getString(R.string.points_low));
                    } else if (pointsGained >= 24 && pointsGained <= 44 ) {
                        scoreDesc.setText(getString(R.string.points_average));
                    } else if (pointsGained >= 45) {
                        scoreDesc.setText(getString(R.string.points_high));
                    }
                }

            } else {
                String question = setQuestion(questionNumber);
                TextView questionAsk = findViewById(R.id.question);
                questionAsk.setText(question);
                points();
                rg.clearCheck();
                questionNum.setText(getString(R.string.question_number) + (questionNumber+1) + getString(R.string.of) + " 17");
                cdsPointsText.setText(getString(R.string.dog_results) + pointsGained);
                questNum.setProgress(questionNumber);
                cdsPoints.setProgress(pointsGained);
                if (questionNumber > 4 && questionNumber < 10) {
                    segment.setText("B");
                    segmentName.setText(getString(R.string.segment_B));
                } else if (questionNumber > 9 && questionNumber < 12) {
                    segment.setText("C");
                    segmentName.setText(getString(R.string.segment_C));
                } else if (questionNumber > 11) {
                    segment.setText("D");
                    segmentName.setText(getString(R.string.segment_D));
                }
            }
        }
    }

    // Method which calls question.
    public String setQuestion(int questionNumber) {
        String[] question = new String[17];
        question[0] = getString(R.string.A_dezorienation);
        question[1] = getString(R.string.A_people_humans);
        question[2] = getString(R.string.A_known_items);
        question[3] = getString(R.string.A_pointless_walking);
        question[4] = getString(R.string.A_skills);
        question[5] = getString(R.string.B_interactions);
        question[6] = getString(R.string.B_behaviors);
        question[7] = getString(R.string.B_commands);
        question[8] = getString(R.string.B_irritation);
        question[9] = getString(R.string.B_aggression);
        question[10] = getString(R.string.C_night);
        question[11] = getString(R.string.C_sleeping);
        question[12] = getString(R.string.D_inside);
        question[13] = getString(R.string.D_bed);
        question[14] = getString(R.string.D_signals);
        question[15] = getString(R.string.D_walk);
        question[16] = getString(R.string.D_uncommon);
        return question[questionNumber];
    }
    public int points () {
        RadioGroup rg = findViewById(R.id.q1);
        int id = rg.getCheckedRadioButtonId();
        View radioB = rg.findViewById(id);
        int position = rg.indexOfChild(radioB);

        switch( position ) {
            case 0:
                pointsGained += 0;
                break;
            case 1:
                if (questionNumber == 10 || questionNumber == 11) {
                pointsGained += 4;
                } else {
                pointsGained += 2;
                }
                break;
            case 2:
                if (questionNumber == 10 || questionNumber == 11) {
                    pointsGained += 6;
                } else {
                    pointsGained += 3;
                }
                break;
            case 3:
                if (questionNumber == 10 || questionNumber == 11) {
                    pointsGained += 8;
                } else {
                    pointsGained += 4;
                }
                break;
            case 4:
                if (questionNumber == 10 || questionNumber == 11) {
                    pointsGained += 10;
                } else {
                    pointsGained += 5;
                }
                break;
        }
        return pointsGained;
    }
    public void restart(View view) {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
    public void readMore(View view) {
        String url = getString(R.string.url);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
    public void quit(View view) {
        finish();
    }
}
