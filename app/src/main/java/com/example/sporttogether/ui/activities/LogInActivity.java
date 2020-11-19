package com.example.sporttogether.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sporttogether.R;
import com.example.sporttogether.database.Database;
import com.example.sporttogether.usuario.Usuario;

public class LogInActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    public static Usuario usuario = new Usuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        username = (EditText) findViewById(R.id.editText_username);
        password = (EditText) findViewById(R.id.editText_password);
        Button logInButton = (Button) findViewById(R.id.login_button1);

        logInButton.setOnClickListener(v -> {
            if (Database.verifyLogin(username.getText().toString(), password.getText().toString())){
                usuario = Database.getUserInfo(username.getText().toString());
                Toast.makeText(getBaseContext(), "Bienvenido " + username.getText().toString() + "!", Toast.LENGTH_LONG).show();
                openMatchesMainActivity();
                LogInActivity.super.finish();
                if (usuario.getFirstLogin()==1){
                    openFirstLoginActivity();
                }else{
                    Toast.makeText(getBaseContext(), "¡Pa'dentro!", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(getBaseContext(), "El usuario no existe",
                        Toast.LENGTH_LONG).show();
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