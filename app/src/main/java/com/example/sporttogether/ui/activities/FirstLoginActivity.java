package com.example.sporttogether.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sporttogether.R;
import com.example.sporttogether.database.Database;

public class FirstLoginActivity extends AppCompatActivity {

    private EditText nombreEditText;
    private EditText apellidoEditText;
    private EditText edadEditText;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_login);

        nombreEditText = findViewById(R.id.nombreEditText);
        apellidoEditText = findViewById(R.id.apellidoEditText);
        edadEditText = findViewById(R.id.edadEditText);
        TextView welcomeUsernameTextView = findViewById(R.id.welcomeUsernameTextView);

        welcomeUsernameTextView.setText(LogInActivity.usuario.getUsername() + "!");

        Button completarPerfilButton = findViewById(R.id.completarPerfilButton);

        completarPerfilButton.setOnClickListener(v -> {
            Database.completeProfile(LogInActivity.usuario.getUsername(), nombreEditText.getText().toString(), apellidoEditText.getText().toString(),
                    Integer.parseInt(edadEditText.getText().toString()));
            openMatchesMainActivity();
            FirstLoginActivity.super.finish();
        });

    }

    public void openMatchesMainActivity(){
        Intent intent = new Intent(this, MatchesMainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}