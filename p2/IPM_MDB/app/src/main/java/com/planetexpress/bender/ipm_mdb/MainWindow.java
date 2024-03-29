package com.planetexpress.bender.ipm_mdb;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.planetexpress.bender.ipm_mdb.Model.Model;
import com.planetexpress.bender.ipm_mdb.Model.Movie;

import java.util.ArrayList;
import java.util.List;


public class MainWindow extends Activity {

    private ListView listView;
    public Model model;
    public Intent intent;
    private Integer page=1;
    private List<Movie> movies = new ArrayList<Movie>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);

        intent=getIntent();

        listView = (ListView) findViewById(R.id.moviesView);


        Intent intent = getIntent();
        final String accountName = intent.getStringExtra(GlobalNames.ARG_ACCOUNT_NAME);
        final String authToken = intent.getStringExtra(GlobalNames.ARG_AUTH_TOKEN);

        model = new Model(accountName, authToken);
        final ListMovies listMovies = new ListMovies();
        listMovies.execute(page.toString());

        SearchView searchView = (SearchView) findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                SearchMovie searchMovie = new SearchMovie();
                searchMovie.execute(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                LinearLayout linearLayoutParent = (LinearLayout) view;
                TextView id_tv = (TextView) linearLayoutParent.getChildAt(3);
                String id = id_tv.getText().toString();

                Intent myIntent = new Intent(MainWindow.this,MovieDetail.class);
                myIntent.putExtra("id",id);
                myIntent.putExtra("accountName",accountName);
                myIntent.putExtra("authToken",authToken);
                startActivity(myIntent);
            }
        };

        listView.setOnItemClickListener(itemClickListener);

        Button nextButtonPage = (Button) findViewById(R.id.nextPageButton);
        nextButtonPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page ++;
                new ListMovies().execute(page.toString());
            }
        });


    }

    public class ListMovies extends AsyncTask<String, Void, ArrayList<Movie>>{
        @Override
        protected ArrayList<Movie> doInBackground(String... strings) {
            String p = strings[0];
            return model.listMovies(p);
        }
        @Override
        protected void onPostExecute(ArrayList<Movie> m){
            if (m.isEmpty()){
                Button b = (Button) findViewById(R.id.nextPageButton);
                b.setText(R.string.noFilm);
            } else {
                movies.addAll(m);
                AdapterMovies<Movie> adapter = new AdapterMovies<Movie>(MainWindow.this, R.layout.row_listview, movies);
                listView.setAdapter(adapter);
            }
        }
    }

    public class SearchMovie extends AsyncTask<String, Void, ArrayList<Movie>>{

        @Override
        protected ArrayList<Movie> doInBackground(String...title) {
            return model.searchMovie(title[0]);
        }
        @Override
        protected void onPostExecute(ArrayList<Movie> movies){
            ListView listView = (ListView) findViewById(R.id.moviesView);
            AdapterMovies<Movie> adapter = new AdapterMovies<Movie>(MainWindow.this,R.layout.row_listview,movies);
            listView.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_window, menu);
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


}
