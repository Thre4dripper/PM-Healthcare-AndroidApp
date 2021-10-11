package com.example.pmhealthcare.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmhealthcare.R;
import java.util.List;

public class RecordsRecyclerAdapter extends RecyclerView.Adapter<RecordsRecyclerAdapter.ViewHolder> {

    Context mContext;
    List<Uri> list;
    public RecordsRecyclerAdapter(Context context,List<Uri> list){
        mContext=context;
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.record_list_view,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.recordsView.setImageURI(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView recordsView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recordsView=itemView.findViewById(R.id.record_image_view);
        }
    }
}
