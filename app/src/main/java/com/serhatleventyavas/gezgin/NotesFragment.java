package com.serhatleventyavas.gezgin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotesFragment extends Fragment {

    private ListView rvNotes;
    private ImageView btnCreateNote;
    private NotesAdapter noteAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notes_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        noteAdapter = new NotesAdapter();

        rvNotes = view.findViewById(R.id.notes_fragment_rvNotes);
        rvNotes.setAdapter(noteAdapter);

        btnCreateNote = view.findViewById(R.id.notes_fragment_btnCreate);

        btnCreateNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Activity olusturabiliriz. Bu aktivite sayesinde not eklemeleri yapabiliriz.
                Intent intent = new Intent(getActivity(), CreateNoteActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getNotes();
    }

    private void getNotes() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent reStartApplication = new Intent(getActivity(), LoginActivity.class);
            reStartApplication.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(reStartApplication);
            return;
        }

        final String uuid = FirebaseAuth.getInstance().getCurrentUser().getUid();


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference noteRef = database.getReference().child("notes");
        noteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<NoteModel> list = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String userId = data.child("user_id").getValue().toString();
                    if (userId.equalsIgnoreCase(uuid)) {
                        String note = data.child("note").getValue().toString();
                        NoteModel noteModel = new NoteModel();
                        noteModel.setNote(note);
                        list.add(noteModel);
                    }
                }
                noteAdapter.setNotesList(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Tek defalık kullanım
        /*noteRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
    }
}
