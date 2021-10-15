package com.example.pmhealthcare.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmhealthcare.R;

import java.util.List;

public class DegreesRecyclerAdapter extends RecyclerView.Adapter<DegreesRecyclerAdapter.ViewHolder>{

    private final Context mContext;
    public List<String> list;

    public interface degreesOnClickInterface{
        void degreeOnClick(int position);
    }
    public static degreesOnClickInterface onClickInterface;


    public DegreesRecyclerAdapter(Context context,List<String> list,degreesOnClickInterface onClickInterface){
        mContext=context;
        this.list=list;
        this.onClickInterface=onClickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.degree_chip_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.degreeTextView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView degreeTextView;
        ImageView removeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            degreeTextView=itemView.findViewById(R.id.degree_text_view);
            removeButton=itemView.findViewById(R.id.remove_degree_button);

            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickInterface.degreeOnClick(getAdapterPosition());
                }
            });
        }
    }
}
