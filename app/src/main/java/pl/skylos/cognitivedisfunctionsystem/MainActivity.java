package pl.skylos.cognitivedisfunctionsystem;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String pointsGainedKey = "pointsGained";
    private static final String questionNumberKey = "questionNumber";
    private static final String lastPointsKey = "lastPointsGained";
    private static final String lastPointsArrayKey = "lastPointsArray";
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    //variable stores data for last gained points.
    public int[] lastPoints = new int[17];
    int lastPointsGained = 0;
    //Variables defined to call findViewById once.
    private TextView segment;
    private TextView segmentName;
    private TextView questionNum;
    private TextView cdsPointsText;
    private Button nextQ;
    private TextView questionAsk;
    private RadioGroup rg;
    private ProgressBar questNum;
    private ProgressBar cdsPoints;
    private Button backButton;
    //variable holds number of points gained in quiz.
    private int pointsGained = 0;
    //variable setting correct question from array setQuestion.
    private int questionNumber = 0;
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //finding view for later use.
        setContentView(R.layout.activity_main);
        segment = findViewById(R.id.segment);
        segmentName = findViewById(R.id.segment_desc);
        questionNum = findViewById(R.id.question_number);
        cdsPointsText = findViewById(R.id.cds_points);
        nextQ = findViewById(R.id.next);
        questionAsk = findViewById(R.id.question);
        questNum = findViewById(R.id.questions_bar);
        cdsPoints = findViewById(R.id.cds_bar);
        rg = findViewById(R.id.q1);
        backButton = findViewById(R.id.back);
        backButton.setEnabled(false);
        //Check saved data and returns it.
        if (savedInstanceState != null) {
            pointsGained = savedInstanceState.getInt(pointsGainedKey);
            questionNumber = savedInstanceState.getInt(questionNumberKey);
            lastPoints = savedInstanceState.getIntArray(lastPointsArrayKey);
            lastPointsGained = savedInstanceState.getInt(lastPointsKey);
            if (questionNumber != 0) {
                backButton.setEnabled(true);
            }
            //if scores was displayed on screen - saved to display it.
            if (questionNumber == 17) {
                setContentView(R.layout.scores);
                TextView finalScore = findViewById(R.id.final_score);
                ProgressBar cds2 = findViewById(R.id.cds_bar2);
                TextView scoreDesc = findViewById(R.id.score_desc);
                finalScore.setText(String.valueOf(pointsGained));
                cds2.setProgress(pointsGained);
                //Set description for given scores.
                if (pointsGained < 8) {
                    scoreDesc.setText(getString(R.string.points_normal));
                } else if (pointsGained >= 8 && pointsGained <= 23) {
                    scoreDesc.setText(getString(R.string.points_low));
                } else if (pointsGained >= 24 && pointsGained <= 44) {
                    scoreDesc.setText(getString(R.string.points_average));
                } else if (pointsGained >= 45) {
                    scoreDesc.setText(getString(R.string.points_high));
                }
            } else {
                //if question was with id=16 or less it will retrieve data.
                String question = setQuestion(questionNumber);
                questionAsk.setText(question);
                questionNum.setText(getString(R.string.question_number) + " " + (questionNumber + 1) + " " + getString(R.string.of) + " " + " 17");
                cdsPointsText.setText(getString(R.string.dog_results) + " " + pointsGained);
                if (questionNumber > 4 && questionNumber < 10) {
                    segment.setText("B");
                    segmentName.setText(getString(R.string.segment_B));
                } else if (questionNumber > 9 && questionNumber < 12) {
                    segment.setText("C");
                    segmentName.setText(getString(R.string.segment_C));
                } else if (questionNumber > 11) {
                    segment.setText("D");
                    segmentName.setText(getString(R.string.segment_D));
                    if (questionNumber == 16) {
                        nextQ.setText(getString(R.string.result));
                    }
                }
            }
        }
    }

    //Saving data (questions, points, etc. state).
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(pointsGainedKey, pointsGained);
        outState.putInt(questionNumberKey, questionNumber);
        outState.putInt(lastPointsKey, lastPointsGained);
        outState.putIntArray(lastPointsArrayKey, lastPoints);
    }

    //it holds points for each of questions
    public int lastPointsCounter(int questionNumber, int lastPointsGained) {
        lastPoints[questionNumber] = lastPointsGained;
        lastPoints[questionNumber] = lastPointsGained;
        lastPoints[questionNumber] = lastPointsGained;
        lastPoints[questionNumber] = lastPointsGained;
        lastPoints[questionNumber] = lastPointsGained;
        lastPoints[questionNumber] = lastPointsGained;
        lastPoints[questionNumber] = lastPointsGained;
        lastPoints[questionNumber] = lastPointsGained;
        lastPoints[questionNumber] = lastPointsGained;
        lastPoints[questionNumber] = lastPointsGained;
        lastPoints[questionNumber] = lastPointsGained;
        lastPoints[questionNumber] = lastPointsGained;
        lastPoints[questionNumber] = lastPointsGained;
        lastPoints[questionNumber] = lastPointsGained;
        lastPoints[questionNumber] = lastPointsGained;
        lastPoints[questionNumber] = lastPointsGained;
        lastPoints[questionNumber] = lastPointsGained;
        return lastPoints[questionNumber];
    }

    //it holds behavior for clicking back button - going to question before and decrease points.
    public void onBackPressed() {
        if (questionNumber > 0 && questionNumber < 17) {
            if (questionNumber >= 16) {
                nextQ.setText(getString(R.string.next));
            }
            pointsGained -= lastPoints[questionNumber];
            questionNumber -= 1;
            if (questionNumber == 0) {
                backButton.setEnabled(false);
            }
            String question = setQuestion(questionNumber);
            questionAsk.setText(question);
            rg.clearCheck();
            questionNum.setText(getString(R.string.question_number) + " " + (questionNumber + 1) + " " + getString(R.string.of) + " " + " 17");
            cdsPointsText.setText(getString(R.string.dog_results) + " " + pointsGained);
            questNum.setProgress(questionNumber);
            cdsPoints.setProgress(pointsGained);
            //changing headers depends of question
            if (questionNumber > 4 && questionNumber < 10) {
                segment.setText("B");
                segmentName.setText(getString(R.string.segment_B));
            } else if (questionNumber > 9 && questionNumber < 12) {
                segment.setText("C");
                segmentName.setText(getString(R.string.segment_C));
            } else if (questionNumber > 11) {
                segment.setText("D");
                segmentName.setText(getString(R.string.segment_D));
            } else if (questionNumber < 5) {
                segment.setText("A");
                segmentName.setText(getString(R.string.segment_A));
            }
        } else if (questionNumber == 17) {
            //if scores layout is displayed restart when back button is clicked
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        } else if (questionNumber == 0) {
            if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
                super.onBackPressed();
                return;
            } else {
                Toast.makeText(getBaseContext(), R.string.tap_to_exit, Toast.LENGTH_SHORT).show();
            }
            mBackPressed = System.currentTimeMillis();
        }
    }

    public void backPressed(View view) {
        onBackPressed();
    }

    //Method which makes button "next" working as intended.
    public void nextQuestion(View view) {

        //statement check is there any button checked, if not do nothing.
        if (rg.getCheckedRadioButtonId() == -1) {
        } else {
            backButton.setEnabled(true);
            //moving user to next question
            questionNumber += 1;
            //run if it is the one before last question
            if (questionNumber >= 16) {
                //run if it is last question, showing results
                if (questionNumber > 16) {
                    points();
                    score(view);
                } else {
                    //sets question and TextViews correctly.
                    String question = setQuestion(questionNumber);
                    questionAsk.setText(question);
                    nextQ.setText(getString(R.string.result));
                    points();
                    setLastPoints();
                    lastPointsCounter(questionNumber, lastPointsGained);
                    rg.clearCheck();
                    questionNum.setText(getString(R.string.question_number) + " " + (questionNumber + 1) + " " + getString(R.string.of) + " " + " 17");
                    cdsPointsText.setText(getString(R.string.dog_results) + " " + pointsGained);
                    questNum.setProgress(questionNumber);
                    cdsPoints.setProgress(pointsGained);
                }

            } else {
                //updating question to next one, clearing radiobuttons, updating progress bars
                String question = setQuestion(questionNumber);
                questionAsk.setText(question);
                points();
                setLastPoints();
                lastPointsCounter(questionNumber, lastPointsGained);
                rg.clearCheck();
                questionNum.setText(getString(R.string.question_number) + " " + (questionNumber + 1) + " " + getString(R.string.of) + " " + " 17");
                cdsPointsText.setText(getString(R.string.dog_results) + " " + pointsGained);
                questNum.setProgress(questionNumber);
                cdsPoints.setProgress(pointsGained);
                //changing headers depends of question
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

    //This method add points depends of which button is checked.
    public int points() {
        int id = rg.getCheckedRadioButtonId();
        View radioB = rg.findViewById(id);
        int position = rg.indexOfChild(radioB);

        switch (position) {
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

    //method to retrieve correct amount of points acquired in last question.
    public int setLastPoints() {
        int id = rg.getCheckedRadioButtonId();
        View radioB = rg.findViewById(id);
        int position = rg.indexOfChild(radioB);
        switch (position) {
            case 0:
                lastPointsGained = 0;
                break;
            case 1:
                if (questionNumber == 10 || questionNumber == 11) {
                    lastPointsGained = 4;
                } else {
                    lastPointsGained = 2;
                }
                break;
            case 2:
                if (questionNumber == 10 || questionNumber == 11) {
                    lastPointsGained = 6;
                } else {
                    lastPointsGained = 3;
                }
                break;
            case 3:
                if (questionNumber == 10 || questionNumber == 11) {
                    lastPointsGained = 8;
                } else {
                    lastPointsGained = 4;
                }
                break;
            case 4:
                if (questionNumber == 10 || questionNumber == 11) {
                    lastPointsGained = 10;
                } else {
                    lastPointsGained = 5;
                }
                break;
        }
        return lastPointsGained;


    }

    //Method restarts quiz.
    public void restart(View view) {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    //Method opens browser with given link.
    public void readMore(View view) {
        String url = getString(R.string.url);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    //Method stops app.
    public void quit(View view) {
        finish();
    }

    public void score(View view) {
        setContentView(R.layout.scores);
        TextView finalScore = findViewById(R.id.final_score);
        ProgressBar cds2 = findViewById(R.id.cds_bar2);
        TextView scoreDesc = findViewById(R.id.score_desc);
        finalScore.setText(String.valueOf(pointsGained));
        cds2.setProgress(pointsGained);
        //Set description for given scores.
        if (pointsGained < 8) {
            scoreDesc.setText(getString(R.string.points_normal));
        } else if (pointsGained >= 8 && pointsGained <= 23) {
            scoreDesc.setText(getString(R.string.points_low));
        } else if (pointsGained >= 24 && pointsGained <= 44) {
            scoreDesc.setText(getString(R.string.points_average));
        } else if (pointsGained >= 45) {
            scoreDesc.setText(getString(R.string.points_high));
        }
    }
}
