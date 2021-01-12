package com.example.sporttogether.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sporttogether.R;
import com.example.sporttogether.database.Database;
import com.example.sporttogether.usuario.Usuario;

public class LogInActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private TextView userpassincorrect;
    Button logInButton;
    public static Usuario usuario = new Usuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        username = findViewById(R.id.editText_username);
        password =  findViewById(R.id.editText_password);
        userpassincorrect = findViewById(R.id.textView_userpassincorrect);
        logInButton = findViewById(R.id.login_button1);

        logInButton.setOnClickListener(v -> {
            if (username.getText().toString().isEmpty()){
                username.setHint(R.string.username);
                username.setHintTextColor(Color.parseColor("#FB1100"));
            }if (password.getText().toString().isEmpty()){
                password.setHint(R.string.password);
                password.setHintTextColor(Color.parseColor("#FB1100"));
            }else{
                if (Database.verifyLogin(username.getText().toString(), password.getText().toString())){
                    usuario = Database.getUserInfo(username.getText().toString());
                    Toast.makeText(getBaseContext(), "Bienvenido " + username.getText().toString() + "!", Toast.LENGTH_LONG).show();
                    openMatchesMainActivity();
                    LogInActivity.super.finish();
                    if (usuario.getFirstLogin()==1){
                        openFirstLoginActivity();
                    }
                }else{
                    userpassincorrect.setVisibility(View.VISIBLE);
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
                if (userpassincorrect.getVisibility() == View.VISIBLE){
                    userpassincorrect.setVisibility(View.INVISIBLE);
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
                if (userpassincorrect.getVisibility() == View.VISIBLE)
                    userpassincorrect.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        ImageView returnSign = (ImageView) findViewById(R.id.returnImage);
        returnSign.setOnClickListener(v -> finish());
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void openFirstLoginActivity(){
        Intent intent = new Intent(this, FirstLoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void openMatchesMainActivity(){
        Intent intent = new Intent(this, MatchesMainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

}