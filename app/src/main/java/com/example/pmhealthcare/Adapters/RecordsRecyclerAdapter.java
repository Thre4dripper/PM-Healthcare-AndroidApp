package com.example.pmhealthcare.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.pmhealthcare.R;
import com.example.pmhealthcare.database.RecordDetails;

import java.util.List;

public class RecordsRecyclerAdapter extends RecyclerView.Adapter<RecordsRecyclerAdapter.ViewHolder> {

    Context mContext;
    public static List<RecordDetails> list;
    ProgressDialog progressDialog;


    public interface RecordsItemOnClickInterface{
        void onRecordsItemOnCLick(int position, Uri imageUri);
        void deleteOnClick(int position);
    }

    public static RecordsItemOnClickInterface recordsItemOnClickInterface;

    public RecordsRecyclerAdapter(Context context, List<RecordDetails> list, RecordsItemOnClickInterface onClickInterface){
        mContext=context;
        RecordsRecyclerAdapter.list =list;

        if(list.size()!=0)
        {
            progressDialog=new ProgressDialog(mContext);
            progressDialog.setTitle("Loading Records...");
            progressDialog.show();
        }
        else
        {
            Toast.makeText(mContext, "No Records Available", Toast.LENGTH_SHORT).show();
            Toast.makeText(mContext, "Or Check your Internet Connection", Toast.LENGTH_SHORT).show();
        }


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

        Glide.with(mContext).load(list.get(position).getImageID()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                progressDialog.dismiss();
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                //Toast.makeText(mContext, "loaded", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                return false;
            }
        }).into(holder.recordsView);

        holder.textView.setText(list.get(position).getName());
        //System.out.println(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView recordsView;
        TextView textView;
        ImageView deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recordsView=itemView.findViewById(R.id.record_image_view);
            textView=itemView.findViewById(R.id.record_display_name);
            deleteButton=itemView.findViewById(R.id.record_delete_button);

            recordsView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recordsItemOnClickInterface.onRecordsItemOnCLick(getAdapterPosition(), Uri.parse(list.get(getAdapterPosition()).getImageID()));
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recordsItemOnClickInterface.deleteOnClick(getAdapterPosition());
                }
            });
        }
    }
}
