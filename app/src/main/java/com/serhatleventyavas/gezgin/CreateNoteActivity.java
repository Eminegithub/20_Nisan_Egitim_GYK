package com.serhatleventyavas.gezgin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateNoteActivity extends AppCompatActivity {

    private Button btnSave;
    private ImageView btnBack;
    private Toolbar toolbar;
    private EditText editNote;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_note_activity);

        toolbar = findViewById(R.id.create_note_activity_toolbar);
        setSupportActionBar(toolbar);

        btnBack = findViewById(R.id.create_note_activity_btnBack);
        btnSave = findViewById(R.id.create_note_activity_btnSave);
        editNote = findViewById(R.id.create_note_activity_editNote);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    private void saveNote() {
        if (editNote.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(CreateNoteActivity.this, "Lütfen bir not giriniz.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent reStartApplication = new Intent(CreateNoteActivity.this, LoginActivity.class);
            reStartApplication.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(reStartApplication);
            return;
        }

        String uuid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference notesRef = mDatabase.getReference().child("notes");
        String noteId = notesRef.push().getKey();
        String note = editNote.getText().toString();

        if (noteId == null) {
            Toast.makeText(CreateNoteActivity.this, "Note Id alınamadı", Toast.LENGTH_SHORT).show();
            return;
        }

        notesRef.child(noteId).child("user_id").setValue(uuid);
        notesRef.child(noteId).child("note").setValue(note, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError == null) {
                    Toast.makeText(CreateNoteActivity.this, "Kayıt başarılı", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreateNoteActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
