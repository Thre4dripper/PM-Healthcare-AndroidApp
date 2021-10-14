package com.example.pmhealthcare.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmhealthcare.R;
import com.example.pmhealthcare.database.DoctorDetails;

import java.util.List;

public class DigiDocRecyclerAdapter extends RecyclerView.Adapter<DigiDocRecyclerAdapter.ViewHolder> {

    private static final String TAG = "Digidoc";
    public Context mContext;

    List<DoctorDetails> list;
    public DigiDocRecyclerAdapter(Context context){
        this.mContext=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.digi_doc_list_view,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Log.d(TAG, "onBindViewHolder: called");
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
