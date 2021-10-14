package com.example.pmhealthcare.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pmhealthcare.R;
import com.example.pmhealthcare.database.RecordDetails;

import java.util.List;

public class RecordsRecyclerAdapter extends RecyclerView.Adapter<RecordsRecyclerAdapter.ViewHolder> {

    Context mContext;
    public static List<RecordDetails> list;


    public interface RecordsItemOnClickInterface{
        void onCLick(int position,Uri imageUri,String imageName);
    }

    public static RecordsItemOnClickInterface recordsItemOnClickInterface;

    public RecordsRecyclerAdapter(Context context, List<RecordDetails> list, RecordsItemOnClickInterface onClickInterface){
        mContext=context;
        RecordsRecyclerAdapter.list =list;


        recordsItemOnClickInterface=onClickInterface;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.record_list_view,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext).load(list.get(position).getImageID()).into(holder.recordsView);

        holder.textView.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView recordsView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recordsView=itemView.findViewById(R.id.record_image_view);
            textView=itemView.findViewById(R.id.record_display_name);

            recordsView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recordsItemOnClickInterface.onCLick(getAdapterPosition(), Uri.parse(list.get(getAdapterPosition()).getImageID()),list.get(getAdapterPosition()).getName());
                }
            });
        }
    }
}
