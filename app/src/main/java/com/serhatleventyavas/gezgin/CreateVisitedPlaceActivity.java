package com.serhatleventyavas.gezgin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class CreateVisitedPlaceActivity extends AppCompatActivity {

    private FirebaseStorage mFirebaseStorage;
    private FirebaseDatabase mFirebaseDatabase;

    private Uri filePath;

    private ImageView btnBack;
    private Button btnSave;
    private EditText editCityName;
    private EditText editDesc;
    private ImageView imgCity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_visited_place_activity);

        btnBack = findViewById(R.id.create_visited_place_activity_btnBack);
        btnSave = findViewById(R.id.create_visited_place_activity_btnSave);
        editCityName = findViewById(R.id.create_visited_place_activity_editCityName);
        editDesc = findViewById(R.id.create_visited_place_activity_editCityNote);
        imgCity = findViewById(R.id.create_visited_place_activity_imgPlace);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePost();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Bir önceki aktiviteye dönmemizi sağlar
            }
        });

        imgCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhoto();
            }
        });
    }

    // Burada basit bir intent işlemi ile fotoğraf seçmek için galeriyi acacaz.
    private void selectPhoto() {
        Intent selectPhotoIntent = new Intent();
        selectPhotoIntent.setType("image/*");
        selectPhotoIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(selectPhotoIntent, "Lütfen Resim Seçiniz"), 1881);
    }

    private void savePost() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent reStartApplication = new Intent(CreateVisitedPlaceActivity.this, LoginActivity.class);
            reStartApplication.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(reStartApplication);
            return;
        }

        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = mFirebaseDatabase.getReference().child("posts");

        final String cityName = editCityName.getText().toString();
        final String desc = editDesc.getText().toString();

        if (cityName.equalsIgnoreCase("") || desc.equalsIgnoreCase("")) {
            Toast.makeText(CreateVisitedPlaceActivity.this, "Lütfen boş alanları doldurunuz", Toast.LENGTH_SHORT).show();
            return;
        }

        final String uuid = databaseReference.push().getKey().toString();
        if (uuid == null) {
            return;
        }

        final String imageName = uuid + "_image";
        mFirebaseStorage = FirebaseStorage.getInstance();
        mFirebaseStorage.getReference()
                .child(imageName)
                .putFile(filePath)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    // TODO VERITABANINA KAYIT OLACAK.
                    databaseReference.child(uuid).child("image_url").setValue(imageName);
                    databaseReference.child(uuid).child("user_id").setValue(userId);
                    databaseReference.child(uuid).child("city_name").setValue(cityName);
                    databaseReference.child(uuid).child("desc").setValue(desc, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if (databaseError == null) {
                                AlertDialog dialog = new AlertDialog.Builder(CreateVisitedPlaceActivity.this)
                                        .setTitle("Başarılı")
                                        .setMessage("Post başarıyla oluşturuldu")
                                        .setNeutralButton("Tamam", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                onBackPressed();
                                            }
                                        }).create();
                                dialog.show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(CreateVisitedPlaceActivity.this, "Resim yüklenemedi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1881 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            Picasso.with(CreateVisitedPlaceActivity.this)
                    .load(filePath)
                    .into(imgCity);

        } else {
            Toast.makeText(CreateVisitedPlaceActivity.this, "Resim seçilemedi", Toast.LENGTH_SHORT).show();
        }
    }
}
