package com.eya.projectmovie.moduls;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.eya.projectmovie.R;
import com.google.api.Authentication;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.p)
    ProgressBar pr;
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, Auth.class));
                finish();
                pr.setVisibility(View.INVISIBLE);
            }
        },4000);
       // Intent i=new Intent(MainActivity.this,Authentification.class);
        //startActivity(i);
    }
}
