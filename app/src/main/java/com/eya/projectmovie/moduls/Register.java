package com.eya.projectmovie.moduls;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.api.Authentication;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.perf.util.Timer;

import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Register extends AppCompatActivity {
    @BindView(R.id.prog)
    ProgressBar prog;
    @BindView(R.id.connecter)
    Button bt;
    @BindView(R.id.fname)
    EditText fn;
    @BindView(R.id.mail)
    EditText em;
    @BindView(R.id.pss)
    EditText pw;
    FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        ButterKnife.bind(this);
        auth = FirebaseAuth.getInstance();

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fulln = fn.getText().toString();
                String email = em.getText().toString();
                String pass = pw.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    em.setError("email is required!");
                    return;
                }

                if (TextUtils.isEmpty(pass)) {
                    pw.setError("password is required!");
                    return;
                }
                if (pass.length() < 6) {
                    pw.setError("password's length must be >=6!");
                    return;
                }

                prog.setVisibility(View.VISIBLE);

                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "user created", Toast.LENGTH_LONG).show();
                            FirebaseUser user = auth.getCurrentUser();
                            updateUI(user);
                            prog.setVisibility(View.GONE);
                            verify();
                            Intent i = new Intent(Register.this, Auth.class);
                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(Register.this, "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                            updateUI(null);


                        }
                    }
                });
                prog.setVisibility(View.VISIBLE);
            }
        });
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser == null) {
            // Toast.makeText(this, "", Toast.LENGTH_LONG).show();
            startActivity(new Intent(Register.this, Auth.class));


        }
    }
    private void verify(){
        auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Register.this, "please check your email!", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(Register.this,Auth.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(Register.this, "error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

