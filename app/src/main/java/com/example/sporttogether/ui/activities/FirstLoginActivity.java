package com.example.sporttogether.ui.activities;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_login);

        nombreEditText = (EditText) findViewById(R.id.nombreEditText);
        apellidoEditText = (EditText) findViewById(R.id.apellidoEditText);
        edadEditText = (EditText) findViewById(R.id.edadEditText);
        TextView welcomeUsernameTextView = (TextView) findViewById(R.id.welcomeUsernameTextView);

        welcomeUsernameTextView.setText(LogInActivity.usuario.getUsername() + "!");

        Button completarPerfilButton = (Button) findViewById(R.id.completarPerfilButton);

        completarPerfilButton.setOnClickListener(v -> Database.completeProfile(LogInActivity.usuario.getUsername(), nombreEditText.getText().toString(), apellidoEditText.getText().toString(),
                                Integer.parseInt(edadEditText.getText().toString())));



    }
}