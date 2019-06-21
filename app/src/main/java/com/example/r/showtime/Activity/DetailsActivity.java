package com.example.r.showtime.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.r.showtime.Adapter.FavItemAdapter;
import com.example.r.showtime.BuildConfig;
import com.example.r.showtime.DB.AppDatabase;
import com.example.r.showtime.DB.Favorite;
import com.example.r.showtime.DB.FavoriteDAO;
import com.example.r.showtime.Model.Movie;
import com.example.r.showtime.Model.TV;
import com.example.r.showtime.Model.Trailer;
import com.example.r.showtime.Model.TrailerResponse;
import com.example.r.showtime.R;
import com.example.r.showtime.RetrofitClient;
import com.example.r.showtime.Services.MovieService;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.aa_anime_name)
    TextView tv_title;
    @BindView(R.id.aa_description)
    TextView tv_desc;
    @BindView(R.id.aa_rating)
    TextView tv_rating;
    @BindView(R.id.aa_thumbnail)
    ImageView img;
    @BindView(R.id.aa_categorie)
    TextView tv_release;
    @BindView(R.id.favButton)
    MaterialFavoriteButton favoriteButton;
//    @BindView(R.id.fav_btn)
//    ImageButton imgBtnFav;
    @BindView(R.id.playTvId)
    TextView tv_play;
    @BindView(R.id.playTvIdTemp)
    TextView tv_temp;

    AppDatabase appDatabase;
    private Movie favorite;
    private SQLiteDatabase mDb;
    Movie movie;
    TV tv;
    int movie_id,favPosition;
    private final AppCompatActivity activity = DetailsActivity.this;
    String name, description, release, rating, image_url, poster, id;
    static boolean status;
    static int temp;
    static int val;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int activity_details = R.layout.activity_details;
        setContentView(activity_details);
        ButterKnife.bind(this);
        Intent intentThatStartedThisActivity = getIntent();
        //appDatabase=Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"favoritedb").build();
        if (intentThatStartedThisActivity.hasExtra("movies")) {
            temp=0;
            movie = getIntent().getParcelableExtra("movies");
            name = movie.getOriginalTitle();
            description = movie.getOverview();
            release = movie.getReleaseDate();
            rating = movie.getVoteAverage().toString();
            image_url = movie.getPosterPath();
            movie_id = movie.getId();
            poster = getIntent().getStringExtra("movie_image");
            CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar_id);
            collapsingToolbarLayout.setTitleEnabled(true);
            collapsingToolbarLayout.setTitle(name);

            tv_title.setText(name);
            tv_desc.setText(description);
            tv_rating.setText(rating);
            tv_release.setText(release);

            Glide.with(this)
                    .load(poster)
                    .into(img);
        } else if(intentThatStartedThisActivity.hasExtra("Tv")){
            temp=1;
            tv = getIntent().getParcelableExtra("Tv");
            name = tv.getName();
            description = tv.getOverview();
            release = tv.getFirstAirDate();
            rating = tv.getVoteAverage().toString();
            image_url = tv.getPosterPath();
            movie_id = tv.getId();
            poster = getIntent().getStringExtra("tv_image");
            CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar_id);
            collapsingToolbarLayout.setTitleEnabled(true);
            collapsingToolbarLayout.setTitle(name);

            tv_title.setText(name);
            tv_desc.setText(description);
            tv_rating.setText(rating);
            tv_release.setText(release);

            Glide.with(this)
                    .load(poster)
                    .into(img);
        }
        else{
            temp=2;
            name = getIntent().getStringExtra("fav_title");
            description =getIntent().getStringExtra("fav_desc");
            release = getIntent().getStringExtra("fav_release");
            rating = getIntent().getStringExtra("fav_rating");
            movie_id=getIntent().getIntExtra("fav_id",0);
            poster = getIntent().getStringExtra("fav_image");
           // favPosition=getIntent().getIntExtra("fav_pos",0);
            CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar_id);
            collapsingToolbarLayout.setTitleEnabled(true);
            collapsingToolbarLayout.setTitle(name);
            tv_title.setText(name);
            tv_desc.setText(description);
            tv_rating.setText(rating);
            tv_release.setText(release);
            Glide.with(this)
                    .load(poster)
                    .into(img);
            tv_play.setVisibility(View.GONE);
        }

       val= isFav(movie_id);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(val==1)
        {
            //favoriteButton.setImageResource(R.drawable.ic_favorite_black_24dp);
            favoriteButton.setFavorite(true);
            favoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
                @Override
                public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                    if(favorite==true)
                    {
//                        SharedPreferences.Editor editor = getSharedPreferences("com.example.r.showtime", MODE_PRIVATE).edit();
//                        editor.putBoolean("Favorite Added", true);
//                        editor.commit();
                        addFav();
                        Snackbar.make(buttonView, "Added to Favorite",
                                Snackbar.LENGTH_SHORT).show();
                    }
                    else {
                        deleteFav();
                        SharedPreferences.Editor editor = getSharedPreferences("com.example.r.showtime", MODE_PRIVATE).edit();
                        editor.putBoolean("Favorite Removed", true);
                        editor.commit();
                        Snackbar.make(buttonView, "Removed from Favorite",
                                Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            favoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
                @Override
                public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                    if(favorite)
                    {
//                        SharedPreferences.Editor editor = getSharedPreferences("com.example.r.showtime", MODE_PRIVATE).edit();
//                        editor.putBoolean("Favorite Added", true);
//                        editor.commit();
                        addFav();
                        Snackbar.make(buttonView, "Added to Favorite",
                                Snackbar.LENGTH_SHORT).show();
                    }
                    else {
                        deleteFav();
//                        SharedPreferences.Editor editor = getSharedPreferences("com.delaroystudios.movieapp.DetailActivity", MODE_PRIVATE).edit();
//                        editor.putBoolean("Favorite Removed", true);
//                        editor.commit();
                        Snackbar.make(buttonView, "Removed from Favorite",
                                Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
        }

        tv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchVideoDetails();
            }
        });
    }

    private String fetchVideoDetails() {
        Call<TrailerResponse> call = null;
        try{
            RetrofitClient client=new RetrofitClient();
            MovieService service=RetrofitClient.getClient().create(MovieService.class);
            if(temp==0)
                call = ((MovieService) service).getMovieVideos(movie_id, BuildConfig.THE_MOVIE_DB_API_TOKEN);
            else if(temp==1)
                call = ((MovieService) service).getTvVideos(movie_id, BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<TrailerResponse>() {
                @Override
                public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                    List<Trailer> trailer = response.body().getResults();
                    tv_temp.setText(trailer.get(0).getKey());
                    doRedirect();
                }

                @Override
                public void onFailure(Call<TrailerResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(DetailsActivity.this, "Error fetching trailer data", Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    void doRedirect()
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="+tv_temp.getText()));
        startActivity(intent);
    }
    private int isFav(final int id){
        return AppDatabase.getAppDatabase(getApplicationContext()).favoriteDAO().isFav(id);
    }
    private void deleteFav() {
        Favorite favorite=new Favorite();
        favorite.setTitle(name);
        favorite.setRating(rating);
        favorite.setDesc(description);
        favorite.setId(movie_id);
        favorite.setImageUrl(image_url);
        favorite.setReleaseDate(release);
        AppDatabase.getAppDatabase(getApplicationContext()).favoriteDAO().delete(favorite);

    }

    private void addFav()
    {
        Favorite favorite=new Favorite();
        favorite.setTitle(name);
        favorite.setRating(rating);
        favorite.setDesc(description);
        favorite.setId(movie_id);
        favorite.setImageUrl(image_url);
        favorite.setReleaseDate(release);
        favorite.setAdded(true);
        AppDatabase.getAppDatabase(getApplicationContext()).favoriteDAO().addFav(favorite);
        //Toast.makeText(getApplicationContext(),"Added",Toast.LENGTH_SHORT).show();
    }
}
