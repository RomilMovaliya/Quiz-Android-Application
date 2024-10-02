package com.example.quiztask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText nameInput;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInput = findViewById(R.id.nameInput);
        startButton = findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString();
                if (!name.isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                    intent.putExtra("USER_NAME", name);
                    startActivity(intent);
                } else {
                    nameInput.setError("Please enter your name");
                }
            }
        });
    }
}