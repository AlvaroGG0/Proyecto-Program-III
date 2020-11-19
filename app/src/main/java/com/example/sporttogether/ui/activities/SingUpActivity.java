package com.example.sporttogether.ui.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sporttogether.R;
import com.example.sporttogether.database.Database;

public class SingUpActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        username = (EditText) findViewById(R.id.editText_username);
        password = (EditText) findViewById(R.id.editText_password);
        Button singupButton = (Button) findViewById(R.id.login_button1);
        ImageView returnSign = (ImageView) findViewById(R.id.returnImage);

        singupButton.setOnClickListener(v -> {
            Database.registerNewUser(username.getText().toString(), password.getText().toString());
            Toast.makeText(getBaseContext(), "Usuario " + username.getText().toString() + " registrado",
                    Toast.LENGTH_LONG).show();
        });

        returnSign.setOnClickListener(v -> finish());
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}