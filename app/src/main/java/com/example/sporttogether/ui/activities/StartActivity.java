package com.example.sporttogether.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sporttogether.R;
import com.example.sporttogether.database.Database;

public class StartActivity extends AppCompatActivity {

    private VideoView bgVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Database.startConnection(this);
        Toast.makeText(getBaseContext(), "Conectado a la base de datos",
                Toast.LENGTH_LONG).show();

        bgVideoView = (VideoView) findViewById(R.id.bg_videoView);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.bgvideo);
        bgVideoView.setVideoURI(uri);
        bgVideoView.start();
        bgVideoView.setOnPreparedListener(mp -> mp.setLooping(true));

        Button logInButton = (Button) findViewById(R.id.login_button);
        logInButton.setOnClickListener(v -> openLogInActivity());

        Button singUpButton = (Button) findViewById(R.id.signup_button);
        singUpButton.setOnClickListener(v -> openSignUpActivity());

    }

    @Override
    protected void onResume() {
        super.onResume();
        bgVideoView.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Database.endConnection();
        Toast.makeText(getBaseContext(), "Desconectado de la base de datos",
                Toast.LENGTH_LONG).show();
    }

    public void openLogInActivity(){
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void openSignUpActivity(){
        Intent intent = new Intent(this, SingUpActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

}