package com.eya.projectmovie.moduls;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.eya.projectmovie.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgetPassword extends AppCompatActivity {
    @BindView(R.id.em)
    EditText email;
    @BindView(R.id.button2)
    Button reset;
    @BindView(R.id.progressBar)
    ProgressBar prb;
FirebaseAuth auth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpassword_layout);
        ButterKnife.bind(this);
        auth=FirebaseAuth.getInstance();
        prb.setVisibility(View.GONE);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=email.getText().toString();
                if (TextUtils.isEmpty(mail)){
                   email.setError("email is required!");
                    return;}
                if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                    email.setError("please enter a valid mail ");
                    email.requestFocus();
                    return;
                }
            prb.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ForgetPassword.this, "Check your email to reset password!", Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(ForgetPassword.this,Auth.class);
                            startActivity(i);
                        }
                        else
                            Toast.makeText(ForgetPassword.this, "Try again! something went wrong.", Toast.LENGTH_SHORT).show();
                   prb.setVisibility(View.GONE);
                    }
                });
            }

        });


    }
}
