package com.anzelmasfile.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //Reiksmes, kad turetume access visoje funkcijoje
    Button startButton;
    TextView sumTextView;
    Button answerButton0;
    Button answerButton1;
    Button answerButton2;
    Button answerButton3;
    int locationOfCorrectAnswer;
    TextView resultTextView;
    int score = 0;
    int numberOfQuestions = 0;
    TextView scoreTextView;
    TextView timerTextView;
    Button playAgainButton;
    ConstraintLayout gameLayout;

    //Susikuriamas answers array
    ArrayList<Integer> answers = new ArrayList<Integer>();

    public void playAgain(View view) {
        score = 0;
        numberOfQuestions = 0;
        timerTextView.setText("30s");
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
        newQuestion();
        playAgainButton.setVisibility(View.INVISIBLE);
        resultTextView.setText("");
        new CountDownTimer(30100, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf(millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                resultTextView.setText("Done!");
                playAgainButton.setVisibility(View.VISIBLE);

            }
        }.start();
    }

    public void chooseAnswer(View view) {
        //Logas suzinoti, kuri mygtuka pasirinko useris
        Log.i("Tag", view.getTag().toString());
        if (Integer.toString(locationOfCorrectAnswer).equals(view.getTag().toString())) {
            Log.i("Correct", "You are right");
            //Jeigu pasirinko teisinga atsakyma, isspausdinti tai ir prideti prie rezultatu
            resultTextView.setText("Correct!");
            score++;
        } else {
            Log.i("Wrong", "You are wrong");
            //Jeigu pasirinko neteisinga answer, isspausdinti ir prideti prie questions
            resultTextView.setText("Wrong!");
        }
        //Prideda klausimo numeri, isspausdina rezultata ir duoda nauja klausima
        numberOfQuestions++;
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
        newQuestion();
    }

    //Metodas, kad paspaudus mygtuka jis taptu nematomas
    public void start(View view) {
        startButton.setVisibility(View.INVISIBLE);
        gameLayout.setVisibility(View.VISIBLE);
        playAgain(findViewById(R.id.timerTextView));

    }

    public void newQuestion() {
        //Sugeneruoja random numeri bounduose nuo 0 iki 100
        Random random = new Random();
        int a = random.nextInt(100);
        int b = random.nextInt(100);

        //Spausdina du random skaicius
        sumTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));

        //Random location of teisingo atsakymo
        locationOfCorrectAnswer = random.nextInt(4);

        //Isclearina visas reiksmes saugomas masyve, kitu atveju jos butu visada
        answers.clear();

        /*
        For loop'as eina per 4 vietas ir iesko teisingo atsakymo
        Jeigu ji randa, ideda a+b atsakyma
        Else ideda random number, kuris nera lygus a+b
        */

        for (int i = 0; i < 4; i++) {
            if (i == locationOfCorrectAnswer) {
                answers.add(a + b);
            } else {
                int wrongAnswer = random.nextInt(200);
                while (wrongAnswer == a + b) {
                    wrongAnswer = random.nextInt(200);
                }
                answers.add(wrongAnswer);
            }
        }

        answerButton0.setText(Integer.toString(answers.get(0)));
        answerButton1.setText(Integer.toString(answers.get(1)));
        answerButton2.setText(Integer.toString(answers.get(2)));
        answerButton3.setText(Integer.toString(answers.get(3)));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Duoda prieeiga prie mygtuku
        startButton = findViewById(R.id.startButton);
        sumTextView = findViewById(R.id.sumTextView);
        answerButton0 = findViewById(R.id.answerButton0);
        answerButton1 = findViewById(R.id.answerButton1);
        answerButton2 = findViewById(R.id.answerButton2);
        answerButton3 = findViewById(R.id.answerButton3);
        resultTextView = findViewById(R.id.resultTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);
        playAgainButton = findViewById(R.id.playAgainButton);
        gameLayout = findViewById(R.id.gameLayout);

        startButton.setVisibility(View.VISIBLE);
        gameLayout.setVisibility(View.INVISIBLE);

    }
}
