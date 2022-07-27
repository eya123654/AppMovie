package com.eya.projectmovie.moduls;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.eya.projectmovie.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Auth extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    FirebaseAuth mAuth;
    @BindView(R.id.login)
    EditText log;
    @BindView(R.id.pw) EditText pass;
    @BindView(R.id.connecter)
    Button btn;
    @BindView(R.id.prog)
    ProgressBar pr;
    @BindView(R.id.sign) Button register;
    @BindView(R.id.forget)
    TextView forgetpassword;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentification_layout);
        ButterKnife.bind(this);
        mAuth=FirebaseAuth.getInstance();
        preferences=getSharedPreferences("mypre",MODE_PRIVATE);
        editor=preferences.edit();
        if (preferences.contains("save_login")) {
            Intent i=new Intent(Auth.this, AffichageEmissionActivity.class);
            startActivity(i);}
        else {

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String email=log.getText().toString();
                String psw=pass.getText().toString();
                if (TextUtils.isEmpty(email)){
                    log.setError("email is required!");
                    return;}
                if (TextUtils.isEmpty(psw)){
                    pass.setError("password is required!");
                    return;}
                if( psw.length()<6){
                    pass.setError("password's length must be >=6!");
                    return;
                }

                pr.setVisibility(View.VISIBLE);
                //authentificate the user
                mAuth.signInWithEmailAndPassword(email,psw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Auth.this,"you logged in successfully",Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                           // Log.d("err",""+user);
                            pr.setVisibility(View.GONE);
                            Isverified();
                            Intent i=new Intent(Auth.this, AffichageEmissionActivity.class);
                            startActivity(i);
                        }
                        else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(Auth.this, "Error "+ task.getException(), Toast.LENGTH_SHORT).show();




                        }}
                });
                editor.putString("save_login",email);
                editor.putString("save_pw",psw);
                editor.commit();
              //  Intent i=new Intent(Auth.this, AffichageEmissionActivity.class);
                //startActivity(i);
                }

        });}
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Auth.this, Register.class);
                startActivity(i);
            }
        });
        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent inte =new Intent(Auth.this,ForgetPassword.class);
                startActivity(inte);
            }
        });



    }
private void Isverified(){
    String email=log.getText().toString();
    String psw=pass.getText().toString();
       if(mAuth.getCurrentUser().isEmailVerified()){
           Intent i=new Intent (Auth.this,AffichageEmissionActivity.class);
           startActivity(i);

    }
}

    }

