package com.example.quiztask;
import com.android.volley.toolbox.JsonArrayRequest;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    private TextView welcomeMessage, questionCount, questionTitle, questionDetails;
    private ProgressBar progressBar;
    private Button answer1, answer2, answer3, submitButton;
    private int currentQuestion = 0;
    private int totalQuestions;
    private int score = 0;
    private ArrayList<JSONObject> questionsList = new ArrayList<>();
    private JSONObject currentQuestionObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        welcomeMessage = findViewById(R.id.welcomeMessage);
        questionCount = findViewById(R.id.questionCount);
        progressBar = findViewById(R.id.progressBar);
        questionTitle = findViewById(R.id.questionTitle);
        questionDetails = findViewById(R.id.questionDetails);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        submitButton = findViewById(R.id.submitButton);

        String userName = getIntent().getStringExtra("USER_NAME");
        welcomeMessage.setText("Welcome " + userName + "!");

        // Fetch the questions from the API
        fetchQuestions();

        // Handle answer button clicks
        answer1.setOnClickListener(view -> selectAnswer(1));
        answer2.setOnClickListener(view -> selectAnswer(2));
        answer3.setOnClickListener(view -> selectAnswer(3));

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentQuestion < totalQuestions - 1) {
                    currentQuestion++;
                    setQuestion();
                } else {
                    // Quiz finished, go to Quiz Completed Screen
                    Intent intent = new Intent(QuizActivity.this, QuizCompletedActivity.class);
                    intent.putExtra("USER_NAME", userName);
                    intent.putExtra("SCORE", score);
                    intent.putExtra("TOTAL_QUESTIONS", totalQuestions);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    // Fetch questions from JSON API
    private void fetchQuestions() {
        String url = "http://192.168.29.37/coderkube/php/index.php";  // Ensure this is your actual URL
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            totalQuestions = response.length();
                            for (int i = 0; i < totalQuestions; i++) {
                                questionsList.add(response.getJSONObject(i));
                            }
                            setQuestion();  // Set the first question
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(QuizActivity.this, "Error parsing JSON data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(QuizActivity.this, "Error fetching data: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        queue.add(request);
    }


    // Set the question details dynamically from JSON
    private void setQuestion() {
        try {
            currentQuestionObject = questionsList.get(currentQuestion);
            questionCount.setText((currentQuestion + 1) + "/" + totalQuestions);
            progressBar.setProgress(((currentQuestion + 1) * 100) / totalQuestions);

            questionTitle.setText("Question " + (currentQuestion + 1));
            questionDetails.setText(currentQuestionObject.getString("question"));

            JSONArray optionsArray = currentQuestionObject.getJSONArray("quiz_options");
            answer1.setText(optionsArray.getJSONObject(0).getString("option"));
            answer2.setText(optionsArray.getJSONObject(1).getString("option"));
            answer3.setText(optionsArray.getJSONObject(2).getString("option"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Handle answer selection
    private void selectAnswer(int answerNumber) {
        try {
            JSONArray optionsArray = currentQuestionObject.getJSONArray("quiz_options");
            JSONObject selectedOption = optionsArray.getJSONObject(answerNumber - 1);
            int correctAnswerId = currentQuestionObject.getInt("correct_answer_option_id");
            if (selectedOption.getInt("option_id") == correctAnswerId) {
                score += 1;
                Toast.makeText(QuizActivity.this, "Correct Answer!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(QuizActivity.this, "Wrong Answer!", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
