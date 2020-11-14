package com.example.sporttogether;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sporttogether.database.Database;

public class FirstLoginActivity extends AppCompatActivity {

    private EditText nombreEditText;
    private EditText apellidoEditText;
    private EditText edadEditText;
    private Button completarPerfilButton;
    private TextView welcomeUsernameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_login);

        nombreEditText = (EditText) findViewById(R.id.nombreEditText);
        apellidoEditText = (EditText) findViewById(R.id.apellidoEditText);
        edadEditText = (EditText) findViewById(R.id.edadEditText);
        welcomeUsernameTextView = (TextView) findViewById(R.id.welcomeUsernameTextView);

        welcomeUsernameTextView.setText(LogInActivity.usuario.getUsername() + "!");

        completarPerfilButton = (Button) findViewById(R.id.completarPerfilButton);

        completarPerfilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database.completeProfile(LogInActivity.usuario.getUsername(), nombreEditText.getText().toString(), apellidoEditText.getText().toString(),
                                        Integer.parseInt(edadEditText.getText().toString()));
            }
        });



    }
}