package com.eya.projectmovie.moduls;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.eya.projectmovie.R;
import com.eya.projectmovie.models.Bande_Annonce;
import com.eya.projectmovie.models.Details;
import com.eya.projectmovie.models.DetailPage;
import com.eya.projectmovie.models.VideoPage;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AffichageDetailsEmission extends YouTubeBaseActivity {
    @BindView(R.id.title)
    TextView movie_title;
    @BindView(R.id.poster)
    ImageView notrailer;
    @BindView(R.id.overview)
    TextView overview;
    @BindView(R.id.txtv)
    TextView tv;
    @BindView(R.id.imgedetail)
    ImageView img;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.runtime)
    TextView runtime;
    @BindView(R.id.saison)
    TextView season;
    @BindView(R.id.review)
    TextView review;
    @BindView(R.id.progressBar3)
    ProgressBar pb;
    @BindView(R.id.match)
    TextView matchs;
    @BindView(R.id.original)
    TextView original;
    @BindView(R.id.ytb_player)
    YouTubePlayerView vid;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    private static final String key = "e66f4ebe720469069d93e9b821877a3b";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moviedetails_layout);

        ButterKnife.bind(this);

        // detaillist=new ArrayList<>();
        Intent i = getIntent();
        Integer id = i.getIntExtra("movie_id", -1);
        Log.d("idtag", "" + id);
        String name = i.getStringExtra("movie_title");
        String over = i.getStringExtra("overview");
       

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/").addConverterFactory(GsonConverterFactory.create()).build();
        Details details = retrofit.create(Details.class);
        Call<DetailPage> call = details.getdetails(id, key);
        // rate.setVisibility(View.GONE);
        call.enqueue(new Callback<DetailPage>() {
            @Override
            public void onResponse(Call<DetailPage> call, Response<DetailPage> response) {
                pb.setVisibility(View.GONE);
                // rate.setVisibility(View.VISIBLE);
                Log.d("TAG22", "" + response.body().getOriginalName());
                movie_title.setText(response.body().getOriginalName());
                //  rb.setRating(new Float(response.body().getVoteAverage()));
                tv.setText("Overview ");
                overview.setText(response.body().getOverview());
                season.setText(response.body().getNumberOfSeasons() + " season");
                original.setText("Original Language : " + response.body().getOriginalLanguage().toUpperCase());
                matchs.setText(response.body().getVoteAverage() + "% Match");
                runtime.setText("" + response.body().getEpisodeRunTime());
                review.setText("(" + response.body().getLastAirDate() + ")");
                date.setText(response.body().getFirstAirDate());
                Glide.with(AffichageDetailsEmission.this).load("https://image.tmdb.org/t/p/w500" + response.body().getPosterPath()).centerCrop().into(img);
                Glide.with(AffichageDetailsEmission.this).load("https://image.tmdb.org/t/p/w500" + response.body().getPosterPath()).centerCrop().into(notrailer);
            }

            @Override
            public void onFailure(Call<DetailPage> call, Throwable t) {

            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AffichageDetailsEmission.this, Trailer.class);
                intent.putExtra("vid_id", id);
                Log.d("id", "" + id);
                Toast.makeText(AffichageDetailsEmission.this, "ItemId" + view.getId(), Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        Bande_Annonce bande_annonce = retrofit.create(Bande_Annonce.class);
        Call<VideoPage> call2 = bande_annonce.getvideos(id, key);
        call2.enqueue(new Callback<VideoPage>() {
            @Override
            public void onResponse(Call<VideoPage> call, Response<VideoPage> response) {
               if (response.body().getResults().size()>=1) {
                   Log.d("size_", "" + response.body().getResults().size());
                   String m = response.body().getResults().get(0).getKey();
                   Log.d("key", m);

                   //url vid
                   onInitializedListener = new YouTubePlayer.OnInitializedListener() {
                       @Override
                       public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                           youTubePlayer.loadVideo(m);


                       }

                       @Override
                       public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                           Toast.makeText(AffichageDetailsEmission.this, "erreur", Toast.LENGTH_SHORT).show();
                       }
                   };
                   //fin url
                   if (response.body().getResults().size() >= 1) {
                       Log.d("if", "" + response.body().getResults().size());
                       vid.initialize("AIzaSyC3jx-Y_1cVUXxYalP7I2uMBX2XoYggkcY", onInitializedListener);
                       notrailer.setVisibility(View.INVISIBLE);
                   } else {
                       notrailer.setVisibility(View.VISIBLE);
                   }
               }


            }

            @Override
            public void onFailure(Call<VideoPage> call, Throwable t) {

            }


        });
    }

}


