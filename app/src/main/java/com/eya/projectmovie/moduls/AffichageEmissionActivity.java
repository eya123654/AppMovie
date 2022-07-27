package com.eya.projectmovie.moduls;

import static android.content.ContentValues.TAG;
import static java.lang.String.valueOf;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eya.projectmovie.R;
import com.eya.projectmovie.models.MovieApi;
import com.eya.projectmovie.models.Moviepage;
import com.eya.projectmovie.models.ResultM;
import com.eya.projectmovie.network.NetworkChangeListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.widget.Toolbar;


import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AffichageEmissionActivity extends AppCompatActivity implements AdapterM.OnPosterclicklistener {
    // lien des films populaire: https://api.themoviedb.org/3/movie/popular?api_key=e66f4ebe720469069d93e9b821877a3b
    private static final String key = "e66f4ebe720469069d93e9b821877a3b";
    List<ResultM> movieList;
    @BindView(R.id.recv)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar tb;

    AdapterM adapter;
    List<ResultM> filter;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_layout);
        ButterKnife.bind(this);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("TV movies");
        preferences = getSharedPreferences("mypre", MODE_PRIVATE);
        editor = preferences.edit();
        movieList = new ArrayList<>();
        //recyclerView =findViewById(R.id.recv);


        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/").addConverterFactory(GsonConverterFactory.create()).build();
        MovieApi movieApi = retrofit.create(MovieApi.class);
        Call<Moviepage> call = movieApi.getMovies(key);
        call.enqueue(new Callback<Moviepage>() {
            @Override
            public void onResponse(Call<Moviepage> call, Response<Moviepage> response) {


                movieList = response.body().getResults();

                PutDataIntoRecycleView(movieList);
            }

            @Override
            public void onFailure(Call<Moviepage> call, Throwable t) {
                Log.e("error", t.getMessage(), t);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        MenuItem item1 = menu.findItem(R.id.logout);
        item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                editor.clear();
                editor.commit();
                Intent i = new Intent(AffichageEmissionActivity.this, Auth.class);
                startActivity(i);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void PutDataIntoRecycleView(List<ResultM> movieList) {
        adapter = new AdapterM(this, movieList, this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onclick(int position) {
        Log.d("TAG", "onclick: item clicked!" + position);
        Toast.makeText(AffichageEmissionActivity.this, "item clicked" + position, Toast.LENGTH_SHORT).show();
        Intent i = new Intent(AffichageEmissionActivity.this, AffichageDetailsEmission.class);
        i.putExtra("movie_id", movieList.get(position).getId());
        i.putExtra("movie_title", movieList.get(position).getName());
        i.putExtra("overview", movieList.get(position).getOverview());
        i.putExtra("rating", movieList.get(position).getVoteAverage());

        startActivity(i);


    }

    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
}

