package com.example.pmhealthcare.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pmhealthcare.R;
import com.example.pmhealthcare.database.DoctorDetails;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DigiDocRecyclerAdapter extends RecyclerView.Adapter<DigiDocRecyclerAdapter.ViewHolder> {

    private static final String TAG = "Digidoc";
    public Context mContext;

    List<DoctorDetails> list;
    public DigiDocRecyclerAdapter(Context context,List<DoctorDetails> list,DigiDocOnClickInterface onClickInterface){
        this.mContext=context;
        this.list=list;

        digiDocOnClickInterface=onClickInterface;
    }

    public interface DigiDocOnClickInterface{
        void digiOnClick(int position);
    }

    DigiDocOnClickInterface digiDocOnClickInterface;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.digi_doc_list_view,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(mContext).load(list.get(position).getDoctorDp()).into(holder.doctorDp);

        holder.doctorName.setText(list.get(position).getDoctorName());
        holder.doctorQualification.setText(list.get(position).getQualifications());
        holder.doctorSpeciality.setText(list.get(position).getSpecialization());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView doctorDp;
        TextView doctorName,doctorQualification,doctorSpeciality;

        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            doctorDp=itemView.findViewById(R.id.current_doctor_dp);
            doctorName=itemView.findViewById(R.id.current_doctor_name);
            doctorQualification=itemView.findViewById(R.id.current_doctor_qualification);
            doctorSpeciality=itemView.findViewById(R.id.current_doctor_speciality);

            cardView=itemView.findViewById(R.id.digidoc_card_view);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    digiDocOnClickInterface.digiOnClick(getAdapterPosition());
                }
            });
        }
    }
}
