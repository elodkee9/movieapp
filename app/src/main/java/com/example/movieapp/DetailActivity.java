package com.example.movieapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.movieapp.data.FavoriteDBHelper;
import com.example.movieapp.model.Movie;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;

public class DetailActivity extends AppCompatActivity {
    TextView nameOfMovie, plotSynopsis, userRating, releaseDate;
    ImageView imageView;


    Movie movie;
    String thumbnail, movieName, synopsis, rating, dateOfRelease;
    int movie_id;

    private FavoriteDBHelper favoriteDBHelper;
    private Movie favorite;
    private final AppCompatActivity activity = DetailActivity.this;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initCollapsingToolbar();

        imageView = (ImageView) findViewById(R.id.thumbnail_image_header);
        nameOfMovie = (TextView)findViewById(R.id.title);
        plotSynopsis = (TextView)findViewById(R.id.plotsynopsis);
        userRating = (TextView)findViewById(R.id.userrating);
        releaseDate = (TextView)findViewById(R.id.releasedate);

        Intent intentThatStartedThisActivity = getIntent();
        if(intentThatStartedThisActivity.hasExtra("movies")){
            movie = getIntent().getParcelableExtra("movies");

            thumbnail = movie.getPosterPath();
            movieName = movie.getOriginalTitle();
            synopsis = movie.getOverview();
            rating = Double.toString(movie.getVoteAverage());
            dateOfRelease = movie.getReleaseDate();
            movie_id = movie.getId();

            String poster = "https://image.tmdb.org/t/p/w500" + thumbnail;

            Glide.with(this)
                    .load(poster)
                    .placeholder(R.drawable.load)
                    .into(imageView);

            nameOfMovie.setText(movieName);
            plotSynopsis.setText(synopsis);
            userRating.setText(rating);
            releaseDate.setText(dateOfRelease);

        }else{
            Toast.makeText(this,"No API data", Toast.LENGTH_SHORT).show();
        }

        //favorite button with shared preferences

        MaterialFavoriteButton materialFavoriteButton = (MaterialFavoriteButton)findViewById(R.id.favorite_button);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        materialFavoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
            @Override
            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                if(favorite){
                    SharedPreferences.Editor editor = getSharedPreferences("com.example.movieapp.DetailActivity", MODE_PRIVATE).edit();
                    editor.putBoolean("Favorite added", true);
                    editor.commit();
                    saveFavorite();
                    Snackbar.make(buttonView, "Added to favorites", Snackbar.LENGTH_SHORT).show();
                }else{
                    int movie_id = getIntent().getExtras().getInt("id");
                    favoriteDBHelper = new FavoriteDBHelper(DetailActivity.this);
                    favoriteDBHelper.deleteFavorite(movie_id);
                    SharedPreferences.Editor editor = getSharedPreferences("com.example.movieapp.DetailActivity", MODE_PRIVATE).edit();
                    editor.putBoolean("Favorite removed", true);
                    editor.commit();
                    Snackbar.make(buttonView, "Removed from favorites", Snackbar.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void initCollapsingToolbar(){
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout)findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(scrollRange == -1){
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if(scrollRange + verticalOffset == 0){
                    collapsingToolbarLayout.setTitle(getString(R.string.movie_details));
                    isShow = true;
                }else if(isShow){
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    public void saveFavorite(){
        favoriteDBHelper = new FavoriteDBHelper(activity);
        favorite = new Movie();

        Double rate = movie.getVoteAverage();


        favorite.setId(movie_id);
        favorite.setOriginalTitle(movieName);
        favorite.setPosterPath(thumbnail);
        favorite.setVoteAverage(rate);
        favorite.setOverview(synopsis);

        favoriteDBHelper.addFavorite(favorite);
    }

}
