package com.serhatleventyavas.gezgin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
        homeAdapter.setList(CityModel.mockData());
        rvCities.setAdapter(homeAdapter);
    }
}
