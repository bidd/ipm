package com.planetexpress.bender.ipm_mdb;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.planetexpress.bender.ipm_mdb.Model.Model;
import com.planetexpress.bender.ipm_mdb.Model.Movie;


public class MovieDetail extends Activity {
    String id;
    String accountName;
    String authToken;
    Movie movie;
    Boolean fav = false;
    Model model;
    Button favButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);


        final Intent intent= getIntent();
        id = intent.getStringExtra("id");
        accountName=intent.getStringExtra("accountName");
        authToken=intent.getStringExtra("authToken");

        model = new Model(accountName,authToken);

        Detail detail = new Detail();
        detail.execute(id);

        Button back_button= (Button) findViewById(R.id.backButtonMD);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        favButton= (Button) findViewById(R.id.fav_button);
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fav) {
                    UnFav unFav = new UnFav();
                    unFav.execute(id);
                }else {
                   Fav fav1 = new Fav();
                   fav1.execute(id);
                }


            }
        });

        Button commentsButton = (Button) findViewById(R.id.comments_button);
        commentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MovieDetail.this, comments.class);


                myIntent.putExtra("id", id);
                myIntent.putExtra("accountName", accountName);
                myIntent.putExtra("authToken", authToken);

                startActivity(myIntent);
            }
        });

        IsaFav isaFav = new IsaFav();
        isaFav.execute(id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.movie_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class Fav extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... strings) {
            model.favMovie(strings[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            favButton.setBackgroundResource(R.drawable.ic_star_black_24dp);
            fav=true;
        }
    }

    public class Detail extends AsyncTask<String,Void,Movie>{
        @Override
        protected Movie doInBackground(String... strings) {
            return model.detailMovie(strings[0]);
        }

        @Override
        protected void onPostExecute(Movie aMovie) {
            movie = aMovie;

            TextView tvTitle = (TextView) findViewById(R.id.title_content);
            tvTitle.setText(movie.get_title());

            TextView tvYear = (TextView) findViewById(R.id.year_content);
            tvYear.setText(movie.get_year().toString());

            TextView tvCategory = (TextView) findViewById(R.id.category_content);
            tvCategory.setText(movie.get_category());

            TextView tvSynopsis = (TextView) findViewById(R.id.synopsis_content);
            tvSynopsis.setText(movie.get_synopsis());

        }
    }

    public class IsaFav extends AsyncTask<String,Void,Boolean>{
        @Override
        protected Boolean doInBackground(String... strings) {
            return model.isFav(strings[0]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean)
                favButton.setBackgroundResource(R.drawable.ic_star_black_24dp);
        }
    }

    public class UnFav extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... strings) {
            model.unfavMovie(strings[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            favButton.setBackgroundResource(R.drawable.ic_star_outline_black_24dp);
            fav=false;
        }
    }
}
