package com.example.sporttogether;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.sporttogether.database.Database;

public class MainActivity extends AppCompatActivity {

    private VideoView bgVideoView;
    private Button logInButton;
    private Button singUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Database.startConnection(this);
        Toast.makeText(getBaseContext(), "Conectado a la base de datos",
                Toast.LENGTH_LONG).show();

        bgVideoView = (VideoView) findViewById(R.id.bg_videoView);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.bgvideo);
        bgVideoView.setVideoURI(uri);
        bgVideoView.start();
        bgVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        logInButton = (Button) findViewById(R.id.login_button);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogInActivity();
            }
        });

        singUpButton = (Button) findViewById(R.id.signup_button);
        singUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUpActivity();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        bgVideoView.start();
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