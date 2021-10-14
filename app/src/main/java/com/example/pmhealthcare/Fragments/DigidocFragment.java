package com.example.pmhealthcare.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pmhealthcare.Adapters.DigiDocRecyclerAdapter;
import com.example.pmhealthcare.R;


public class DigidocFragment extends Fragment {

    RecyclerView recyclerView;
    DigiDocRecyclerAdapter recyclerAdapter;

    public DigidocFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_digidoc, container, false);

        recyclerView=view.findViewById(R.id.digiDoc_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        recyclerAdapter=new DigiDocRecyclerAdapter(getContext());
        recyclerView.setAdapter(recyclerAdapter);
        return view;
    }
}