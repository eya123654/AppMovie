package com.eya.projectmovie.moduls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.eya.projectmovie.R;
import com.eya.projectmovie.models.DetailPage;

import java.util.List;

public class AdapterT extends RecyclerView.Adapter<AdapterT.MyViewHolder> {
    private Context mContext;
    private List<DetailPage> data;
  //  private AdapterM.OnPosterclicklistener mOnposter;

    public AdapterT(Context mContext, List<DetailPage> data) {
        this.mContext = mContext;
        this.data = data;

    }
    @NonNull
    @Override
    public AdapterT.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        v=inflater.inflate(R.layout.item_details,parent,false);

        return  new AdapterT.MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterT.MyViewHolder holder, int position) {

        Glide.with(mContext).load("https://image.tmdb.org/t/p/w500"+data.get(position).getBackdropPath()).centerCrop().into(holder.img);
        holder.movie_title.setText(data.get(position).getOriginalName());
       holder.rb.setRating(new Float(data.get(position).getVoteAverage()));
       holder.overview.setText(data.get(position).getOverview());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView movie_title;
        RatingBar rb;
        TextView overview;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img=(ImageView) itemView.findViewById(R.id.imgdetail);
movie_title=(TextView) itemView.findViewById(R.id.title);
rb=(RatingBar) itemView.findViewById(R.id.ratingBar_detail);
overview=(TextView) itemView.findViewById(R.id.textView);
        }
    }
}
