package com.example.quiztask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class QuizCompletedActivity extends AppCompatActivity {

    private TextView congratulationsMessage, scoreDisplay;
    private Button takeNewQuizButton, finishButton;
    private String userName;
    private int score, totalQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_completed);

        // Retrieve data passed from previous activity
        userName = getIntent().getStringExtra("USER_NAME");
        score = getIntent().getIntExtra("SCORE", 0);
        totalQuestions = getIntent().getIntExtra("TOTAL_QUESTIONS", 5);

        // Initialize views
        congratulationsMessage = findViewById(R.id.congratulationsMessage);
        scoreDisplay = findViewById(R.id.scoreDisplay);
        takeNewQuizButton = findViewById(R.id.takeNewQuizButton);
        finishButton = findViewById(R.id.finishButton);

        // Set messages
        congratulationsMessage.setText("Congratulations " + userName + "!");
        scoreDisplay.setText("YOUR SCORE: " + score + "/" + totalQuestions);

        // Set button actions
        takeNewQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Restart the quiz
                Intent intent = new Intent(QuizCompletedActivity.this, QuizActivity.class);
                intent.putExtra("USER_NAME", userName); // pass the user name again if needed
                startActivity(intent);
                finish();
            }
        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Finish the quiz and close the app
                finish();
            }
        });
    }
}