package com.example.sporttogether.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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

        bgVideoView = findViewById(R.id.bg_videoView);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.bgvideo);
        bgVideoView.setVideoURI(uri);
        bgVideoView.start();
        bgVideoView.setOnPreparedListener(mp -> mp.setLooping(true));

        Button logInButton = findViewById(R.id.login_button);
        logInButton.setOnClickListener(v -> openLogInActivity());

        Button singUpButton = findViewById(R.id.signup_button);
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