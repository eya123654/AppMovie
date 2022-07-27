package com.eya.projectmovie.moduls;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.eya.projectmovie.R;
import com.eya.projectmovie.models.Moviepage;
import com.eya.projectmovie.models.ResultM;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdapterM extends RecyclerView.Adapter<AdapterM.MyViewHolder> implements Filterable {
    private Context mContext;
    private List<ResultM> data;
    private OnPosterclicklistener mOnposter;
    private List<ResultM> allMovies ;


    public AdapterM(Context mContext, List<ResultM> data, OnPosterclicklistener onPosterclicklistener) {
        this.mContext = mContext;
        this.data = data;
        this.mOnposter = onPosterclicklistener;
        allMovies = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        v = inflater.inflate(R.layout.itemovie_layout, parent, false);

        return new MyViewHolder(v, mOnposter);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(mContext).load("https://image.tmdb.org/t/p/w500" + allMovies.get(position).getPosterPath()).into(holder.img);


    }

    @Override
    public int getItemCount() {
        if (allMovies != null)
            return allMovies.size();
        else
            return 0;
    }

    @Override
    public Filter getFilter() {
        // return filter;


        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                if (charSequence.toString().isEmpty()) { //testit ken bulle de recherche vide ykhali liste kima hya
                    // filtertolist.addAll(allMovies);
                    allMovies = data;
                    Log.d("aallmovies_bff_boucl : ", "" + allMovies.size());

                } else {
                    List<ResultM> filtertolist = new ArrayList<>();// declarit list bch nhot feha film filté


                    for (ResultM movie : allMovies) {
                        Log.d("movi_title : ", " hello ");


                        Log.d("movie_title : ", movie.getName());
                        try {
                            if (movie.getName().toLowerCase().contains(charSequence.toString().toLowerCase())) { //yparcouri fel liste aflem w ytesti ken nom fil contient carractére  fel bulle de recherche
                                filtertolist.add(movie);
                            }
                        } catch (Exception e) {
                            Log.d("Exce adapterM : ", e.getMessage());

                        }


                    }
                   /* Log.d("aallmovies_af_boucle : ", "" + allMovies.size());
                    Log.d("aallmovies_af_be_fil : ", "" + filtertolist.size());*/

                    allMovies = filtertolist;
                }

                FilterResults filterResults = new FilterResults(); // bch nhot resultat fi variable
                filterResults.values = allMovies;
                return filterResults;

            }


            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                //data.clear();
                //  Log.d("prob",""+data.size());
                //   data.addAll((Collection<? extends ResultM>) filterResults.values); // bch tfasakh liste 9dima w t'affichie list filtré
                //  Log.d("probi",""+data.size());
                allMovies = (ArrayList<ResultM>) filterResults.values;
                notifyDataSetChanged();

            }
        };

    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img;
        OnPosterclicklistener onPosterclicklistener;

        public MyViewHolder(@NonNull View itemView, OnPosterclicklistener onPosterclicklistener) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.imageView);
            this.onPosterclicklistener = onPosterclicklistener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onPosterclicklistener.onclick(getAdapterPosition());
        }
    }

    public interface OnPosterclicklistener {
        void onclick(int position);
    }
/*public ResultM getSelectedMovie(int position){
        if(data!=null){
            if (data.size()>0){
               / return( "https://image.tmdb.org/t/p/w500"+data.get(position).getPosterPath());
            }
        }
        return  null;
}*/
}

