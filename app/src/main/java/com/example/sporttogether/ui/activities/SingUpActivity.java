package com.example.sporttogether.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sporttogether.R;
import com.example.sporttogether.database.Database;

public class SingUpActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private TextView userExists;
    private Button singupButton;
    private ImageView returnSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        username = findViewById(R.id.editText_username);
        password = findViewById(R.id.editText_password);
        userExists = findViewById(R.id.user_exists);
        singupButton = findViewById(R.id.login_button1);
        returnSign = findViewById(R.id.returnImage);

        singupButton.setOnClickListener(v -> {
            if (username.getText().toString().isEmpty()){
                username.setHint(R.string.username);
                username.setHintTextColor(Color.parseColor("#FB1100"));
            }if (password.getText().toString().isEmpty()){
                password.setHint(R.string.password);
                password.setHintTextColor(Color.parseColor("#FB1100"));
            }else {
                if (userExists.getVisibility() == View.VISIBLE){
                    Toast.makeText(getBaseContext(), "El usuario ya existe", Toast.LENGTH_LONG).show();
                }else{
                    Database.registerNewUser(username.getText().toString(), password.getText().toString());
                    Toast.makeText(getBaseContext(), "Usuario " + username.getText().toString() + " registrado",
                            Toast.LENGTH_LONG).show();
                    openLogInActivity();
                    SingUpActivity.super.finish();
                }
            }
        });

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (username.getCurrentHintTextColor() == Color.parseColor("#FB1100")){
                    username.setHintTextColor(Color.parseColor("#32FFFFFF"));
                }
                if (Database.verifyRegisterUser(username.getText().toString())){
                    userExists.setVisibility(View.VISIBLE);
                }else{
                    userExists.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (password.getCurrentHintTextColor() == Color.parseColor("#FB1100")){
                    password.setHintTextColor(Color.parseColor("#32FFFFFF"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        returnSign.setOnClickListener(v -> finish());
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void openLogInActivity(){
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}