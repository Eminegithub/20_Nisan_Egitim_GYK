package com.serhatleventyavas.gezgin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ListView rvCities;
    private HomeAdapter homeAdapter = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvCities = view.findViewById(R.id.home_fragment_rvCities);
        homeAdapter = new HomeAdapter();
        rvCities.setAdapter(homeAdapter);

        getPosts();
    }

    private void getPosts() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent reStartApplication = new Intent(getActivity(), LoginActivity.class);
            reStartApplication.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(reStartApplication);
            return;
        }

        final String uuid = FirebaseAuth.getInstance().getCurrentUser().getUid();


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference noteRef = database.getReference().child("posts");
        noteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<CityModel> list = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String userId = data.child("user_id").getValue().toString();
                    if (userId.equalsIgnoreCase(uuid)) {
                        String city = data.child("city_name").getValue().toString();
                        String desc = data.child("desc").getValue().toString();
                        String imageUrl = data.child("image_url").getValue().toString();
                        CityModel cityModel = new CityModel(city, desc, imageUrl);
                        list.add(cityModel);
                    }
                }

                homeAdapter.setList(list);
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
