package com.serhatleventyavas.gezgin;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText editEmail;
    private EditText editPassword;
    private EditText editPasswordVerify;
    private Button btnRegister;

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        mFirebaseAuth = FirebaseAuth.getInstance();

        editEmail = findViewById(R.id.register_activity_editEmail);
        editPassword = findViewById(R.id.register_activity_editPassword);
        editPasswordVerify = findViewById(R.id.register_activity_editPasswordVerify);
        btnRegister = findViewById(R.id.register_activity_btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        if (editEmail.getText().toString().equalsIgnoreCase("")
                || editPassword.getText().toString().equalsIgnoreCase("")
                || editPasswordVerify.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(RegisterActivity.this, "Boş alanları lütfen doldurunuz.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!editPassword.getText().toString().equalsIgnoreCase(editPasswordVerify.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "Şifreler uyuşmuyor", Toast.LENGTH_SHORT).show();
            return;
        }

        final android.app.AlertDialog progressDialog = new android.app.AlertDialog.Builder(RegisterActivity.this)
                .setTitle("İşlem Sürüyor")
                .setMessage("Lütfen Bekleyiniz.")
                .setCancelable(false)
                .show();

        Task<AuthResult> task =
                mFirebaseAuth.createUserWithEmailAndPassword(editEmail.getText().toString(), editPassword.getText().toString());
        task.addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("Başarılı")
                            .setMessage("Başarıyla kayıt oldunuz.")
                            .setNeutralButton("Giriş Yap", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Kayıt olunurken bir hata oluştu", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
