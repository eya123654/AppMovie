package com.eya.projectmovie.moduls;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.eya.projectmovie.R;
import com.eya.projectmovie.models.Bande_Annonce;
import com.eya.projectmovie.models.ResultVideo;
import com.eya.projectmovie.models.VideoPage;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Trailer extends YouTubeBaseActivity {
    @BindView(R.id.ytb_player)
    YouTubePlayerView vid;

    YouTubePlayer.OnInitializedListener onInitializedListener;
    private static final String key = "e66f4ebe720469069d93e9b821877a3b";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.affem_layout);
        ButterKnife.bind(this);
        Intent i = getIntent();
        Integer id = i.getIntExtra("vid_id", -1);
        Log.d("verif", "" + id);

//btn play


        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/").addConverterFactory(GsonConverterFactory.create()).build();
        Bande_Annonce bande_annonce = retrofit.create(Bande_Annonce.class);
        Call<VideoPage> call = bande_annonce.getvideos(id, key);
        String videoBaseurl = "https://www.youtube.com/watch?v=";
        call.enqueue(new Callback<VideoPage>() {
            @Override
            public void onResponse(Call<VideoPage> call, Response<VideoPage> response) {

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

                            }
                        };
                        //fin url
                        vid.initialize("AIzaSyC3jx-Y_1cVUXxYalP7I2uMBX2XoYggkcY", onInitializedListener);

                    }

            @Override
            public void onFailure(Call<VideoPage> call, Throwable t) {

            }






        });


    }
}
